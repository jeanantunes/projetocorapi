package br.com.odontoprev.portal.corretor.business;

import javax.annotation.ManagedBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import br.com.odontoprev.portal.corretor.dao.EnderecoDAO;
import br.com.odontoprev.portal.corretor.dao.ResponsavelContratualDAO;
import br.com.odontoprev.portal.corretor.dao.TipoEnderecoDAO;
import br.com.odontoprev.portal.corretor.dto.EnderecoProposta;
import br.com.odontoprev.portal.corretor.dto.ResponsavelContratual;
import br.com.odontoprev.portal.corretor.model.TbodEndereco;
import br.com.odontoprev.portal.corretor.model.TbodResponsavelContratual;
import br.com.odontoprev.portal.corretor.util.DataUtil;

@ManagedBean
public class ResponsavelContratualBusiness {
	
	private static final Log log = LogFactory.getLog(ResponsavelContratualBusiness.class);
	
	@Autowired
	TipoEnderecoDAO tipoEnderecoDao;
	
	@Autowired
	EnderecoDAO enderecoDao;
	
	@Autowired
	ResponsavelContratualDAO responsavelContratualDao;

	@Transactional(rollbackFor={Exception.class}) //201806290926 - esert - COR-352 rollback pf
	public TbodResponsavelContratual salvarResponsavelContratualComEndereco(
			ResponsavelContratual responsavelContratual) {

		log.info("salvarResponsavelContratualComEndereco");
		
		TbodResponsavelContratual tbResponsavelContratual = null;
		
		try {
			
			tbResponsavelContratual = new TbodResponsavelContratual();
			tbResponsavelContratual.setNome(responsavelContratual.getNome());
			tbResponsavelContratual.setCpf(responsavelContratual.getCpf() != null && !responsavelContratual.getCpf().isEmpty() ? 
					responsavelContratual.getCpf().replace(".", "").replace("-", "") : "");
			tbResponsavelContratual.setCelular(responsavelContratual.getCelular() != null && !responsavelContratual.getCelular().isEmpty() ? 
					responsavelContratual.getCelular().replace(".", "").replace("-", "").replace(" ", "").replace("(", "").replace(")", "") : "");
			tbResponsavelContratual.setEmail(responsavelContratual.getEmail());
			tbResponsavelContratual.setDataNascimento(DataUtil.dateParse(responsavelContratual.getDataNascimento()));
			tbResponsavelContratual.setSexo(responsavelContratual.getSexo());
			
			if(responsavelContratual.getEndereco() != null) {
				
				EnderecoProposta endereco = responsavelContratual.getEndereco();
				TbodEndereco tbEndereco = new TbodEndereco();
				
				tbEndereco.setLogradouro(endereco.getLogradouro());
				tbEndereco.setNumero(endereco.getNumero());
				tbEndereco.setBairro(endereco.getBairro());
				tbEndereco.setComplemento(endereco.getComplemento());
				tbEndereco.setCidade(endereco.getCidade());
				tbEndereco.setUf(endereco.getEstado());
				tbEndereco.setCep(endereco.getCep() != null && !endereco.getCep().isEmpty() ? endereco.getCep().replaceAll("-", "") : "");
				
				tbEndereco = enderecoDao.save(tbEndereco);
				
				tbResponsavelContratual.setTbodEndereco(tbEndereco);
				
			}
			
			tbResponsavelContratual = responsavelContratualDao.save(tbResponsavelContratual);
			
			log.info("salvarResponsavelContratualComEndereco :: "
					+ "Responsavel Contratual cadastrado com sucesso! "
					+ "CdResponsavelContratual [" + tbResponsavelContratual.getCdResponsavelContratual() + "]");
			
		} catch (Exception e) {
			log.error("salvarResponsavelContratualComEndereco :: "
					+ "Erro ao cadastrar Responsavel Contratual. "
					+ "Detalhe [" + e.getMessage() + "]");
		}
		
		return tbResponsavelContratual;
		
	}

}
