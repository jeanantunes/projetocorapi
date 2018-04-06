package br.com.odontoprev.portal.corretor.business;

import static br.com.odontoprev.portal.corretor.util.Constantes.NAO;
import static br.com.odontoprev.portal.corretor.util.Constantes.SIM;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.ManagedBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.odontoprev.portal.corretor.dao.EmpresaContatoDAO;
import br.com.odontoprev.portal.corretor.dao.EmpresaDAO;
import br.com.odontoprev.portal.corretor.dao.EnderecoDAO;
import br.com.odontoprev.portal.corretor.dao.PlanoDAO;
import br.com.odontoprev.portal.corretor.dao.TipoEnderecoDAO;
import br.com.odontoprev.portal.corretor.dao.VendaDAO;
import br.com.odontoprev.portal.corretor.dto.ContatoEmpresa;
import br.com.odontoprev.portal.corretor.dto.Empresa;
import br.com.odontoprev.portal.corretor.dto.EmpresaResponse;
import br.com.odontoprev.portal.corretor.dto.Endereco;
import br.com.odontoprev.portal.corretor.dto.Plano;
import br.com.odontoprev.portal.corretor.model.TbodEmpresa;
import br.com.odontoprev.portal.corretor.model.TbodEmpresaContato;
import br.com.odontoprev.portal.corretor.model.TbodEndereco;
import br.com.odontoprev.portal.corretor.model.TbodPlano;
import br.com.odontoprev.portal.corretor.model.TbodTipoEndereco;
import br.com.odontoprev.portal.corretor.model.TbodVenda;
import br.com.odontoprev.portal.corretor.util.XlsEmpresa;

@ManagedBean
public class EmpresaBusiness {

	private static final Log log = LogFactory.getLog(EmpresaBusiness.class);
	
	@Autowired
	TipoEnderecoDAO tipoEnderecoDao;
	
	@Autowired
	EnderecoDAO enderecoDao;
	
	@Autowired
	EmpresaDAO empresaDao;
	
	@Autowired
	PlanoDAO planoDao;
	
	@Autowired
	VendaDAO vendaDao;
	
	@Autowired
	EmpresaContatoDAO empresaContatoDAO;

	public EmpresaResponse salvarEmpresaEnderecoVenda(Empresa empresa) {
		TbodEmpresa tbEmpresa = new TbodEmpresa();

		try {
			TbodEndereco tbEndereco = new TbodEndereco();
			Endereco endereco = empresa.getEnderecoEmpresa();

			tbEndereco.setLogradouro(endereco.getLogradouro());
			tbEndereco.setBairro(endereco.getBairro());
			tbEndereco.setCep(endereco.getCep());
			tbEndereco.setCidade(endereco.getCidade());
			tbEndereco.setNumero(endereco.getNumero());
			tbEndereco.setUf(endereco.getEstado());
			tbEndereco.setComplemento(endereco.getComplemento());

			if (endereco.getTipoEndereco() != null) {
				TbodTipoEndereco tbTipoEndereco = tipoEnderecoDao.findOne(endereco.getTipoEndereco());
				tbEndereco.setTbodTipoEndereco(tbTipoEndereco);
			}

			tbEndereco = enderecoDao.save(tbEndereco);

			tbEmpresa.setCnpj(empresa.getCnpj());
			tbEmpresa.setIncEstadual(empresa.getIncEstadual());
			tbEmpresa.setRamoAtividade(empresa.getRamoAtividade());
			tbEmpresa.setRazaoSocial(empresa.getRazaoSocial());
			tbEmpresa.setNomeFantasia(empresa.getNomeFantasia());
			tbEmpresa.setRepresentanteLegal(empresa.getRepresentanteLegal());
			tbEmpresa.setContatoEmpresa(empresa.isContatoEmpresa() == true ? SIM : NAO);
			tbEmpresa.setTelefone(empresa.getTelefone());
			tbEmpresa.setCelular(empresa.getCelular());
			tbEmpresa.setEmail(empresa.getEmail());
			tbEmpresa.setCnae(empresa.getCnae());
			tbEmpresa.setTbodEndereco(tbEndereco);
			tbEmpresa = empresaDao.save(tbEmpresa);

			if (empresa.getPlanos() != null && !empresa.getPlanos().isEmpty()) {
				for (Plano plano : empresa.getPlanos()) {
					TbodPlano tbPlano = new TbodPlano();
					tbPlano = planoDao.findByCdPlano(plano.getCdPlano());

					TbodVenda tbVenda = new TbodVenda();
					tbVenda.setDtVenda(new Date());
					tbVenda.setFaturaVencimento(empresa.getVencimentoFatura());
					tbVenda.setTbodEmpresa(tbEmpresa);
					tbVenda.setTbodPlano(tbPlano);
					vendaDao.save(tbVenda);
				}
			}

			XlsEmpresa xlsEmpresa = new XlsEmpresa();
			xlsEmpresa.GerarEmpresaXLS(tbEmpresa, empresa);

		} catch (Exception e) {
			log.error("EmpresaServiceImpl :: Erro ao cadastrar empresa. Detalhe: [" + e.getMessage() + "]");
			return new EmpresaResponse(0, "Erro ao cadastrar empresa. Favor, entre em contato com o suporte.");
		}

		return new EmpresaResponse(tbEmpresa.getCdEmpresa(), "Empresa cadastrada.");
	}

	public EmpresaResponse salvarEmpresaEndereco(Empresa empresa) {
		
		TbodEmpresa tbEmpresa = new TbodEmpresa();
	
		try {
			TbodEndereco tbEndereco = new TbodEndereco();
			Endereco endereco = empresa.getEnderecoEmpresa();
	
			tbEndereco.setLogradouro(endereco.getLogradouro());
			tbEndereco.setBairro(endereco.getBairro());
			tbEndereco.setCep(endereco.getCep());
			tbEndereco.setCidade(endereco.getCidade());
			tbEndereco.setNumero(endereco.getNumero());
			tbEndereco.setUf(endereco.getEstado());
			tbEndereco.setComplemento(endereco.getComplemento());
	
			if (endereco.getTipoEndereco() != null) {
				TbodTipoEndereco tbTipoEndereco = tipoEnderecoDao.findOne(endereco.getTipoEndereco());
				tbEndereco.setTbodTipoEndereco(tbTipoEndereco);
			}
	
			tbEndereco = enderecoDao.save(tbEndereco);
			
			/****  contato empresa ****/
			if (empresa.getContactEmpresa() != null)  {
				TbodEmpresaContato tbodContatoEmpresa = new TbodEmpresaContato();
				ContatoEmpresa contatoEmpresa = empresa.getContactEmpresa();				
			
				tbodContatoEmpresa.setCelular(contatoEmpresa.getCelular()  == null ? " " : contatoEmpresa.getCelular());
				tbodContatoEmpresa.setEmail(contatoEmpresa.getEmail()  == null ? " " : contatoEmpresa.getEmail());
				tbodContatoEmpresa.setNome(contatoEmpresa.getNome()  == null ? " " : contatoEmpresa.getNome());
				tbodContatoEmpresa.setTelefone(contatoEmpresa.getTelefone()  == null ? " " : contatoEmpresa.getTelefone());			
				tbodContatoEmpresa = empresaContatoDAO.save(tbodContatoEmpresa);
			}	
			

			//Iterable<TbodEmpresaContato> entities = new ArrayList<TbodEmpresaContato>();
		
			Long x = (long) empresaContatoDAO.buscamaxCod();
					
			//System.out.println(empresaContatoDAO.buscamaxCod());
			
			tbEmpresa.setCnpj(empresa.getCnpj());
			tbEmpresa.setIncEstadual(empresa.getIncEstadual());
			tbEmpresa.setRamoAtividade(empresa.getRamoAtividade());
			tbEmpresa.setRazaoSocial(empresa.getRazaoSocial());
			tbEmpresa.setNomeFantasia(empresa.getNomeFantasia());
			tbEmpresa.setRepresentanteLegal(empresa.getRepresentanteLegal());
			tbEmpresa.setContatoEmpresa(empresa.isContatoEmpresa() == true ? SIM : NAO);
			tbEmpresa.setTelefone(empresa.getTelefone());
			tbEmpresa.setCelular(empresa.getCelular());
			tbEmpresa.setEmail(empresa.getEmail());
			tbEmpresa.setTbodEndereco(tbEndereco);
			tbEmpresa.setCnae(empresa.getCnae());
			tbEmpresa.setCdEmpresaContato(x);
			tbEmpresa = empresaDao.save(tbEmpresa);
		
			XlsEmpresa xlsEmpresa = new XlsEmpresa();
			xlsEmpresa.GerarEmpresaXLS(tbEmpresa, empresa);
	
		} catch (Exception e) {
			String msgErro = "EmpresaBusiness.salvarEmpresaEndereco :: Erro ao cadastrar empresa; Message:[" + e.getMessage() + "]; Cause:[" + e.getCause() != null ? e.getCause().getMessage() : "NuLL" + "]";
			log.error(msgErro);
			return new EmpresaResponse(0, msgErro);
		}
	
		return new EmpresaResponse(tbEmpresa.getCdEmpresa(), "Empresa cadastrada.");
	}

}
