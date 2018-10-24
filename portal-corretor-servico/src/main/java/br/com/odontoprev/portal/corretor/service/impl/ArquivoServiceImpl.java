package br.com.odontoprev.portal.corretor.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.odontoprev.portal.corretor.dao.ArquivoDAO;
import br.com.odontoprev.portal.corretor.dto.Arquivo;
import br.com.odontoprev.portal.corretor.model.TbodArquivo;
import br.com.odontoprev.portal.corretor.service.ArquivoService;

//201810231800 - esert - COR-723:API - Novo GET/ARQUIVO/(ID)
@Service
@Transactional(rollbackFor={Exception.class})
public class ArquivoServiceImpl implements ArquivoService {
	
	private static final Logger log = LoggerFactory.getLogger(ArquivoServiceImpl.class);
	
	@Autowired
	private ArquivoDAO arquivoDAO;
	
	@Override
	public Arquivo getByCdArquivo(Long cdArquivo) {		
		log.info("getByCdArquivo - ini");		
		
		TbodArquivo entity = arquivoDAO.findOne(cdArquivo);
						
		Arquivo arquivo = adaptEntityToDto(entity);
		
		log.info("getByCdArquivo - fim");
		return arquivo;

	}
	
	@Override
	public Arquivo saveArquivo(Arquivo dto) {
		log.info("saveArquivo - ini");	
		TbodArquivo tbod = null;
		
		if(dto.getCdArquivo() != null) {
			tbod = arquivoDAO.findOne(dto.getCdArquivo());
		}
		
		tbod = adaptDtoToEntity(dto);
		
		tbod = arquivoDAO.save(tbod);
		
		dto = adaptEntityToDto(tbod);
		
		log.info("saveArquivo - fim");	
		return dto;
	}

	private TbodArquivo adaptDtoToEntity(Arquivo dto) {
		log.info("TbodArquivoContratacao adaptDtoToEntity(ArquivoContratacao dto, boolean isArquivo) - ini");
		TbodArquivo entity = null;
		try {
			if(dto != null) {
				entity = new TbodArquivo();
				entity.setCodigoArquivo(dto.getCdArquivo());
				try {
					entity.setDataCriacao((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(dto.getDataCriacao())); //201808271556 - esert
				} catch (Exception e1) {
					log.error("Erro em parse: new SimpleDateFormat(yyyy-MM-dd HH:mm:ss)).parse(" + dto.getDataCriacao() + "), aplicado (new Date())", e1); //201808271556 - esert
					entity.setDataCriacao(new Date());
				}
				entity.setNomeArquivo(dto.getNomeArquivo());
				entity.setTamanhoArquivo(dto.getTamanho());
				entity.setTipoConteudo(dto.getTipoConteudo());
				
				if(dto.getCaminhoArquivo()!=null && !dto.getCaminhoArquivo().isEmpty()) {
					File file = new File(dto.getCaminhoArquivo().concat(dto.getNomeArquivo()));
					
					// h t t p s : //stackoverflow .com /questions /51438 /getting-a-files-mime-type-in-java
					String mimeType = URLConnection.guessContentTypeFromName(file.getName()); //201810241246 - esert - determina ContentType/TipoConteudo automaticamente 
					log.info("mimeType:[{}] = URLConnection.guessContentTypeFromName(file.getName({}))"
							,mimeType
							,file.getName()
							);
					if(entity.getTipoConteudo()==null || entity.getTipoConteudo().trim().length()==0) {
						entity.setTipoConteudo(mimeType); //
					}

					if(entity.getTamanho()==null) {
						entity.setTamanhoArquivo(file.length());
					}
					
					byte[] pdfInBytes = new byte[(int)file.length()];
					FileInputStream fis;
					try {
						fis = new FileInputStream(file);
						fis.read(pdfInBytes);
						fis.close();
						
						entity.setArquivo(pdfInBytes);
						
					} catch (FileNotFoundException e) {
						log.error("adaptDtoToEntity", e);
					} catch (IOException e) {
						log.error("adaptDtoToEntity", e);
					}
				} else {
					entity.setArquivo(Base64.decodeBase64(dto.getArquivoBase64())); //201808231947 - esert
				}
			}
		} catch (Exception e) {
			log.error("TbodArquivoContratacao adaptDtoToEntity(ArquivoContratacao dto, boolean isArquivo) - erro", e);
			return null;
		}
		log.info("TbodArquivoContratacao adaptDtoToEntity(ArquivoContratacao dto, boolean isArquivo) - fim");
		return entity;
	}

	private Arquivo adaptEntityToDto(TbodArquivo entity) {
		log.info("Arquivo adaptEntityToDto(TbodArquivo entity) - ini");
		Arquivo dto = null;
		try {
			if(entity != null) {
				dto = new Arquivo();
				
				dto.setCdArquivo(entity.getCodigoArquivo());
				
				try {
					//dto.setDataCriacao(entity.getDataCriacao());
					dto.setDataCriacao((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(entity.getDataCriacao()).replace(" ", "T").concat("-0300")); //201808271556 - esert
				} catch (Exception e) {
					log.error("Erro formatando data ...format(entity.getDataCriacao())", e);
				}

				dto.setNomeArquivo(entity.getNomeArquivo());
				dto.setTamanho(entity.getTamanho());
				dto.setTipoConteudo(entity.getTipoConteudo());
				
				dto.setTamanho( entity.getArquivo()!=null ? (long)entity.getArquivo().length : -1L );

				dto.setArquivoBase64(Base64.encodeBase64String(entity.getArquivo())); //201807131230
				//dto.setTamanho( dto.getArquivoBase64()!=null ? (long)dto.getArquivoBase64().length() : -1L ); //201810241251 - esert - informar o tamanho real em bytes e nao o tamanho do string base64

			}
		} catch (Exception e) {
			log.error("Arquivo adaptEntityToDto(TbodArquivo entity) - erro", e);
			return dto;
		}		
		log.info("Arquivo adaptEntityToDto(TbodArquivo entity) - fim");
		return dto;
	}
}

