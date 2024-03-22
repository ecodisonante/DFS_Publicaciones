package com.fullstack.publicaciones.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExepcionesController {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String notFoundUri() {
        return "Ooops!! Creo que te perdiste.";
    }

}
