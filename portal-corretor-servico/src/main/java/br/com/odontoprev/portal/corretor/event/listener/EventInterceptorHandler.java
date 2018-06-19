package br.com.odontoprev.portal.corretor.event.listener;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import br.com.odontoprev.portal.corretor.event.processor.ProcessorEventThread;

public class EventInterceptorHandler extends HandlerInterceptorAdapter {		
	
    @Autowired
    private ProcessorEventThread threadBean;
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {			
		threadBean.setRequest(request);
		threadBean.start();
		super.postHandle(request, response, handler, modelAndView);
	}

}
