package br.com.odontoprev.portal.corretor.service;

import java.text.ParseException;

import org.springframework.http.ResponseEntity;

import br.com.odontoprev.portal.corretor.dto.CnpjDados;
import br.com.odontoprev.portal.corretor.dto.CnpjDadosAceite;
import br.com.odontoprev.portal.corretor.dto.Empresa;
import br.com.odontoprev.portal.corretor.dto.EmpresaDcms;
import br.com.odontoprev.portal.corretor.dto.EmpresaEmailAceite;
import br.com.odontoprev.portal.corretor.dto.EmpresaResponse;

public interface EmpresaService {

	public EmpresaResponse add(Empresa empresa);

	public EmpresaResponse updateEmpresa(EmpresaDcms empresaDcms);
	
	public CnpjDados findDadosEmpresaByCnpj(String cnpj) throws ParseException; 

	public ResponseEntity<EmpresaDcms> sendEmailBoasVindasPME(Long cdEmpresa);

	public CnpjDadosAceite findDadosEmpresaAceiteByCnpj(String cnpj) throws ParseException; //201805111131 - esert - COR-172 - Serviço - Consultar dados empresa PME

	public EmpresaResponse updateEmpresaEmailAceite(EmpresaEmailAceite empresaEmail); //201805111544 - esert - COR-171 - Serviço - Atualizar email cadastrado empresa
}
