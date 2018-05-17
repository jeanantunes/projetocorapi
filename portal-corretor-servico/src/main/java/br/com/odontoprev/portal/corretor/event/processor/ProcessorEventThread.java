package br.com.odontoprev.portal.corretor.event.processor;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ProcessorEventThread extends Thread {

	
	private HttpServletRequest request;
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessorEventThread.class);
	
	@Override
	public void run() {
		
		LOGGER.info(request.getMethod());
		LOGGER.info(request.getRequestURI());
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	
	
	

}
