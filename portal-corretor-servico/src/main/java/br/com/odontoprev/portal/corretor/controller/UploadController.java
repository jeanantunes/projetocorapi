package br.com.odontoprev.portal.corretor.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.odontoprev.portal.corretor.model.TbodUpload;
import br.com.odontoprev.portal.corretor.service.UploadService;
import br.com.odontoprev.portal.corretor.util.PropertiesUtils;

@Controller
public class UploadController {
	
	@Autowired
	UploadService uploadService;
	
	@SuppressWarnings("resource")
	@RequestMapping(value="/upload/{cnpj}", method = RequestMethod.POST)
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile uploadFile, @PathVariable String cnpj  ) throws IOException, EncryptedDocumentException, InvalidFormatException{	
		
		SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
		
		DecimalFormat formatter = new DecimalFormat("######");
        String cellCPF= "";
        String cellCelular= "";
        
        //File arquivo = new File("C:\\planilhaUploadOdpv\\" + uploadFile.getOriginalFilename());		
               
        //FileInputStream excelFile = new FileInputStream(new File(arquivo.getAbsoluteFile().toString()));
        
        //String caminho = "//u01//oracle//Middleware//Oracle_Home//user_projects//domains//corretor_venda_odonto";
        
        String pathEmpresa = PropertiesUtils.getProperty(PropertiesUtils.PATH_XLS_EMPRESA);
        String filename = pathEmpresa + uploadFile.getOriginalFilename();
        FileOutputStream fileOut = new FileOutputStream(filename);
        Workbook workbook = new XSSFWorkbook(filename);
		workbook.write(fileOut);
		fileOut.close();
              
        Workbook workb = new XSSFWorkbook(filename);
        Sheet datatypeSheet = workb.getSheetAt(0);
        Iterator<Row> iterator = datatypeSheet.iterator();      
        
        TbodUpload tbodUpload = null;
     
        while (iterator.hasNext()) {
        	
            Row currentRow = iterator.next();
            Iterator<Cell> cellIterator = currentRow.iterator();            
          
            tbodUpload = new TbodUpload();
            tbodUpload.setCnpj(cnpj);
			tbodUpload.setArquivo(uploadFile.getOriginalFilename());
			tbodUpload.setDataCriacao(new Date());
			tbodUpload.setStatus("PENDENTE");
			
            while (cellIterator.hasNext()) {

                Cell currentCell = cellIterator.next(); 
    			
                switch( currentCell.getColumnIndex() )
                {
                    case 0: //nome
                    	tbodUpload.setNome(currentCell.getStringCellValue());                    	
                        break;                    
                    case 1: //cpf
                    	cellCPF = formatter.format(currentCell.getNumericCellValue());
                    	tbodUpload.setCpf(cellCPF);                    	
                        break;                    
                    case 2: //data nascimento
                    	if (currentCell.getDateCellValue() != null)
                        tbodUpload.setDataNascimento(data.format(currentCell.getDateCellValue()));                    	                  	
                        break; 
                    case 3: //celular
                    	cellCelular = formatter.format(currentCell.getNumericCellValue());
                    	tbodUpload.setCelular(cellCelular);                    	
                        break;  
                    case 4: //email
                    	tbodUpload.setEmail(currentCell.getStringCellValue());                    	
                        break;             
                    case 5: //departamento
                    	tbodUpload.setDepartamento(currentCell.getStringCellValue());                    	
                        break;         
                    case 6: //cargo
                    	tbodUpload.setCargo(currentCell.getStringCellValue());                    	
                        break;             
                    default:
                        System.out.println("dados fora colunas permitidas");                       
                }
                
            } 
            
            if (tbodUpload.getNome() != null && tbodUpload.getCpf() != null && tbodUpload.getDataNascimento() != null && 
            		tbodUpload.getCelular() != null && tbodUpload.getEmail() != null && tbodUpload.getDepartamento() != null && tbodUpload.getCargo() != null) {
            	uploadService.addDadosUpload(tbodUpload);  
                tbodUpload = null;
            }            
        } 
      
        return ResponseEntity.ok("Transação realizada com sucesso");
	}

}
