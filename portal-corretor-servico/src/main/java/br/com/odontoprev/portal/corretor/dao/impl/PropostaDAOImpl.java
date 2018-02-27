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
		
		String sQuery = "SELECT V.* FROM TBOD_VENDA V, TBOD_FORCA_VENDA F, TBOD_CORRETORA C "
				+ " WHERE V.CD_FORCA_VENDAS = F.CD_FORCA_VENDA "
				+ " AND F.CD_CORRETORA = C.CD_CORRETORA ";
				
		if(dtInicio != null && dtFim != null) {
			sQuery += " AND V.DT_VENDA >= :dtInicio AND V.DT_VENDA < :dtFim ";
		}
		
		if(cdCorretora > 0L) {
			sQuery += " AND C.CD_CORRETORA = :cdCorretora ";
		}
		
		if(cdForcaVenda > 0L) {
			sQuery += " AND F.CD_FORCA_VENDA = :cdForcaVenda ";
		}
		
		sQuery += " AND V.CD_STATUS_VENDA = 1";
		
		Query query = entityManager.createNativeQuery(sQuery, TbodVenda.class);
		
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
