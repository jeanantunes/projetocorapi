package br.com.odontoprev.portal.corretor.service.impl;



import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.odontoprev.portal.corretor.dao.EmpresaDAO;
import br.com.odontoprev.portal.corretor.dao.EnderecoDAO;
import br.com.odontoprev.portal.corretor.dao.PlanoDAO;
import br.com.odontoprev.portal.corretor.dao.VendaDAO;
import br.com.odontoprev.portal.corretor.dto.Empresa;
import br.com.odontoprev.portal.corretor.dto.EmpresaResponse;
import br.com.odontoprev.portal.corretor.dto.Endereco;
import br.com.odontoprev.portal.corretor.dto.Plano;
import br.com.odontoprev.portal.corretor.model.TbodEmpresa;
import br.com.odontoprev.portal.corretor.model.TbodEndereco;
import br.com.odontoprev.portal.corretor.model.TbodPlano;
import br.com.odontoprev.portal.corretor.model.TbodVenda;
import br.com.odontoprev.portal.corretor.service.EmpresaService;

@Service
public class EmpresaServiceImpl implements EmpresaService {
	
	private static final Log log = LogFactory.getLog(EmpresaServiceImpl.class);
	
	@Autowired
	EnderecoDAO enderecoDao;
	
	@Autowired
	EmpresaDAO empresaDao;
	
	@Autowired
	PlanoDAO planoDao;
	
	@Autowired
	VendaDAO vendaDao;
	
	@Override
	@Transactional
	public EmpresaResponse add(Empresa empresa) {
		
		log.info("[EmpresaServiceImpl::add]");
		
		TbodEmpresa tbEmpresa = new TbodEmpresa();
		
		try {
			
			TbodEndereco tbEndereco = new TbodEndereco();
			Endereco endereco = empresa.getEnderecoEmpresa();
			tbEndereco.setLogradouro(endereco.getEndereco());
			tbEndereco.setBairro(endereco.getBairro());
			tbEndereco.setCep(endereco.getBairro());
			tbEndereco.setCidade(endereco.getCidade());
			tbEndereco.setNumero(endereco.getNumero());
			tbEndereco.setUf(endereco.getEstado());
			tbEndereco.setComplemento(endereco.getComplemento());
			tbEndereco = enderecoDao.save(tbEndereco);
			
			tbEmpresa.setCnpj(empresa.getCnpj());
			tbEmpresa.setIncEstadual(empresa.getIncEstadual());
			tbEmpresa.setRamoAtividade(empresa.getRamoAtividade());
			tbEmpresa.setRazaoSocial(empresa.getRazaoSocial());
			tbEmpresa.setNomeFantasia(empresa.getNomeFantasia());
			tbEmpresa.setRepresentanteLegal(empresa.getRepresentanteLegal());
			tbEmpresa.setContatoEmpresa(empresa.isContatoEmpresa() == true ? "S" : "N");
			tbEmpresa.setTelefone(empresa.getTelefone());
			tbEmpresa.setCelular(empresa.getCelular());
			tbEmpresa.setEmail(empresa.getEmail());
			tbEmpresa.setTbodEndereco(tbEndereco);
			tbEmpresa = empresaDao.save(tbEmpresa);
			
			if(!empresa.getPlanos().isEmpty()) {
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
			
		} catch (Exception e) {
			log.info("EmpresaServiceImpl :: Erro ao cadastrar empresa. Detalhe: [" + e.getMessage() + "]");
			return new EmpresaResponse(0, "Erro ao cadastrar empresa. Favor, entre em contato com o suporte.");
		}
			
		return new EmpresaResponse(tbEmpresa.getCdEmpresa(), "Empresa cadastrada.");
	}

}
