package br.com.odontoprev.portal.corretor.service;

import br.com.odontoprev.portal.corretor.dto.PushNotification;
import br.com.odontoprev.portal.corretor.exceptions.ApiTokenException;

public interface PushNotificationService {

    public String envioMensagemPush(PushNotification pushNotification)throws ApiTokenException;

}
