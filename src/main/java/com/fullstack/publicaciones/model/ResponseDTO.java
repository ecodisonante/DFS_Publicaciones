package com.fullstack.publicaciones.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDTO {

    int code;
    String message;

}
