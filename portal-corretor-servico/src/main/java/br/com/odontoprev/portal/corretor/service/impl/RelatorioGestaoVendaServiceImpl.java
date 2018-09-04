package br.com.odontoprev.portal.corretor.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.dao.ForcaVendaDAO;
import br.com.odontoprev.portal.corretor.dto.Corretora;
import br.com.odontoprev.portal.corretor.dto.RelatorioGestaoVenda;
import br.com.odontoprev.portal.corretor.service.CorretoraService;
import br.com.odontoprev.portal.corretor.service.ForcaVendaService;
import br.com.odontoprev.portal.corretor.service.RelatorioGestaoVendaService;

@Service
public class RelatorioGestaoVendaServiceImpl implements RelatorioGestaoVendaService {

	private static final Log log = LogFactory.getLog(RelatorioGestaoVendaServiceImpl.class);
	
	@Autowired
	CorretoraService corretoraService;

	@Autowired
	ForcaVendaService forcaVendaService;
	
	@Autowired
	ForcaVendaDAO forcaVendaDAO;
	
	@Override
	public List<RelatorioGestaoVenda> findVendasByCorretora(String cnpj) {

		log.info("[RelatorioGestaoVendaServiceImpl::findVendasByCorretora]");
		
		List<RelatorioGestaoVenda> gestaoVendas = new ArrayList<RelatorioGestaoVenda>();
		
		Corretora corretora;

		try {
			corretora = corretoraService.buscaCorretoraPorCnpj(cnpj);

			List<Object[]> objects = new ArrayList<Object[]>();

			List<Object[]> forcaVenda = forcaVendaDAO.findForcaVendaAtiva(corretora.getCdCorretora());

			for (Object object : forcaVenda) {
				forcaVendaDAO.vendasByForcaVenda(Long.valueOf(object.toString())).stream().forEach(dados -> {
					objects.add(dados);
				});
			}
			;

			for (Object object : objects) {

				Object[] obj = (Object[]) object;

				RelatorioGestaoVenda rel = new RelatorioGestaoVenda();

				/*data venda*/
				rel.setDataVenda(String.valueOf(obj[0]));
				/*nome*/
				rel.setNome(String.valueOf(obj[1]));
				/*cpf*/
				rel.setCpf(String.valueOf(obj[2]));
				/*data Nascimento*/
				rel.setDataNascimento(String.valueOf(obj[3]));
				/*nome mae*/
				rel.setNomeMae(String.valueOf(obj[4]));
				/*celular*/
				rel.setCelular(String.valueOf(obj[5]));
				/*email*/
				rel.setEmail(String.valueOf(obj[6]));
				/*lograduro*/
				rel.setLogradouro(String.valueOf(obj[7]));
				/*cep*/
				rel.setCep(String.valueOf(obj[8]));
				/*cidade*/
				rel.setCidade(String.valueOf(obj[9]));
				/*complemento*/
				rel.setComplemento(String.valueOf(obj[10]));
				/*bairro*/
				rel.setBairro(String.valueOf(obj[11]));
				/*uf*/
				rel.setUf(String.valueOf(obj[12]));
				/*numero*/
				rel.setNumero(String.valueOf(obj[13]));
				/*nome plano*/
				rel.setNomePlano(String.valueOf(obj[14]));
				/*valor mensal*/
				rel.setValorMensal(String.valueOf(obj[15]));
				/*valor anual*/
				rel.setValorAnual(String.valueOf(obj[16]));
				/*tipo Plano*/
				rel.setTipoPlano(String.valueOf(obj[17]));
				/*forca Nome*/
				rel.setForcaNome(String.valueOf(obj[18])); //201805301905 - esert/jack - COR-8 - ajuste

				gestaoVendas.add(rel);
			}

			return gestaoVendas;
		}catch (Exception e){

			log.error("[RelatorioGestaoVendaServiceImpl:: Detalhe: [" + e.getMessage() + "]");
			return null;

		}
	}

}
