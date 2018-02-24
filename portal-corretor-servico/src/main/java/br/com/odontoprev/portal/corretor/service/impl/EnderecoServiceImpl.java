package br.com.odontoprev.portal.corretor.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.dao.EnderecoDAO;
import br.com.odontoprev.portal.corretor.dao.TipoEnderecoDAO;
import br.com.odontoprev.portal.corretor.dto.Endereco;
import br.com.odontoprev.portal.corretor.model.TbodEndereco;
import br.com.odontoprev.portal.corretor.model.TbodTipoEndereco;
import br.com.odontoprev.portal.corretor.service.EnderecoService;

@Service
public class EnderecoServiceImpl implements EnderecoService {

	private static final Log log = LogFactory.getLog(ForcaVendaServiceImpl.class);

	@Autowired
	EnderecoDAO enderecoDao;

	@Autowired
	TipoEnderecoDAO tipoEnderecoDao;

	@Override
	public TbodEndereco addEndereco(Endereco endereco) {

		log.info("[EnderecoServiceImpl::addEndereco]");

		TbodEndereco tbEndereco = new TbodEndereco();

		try {
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

		} catch (Exception e) {
			log.error("EnderecoServiceImpl :: Erro ao cadastrar Endereco. Detalhe: [" + e.getMessage() + "]");
			return tbEndereco;
		}

		return tbEndereco;
	}

}
