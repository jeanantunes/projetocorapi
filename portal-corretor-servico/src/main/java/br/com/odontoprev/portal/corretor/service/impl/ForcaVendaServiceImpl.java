package br.com.odontoprev.portal.corretor.service.impl;

import static br.com.odontoprev.portal.corretor.enums.StatusForcaVendaEnum.AGUARDANDO_APRO;
import static br.com.odontoprev.portal.corretor.enums.StatusForcaVendaEnum.ATIVO;
import static br.com.odontoprev.portal.corretor.enums.StatusForcaVendaEnum.PRE_CADASTRO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.odontoprev.portal.corretor.dao.CorretoraDAO;
import br.com.odontoprev.portal.corretor.dao.ForcaVendaDAO;
import br.com.odontoprev.portal.corretor.dao.LoginDAO;
import br.com.odontoprev.portal.corretor.dao.StatusForcaVendaDAO;
import br.com.odontoprev.portal.corretor.dto.Corretora;
import br.com.odontoprev.portal.corretor.dto.Endereco;
import br.com.odontoprev.portal.corretor.dto.ForcaVenda;
import br.com.odontoprev.portal.corretor.dto.ForcaVendaResponse;
import br.com.odontoprev.portal.corretor.model.TbodCorretora;
import br.com.odontoprev.portal.corretor.model.TbodForcaVenda;
import br.com.odontoprev.portal.corretor.model.TbodLogin;
import br.com.odontoprev.portal.corretor.model.TbodStatusForcaVenda;
import br.com.odontoprev.portal.corretor.service.ForcaVendaService;
import br.com.odontoprev.portal.corretor.util.DataUtil;

@Service
public class ForcaVendaServiceImpl implements ForcaVendaService {

	private static final Log log = LogFactory.getLog(ForcaVendaServiceImpl.class);

	@Autowired
	private CorretoraDAO corretoraDao;

	@Autowired
	private StatusForcaVendaDAO statusForcaVendaDao;

	@Autowired
	private ForcaVendaDAO forcaVendaDao;

	@Autowired
	private LoginDAO loginDao;

	@Value("${DCSS_URL}")
	private String dcssUrl;

	private void integracaoForcaDeVendaDcss(ForcaVenda forca) {

		Map<String, Object> forcaMap = new HashMap<>();

		RestTemplate restTemplate = new RestTemplate();
		forcaMap.put("nome", forca.getNome());
		forcaMap.put("email", forca.getEmail());
		forcaMap.put("telefone", forca.getCelular());
		forcaMap.put("nomeEmpresa", forca.getNomeEmpresa());
		forcaMap.put("login", forca.getCpf());
		forcaMap.put("rg", forca.getRg());
		forcaMap.put("cpf", forca.getCpf());
		forcaMap.put("cargo", forca.getCargo());
		forcaMap.put("responsavel", forca.getResponsavel());
		forcaMap.put("nomeGerente", forca.getNomeGerente());
		forcaMap.put("senha", forca.getSenha());
		forcaMap.put("canalVenda", forca.getCdForcaVenda());
		log.error(forcaMap);
		restTemplate.postForEntity((dcssUrl + "/usuario/1.0/"), forcaMap, ForcaVenda.class);

	}

	@Override
	public ForcaVendaResponse addForcaVenda(ForcaVenda forcaVenda) {

		log.info("[addForcaVenda]");

		TbodForcaVenda tbForcaVenda = new TbodForcaVenda();

		try {

			List<TbodForcaVenda> forcas = forcaVendaDao.findByCpf(forcaVenda.getCpf());
			if (forcas != null && !forcas.isEmpty()) {
				throw new Exception("CPF [" + forcaVenda.getCpf() + "] já existe!");
			}

			tbForcaVenda.setNome(forcaVenda.getNome());
			tbForcaVenda.setCpf(forcaVenda.getCpf());
			tbForcaVenda.setDataNascimento(DataUtil.dateParse(forcaVenda.getDataNascimento()));
			tbForcaVenda.setCelular(forcaVenda.getCelular());
			tbForcaVenda.setEmail(forcaVenda.getEmail());
			tbForcaVenda.setAtivo("N");
			tbForcaVenda.setCargo(forcaVenda.getCargo());
			tbForcaVenda.setDepartamento(forcaVenda.getDepartamento());
			TbodStatusForcaVenda tbStatusForcaVenda = new TbodStatusForcaVenda();
			if (forcaVenda.getCorretora() != null && forcaVenda.getCorretora().getCdCorretora() > 0) {
				TbodCorretora tbCorretora = corretoraDao.findOne(forcaVenda.getCorretora().getCdCorretora());
				tbForcaVenda.setTbodCorretora(tbCorretora);
				tbStatusForcaVenda = statusForcaVendaDao.findOne(PRE_CADASTRO.getCodigo());
			} else {
				tbStatusForcaVenda = statusForcaVendaDao.findOne(AGUARDANDO_APRO.getCodigo());
			}
			tbForcaVenda.setTbodStatusForcaVenda(tbStatusForcaVenda);

			// Grava senha na tabela de login na tela de Aguardando Aprovacao
			if (forcaVenda.getSenha() != null && forcaVenda.getSenha() != "") {
				TbodLogin tbLogin = new TbodLogin();
				tbLogin.setCdTipoLogin((long) 1); // TODO
				tbLogin.setSenha(forcaVenda.getSenha());
				tbLogin = loginDao.save(tbLogin);
				tbForcaVenda.setTbodLogin(tbLogin);
			}

			tbForcaVenda = forcaVendaDao.save(tbForcaVenda);
			// integracaoForcaDeVendaDcss(forcaVenda); //Chamar no PUT

		} catch (Exception e) {
			// log.error(e);
			log.error("Erro ao cadastrar forcaVenda :: Detalhe: [" + e.getMessage() + "]");
			return new ForcaVendaResponse(0, "Erro ao cadastrar forcaVenda. Detalhe: [" + e.getMessage() + "]");
		}

		return new ForcaVendaResponse(tbForcaVenda.getCdForcaVenda(), "ForcaVenda cadastrada.");
	}

	@Override
	public ForcaVenda findForcaVendaByCpf(String cpf) {

		log.info("[findForcaVendaByCpf]");

		ForcaVenda forcaVenda = new ForcaVenda();

		List<TbodForcaVenda> entities = forcaVendaDao.findByCpf(cpf);

		if (!entities.isEmpty()) {
			TbodForcaVenda tbForcaVenda = entities.get(0);

			forcaVenda.setCdForcaVenda(tbForcaVenda.getCdForcaVenda());
			forcaVenda.setNome(tbForcaVenda.getNome());
			forcaVenda.setCelular(tbForcaVenda.getCelular());
			forcaVenda.setEmail(tbForcaVenda.getEmail());
			forcaVenda.setCpf(tbForcaVenda.getCpf());
			forcaVenda.setAtivo("S".equals(tbForcaVenda.getAtivo()) ? true : false);

			String dataStr = "00/00/0000";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try {
				dataStr = sdf.format(tbForcaVenda.getDataNascimento());
			} catch (Exception e) {
				log.error("Erro ao formatar data de nascimento :: Detalhe: [" + e.getMessage() + "]");
			}
			forcaVenda.setDataNascimento(dataStr);

			forcaVenda.setCargo(tbForcaVenda.getCargo());
			forcaVenda.setDepartamento(tbForcaVenda.getDepartamento());

			String statusForca = tbForcaVenda.getTbodStatusForcaVenda() != null
					? tbForcaVenda.getTbodStatusForcaVenda().getDescricao()
					: "";
			forcaVenda.setStatusForcaVenda(statusForca);

			if (tbForcaVenda.getTbodCorretora() != null) {

				TbodCorretora tbCorretora = tbForcaVenda.getTbodCorretora();
				Corretora corretora = new Corretora();

				corretora.setCdCorretora(tbCorretora.getCdCorretora());
				corretora.setCnpj(tbCorretora.getCnpj());
				corretora.setRazaoSocial(tbCorretora.getRazaoSocial());

				forcaVenda.setCorretora(corretora);
			}
		}

		return forcaVenda;

	}

	@Override
	public ForcaVendaResponse findAssocForcaVendaCorretora(Long cdForcaVenda, String cnpj) {

		log.info("[findAssocForcaVendaCorretora]");

		TbodForcaVenda tbForcaVenda;

		try {

			tbForcaVenda = forcaVendaDao.findByCdForcaVendaAndTbodCorretoraCnpj(cdForcaVenda, cnpj);

			if (tbForcaVenda == null) {
				throw new Exception("ForcaVenda e Corretora nao associadas!");
			}

		} catch (Exception e) {
			log.error("Erro ao verificar associacao ForcaVendaCorretora :: Detalhe: [" + e.getMessage() + "]");
			return new ForcaVendaResponse(0,
					"Erro ao verificar associacao ForcaVendaCorretora. Detalhe: [" + e.getMessage() + "].");
		}

		return new ForcaVendaResponse(1, "ForcaVenda [" + tbForcaVenda.getCpf() + "] esta associada a corretora ["
				+ tbForcaVenda.getTbodCorretora().getCnpj() + "]");

	}

	@Override
	public ForcaVendaResponse updateForcaVendaLogin(ForcaVenda forcaVenda) {

		log.info("[updateForcaVendaLogin]");

		TbodForcaVenda tbForcaVenda = new TbodForcaVenda();

		try {

			if (forcaVenda.getCdForcaVenda() == null) {
				throw new Exception("cdForcaVenda nao informado. Atualizacao nao realizada!");
			}

			tbForcaVenda = forcaVendaDao.findOne(forcaVenda.getCdForcaVenda());

			if (tbForcaVenda == null) {
				throw new Exception("cdForcaVenda nao encontrado. Atualizacao nao realizada!");
			}

			if (forcaVenda.getNome() != null && !forcaVenda.getNome().isEmpty()) {
				tbForcaVenda.setNome(forcaVenda.getNome());
			}
			if (forcaVenda.getCelular() != null && !forcaVenda.getCelular().isEmpty()) {
				tbForcaVenda.setCelular(forcaVenda.getCelular());
			}
			if (forcaVenda.getEmail() != null && !forcaVenda.getEmail().isEmpty()) {
				tbForcaVenda.setEmail(forcaVenda.getEmail());
			}

			TbodStatusForcaVenda tbStatusForcaVenda = statusForcaVendaDao.findOne(ATIVO.getCodigo());
			tbForcaVenda.setTbodStatusForcaVenda(tbStatusForcaVenda);
			tbForcaVenda.setAtivo("S");

			if (forcaVenda.getSenha() != null && !forcaVenda.getSenha().isEmpty()) {
				TbodLogin tbLogin = new TbodLogin();
				tbLogin.setCdTipoLogin((long) 1); // TODO
				tbLogin.setSenha(forcaVenda.getSenha());
				tbLogin = loginDao.save(tbLogin);
				tbForcaVenda.setTbodLogin(tbLogin);
			}
			// Atualiza forcaVenda e associa login
			tbForcaVenda = forcaVendaDao.save(tbForcaVenda);
			forcaVenda = this.adaptEntityToDto(tbForcaVenda, forcaVenda);
			this.integracaoForcaDeVendaDcss(forcaVenda);

		} catch (Exception e) {
			log.error("Erro ao atualizar ForcaVendaLogin :: Detalhe: [" + e.getMessage() + "]");
			return new ForcaVendaResponse(0, "Erro ao atualizar ForcaVendaLogin :: Detalhe: [" + e.getMessage() + "].");
		}

		return new ForcaVendaResponse(1, "ForcaVendaLogin atualizado! [" + tbForcaVenda.getCpf() + "]");
	}

	private ForcaVenda adaptEntityToDto(TbodForcaVenda tbForcaVenda, ForcaVenda forcaVenda) {

		forcaVenda.setCdForcaVenda(tbForcaVenda.getCdForcaVenda());
		forcaVenda.setNome(tbForcaVenda.getNome());
		forcaVenda.setCelular(tbForcaVenda.getCelular());
		forcaVenda.setEmail(tbForcaVenda.getEmail());
		forcaVenda.setStatusForcaVenda(tbForcaVenda.getTbodStatusForcaVenda().getDescricao());
		forcaVenda.setCpf(tbForcaVenda.getCpf());
		forcaVenda.setAtivo(tbForcaVenda.getAtivo() == "S" ? true : false);
		forcaVenda.setDepartamento(tbForcaVenda.getDepartamento());
		forcaVenda.setCargo(tbForcaVenda.getCargo());

		return forcaVenda;
	}

	@Override
	public List<ForcaVenda> findForcaVendasByCdCorretoraStatusAprovacao(Long cdCorretora) {

		log.info("[findForcaVendasByCdCorretoraStatusAprovacao]");

		List<TbodForcaVenda> forcaVendasEmAprovacao = new ArrayList<TbodForcaVenda>();
		List<ForcaVenda> forcasVendas = new ArrayList<ForcaVenda>();

		try {
			Long cdStatusForcaVenda = AGUARDANDO_APRO.getCodigo();

			forcaVendasEmAprovacao = forcaVendaDao
					.findByTbodStatusForcaVendaCdStatusForcaVendasAndTbodCorretoraCdCorretora(cdStatusForcaVenda,
							cdCorretora);

			if (!forcaVendasEmAprovacao.isEmpty()) {
				for (TbodForcaVenda tbodForcaVenda : forcaVendasEmAprovacao) {
					ForcaVenda forcaVenda = new ForcaVenda();
					forcasVendas.add(adaptEntityToDtoUpdated(tbodForcaVenda, forcaVenda));
				}
			}
		} catch (Exception e) {
			log.error("Erro ao buscar ForcaVenda em aprovacao :: Detalhe: [" + e.getMessage() + "]");
			return new ArrayList<ForcaVenda>();
		}

		return forcasVendas;
	}

	public ForcaVenda adaptEntityToDtoUpdated(TbodForcaVenda tbForcaVenda, ForcaVenda forcaVenda) {
		forcaVenda.setCdForcaVenda(tbForcaVenda.getCdForcaVenda());
		forcaVenda.setAtivo(tbForcaVenda.getAtivo() == "S" ? true : false);
		forcaVenda.setCargo(tbForcaVenda.getCargo());
		forcaVenda.setCelular(tbForcaVenda.getCelular());
		forcaVenda.setCpf(tbForcaVenda.getCpf());

		forcaVenda.setDepartamento(tbForcaVenda.getDepartamento());
		forcaVenda.setEmail(tbForcaVenda.getEmail());
		forcaVenda.setNome(tbForcaVenda.getNome());
		forcaVenda.setStatusForcaVenda(tbForcaVenda.getTbodStatusForcaVenda().getDescricao());

		String dataStr = "00/00/0000";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			dataStr = sdf.format(tbForcaVenda.getDataNascimento());
		} catch (Exception e) {
			log.error("Erro ao formatar data de nascimento :: Detalhe: [" + e.getMessage() + "]");
		}
		forcaVenda.setDataNascimento(dataStr);

		Corretora corretora = new Corretora();
		corretora.setCdCorretora(tbForcaVenda.getTbodCorretora().getCdCorretora());
		corretora.setCnpj(tbForcaVenda.getTbodCorretora().getCnpj());
		corretora.setCnae(tbForcaVenda.getTbodCorretora().getCnae());
		corretora.setRazaoSocial(tbForcaVenda.getTbodCorretora().getRazaoSocial());
		corretora.setTelefone(tbForcaVenda.getTbodCorretora().getTelefone());
		corretora.setCelular(tbForcaVenda.getTbodCorretora().getCelular());
		corretora.setEmail(tbForcaVenda.getTbodCorretora().getEmail());
		corretora.setStatusCnpj(tbForcaVenda.getTbodCorretora().getStatusCnpj() == "S" ? true : false);
		corretora.setSimplesNacional(tbForcaVenda.getTbodCorretora().getSimplesNacional() == "S" ? true : false);
		corretora.setDataAbertura(tbForcaVenda.getTbodCorretora().getDataAbertura());
		
		Endereco enderecoCorretora = new Endereco();
		enderecoCorretora.setLogradouro(tbForcaVenda.getTbodCorretora().getTbodEndereco().getLogradouro());
		enderecoCorretora.setNumero(tbForcaVenda.getTbodCorretora().getTbodEndereco().getNumero());
		enderecoCorretora.setComplemento(tbForcaVenda.getTbodCorretora().getTbodEndereco().getComplemento());
		enderecoCorretora.setBairro(tbForcaVenda.getTbodCorretora().getTbodEndereco().getBairro());
		enderecoCorretora.setCidade(tbForcaVenda.getTbodCorretora().getTbodEndereco().getCidade());
		enderecoCorretora.setEstado(tbForcaVenda.getTbodCorretora().getTbodEndereco().getUf());
		enderecoCorretora.setCep(tbForcaVenda.getTbodCorretora().getTbodEndereco().getCep());
		corretora.setEnderecoCorretora(enderecoCorretora);
		
		
		forcaVenda.setCorretora(corretora);
		return forcaVenda;
	}
}
