package br.com.odontoprev.portal.corretor.interceptor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

//import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoggerFilter implements Filter {

	private static final Log log = LogFactory.getLog(LoggerFilter.class);

	private Auditor auditor;

	public void destroy() {
		// Nothing to do
		log.info("destroy ini-fim");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		ResettableStreamHttpServletRequest wrappedRequest = new ResettableStreamHttpServletRequest(
				(HttpServletRequest) request);
		// wrappedRequest.getInputStream().read();
		//String body = IOUtils.toString(wrappedRequest.getReader());
		//auditor.audit(wrappedRequest.getRequestURI(), wrappedRequest.getUserPrincipal(), body);
		wrappedRequest.resetInputStream();
		chain.doFilter(wrappedRequest, response);

	}

	public void init(FilterConfig arg0) throws ServletException {
		// Nothing to do
	}

	private static class ResettableStreamHttpServletRequest extends HttpServletRequestWrapper {

		private byte[] rawData;
		private HttpServletRequest request;
		private ResettableServletInputStream servletStream;

		public ResettableStreamHttpServletRequest(HttpServletRequest request) {
			super(request);
			this.request = request;
			this.servletStream = new ResettableServletInputStream();
		}

		public void resetInputStream() {
			servletStream.stream = new ByteArrayInputStream(rawData);
		}

		@Override
		public ServletInputStream getInputStream() throws IOException {
			if (rawData == null) {
				//rawData = IOUtils.toByteArray(this.request.getReader());
				servletStream.stream = new ByteArrayInputStream(rawData);
			}
			return servletStream;
		}

		@Override
		public BufferedReader getReader() throws IOException {
			if (rawData == null) {
				//rawData = IOUtils.toByteArray(this.request.getReader());
				servletStream.stream = new ByteArrayInputStream(rawData);
			}
			return new BufferedReader(new InputStreamReader(servletStream));
		}

		private class ResettableServletInputStream extends ServletInputStream {

			private InputStream stream;

			@Override
			public int read() throws IOException {
				return stream.read();
			}

			@Override
			public boolean isFinished() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isReady() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void setReadListener(ReadListener listener) {
				// TODO Auto-generated method stub
				
			}
		}
	}
}