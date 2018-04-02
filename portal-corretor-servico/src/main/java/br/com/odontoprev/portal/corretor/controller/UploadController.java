package br.com.odontoprev.portal.corretor.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.odontoprev.portal.corretor.model.TbodUpload;
import br.com.odontoprev.portal.corretor.service.UploadService;

@Controller
public class UploadController {
	
	private static final String FILE_NAME = "/planilhaUploadOdpv/uploadArqForca.xlsx";
	
	@Autowired
	UploadService uploadService;
	
	@SuppressWarnings("resource")
	@RequestMapping(value="/upload", method = RequestMethod.POST)
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile uploadFile ) throws IOException, EncryptedDocumentException, InvalidFormatException{	
		
		DecimalFormat formatter = new DecimalFormat("######");
        String cellCPF= "";
        String cellCelular= "";
        
		FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
              
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet datatypeSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = datatypeSheet.iterator();        

        while (iterator.hasNext()) {

            Row currentRow = iterator.next();
            Iterator<Cell> cellIterator = currentRow.iterator();
            
            TbodUpload tbodUpload = new TbodUpload();

            while (cellIterator.hasNext()) {

                Cell currentCell = cellIterator.next(); 
                
                tbodUpload.setCnpj("03136742000137");
    			tbodUpload.setArquivo(uploadFile.getOriginalFilename());
    			tbodUpload.setDataCriacao(new Date());
    			tbodUpload.setStatus("PENDENTE");
    			
                switch( currentCell.getColumnIndex() )
                {
                    case 0: //nome
                    	tbodUpload.setNome(currentCell.getStringCellValue());
                    	//System.out.print(currentCell.getStringCellValue() + " | ");
                        break;                    
                    case 1: //cpf
                    	cellCPF = formatter.format(currentCell.getNumericCellValue());
                    	tbodUpload.setCpf(cellCPF);
                    	//System.out.println(cellCPF + " | ");
                        break;                    
                    case 2: //data nascimento
                    	tbodUpload.setDataNascimento("30/10/1975");
                    	//System.out.print(currentCell.getDateCellValue() + " | ");
                        break; 
                    case 3: //celular
                    	cellCelular = formatter.format(currentCell.getNumericCellValue());
                    	tbodUpload.setCelular(cellCelular);
                    	//System.out.println(cellCelular + " | ");
                        break;  
                    case 4: //email
                    	tbodUpload.setEmail(currentCell.getStringCellValue());
                    	//System.out.print(currentCell.getStringCellValue() + " | ");
                        break;             
                    case 5: //departamento
                    	tbodUpload.setDepartamento(currentCell.getStringCellValue());
                    	//System.out.print(currentCell.getStringCellValue() + " | ");
                        break;         
                    case 6: //cargo
                    	tbodUpload.setCargo(currentCell.getStringCellValue());
                    	//System.out.print(currentCell.getStringCellValue() + " | ");
                        break;             
                    default:
                        System.out.println("dados fora colunas permitidas");
                }
            }            
            uploadService.addDadosUpload(tbodUpload);            
        }
		return null;
	}

}
