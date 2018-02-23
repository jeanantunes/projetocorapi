package br.com.odontoprev.portal.corretor.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.odontoprev.portal.corretor.dao.EnderecoDAO;
import br.com.odontoprev.portal.corretor.dao.VendaDAO;
import br.com.odontoprev.portal.corretor.dao.VendaVidaDAO;
import br.com.odontoprev.portal.corretor.dao.VidaDAO;
import br.com.odontoprev.portal.corretor.dto.Beneficiario;
import br.com.odontoprev.portal.corretor.dto.BeneficiarioResponse;
import br.com.odontoprev.portal.corretor.dto.Endereco;
import br.com.odontoprev.portal.corretor.model.TbodEndereco;
import br.com.odontoprev.portal.corretor.model.TbodVenda;
import br.com.odontoprev.portal.corretor.model.TbodVendaVida;
import br.com.odontoprev.portal.corretor.model.TbodVida;
import br.com.odontoprev.portal.corretor.service.BeneficiarioService;
import br.com.odontoprev.portal.corretor.util.DataUtil;

@Service
public class BeneficiarioServiceImpl implements BeneficiarioService {

	private static final Log log = LogFactory.getLog(BeneficiarioServiceImpl.class);

	@Autowired
	VidaDAO vidaDao;

	@Autowired
	EnderecoDAO enderecoDao;

	@Autowired
	VendaDAO vendaDao;

	@Autowired
	VendaVidaDAO vendaVidaDao;

	@Override
	@Transactional
	public BeneficiarioResponse add(List<Beneficiario> beneficiarios) {

		log.info("[BeneficiarioServiceImpl::add]");

		TbodVida titular = new TbodVida();

		try {

			for (Beneficiario beneficiario : beneficiarios) {

				// TODO Verificar inclusao de endereco
				 TbodEndereco tbEndereco = new TbodEndereco();
				 Endereco endereco = beneficiario.getEndereco();
				
				 tbEndereco.setLogradouro(endereco.getEndereco());
				 tbEndereco.setBairro(endereco.getBairro());
				 tbEndereco.setCep(endereco.getBairro());
				 tbEndereco.setCidade(endereco.getCidade());
				 tbEndereco.setNumero(endereco.getNumero());
				 tbEndereco.setUf(endereco.getEstado());
				 tbEndereco.setComplemento(endereco.getComplemento());
				 tbEndereco = enderecoDao.save(tbEndereco);

				titular.setNome(beneficiario.getNome());
				titular.setCpf(beneficiario.getCpf());
				titular.setSexo(beneficiario.getSexo());
				titular.setDataNascimento(DataUtil.dateParse(beneficiario.getDataNascimento()));
				titular.setNomeMae(beneficiario.getNomeMae());
				titular.setCelular(beneficiario.getCelular());
				titular.setEmail(beneficiario.getEmail());
				titular.setPfPj(beneficiario.getPfPj());
				titular.setTbodEndereco(tbEndereco);
				titular = vidaDao.save(titular);

				TbodVenda tbVenda = vendaDao.findOne(beneficiario.getCdVenda());

				TbodVendaVida tbVendaVidaTit = new TbodVendaVida();
				tbVendaVidaTit.setTbodVenda(tbVenda);
				tbVendaVidaTit.setCdPlano(beneficiario.getCdPlano());
				tbVendaVidaTit.setTbodVida(titular);
				vendaVidaDao.save(tbVendaVidaTit);
				
				for (Beneficiario dependente : beneficiario.getDependentes()) {
					TbodVida tbdep = new TbodVida();
					tbdep.setNome(dependente.getNome());
					tbdep.setCpf(dependente.getCpf());
					tbdep.setSexo(dependente.getSexo());
					tbdep.setDataNascimento(DataUtil.dateParse(dependente.getDataNascimento()));
					tbdep.setNomeMae(dependente.getNomeMae());
					tbdep.setCelular(dependente.getCelular());
					tbdep.setEmail(dependente.getEmail());
					tbdep.setPfPj(dependente.getPfPj());
					tbdep.setTbodVida(titular);
					tbdep = vidaDao.save(tbdep);

					TbodVendaVida tbVendaVidaDep = new TbodVendaVida();
					tbVendaVidaDep.setTbodVenda(tbVenda);
					tbVendaVidaDep.setCdPlano(beneficiario.getCdPlano());
					tbVendaVidaDep.setTbodVida(tbdep);
					vendaVidaDao.save(tbVendaVidaDep);
				}
			}

		} catch (Exception e) {
			log.error("BeneficiarioServiceImpl :: Erro ao cadastrar vidas. Detalhe: [" + e.getMessage() + "]");
			return new BeneficiarioResponse(0, "Erro ao cadastrar vidas. Favor, entre em contato com o suporte.");
		}

		return new BeneficiarioResponse(1, "Vidas cadastradas.");
	}

}
