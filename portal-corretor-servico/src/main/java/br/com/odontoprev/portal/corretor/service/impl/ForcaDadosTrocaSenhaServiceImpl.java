package br.com.odontoprev.portal.corretor.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.dto.Corretora;
import br.com.odontoprev.portal.corretor.dto.ForcaDadosTrocaSenha;
import br.com.odontoprev.portal.corretor.dto.ForcaVenda;
import br.com.odontoprev.portal.corretor.service.CorretoraService;
import br.com.odontoprev.portal.corretor.service.ForcaDadosTrocaSenhaService;
import br.com.odontoprev.portal.corretor.service.ForcaVendaService;
import br.com.odontoprev.portal.corretor.util.Constantes;

@Service
public class ForcaDadosTrocaSenhaServiceImpl implements ForcaDadosTrocaSenhaService {

	private static final Log log = LogFactory.getLog(ForcaDadosTrocaSenhaServiceImpl.class);
	
	@Autowired
	ForcaVendaService forcaVendaService;
	
	@Autowired
	CorretoraService corretoraService;
	
	@Override
	public ForcaDadosTrocaSenha buscarDadosForcaVendaPorCPF(String cpfCnpj) {
		
		log.info("buscarDadosForcaVendaPorCPF - ini");
		
		ForcaDadosTrocaSenha forcaDadosTrocaSenha = new ForcaDadosTrocaSenha();
		
		try {
			if(cpfCnpj == null) {
				throw new Exception("Cpf ou Cnpj esta nulo.");
				
			} else if(cpfCnpj.trim().isEmpty()) {
				throw new Exception("Cpf ou Cnpj esta vazio.");
				
			} else if(cpfCnpj.length() == Constantes.TAMANHO_CPF) {			
				ForcaVenda forcaVenda = forcaVendaService.findForcaVendaByCpf(cpfCnpj);
				
				if(forcaVenda != null) {
					forcaDadosTrocaSenha.setCdForcaVenda(forcaVenda.getCdForcaVenda());
					forcaDadosTrocaSenha.setNome(forcaVenda.getNome());
					forcaDadosTrocaSenha.setEmail(forcaVenda.getEmail());
					forcaDadosTrocaSenha.setCelular(forcaVenda.getCelular());
				}
				
			} else if(cpfCnpj.length() == Constantes.TAMANHO_CNPJ) { //201807172152 - esert - COR-318
				Corretora corretora = corretoraService.buscaCorretoraPorCnpj(cpfCnpj); //201807172152 - esert - COR-318
				
				if(corretora != null) {
					forcaDadosTrocaSenha.setCdCorretora(corretora.getCdCorretora());
					forcaDadosTrocaSenha.setNome(corretora.getRazaoSocial());
					forcaDadosTrocaSenha.setEmail(corretora.getEmail());
					forcaDadosTrocaSenha.setCelular(corretora.getCelular());
				}
				
			} else {
				throw new Exception("Cpf ou Cnpj tamanho invalido [" + cpfCnpj.length() + "].");						

			}
			
		} catch (Exception e) {
			log.info("buscarDadosForcaVendaPorCPF - erro");
			log.error(e);
			log.error("Erro ao buscar dados forca venda por CPF/CNPJ:[" + cpfCnpj + "] Detalhe:[" + e.getMessage() + "]");
		}
		
		log.info("buscarDadosForcaVendaPorCPF - fim");
		return forcaDadosTrocaSenha;
	}

}
