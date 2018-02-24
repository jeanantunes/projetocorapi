package br.com.odontoprev.portal.corretor.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.dao.EmpresaDAO;
import br.com.odontoprev.portal.corretor.dao.ForcaVendaDAO;
import br.com.odontoprev.portal.corretor.dao.PlanoDAO;
import br.com.odontoprev.portal.corretor.dao.StatusVendaDAO;
import br.com.odontoprev.portal.corretor.dao.VendaDAO;
import br.com.odontoprev.portal.corretor.dto.Venda;
import br.com.odontoprev.portal.corretor.dto.VendaResponse;
import br.com.odontoprev.portal.corretor.model.TbodEmpresa;
import br.com.odontoprev.portal.corretor.model.TbodForcaVenda;
import br.com.odontoprev.portal.corretor.model.TbodPlano;
import br.com.odontoprev.portal.corretor.model.TbodStatusVenda;
import br.com.odontoprev.portal.corretor.model.TbodVenda;
import br.com.odontoprev.portal.corretor.service.VendaPFService;

@Service
public class VendaPFServiceImpl implements VendaPFService {

	private static final Log log = LogFactory.getLog(VendaPFServiceImpl.class);

	@Autowired
	private EmpresaDAO empresaDao;

	@Autowired
	private PlanoDAO planoDao;

	@Autowired
	private ForcaVendaDAO forcaVendaDao;

	@Autowired
	private StatusVendaDAO statusVendaDao;

	@Autowired
	private VendaDAO vendaDao;

	@Override
	public VendaResponse addVenda(Venda venda) {

		log.info("[VendaPFServiceImpl::addVenda]");

		TbodVenda tbVenda = new TbodVenda();

		try {		
			
			
			if(venda != null) {
				if(venda.getCdVenda() != null) {
					tbVenda = vendaDao.findOne(venda.getCdVenda());					
				}
			}

			if(tbVenda == null) {
				throw new Exception("Venda CdVenda:[" + venda.getCdVenda() + "] não existe!");
			}
			
			tbVenda.setCdVenda(venda.getCdVenda());
			
			TbodEmpresa tbodEmpresa = empresaDao.findOne(venda.getCdEmpresa());
			tbVenda.setTbodEmpresa(tbodEmpresa);
			
			TbodPlano tbodPlano = planoDao.findByCdPlano(venda.getCdPlano());
			tbVenda.setTbodPlano(tbodPlano);
			
			TbodForcaVenda tbodForcaVenda = forcaVendaDao.findOne(venda.getCdForcaVenda()); 
			tbVenda.setTbodForcaVenda(tbodForcaVenda);
			
			tbVenda.setDtVenda(venda.getDataVenda());
			
			//TbodVendaVida tbodVendaVida = null;
			//tbVenda.getTbodVendaVida().setCdVendaVida((Long) null);
			
			TbodStatusVenda tbodStatusVenda = statusVendaDao.findOne(venda.getCdStatusVenda());
			tbVenda.setTbodStatusVenda(tbodStatusVenda);
			
			tbVenda.setFaturaVencimento(venda.getFaturaVencimento());
			
			tbVenda = vendaDao.save(tbVenda);
			
//			tbForcaVenda.setCpf(forcaVenda.getCpf());
//			tbForcaVenda.setDataNascimento(DataUtil.dateParse(forcaVenda.getDataNascimento()));
//			tbForcaVenda.setCelular(forcaVenda.getCelular());
//			tbForcaVenda.setEmail(forcaVenda.getEmail());
//			tbForcaVenda.setAtivo(forcaVenda.isAtivo() == true ? "S" : "N");
//			tbForcaVenda.setCargo(forcaVenda.getCargo());
//			tbForcaVenda.setDepartamento(forcaVenda.getDepartamento());
//			TbodStatusForcaVenda statusForcaVenda = new TbodStatusForcaVenda();
//			if (forcaVenda.getCorretora() != null && forcaVenda.getCorretora().getCdCorretora() > 0) {
//				TbodCorretora tbCorretora = corretoraDao.findOne(forcaVenda.getCorretora().getCdCorretora());
//				tbForcaVenda.setTbodCorretora(tbCorretora);
//				statusForcaVenda = statusForcaVendaDao.findOne(StatusForcaVendaEnum.ATIVO.getCodigo());
//			} else {
//				statusForcaVenda = statusForcaVendaDao.findOne(StatusForcaVendaEnum.PENDENTE.getCodigo());
//			}
//			tbForcaVenda.setTbodStatusForcaVenda(statusForcaVenda);
//
//			tbForcaVenda = forcaVendaDao.save(tbForcaVenda);

		} catch (Exception e) {
			log.error("VendaPFServiceImpl :: Erro ao cadastrar venda CdVenda:[" + venda.getCdVenda() + "]. Detalhe: [" + e.getMessage() + "]");
			
			String msg = "Erro ao cadastrar venda ";
			if(venda.getCdVenda() != null) {
				msg += ", CdVenda:["+ venda.getCdVenda() +"]";
			} else {
				msg += ", CdVenda:[null]";
			}
			if(venda.getCdEmpresa() != null) {
				msg += ", CdEmpresa:["+ venda.getCdEmpresa() +"]";
			} else {
				msg += ", CdEmpresa:[null]";
			}
			if(venda.getCdForcaVenda() != null) {
				msg += ", CdForcaVenda:["+ venda.getCdForcaVenda() +"]";
			} else {
				msg += ", CdForcaVenda:[null]";
			}
			if(venda.getCdPlano() != null) {
				msg += ", CdPlano:["+ venda.getCdPlano() +"].";
			} else {
				msg += ", CdPlano:[null].";
			}
			return new VendaResponse(0, msg);
		}

		return new VendaResponse(tbVenda.getCdVenda(), "Venda cadastrada CdVenda:["+ tbVenda.getCdVenda() +"].");
	}

}
