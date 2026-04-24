package com.mgcss.domain.tecnico;

import java.security.SecureRandom;

public class Tecnico {
	private Long id;
	private String nombre;
	private String especialidad;
	private boolean activo;
	
	private static SecureRandom random = new SecureRandom();
	
	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	public boolean getActivo() {
		return this.activo;
	}

	public static Tecnico crearTecnico(String nombre, String especialidad) {
		Tecnico tec = new Tecnico();
		tec.id = random.nextLong();
		tec.nombre = nombre;
		tec.especialidad = especialidad;
		tec.activo = true;
		
		return tec;
	}
	
	public static Tecnico crearTecnico(String nombre) {
		Tecnico tec = new Tecnico();
		tec.id = random.nextLong();
		tec.nombre = nombre;
		tec.activo = false;
		
		return tec;
	}
	public void activarTecnico() {
		if(this.especialidad != null) {
			this.activo=true;
		}
	}
}
