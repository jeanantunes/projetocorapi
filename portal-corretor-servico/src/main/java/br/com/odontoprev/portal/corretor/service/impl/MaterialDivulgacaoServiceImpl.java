package br.com.odontoprev.portal.corretor.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.odontoprev.portal.corretor.dao.MaterialDivulgacaoDAO;
import br.com.odontoprev.portal.corretor.dto.CategoriaMaterialDivulgacao;
import br.com.odontoprev.portal.corretor.dto.MateriaisDivulgacao;
import br.com.odontoprev.portal.corretor.dto.MaterialDivulgacao;
import br.com.odontoprev.portal.corretor.dto.SubCategoriaMaterialDivulgacao;
import br.com.odontoprev.portal.corretor.model.TbodMaterialDivulgacao;
import br.com.odontoprev.portal.corretor.service.MaterialDivulgacaoService;

@Service
@Transactional(rollbackFor={Exception.class}) //201807111650 - esert - COR-405
public class MaterialDivulgacaoServiceImpl implements MaterialDivulgacaoService {
	
	private static final Log log = LogFactory.getLog(MaterialDivulgacaoServiceImpl.class);
	
	@Autowired
	MaterialDivulgacaoDAO materialDivulgacaoDAO;

	@Override
	public MateriaisDivulgacao getMateriaisDivulgacao() {
		
		log.info("getMateriaisDivulgacao - ini");
		MateriaisDivulgacao materiaisDivulgacao = new MateriaisDivulgacao();
		
		List<TbodMaterialDivulgacao> entities = new ArrayList<TbodMaterialDivulgacao>();
		
		entities = materialDivulgacaoDAO.findAll();
						
		materiaisDivulgacao.setCategoriasMaterialDivulgacao(adaptEntityToDto(entities));
		
		log.info("getMateriaisDivulgacao - fim");
		return materiaisDivulgacao;
	}
	
	private List<CategoriaMaterialDivulgacao> adaptEntityToDto(List<TbodMaterialDivulgacao> entities){
		log.info("List<CategoriaMaterialDivulgacao> adaptEntityToDto(List<TbodMaterialDivulgacao> entities) - ini");

		List<CategoriaMaterialDivulgacao> listCategoriaMaterialDivulgacao = new ArrayList<CategoriaMaterialDivulgacao>();
		
		Long catAnt = -1L;
		Long cat = -1L;
		Long subAnt = -1L;
		Long sub = -1L;
		CategoriaMaterialDivulgacao categoriaMaterialDivulgacao = null;
		SubCategoriaMaterialDivulgacao subCategoriaMaterialDivulgacao = null;

		for (TbodMaterialDivulgacao entity : entities) {
			MaterialDivulgacao materialDivulgacao = adaptEntityToDto(entity);

			cat = entity.getCodigoCategoria();
			sub = entity.getCodigoSubCategoria();
			
			if(cat != catAnt) {
				categoriaMaterialDivulgacao = new CategoriaMaterialDivulgacao();
				categoriaMaterialDivulgacao.setSubCategoriasMaterialDivulgacao(new ArrayList<SubCategoriaMaterialDivulgacao>());
	//			categoriaMaterialDivulgacao.getSubCategoriasMaterialDivulgacao().add(subCategoriaMaterialDivulgacao);
				categoriaMaterialDivulgacao.setNome("nome categoria " + materialDivulgacao.getCodigoCategoria());
				listCategoriaMaterialDivulgacao.add(categoriaMaterialDivulgacao);
				catAnt = cat; 
			}
			
			if(sub != subAnt) {
				subCategoriaMaterialDivulgacao = new SubCategoriaMaterialDivulgacao();
				subCategoriaMaterialDivulgacao.setMateriaisDivulgacao(new ArrayList<MaterialDivulgacao>());
				subCategoriaMaterialDivulgacao.setNome("nome subCategoria " + materialDivulgacao.getCodigoSubCategoria());
				categoriaMaterialDivulgacao.getSubCategoriasMaterialDivulgacao().add(subCategoriaMaterialDivulgacao);
				subAnt = sub; 
			}

			subCategoriaMaterialDivulgacao.getMateriaisDivulgacao().add(materialDivulgacao);
		}
		
		log.info("List<CategoriaMaterialDivulgacao> adaptEntityToDto(List<TbodMaterialDivulgacao> entities) - fim");
		return listCategoriaMaterialDivulgacao;		
	}

	private MaterialDivulgacao adaptEntityToDto(TbodMaterialDivulgacao entity) {
		log.info("MaterialDivulgacao adaptEntityToDto(TbodMaterialDivulgacao entity) - ini");

		MaterialDivulgacao materialDivulgacao = null;
		if(entity != null) {
			materialDivulgacao = new MaterialDivulgacao();
			materialDivulgacao.setCodigoMaterialDivulgacao(entity.getCodigoMaterialDivulgacao());
			materialDivulgacao.setCodigoCategoria(entity.getCodigoCategoria());
			materialDivulgacao.setCodigoSubCategoria(entity.getCodigoSubCategoria());
			materialDivulgacao.setNome(entity.getNome());
			materialDivulgacao.setDescricao(entity.getDescricao());
			materialDivulgacao.setContentType(entity.getTipoConteudo());
//			materialDivulgacao.setThumbnail(entity.getThumbnail());
//			materialDivulgacao.setArquivo(entity.getArquivo());
			materialDivulgacao.setAtivo(entity.getAtivo());		
		}
		
		log.info("MaterialDivulgacao adaptEntityToDto(TbodMaterialDivulgacao entity) - fim");
		return materialDivulgacao;
	}
}

