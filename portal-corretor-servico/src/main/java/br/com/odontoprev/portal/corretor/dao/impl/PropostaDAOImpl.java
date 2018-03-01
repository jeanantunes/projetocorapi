package br.com.odontoprev.portal.corretor.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.odontoprev.portal.corretor.dao.PropostaDAO;
import br.com.odontoprev.portal.corretor.model.TbodVenda;

@Transactional
@Repository
public class PropostaDAOImpl implements PropostaDAO {

	private static final Log log = LogFactory.getLog(PropostaDAOImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<TbodVenda> propostasByFiltro(Date dtInicio, Date dtFim, long cdCorretora, long cdForcaVenda) {
		
		log.info("[PropostaDAOImpl::propostasByFiltro]");
		
		String squery = "SELECT v FROM TbodVenda v WHERE 1=1 ";
				
		if(dtInicio != null && dtFim != null) {
			squery += " and v.dtVenda >= :dtInicio AND v.dtVenda < :dtFim ";
		}
		
		if(cdCorretora > 0L) {
			squery += " and v.tbodForcaVenda.tbodCorretora.cdCorretora = :cdCorretora ";
		}
		
		if(cdForcaVenda > 0L) {
			squery += " and v.tbodForcaVenda.cdForcaVenda = :cdForcaVenda ";
		}
		
		squery += " and v.tbodStatusVenda.cdStatusVenda = 1";
		
		Query query = entityManager.createQuery(squery, TbodVenda.class);
		
		if(dtInicio != null && dtFim != null) {
			query.setParameter("dtInicio", dtInicio);			
			query.setParameter("dtFim", dtFim);
		}
		if(cdCorretora > 0L) {			
			query.setParameter("cdCorretora", cdCorretora);
		}
		if(cdForcaVenda > 0L) {			
			query.setParameter("cdForcaVenda", cdForcaVenda);
		}
		
		@SuppressWarnings("unchecked")
		List<TbodVenda> tbodVendas = query.getResultList();
		
		return tbodVendas;
	}

}
