package br.com.odontoprev.portal.corretor.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import br.com.odontoprev.portal.corretor.dao.ForcaVendaDAO;
import br.com.odontoprev.portal.corretor.dto.PropostaDCMS;
import br.com.odontoprev.portal.corretor.dto.PropostaDCMSResponse;
import br.com.odontoprev.portal.corretor.interceptor.LoggerInterceptor;
import br.com.odontoprev.portal.corretor.service.DCSSService;
import br.com.odontoprev.portal.corretor.service.OdpvAuditorService;

//201810171900 - esert - inc - COR-763:Isolar Inserção JSON Request DCMS
@Service
//@Component //201810191155 - esert - exc - teste - COR-763:Isolar Inserção JSON Request DCMS
@Transactional(noRollbackFor = {Exception.class}) //201810181226 - esert - COR-763:Isolar Inserção JSON Request DCMS
public class DCSSServiceImpl implements DCSSService {

	private static final Logger log = LoggerFactory.getLogger(DCSSServiceImpl.class);

	@Value("${DCSS_VENDAS_PROPOSTA_URL}")
	//201810031800 - esert - COR-852:Alterar Request Angariador Dados nao Obrigatorios - segregar rota de login da rota de proposta para desv e teste
	private String dcss_venda_propostaUrl; //201810031800 - esert - COR-852:Alterar Request Angariador Dados nao Obrigatorios - segregar rota de login da rota de proposta para desv e teste
	
	@Value("${DCSS_VENDAS_PROPOSTA_PATH}")
	private String dcss_venda_propostaPath;
	
	@Autowired
	ApiManagerTokenServiceImpl apiManagerTokenService;

    @Autowired
    ForcaVendaDAO forcaVendaDao;

	@Autowired
	OdpvAuditorService odpvAuditor; //201806071601 - esert - log do json enviado ao dcms - solic fsetai

    
    //@SuppressWarnings({})
    //@Transactional(rollbackFor = {Exception.class}) //201806290926 - esert - COR-352 rollback pf
	//@Transactional(noRollbackFor = {Exception.class}) //201810181226 - esert - COR-763:Isolar Inserção JSON Request DCMS
	//@Transactional(isolation=Isolation.DEFAULT) //201810181300 - esert - COR-763:Isolar Inserção JSON Request DCMS
    public PropostaDCMSResponse chamarWSLegadoPropostaPOST(PropostaDCMS propostaDCMS) {
        log.info("chamarWSLegadoPropostaPOST - ini");
        PropostaDCMSResponse propostaDCMSResponse = new PropostaDCMSResponse();
//		ApiManagerToken apiManager = null;
//		ApiToken apiToken = null;
        ResponseEntity<PropostaDCMSResponse> response = null;
        final RestTemplate restTemplate = new RestTemplate();
        String msgErro = "";

        try {
            String URLAPI = dcss_venda_propostaUrl + dcss_venda_propostaPath; //201810031800 - esert - COR-852:Alterar Request Angariador Dados nao Obrigatorios - segregar rota de login da rota de proposta para desv e teste

//			apiManager = ApiManagerTokenFactory.create(ApiManagerTokenEnum.WSO2, "PORTAL_CORRETOR_SERVICO");
//			apiToken = apiManager.generateToken();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + apiManagerTokenService.getToken());
            headers.add("Cache-Control", "no-cache");
            //headers.add("Content-Type", "application/x-www-form-urlencoded");
            headers.add("Content-Type", "application/json");

            Gson gson = new Gson();
            String propostaJson = gson.toJson(propostaDCMS);
            //log.info("propostaJson:[" + propostaJson + "];");
            //odpvAuditor.audit(dcss_venda_propostaPath, propostaJson, "VendaPFBusiness.chamarWsDcssLegado()"); //201806071601 - esert - log do json enviado ao dcms - solic fsetai
            odpvAuditor.audit(URLAPI, null, propostaJson, "VendaPFBusiness.chamarWsDcssLegado", LoggerInterceptor.getHeaders(headers), null); //201806291203 - esert - log do json com headers

            HttpEntity<?> request = new HttpEntity<PropostaDCMS>(propostaDCMS, headers);

            /*
            response = restTemplate.exchange(
                    URLAPI,
                    HttpMethod.POST,
                    request,
                    PropostaDCMSResponse.class);
            */

            PropostaDCMSResponse propostaDCMSResponseFAKE = new PropostaDCMSResponse(); //201810171713 - esert - COR-763 - FAKE RESPOSTA DA CHAMADA POST AO DCMS 999999
            propostaDCMSResponseFAKE.setNumeroProposta("999999"); //201810171713 - esert - COR-763 - FAKE RESPOSTA DA CHAMADA POST AO DCMS 999999
            propostaDCMSResponseFAKE.setMensagemErro("FAKE RESPOSTA DA CHAMADA POST AO DCMS 999999"); //201810171713 - esert - COR-763 - FAKE RESPOSTA DA CHAMADA POST AO DCMS 999999
            response = ResponseEntity.ok(propostaDCMSResponseFAKE); //201810171713 - esert - COR-763 - FAKE RESPOSTA DA CHAMADA POST AO DCMS 999999
            

            if (response != null) {
                log.info("chamarWSLegadoPropostaPOST; propostaRet.getStatusCode():[" + response.getStatusCode() + "];");
            }

            if (response == null
                    || (response.getStatusCode() == HttpStatus.FORBIDDEN)
                    || (response.getStatusCode() == HttpStatus.BAD_REQUEST)
            ) {
                msgErro = "chamarWSLegadoPropostaPOST; HTTP_STATUS " + response.getStatusCode();
                log.error(msgErro);
                propostaDCMSResponse.setMensagemErro(msgErro);
                return propostaDCMSResponse;
            }

//		} catch (CredentialsInvalidException e1) {
//			msgErro = "chamarWSLegadoPropostaPOST; CredentialsInvalidException.getMessage():[" + e1.getMessage() + "];";
//			log.error(msgErro);
//			propostaDCMSResponse.setMensagemErro(msgErro);
//			return propostaDCMSResponse;
//		} catch (URLEndpointNotFound e1) {
//			msgErro = "chamarWSLegadoPropostaPOST; URLEndpointNotFound.getMessage():[" + e1.getMessage() + "];";
//			log.error(msgErro);
//			propostaDCMSResponse.setMensagemErro(msgErro);
//			return propostaDCMSResponse;
//		} catch (ConnectionApiException e) {
//			msgErro = "chamarWSLegadoPropostaPOST; ConnectionApiException.getMessage():[" + e.getMessage() + "];";
//			log.error(msgErro);
//			propostaDCMSResponse.setMensagemErro(msgErro);
//			//return propostaDCMSResponse;
//			throw e;			
//		} catch (RestClientException e) {
//			msgErro = "chamarWSLegadoPropostaPOST; RestClientException.getMessage():[" + e.getMessage() + "];";
//			log.error(msgErro);
//			propostaDCMSResponse.setMensagemErro(msgErro);
//			//return propostaDCMSResponse;
//			throw e; //201806291524 - esert - se o DCMS falhar deve fazer rollback - COR-352 rollback pf
//			//e.printStackTrace();
//		} catch (ApiTokenException e2) {
//			// TODO Auto-generated catch block
//			//e.printStackTrace();
//			throw e2;
        } catch (Exception e) {
            msgErro = "chamarWSLegadoPropostaPOST; Exception.getMessage():[" + e.getMessage() + "];";
            log.error(msgErro);
            //e.printStackTrace();
            propostaDCMSResponse.setMensagemErro(msgErro);

            //throw new RollbackException(msgErro); //201806291524 - esert - se o DCMS falhar deve fazer rollback - COR-352 rollback pf

            ////201808021330 - fake
            ////201809131714 - fake - novo teste apos aplicar/merge do COR-736 no sprint13 - esert
            //propostaDCMSResponse.setMensagemErro(propostaDCMSResponse.getMensagemErro().concat(";fake-999999"));
            //propostaDCMSResponse.setNumeroProposta("999999");
            
            log.info("chamarWSLegadoPropostaPOST; Exception;");
            return propostaDCMSResponse;
        }
        propostaDCMSResponse = response.getBody();

        log.info("chamarWSLegadoPropostaPOST; fim;");
        return propostaDCMSResponse;
    }	
}
