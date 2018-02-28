package br.com.odontoprev.portal.corretor.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.odontoprev.portal.corretor.dao.ViewCorSumarioVendaDAO;
import br.com.odontoprev.portal.corretor.model.ViewCorSumarioVenda;

@Transactional
@Repository
public class ViewCorSumarioVendaDAOImpl implements ViewCorSumarioVendaDAO {

 //   private static final Log LOGGER = LogFactory.getLog(PropostaDAOImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<ViewCorSumarioVenda> viewCorSumarioVendasByFiltro(Date dtInicio, Date dtFim, long cdCorretora, long cdForcaVenda, long cpf, long cnpj) {
		String squery = "SELECT v FROM ViewCorSumarioVenda v WHERE  ";
		
	/*	if(dtInicio != null && dtFim != null) {
			squery += "v.dtVenda >= :dtInicio AND v.dtVenda < :dtFim ";
		}*/
		
		if(cdCorretora > 0L) {
			squery += " v.codigoCorretora = :cdCorretora ";
		}
		
		if(cdForcaVenda > 0L) {
			squery += " and v.codigoForcaVenda = :cdForcaVenda ";
		}
		
		if(cpf > 0L) {
			squery += " v.cpf = :cpf ";
		}
		
		if(cnpj > 0L) {
			squery += " and v.cnpj = :cnpj ";
		}
		
		
		Query query = entityManager.createQuery(squery, ViewCorSumarioVenda.class);
		
	/*	if(dtInicio != null && dtFim != null) {
			query.setParameter("dtInicio", dtInicio);			
			query.setParameter("dtFim", dtFim);
		}*/
		if(cdCorretora > 0L) {			
			query.setParameter("cdCorretora", cdCorretora);
		}
		if(cdForcaVenda > 0L) {			
			query.setParameter("cdForcaVenda", cdForcaVenda);
		}
		if(cnpj > 0L) {			
			query.setParameter("cnpj", cnpj);
		}
		if(cpf > 0L) {			
			query.setParameter("cpf", cpf);
		}
		
		@SuppressWarnings("unchecked")
		List<ViewCorSumarioVenda> viewCorSumarioVendas = query.getResultList();
		
		return viewCorSumarioVendas;
	}

}
