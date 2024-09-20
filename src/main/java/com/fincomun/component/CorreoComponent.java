package com.fincomun.component;

import javax.mail.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.context.Context;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring4.SpringTemplateEngine;
import com.fincomun.utilities.PropiedadesUtilities;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Slf4j
@Component
public class CorreoComponent {

    @Qualifier("plantillaSimple")
    @Autowired
    private SpringTemplateEngine plantilla;

    @Qualifier("servidorTransmision")
    @Autowired
    private Properties propiedades_servidor;

    public Integer correo(String correo_electronico, String periodo_cformato, String periodo_sformato, String nombre_completo, String numero_cuenta,
            Integer proceso, String nombre_hilo) {

        Session sesion = Session.getInstance(propiedades_servidor);
        MimeMessage mensaje = new MimeMessage(sesion);

        try {

            String[] correo_destinatario = {correo_electronico};
            String[] correo_copias = null;

            MimeMessageHelper mensaje_ayuda = new MimeMessageHelper(mensaje, true, StandardCharsets.UTF_8.name());
            mensaje_ayuda.setTo(correo_destinatario);

            if (correo_copias != null && correo_copias.length > 0) {

                mensaje_ayuda.setCc(correo_copias);

            } else {

                String[] vacio = new String[0];
                mensaje_ayuda.setCc(vacio);

            }

            byte[] arreglo = PropiedadesUtilities.ServidorCorreo.ASUNTO_CORREO.getBytes("ISO-8859-1");
            String asunto = new String(arreglo, "UTF-8");

            String cuenta_cifrada = cifrar(numero_cuenta);

            mensaje_ayuda.setFrom(PropiedadesUtilities.ServidorCorreo.ENVIO_CORREO);
            mensaje_ayuda.setSubject(asunto);

            Context contexto = new Context();
            Map<String, Object> parametros = new HashMap<>();

            parametros.put("param1", nombre_completo);
            parametros.put("param2", cuenta_cifrada);
            parametros.put("param3", periodo_cformato);

            String estado_cuenta = PropiedadesUtilities.ServicioEstadoCuenta.DOMINIO_SERVICIO_ESTADO_CUENTA + PropiedadesUtilities.ServicioEstadoCuenta.RUTA_SERVICIO_ESTADO_CUENTA
                    + "?numCredito=" + Integer.parseInt(numero_cuenta) + "&periodo=" + Integer.parseInt(periodo_sformato);

            if (proceso == 1) {

                log.info("DIRECCION: " + estado_cuenta);
                log.info("");
                log.info("");

            } else {

                log.info("DIRECCION (" + estado_cuenta + ") EMITIDA POR " + nombre_hilo);

            }

            parametros.put("param4", estado_cuenta);
            contexto.setVariables(parametros);

            String archivo_html = plantilla.process("estados-cuenta", contexto);
            mensaje_ayuda.setText(archivo_html, true);

            Transport.send(mensaje);

            if (proceso == 1) {

                log.info("CORREO ENVIADO");
                log.info("");
                log.info("");

            } else {

                log.info("CORREO ENVIADO POR " + nombre_hilo);

            }

            return 0;

        } catch (Exception e) {

            log.error("ERROR CORREO");
            log.error("");
            log.error("" + e.getMessage());
            log.error("");
            log.error("");

            return 1;

        }

    }

    public void reporte(Integer registros, String hora_inicio, String hora_final, String horas, String minutos,
            String segundos, Integer exitosos, Integer fallidos, Integer informacion_incompleta) {

        Session sesion = Session.getInstance(propiedades_servidor);
        MimeMessage mensaje = new MimeMessage(sesion);

        try {

            String[] correo_destinatario = {PropiedadesUtilities.ProcesoMasivo.CORREO_REPORTE_MASIVO};
            String[] correo_copias = null;

            MimeMessageHelper mensaje_ayuda = new MimeMessageHelper(mensaje, true, StandardCharsets.UTF_8.name());
            mensaje_ayuda.setTo(correo_destinatario);

            if (correo_copias != null && correo_copias.length > 0) {

                mensaje_ayuda.setCc(correo_copias);

            } else {

                String[] vacio = new String[0];
                mensaje_ayuda.setCc(vacio);

            }

            mensaje_ayuda.setFrom(PropiedadesUtilities.ServidorCorreo.ENVIO_CORREO);
            mensaje_ayuda.setSubject(PropiedadesUtilities.ProcesoMasivo.ASUNTO_REPORTE_MASIVO);

            Context contexto = new Context();
            Map<String, Object> parametros = new HashMap<>();

            parametros.put("param1", registros);
            parametros.put("param2", hora_inicio);
            parametros.put("param3", hora_final);
            parametros.put("param4", horas);
            parametros.put("param5", minutos);
            parametros.put("param6", segundos);
            parametros.put("param7", exitosos);
            parametros.put("param8", fallidos);
            parametros.put("param9", informacion_incompleta);
            contexto.setVariables(parametros);

            String archivo_html = plantilla.process("correo-reporte", contexto);
            mensaje_ayuda.setText(archivo_html, true);

            Transport.send(mensaje);

            log.info("CORREO ENVIADO");
            log.info("");
            log.info("");

        } catch (Exception e) {

            log.error("ERROR CORREO");
            log.error("");
            log.error("" + e.getMessage());
            log.error("");
            log.error("");

        }

    }

    private String cifrar(String numero_cuenta) {

        StringBuilder remplazar = new StringBuilder(numero_cuenta);

        int posicion_fin = numero_cuenta.length() - 4;

        for (int i = 0; i <= posicion_fin; i++) {

            remplazar.setCharAt(i, '*');

        }

        return remplazar.toString();

    }

}
