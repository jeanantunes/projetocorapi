package br.com.odontoprev.portal.corretor.service;

import java.util.List;

import br.com.odontoprev.portal.corretor.dto.DeviceToken;

public interface DeviceTokenService {

	public void inserir(DeviceToken device, Long codigoLogin);	
	public List<DeviceToken> buscarPorTokenLogin(String idToken, Long codigoLogin); 
}
