package br.com.odontoprev.portal.corretor.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.dao.BancoContaDAO;
import br.com.odontoprev.portal.corretor.dao.CorretoraBancoDAO;
import br.com.odontoprev.portal.corretor.dao.CorretoraDAO;
import br.com.odontoprev.portal.corretor.dao.EnderecoDAO;
import br.com.odontoprev.portal.corretor.dto.Corretora;
import br.com.odontoprev.portal.corretor.dto.CorretoraResponse;
import br.com.odontoprev.portal.corretor.dto.Endereco;
import br.com.odontoprev.portal.corretor.model.TbodBancoConta;
import br.com.odontoprev.portal.corretor.model.TbodCorretora;
import br.com.odontoprev.portal.corretor.model.TbodCorretoraBanco;
import br.com.odontoprev.portal.corretor.model.TbodEndereco;
import br.com.odontoprev.portal.corretor.service.CorretoraService;

@Service
public class CorretoraServiceImpl implements CorretoraService {

	@Autowired
	CorretoraDAO corretoraDao;

	@Autowired
	EnderecoDAO enderecoDao;

	@Autowired
	BancoContaDAO bancoContaDAO;

	@Autowired
	CorretoraBancoDAO corretoraBancoDAO;

	@Override
	public CorretoraResponse addCorretora(Corretora corretora) {
		return this.saveCorretora(corretora);
	}

	@Override
	public CorretoraResponse addCorretor(Corretora corretora) {
		return this.saveCorretora(corretora);
	}

	private CorretoraResponse saveCorretora(Corretora corretora) {

		TbodEndereco tbEndereco = new TbodEndereco();
		Endereco endereco = corretora.getEnderecoCorretora();
		
		tbEndereco.setLogradouro(endereco.getLogradouro()==null ? " " : endereco.getLogradouro());
		tbEndereco.setBairro(endereco.getBairro()==null ? " " : endereco.getBairro());
		tbEndereco.setCep(endereco.getCep()==null ? " " : endereco.getCep());
		tbEndereco.setCidade(endereco.getCidade()==null ? " " : endereco.getCidade());
		tbEndereco.setNumero(endereco.getNumero()==null ? " " : endereco.getNumero());
		tbEndereco.setUf(endereco.getEstado()==null ? " " : endereco.getEstado());
		tbEndereco = enderecoDao.save(tbEndereco);
		
		TbodBancoConta conta = new TbodBancoConta();
		conta.setAgencia(corretora.getConta().getCodigoAgencia()==null ? " " : corretora.getConta().getCodigoAgencia());
		conta.setConta(corretora.getConta().getNumeroConta()==null ? " " : corretora.getConta().getNumeroConta());
		conta.setCodigoBanco(new BigDecimal(corretora.getConta().getCodigoBanco()));
		conta = bancoContaDAO.save(conta);
		
		TbodCorretora tbCorretora = new TbodCorretora();
		tbCorretora.setAtivo("S"); // TODO Onde pega esse campo?
		tbCorretora.setCpfResponsavel("0000000");  // TODO Onde pega esse campo?
		tbCorretora.setDataAbertura(corretora.getDataAbertura());
		tbCorretora.setRazaoSocial(corretora.getRazaoSocial()==null ? " " : corretora.getRazaoSocial());      
		tbCorretora.setSimplesNacional(corretora.isSimplesNacional() == true ? "S" : "N");
		tbCorretora.setStatusCnpj(corretora.isStatusCnpj() == true ? "S" : "N");
		tbCorretora.setNome(corretora.getRazaoSocial()==null ? " " : corretora.getRazaoSocial()); //TODO Nome do Corretor? 
		tbCorretora.setCodigo("000");  // TODO Onde pega esse codigo?
		tbCorretora.setCnpj(corretora.getCnpj()==null ? " " : corretora.getCnpj());
		tbCorretora.setEmail(corretora.getEmail()==null ? " " : corretora.getEmail());
		tbCorretora.setTelefone(corretora.getTelefone()==null ? " " : corretora.getTelefone());
		tbCorretora.setRegional("1"); // TODO Onde pega a Regional? 
		tbCorretora.setCelular(corretora.getCelular()==null ? " " : corretora.getCelular());
		tbCorretora.setTbodEndereco(tbEndereco);
		tbCorretora.setNomeRepresentanteLegal1(" "); 
		tbCorretora.setNomeRepresentanteLegal1(" ");
		
		TbodCorretoraBanco corretoraBanco = new TbodCorretoraBanco();
		corretoraBanco.setCnae(corretora.getCnae()==null ? " " : corretora.getCnae());
		corretoraBanco.setCpfResponsavelLegal1(" ");
		corretoraBanco.setCpfResponsavelLegal2(" ");
		corretoraBanco.setTbodBancoConta(conta);
		
		if(!corretora.getRepresentantes().isEmpty()) {
			tbCorretora.setNomeRepresentanteLegal1(corretora.getRepresentantes().get(0).getNome());
			corretoraBanco.setCpfResponsavelLegal1(corretora.getRepresentantes().get(0).getNome());
			
			if(corretora.getRepresentantes().size() > 1) {
				tbCorretora.setNomeRepresentanteLegal2(corretora.getRepresentantes().get(1).getNome());
				corretoraBanco.setCpfResponsavelLegal2(corretora.getRepresentantes().get(1).getNome());
			}
			
		}
		
		tbCorretora = corretoraDao.save(tbCorretora);
		
		corretoraBanco.setTbodCorretora(tbCorretora);
		corretoraBanco = corretoraBancoDAO.save(corretoraBanco);

		return new CorretoraResponse(tbCorretora.getCdCorretora());

	}
}
