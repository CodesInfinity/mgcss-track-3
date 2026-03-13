package com.mgcss.domain;

import java.util.Date;

import com.mgcss.domain.Enums.Estado;

public class Solicitud {
	private Long id;
	private Cliente cliente;
	private String descripcion;
	private Date fechaCreacion;
	private Estado estado;
	private Tecnico tecnico;
	private Date fechaCierre;
	
	
	
	public void cerrar() {}
}
