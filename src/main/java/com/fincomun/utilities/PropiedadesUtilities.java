package com.fincomun.utilities;

public class PropiedadesUtilities {

    public static class Weblogic {

        public Weblogic() {

        }

        public static final String WEBLOGIC_USUARIO = ConfiguracionPropiedadesUtilities.propiedad("weblogic.usuario");
        public static final String WEBLOGIC_CLAVE = ConfiguracionPropiedadesUtilities.propiedad("weblogic.clave");

    }

    public static class ServidorCorreo {

        public ServidorCorreo() {

        }

        public static final String AUTENTICACION_SERVIDOR_CORREO = ConfiguracionPropiedadesUtilities.propiedad("autenticacion.servidor.correo");

        public static final String SOBRESALTOS_SERVIDOR_CORREO = ConfiguracionPropiedadesUtilities.propiedad("sobresaltos.servidor.correo");

        public static final String DIRECCION_SERVIDOR_CORREO = ConfiguracionPropiedadesUtilities.propiedad("direccion.servidor.correo");

        public static final String PUERTO_SERVIDOR_CORREO = ConfiguracionPropiedadesUtilities.propiedad("puerto.servidor.correo");

        public static final String TIEMPO_ESPERA_CORREO = ConfiguracionPropiedadesUtilities.propiedad("tiempo.espera.correo");

        public static final String ENVIO_CORREO = ConfiguracionPropiedadesUtilities.propiedad("envio.correo");

        public static final String ASUNTO_CORREO = ConfiguracionPropiedadesUtilities.propiedad("asunto.correo");

    }

    public static class BaseMiddwadv {

        public BaseMiddwadv() {

        }

        public static final String ESQUEMA_BASE_DATOS_MID = ConfiguracionPropiedadesUtilities.propiedad("esquema.base.datos.mid");

        public static final String PAQUETE_ESTADOS_CUENTA_MID = ConfiguracionPropiedadesUtilities.propiedad("paquete.estados.cuenta.mid");

        public static final String PROCEDIMIENTO_REGISTRAR_REENVIO = ConfiguracionPropiedadesUtilities.propiedad("procedimiento.registrar.reenvio");

        public static final String PROCEDIMIENTO_REGISTRAR_BITACORA = ConfiguracionPropiedadesUtilities.propiedad("procedimiento.registrar.bitacora");

    }

    public static class BaseIsifindev {

        public BaseIsifindev() {

        }

        public static final String ESQUEMA_BASE_DATOS_ISI = ConfiguracionPropiedadesUtilities.propiedad("esquema.base.datos.isi");

        public static final String PAQUETE_ESTADOS_CUENTA_ISI = ConfiguracionPropiedadesUtilities.propiedad("paquete.estados.cuenta.isi");

        public static final String PROCEDIMIENTO_BUSQUEDA_NOMBRE = ConfiguracionPropiedadesUtilities.propiedad("procedimiento.busqueda.nombre");

        public static final String PROCEDIMIENTO_BUSQUEDA_REGISTRO = ConfiguracionPropiedadesUtilities.propiedad("procedimiento.busqueda.registro");

        public static final String PROCEDIMIENTO_BUSQUEDA_CLIENTE = ConfiguracionPropiedadesUtilities.propiedad("procedimiento.busqueda.cliente");

        public static final String PROCEDIMIENTO_BUSQUEDA_CUENTA = ConfiguracionPropiedadesUtilities.propiedad("procedimiento.busqueda.cuenta");

        public static final String PROCEDIMIENTO_BUSQUEDA_CLIENTES = ConfiguracionPropiedadesUtilities.propiedad("procedimiento.busqueda.clientes");

        public static final String PROCEDIMIENTO_ACTUALIZAR_ESTATUS = ConfiguracionPropiedadesUtilities.propiedad("procedimiento.actualizar.estatus");

        public static final String PROCEDIMIENTO_BUSQUEDA_CLIENTE_HIST = ConfiguracionPropiedadesUtilities.propiedad("procedimiento.busqueda.cliente.hist");

    }

    public static class ProcesoMasivo {

        public ProcesoMasivo() {

        }

        public static final String NUMERO_REGISTROS_PAQUETE = ConfiguracionPropiedadesUtilities.propiedad("numero.registros.paquete");

        public static final String NUMERO_HILOS_EJECUTAR = ConfiguracionPropiedadesUtilities.propiedad("numero.hilos.ejecutar");

        public static final String TIEMPO_ESPERA_ENVIO = ConfiguracionPropiedadesUtilities.propiedad("tiempo.espera.envio");

        public static final String REGISTRO_PROCESAR_TIEMPO = ConfiguracionPropiedadesUtilities.propiedad("registro.procesar.tiempo");

        public static final String CORREO_REPORTE_MASIVO = ConfiguracionPropiedadesUtilities.propiedad("correo.reporte.masivo");

        public static final String ASUNTO_REPORTE_MASIVO = ConfiguracionPropiedadesUtilities.propiedad("asunto.reporte.masivo");
        
        public static final String CORREO_NOPROPORCIONADO_MASIVO = ConfiguracionPropiedadesUtilities.propiedad("correo.noproporcionado.masivo");

    }

    public static class ServicioEstadoCuenta {

        public ServicioEstadoCuenta() {

        }

        public static final String DOMINIO_SERVICIO_ESTADO_CUENTA = ConfiguracionPropiedadesUtilities.propiedad("dominio.servicio.estado.cuenta");

        public static final String RUTA_SERVICIO_ESTADO_CUENTA = ConfiguracionPropiedadesUtilities.propiedad("ruta.servicio.estado.cuenta");

    }

}
