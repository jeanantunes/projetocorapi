//package br.com.odontoprev.portal.corretor.service.impl;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import br.com.odontoprev.portal.corretor.dao.EmpresaDAO;
//import br.com.odontoprev.portal.corretor.dao.ForcaVendaDAO;
//import br.com.odontoprev.portal.corretor.dao.PlanoDAO;
//import br.com.odontoprev.portal.corretor.dao.VendaDAO;
//import br.com.odontoprev.portal.corretor.dto.Venda;
//import br.com.odontoprev.portal.corretor.dto.VendaResponse;
//import br.com.odontoprev.portal.corretor.model.TbodEmpresa;
//import br.com.odontoprev.portal.corretor.model.TbodForcaVenda;
//import br.com.odontoprev.portal.corretor.model.TbodPlano;
//import br.com.odontoprev.portal.corretor.model.TbodVenda;
//import br.com.odontoprev.portal.corretor.service.VendaService;
//
//@Service
//public class VendaServiceImpl implements VendaService {
//	
//	private static final Log log = LogFactory.getLog(VendaServiceImpl.class);
//	
//	@Autowired
//	VendaDAO vendaDao;
//	
//	@Autowired
//	EmpresaDAO empresaDao;
//	
//	@Autowired
//	PlanoDAO planoDao;
//	
//	@Autowired
//	ForcaVendaDAO forcaVendaDao;
//	
//
//	@Override
//	public VendaResponse addVenda(Venda venda) {
//
//		log.info("[VendaServiceImpl::addVenda]");
//		
//		TbodVenda tbVenda = new TbodVenda();
//		
//		try {
//
//			tbVenda.setFaturaVencimento(venda.getFaturaVencimento());
//			tbVenda.setDtVenda(venda.getDataVenda());
//			TbodEmpresa tbEmpresa;
//			if(venda.getCdEmpresa() > 0) {
//				tbEmpresa = empresaDao.findOne(venda.getCdEmpresa());
//				tbVenda.setTbodEmpresa(tbEmpresa);
//			}
//			TbodPlano tbPlano;
//			if(venda.getCdPlano() > 0) {
//				tbPlano = planoDao.findOne(venda.getCdPlano());
//				tbVenda.setTbodPlano(tbPlano);
//			}
//			TbodForcaVenda tbForcaVenda;
//			if(venda.getCdForcaVenda() > 0) {
//				tbForcaVenda = forcaVendaDao.findOne(venda.getCdForcaVenda());
//			}
//			
//			
//			tbVenda = vendaDao.save(tbVenda);	
//			
//		} catch (Exception e) {
//			log.info("VendaServiceImpl :: Erro ao cadastrar Venda. Detalhe: [" + e.getMessage() + "]");
//			return new VendaResponse(0, "Erro ao cadastrar Venda.");
//		}
//
//		return new VendaResponse(tbVenda.getCdVenda(), "Venda finalizada.");
//		
//	}
//
//}
