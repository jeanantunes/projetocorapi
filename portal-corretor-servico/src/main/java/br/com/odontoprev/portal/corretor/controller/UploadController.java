package br.com.odontoprev.portal.corretor.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.odontoprev.portal.corretor.dto.FileUploadLoteDCMS;
import br.com.odontoprev.portal.corretor.dto.FileUploadLoteDCMSResponse;
import br.com.odontoprev.portal.corretor.model.TbodUploadForcavenda;
import br.com.odontoprev.portal.corretor.service.EmpresaService;
import br.com.odontoprev.portal.corretor.service.UploadService;

@Controller
public class UploadController {
	
	private static final Logger log = LoggerFactory.getLogger(UploadController.class);
	
	@Autowired
	UploadService uploadService;

	@Autowired
	private EmpresaService empresaService;
	
	@RequestMapping(value="upload/{cdCorretora}", method = RequestMethod.POST)
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile uploadFile, @PathVariable String cdCorretora  ) throws IOException, EncryptedDocumentException, InvalidFormatException{	
		
		File serverFile = serverFile(uploadFile);  
				
		BufferedReader bufferedReader = null;
		
		try {
			String lines;
			bufferedReader = new BufferedReader(new FileReader(serverFile.getAbsolutePath()));
			bufferedReader.readLine();
			while ((lines = bufferedReader.readLine()) != null) {				
				uploadService.addDadosUpload(csvToArrayList(lines, cdCorretora, uploadFile.getOriginalFilename()));  
			}
			
		} catch (IOException e) {
			log.info("##### Error csv: " + e.getMessage());
			return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			if (bufferedReader != null) bufferedReader.close();
		}
		      
        return ResponseEntity.ok("Transação realizada com sucesso");
	}

	public TbodUploadForcavenda csvToArrayList(String csv, String cdCorretora, String uploadFile) {
		
		TbodUploadForcavenda uploadForcavenda = null;
		
		if (csv != null) {
			
			String[] splitData = csv.split("\\s*;\\s*");
			
			uploadForcavenda = new TbodUploadForcavenda();
			
			for (int i = 0; i < splitData.length; i++) {
				if (!(splitData[i] == null) || !(splitData[i].length() == 0)) {
					uploadForcavenda.setCdCorretora(Long.valueOf(cdCorretora));				
					uploadForcavenda.setCsvArquivo(uploadFile);
					uploadForcavenda.setDataCriacao(new Date());
					uploadForcavenda.setStatus("PENDENTE");
					uploadForcavenda.setNome(splitData[0].trim());
					uploadForcavenda.setCpf(splitData[1].trim());
					uploadForcavenda.setDataNascimento(splitData[2].trim());
					uploadForcavenda.setCelular(splitData[3].trim());
					uploadForcavenda.setEmail(splitData[4].trim());
					uploadForcavenda.setDepartamento(splitData[5].trim());
					try {
						uploadForcavenda.setCargo(splitData[6].trim());
					} catch (ArrayIndexOutOfBoundsException exception) {
						uploadForcavenda.setCargo(null);
			        }			
				}
			}
		}
		
		return uploadForcavenda;
	}
		
	private File serverFile(MultipartFile uploadFile) throws IOException, FileNotFoundException {
		
		String rootPath = System.getProperty("catalina.home");
        File dir = new File(rootPath + File.separator + "ArquivosCSV");
         
        if (!dir.exists())
            dir.mkdirs();
        File serverFile = new File(dir.getAbsolutePath() + File.separator + uploadFile.getOriginalFilename());
                         
        InputStream in = uploadFile.getInputStream();
        FileOutputStream out = new FileOutputStream(serverFile);
        byte[] b = new byte[1024];
        int len = 0;
        while ((len = in.read(b)) > 0) {
            out.write(b, 0, len);
        }
        out.close();
        in.close();
         
        log.info("##### Server File Location: " + serverFile.getAbsolutePath());
		return serverFile;
	}

	//201810041803 - esert - COR-861:Serviço - Receber / Retornar Planilha
	//201810051600 - esert - COR-861:Serviço - Receber / Retornar Planilha - refactor
	@RequestMapping(value="fileupload/lotedcms", method = RequestMethod.POST)
	public ResponseEntity<FileUploadLoteDCMSResponse> fileuploadLoteDCMS(@RequestBody FileUploadLoteDCMS fileUploadLoteDCMS) throws IOException, EncryptedDocumentException, InvalidFormatException{	
		log.info("fileuploadLoteDCMS - ini");
		FileUploadLoteDCMSResponse fileUploadLoteDCMSResponse = new FileUploadLoteDCMSResponse();
		try {
		
			log.info(fileUploadLoteDCMS.toString());

			fileUploadLoteDCMSResponse = empresaService.processarLoteDCMS(fileUploadLoteDCMS);
			
			log.info("fileuploadLoteDCMS - fim");		      
	        return ResponseEntity.ok(fileUploadLoteDCMSResponse);
	        
		} catch (Exception e) {
			log.info("fileuploadLoteDCMS - erro");		      
			log.error("Exception", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
