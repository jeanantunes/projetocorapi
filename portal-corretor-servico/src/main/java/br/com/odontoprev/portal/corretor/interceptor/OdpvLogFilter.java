package br.com.odontoprev.portal.corretor.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.odontoprev.portal.corretor.service.OdpvAuditorService;

//h t t p s :// howtodoinjava .com /servlets /httpservletrequestwrapper-example-read-request-body/
//201806052017 - esert - interceptor por filter
public class OdpvLogFilter implements Filter {
	 
	private static final Log log = LogFactory.getLog(OdpvLogFilter.class);
	
    private static final String INDEX_DIR = "c:/temp/cache";
    private FilterConfig filterConfig = null;
    
    @Autowired
	OdpvAuditorService odpvAuditor;
 
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
    	log.info("[doFilter] ini");
 
    	HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
    	RequestWrapper requestWrapper = new RequestWrapper(httpServletRequest);
 
        HttpServletRequestWrapper httpServletRequestWrapper = 
        		new HttpServletRequestWrapper((HttpServletRequest) servletRequest);
        
        String stringRequestURI = httpServletRequestWrapper.getRequestURI();

        //Read request.getBody() as many time you need
        String stringJsonBody = ((RequestWrapper) requestWrapper).getBody();
        
        //Principal userPrincipal = httpServletRequestWrapper.getUserPrincipal();
        //String stringParams = LoggerInterceptor.getParameters(httpServletRequest);
        //String stringUserAgent = ((RequestWrapper) requestWrapper).getParameter("User-Agent");
        String stringUserAgent = ((RequestWrapper) requestWrapper).getHeader("User-Agent");
        
        log.info("[doFilter] stringRequestURI:[" + stringRequestURI + "]"); //201806071209
        log.info("[doFilter] stringUserAgent:[" + stringUserAgent + "]"); //201806071209
        log.info("[doFilter] request.getBody():[" + stringJsonBody + "]");
        
        odpvAuditor.audit(stringRequestURI, stringJsonBody, stringUserAgent); //201806052021 - esert
        ////////Auditor.audit(stringBody); //201806052021 - esert
 
        chain.doFilter(requestWrapper, servletResponse);
        
    	log.info("[doFilter] fim");
    }
 
    public void init(FilterConfig filterConfiguration) throws ServletException {
        this.filterConfig = filterConfiguration;
    }
 
    public void destroy() {
        this.filterConfig = null;
    }
}