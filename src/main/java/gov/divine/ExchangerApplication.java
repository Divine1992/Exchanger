package gov.divine;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = {"gov.divine.Repository"})
//@EntityScan(basePackages = {"gov.divine"})
@ComponentScan(basePackages = {"gov.divine"})
public class ExchangerApplication {

	public static void main(String[] args) {

		SpringApplication.run(ExchangerApplication.class, args);
	}
}
