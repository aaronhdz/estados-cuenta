package com.fincomun.controller;

import lombok.extern.slf4j.Slf4j;
import com.fincomun.model.EnviarModel;
import com.fincomun.model.ConsultarModel;
import com.fincomun.model.HistoricoModel;
import org.springframework.http.ResponseEntity;
import com.fincomun.service.EstadosCuentaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@RestController
@RequestMapping("/middleware")
public class EstadosCuentaController {

    @Autowired
    private EstadosCuentaService servicio;

    @GetMapping("/proceso/{periodo}")
    public String proceso(@PathVariable(required = false) Integer periodo) {

        log.info("# . # . # . # . # . # . # . # . # . # . # . # . # . # . #");
        log.info("");
        log.info("INCIO PROCESO MASIVO");
        log.info("");
        log.info("");

        servicio.ejecutar(periodo);

        return "PROCESO MASIVO INICIALIZADO";

    }

    @PostMapping("/consultar")
    public ResponseEntity<Object> consultar(@RequestBody(required = false) ConsultarModel peticion) {

        log.info("# . # . # . # . # . # . # . # . # . # . # . # . # . # . #");
        log.info("");
        log.info("METODO CONSULTAR");
        log.info("");
        log.info("");

        return servicio.informacion(peticion);

    }

    @PostMapping("/enviar")
    public ResponseEntity<Object> enviar(@RequestBody(required = false) EnviarModel peticion) {

        log.info("# . # . # . # . # . # . # . # . # . # . # . # . # . # . #");
        log.info("");
        log.info("METODO ENVIAR");
        log.info("");
        log.info("");

        return servicio.envio(peticion);

    }

    @PostMapping("/consultar/historico")
    public ResponseEntity<Object> cliente_historico(@RequestBody(required = false) HistoricoModel peticion) {

        log.info("# . # . # . # . # . # . # . # . # . # . # . # . # . # . #");
        log.info("");
        log.info("METODO CONSULTAR HISTORICO CLIENTE");
        log.info("");
        log.info("");

        return servicio.consultar_historial(peticion);

    }

}
