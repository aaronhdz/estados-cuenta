package com.fincomun.service;

import java.util.List;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Objects;
import java.time.Instant;
import java.time.Duration;
import org.json.JSONObject;
import java.util.ArrayList;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.Executors;
import com.fincomun.model.EnviarModel;
import com.fincomun.model.BusquedaModel;
import com.fincomun.model.HistoricoModel;
import com.fincomun.model.ConsultarModel;
import java.time.format.DateTimeFormatter;
import org.springframework.http.HttpStatus;
import java.util.concurrent.ExecutorService;
import com.fincomun.model.EstadosCuentaModel;
import com.fincomun.utilities.TareaUtilities;
import org.springframework.stereotype.Service;
import com.fincomun.component.CorreoComponent;
import org.springframework.http.ResponseEntity;
import com.fincomun.component.ConsultasComponent;
import org.apache.commons.lang3.math.NumberUtils;
import com.fincomun.utilities.PropiedadesUtilities;
import org.springframework.scheduling.annotation.Async;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Service
public class EstadosCuentaService {

    @Autowired
    private ConsultasComponent consultas_componente;

    @Autowired
    private CorreoComponent correo_componente;

    @Async
    public void ejecutar(Integer periodo) {

        Long tiempo_inicio = System.currentTimeMillis();

        if (Objects.isNull(periodo)) {

            log.info("CODIGO (" + 5 + ") PERIODO NO INGRESADO");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("FIN PROCESO MASIVO");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return;

        } else {

            List<EstadosCuentaModel> clientes = consultas_componente.consultar_clientes(periodo);

            if (clientes.isEmpty()) {

                log.info("CODIGO (" + 6 + ") SIN INFORMACION POR PROCESAR");
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("FIN PROCESO MASIVO");
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");

                return;

            } else {

                log.info("NUMERO REGISTROS (" + clientes.size() + ")");
                log.info("");
                log.info("");

                masivo(clientes, tiempo_inicio);

            }

        }

    }

    public ResponseEntity<Object> informacion(ConsultarModel peticion) {

        HashMap<String, String> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        if (Objects.isNull(peticion)) {

            resultado.put("codigo", "101");
            resultado.put("descripcion", "Petición vacía.");

            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        log.info("DATOS PETICION");
        log.info("");
        log.info("");
        log.info("PERIODO: " + peticion.getPeriodo());
        log.info("PROCESO: " + peticion.getProceso());
        log.info("NOMBRE COMPLETO: " + peticion.getNombre_completo());
        log.info("REGISTRO FEDERAL: " + peticion.getRegistro_federal());
        log.info("NUMERO CLIENTE: " + peticion.getNumero_cliente());
        log.info("NUMERO CUENTA: " + peticion.getNumero_cuenta());
        log.info("");
        log.info("");

        if (Objects.isNull(peticion.getPeriodo()) || peticion.getPeriodo().trim().isEmpty()) {

            resultado.put("codigo", "102");
            resultado.put("descripcion", "Ingresar periodo.");

            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (!NumberUtils.isParsable(peticion.getPeriodo().trim())) {

            resultado.put("codigo", "113");
            resultado.put("descripcion", "El periodo debe ser de tipo numérico.");

            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (Objects.isNull(peticion.getProceso()) || peticion.getProceso().trim().isEmpty()) {

            resultado.put("codigo", "103");
            resultado.put("descripcion", "Ingresar proceso.");

            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (!NumberUtils.isParsable(peticion.getProceso().trim())) {

            resultado.put("codigo", "110");
            resultado.put("descripcion", "El proceso debe ser de tipo numérico.");

            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        HttpStatus estatus = HttpStatus.BAD_REQUEST;

        switch (peticion.getProceso().trim()) {

            case "100":

                if (Objects.isNull(peticion.getNombre_completo()) || peticion.getNombre_completo().trim().isEmpty()) {

                    resultado.put("codigo", "104");
                    resultado.put("descripcion", "Ingresar nombre.");

                    respuesta.put("resultado", resultado);

                } else {

                    List<BusquedaModel> busqueda = consultas_componente.busqueda_nombre(peticion.getNombre_completo().trim(), peticion.getPeriodo().trim());

                    if (busqueda.isEmpty()) {

                        resultado.put("codigo", "105");
                        resultado.put("descripcion", "No fue posible consultar la información del cliente.");

                        respuesta.put("resultado", resultado);
                        estatus = HttpStatus.INTERNAL_SERVER_ERROR;

                    } else {

                        resultado.put("codigo", "100");
                        resultado.put("descripcion", "Petición realizada con éxito.");
                        respuesta.put("resultado", resultado);

                        respuesta.put("clientes", busqueda);
                        estatus = HttpStatus.OK;

                    }

                }

                break;

            case "200":

                if (Objects.isNull(peticion.getRegistro_federal()) || peticion.getRegistro_federal().trim().isEmpty()) {

                    resultado.put("codigo", "106");
                    resultado.put("descripcion", "Ingresar registro federal.");

                    respuesta.put("resultado", resultado);

                } else {

                    List<BusquedaModel> busqueda = consultas_componente.busqueda_registro(peticion.getRegistro_federal().trim(), peticion.getPeriodo().trim());

                    if (busqueda.isEmpty()) {

                        resultado.put("codigo", "105");
                        resultado.put("descripcion", "No fue posible consultar la información del cliente.");

                        respuesta.put("resultado", resultado);
                        estatus = HttpStatus.INTERNAL_SERVER_ERROR;

                    } else {

                        resultado.put("codigo", "100");
                        resultado.put("descripcion", "Petición realizada con éxito.");
                        respuesta.put("resultado", resultado);

                        respuesta.put("clientes", busqueda);
                        estatus = HttpStatus.OK;

                    }

                }

                break;

            case "300":

                if (Objects.isNull(peticion.getNumero_cliente()) || peticion.getNumero_cliente().trim().isEmpty()) {

                    resultado.put("codigo", "108");
                    resultado.put("descripcion", "Ingresar número de cliente.");

                    respuesta.put("resultado", resultado);

                } else {

                    if (!NumberUtils.isParsable(peticion.getNumero_cliente().trim())) {

                        resultado.put("codigo", "111");
                        resultado.put("descripcion", "El número de cliente debe ser de tipo numérico.");

                        respuesta.put("resultado", resultado);

                    } else {

                        List<BusquedaModel> busqueda = consultas_componente.busqueda_cliente(peticion.getNumero_cliente().trim(), peticion.getPeriodo().trim());

                        if (busqueda.isEmpty()) {

                            resultado.put("codigo", "105");
                            resultado.put("descripcion", "No fue posible consultar la información del cliente.");

                            respuesta.put("resultado", resultado);
                            estatus = HttpStatus.INTERNAL_SERVER_ERROR;

                        } else {

                            resultado.put("codigo", "100");
                            resultado.put("descripcion", "Petición realizada con éxito.");
                            respuesta.put("resultado", resultado);

                            respuesta.put("clientes", busqueda);
                            estatus = HttpStatus.OK;

                        }

                    }

                }

                break;

            case "400":

                if (Objects.isNull(peticion.getNumero_cuenta()) || peticion.getNumero_cuenta().trim().isEmpty()) {

                    resultado.put("codigo", "109");
                    resultado.put("descripcion", "Ingresar número de cuenta.");

                    respuesta.put("resultado", resultado);

                } else {

                    if (!NumberUtils.isParsable(peticion.getNumero_cuenta().trim())) {

                        resultado.put("codigo", "112");
                        resultado.put("descripcion", "El número de cuenta debe ser de tipo numérico.");

                        respuesta.put("resultado", resultado);

                    } else {

                        List<BusquedaModel> busqueda = consultas_componente.busqueda_cuenta(peticion.getNumero_cuenta().trim(), peticion.getPeriodo().trim());

                        if (busqueda.isEmpty()) {

                            resultado.put("codigo", "105");
                            resultado.put("descripcion", "No fue posible consultar la información del cliente.");

                            respuesta.put("resultado", resultado);
                            estatus = HttpStatus.INTERNAL_SERVER_ERROR;

                        } else {

                            resultado.put("codigo", "100");
                            resultado.put("descripcion", "Petición realizada con éxito.");
                            respuesta.put("resultado", resultado);

                            respuesta.put("clientes", busqueda);
                            estatus = HttpStatus.OK;

                        }

                    }

                }

                break;

            default:

                resultado.put("codigo", "107");
                resultado.put("descripcion", "Número de proceso incorrecto.");
                respuesta.put("resultado", resultado);

                break;

        }

        log.info("RESPUESTA");
        log.info("");
        log.info(new JSONObject(respuesta).toString());
        log.info("");
        log.info("");
        log.info("");
        log.info("");
        log.info("");
        log.info("");
        log.info("");
        log.info("");

        return new ResponseEntity(respuesta, estatus);

    }

    public ResponseEntity<Object> envio(EnviarModel peticion) {

        HashMap<String, String> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        if (Objects.isNull(peticion)) {

            resultado.put("codigo", "101");
            resultado.put("descripcion", "Petición vacía.");

            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        log.info("DATOS PETICION");
        log.info("");
        log.info("");
        log.info("CORREO ELECTRONICO: " + peticion.getCorreo_electronico());
        log.info("NOMBRE COMPLETO: " + peticion.getNombre_completo());
        log.info("NUMERO CUENTA: " + peticion.getNumero_cuenta());
        log.info("PERIODO CON FORMATO: " + peticion.getPeriodo_cformato());
        log.info("PERIODO SIN FORMATO: " + peticion.getPeriodo_sformato());
        log.info("USUARIO: " + peticion.getUsuario());
        log.info("");
        log.info("");

        if (Objects.isNull(peticion.getNombre_completo()) || peticion.getNombre_completo().trim().isEmpty()) {

            resultado.put("codigo", "102");
            resultado.put("descripcion", "Ingresar nombre.");

            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (Objects.isNull(peticion.getNumero_cuenta()) || peticion.getNumero_cuenta().trim().isEmpty()) {

            resultado.put("codigo", "103");
            resultado.put("descripcion", "Ingresar número de cuenta.");

            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (!NumberUtils.isParsable(peticion.getNumero_cuenta().trim())) {

            resultado.put("codigo", "110");
            resultado.put("descripcion", "El número de cuenta debe ser de tipo numérico.");

            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (Objects.isNull(peticion.getCorreo_electronico()) || peticion.getCorreo_electronico().trim().isEmpty()) {

            resultado.put("codigo", "104");
            resultado.put("descripcion", "Ingresar correo electrónico.");

            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (Objects.isNull(peticion.getPeriodo_cformato()) || peticion.getPeriodo_cformato().trim().isEmpty()) {

            resultado.put("codigo", "105");
            resultado.put("descripcion", "Ingresar periodo con formato.");

            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (Objects.isNull(peticion.getPeriodo_sformato()) || peticion.getPeriodo_sformato().trim().isEmpty()) {

            resultado.put("codigo", "106");
            resultado.put("descripcion", "Ingresar periodo sin formato.");

            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (!NumberUtils.isParsable(peticion.getPeriodo_sformato().trim())) {

            resultado.put("codigo", "111");
            resultado.put("descripcion", "El periodo sin formato debe ser de tipo numérico.");

            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (Objects.isNull(peticion.getUsuario()) || peticion.getUsuario().trim().isEmpty()) {

            resultado.put("codigo", "107");
            resultado.put("descripcion", "Ingresar usuario.");

            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

        }

        Integer envio = correo_componente.correo(peticion.getCorreo_electronico().trim(),
                peticion.getPeriodo_cformato().trim(), peticion.getPeriodo_sformato().trim(),
                peticion.getNombre_completo().trim(), peticion.getNumero_cuenta().trim(), 1, "");

        if (envio == 1) {

            Integer registrar = consultas_componente.registrar_reenvio(peticion.getNombre_completo().trim(),
                    Long.parseLong(peticion.getNumero_cuenta().trim()), peticion.getCorreo_electronico().trim(), peticion.getPeriodo_cformato().trim(),
                    peticion.getPeriodo_sformato(), peticion.getUsuario(), 500);

            if (registrar == 1) {

                resultado.put("codigo", "109");
                resultado.put("descripcion", "No fue posible registrar el reenvío.");

                respuesta.put("resultado", resultado);

                log.info("RESULTADO");
                log.info("");
                log.info(new JSONObject(respuesta).toString());
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");

            } else {

                resultado.put("codigo", "108");
                resultado.put("descripcion", "Envío de correo electrónico fallido.");

                respuesta.put("resultado", resultado);

                log.info("RESULTADO");
                log.info("");
                log.info(new JSONObject(respuesta).toString());
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");
                log.info("");

            }

            return new ResponseEntity(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        Integer registrar = consultas_componente.registrar_reenvio(peticion.getNombre_completo().trim(),
                Long.parseLong(peticion.getNumero_cuenta().trim()), peticion.getCorreo_electronico().trim(), peticion.getPeriodo_cformato().trim(),
                peticion.getPeriodo_sformato(), peticion.getUsuario(), 400);

        if (registrar == 1) {

            resultado.put("codigo", "109");
            resultado.put("descripcion", "No fue posible registrar el reenvío.");

            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);

        } else {

            resultado.put("codigo", "100");
            resultado.put("descripcion", "Petición realizada con éxito.");

            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity(respuesta, HttpStatus.OK);

        }

    }

    public ResponseEntity<Object> consultar_historial(HistoricoModel peticion) {

        HashMap<String, String> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        if (Objects.isNull(peticion)) {

            resultado.put("codigo", "101");
            resultado.put("descripcion", "Petición vacía.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);

        }

        log.info("DATOS PETICION");
        log.info("");
        log.info("");
        log.info("PERIODO: " + peticion.getPeriodo());
        log.info("NUMERO CLIENTE: " + peticion.getNumero_cliente());
        log.info("");
        log.info("");

        if (Objects.isNull(peticion.getPeriodo()) || peticion.getPeriodo().trim().isEmpty()) {

            resultado.put("codigo", "102");
            resultado.put("descripcion", "Ingresar periodo.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (Objects.isNull(peticion.getNumero_cliente()) || peticion.getNumero_cliente().trim().isEmpty()) {

            resultado.put("codigo", "103");
            resultado.put("descripcion", "Ingresar número de cliente.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (!NumberUtils.isParsable(peticion.getPeriodo().trim())) {

            resultado.put("codigo", "104");
            resultado.put("descripcion", "El periodo debe ser de tipo numérico.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (!NumberUtils.isParsable(peticion.getNumero_cliente().trim())) {

            resultado.put("codigo", "105");
            resultado.put("descripcion", "El número de cliente debe ser de tipo numérico.");
            respuesta.put("resultado", resultado);

            log.info("RESULTADO");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);

        }

        List<BusquedaModel> busqueda = consultas_componente.busqueda_cliente_hist(peticion.getNumero_cliente().trim(), peticion.getPeriodo().trim());

        if (busqueda.isEmpty()) {

            resultado.put("codigo", "106");
            resultado.put("descripcion", "No fue posible consultar el historial del cliente.");
            respuesta.put("resultado", resultado);

            log.info("RESPUESTA");
            log.info("");
            log.info(new JSONObject(respuesta).toString());
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");
            log.info("");

            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        resultado.put("codigo", "100");
        resultado.put("descripcion", "Petición realizada con éxito.");
        respuesta.put("resultado", resultado);
        respuesta.put("clientes", busqueda);

        log.info("RESPUESTA");
        log.info("");
        log.info(new JSONObject(respuesta).toString());
        log.info("");
        log.info("");
        log.info("");
        log.info("");
        log.info("");
        log.info("");
        log.info("");
        log.info("");

        return new ResponseEntity<>(respuesta, HttpStatus.OK);

    }

    private void masivo(List<EstadosCuentaModel> clientes, Long tiempo_inicio) {

        Integer indice_final = 0;
        Integer indice_inicial = 0;

        List<List<EstadosCuentaModel>> numero_hilos = new ArrayList<>();
        Integer incremento_paquete = Integer.parseInt(PropiedadesUtilities.ProcesoMasivo.NUMERO_REGISTROS_PAQUETE);

        for (int i = 1; i <= clientes.size(); i++) {

            if (i == incremento_paquete) {

                numero_hilos.add(clientes.subList(0, incremento_paquete));
                indice_final = incremento_paquete + incremento_paquete;
                indice_inicial = incremento_paquete;

            }

            if (i == indice_final) {

                numero_hilos.add(clientes.subList(indice_inicial, indice_final));

                indice_inicial = indice_final;
                indice_final = indice_final + incremento_paquete;

            }

            if (i == clientes.size()) {

                numero_hilos.add(clientes.subList(indice_inicial, i));

            }

        }

        numero_hilos.removeIf(item -> item.isEmpty());

        log.info("NUMERO DE HILOS A PROCESAR (" + numero_hilos.size() + ")");
        log.info("");
        log.info("");

        for (int i = 0; i < numero_hilos.size(); i++) {

            log.info(numero_hilos.get(i).toString());

        }

        ExecutorService servicio_ejecucion = Executors.newFixedThreadPool(Integer.parseInt(PropiedadesUtilities.ProcesoMasivo.NUMERO_HILOS_EJECUTAR));

        log.info("NUMERO DE HILOS A EJECUTAR EN PARALELO (" + PropiedadesUtilities.ProcesoMasivo.NUMERO_HILOS_EJECUTAR + ")");
        log.info("");
        log.info("");

        List<TareaUtilities> tareas = new ArrayList<>();

        for (int i = 0; i < numero_hilos.size(); i++) {

            tareas.add(new TareaUtilities("HILO (" + (i + 1) + ")", numero_hilos.get(i), consultas_componente, correo_componente));

        }

        try {

            servicio_ejecucion.invokeAll(tareas);
            servicio_ejecucion.shutdown();

        } catch (Exception e) {

            servicio_ejecucion.shutdown();

            log.error("CODIGO (" + 13 + ") ERROR DE EJECUCION DE TAREAS");
            log.error("");
            log.error("" + e.getMessage());
            log.error("");
            log.error("");

            return;

        }

        Long tiempo_fin = System.currentTimeMillis();

        String hora_inicio = cambiar_formato(tiempo_inicio);
        String hora_fin = cambiar_formato(tiempo_fin);
        List<String> campos_tiempo = calcular_tiempo(hora_inicio, hora_fin);

        log.info("");
        log.info("");
        log.info("");
        log.info("");
        log.info("");
        log.info("HORA INICIO PROCESO " + hora_inicio);
        log.info("HORA TERMINO PROCESO " + hora_fin);
        log.info("");
        log.info("");
        log.info("");
        log.info("");
        log.info("");
        log.info("TIEMPO PROCESAMIENTO");
        log.info("");
        log.info("HORAS " + campos_tiempo.get(0));
        log.info("MINUTOS " + campos_tiempo.get(1));
        log.info("SEGUNDOS " + campos_tiempo.get(2));
        log.info("");
        log.info("");
        log.info("");
        log.info("");
        log.info("");
        log.info("PROCESAMIENTO REGISTROS");
        log.info("");
        log.info("NUMERO REGISTROS EXITOSOS (" + tareas.get(0).get_contador_exitosos() + ")");
        log.info("NUMERO REGISTROS FALLIDOS (" + tareas.get(0).get_contador_fallidos() + ")");
        log.info("NUMERO REGISTROS INFORMACION INCOMPLETA (" + tareas.get(0).get_contador_informacion_incompleta() + ")");
        log.info("");
        log.info("");
        log.info("");
        log.info("");
        log.info("FIN PROCESO MASIVO");
        log.info("");
        log.info("");
        log.info("");
        log.info("");
        log.info("");

        correo_componente.reporte(clientes.size(), hora_inicio, hora_fin, campos_tiempo.get(0), campos_tiempo.get(1),
                campos_tiempo.get(2), tareas.get(0).get_contador_exitosos(), tareas.get(0).get_contador_fallidos(), tareas.get(0).get_contador_informacion_incompleta());

        return;

    }

    private String cambiar_formato(Long tiempo) {

        Instant instancia = Instant.ofEpochMilli(tiempo);

        ZonedDateTime zona = instancia.atZone(ZoneId.systemDefault());

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");

        return zona.format(formato);

    }

    private List<String> calcular_tiempo(String hora_inicio, String hora_final) {

        List<String> tiempo = new ArrayList<>();

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");

        LocalTime inicio = LocalTime.parse(hora_inicio, formato);
        LocalTime fin = LocalTime.parse(hora_final, formato);

        Duration duracion = Duration.between(inicio, fin);
        long diferencia_segundos = duracion.getSeconds();

        tiempo.add((diferencia_segundos / 3600) + "");
        tiempo.add(((diferencia_segundos % 3600) / 60) + "");
        tiempo.add((diferencia_segundos % 60) + "");

        return tiempo;

    }

}
