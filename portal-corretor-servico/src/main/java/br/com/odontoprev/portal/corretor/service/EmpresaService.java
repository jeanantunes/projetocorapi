package br.com.odontoprev.portal.corretor.service;



import br.com.odontoprev.portal.corretor.dto.Empresa;
import br.com.odontoprev.portal.corretor.dto.EmpresaDcms;
import br.com.odontoprev.portal.corretor.dto.EmpresaResponse;

public interface EmpresaService {
	
	public EmpresaResponse add(Empresa empresa);

	public EmpresaResponse updateEmpresa(EmpresaDcms empresaDcms);

}
