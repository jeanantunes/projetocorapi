package br.com.odontoprev.portal.corretor.service.impl;

import br.com.odontoprev.portal.corretor.dto.PushNotification;
import br.com.odontoprev.portal.corretor.exceptions.ApiTokenException;
import br.com.odontoprev.portal.corretor.service.PushNotificationService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class PushNotificationImpl implements PushNotificationService {

    private static final Log log = LogFactory.getLog(PushNotificationImpl.class);

    @Value("${PUSH_NOTIFICATION_URL}")
    private String pushNotificatinUrl;

    @Autowired
    private ApiManagerTokenServiceImpl apiManagerTokenService;


    @Override
    public String envioMensagemPush(PushNotification pushNotification) throws ApiTokenException {
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", "Bearer " + apiManagerTokenService.getToken());

        final RestTemplate restTemplate = new RestTemplate();

//        HttpEntity<?> request = new HttpEntity<Map<String, Object>>(pushNotification.toString(), headers);
//     ResponseEntity response = restTemplate.postForEntity(pushNotificatinUrl + "/push/", request);

        //      return response.getBody().toString();
        return "";
    }
}

