package br.com.odontoprev.portal.corretor.interceptor;

import java.util.Collections;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoggerInterceptor extends HandlerInterceptorAdapter {

	private static final Log log = LogFactory.getLog(LoggerInterceptor.class);

	@Override
	public boolean preHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler
			) throws Exception {

		log.info("[preHandle]; request:[" + request + "]");
		log.info("[preHandle]; request.getMethod():[" + request.getMethod() + "]");
		log.info("[preHandle]; request.getRequestURI():[" + request.getRequestURI() + "]");
		log.info("[preHandle]; getParameters(request):[" + getParameters(request) + "]");
		//201806061147 - esert - desativado
		//log.info("[preHandle]; getBody:[" + getBody(request) + "]"); // 201806051824 - esert
		//log.info("[preHandle]; getRequestBody:[" + getRequestBody(request) + "]"); // 201806051911 - esert

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		//201806061147 - esert - desativado
		// String body = null;
		// ResettableStreamHttpServletRequest wrappedRequest = new
		// ResettableStreamHttpServletRequest((HttpServletRequest) request);
		// //201806041520 - esert
		// ////// wrappedRequest.getInputStream().read();
		// //body = wrappedRequest.getInputStream().toString();
		// body = IOUtils.toString(wrappedRequest.getReader());
		// log.info("[postHandle]; body:[" + body + "]");

		log.info("[postHandle]; modelAndView:[" + modelAndView + "]"); // 201806051834 - esert
		//201806061147 - esert - desativado
		// log.info("[postHandle]; getBody:[" + getBody(request) + "]"); // 201806051819 - esert
		//log.info("[postHandle]; getRequestBody:[" + getRequestBody(request) + "]"); // 201806051900 - esert

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		if (ex != null) {
			ex.printStackTrace();
		}

		log.info("[afterCompletion]; request.getContextPath():[" + request.getContextPath() + "]"); //201806191843 - esert
		log.info("[afterCompletion]; request.getContentLength():[" + request.getContentLength() + "]"); //201806191829 - esert
		log.info("[afterCompletion]; response.getStatus():[" + response.getStatus() + "]"); //201806191829 - esert
		log.info("[afterCompletion]; exception:[" + ex + "]");
		if(ex!=null) { //201806191829 - esert
			log.info("[afterCompletion]; exception.getMessage():[" + ex.getMessage() + "]"); //201806191829 - esert
		}
		//201806061147 - esert - desativado
		//log.info("[afterCompletion]; getBody:[" + getBody(request) + "]"); // 201806051824 - esert
		//log.info("[afterCompletion]; getRequestBody:[" + getRequestBody(request) + "]"); // 201806051853 - esert
	}

	//private String getParameters(HttpServletRequest request) {
	public static String getParameters(HttpServletRequest request) { //2018060612334 - esert

		StringBuffer posted = new StringBuffer();
		Enumeration<?> enumeration = request.getParameterNames();

		if (enumeration != null) {
			posted.append("?");
		}
		while (enumeration.hasMoreElements()) {
			if (posted.length() > 1) {
				posted.append("&");
			}
			String curr = (String) enumeration.nextElement();
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

	//private String getRemoteAddr(HttpServletRequest request) {
	public static String getRemoteAddr(HttpServletRequest request) { //2018060612334 - esert
		String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
		if (ipFromHeader != null && ipFromHeader.length() > 0) {
			log.debug("ip from proxy - X-FORWARDED-FOR : " + ipFromHeader);
			return ipFromHeader;
		}
		return request.getRemoteAddr();
	}

	// 201806051819 - esert - pega o bode
	// h t t p s : // jaketrent .com /post /http-request-body-spring/
//	private String getBody(HttpServletRequest req) {
//		String body = "";
//		if (req.getMethod().equals("POST")) {
//			StringBuilder sb = new StringBuilder();
//			BufferedReader bufferedReader = null;
//
//			try {
//				bufferedReader = req.getReader();
//				char[] charBuffer = new char[128];
//				int bytesRead;
//				while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
//					sb.append(charBuffer, 0, bytesRead);
//				}
//			} catch (IOException ex) {
//				// swallow silently -- can't get body, won't
//			} finally {
//				if (bufferedReader != null) {
//					try {
//						bufferedReader.close();
//					} catch (IOException ex) {
//						// swallow silently -- can't get body, won't
//					}
//				}
//			}
//			body = sb.toString();
//		}
//		return body;
//	}

//	private String getRequestBody(final HttpServletRequest request) throws AuthenticationException {
//
//		HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(request);
//		StringBuilder stringBuilder = new StringBuilder();
//		BufferedReader bufferedReader = null;
//		try {
//			InputStream inputStream = requestWrapper.getInputStream();
//			if (inputStream != null) {
//				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//				char[] charBuffer = new char[128];
//				int bytesRead = -1;
//				while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
//					stringBuilder.append(charBuffer, 0, bytesRead);
//				}
//			}
//		} catch (IOException ex) {
//			log.error("[LoggerInterceptor.getRequestBody(HttpServletRequest)] Error reading the request payload", ex);
//			throw new AuthenticationException("[LoggerInterceptor.getRequestBody(HttpServletRequest)] Error reading the request payload", ex);
//		} finally {
//			if (bufferedReader != null) {
//				try {
//					bufferedReader.close();
//				} catch (IOException iox) {
//					// ignore
//				}
//			}
//		}
//
//		return stringBuilder.toString();
//	}

	//h t t p s :// gist .github .com /int128/e47217bebdb4c402b2ffa7cc199307ba
	//201806121747 - esert - inc Header + Parameter
    public static String getHeaders(HttpServletRequest request) {
		log.info("[getHeaders(HttpServletRequest)] ini");
    	StringBuffer stringBufferRet = new StringBuffer();
//        log.info("request.getMethod():["+ request.getMethod() +"]");
//        log.info("request.getRequestURI():["+ request.getRequestURI() +"]");            	
//        log.info("request.getQueryString():["+ request.getQueryString() +"]");            	
        Collections.list(request.getHeaderNames()).forEach(headerName ->
            Collections.list(request.getHeaders(headerName)).forEach(headerValue -> {
                	String headerNameValue = "[" + headerName + "]:[" + headerValue + "]";
            		log.info("[logRequestHeader] " + headerNameValue);
            		if(stringBufferRet.length()>0) {
                		stringBufferRet.append(",");
            		}
                	stringBufferRet.append(headerNameValue);
            	}
            )
        );
		log.info("[getHeaders(HttpServletRequest)] fim");
        return stringBufferRet.toString();
    }

	public static String getHeaders(HttpHeaders headers) {
		log.info("[getHeaders(HttpHeaders)] ini");
		StringBuffer stringBufferRet = new StringBuffer();
		headers.forEach((headerName, hValue) ->
			headers.get(headerName).forEach(headerValue -> {
				String headerNameValue = "[" + headerName + "]:[" + headerValue + "]";
				log.info("[getHeaders(HttpHeaders)]->" + headerNameValue);
				if(stringBufferRet.length()>0) {
					stringBufferRet.append(",");
		  		}
		      	stringBufferRet.append(headerNameValue);
		  	})
		);
		log.info("[getHeaders(HttpHeaders)] fim");
		return stringBufferRet.toString();
	}
}
