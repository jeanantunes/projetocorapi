package br.com.odontoprev.portal.corretor.interceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.auth.AuthenticationException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoggerInterceptor extends HandlerInterceptorAdapter {

	private static final Log log = LogFactory.getLog(LoggerInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		log.info("[preHandle]; request:[" + request + "]");
		log.info("[preHandle]; request.getMethod():[" + request.getMethod() + "]");
		log.info("[preHandle]; request.getRequestURI():[" + request.getRequestURI() + "]");
		log.info("[preHandle]; getParameters(request):[" + getParameters(request) + "]");
		// log.info("[preHandle]; getBody:[" + getBody(request) + "]"); // 201806051824 - esert
		//log.info("[preHandle]; getRequestBody:[" + getRequestBody(request) + "]"); // 201806051911 - esert

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		// String body = null;
		// ResettableStreamHttpServletRequest wrappedRequest = new
		// ResettableStreamHttpServletRequest((HttpServletRequest) request);
		// //201806041520 - esert
		// ////// wrappedRequest.getInputStream().read();
		// //body = wrappedRequest.getInputStream().toString();
		// body = IOUtils.toString(wrappedRequest.getReader());
		// log.info("[postHandle]; body:[" + body + "]");

		log.info("[postHandle]; modelAndView:[" + modelAndView + "]"); // 201806051834 - esert
		// log.info("[postHandle]; getBody:[" + getBody(request) + "]"); // 201806051819 - esert
		//log.info("[postHandle]; getRequestBody:[" + getRequestBody(request) + "]"); // 201806051900 - esert

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		if (ex != null) {
			ex.printStackTrace();
		}

		log.info("[afterCompletion]; request:[" + request + "]");
		log.info("[afterCompletion]; exception:[" + ex + "]");
		//log.info("[afterCompletion]; getBody:[" + getBody(request) + "]"); // 201806051824 - esert
		//log.info("[afterCompletion]; getRequestBody:[" + getRequestBody(request) + "]"); // 201806051853 - esert
	}

	private String getParameters(HttpServletRequest request) {

		StringBuffer posted = new StringBuffer();
		Enumeration<?> e = request.getParameterNames();

		if (e != null) {
			posted.append("?");
		}
		while (e.hasMoreElements()) {
			if (posted.length() > 1) {
				posted.append("&");
			}
			String curr = (String) e.nextElement();
			posted.append(curr + "=");
			if (curr.contains("password") || curr.contains("pass") || curr.contains("pwd")) {
				posted.append("*****");
			} else {
				posted.append(request.getParameter(curr));
			}
		}
		String ip = request.getHeader("X-FORWARDED-FOR");
		String ipAddr = (ip == null) ? getRemoteAddr(request) : ip;
		if (ipAddr != null && !ipAddr.equals("")) {
			posted.append("&_psip=" + ipAddr);
		}
		return posted.toString();
	}

	private String getRemoteAddr(HttpServletRequest request) {
		String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
		if (ipFromHeader != null && ipFromHeader.length() > 0) {
			log.debug("ip from proxy - X-FORWARDED-FOR : " + ipFromHeader);
			return ipFromHeader;
		}
		return request.getRemoteAddr();
	}

	// 201806051819 - esert - pega o bode
	// h t t p s : // jaketrent .com /post /http-request-body-spring/
	private String getBody(HttpServletRequest req) {
		String body = "";
		if (req.getMethod().equals("POST")) {
			StringBuilder sb = new StringBuilder();
			BufferedReader bufferedReader = null;

			try {
				bufferedReader = req.getReader();
				char[] charBuffer = new char[128];
				int bytesRead;
				while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
					sb.append(charBuffer, 0, bytesRead);
				}
			} catch (IOException ex) {
				// swallow silently -- can't get body, won't
			} finally {
				if (bufferedReader != null) {
					try {
						bufferedReader.close();
					} catch (IOException ex) {
						// swallow silently -- can't get body, won't
					}
				}
			}
			body = sb.toString();
		}
		return body;
	}

	private String getRequestBody(final HttpServletRequest request) throws AuthenticationException {

		HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(request);
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			InputStream inputStream = requestWrapper.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			}
		} catch (IOException ex) {
			log.error("[LoggerInterceptor.getRequestBody(HttpServletRequest)] Error reading the request payload", ex);
			throw new AuthenticationException("[LoggerInterceptor.getRequestBody(HttpServletRequest)] Error reading the request payload", ex);
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException iox) {
					// ignore
				}
			}
		}

		return stringBuilder.toString();
	}

}
