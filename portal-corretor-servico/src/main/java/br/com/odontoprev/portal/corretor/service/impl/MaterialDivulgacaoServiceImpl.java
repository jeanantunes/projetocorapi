package br.com.odontoprev.portal.corretor.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.odontoprev.portal.corretor.dao.MaterialDivulgacaoCategoriaDAO;
import br.com.odontoprev.portal.corretor.dao.MaterialDivulgacaoDAO;
import br.com.odontoprev.portal.corretor.dao.MaterialDivulgacaoSubCategoriaDAO;
import br.com.odontoprev.portal.corretor.dto.MateriaisDivulgacao;
import br.com.odontoprev.portal.corretor.dto.MaterialDivulgacao;
import br.com.odontoprev.portal.corretor.dto.MaterialDivulgacaoCategoria;
import br.com.odontoprev.portal.corretor.dto.MaterialDivulgacaoSubCategoria;
import br.com.odontoprev.portal.corretor.model.TbodMaterialDivulgacao;
import br.com.odontoprev.portal.corretor.model.TbodMaterialDivulgacaoCategoria;
import br.com.odontoprev.portal.corretor.model.TbodMaterialDivulgacaoSubCategoria;
import br.com.odontoprev.portal.corretor.service.MaterialDivulgacaoService;
import br.com.odontoprev.portal.corretor.util.Constantes;

@Service
@Transactional(rollbackFor={Exception.class}) //201807111650 - esert - COR-405
public class MaterialDivulgacaoServiceImpl implements MaterialDivulgacaoService {
	
	private static final Log log = LogFactory.getLog(MaterialDivulgacaoServiceImpl.class);
	
	@Autowired
	MaterialDivulgacaoDAO materialDivulgacaoDAO;
	
	@Autowired
	MaterialDivulgacaoCategoriaDAO materialDivulgacaoCategoriaDAO;
	
	@Autowired
	MaterialDivulgacaoSubCategoriaDAO materialDivulgacaoSubCategoriaDAO;
	
	@Override
	public MateriaisDivulgacao getMateriaisDivulgacao(String tipoInterface) { //201807111650 - esert - COR-405
		log.info("getMateriaisDivulgacao - ini");
		log.info("tipoInterface:[" + tipoInterface + "]");
		MateriaisDivulgacao materiaisDivulgacao = new MateriaisDivulgacao();
		
		List<TbodMaterialDivulgacao> entities = (List<TbodMaterialDivulgacao>)materialDivulgacaoDAO.findAllAtivo();
						
		materiaisDivulgacao.setCategoriasMaterialDivulgacao(adaptEntityToDto(entities, tipoInterface));
		
		log.info("getMateriaisDivulgacao - fim");
		return materiaisDivulgacao;
	}
	
	private List<MaterialDivulgacaoCategoria> adaptEntityToDto(List<TbodMaterialDivulgacao> entities, String tipoInterface){
		log.info("List<CategoriaMaterialDivulgacao> adaptEntityToDto(List<TbodMaterialDivulgacao> entities) - ini");

		List<MaterialDivulgacaoCategoria> listCategoriaMaterialDivulgacao = new ArrayList<MaterialDivulgacaoCategoria>();
		
		Long catAnt = -1L;
		Long cat = -1L;
		Long subAnt = -1L;
		Long sub = -1L;
		MaterialDivulgacaoCategoria materialDivulgacaoCategoria = null;
		MaterialDivulgacaoSubCategoria materialDivulgacaoSubCategoria = null;

		for (TbodMaterialDivulgacao entity : entities) {
			MaterialDivulgacao materialDivulgacao = adaptEntityToDto(entity, true, false); //201807161100 thumbnail = true, arquivo=false

			cat = entity.getCodigoCategoria();
			sub = entity.getCodigoSubCategoria();
			
			if(cat != catAnt) {
				materialDivulgacaoCategoria = new MaterialDivulgacaoCategoria();
				materialDivulgacaoCategoria.setSubCategoriasMaterialDivulgacao(new ArrayList<MaterialDivulgacaoSubCategoria>());
				TbodMaterialDivulgacaoCategoria tbodMaterialDivulgacaoCategoria = materialDivulgacaoCategoriaDAO.findOne(entity.getCodigoCategoria());
				if(tbodMaterialDivulgacaoCategoria!=null) {
					if(!tbodMaterialDivulgacaoCategoria.getAtivo().equals(Constantes.ATIVO)) {
						continue;
					}
					materialDivulgacaoCategoria.setCodigoCategoria(tbodMaterialDivulgacaoCategoria.getCodigoMaterialDivulgacaoCategoria());
					materialDivulgacaoCategoria.setNome(tbodMaterialDivulgacaoCategoria.getNome());
					materialDivulgacaoCategoria.setDescricao(tbodMaterialDivulgacaoCategoria.getDescricao());
				}
				listCategoriaMaterialDivulgacao.add(materialDivulgacaoCategoria);
				catAnt = cat; 
			}
			
			if(sub != subAnt) {
				materialDivulgacaoSubCategoria = new MaterialDivulgacaoSubCategoria();
				materialDivulgacaoSubCategoria.setMateriaisDivulgacao(new ArrayList<MaterialDivulgacao>());
				TbodMaterialDivulgacaoSubCategoria tbodMaterialDivulgacaoSubCategoria = materialDivulgacaoSubCategoriaDAO.findOne(entity.getCodigoSubCategoria());
				if(tbodMaterialDivulgacaoSubCategoria!=null) {
					if(!tbodMaterialDivulgacaoSubCategoria.getAtivo().equals(Constantes.ATIVO)) {
						continue;
					}
					if(tipoInterface.equals(Constantes.TIPO_INTERFACE_APP)) {						
						if(!tbodMaterialDivulgacaoSubCategoria.getApp().equals(Constantes.SIM)) {
							continue;
						}
					} else if(tipoInterface.equals(Constantes.TIPO_INTERFACE_WEB)) {
						if(!tbodMaterialDivulgacaoSubCategoria.getWeb().equals(Constantes.SIM)) {
							continue;
						}
					} else {
						continue; //nem app nem web						
					}
					
					materialDivulgacaoSubCategoria.setCodigoSubCategoria(tbodMaterialDivulgacaoSubCategoria.getCodigoMaterialDivulgacaoSubCategoria());
					materialDivulgacaoSubCategoria.setNome(tbodMaterialDivulgacaoSubCategoria.getNome());
					materialDivulgacaoSubCategoria.setDescricao(tbodMaterialDivulgacaoSubCategoria.getDescricao());
					materialDivulgacaoSubCategoria.setAtivo(tbodMaterialDivulgacaoSubCategoria.getAtivo());
					materialDivulgacaoSubCategoria.setApp(tbodMaterialDivulgacaoSubCategoria.getApp());
					materialDivulgacaoSubCategoria.setWeb(tbodMaterialDivulgacaoSubCategoria.getWeb());
				}
				
				materialDivulgacaoCategoria.getSubCategoriasMaterialDivulgacao().add(materialDivulgacaoSubCategoria);
				subAnt = sub; 
			}

			materialDivulgacaoSubCategoria.getMateriaisDivulgacao().add(materialDivulgacao);
			log.info("materialDivulgacao:[".concat(materialDivulgacao.toString()).concat("]"));
		}
		
		log.info("List<CategoriaMaterialDivulgacao> adaptEntityToDto(List<TbodMaterialDivulgacao> entities) - fim");
		return listCategoriaMaterialDivulgacao;		
	}

	private MaterialDivulgacao adaptEntityToDto(TbodMaterialDivulgacao entity, boolean isThumbnail, boolean isArquivo) {
		log.info("MaterialDivulgacao adaptEntityToDto(TbodMaterialDivulgacao entity) - ini");
		MaterialDivulgacao materialDivulgacao = null;
		if(entity != null) {
			materialDivulgacao = new MaterialDivulgacao();
			
			materialDivulgacao.setCodigoMaterialDivulgacao(entity.getCodigoMaterialDivulgacao());
			materialDivulgacao.setCodigoCategoria(entity.getCodigoCategoria());
			materialDivulgacao.setCodigoSubCategoria(entity.getCodigoSubCategoria());
			
			materialDivulgacao.setNome(entity.getNome());
			materialDivulgacao.setDescricao(entity.getDescricao());
			
			materialDivulgacao.setTipoConteudo(entity.getTipoConteudo());
			
			materialDivulgacao.setTamanhoThumbnail( entity.getThumbnail()!=null ? entity.getThumbnail().length : -1 );
			if(isThumbnail) {
				materialDivulgacao.setThumbnail(Base64.encodeBase64String(entity.getThumbnail())); //201807131230
				materialDivulgacao.setTamanhoThumbnail( materialDivulgacao.getThumbnail()!=null ? materialDivulgacao.getThumbnail().length() : -1 );
			}
			
			materialDivulgacao.setTamanhoArquivo( entity.getArquivo()!=null ? entity.getArquivo().length : -1 );
			if(isArquivo) {
				materialDivulgacao.setArquivo(Base64.encodeBase64String(entity.getArquivo())); //201807131230
				materialDivulgacao.setTamanhoArquivo( materialDivulgacao.getArquivo()!=null ? materialDivulgacao.getArquivo().length() : -1 );
			}
			
			materialDivulgacao.setAtivo(entity.getAtivo());		
		}		
		log.info("MaterialDivulgacao adaptEntityToDto(TbodMaterialDivulgacao entity) - fim");
		return materialDivulgacao;
	}
	
	@Override
	public MaterialDivulgacao getMaterialDivulgacao(Long codigoMaterialDivulgacao, boolean isArquivo) {		
		log.info("getMaterialDivulgacao - ini");		
		
		TbodMaterialDivulgacao entity = materialDivulgacaoDAO.findOne(codigoMaterialDivulgacao);
						
		MaterialDivulgacao materialDivulgacao = null;
		if(isArquivo) {
			materialDivulgacao = adaptEntityToDto(entity, false, true);
		} else {
			materialDivulgacao = adaptEntityToDto(entity, true, false);			
		}
		
		log.info("getMaterialDivulgacao - fim");
		return materialDivulgacao;
	}
	
	@Override
	public MaterialDivulgacao save(MaterialDivulgacao materialDivulgacao, boolean isThumbnail, boolean isArquivo) {
		log.info("save - ini");	
		TbodMaterialDivulgacao tbodMaterialDivulgacao = null;
		
		if(materialDivulgacao.getCodigoMaterialDivulgacao() != null) { //201807161605 - esert
			tbodMaterialDivulgacao = materialDivulgacaoDAO.findOne(materialDivulgacao.getCodigoMaterialDivulgacao());
		}
		
		if(tbodMaterialDivulgacao==null) {
			tbodMaterialDivulgacao = new TbodMaterialDivulgacao(); //se nao existe deve criar
		}
		
		//tbodMaterialDivulgacao.setCodigoMaterialDivulgacao(materialDivulgacao.getCodigoMaterialDivulgacao());
		
		if(materialDivulgacao.getCodigoCategoria()!=null) {
			tbodMaterialDivulgacao.setCodigoCategoria(materialDivulgacao.getCodigoCategoria());
		} else {
			if(tbodMaterialDivulgacao.getCodigoCategoria()==null) { //201807181220 - esert - cat 1 default
				tbodMaterialDivulgacao.setCodigoCategoria(1L); //201807161605 - esert - cat 1 default
			}
		}
		if(materialDivulgacao.getCodigoSubCategoria()!=null) {
			tbodMaterialDivulgacao.setCodigoSubCategoria(materialDivulgacao.getCodigoSubCategoria());
		} else {
			if(tbodMaterialDivulgacao.getCodigoSubCategoria()==null) { //201807181220 - esert - cat 1 default
				tbodMaterialDivulgacao.setCodigoSubCategoria(1L); //201807161605 - esert - subcat 1 default
			}
		}
		tbodMaterialDivulgacao.setNome(materialDivulgacao.getNome());
		tbodMaterialDivulgacao.setDescricao(materialDivulgacao.getDescricao());
		tbodMaterialDivulgacao.setTipoConteudo(materialDivulgacao.getTipoConteudo());
		tbodMaterialDivulgacao.setAtivo(materialDivulgacao.getAtivo());
		if(tbodMaterialDivulgacao.getAtivo() == null || tbodMaterialDivulgacao.getAtivo().isEmpty()) {
			tbodMaterialDivulgacao.setAtivo(Constantes.ATIVO); //se nao existe define com ATIVO='S'
		}
		
		if(materialDivulgacao.getCaminhoCarga()!=null && !materialDivulgacao.getCaminhoCarga().isEmpty()) {
			File imagePath = new File(materialDivulgacao.getCaminhoCarga().concat(materialDivulgacao.getNome()));
			byte[] imageInBytes = new byte[(int)imagePath.length()];
			FileInputStream fis;
			try {
				fis = new FileInputStream(imagePath);
				fis.read(imageInBytes);
				fis.close();
				
				if(isThumbnail) {
					tbodMaterialDivulgacao.setThumbnail(imageInBytes);
				}
				
				if(isArquivo) {
					tbodMaterialDivulgacao.setArquivo(imageInBytes);
				}
				
				tbodMaterialDivulgacao = materialDivulgacaoDAO.save(tbodMaterialDivulgacao);
				
				materialDivulgacao = adaptEntityToDto(tbodMaterialDivulgacao, false, false);
	
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
			
				if(isThumbnail) {
					byte[] imageInBytes = Base64.decodeBase64(materialDivulgacao.getThumbnail());
					tbodMaterialDivulgacao.setThumbnail(imageInBytes);
				}
				
				if(isArquivo) {
					byte[] imageInBytes = Base64.decodeBase64(materialDivulgacao.getArquivo());
					tbodMaterialDivulgacao.setArquivo(imageInBytes);
				}
				
				tbodMaterialDivulgacao = materialDivulgacaoDAO.save(tbodMaterialDivulgacao);
				
				materialDivulgacao = adaptEntityToDto(tbodMaterialDivulgacao, false, false);
	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		log.info("save - fim");	
		return materialDivulgacao;
	}

	@Override
	public MaterialDivulgacao saveArquivoThumbnail(MaterialDivulgacao materialDivulgacao) {
		log.info("saveArquivoThumbnail - ini");	
		TbodMaterialDivulgacao tbodMaterialDivulgacao = null;
		
		if(materialDivulgacao.getCodigoMaterialDivulgacao() != null) { //201807161605 - esert
			tbodMaterialDivulgacao = materialDivulgacaoDAO.findOne(materialDivulgacao.getCodigoMaterialDivulgacao());
		}
		
		if(tbodMaterialDivulgacao==null) {
			tbodMaterialDivulgacao = new TbodMaterialDivulgacao(); //se nao existe deve criar
		}
		
		//tbodMaterialDivulgacao.setCodigoMaterialDivulgacao(materialDivulgacao.getCodigoMaterialDivulgacao());
		
		if(materialDivulgacao.getCodigoCategoria()!=null) {
			tbodMaterialDivulgacao.setCodigoCategoria(materialDivulgacao.getCodigoCategoria());
		} else {
			if(tbodMaterialDivulgacao.getCodigoCategoria()==null) { //201807181220 - esert - cat 1 default
				tbodMaterialDivulgacao.setCodigoCategoria(1L); //201807161605 - esert - cat 1 default
			}
		}
		if(materialDivulgacao.getCodigoSubCategoria()!=null) {
			tbodMaterialDivulgacao.setCodigoSubCategoria(materialDivulgacao.getCodigoSubCategoria());
		} else {
			if(tbodMaterialDivulgacao.getCodigoSubCategoria()==null) { //201807181220 - esert - cat 1 default
				tbodMaterialDivulgacao.setCodigoSubCategoria(1L); //201807161605 - esert - subcat 1 default
			}
		}
		tbodMaterialDivulgacao.setNome(materialDivulgacao.getNome());
		tbodMaterialDivulgacao.setDescricao(materialDivulgacao.getDescricao());
		tbodMaterialDivulgacao.setTipoConteudo(materialDivulgacao.getTipoConteudo());
		tbodMaterialDivulgacao.setAtivo(materialDivulgacao.getAtivo());
		if(tbodMaterialDivulgacao.getAtivo() == null || tbodMaterialDivulgacao.getAtivo().isEmpty()) {
			tbodMaterialDivulgacao.setAtivo(Constantes.ATIVO); //se nao existe define com ATIVO='S'
		}
		
		if(materialDivulgacao.getCaminhoCarga()!=null && !materialDivulgacao.getCaminhoCarga().isEmpty()) {
			try {
								
				if(materialDivulgacao.getThumbnail()!=null && !materialDivulgacao.getThumbnail().isEmpty()) {
					File imagePathThumbnail = new File(materialDivulgacao.getCaminhoCarga().concat(materialDivulgacao.getThumbnail()));
					byte[] imageInBytesThumbnail = new byte[(int)imagePathThumbnail.length()];
					FileInputStream fisThumbnail;
					fisThumbnail = new FileInputStream(imagePathThumbnail);
					fisThumbnail.read(imageInBytesThumbnail);
					fisThumbnail.close();
					tbodMaterialDivulgacao.setThumbnail(imageInBytesThumbnail);
				}
				
				if(materialDivulgacao.getArquivo()!=null && !materialDivulgacao.getArquivo().isEmpty()) {
					File imagePathArquivo = new File(materialDivulgacao.getCaminhoCarga().concat(materialDivulgacao.getArquivo()));
					byte[] imageInBytesArquivo = new byte[(int)imagePathArquivo.length()];
					FileInputStream fisArquivo;
					fisArquivo = new FileInputStream(imagePathArquivo);
					fisArquivo.read(imageInBytesArquivo);
					fisArquivo.close();
					tbodMaterialDivulgacao.setArquivo(imageInBytesArquivo);
				}
				
				tbodMaterialDivulgacao = materialDivulgacaoDAO.save(tbodMaterialDivulgacao);
				
				materialDivulgacao = adaptEntityToDto(tbodMaterialDivulgacao, false, false);
	
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		log.info("saveArquivoThumbnail - fim");	
		return materialDivulgacao;
	}

}

