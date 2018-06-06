package br.com.odontoprev.portal.corretor.interceptor;

import java.io.IOException;
import java.security.Principal;

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

//h t t p s :// howtodoinjava .com /servlets /httpservletrequestwrapper-example-read-request-body/
//201806052017 - esert - interceptor
public class OdpvLogFilter implements Filter {
	 
	private static final Log log = LogFactory.getLog(LoggerInterceptor.class);
    private static final String INDEX_DIR = "c:/temp/cache";
 
    private FilterConfig filterConfig = null;
 
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
 
    	RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest) servletRequest);
 
        HttpServletRequestWrapper httpServletRequestWrapper = 
        		new HttpServletRequestWrapper((HttpServletRequest) servletRequest);
        
        String stringRequestURI = httpServletRequestWrapper.getRequestURI();
        Principal userPrincipal = httpServletRequestWrapper.getUserPrincipal();
        //Read request.getBody() as many time you need
        String stringBody = ((RequestWrapper) requestWrapper).getBody();
        
        //log.info("[doFilter] request.getBody():[" + stringBody + "]");
        OdpvAuditor.audit(stringRequestURI, userPrincipal, stringBody); //201806052021 - esert
        //Auditor.audit(stringBody); //201806052021 - esert
 
        chain.doFilter(requestWrapper, servletResponse);
    }
 
    public void init(FilterConfig filterConfiguration) throws ServletException {
        this.filterConfig = filterConfiguration;
    }
 
    public void destroy() {
        this.filterConfig = null;
    }
}