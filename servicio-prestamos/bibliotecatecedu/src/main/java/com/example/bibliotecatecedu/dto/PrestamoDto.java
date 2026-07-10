package com.example.bibliotecatecedu.dto;

import lombok.Data;
import java.util.Date;

@Data
public class PrestamoDto {
    private Long idLibro;
    private String runSolicitante;
    private Date fechaSolicitud;
    private Date fechaEntrega;
    private int cantidadDias;
    private int multas;
}