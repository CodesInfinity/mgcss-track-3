package com.mgcss.domain.tecnico;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tecnico {
	private Long id;
	private String nombre;
	private String especialidad;
	private boolean activo;
	
	public static Tecnico crearTecnico(String nombre, String especialidad) {
		Tecnico tec = new Tecnico();
		tec.nombre = nombre;
		tec.especialidad = especialidad;
		tec.activo = true;
		
		return tec;
	}
	
	public static Tecnico crearTecnico(String nombre) {
		Tecnico tec = new Tecnico();
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
