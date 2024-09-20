package com.fincomun.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class AvisoController {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> incorrecta() {

        HashMap<String, String> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        resultado.put("codigo", "-50");
        resultado.put("descripcion", "Solicitud incorrecta.");
        respuesta.put("resultado", resultado);

        return new ResponseEntity(respuesta, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<Object> vacio() {

        HashMap<String, String> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        resultado.put("codigo", "-51");
        resultado.put("descripcion", "Ingresar periodo.");

        respuesta.put("resultado", resultado);
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> parametro() {

        HashMap<String, String> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        resultado.put("codigo", "-52");
        resultado.put("descripcion", "El periodo debe ser numérico.");

        respuesta.put("resultado", resultado);
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> operacion() {

        HashMap<String, String> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        resultado.put("codigo", "-53");
        resultado.put("descripcion", "Tipo de operación incorrecta.");

        respuesta.put("resultado", resultado);
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }

}
