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
	public List<ViewCorSumarioVenda> viewCorSumarioVendasByFiltro(Date dtInicio, Date dtFim, long cdCorretora, long cdForcaVenda, String cpf, String cnpj, Date dtVenda) {
		String squery = "from ViewCorSumarioVenda v where 1=1 ";
		
	/*	if(dtInicio != null && dtFim != null) {
			squery += "v.dtVenda >= :dtInicio AND v.dtVenda < :dtFim ";
		}*/
		
		if(cdCorretora > 0L) {
			squery += " and v.codigoCorretora = :cdCorretora ";
		}
		
		if(cdForcaVenda > 0L) {
			squery += " and v.codigoForcaVenda = :cdForcaVenda ";
		}
		
		if(cpf!= null) {
			squery += " and v.cpf = :cpf ";
		}
		
		if(cnpj != null) {
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
		if(cnpj !=null) {			
			query.setParameter("cnpj", cnpj);
		}
		if(cpf != null) {			
			query.setParameter("cpf", cpf);
		}
		
		if(dtVenda != null) {
			query.setParameter("dtVenda", dtVenda);
		}
		
		@SuppressWarnings("unchecked")
		List<ViewCorSumarioVenda> viewCorSumarioVendas = query.getResultList();
		
		return viewCorSumarioVendas;
	}

}
