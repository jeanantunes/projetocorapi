//package br.com.odontoprev.portal.corretor.dao.impl;
//
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import br.com.odontoprev.portal.corretor.dao.PlanoDAO;
//import br.com.odontoprev.portal.corretor.model.TbodPlano;
//
//@Transactional
//@Repository
//public class PlanoDAOImpl implements PlanoDAO {
//	
//	private static final Log log = LogFactory.getLog(PlanoDAOImpl.class);
//	
//	@PersistenceContext
//	private EntityManager entityManager;
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<TbodPlano> planos() {
//		
//		log.info("[PlanoDAOImpl::planos]");
//		
//		String hql = "FROM TbodPlano as plano";
//		return (List<TbodPlano>) entityManager.createQuery(hql).getResultList();
//	}
//	
//	@Override
//	public List<TbodPlano> planosByEmpresa(long cdEmpresa) {
//		
//		log.info("[PlanoDAOImpl::planosByEmpresa]");
//		
//		String sQuery = "select plano.CD_PLANO, plano.NOME_PLANO " + 
//				"from TBOD_PLANO plano " + 
//				"inner join TBOD_VENDA venda " + 
//				"on plano.cd_plano = venda.cd_plano " + 
//				"and venda.cd_empresa = :cdEmpresa ";
//		
//		Query query = entityManager.createNativeQuery(sQuery, TbodPlano.class);
//		query.setParameter("cdEmpresa", cdEmpresa);
//		
//		@SuppressWarnings("unchecked")
//		List<TbodPlano> tbPlano = query.getResultList();
//		
//		return tbPlano;
//	}
//
//}
