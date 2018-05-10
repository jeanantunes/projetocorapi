package br.com.odontoprev.portal.corretor.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.text.MaskFormatter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.odontoprev.portal.corretor.business.BeneficiarioBusiness;
import br.com.odontoprev.portal.corretor.business.EmpresaBusiness;
import br.com.odontoprev.portal.corretor.business.SendMailBoasVindasPME;
import br.com.odontoprev.portal.corretor.dao.EmpresaDAO;
import br.com.odontoprev.portal.corretor.dao.VendaDAO;
import br.com.odontoprev.portal.corretor.dto.CnpjDados;
import br.com.odontoprev.portal.corretor.dto.Empresa;
import br.com.odontoprev.portal.corretor.dto.EmpresaDcms;
import br.com.odontoprev.portal.corretor.dto.EmpresaResponse;
import br.com.odontoprev.portal.corretor.model.TbodEmpresa;
import br.com.odontoprev.portal.corretor.model.TbodVenda;
import br.com.odontoprev.portal.corretor.model.TbodVida;
import br.com.odontoprev.portal.corretor.service.EmpresaService;
import br.com.odontoprev.portal.corretor.util.DataUtil;
import br.com.odontoprev.portal.corretor.util.PropertiesUtils;
import br.com.odontoprev.portal.corretor.util.XlsVidas;

@Service
public class EmpresaServiceImpl implements EmpresaService {

	private static final Log log = LogFactory.getLog(EmpresaServiceImpl.class);

	@Autowired
	EmpresaDAO empresaDao;

	@Autowired
	VendaDAO vendaDao;

	@Autowired
	EmpresaBusiness empresaBusiness;

	@Autowired
	BeneficiarioBusiness beneficiarioBusiness;

	@Autowired
	SendMailBoasVindasPME sendMailBoasVindasPME;

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
				
				//201805091745 - esert
				//201805101609 - esert - criar servico independente para Email Boas Vindas PME vide Fernando@ODPV em 20180510
				//201805101941 - esert - excluido param cnpj
				ResponseEntity<EmpresaDcms> res = this.sendEmailBoasVindasPME(empresaDcms.getCdEmpresa());
				log.info("res:[" + res.toString() + "]");
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

	//201805091745 - esert
	//201805101941 - esert - excluido param cnpj
	//201805101947 - esert - service adaptado para reenvio de emails vide Fernando@ODPV
	@Override
	public ResponseEntity<EmpresaDcms> sendEmailBoasVindasPME(Long cdEmpresa) {
		Long longDiaVencimentoFatura = 0L;
		Date dateDataVenda = null;
		
		try {
			log.info("cdEmpresa:[" + cdEmpresa + "]");
			
			TbodEmpresa tbodEmpresa = empresaDao.findOne(cdEmpresa);
			
			if(tbodEmpresa==null) {
				log.info("tbodEmpresa==null");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			
			if(tbodEmpresa.getEmail()==null) {
				log.info("tbodEmpresa.getEmail()==null");
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).build();
			}
			
			log.info("tbodEmpresa.getEmpDcms():[" + tbodEmpresa.getEmpDcms() + "]");
			
			List<TbodVenda> tbodVendas = vendaDao.findByTbodEmpresaCdEmpresa(cdEmpresa);
			
			if(tbodVendas!=null) {				
				log.info("tbodVendas.size():[" + tbodVendas.size() + "]");				
				for (TbodVenda tbodVenda : tbodVendas) {
					//201805101616 - esert - arbitragem : fica com a ultima venda suposta mais recente
					longDiaVencimentoFatura = tbodVenda.getFaturaVencimento();
					log.info("longDiaVencimentoFatura:[" + longDiaVencimentoFatura + "]");				
					dateDataVenda = tbodVenda.getDtVenda();
					log.info("dateDataVenda:[" + (new SimpleDateFormat("dd/MM/yyyy")).format(dateDataVenda) + "]");				
				}
			} else {
				log.info("tbodVendas==null");				
			}

			log.info("longDiaVencimentoFatura:[" + longDiaVencimentoFatura + "]");				
			log.info("dateDataVenda:[" + (new SimpleDateFormat("dd/MM/yyyy")).format(dateDataVenda) + "]");				

			String strDiaVencimentoFatura = String.valueOf(100L + longDiaVencimentoFatura).substring(1,3);
			log.info("strDiaVencimentoFatura:[" + strDiaVencimentoFatura + "]");				

			//201805101739 - esert - funcao isEffectiveDate copíada do App na versao abaixo porem usa DataVenda ao inves de CurrentDate vide Camila@ODPV
			//http://git.odontoprev.com.br/esteira-digital/est-portalcorretor-app/blob/sprint6/VendasOdontoPrev/app/src/main/assets/app/pmeFaturaController.js
			String strDataVigencia = DataUtil.isEffectiveDate(longDiaVencimentoFatura, dateDataVenda);
			log.info("strDataVigencia:[" + strDataVigencia + "]");				
			
			sendMailBoasVindasPME.sendMail(
				tbodEmpresa.getEmail(), //email, 
				tbodEmpresa.getNomeFantasia(), //nomeCorretora, 
				tbodEmpresa.getEmpDcms(), //login, 
				PropertiesUtils.getProperty(PropertiesUtils.SENHA_INICIAL_PORTAL_PME), //senha, 
				PropertiesUtils.getProperty(PropertiesUtils.LINK_PORTAL_PME_URL), //linkPortal, 
				strDataVigencia, //dataVigencia, 
				strDiaVencimentoFatura //diaVencimentoFatura
				);
			
			EmpresaDcms empresaDcms = new EmpresaDcms();
			empresaDcms.setCdEmpresa(tbodEmpresa.getCdEmpresa());
			empresaDcms.setEmpDcms(tbodEmpresa.getEmpDcms());
			empresaDcms.setCnpj("email:".concat(tbodEmpresa.getEmail())); //QG so para mostrar email de destino no retorno json
			return ResponseEntity.ok(empresaDcms);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}		
		
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
