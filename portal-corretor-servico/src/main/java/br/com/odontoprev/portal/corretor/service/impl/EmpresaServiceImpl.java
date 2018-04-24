package br.com.odontoprev.portal.corretor.service.impl;

import java.text.ParseException;
import java.util.List;

import javax.swing.text.MaskFormatter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.odontoprev.portal.corretor.business.BeneficiarioBusiness;
import br.com.odontoprev.portal.corretor.business.EmpresaBusiness;
import br.com.odontoprev.portal.corretor.dao.EmpresaDAO;
import br.com.odontoprev.portal.corretor.dto.CnpjDados;
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
				
				if(vidas != null && !vidas.isEmpty()) {
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

	@Override
	public CnpjDados findDadosEmpresaByCnpj(String cnpj) throws ParseException {
		
		log.info("#### findDadosEmpresaByCnpj ####");
		
		MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
		mask.setValueContainsLiteralCharacters(false);
		
		TbodEmpresa tbodEmpresa = null;		
		CnpjDados cnpjDados = new CnpjDados();		
		
		try {
			
			if ( cnpj.length() == 14) {
				
				tbodEmpresa = empresaDao.findByCnpj(mask.valueToString(cnpj));
								
				if(tbodEmpresa != null) {
					cnpjDados.setCdEmpresa(tbodEmpresa.getCdEmpresa());
					cnpjDados.setRazaoSocial(tbodEmpresa.getRazaoSocial());
					cnpjDados.setCnpj(cnpj);				
				} else {				
					tbodEmpresa = empresaDao.findByCnpj(cnpj);
					if(tbodEmpresa == null) {
						cnpjDados.setObservacao("Cnpj não encontrado na base!!!");
					} else {
						cnpjDados.setCdEmpresa(tbodEmpresa.getCdEmpresa());
						cnpjDados.setRazaoSocial(tbodEmpresa.getRazaoSocial());
						cnpjDados.setCnpj(cnpj);
					}						
				}
			} else {
				cnpjDados.setObservacao("Cnpj é obrigatório informar 14 digitos!!!");
			}
			
		} catch (Exception e) {
			cnpjDados.setObservacao("Encontrado +1 cnpj na base!!!");
			return cnpjDados;
		}
		
		return cnpjDados;	
	}

}
