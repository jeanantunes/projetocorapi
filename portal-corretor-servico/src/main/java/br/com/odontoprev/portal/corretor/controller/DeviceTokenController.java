package br.com.odontoprev.portal.corretor.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.odontoprev.portal.corretor.dto.DeviceToken;
import br.com.odontoprev.portal.corretor.service.DeviceTokenService;

@Controller
public class DeviceTokenController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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

}
