package com.fincomun.utilities;

import java.sql.Connection;
import javax.sql.DataSource;

public class ConexionUtilities {

    public Connection conexion(Integer tipo_conexion) {

        try {

            if (tipo_conexion == 1) {

                DataSource conexion = LocalizadorUtilities.obtener_instancia().informacion_isiedoctacred();
                return conexion.getConnection();

            } else {

                DataSource conexion = LocalizadorUtilities.obtener_instancia().informacion_oracle();
                return conexion.getConnection();

            }

        } catch (Exception e) {

            return null;

        }

    }

}
