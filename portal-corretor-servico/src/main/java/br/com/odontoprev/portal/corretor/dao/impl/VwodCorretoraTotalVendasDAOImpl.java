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

import br.com.odontoprev.portal.corretor.dao.VwodCorretoraTotalVendasDAO;
import br.com.odontoprev.portal.corretor.model.VwodCorretoraTotalVidas;

//201806081613 - esert - relatorio vendas pme
@Transactional
@Repository
public class VwodCorretoraTotalVendasDAOImpl implements VwodCorretoraTotalVendasDAO {

	private static final Log log = LogFactory.getLog(VwodCorretoraTotalVendasDAOImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<VwodCorretoraTotalVidas> vwodCorretoraTotalVendasByFiltro(
			Date dtVendaInicio, 
			Date dtVendaFim, 
			String cnpjCorretora 
	) {
		String stringQuery = "from VwodCorretoraTotalVidas v where 1=1 ";
		
		if(dtVendaInicio != null) {
			stringQuery += " AND v.dtVenda >= :dtVendaInicio";
		}
		
		if(dtVendaFim != null) {
			stringQuery += " AND v.dtVenda <= :dtVendaFim ";
		}
		
		if(cnpjCorretora != null) {
			stringQuery += " AND v.cnpj_corretora = :cnpjCorretora ";
		}
				
		Query query = entityManager.createQuery(stringQuery, VwodCorretoraTotalVidas.class);
		
		if(dtVendaInicio != null) {
			query.setParameter("dtVendaInicio", dtVendaInicio);			
		}
		
		if(dtVendaFim != null) {
			query.setParameter("dtVendaFim", dtVendaFim);			
		}
		
		if(cnpjCorretora !=null) {			
			query.setParameter("cnpjCorretora", cnpjCorretora);
		}
		
		@SuppressWarnings("unchecked")
		List<VwodCorretoraTotalVidas> vwodCorretoraTotalVidas = query.getResultList();
		
		log.info("vwodCorretoraTotalVidas.size():[" + vwodCorretoraTotalVidas.size() + "]"); //201806081852 - esert
		
		return vwodCorretoraTotalVidas;
	}
}
