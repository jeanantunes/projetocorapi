package br.com.odontoprev.portal.corretor.service;

import java.util.List;

import br.com.odontoprev.portal.corretor.dto.DeviceToken;

public interface DeviceTokenService {

	public void inserir(DeviceToken device, Long codigoForcaVenda);	
	public List<DeviceToken> buscarPorTokenLogin(String idToken, Long codigoLogin);
	public void atualizar(DeviceToken deviceToken,Long codigoForcaVenda);
	public void excluir(Long codigo); //201808091130 - esert - COR-556 nova rota excluir 
}
