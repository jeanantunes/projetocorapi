package br.com.odontoprev.portal.corretor.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.dao.CorretoraDAO;
import br.com.odontoprev.portal.corretor.dao.ForcaVendaDAO;
import br.com.odontoprev.portal.corretor.dao.StatusForcaVendaDAO;
import br.com.odontoprev.portal.corretor.dto.ForcaVenda;
import br.com.odontoprev.portal.corretor.dto.ForcaVendaResponse;
import br.com.odontoprev.portal.corretor.enums.StatusForcaVendaEnum;
import br.com.odontoprev.portal.corretor.model.TbodCorretora;
import br.com.odontoprev.portal.corretor.model.TbodForcaVenda;
import br.com.odontoprev.portal.corretor.model.TbodStatusForcaVenda;
import br.com.odontoprev.portal.corretor.service.ForcaVendaService;
import br.com.odontoprev.portal.corretor.util.DataUtil;

@Service
public class ForcaVendaServiceImpl implements ForcaVendaService {

	private static final Log log = LogFactory.getLog(ForcaVendaServiceImpl.class);

	@Autowired
	private CorretoraDAO corretoraDao;

	@Autowired
	private StatusForcaVendaDAO statusForcaVendaDao;

	@Autowired
	private ForcaVendaDAO forcaVendaDao;

	@Override
	public ForcaVendaResponse addForcaVenda(ForcaVenda forcaVenda) {

		log.info("[ForcaVendaServiceImpl::addForcaVenda]");

		TbodForcaVenda tbForcaVenda = new TbodForcaVenda();

		try {		
			
			List<TbodForcaVenda> forcas = forcaVendaDao.findByCpf(forcaVenda.getCpf());
			
			if(forcas != null && !forcas.isEmpty()) {
				throw new Exception("CPF " + forcaVenda.getCpf() + " já existe!");
			}
			
			tbForcaVenda.setNome(forcaVenda.getNome());
			tbForcaVenda.setCpf(forcaVenda.getCpf());
			tbForcaVenda.setDataNascimento(DataUtil.dateParse(forcaVenda.getDataNascimento()));
			tbForcaVenda.setCelular(forcaVenda.getCelular());
			tbForcaVenda.setEmail(forcaVenda.getEmail());
			tbForcaVenda.setAtivo(forcaVenda.isAtivo() == true ? "S" : "N");
			tbForcaVenda.setCargo(forcaVenda.getCargo());
			tbForcaVenda.setDepartamento(forcaVenda.getDepartamento());
			TbodStatusForcaVenda statusForcaVenda = new TbodStatusForcaVenda();
			if (forcaVenda.getCorretora() != null && forcaVenda.getCorretora().getCdCorretora() > 0) {
				TbodCorretora tbCorretora = corretoraDao.findOne(forcaVenda.getCorretora().getCdCorretora());
				tbForcaVenda.setTbodCorretora(tbCorretora);
				statusForcaVenda = statusForcaVendaDao.findOne(StatusForcaVendaEnum.ATIVO.getCodigo());
			} else {
				statusForcaVenda = statusForcaVendaDao.findOne(StatusForcaVendaEnum.PENDENTE.getCodigo());
			}
			tbForcaVenda.setTbodStatusForcaVenda(statusForcaVenda);

			tbForcaVenda = forcaVendaDao.save(tbForcaVenda);

		} catch (Exception e) {
			log.error("ForcaVendaServiceImpl :: Erro ao cadastrar forcaVenda. Detalhe: [" + e.getMessage() + "]");
			return new ForcaVendaResponse(0, "Erro ao cadastrar forcaVenda.");
		}

		return new ForcaVendaResponse(tbForcaVenda.getCdForcaVenda(), "ForcaVenda cadastrada.");
	}

}
