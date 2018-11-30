package org.lab.samples.mongo.api;

import org.lab.samples.mongo.api.populator.PopulatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Autowired
	private PopulatorService populatorService;

	@Override
	public void run(String... args) {
		populatorService.check();
	}

	@ApiIgnore
	@Controller
	public class RedirectController {

		@RequestMapping("/")
		public ModelAndView swaggerRedirectController() {
			RedirectView redirectView = new RedirectView("swagger-ui.html");
			redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
			return new ModelAndView(redirectView);
		}

	}

}
