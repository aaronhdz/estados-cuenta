package com.fincomun.utilities;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.Callable;
import com.fincomun.model.EstadosCuentaModel;
import com.fincomun.component.CorreoComponent;
import com.fincomun.component.ConsultasComponent;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class TareaUtilities implements Callable<Void> {

    private volatile String nombre;
    private volatile List<EstadosCuentaModel> clientes;
    private volatile CorreoComponent correo_componente;
    private volatile ConsultasComponent consultas_componente;

    private static final AtomicInteger contador_exitosos = new AtomicInteger(0);
    private static final AtomicInteger contador_fallidos = new AtomicInteger(0);
    private static final AtomicInteger contador_informacion_incompleta = new AtomicInteger(0);

    private static final String EXPRESION_CORREO = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public TareaUtilities(String nombre, List<EstadosCuentaModel> clientes, ConsultasComponent consultas_componente, CorreoComponent correo_componente) {

        this.nombre = nombre;
        this.clientes = clientes;
        this.correo_componente = correo_componente;
        this.consultas_componente = consultas_componente;

    }

    public int get_contador_exitosos() {

        return contador_exitosos.get();

    }

    public int get_contador_fallidos() {

        return contador_fallidos.get();

    }

    public int get_contador_informacion_incompleta() {

        return contador_informacion_incompleta.get();

    }

    @Override
    public Void call() throws Exception {

        Integer tiempo_inicial = Integer.parseInt(PropiedadesUtilities.ProcesoMasivo.REGISTRO_PROCESAR_TIEMPO);

        try {

            Integer contador = -1;

            for (int i = 0; i < clientes.size(); i++) {

                if (Objects.isNull(clientes.get(i).getCorreo_electronico()) || clientes.get(i).getCorreo_electronico().trim().isEmpty()) {

                    consultas_componente.registrar_bitacora(Long.parseLong(clientes.get(i).getIdentificador_cuenta()), clientes.get(i).getNombre_completo().trim() + "",
                            clientes.get(i).getRegistro_federal().trim() + "", clientes.get(i).getCuenta_clabe().trim() + "", "", clientes.get(i).getPeriodo_cf().trim() + "",
                            clientes.get(i).getPeriodo_sf().trim() + "", 300);

                    consultas_componente.actualizar_estatus(0, Long.parseLong(clientes.get(i).getIdentificador_cuenta()));

                    contador_informacion_incompleta.getAndIncrement();

                } else if (!correo_valido(clientes.get(i).getCorreo_electronico().trim())) {

                    consultas_componente.registrar_bitacora(Long.parseLong(clientes.get(i).getIdentificador_cuenta()), clientes.get(i).getNombre_completo().trim() + "",
                            clientes.get(i).getRegistro_federal().trim() + "", clientes.get(i).getCuenta_clabe().trim() + "", "", clientes.get(i).getPeriodo_cf().trim() + "",
                            clientes.get(i).getPeriodo_sf().trim() + "", 300);

                    consultas_componente.actualizar_estatus(0, Long.parseLong(clientes.get(i).getIdentificador_cuenta()));

                    contador_informacion_incompleta.getAndIncrement();

                } else if (clientes.get(i).getCorreo_electronico().trim().toUpperCase().contains(PropiedadesUtilities.ProcesoMasivo.CORREO_NOPROPORCIONADO_MASIVO)) {

                    consultas_componente.registrar_bitacora(Long.parseLong(clientes.get(i).getIdentificador_cuenta()), clientes.get(i).getNombre_completo().trim() + "",
                            clientes.get(i).getRegistro_federal().trim() + "", clientes.get(i).getCuenta_clabe().trim() + "", "", clientes.get(i).getPeriodo_cf().trim() + "",
                            clientes.get(i).getPeriodo_sf().trim() + "", 300);

                    consultas_componente.actualizar_estatus(0, Long.parseLong(clientes.get(i).getIdentificador_cuenta()));

                    contador_informacion_incompleta.getAndIncrement();

                } else {

                    Integer resultado = correo_componente.correo(clientes.get(i).getCorreo_electronico().trim(), clientes.get(i).getPeriodo_cf().trim(), clientes.get(i).getPeriodo_sf().trim(),
                            clientes.get(i).getNombre_completo().trim(), clientes.get(i).getIdentificador_cuenta().trim(), 2, nombre);

                    if (resultado == 0) {

                        consultas_componente.registrar_bitacora(Long.parseLong(clientes.get(i).getIdentificador_cuenta()), clientes.get(i).getNombre_completo().trim() + "",
                                clientes.get(i).getRegistro_federal().trim() + "", clientes.get(i).getCuenta_clabe().trim() + "", clientes.get(i).getCorreo_electronico() + "",
                                clientes.get(i).getPeriodo_cf().trim() + "", clientes.get(i).getPeriodo_sf().trim() + "", 100);

                        consultas_componente.actualizar_estatus(1, Long.parseLong(clientes.get(i).getIdentificador_cuenta()));

                        contador_exitosos.getAndIncrement();

                    } else {

                        consultas_componente.registrar_bitacora(Long.parseLong(clientes.get(i).getIdentificador_cuenta()), clientes.get(i).getNombre_completo().trim() + "",
                                clientes.get(i).getRegistro_federal().trim() + "", clientes.get(i).getCuenta_clabe().trim() + "", clientes.get(i).getCorreo_electronico() + "",
                                clientes.get(i).getPeriodo_cf().trim() + "", clientes.get(i).getPeriodo_sf().trim() + "", 200);

                        consultas_componente.actualizar_estatus(0, Long.parseLong(clientes.get(i).getIdentificador_cuenta()));

                        contador_fallidos.getAndIncrement();

                    }

                }

                if (i == tiempo_inicial) {

                    log.info("PAUSAR " + nombre);

                    Thread.sleep(Integer.parseInt(PropiedadesUtilities.ProcesoMasivo.TIEMPO_ESPERA_ENVIO));
                    contador = tiempo_inicial + tiempo_inicial;

                }

                if (i == contador || i == (clientes.size() - 1)) {

                    if ((clientes.size() - 1) == i) {

                        log.info(nombre + " TERMINADO");

                    } else {

                        log.info("PAUSAR " + nombre);

                    }

                    Thread.sleep(Integer.parseInt(PropiedadesUtilities.ProcesoMasivo.TIEMPO_ESPERA_ENVIO));
                    contador = contador + tiempo_inicial;

                }

            }

            return null;

        } catch (Exception e) {

            log.error("CODIGO (" + 7 + ") ERROR AL PROCESAR " + nombre);
            log.error("");
            log.error("" + e.getMessage());
            log.error("");
            log.error("");

            return null;

        }

    }

    public static boolean correo_valido(String correo) {

        Pattern patron = Pattern.compile(EXPRESION_CORREO);
        Matcher coincidencia = patron.matcher(correo);

        return coincidencia.matches();

    }

}
