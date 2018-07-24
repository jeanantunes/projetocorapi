package br.com.odontoprev.portal.corretor.controller;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.RollbackException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.odontoprev.portal.corretor.dao.VidaDAO;
import br.com.odontoprev.portal.corretor.dto.Beneficiario;
import br.com.odontoprev.portal.corretor.dto.BeneficiarioResponse;
import br.com.odontoprev.portal.corretor.dto.Beneficiarios;
import br.com.odontoprev.portal.corretor.dto.Endereco;
import br.com.odontoprev.portal.corretor.service.BeneficiarioService;
import br.com.odontoprev.portal.corretor.util.ConvertObjectUtil;

@RestController
public class BeneficiarioController {
	
	private static final Log log = LogFactory.getLog(BeneficiarioController.class);
	
	@Autowired
	BeneficiarioService beneficiarioService;

	//201805281830 - esert - informação: por conta da trstativa de erro, verificou-se nesta data que esta rota nao tem chamada nem web nem app
	@RequestMapping(value = "/beneficiario", method = { RequestMethod.POST })
	public BeneficiarioResponse addBeneficiario(@RequestBody List<Beneficiario> beneficiarios) {
		BeneficiarioResponse beneficiarioResponse = new BeneficiarioResponse(0);
		
		log.info(beneficiarios);
		
		try { //201805281830 - esert - incluido try/catch para tratar throws que causara rollback de toda transacao - teste
			beneficiarioResponse = beneficiarioService.add(beneficiarios);
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e); //201805281830 - esert - incluido log
		}
		
		return beneficiarioResponse; 
	}


	//201807241851 - esert - COR-398
	@RequestMapping(value = "/beneficiarios/empresa/{cdEmpresa}/ps/{pageSize}/pn/{pageNum}", method = { RequestMethod.GET })
	public ResponseEntity<Beneficiarios> getBeneficiariosEmpresa2(
			@PathVariable Long cdEmpresa, 
			@PathVariable Long pageSize, 
			@PathVariable Long pageNum) {
		
		return getBeneficiariosEmpresa(cdEmpresa, pageSize, pageNum);
	}
	
	//201807241723 - esert - COR-398
	@RequestMapping(value = "/beneficiarios/empresa/{cdEmpresa}", method = { RequestMethod.GET })
	public ResponseEntity<Beneficiarios> getBeneficiariosEmpresa(
			@PathVariable Long cdEmpresa, 
			@RequestParam("ps") Long pageSize, 
			@RequestParam("pn") Long pageNum) {
		Beneficiarios beneficiarios = new Beneficiarios();
		beneficiarios.setQtdRegistros(13L);
		beneficiarios.setQtdPaginas(4L);
		beneficiarios.setTamPagina(pageSize);
		beneficiarios.setNumPagina(pageNum);
		beneficiarios.setTitulares(new ArrayList<>());		
		for(Long i=1L; i<=pageSize; i++) {
			Long cdVida = ( ( pageNum * pageSize ) - pageSize ) + i;
			
			Beneficiario benefTitular = new Beneficiario();
			
			benefTitular.setCdVida(cdVida);
			benefTitular.setNome("nome beneficiario [".concat(cdVida.toString()).concat("]"));
			
			benefTitular.setEndereco(new Endereco());
			
			benefTitular.setDependentes(new ArrayList<>());
			for(Long j=1L; j<=3; j++) {
				Beneficiario benefDepend = new Beneficiario();
				benefDepend.setCdVida(cdVida * 10L + j);
				benefDepend.setCdTitular(cdVida);
				benefDepend.setNome("nome dependente [".concat(cdVida.toString()).concat(".").concat(j.toString()).concat("]"));
				benefTitular.getDependentes().add(benefDepend);
			}
			
			beneficiarios.getTitulares().add(benefTitular);
		}
		return ResponseEntity.ok(beneficiarios);
	}

	//201807241859 - esert - COR-398
	@RequestMapping(value = "/beneficiario/{cdVida}", method = { RequestMethod.GET })
	public ResponseEntity<Beneficiario> getBeneficiario(@PathVariable Long cdVida) {		
		return ResponseEntity.ok(beneficiarioService.get(cdVida)); 
	}

}
