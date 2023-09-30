package com.company.rest.api.dto;

public class SolicitudResult {

	private final String persistence_id;
	private final String status;
	private final String fecha_inicio;
	private final String licenciatura_nombre;
	private final String periodo_de_ingreso_nombre;
	
	public SolicitudResult(String persistence_id, String status, String fecha_inicio, String licenciatura_nombre, String periodo_de_ingreso_nombre) {
        this.persistence_id = persistence_id;
		this.status = status;
        this.fecha_inicio = fecha_inicio;
        this.licenciatura_nombre = licenciatura_nombre;
        this.periodo_de_ingreso_nombre = periodo_de_ingreso_nombre;
    }

	public String getStatus() {
		return status;
	}

	public String getFecha_inicio() {
		return fecha_inicio;
	}

	public String getLicenciatura_nombre() {
		return licenciatura_nombre;
	}

	public String getPeriodo_de_ingreso_nombre() {
		return periodo_de_ingreso_nombre;
	}

	public String getPersistence_id() {
		return persistence_id;
	}
}
