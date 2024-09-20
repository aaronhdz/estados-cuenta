package com.fincomun.utilities;

import java.io.InputStream;
import java.util.Properties;
import java.io.FileInputStream;

public class ConfiguracionPropiedadesUtilities {

    static Properties propiedades = new Properties();

    private ConfiguracionPropiedadesUtilities() {

    }

    static {

        try {

            InputStream entrada = new FileInputStream("estados_cuenta/conf/estados_cuenta.properties");
            propiedades.load(entrada);

        } catch (Exception e) {

        }

    }

    public static String propiedad(String nombre) {

        String valor = propiedades.getProperty(nombre);

        if (valor != null) {

            valor = valor.trim();

        }

        return valor;

    }

}
