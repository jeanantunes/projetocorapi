package br.com.odontoprev.portal.corretor.service;

import java.text.ParseException;
import java.util.List;

import br.com.odontoprev.portal.corretor.dto.*;
import org.springframework.http.ResponseEntity;

public interface EmpresaService {

	public EmpresaResponse add(Empresa empresa);

	public EmpresaResponse updateEmpresa(EmpresaDcms empresaDcms);
	
	public CnpjDados findDadosEmpresaByCnpj(String cnpj) throws ParseException; 

	public ResponseEntity<EmpresaDcms> sendMailBoasVindasPME(Long cdEmpresa);

	public CnpjDadosAceite findDadosEmpresaAceiteByCnpj(String cnpj) throws ParseException; //201805111131 - esert - COR-172 - Serviço - Consultar dados empresa PME

	public EmpresaResponse updateEmpresaEmailAceite(EmpresaEmailAceite empresaEmail); //201805111544 - esert - COR-171 - Serviço - Atualizar email cadastrado empresa

	public Empresa findByCdEmpresa(Long cdEmpresa);

	public EmpresaArquivoResponse gerarArquivoEmpresa(EmpresaArquivo cdEmpresas);
}
