package br.com.odontoprev.portal.corretor.business;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.odontoprev.portal.corretor.dao.BeneficiarioDAO;
import br.com.odontoprev.portal.corretor.dao.EnderecoDAO;
import br.com.odontoprev.portal.corretor.dao.TipoEnderecoDAO;
import br.com.odontoprev.portal.corretor.dao.VendaDAO;
import br.com.odontoprev.portal.corretor.dao.VendaVidaDAO;
import br.com.odontoprev.portal.corretor.dao.VidaDAO;
import br.com.odontoprev.portal.corretor.dto.Beneficiario;
import br.com.odontoprev.portal.corretor.dto.BeneficiarioResponse;
import br.com.odontoprev.portal.corretor.dto.Endereco;
import br.com.odontoprev.portal.corretor.model.TbodEndereco;
import br.com.odontoprev.portal.corretor.model.TbodTipoEndereco;
import br.com.odontoprev.portal.corretor.model.TbodVenda;
import br.com.odontoprev.portal.corretor.model.TbodVendaVida;
import br.com.odontoprev.portal.corretor.model.TbodVida;
import br.com.odontoprev.portal.corretor.util.DataUtil;

@ManagedBean
public class BeneficiarioBusiness {

	private static final Log log = LogFactory.getLog(BeneficiarioBusiness.class);

	@Autowired
	VidaDAO vidaDao;

	@Autowired
	EnderecoDAO enderecoDao;

	@Autowired
	VendaDAO vendaDao;

	@Autowired
	VendaVidaDAO vendaVidaDao;

	@Autowired
	TipoEnderecoDAO tipoEnderecoDao;

	@Autowired
	BeneficiarioDAO beneficiarioDao;

	public BeneficiarioResponse salvarTitularComDependentes(List<Beneficiario> titulares) {

		log.info("[salvarTitularComDependentes]");

		try {

			for (Beneficiario titular : titulares) {

				Endereco enderecoTitular = titular.getEndereco();

				TbodEndereco tbEnderecoTitular = new TbodEndereco();

				tbEnderecoTitular.setLogradouro(enderecoTitular.getLogradouro());
				tbEnderecoTitular.setBairro(enderecoTitular.getBairro());
				tbEnderecoTitular.setCep(enderecoTitular.getCep());
				tbEnderecoTitular.setCidade(enderecoTitular.getCidade());
				tbEnderecoTitular.setNumero(enderecoTitular.getNumero());
				tbEnderecoTitular.setUf(enderecoTitular.getEstado());
				tbEnderecoTitular.setComplemento(enderecoTitular.getComplemento());

				if (enderecoTitular.getTipoEndereco() != null) {
					TbodTipoEndereco tbTipoEndereco = tipoEnderecoDao.findOne(enderecoTitular.getTipoEndereco());
					tbEnderecoTitular.setTbodTipoEndereco(tbTipoEndereco);
				}

				tbEnderecoTitular = enderecoDao.save(tbEnderecoTitular);

				TbodVida tbVidaTitular = new TbodVida();

				tbVidaTitular.setNome(titular.getNome());
				tbVidaTitular.setCpf(titular.getCpf());
				tbVidaTitular.setSexo(titular.getSexo());
				tbVidaTitular.setDataNascimento(DataUtil.dateParse(titular.getDataNascimento()));
				tbVidaTitular.setNomeMae(titular.getNomeMae());
				tbVidaTitular.setCelular(titular.getCelular());
				tbVidaTitular.setEmail(titular.getEmail());
				tbVidaTitular.setPfPj(titular.getPfPj());
				tbVidaTitular.setTbodEndereco(tbEnderecoTitular);

				tbVidaTitular = vidaDao.save(tbVidaTitular);

				TbodVenda tbVenda = vendaDao.findOne(titular.getCdVenda());

				TbodVendaVida tbVendaVidaTit = new TbodVendaVida();
				tbVendaVidaTit.setTbodVenda(tbVenda);
				tbVendaVidaTit.setCdPlano(titular.getCdPlano());
				tbVendaVidaTit.setTbodVida(tbVidaTitular);
				vendaVidaDao.save(tbVendaVidaTit);

				for (Beneficiario dependente : titular.getDependentes()) {

					TbodVida tbVidaDependente = new TbodVida();
					tbVidaDependente.setNome(dependente.getNome());
					tbVidaDependente.setCpf(dependente.getCpf());
					tbVidaDependente.setSexo(dependente.getSexo());
					tbVidaDependente.setDataNascimento(DataUtil.dateParse(dependente.getDataNascimento()));
					tbVidaDependente.setNomeMae(dependente.getNomeMae());
					tbVidaDependente.setCelular(dependente.getCelular());
					tbVidaDependente.setEmail(dependente.getEmail());
					tbVidaDependente.setPfPj(dependente.getPfPj());
					tbVidaDependente.setTbodVida(tbVidaTitular);
					tbVidaDependente = vidaDao.save(tbVidaDependente);

					TbodVendaVida tbVendaVidaDep = new TbodVendaVida();
					tbVendaVidaDep.setTbodVenda(tbVenda);
					tbVendaVidaDep.setCdPlano(titular.getCdPlano());
					tbVendaVidaDep.setTbodVida(tbVidaDependente);
					vendaVidaDao.save(tbVendaVidaDep);
				}
			}

		} catch (Exception e) {
			log.error("salvarTitularComDependentes :: Erro ao cadastrar vidas. Detalhe: [" + e.getMessage() + "]");
			return new BeneficiarioResponse(0, "Erro ao cadastrar vidas. Favor, entre em contato com o suporte.");
		}

		return new BeneficiarioResponse(1, "Vidas cadastradas.");
	}

	public List<TbodVida> buscarVidasPorEmpresa(Long cdEmpresa) {

		log.info("[buscarVidasPorEmpresa]");

		try {

			List<TbodVenda> tbVendas = new ArrayList<TbodVenda>();

			tbVendas = vendaDao.findByTbodEmpresaCdEmpresa(cdEmpresa);

			List<TbodVendaVida> tbVendaVidas = new ArrayList<TbodVendaVida>();

			for (TbodVenda tbVenda : tbVendas) {
				tbVendaVidas.addAll(tbVenda.getTbodVendaVidas());
			}

			List<TbodVida> tbVidas = new ArrayList<TbodVida>();

			for (TbodVendaVida tbVendaVida : tbVendaVidas) {
				if (tbVendaVida.getTbodVida() != null) {
					tbVidas.add(tbVendaVida.getTbodVida());
				}
			}

			return tbVidas;

		} catch (Exception e) {
			log.error("buscarVidasPorEmpresa :: Erro ao buscar vidas por empresa. Detalhe: [" + e.getMessage() + "]");
			return new ArrayList<TbodVida>();
		}
	}

}
