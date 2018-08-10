package br.com.odontoprev.portal.corretor.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.dto.ForcaVenda;
import br.com.odontoprev.portal.corretor.dto.ForcaVendaResponse;
import br.com.odontoprev.portal.corretor.service.ForcaVendaService;

@RestController
public class ForcaVendaController {

	private static final Log log = LogFactory.getLog(ForcaVendaController.class);

	@Autowired
	ForcaVendaService forcaVendaService;

	@RequestMapping(value = "/forcavenda", method = { RequestMethod.POST })
	public ForcaVendaResponse addForcaVenda(@RequestBody ForcaVenda forcaVenda) {

		log.info(forcaVenda);

		return forcaVendaService.addForcaVenda(forcaVenda);
	}

	@RequestMapping(value = "/forcavenda", method = { RequestMethod.PUT })
	public ResponseEntity<ForcaVendaResponse> updateForcaVenda(@RequestBody ForcaVenda forcaVenda) {
		log.info("updateForcaVenda - ini");
		log.info(forcaVenda);		
		try {
			ForcaVendaResponse forcaVendaResponse = forcaVendaService.updateForcaVenda(forcaVenda);
			//TODO: tratar retorno erro com APP@Yago //201808101839 
			if(forcaVendaResponse==null) {
				log.info("updateForcaVenda - fim");		
				return ResponseEntity.noContent().build();				
			}
			log.info("updateForcaVenda - fim");		
			return ResponseEntity.ok(forcaVendaResponse);
		} catch (final Exception e) {
			log.error("ERROR: ",e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}				
	}
	
	@RequestMapping(value = "/forcavenda/login", method = { RequestMethod.PUT })
	public ForcaVendaResponse updateForcaVendaLogin(@RequestBody ForcaVenda forcaVenda) {

		log.info(forcaVenda);

		return forcaVendaService.updateForcaVendaLogin(forcaVenda);
	}
	
	@RequestMapping(value = "/forcavenda/status-ativo", method = { RequestMethod.PUT })
	public ForcaVendaResponse updateForcaVendaStatus(@RequestBody ForcaVenda forcaVenda) {

		log.info(forcaVenda);

		return forcaVendaService.updateForcaVendaStatusByCpf(forcaVenda);
	}

	@RequestMapping(value = "/forcavenda/{cpf}", method = { RequestMethod.GET })
	public ForcaVenda findForcaVendaByCpf(@PathVariable String cpf) {

		log.info(cpf);

		return forcaVendaService.findForcaVendaByCpf(cpf);
	}

	@RequestMapping(value = "/forcavenda/{cdForcaVenda}/corretora/{cnpj}", method = { RequestMethod.GET })
	public ForcaVendaResponse findAssocForcaVendaCorretora(@PathVariable Long cdForcaVenda, @PathVariable String cnpj) {

		log.info("cdForcaVenda [" + cdForcaVenda + "] + cnpj [" + cnpj + "]");

		return forcaVendaService.findAssocForcaVendaCorretora(cdForcaVenda, cnpj);
	}
	
	@RequestMapping(value = "/forcavendastatus/{cdStatusForcaVenda}/corretora/{cdCorretora}", method = { RequestMethod.GET })
	public List<ForcaVenda> findForcaVendasByCdCorretoraStatusAprovacao(@PathVariable Long cdStatusForcaVenda, @PathVariable Long cdCorretora) {

		log.info("cdStatusForcaVenda [" + cdStatusForcaVenda + "], cdCorretora [" + cdCorretora + "]");

		return forcaVendaService.findForcaVendasByCdStatusForcaCdCorretora(cdStatusForcaVenda, cdCorretora);
	}

	@RequestMapping(value = "/forcavenda/corretora/{cdCorretora}", method = { RequestMethod.GET })
	public List<ForcaVenda> findForcaVendasByCdCorretora(@PathVariable Long cdCorretora) {

		log.info("cdCorretora [" + cdCorretora + "]");

		//return forcaVendaService.findForcaVendasByCdStatusForcaCdCorretora(cdCorretora);
		return forcaVendaService.findForcaVendasByForcaCdCorretora(cdCorretora);
	}
	
	@RequestMapping(value = "/forcavenda/status-excluido", method = { RequestMethod.PUT })
	public ForcaVendaResponse updateForcaVendaStatusExcluir(@RequestBody ForcaVenda forcaVenda) throws Exception {
		log.info("[updateForcaVendaStatusExcluir]");
		return forcaVendaService.updateForcaVendaStatusByCpf(forcaVenda,"EXCLUIR");
	}

	@RequestMapping(value = "/forcavenda/status-reprovado", method = { RequestMethod.PUT })
	public ForcaVendaResponse updateForcaVendaStatusReprovar(@RequestBody ForcaVenda forcaVenda) throws Exception {
		log.info("[updateForcaVendaStatusReprovar]");
		return forcaVendaService.updateForcaVendaStatusByCpf(forcaVenda, "REPROVAR");
	}
}
