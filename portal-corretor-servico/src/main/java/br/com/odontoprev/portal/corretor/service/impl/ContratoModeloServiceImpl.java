package br.com.odontoprev.portal.corretor.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.pdf.codec.Base64;

import br.com.odontoprev.portal.corretor.dao.ContratoModeloDAO;
import br.com.odontoprev.portal.corretor.dto.ContratoModelo;
import br.com.odontoprev.portal.corretor.model.TbodContratoModelo;
import br.com.odontoprev.portal.corretor.service.ContratoModeloService;

//201808231715 - esert - COR-617 - novo servico para nova tabela TBOD_ARQUIVO_CONTRATACAO
@Service
@Transactional(rollbackFor={Exception.class})
public class ContratoModeloServiceImpl implements ContratoModeloService {
	
	private static final Log log = LogFactory.getLog(ContratoModeloServiceImpl.class);
	
	@Autowired
	private ContratoModeloDAO contratoModeloDAO;
		
//	@Value("${server.path.pdfcontratocorretora}") //201809112051 - esert - COR-xxx gerar pdf contrato corretora
//	private String pdfContratoCorretora; //201808311529 - esert - COR-xxx gerar pdf contrato corretora

	@Override
	public ContratoModelo getByCdContratoModelo(Long cdContratoModelo) {		
		log.info("getByCdContratoModelo - ini");		
		ContratoModelo dto = null;
		try {
			TbodContratoModelo entity = contratoModeloDAO.findOne(cdContratoModelo);

			dto = adaptEntityToDto(entity);
		} catch (Exception e) {
			log.error("getByCdContratoModelo - erro", e);
			return null;
		}
		log.info("getByCdContratoModelo - fim");
		return dto;
	}

	@Override
	public ContratoModelo saveArquivo(ContratoModelo dto) {
		log.info("saveArquivo - ini");	
		TbodContratoModelo tbod = null;
		
		if(dto.getCdContratoModelo() != null) {
			tbod = contratoModeloDAO.findOne(dto.getCdContratoModelo());
		}
		
		tbod = adaptDtoToEntity(dto);
		
		tbod = contratoModeloDAO.save(tbod);
		
		dto = adaptEntityToDto(tbod);
		
		log.info("saveArquivo - fim");	
		return dto;
	}

	private TbodContratoModelo adaptDtoToEntity(ContratoModelo dto) {
		log.info("TbodContratoModelo adaptDtoToEntity(ContratoModelo dto) - ini");
		TbodContratoModelo entity = null;
		try {
			if(dto != null) {
				entity = new TbodContratoModelo();
				entity.setCdContratoModelo(dto.getCdContratoModelo());
				try {
					entity.setDtCriacao((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(dto.getDtCriacao()));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					log.error("Erro em parse: new SimpleDateFormat(yyyy-MM-dd HH:mm:ss)).parse(" + dto.getDtCriacao() + "), aplicado (new Date())", e1); //201808271556 - esert
					entity.setDtCriacao(new Date());
				}
				entity.setNomeArquivo(dto.getNomeArquivo());
				entity.setTamanhoArquivo(dto.getTamanhoArquivo());
				entity.setTipoConteudo(dto.getTipoConteudo());
				
				if(dto.getCaminhoCarga()!=null && !dto.getCaminhoCarga().isEmpty()) {
					File file = new File(dto.getCaminhoCarga().concat(dto.getNomeArquivo()));
					
					if(entity.getTamanhoArquivo()==null) {
						entity.setTamanhoArquivo(file.length());
					}
					
					byte[] fileInBytes = new byte[(int)file.length()];
					FileInputStream fis;
					try {
						fis = new FileInputStream(file);
						fis.read(fileInBytes);
						fis.close();
						
						entity.setArquivoBytes(fileInBytes);
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						log.error(e);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						log.error(e);
					}
				} else {
					entity.setArquivoBytes(dto.getArquivoBase64().getBytes());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error("TbodContratoModelo adaptDtoToEntity(ContratoModelo dto) - erro", e);
			return null;
		}
		log.info("TbodContratoModelo adaptDtoToEntity(ContratoModelo dto) - fim");
		return entity;
	}

	private ContratoModelo adaptEntityToDto(TbodContratoModelo entity) {
		log.info("ContratoModelo adaptEntityToDto(TbodContratoModelo entity, boolean isArquivo) - ini");
		ContratoModelo dto = null;
		try {
			if(entity != null) {
				dto = new ContratoModelo();
				
				dto.setCdContratoModelo(entity.getCdContratoModelo());
				
				try {
					//dto.setDataCriacao(entity.getDataCriacao());
					dto.setDtCriacao((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(entity.getDtCriacao())/*.replace(" ", "T").concat("-0300")*/); //201809111132 - esert
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					log.error("Erro formatando data ...format(entity.getDataCriacao())", e);
				}

				dto.setNomeArquivo(entity.getNomeArquivo());
				dto.setTamanhoArquivo(entity.getTamanhoArquivo());
				dto.setTipoConteudo(entity.getTipoConteudo());
				
				dto.setArquivoBase64(Base64.encodeBytes(entity.getArquivoBytes()));
				dto.setArquivoString(new String(entity.getArquivoBytes(), Charset.defaultCharset())); //201809112014
					
				//dto.setTamanhoArquivo( dto.getArquivoBase64()!=null ? (long)dto.getArquivoBase64().length() : -1L );
				//dto.setTamanhoArquivo( dto.getArquivoString()!=null ? (long)dto.getArquivoString().length() : -1L );
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error("ContratoModelo adaptEntityToDto(TbodContratoModelo entity) - erro", e);
			return dto;
		}		
		log.info("ContratoModelo adaptEntityToDto(TbodContratoModelo entity) - fim");
		return dto;
	}

}

