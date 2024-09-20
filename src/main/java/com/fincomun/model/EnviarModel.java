package com.fincomun.model;

import lombok.Data;

@Data
public class EnviarModel {

    private String correo_electronico;

    private String nombre_completo;

    private String numero_cuenta;

    private String periodo_cformato;

    private String periodo_sformato;

    private String usuario;

}
