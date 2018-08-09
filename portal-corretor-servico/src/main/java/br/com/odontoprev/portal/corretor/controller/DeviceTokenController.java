package br.com.odontoprev.portal.corretor.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.odontoprev.portal.corretor.dto.DeviceToken;
import br.com.odontoprev.portal.corretor.service.DeviceTokenService;
import br.com.odontoprev.portal.corretor.service.impl.ApiManagerTokenServiceImpl;

@Controller
public class DeviceTokenController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ApiManagerTokenServiceImpl.class);

	@Autowired
	private DeviceTokenService service;
	
	@RequestMapping(value = "/devicetoken/forcavenda/{codigo}", method = { RequestMethod.POST })	
	public ResponseEntity<BaseResponse> inserirDeviceToken(@PathVariable Long codigo,@RequestBody DeviceToken request) {		
		if(codigo==null) {
			return ResponseEntity.badRequest().body(new BaseResponse("Código usuário não informado!"));
		}
		String mensagens = this.validar(request);		
		if(mensagens != null) {
			return ResponseEntity.badRequest().body(new BaseResponse(mensagens));
		}		
		List<DeviceToken> tokens = service.buscarPorTokenLogin(request.getToken(), codigo);
		if(!tokens.isEmpty()){
			DeviceToken deviceToken = tokens.get(0);
			deviceToken.setDataAtualizacao(new Date());
			service.atualizar(deviceToken,codigo);
			return ResponseEntity.ok().build();
		}
		service.inserir(request,codigo);
		
		return ResponseEntity.ok().build();
	}

	private String validar(DeviceToken request) {
		if(request==null) {
			return "request nao informado";
		}
		if(request.getToken()==null) {
		return "token nao informado";
		}
		if(request.getModelo()==null) {
			return "modelo nao informado";		
		}
		if(request.getSistemaOperacional()==null) {
			return "sistema operacional nao informado";
		}
		return null;
	}

	//201808091130 - esert - COR-556 nova rota excluir
	@RequestMapping(value = "/devicetoken/forcavenda/{codigoForcaVenda}", method = { RequestMethod.DELETE })	
	public ResponseEntity<BaseResponse> excluirDeviceToken(@PathVariable Long codigoForcaVenda,@RequestBody DeviceToken request) {
		LOGGER.info("excluirDeviceToken - ini");
		
		if(codigoForcaVenda==null) {
			LOGGER.info("excluirDeviceToken - badRequest");
			return ResponseEntity.badRequest().body(new BaseResponse("Código Forca Venda não informado!"));
		}	
		
		String mensagens = this.validarExcluir(request);		
		
		if(mensagens != null) {
			LOGGER.info("excluirDeviceToken - badRequest");
			return ResponseEntity.badRequest().body(new BaseResponse(mensagens));
		}		
		List<DeviceToken> tokens = service.buscarPorTokenLogin(request.getToken(), codigoForcaVenda);
		
		
		if(tokens==null || tokens.isEmpty()){
			LOGGER.info("excluirDeviceToken - noContent");
			return ResponseEntity.noContent().build();
		}
		
		service.excluir(tokens.get(0).getCodigo());

		LOGGER.info("excluirDeviceToken - fim");
		return ResponseEntity.ok().build();
	}
	
	private String validarExcluir(DeviceToken request) {
		String ret = "";
		if(request==null) {
			ret += " request nao informado.";
		}
		if(request.getToken()==null) {
			ret += " token nao informado.";
		}
		/*
		if(request.getModelo()==null) {
			ret += " modelo nao informado.";		
		}
		if(request.getSistemaOperacional()==null) {
			ret += "sistema operacional nao informado.";
		}
		*/
		return ret.isEmpty()?null:ret;
	}

}
