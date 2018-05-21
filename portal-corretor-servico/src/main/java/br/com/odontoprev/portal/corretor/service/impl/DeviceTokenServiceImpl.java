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

	
	@Autowired
	private DeviceTokenDAO dao;
	
	@Autowired 
	private ForcaVendaDAO vendaDAO;
	
	@Override
	public void inserir(DeviceToken device,Long codigoForcaVenda) {
		TbodDeviceToken token = new TbodDeviceToken();			
		token.setModelo(device.getModelo());
		token.setSistemaOperacional(device.getSistemaOperacional());
		token.setToken(device.getToken());
		TbodForcaVenda forcaVenda = vendaDAO.findOne(codigoForcaVenda);						
		token.setLogin(forcaVenda.getTbodLogin());
		dao.save(token);
		
	}

	@Override
	public List<DeviceToken> buscarPorTokenLogin(String idToken, Long codigoForcaVenda) {				
		return dao.findByTokenAndLoginTbodForcaVendasCdForcaVenda(idToken, codigoForcaVenda).stream().map((token) -> new DeviceToken(token)).collect(Collectors.toList());		
	}
	
	@Override
	public void atualizar(DeviceToken deviceToken, Long codigoForcaVenda) {
		TbodDeviceToken token = new TbodDeviceToken();	
		token.setCodigo(deviceToken.getCodigo());
		token.setModelo(deviceToken.getModelo());
		token.setSistemaOperacional(deviceToken.getSistemaOperacional());
		token.setToken(deviceToken.getToken());
		TbodForcaVenda forcaVenda = vendaDAO.findOne(codigoForcaVenda);						
		token.setLogin(forcaVenda.getTbodLogin());
		dao.save(token);
	}

}
