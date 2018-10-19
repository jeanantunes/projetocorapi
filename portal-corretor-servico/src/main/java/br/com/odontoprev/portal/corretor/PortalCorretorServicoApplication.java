package br.com.odontoprev.portal.corretor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.WebApplicationInitializer;

@SpringBootApplication
@EnableAutoConfiguration
public class PortalCorretorServicoApplication extends SpringBootServletInitializer
		implements WebApplicationInitializer {

	private static final Log log = LogFactory.getLog(PortalCorretorServicoApplication.class);

	public static void main(String[] args) {

//		log.info("[PortalCorretorServicoApplication][v.201802251422]");
//		log.info("[PortalCorretorServicoApplication][v.201805301758]"); //201805301758 - esert - interceptor - teste
		log.info("[PortalCorretorServicoApplication][v.201810181904]"); //201810181904 - esert - wip - COR-763:Isolar Transação de Inserção do JSON Request DCMS das Demais Persistências

		SpringApplication.run(PortalCorretorServicoApplication.class, args);
	}
}
