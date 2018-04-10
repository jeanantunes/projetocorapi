package br.com.odontoprev.portal.corretor.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.dto.ForcaDadosTrocaSenha;
import br.com.odontoprev.portal.corretor.dto.ForcaVenda;
import br.com.odontoprev.portal.corretor.service.ForcaDadosTrocaSenhaService;
import br.com.odontoprev.portal.corretor.service.ForcaVendaService;

@Service
public class ForcaDadosTrocaSenhaServiceImpl implements ForcaDadosTrocaSenhaService {

	private static final Log log = LogFactory.getLog(ForcaDadosTrocaSenhaServiceImpl.class);
	
	@Autowired
	ForcaVendaService forcaVendaService;
	
	@Override
	public ForcaDadosTrocaSenha buscarDadosForcaVendaPorCPF(String cpf) {
		
		log.info("### buscar Dados Forca Venda Por CPF ###");
		
		ForcaDadosTrocaSenha forcaDadosTrocaSenha = new ForcaDadosTrocaSenha();
		
		try {
			
			ForcaVenda forcaVenda = forcaVendaService.findForcaVendaByCpf(cpf);
			
			if(forcaVenda != null) {
				forcaDadosTrocaSenha.setCdForcaVenda(forcaVenda.getCdForcaVenda());
				forcaDadosTrocaSenha.setNome(forcaVenda.getNome());
				forcaDadosTrocaSenha.setEmail(forcaVenda.getEmail());
				forcaDadosTrocaSenha.setCelular(forcaVenda.getCelular());
			}
			
		} catch (Exception e) {
			log.error(e);
			log.error("Erro ao buscar dados forca venda por CPF. Detalhe: [" + e.getMessage() + "]");
		}
		
		return forcaDadosTrocaSenha;
	}
	
	

	
}
