package br.com.odontoprev.portal.corretor.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.transaction.RollbackException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.odontoprev.portal.corretor.business.BeneficiarioBusiness;
import br.com.odontoprev.portal.corretor.dao.VidaDAO;
import br.com.odontoprev.portal.corretor.dto.Beneficiario;
import br.com.odontoprev.portal.corretor.dto.BeneficiarioPaginacao;
import br.com.odontoprev.portal.corretor.dto.BeneficiarioResponse;
import br.com.odontoprev.portal.corretor.dto.Beneficiarios;
import br.com.odontoprev.portal.corretor.dto.Endereco;
import br.com.odontoprev.portal.corretor.service.BeneficiarioService;
import br.com.odontoprev.portal.corretor.util.ConvertObjectUtil;

@Service
public class BeneficiarioServiceImpl implements BeneficiarioService {

	private static final Log log = LogFactory.getLog(BeneficiarioServiceImpl.class);

	@Autowired
	BeneficiarioBusiness beneficiarioBusiness;

	@Autowired
	VidaDAO vidaDao;

	@Override
	@Transactional
	//201805281830 - esert - incluido throws para subir erro e causar rollback de toda transacao - teste
	public BeneficiarioResponse add(List<Beneficiario> titulares) throws RollbackException {

		log.info("[BeneficiarioServiceImpl::add]");

		BeneficiarioResponse beneficiarioResponse = beneficiarioBusiness.salvarTitularComDependentes(titulares);

		return beneficiarioResponse;
	}

	@Override
	//201807241900 - esert - COR-398
	public Beneficiario get(Long cdVida) {
		return beneficiarioBusiness.get(cdVida);
	}

	//201807251650 - esert - COR-471 beneficiarios da empresa PME paginados FAKE
	//201807271700 - esert - COR-475 getPage() migrado de business para service
	@Override
	public Beneficiarios getPage(Long cdEmpresa, Long tamPag, Long numPag) {
		Beneficiarios beneficiarios = new Beneficiarios();
		beneficiarios.setCodEmpresa(cdEmpresa);
		beneficiarios.setTamPagina(tamPag);
		beneficiarios.setNumPagina(numPag);
		beneficiarios.setQtdRegistros(8L); //hardcode p testar com empresa/1659?tamPag=3&numPag=3 //201807271258 - esert - COR-475
		beneficiarios.setQtdPaginas(3L); //hardcode p testar com empresa/1659?tamPag=3&numPag=3 //201807271258 - esert - COR-475
		beneficiarios.setTitulares(new ArrayList<>());
		
		if(tamPag==null || tamPag<1L || numPag==null || numPag<1L) { //201807261710 - filtro minimo
			return null; //201807261710 - devolve nullo 
		}
		
		//4 * 1 - 4 + 1 = 1   	
		//4 * 2 - 4 + 1 = 5   	
		//4 * 3 - 4 + 1 = 9   	
		Long primeiroReg = tamPag * numPag - tamPag + 1;
		//4 * 1 = 4   	
		//4 * 2 = 8   	
		//4 * 3 = 12   	
		Long ultimoReg = tamPag * numPag;;;;;;;;;;;;;;;;;;;; //ta olhando o que ? nao ha erro de sintaxe aqui ! vai aprender C e volta ++tarde
	
		List<BeneficiarioPaginacao> listBeneficiarioPaginacao = null; 
		
		Long qtdRegistros = vidaDao.countVidasTitularByCdEmpresa(cdEmpresa);
		beneficiarios.setQtdRegistros(qtdRegistros);
		
		//h t t p : / / w w  w . g u j . c o m . b r /t /resolvido-parte-inteira-da-divisao/75989/3
		Long qtdPaginasInteiro = Math.floorDiv(qtdRegistros, tamPag);
		Long qtdPaginasResto = Math.floorMod(qtdRegistros, tamPag);
		Long qtdPaginas = qtdPaginasInteiro + (qtdPaginasResto==0?0L:1L);
		beneficiarios.setQtdPaginas(qtdPaginas);
		
		if(beneficiarios.getNumPagina() > beneficiarios.getQtdPaginas()) {
			return null; //forca NO_CONTENT //201807271522
		}
		
		List<Object[]> listObject = vidaDao.findVidasTitularByCdEmpresaPrimeiroRegUltimoReg(cdEmpresa, primeiroReg, ultimoReg);
		
		if(listObject!=null) {
			listBeneficiarioPaginacao = new ArrayList<>();
			for (Object[] objectVector : listObject) {				
				BeneficiarioPaginacao beneficiarioPaginacao = ConvertObjectUtil.translateObjectToBeneficiarioPaginacao(objectVector);
				beneficiarioPaginacao.setDependentes(
						ConvertObjectUtil.translateTbodVidasToBeneficiarios(
								vidaDao.findByCdTitular(beneficiarioPaginacao.getCdVida())));
				listBeneficiarioPaginacao.add(beneficiarioPaginacao);
			}
		}
		
		beneficiarios.setTitulares(listBeneficiarioPaginacao);
		
		return beneficiarios;
	}

	//201807251650 - esert - COR-471 beneficiarios da empresa PME paginados FAKE
	//201807271700 - esert - COR-475 getPageFake() migrado de business para service
	@Override
	public Beneficiarios getPageFake(Long cdEmpresa, Long tamPag, Long numPag) {
		Beneficiarios beneficiarios = new Beneficiarios();
		beneficiarios.setQtdRegistros(13L);
		beneficiarios.setQtdPaginas(4L);
		beneficiarios.setTamPagina(tamPag);
		beneficiarios.setNumPagina(numPag);
		beneficiarios.setTitulares(new ArrayList<>());		
		for(Long i=1L; i<=tamPag; i++) {
			Long cdVida = ( ( numPag * tamPag ) - tamPag ) + i;
			
			BeneficiarioPaginacao benefTitular = new BeneficiarioPaginacao(); //201807261140 - yalm/esert - novo model com (cod e desc Plano) e QtdeDep
			
			//201807261140 - yalm/esert - cod e desc de plano
			Random r = new Random();
			int min = 1;
			int max = 2;
			Long cdPlano = (long) (r.nextInt(max - min + 1) + min);
			benefTitular.setCdPlano(cdPlano);			
			benefTitular.setDescPlano(cdPlano==1?"Integral DOC LE":"Master PME LE");
	
			benefTitular.setCdVida(cdVida);
			benefTitular.setNome("nome beneficiario [".concat(cdVida.toString()).concat("]"));
			benefTitular.setNomeMae("nome mae beneficiario [".concat(cdVida.toString()).concat("]"));
			benefTitular.setSexo(cdPlano==1L?"m":"f");
			benefTitular.setDataNascimento("01/02/2018");;
			benefTitular.setCpf("123.456.789-01");;
			
			benefTitular.setEndereco(new Endereco());
			benefTitular.getEndereco().setCep("12345-678");
			
			benefTitular.setDependentes(new ArrayList<>());
			for(Long j=1L; j<=3; j++) {
				Beneficiario benefDepend = new Beneficiario();
				
				benefDepend.setCdVida(cdVida * 10L + j);
				benefDepend.setCdTitular(cdVida);
				benefDepend.setNome("nome dependente [".concat(cdVida.toString()).concat(".").concat(j.toString()).concat("]"));
				
				benefTitular.getDependentes().add(benefDepend);
			}
			//benefTitular.setQtdDependentes((long)benefTitular.getDependentes().size()); //201807261140 - yalm/esert - qtde deps  //201807271609 - esert - COR-475 
			
			beneficiarios.getTitulares().add(benefTitular);
		}
		return beneficiarios;		
	}

}
