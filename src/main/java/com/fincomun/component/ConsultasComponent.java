package com.fincomun.component;

import java.util.List;
import java.sql.ResultSet;
import java.sql.Connection;
import java.util.ArrayList;
import oracle.jdbc.OracleTypes;
import lombok.extern.slf4j.Slf4j;
import java.sql.CallableStatement;
import com.fincomun.model.BusquedaModel;
import com.fincomun.model.EstadosCuentaModel;
import com.fincomun.utilities.ConexionUtilities;
import org.springframework.stereotype.Component;
import com.fincomun.utilities.PropiedadesUtilities;

@Slf4j
@Component
public class ConsultasComponent extends ConexionUtilities {

    private Connection abrir_conexion(Integer tipo_conexion, Integer proceso) {

        try {

            return this.conexion(tipo_conexion);

        } catch (Exception e) {

            if (proceso == 1) {

                log.error("CODIGO (" + 8 + ") ERROR ABRIR CONEXION");
                log.error("");

            } else {

                log.error("ERROR ABRIR CONEXION");
                log.error("");

            }

            log.error(e.getMessage());
            log.error("");
            log.error("");

            return null;

        }

    }

    private void cerrar_conexion(Integer tipo, CallableStatement declaracion, ResultSet resultado, Connection conexion,
            Integer proceso) {

        try {

            if (tipo == 1) {

                declaracion.close();
                resultado.close();
                conexion.close();

            } else {

                declaracion.close();
                conexion.close();

            }

        } catch (Exception e) {

            if (proceso == 1) {

                log.error("CODIGO (" + 10 + ") ERROR CERRAR CONEXION");
                log.error("");

            } else {

                log.error("ERROR CERRAR CONEXION");
                log.error("");

            }

            log.error(e.getMessage());
            log.error("");
            log.error("");

        }

    }

    public List<EstadosCuentaModel> consultar_clientes(Integer periodo) {

        Connection conexion = abrir_conexion(1, 0);

        List<EstadosCuentaModel> clientes = new ArrayList<>();

        if (conexion != null) {

            String consulta = "BEGIN " + PropiedadesUtilities.BaseIsifindev.ESQUEMA_BASE_DATOS_ISI + "."
                    + PropiedadesUtilities.BaseIsifindev.PAQUETE_ESTADOS_CUENTA_ISI + "." + PropiedadesUtilities.BaseIsifindev.PROCEDIMIENTO_BUSQUEDA_CLIENTES + "(?, ?); END;";

            try {

                CallableStatement declaracion = conexion.prepareCall(consulta);

                declaracion.setString(1, periodo + "");
                declaracion.registerOutParameter(2, OracleTypes.CURSOR);
                declaracion.execute();

                ResultSet registros = (ResultSet) declaracion.getObject(2);

                while (registros.next()) {

                    EstadosCuentaModel registro = new EstadosCuentaModel();

                    registro.setIdentificador_cuenta(registros.getLong(1) + "");
                    registro.setNombre_completo(registros.getString(2));
                    registro.setRegistro_federal(registros.getString(3));
                    registro.setPeriodo_sf(registros.getString(4));
                    registro.setPeriodo_cf(registros.getString(5));
                    registro.setIdentificador_cliente(registros.getLong(6) + "");
                    registro.setCuenta_clabe(registros.getString(7));
                    registro.setCorreo_electronico(registros.getString(8));

                    clientes.add(registro);

                }

                cerrar_conexion(1, declaracion, registros, conexion, 0);

                return clientes;

            } catch (Exception e) {

                log.error("CODIGO (" + 12 + ") ERROR AL CONSULTAR INFORMACION DE LOS CLIENTES");
                log.error("");
                log.error("" + e.getMessage());
                log.error("");
                log.error("");

                return clientes;

            }

        } else {

            return clientes;

        }

    }

    public List<BusquedaModel> busqueda_nombre(String nombre, String periodo) {

        Connection conexion = abrir_conexion(1, 0);

        List<BusquedaModel> busqueda = new ArrayList<>();

        if (conexion != null) {

            String consulta = "BEGIN " + PropiedadesUtilities.BaseIsifindev.ESQUEMA_BASE_DATOS_ISI + "."
                    + PropiedadesUtilities.BaseIsifindev.PAQUETE_ESTADOS_CUENTA_ISI + "." + PropiedadesUtilities.BaseIsifindev.PROCEDIMIENTO_BUSQUEDA_NOMBRE + "(?, ?, ?); END;";

            try {

                CallableStatement declaracion = conexion.prepareCall(consulta);

                declaracion.setString(1, "%" + nombre + "%");
                declaracion.setString(2, periodo);
                declaracion.registerOutParameter(3, OracleTypes.CURSOR);
                declaracion.execute();

                ResultSet registros = (ResultSet) declaracion.getObject(3);

                while (registros.next()) {

                    BusquedaModel registro = new BusquedaModel();

                    registro.setNumero_cuenta(registros.getLong(1) + "");
                    registro.setNombre_completo(registros.getString(2));
                    registro.setRegistro_federal(registros.getString(3));
                    registro.setNumero_cliente(registros.getLong(4) + "");
                    registro.setCorreo_electronico(registros.getString(5));
                    registro.setCuenta_clabe(registros.getString(6));

                    busqueda.add(registro);

                }

                cerrar_conexion(1, declaracion, registros, conexion, 0);

                return busqueda;

            } catch (Exception e) {

                log.error("ERROR BUSQUEDA NOMBRE");
                log.error("");
                log.error("" + e.getMessage());
                log.error("");
                log.error("");

                return busqueda;

            }

        } else {

            return busqueda;

        }

    }

    public List<BusquedaModel> busqueda_registro(String registro_federal, String periodo) {

        Connection conexion = abrir_conexion(1, 0);

        List<BusquedaModel> busqueda = new ArrayList<>();

        if (conexion != null) {

            String consulta = "BEGIN " + PropiedadesUtilities.BaseIsifindev.ESQUEMA_BASE_DATOS_ISI + "."
                    + PropiedadesUtilities.BaseIsifindev.PAQUETE_ESTADOS_CUENTA_ISI + "." + PropiedadesUtilities.BaseIsifindev.PROCEDIMIENTO_BUSQUEDA_REGISTRO + "(?, ?, ?); END;";

            try {

                CallableStatement declaracion = conexion.prepareCall(consulta);

                declaracion.setString(1, "%" + registro_federal + "%");
                declaracion.setString(2, periodo);
                declaracion.registerOutParameter(3, OracleTypes.CURSOR);
                declaracion.execute();

                ResultSet registros = (ResultSet) declaracion.getObject(3);

                while (registros.next()) {

                    BusquedaModel registro = new BusquedaModel();

                    registro.setNumero_cuenta(registros.getLong(1) + "");
                    registro.setNombre_completo(registros.getString(2));
                    registro.setRegistro_federal(registros.getString(3));
                    registro.setNumero_cliente(registros.getLong(4) + "");
                    registro.setCorreo_electronico(registros.getString(5));
                    registro.setCuenta_clabe(registros.getString(6));

                    busqueda.add(registro);

                }

                cerrar_conexion(1, declaracion, registros, conexion, 0);

                return busqueda;

            } catch (Exception e) {

                log.error("ERROR BUSQUEDA REGISTRO");
                log.error("");
                log.error(e.getMessage());
                log.error("");
                log.error("");

                return busqueda;

            }

        } else {

            return busqueda;

        }

    }

    public List<BusquedaModel> busqueda_cliente(String numero_cliente, String periodo) {

        Connection conexion = abrir_conexion(1, 0);

        List<BusquedaModel> busqueda = new ArrayList<>();

        if (conexion != null) {

            String consulta = "BEGIN " + PropiedadesUtilities.BaseIsifindev.ESQUEMA_BASE_DATOS_ISI + "."
                    + PropiedadesUtilities.BaseIsifindev.PAQUETE_ESTADOS_CUENTA_ISI + "." + PropiedadesUtilities.BaseIsifindev.PROCEDIMIENTO_BUSQUEDA_CLIENTE + "(?, ?, ?); END;";

            try {

                CallableStatement declaracion = conexion.prepareCall(consulta);

                declaracion.setLong(1, Long.parseLong(numero_cliente));
                declaracion.setString(2, periodo);
                declaracion.registerOutParameter(3, OracleTypes.CURSOR);
                declaracion.execute();

                ResultSet registros = (ResultSet) declaracion.getObject(3);

                while (registros.next()) {

                    BusquedaModel registro = new BusquedaModel();

                    registro.setNumero_cuenta(registros.getLong(1) + "");
                    registro.setNombre_completo(registros.getString(2));
                    registro.setRegistro_federal(registros.getString(3));
                    registro.setNumero_cliente(registros.getLong(4) + "");
                    registro.setCorreo_electronico(registros.getString(5));
                    registro.setCuenta_clabe(registros.getString(6));

                    busqueda.add(registro);

                }

                cerrar_conexion(1, declaracion, registros, conexion, 0);

                return busqueda;

            } catch (Exception e) {

                log.error("ERROR BUSQUEDA CLIENTE");
                log.error("");
                log.error(e.getMessage());
                log.error("");
                log.error("");

                return busqueda;

            }

        } else {

            return busqueda;

        }

    }

    public List<BusquedaModel> busqueda_cuenta(String numero_cuenta, String periodo) {

        Connection conexion = abrir_conexion(1, 0);

        List<BusquedaModel> busqueda = new ArrayList<>();

        if (conexion != null) {

            String consulta = "BEGIN " + PropiedadesUtilities.BaseIsifindev.ESQUEMA_BASE_DATOS_ISI + "."
                    + PropiedadesUtilities.BaseIsifindev.PAQUETE_ESTADOS_CUENTA_ISI + "." + PropiedadesUtilities.BaseIsifindev.PROCEDIMIENTO_BUSQUEDA_CUENTA + "(?, ?, ?); END;";

            try {

                CallableStatement declaracion = conexion.prepareCall(consulta);

                declaracion.setLong(1, Long.parseLong(numero_cuenta));
                declaracion.setString(2, periodo);
                declaracion.registerOutParameter(3, OracleTypes.CURSOR);
                declaracion.execute();

                ResultSet registros = (ResultSet) declaracion.getObject(3);

                while (registros.next()) {

                    BusquedaModel registro = new BusquedaModel();

                    registro.setNumero_cuenta(registros.getLong(1) + "");
                    registro.setNombre_completo(registros.getString(2));
                    registro.setRegistro_federal(registros.getString(3));
                    registro.setNumero_cliente(registros.getLong(4) + "");
                    registro.setCorreo_electronico(registros.getString(5));
                    registro.setCuenta_clabe(registros.getString(6));

                    busqueda.add(registro);

                }

                cerrar_conexion(1, declaracion, registros, conexion, 0);

                return busqueda;

            } catch (Exception e) {

                log.error("ERROR BUSQUEDA CUENTA");
                log.error("");
                log.error(e.getMessage());
                log.error("");
                log.error("");

                return busqueda;

            }

        } else {

            return busqueda;

        }

    }

    public Integer registrar_reenvio(String nombre_completo, Long numero_cuenta, String correo_electronico, String periodo_cformato, String periodo_sformato, String usuario, Integer estatus) {

        Connection conexion = abrir_conexion(2, 0);

        if (conexion != null) {

            String consulta = "BEGIN " + PropiedadesUtilities.BaseMiddwadv.ESQUEMA_BASE_DATOS_MID + "."
                    + PropiedadesUtilities.BaseMiddwadv.PAQUETE_ESTADOS_CUENTA_MID + "." + PropiedadesUtilities.BaseMiddwadv.PROCEDIMIENTO_REGISTRAR_REENVIO + "(?, ?, ?, ?, ?, ?, ?, ?); END;";

            try {

                CallableStatement declaracion = conexion.prepareCall(consulta);

                declaracion.setString(1, nombre_completo);
                declaracion.setLong(2, numero_cuenta);
                declaracion.setString(3, correo_electronico);
                declaracion.setString(4, periodo_cformato);
                declaracion.setString(5, periodo_sformato);
                declaracion.setString(6, usuario);
                declaracion.setInt(7, estatus);
                declaracion.registerOutParameter(8, OracleTypes.NUMBER);
                declaracion.execute();

                Integer resultado = declaracion.getInt(8);
                cerrar_conexion(0, declaracion, null, conexion, 0);

                return resultado;

            } catch (Exception e) {

                log.error("ERROR REGISTRAR REENVIO");
                log.error("");
                log.error(e.getMessage());
                log.error("");
                log.error("");

                return 1;

            }

        } else {

            return 1;

        }

    }

    public synchronized void registrar_bitacora(Long numero_cuenta, String nombre_completo, String registro_federal, String cuenta_clabe,
            String correo_electronico, String periodo_cformato, String periodo_sformato, Integer estatus) {

        Connection conexion = abrir_conexion(2, 1);

        if (conexion != null) {

            String consulta = "BEGIN " + PropiedadesUtilities.BaseMiddwadv.ESQUEMA_BASE_DATOS_MID + "."
                    + PropiedadesUtilities.BaseMiddwadv.PAQUETE_ESTADOS_CUENTA_MID + "." + PropiedadesUtilities.BaseMiddwadv.PROCEDIMIENTO_REGISTRAR_BITACORA + "(?, ?, ?, ?, ?, ?, ?, ?, ?); END;";

            try {

                CallableStatement declaracion = conexion.prepareCall(consulta);

                declaracion.setLong(1, numero_cuenta);
                declaracion.setString(2, nombre_completo);
                declaracion.setString(3, registro_federal);
                declaracion.setString(4, cuenta_clabe);
                declaracion.setString(5, correo_electronico);
                declaracion.setString(6, periodo_cformato);
                declaracion.setString(7, periodo_sformato);
                declaracion.setInt(8, estatus);
                declaracion.registerOutParameter(9, OracleTypes.NUMBER);
                declaracion.execute();

                conexion.commit();
                cerrar_conexion(0, declaracion, null, conexion, 1);

            } catch (Exception e) {

                log.error("CODIGO (" + 9 + ") ERROR REGISTRAR BITACORA");
                log.error("");
                log.error(e.getMessage());
                log.error("");
                log.error("");

            }

        }

    }

    public synchronized void actualizar_estatus(Integer estatus, Long numero_cuenta) {

        Connection conexion = abrir_conexion(1, 1);

        if (conexion != null) {

            String consulta = "BEGIN " + PropiedadesUtilities.BaseIsifindev.ESQUEMA_BASE_DATOS_ISI + "."
                    + PropiedadesUtilities.BaseIsifindev.PAQUETE_ESTADOS_CUENTA_ISI + "." + PropiedadesUtilities.BaseIsifindev.PROCEDIMIENTO_ACTUALIZAR_ESTATUS + "(?, ?, ?); END;";

            try {

                CallableStatement declaracion = conexion.prepareCall(consulta);

                declaracion.setInt(1, estatus);
                declaracion.setLong(2, numero_cuenta);
                declaracion.registerOutParameter(3, OracleTypes.NUMBER);
                declaracion.execute();

                conexion.commit();
                cerrar_conexion(0, declaracion, null, conexion, 1);

            } catch (Exception e) {

                log.error("CODIGO (" + 11 + ") ERROR ACTUALIZAR ESTATUS");
                log.error("");
                log.error(e.getMessage());
                log.error("");
                log.error("");

            }

        }

    }

    public List<BusquedaModel> busqueda_cliente_hist(String numero_cliente, String periodo) {

        Connection conexion = abrir_conexion(1, 0);

        List<BusquedaModel> busqueda = new ArrayList<>();

        if (conexion != null) {

            String consulta = "BEGIN " + PropiedadesUtilities.BaseIsifindev.ESQUEMA_BASE_DATOS_ISI + "."
                    + PropiedadesUtilities.BaseIsifindev.PAQUETE_ESTADOS_CUENTA_ISI + "." + PropiedadesUtilities.BaseIsifindev.PROCEDIMIENTO_BUSQUEDA_CLIENTE_HIST + "(?, ?, ?); END;";

            try {

                CallableStatement declaracion = conexion.prepareCall(consulta);

                declaracion.setLong(1, Long.parseLong(numero_cliente));
                declaracion.setString(2, periodo);
                declaracion.registerOutParameter(3, OracleTypes.CURSOR);
                declaracion.execute();

                log.info("1");

                ResultSet registros = (ResultSet) declaracion.getObject(3);

                log.info("2");

                while (registros.next()) {

                    BusquedaModel registro = new BusquedaModel();

                    registro.setNumero_cuenta(registros.getLong(1) + "");
                    registro.setNombre_completo(registros.getString(2));
                    registro.setRegistro_federal(registros.getString(3));
                    registro.setNumero_cliente(registros.getLong(4) + "");
                    registro.setCorreo_electronico(registros.getString(5));
                    registro.setCuenta_clabe(registros.getString(6));

                    busqueda.add(registro);

                }

                cerrar_conexion(1, declaracion, registros, conexion, 0);

                return busqueda;

            } catch (Exception e) {

                log.info("ERROR: " + e);

                log.error("ERROR BUSQUEDA CLIENTE HISTORIAL");
                log.error("");
                log.error(e.getMessage());
                log.error("");
                log.error("");

                return busqueda;

            }

        } else {

            return busqueda;

        }

    }

}
