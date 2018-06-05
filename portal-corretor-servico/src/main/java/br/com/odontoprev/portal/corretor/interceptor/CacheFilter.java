package br.com.odontoprev.portal.corretor.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//h t t p s :// howtodoinjava .com /servlets /httpservletrequestwrapper-example-read-request-body/
//201806052017 - esert - interceptor
public class CacheFilter implements Filter {
	 
	private static final Log log = LogFactory.getLog(LoggerInterceptor.class);
    private static final String INDEX_DIR = "c:/temp/cache";
 
    private FilterConfig filterConfig = null;
 
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
 
        request = new RequestWrapper((HttpServletRequest) request);
 
        //Read request.getBody() as many time you need
        String body = ((RequestWrapper) request).getBody();
        log.info("request.getBody():[" + body + "]");
        Auditor.audit(body); //201806052021 - esert
 
        chain.doFilter(request, response);
    }
 
    public void init(FilterConfig filterConfiguration) throws ServletException {
        this.filterConfig = filterConfiguration;
    }
 
    public void destroy() {
        this.filterConfig = null;
    }
}