package br.com.odontoprev.portal.corretor.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.dao.PlanoDAO;
import br.com.odontoprev.portal.corretor.dto.Plano;
import br.com.odontoprev.portal.corretor.model.TbodPlano;
import br.com.odontoprev.portal.corretor.service.PlanoService;

@Service
public class PlanoServiceImpl implements PlanoService {
	
	private static final Log log = LogFactory.getLog(PlanoServiceImpl.class);
	
	@Autowired
	PlanoDAO planoDAO;

	@Override
	public List<Plano> getPlanos() {
		
		log.info("[PlanoServiceImpl::getPlanos]");
		
		List<TbodPlano> entities = new ArrayList<TbodPlano>();
		
		entities = planoDAO.findAll();
				
		return adaptEntityToDto(entities);
	}

	@Override
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

}

