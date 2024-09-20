package com.fincomun.model;

import lombok.Data;

@Data
public class EstadosCuentaModel {

    private String identificador_cuenta;

    private String nombre_completo;

    private String registro_federal;

    private String periodo_sf;

    private String periodo_cf;

    private String identificador_cliente;

    private String cuenta_clabe;

    private String correo_electronico;

}
