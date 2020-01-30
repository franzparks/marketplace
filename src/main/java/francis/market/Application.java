package francis.market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import francis.market.common.config.RestConfig;
import francis.market.common.filter.AuthenticationFilter;
import francis.market.common.filter.CorsFilter;

import javax.servlet.Filter;

@SpringBootApplication()
public class Application {

	@Bean
	public RestConfig restConfig() {
		return new RestConfig();
	}

	@Bean
	public Filter corsFilter() {
		return new CorsFilter();
	}

	@Bean
	public Filter securityFilter() {
		return new AuthenticationFilter();
	}


	@Bean
	public javax.validation.Validator validator() {
		return new org.springframework.validation.beanvalidation.LocalValidatorFactoryBean();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
