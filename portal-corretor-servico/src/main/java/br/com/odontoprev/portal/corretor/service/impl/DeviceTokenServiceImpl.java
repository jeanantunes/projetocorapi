package br.com.odontoprev.portal.corretor.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.odontoprev.portal.corretor.dao.DeviceTokenDAO;
import br.com.odontoprev.portal.corretor.dao.ForcaVendaDAO;
import br.com.odontoprev.portal.corretor.dto.DeviceToken;
import br.com.odontoprev.portal.corretor.model.TbodDeviceToken;
import br.com.odontoprev.portal.corretor.model.TbodForcaVenda;
import br.com.odontoprev.portal.corretor.service.DeviceTokenService;

@Service
public class DeviceTokenServiceImpl implements DeviceTokenService {

	//private static final Log log = LogFactory.getLog(DeviceTokenServiceImpl.class);
	
	@Autowired
	private DeviceTokenDAO deviceTokenDAO;
	
	@Autowired 
	private ForcaVendaDAO forcaVendaDAO;
	
	@Override
	public void inserir(DeviceToken device,Long codigoForcaVenda) {
		TbodDeviceToken token = new TbodDeviceToken();			
		token.setModelo(device.getModelo());
		token.setSistemaOperacional(device.getSistemaOperacional());
		token.setToken(device.getToken());
		TbodForcaVenda forcaVenda = forcaVendaDAO.findOne(codigoForcaVenda);						
		token.setLogin(forcaVenda.getTbodLogin());
		deviceTokenDAO.save(token);
		
	}

	@Override
	public List<DeviceToken> buscarPorTokenLogin(String idToken, Long codigoForcaVenda) {				
		return deviceTokenDAO.findByTokenAndLoginTbodForcaVendasCdForcaVenda(idToken, codigoForcaVenda).stream().map((token) -> new DeviceToken(token)).collect(Collectors.toList());		
	}
	
	@Override
	public void atualizar(DeviceToken deviceToken, Long codigoForcaVenda) {
		TbodDeviceToken token = new TbodDeviceToken();	
		token.setCodigo(deviceToken.getCodigo());
		token.setModelo(deviceToken.getModelo());
		token.setSistemaOperacional(deviceToken.getSistemaOperacional());
		token.setToken(deviceToken.getToken());
		TbodForcaVenda forcaVenda = forcaVendaDAO.findOne(codigoForcaVenda);						
		token.setLogin(forcaVenda.getTbodLogin());
		deviceTokenDAO.save(token);
	}
	
	//201808091130 - esert - COR-556 nova rota excluir
	@Override
	public void excluir(Long codigoDeviceToken) {
		deviceTokenDAO.delete(codigoDeviceToken);
	}

}
