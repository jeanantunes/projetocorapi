package br.com.odontoprev.portal.corretor.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.ManagedBean;
import javax.transaction.RollbackException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import br.com.odontoprev.portal.corretor.dao.BeneficiarioDAO;
import br.com.odontoprev.portal.corretor.dao.EnderecoDAO;
import br.com.odontoprev.portal.corretor.dao.PlanoDAO;
import br.com.odontoprev.portal.corretor.dao.TipoEnderecoDAO;
import br.com.odontoprev.portal.corretor.dao.VendaDAO;
import br.com.odontoprev.portal.corretor.dao.VendaVidaDAO;
import br.com.odontoprev.portal.corretor.dao.VidaDAO;
import br.com.odontoprev.portal.corretor.dto.Beneficiario;
import br.com.odontoprev.portal.corretor.dto.BeneficiarioPaginacao;
import br.com.odontoprev.portal.corretor.dto.BeneficiarioResponse;
import br.com.odontoprev.portal.corretor.dto.Beneficiarios;
import br.com.odontoprev.portal.corretor.dto.Endereco;
import br.com.odontoprev.portal.corretor.model.TbodEndereco;
import br.com.odontoprev.portal.corretor.model.TbodPlano;
import br.com.odontoprev.portal.corretor.model.TbodTipoEndereco;
import br.com.odontoprev.portal.corretor.model.TbodVenda;
import br.com.odontoprev.portal.corretor.model.TbodVendaVida;
import br.com.odontoprev.portal.corretor.model.TbodVida;
import br.com.odontoprev.portal.corretor.util.ConvertObjectUtil;
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
	
	@Autowired
	PlanoDAO planoDao;

	//201805241505 - esert/yalm - adicionada protecão para dependente com Cpf null
	//201805281830 - esert - forcar erro para causar rollback de toda transacao - teste
	//201805281830 - esert - incluido throws para subir erro e causar rollback de toda transacao - teste
	@Transactional(rollbackFor={Exception.class}) //201806120946 - gmazzi@zarp - rollback vendapme //201806261820 - esert - merge from sprint6_rollback
	public BeneficiarioResponse salvarTitularComDependentes(List<Beneficiario> titulares) throws RollbackException {

		log.info("[salvarTitularComDependentes] ini");

		try {
			if(titulares != null) {

				log.info("titulares.size():["+ titulares.size() +"]");
				for (Beneficiario titular : titulares) {
					log.info("titular.getNome():["+ titular.getNome() +"]");
	
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
					log.info("tbEnderecoTitular.getCdEndereco():["+ tbEnderecoTitular.getCdEndereco() +"]");
	
					TbodVida tbVidaTitular = new TbodVida();
	
					tbVidaTitular.setNome(titular.getNome());
					if(titular.getCpf()!=null) { //201805241938 - esert - protecao extra
						tbVidaTitular.setCpf(titular.getCpf().replace(".", "").replace("-", ""));
					}
					tbVidaTitular.setSexo(titular.getSexo());
					tbVidaTitular.setDataNascimento(DataUtil.dateParse(titular.getDataNascimento()));
					tbVidaTitular.setNomeMae(titular.getNomeMae());
					tbVidaTitular.setCelular(titular.getCelular());
					tbVidaTitular.setEmail(titular.getEmail());
					tbVidaTitular.setPfPj(titular.getPfPj());
					tbVidaTitular.setTbodEndereco(tbEnderecoTitular);
					tbVidaTitular = vidaDao.save(tbVidaTitular);
					log.info("tbVidaTitular.getCdVida():["+ tbVidaTitular.getCdVida() +"]");
	
					TbodVenda tbVenda = vendaDao.findOne(titular.getCdVenda());
	
					TbodVendaVida tbVendaVidaTit = new TbodVendaVida();
					tbVendaVidaTit.setTbodVenda(tbVenda);
					tbVendaVidaTit.setCdPlano(titular.getCdPlano());
					tbVendaVidaTit.setTbodVida(tbVidaTitular);
					tbVendaVidaTit = vendaVidaDao.save(tbVendaVidaTit);
					log.info("tbVendaVidaTit.getCdVendaVida():["+ tbVendaVidaTit.getCdVendaVida() +"]" 
							+ "; getCdVenda():["+ tbVendaVidaTit.getTbodVenda().getCdVenda() +"]" 
							+ "; getCdVida():["+ tbVendaVidaTit.getTbodVida().getCdVida() +"]");
	
					for (Beneficiario dependente : titular.getDependentes()) {
						log.info("dependente.getNome():["+ dependente.getNome() +"]");
	
						TbodVida tbVidaDependente = new TbodVida();
						tbVidaDependente.setNome(dependente.getNome());
						if(dependente.getCpf()!=null) { //201805241505 - esert/yalm - protecão
							tbVidaDependente.setCpf(dependente.getCpf().replace(".", "").replace("-", ""));
						}
						tbVidaDependente.setSexo(dependente.getSexo());
						if(dependente.getDataNascimento()!=null) { //201805241505 - esert/yalm - protegato
							tbVidaDependente.setDataNascimento(DataUtil.dateParse(dependente.getDataNascimento()));
						}
						tbVidaDependente.setNomeMae(dependente.getNomeMae());
						tbVidaDependente.setCelular(dependente.getCelular());
						tbVidaDependente.setEmail(dependente.getEmail());
						tbVidaDependente.setPfPj(dependente.getPfPj());
						tbVidaDependente.setTbodVida(tbVidaTitular);
						tbVidaDependente = vidaDao.save(tbVidaDependente);
						log.info("tbVidaDependente.getCdVida():["+ tbVidaDependente.getCdVida() +"]");
	
						TbodVendaVida tbVendaVidaDep = new TbodVendaVida();
						tbVendaVidaDep.setTbodVenda(tbVenda);
						tbVendaVidaDep.setCdPlano(titular.getCdPlano());
						tbVendaVidaDep.setTbodVida(tbVidaDependente);
						tbVendaVidaDep = vendaVidaDao.save(tbVendaVidaDep);
						log.info("tbVendaVidaDep.getCdVendaVida():["+ tbVendaVidaDep.getCdVendaVida() +"]" 
								+ "; getCdVenda():["+ tbVendaVidaDep.getTbodVenda().getCdVenda() +"]" 
								+ "; getCdVida():["+ tbVendaVidaDep.getTbodVida().getCdVida() +"]");
					}
				}
			}
		} catch (Exception e) {
			log.error("salvarTitularComDependentes :: Erro ao cadastrar vidas. Detalhe: [" + e.getMessage() + "]");
			//return new BeneficiarioResponse(0, "Erro ao cadastrar vidas. []");
			throw new RollbackException("Erro ao cadastrar vidas. Exception:[" + e.getMessage() + "]"); //201805281830 - esert - forcar erro para causar rollback de toda transacao - teste
		}

		log.info("[salvarTitularComDependentes] fim");
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
					TbodPlano tbPlano = planoDao.findByCdPlano(tbVendaVida.getCdPlano());
					tbVendaVida.getTbodVida().setSiglaPlano(tbPlano.getSigla());
					tbVidas.add(tbVendaVida.getTbodVida());
				}
			}

			return tbVidas;

		} catch (Exception e) {
			log.error("buscarVidasPorEmpresa :: Erro ao buscar vidas por empresa. Detalhe: [" + e.getMessage() + "]");
			return new ArrayList<TbodVida>();
		}
	}

	//201807241918 - esert - COR-398
	public Beneficiario get(Long cdVida) {
		return ConvertObjectUtil.translateTbodVidaToBeneficiario(vidaDao.findOne(cdVida));
	}

}
