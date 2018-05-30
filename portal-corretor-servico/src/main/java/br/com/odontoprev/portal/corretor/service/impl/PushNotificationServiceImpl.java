package br.com.odontoprev.portal.corretor.service.impl;

import br.com.odontoprev.api.manager.client.token.util.ConfigurationUtils;
import br.com.odontoprev.portal.corretor.dto.PushNotification;
import br.com.odontoprev.portal.corretor.exceptions.ApiTokenException;
import br.com.odontoprev.portal.corretor.service.PushNotificationService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PushNotificationServiceImpl implements PushNotificationService {

    private static final Log log = LogFactory.getLog(PushNotificationServiceImpl.class);

    @Value("${PUSH_NOTIFICATION_URL}")
    private String pushNotificatinUrl;

    ApiManagerTokenServiceImpl apiManagerTokenService;


    @Override
    public String envioMensagemPush(PushNotification pushNotification) throws ApiTokenException {
        HttpHeaders headers = new HttpHeaders();
        apiManagerTokenService =  new ApiManagerTokenServiceImpl();
        headers.add("Authorization", "Bearer " + apiManagerTokenService.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        final RestTemplate restTemplate = new RestTemplate();

        HttpEntity<?> request = new HttpEntity<>(pushNotification, headers);
        ResponseEntity response = restTemplate.postForEntity(
                ConfigurationUtils.getURLGetToken()
                        .replaceAll("/token", "/pushnotification/1.0")+"/push",
                request,
                String.class);

        return response.getBody().toString();

    }
}

