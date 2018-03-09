package br.com.odontoprev.portal.corretor.business;

import javax.annotation.ManagedBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.odontoprev.portal.corretor.dao.ForcaVendaDAO;
import br.com.odontoprev.portal.corretor.dto.Corretora;
import br.com.odontoprev.portal.corretor.model.TbodCorretora;
import br.com.odontoprev.portal.corretor.model.TbodForcaVenda;

@ManagedBean
public class CorretoraBusiness {
	
	private static final Log log = LogFactory.getLog(CorretoraBusiness.class);
	
	@Autowired
	ForcaVendaDAO forcaVendaDao;
	
	public Corretora buscarCorretoraPorForcaVenda(Long cdForcaVenda) {
		
		log.info("[buscarCorretoraPorForcaVenda]");
		
		Corretora corretora = new Corretora();
		
		try {
			TbodForcaVenda tbForcaVenda = forcaVendaDao.findOne(cdForcaVenda);
			
			if(tbForcaVenda != null && tbForcaVenda.getTbodCorretora() != null) {
				TbodCorretora tbCorretora = tbForcaVenda.getTbodCorretora();
				corretora.setCnpj(tbCorretora.getCnpj());
				corretora.setRazaoSocial(tbCorretora.getNome());
			}
		} catch (Exception e) {
			log.error("Erro buscarCorretoraPorForcaVenda. Detalhe: [" + e.getMessage() + "]");
		}
		
		return corretora;
	}

}
