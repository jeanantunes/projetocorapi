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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PushNotificationImpl implements PushNotificationService {

    private static final Log log = LogFactory.getLog(PushNotificationServiceImpl.class);

    @Value("${PUSH_NOTIFICATION_URL}")
    private String pushNotificatinUrl = "http://localhost:7001/est-pushnotification-api-rs-1.0-SNAPSHOT";



    @Override
    public String envioMensagemPush(PushNotification pushNotification, ApiManagerTokenServiceImpl apiManagerTokenService) throws ApiTokenException {
        HttpHeaders headers = new HttpHeaders();

        // headers.add("Authorization", "Bearer " + apiManagerTokenService.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        final RestTemplate restTemplate = new RestTemplate();

        HttpEntity<?> request = new HttpEntity<>(pushNotification, headers);
        ResponseEntity response = restTemplate.postForEntity(pushNotificatinUrl+"/push", request,String.class);

        return response.getBody().toString();

    }
}

