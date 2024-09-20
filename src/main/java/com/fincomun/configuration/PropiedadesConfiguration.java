package com.fincomun.configuration;

import java.util.Properties;
import com.fincomun.utilities.PropiedadesUtilities;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropiedadesConfiguration {

    @Bean(name = "servidorTransmision")
    public Properties servidor_transimision() {

        Properties propiedades = new Properties();

        propiedades.put("mail.smtp.auth", PropiedadesUtilities.ServidorCorreo.AUTENTICACION_SERVIDOR_CORREO);
        propiedades.put("mail.smtp.starttls.enable", PropiedadesUtilities.ServidorCorreo.SOBRESALTOS_SERVIDOR_CORREO);
        propiedades.put("mail.smtp.host", PropiedadesUtilities.ServidorCorreo.DIRECCION_SERVIDOR_CORREO);
        propiedades.put("mail.smtp.ssl.trust", PropiedadesUtilities.ServidorCorreo.DIRECCION_SERVIDOR_CORREO);
        propiedades.put("mail.smtp.port", PropiedadesUtilities.ServidorCorreo.PUERTO_SERVIDOR_CORREO);
        propiedades.put("mail.smtp.timeout", PropiedadesUtilities.ServidorCorreo.TIEMPO_ESPERA_CORREO);
        propiedades.put("mail.smtps.connectiontimeout", PropiedadesUtilities.ServidorCorreo.TIEMPO_ESPERA_CORREO);

        return propiedades;

    }

}
