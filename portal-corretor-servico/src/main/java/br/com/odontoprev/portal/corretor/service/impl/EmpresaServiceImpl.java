package br.com.odontoprev.portal.corretor.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.odontoprev.portal.corretor.business.BeneficiarioBusiness;
import br.com.odontoprev.portal.corretor.business.EmpresaBusiness;
import br.com.odontoprev.portal.corretor.dao.EmpresaDAO;
import br.com.odontoprev.portal.corretor.dto.Empresa;
import br.com.odontoprev.portal.corretor.dto.EmpresaDcms;
import br.com.odontoprev.portal.corretor.dto.EmpresaResponse;
import br.com.odontoprev.portal.corretor.model.TbodEmpresa;
import br.com.odontoprev.portal.corretor.model.TbodVida;
import br.com.odontoprev.portal.corretor.service.EmpresaService;
import br.com.odontoprev.portal.corretor.util.XlsVidas;

@Service
public class EmpresaServiceImpl implements EmpresaService {

	private static final Log log = LogFactory.getLog(EmpresaServiceImpl.class);

	@Autowired
	EmpresaDAO empresaDao;

	@Autowired
	EmpresaBusiness empresaBusiness;

	@Autowired
	BeneficiarioBusiness beneficiarioBusiness;

	@Override
	@Transactional
	public EmpresaResponse add(Empresa empresa) {

		log.info("[EmpresaServiceImpl::add]");

		return empresaBusiness.salvarEmpresaEnderecoVenda(empresa);
	}

	@Override
	@Transactional
	public EmpresaResponse updateEmpresa(EmpresaDcms empresaDcms) {

		log.info("[EmpresaServiceImpl::updateEmpresa]");
		
		TbodEmpresa tbEmpresa = new TbodEmpresa();

		try {
			
			if((empresaDcms.getCdEmpresa() == null)
					|| (empresaDcms.getCnpj() == null || empresaDcms.getCnpj().isEmpty()) 
					|| (empresaDcms.getEmpDcms() == null || empresaDcms.getEmpDcms().isEmpty())) {
				throw new Exception("Os parametros sao obrigatorios!");
			}

			tbEmpresa = empresaDao.findBycdEmpresaAndCnpj(empresaDcms.getCdEmpresa(), empresaDcms.getCnpj());

			if (tbEmpresa != null) {
				tbEmpresa.setEmpDcms(empresaDcms.getEmpDcms());
				empresaDao.save(tbEmpresa);
				
				List<TbodVida> vidas = beneficiarioBusiness.buscarVidasPorEmpresa(tbEmpresa.getCdEmpresa());
				
				if(vidas != null) {
					XlsVidas xlsVidas = new XlsVidas();
					xlsVidas.gerarVidasXLS(vidas, tbEmpresa);
				}
			}
			else {
				throw new Exception("CdEmpresa nao relacionado com CNPJ!");
			}

		} catch (Exception e) {
			log.error("EmpresaServiceImpl :: Erro ao atualizar empresaDcms. Detalhe: [" + e.getMessage() + "]");
			return new EmpresaResponse(0, "Erro ao cadastrar empresaDcms. Favor, entre em contato com o suporte. Detalhe: [" + e.getMessage() + "]");
		}

		return new EmpresaResponse(tbEmpresa.getCdEmpresa(), "Empresa atualizada.");

	}

}
