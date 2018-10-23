package br.com.odontoprev.portal.corretor.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.odontoprev.portal.corretor.dao.ArquivoDAO;
import br.com.odontoprev.portal.corretor.dao.PlanoDAO;
import br.com.odontoprev.portal.corretor.dao.PlanoInfoDAO;
import br.com.odontoprev.portal.corretor.dto.Arquivo;
import br.com.odontoprev.portal.corretor.dto.Plano;
import br.com.odontoprev.portal.corretor.dto.PlanoInfo;
import br.com.odontoprev.portal.corretor.dto.PlanoInfos;
import br.com.odontoprev.portal.corretor.model.TbodArquivo;
import br.com.odontoprev.portal.corretor.model.TbodPlano;
import br.com.odontoprev.portal.corretor.model.TbodPlanoInfo;
import br.com.odontoprev.portal.corretor.service.PlanoService;
import br.com.odontoprev.portal.corretor.util.Constantes;

@Service
@Transactional(rollbackFor={Exception.class}) //201806281838 - esert - COR-348
public class PlanoServiceImpl implements PlanoService {
	
	private static final Logger log = LoggerFactory.getLogger(PlanoServiceImpl.class);
	
	@Autowired
	private PlanoDAO planoDAO;

	@Autowired
	private PlanoInfoDAO planoInfoDAO; //201810221659 - esert - COR-932:API - Novo GET /planoinfo

	@Autowired
	private ArquivoDAO arquivoDAO; //201810221721 - esert - COR-932:API - Novo GET /planoinfo

	@Override
	public List<Plano> getPlanos() {
		
		log.info("[PlanoServiceImpl::getPlanos]");
		
		List<TbodPlano> entities = new ArrayList<TbodPlano>();
		
		entities = planoDAO.findAll();
				
		return adaptEntityToDto(entities);
	}

	@Override
	@Transactional(rollbackFor={Exception.class}) //201806281838 - esert - COR-348
	public List<Plano> findPlanosByEmpresa(long cdEmpresa) {
		
		log.info("[PlanoServiceImpl::findPlanosByEmpresa]");
		
		List<Plano> planos = new ArrayList<Plano>();
		
		List<Object[]> objects = planoDAO.planosByEmpresa(cdEmpresa);
		
		for (Object object : objects) {
			Object[] obj = (Object[]) object;
			
			Plano plano = new Plano();
			plano.setCdPlano(new Long(String.valueOf(obj[0])));
			plano.setDescricao(String.valueOf(obj[1]));
			plano.setCdVenda(new Long(String.valueOf(obj[2])));
			
			BigDecimal valor = ((BigDecimal) obj[3]).add((BigDecimal) obj[4]);
			plano.setValor(String.valueOf(valor));
		
			planos.add(plano);
		}
		
		return planos;
	}
	
	private List<Plano> adaptEntityToDto(List<TbodPlano> entities){
		
		List<Plano> planos = new ArrayList<Plano>();
		
		for (TbodPlano entity : entities) {
			 
			Plano plano = new Plano();
			plano.setCdPlano(entity.getCdPlano());
			plano.setDescricao(entity.getTbodTipoPlano().getDescricao());
			plano.setTitulo(entity.getTitulo());
			plano.setValor(entity.getValorMensal().toString());
			plano.setSigla(entity.getCodigo());
			planos.add(plano);
		}
		
		return planos;
		
	}

	/*201810221655 - esert - COR-932:API - Novo GET /planoinfo */
	@Override
	public PlanoInfos getpPlanoInfos() {
		log.info("getpPlanoInfos - ini");
		PlanoInfos planoInfos = new PlanoInfos();
		
//		try {
			List<TbodPlanoInfo> listTbodPlanoInfo = planoInfoDAO.findByAtivo(Constantes.SIM);
			
			if(listTbodPlanoInfo==null || listTbodPlanoInfo.size()==0) {
				log.info("getpPlanoInfos - listTbodPlanoInfo==null && listTbodPlanoInfo.size()==0");
				return null;
			}
			
			if(listTbodPlanoInfo!=null) {
				planoInfos.setPlanoInfos(new ArrayList<PlanoInfo>());
			}
			for(TbodPlanoInfo tbodPlanoInfo : listTbodPlanoInfo) {
				PlanoInfo planoInfo = new PlanoInfo();
				
				planoInfo.setCodigoPlanoInfo(tbodPlanoInfo.getCdPlanoInfo());
				planoInfo.setNomePlanoInfo(tbodPlanoInfo.getNomePlanoInfo());
				planoInfo.setDescricao(tbodPlanoInfo.getDescricao());
				planoInfo.setTipoSegmento(tbodPlanoInfo.getTipoSegmento());
				planoInfo.setAtivo(tbodPlanoInfo.getAtivo());
				
				if(tbodPlanoInfo.getTbodArquivoGeral()!=null) {
					planoInfo.setCodigoArquivoGeral(tbodPlanoInfo.getTbodArquivoGeral().getCodigoArquivo());
				}
				if(tbodPlanoInfo.getTbodArquivoCarencia()!=null) {
					planoInfo.setCodigoArquivoCarencia(tbodPlanoInfo.getTbodArquivoCarencia().getCodigoArquivo());
				}
				if(tbodPlanoInfo.getTbodArquivoIcone()!=null) {
					planoInfo.setCodigoArquivoIcone(tbodPlanoInfo.getTbodArquivoIcone().getCodigoArquivo());
				}
				
				if(planoInfo.getCodigoArquivoIcone()!=null) {
					TbodArquivo tbodArquivo = arquivoDAO.findOne(planoInfo.getCodigoArquivoIcone());
					if(tbodArquivo!=null) {
						Arquivo arquivoIcone = new Arquivo();
						arquivoIcone.setCdArquivo(tbodArquivo.getCodigoArquivo());
						arquivoIcone.setNomeArquivo(tbodArquivo.getNomeArquivo());
						arquivoIcone.setTipoConteudo(tbodArquivo.getTipoConteudo());
						arquivoIcone.setTamanho(tbodArquivo.getTamanhoArquivo());
						arquivoIcone.setDataCriacao(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tbodArquivo.getDataCriacao())); //201810231726
						arquivoIcone.setArquivoBase64(Base64.encodeBase64String(tbodArquivo.getArquivo()));
						planoInfo.setArquivoIcone(arquivoIcone);
					} else {
						log.info("tbodArquivo==null para planoInfo.getCodigoArquivoIcone({})", planoInfo.getCodigoArquivoIcone());
					}
				} else {
					log.info("planoInfo.getCodigoArquivoIcone()==null para tbodPlanoInfo.getCdPlanoInfo():[{}]", tbodPlanoInfo.getCdPlanoInfo());
				}
				
				planoInfos.getPlanoInfos().add(planoInfo);
			}
//		} catch (Exception e) {
//			log.info("getpPlanoInfos - erro");
//			log.error("getpPlanoInfos - erro", e);
//			return null;
//		}
		
		log.info("getpPlanoInfos - fim");
		return planoInfos;
	}

}

