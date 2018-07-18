package br.com.odontoprev.portal.corretor.test.controller.devicetoken;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

import br.com.odontoprev.portal.corretor.controller.DeviceTokenController;
import br.com.odontoprev.portal.corretor.dto.DeviceToken;
import br.com.odontoprev.portal.corretor.service.DeviceTokenService;


@RunWith(SpringRunner.class)
@WebMvcTest(DeviceTokenController.class)
//@ComponentScan("br.com.odontoprev.portal.corretor.test.controller.devicetoken")
@ContextConfiguration(classes = { MockitoServiceProvider.class })
public class DeviceTokenControllerTest {
	
	   @Autowired
	   private MockMvc mvc;

	   @MockBean
	   private DeviceTokenController deviceControllerMock;	   
	   
	   @Autowired	
	   private DeviceTokenService service;

	   @Test
	   public void testInclusaoRotaOK() throws Exception {
	       DeviceToken deviceToken = new DeviceToken();
	       Long codigoForcaVenda = 0L;
	       	       
	       given(deviceControllerMock.inserirDeviceToken(codigoForcaVenda, deviceToken)).willReturn(ResponseEntity.ok().build());

	       mvc.perform(post("/devicetoken/forcavenda/0").content(new Gson().toJson(deviceToken))	               
	               .contentType(APPLICATION_JSON))
	               .andExpect(status().isOk());
	   }
	   
	   @Test
	   public void testInclusaoRotaBadRequest() throws Exception {
	       DeviceToken deviceToken = new DeviceToken();
	       Long codigoForcaVenda = 0L;
	       	       
	       given(deviceControllerMock.inserirDeviceToken(codigoForcaVenda, deviceToken)).willReturn(ResponseEntity.ok().build());

	       mvc.perform(post("/devicetoken/forcavenda/0")	               
	               .contentType(APPLICATION_JSON))
	               .andExpect(status().isBadRequest());
	   }
	   
	   
	   @Test
	   public void testInclusaoRotaNotFound() throws Exception {
	       DeviceToken deviceToken = new DeviceToken();
	       Long codigoForcaVenda = 0L;
	       	       
	       given(deviceControllerMock.inserirDeviceToken(codigoForcaVenda, deviceToken)).willReturn(ResponseEntity.ok().build());

	       mvc.perform(post("/devicetoken/forcavenda2/0")	               
	               .contentType(APPLICATION_JSON))
	               .andExpect(status().isNotFound());
	   }
	   
	   
	  /* @Test
	   public void testInclusaoRotaLogica() throws Exception {
	       DeviceToken deviceToken = new DeviceToken();
	       Long codigoForcaVenda = 0L;	       	       	    
	       	       
	       verify(service,times(1)).buscarPorTokenLogin(Mockito.any(), Mockito.any());
	       given(service.buscarPorTokenLogin(deviceToken.getToken(), codigoForcaVenda)).willReturn(Arrays.asList(new DeviceToken()));
	       
	       mvc.perform(post("/devicetoken/forcavenda/0").content(new Gson().toJson(deviceToken))	               
	               .contentType(APPLICATION_JSON))
	               .andExpect(status().isOk());
	               
	       
	   }*/
}
