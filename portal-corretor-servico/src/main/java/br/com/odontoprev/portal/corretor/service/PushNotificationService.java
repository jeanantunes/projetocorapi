package br.com.odontoprev.portal.corretor.service;

import br.com.odontoprev.portal.corretor.dto.PushNotification;
import br.com.odontoprev.portal.corretor.exceptions.ApiTokenException;
import br.com.odontoprev.portal.corretor.service.impl.ApiManagerTokenServiceImpl;

public interface PushNotificationService {

    public String envioMensagemPush(PushNotification pushNotification, ApiManagerTokenServiceImpl apiManagerTokenService)throws ApiTokenException;

}
