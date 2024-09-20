package com.fincomun.configuration;

import java.nio.charset.StandardCharsets;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;

@Configuration
public class CorreoConfiguration {

    @Bean("plantillaSimple")
    @Autowired
    public SpringTemplateEngine plantilla_simple(SpringResourceTemplateResolver solucionador_plantilla) {

        SpringTemplateEngine plantilla = new SpringTemplateEngine();
        plantilla.addTemplateResolver(solucionador_plantilla);

        return plantilla;

    }

    @Bean
    public SpringResourceTemplateResolver solucionador() {

        SpringResourceTemplateResolver solucionador_plantilla = new SpringResourceTemplateResolver();

        solucionador_plantilla.setPrefix("/WEB-INF/templates/");
        solucionador_plantilla.setSuffix(".html");
        solucionador_plantilla.setTemplateMode(TemplateMode.HTML);
        solucionador_plantilla.setCharacterEncoding(StandardCharsets.UTF_8.name());

        return solucionador_plantilla;

    }

}
