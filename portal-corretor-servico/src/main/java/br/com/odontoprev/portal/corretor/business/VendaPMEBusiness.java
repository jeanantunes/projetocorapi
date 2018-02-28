package br.com.odontoprev.portal.corretor.business;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.ManagedBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.odontoprev.portal.corretor.dto.Beneficiario;
import br.com.odontoprev.portal.corretor.dto.Empresa;
import br.com.odontoprev.portal.corretor.dto.EmpresaResponse;
import br.com.odontoprev.portal.corretor.dto.Plano;
import br.com.odontoprev.portal.corretor.dto.Venda;
import br.com.odontoprev.portal.corretor.dto.VendaPME;
import br.com.odontoprev.portal.corretor.dto.VendaResponse;

@ManagedBean
public class VendaPMEBusiness {

	private static final Log log = LogFactory.getLog(VendaPMEBusiness.class);
	
	@Autowired
	EmpresaBusiness empresaBusiness;
	
	@Autowired
	VendaPFBusiness vendaPFBusiness;
	
	public VendaResponse salvarVendaPMEComEmpresasPlanosTitularesDependentes(VendaPME vendaPME) {

		log.info("[salvarVendaPMEComEmpresasPlanosTitularesDependentes]");

		Venda venda = new Venda();

		try {		

			for (Empresa empresa : vendaPME.getEmpresas()) {
				
				EmpresaResponse empresaResponse = empresaBusiness.salvarEmpresaEndereco(empresa);

				for (Plano plano : empresa.getPlanos()) {
					
					venda = new Venda();
					venda.setTitulares(new ArrayList<Beneficiario>());
					venda.setCdEmpresa(empresaResponse.getId());
					venda.setCdPlano(plano.getCdPlano());
					venda.setDataVenda(new Date()); //TODO
					venda.setCdStatusVenda(null); //TODO
					venda.setFaturaVencimento(32); //TODO
					
					for (Beneficiario titular : vendaPME.getTitulares()) {
					
						if(
							titular.getCnpj().equals(empresa.getCnpj())
							&&
							titular.getCdPlano() == plano.getCdPlano()
						){
							venda.getTitulares().add(titular);
						}
						
					} //for (Beneficiario titular : titulares)

					VendaResponse vendaResponse = vendaPFBusiness.salvarVendaComTitularesComDependentes(venda);
					
					log.info("salvarVendaPMEComEmpresasPlanosTitularesDependentes :: cadastrada vendaResponse.getId:[" + vendaResponse.getId() + "].");
					
				} //for (Plano plano : empresa.getPlanos())
				
			} //for (Empresa empresa : empresas)
			
		} catch (Exception e) {
			log.error("salvarVendaPMEComEmpresasPlanosTitularesDependentes :: Erro ao cadastrar venda CdVenda:[" + venda.getCdVenda() + "]. Detalhe: [" + e.getMessage() + "]");
			
			String msg = "Erro ao cadastrar venda PME";
			return new VendaResponse(0, msg);
		}

		return new VendaResponse(1, "Venda PME cadastrada CdVenda:["+ 1 +"].");
	}

}
