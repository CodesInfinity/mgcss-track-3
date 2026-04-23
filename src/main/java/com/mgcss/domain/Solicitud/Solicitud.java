package com.mgcss.domain.Solicitud; 

import java.security.SecureRandom;
import java.util.Date;

import com.mgcss.domain.Enums.Estado;
import com.mgcss.domain.Tecnico.Tecnico;
import com.mgcss.domain.Cliente.Cliente;


public class Solicitud {
	private Long id;
	private Cliente cliente;
	private String descripcion;
	private Date fechaCreacion;
	private Estado estado;
	private Tecnico tecnico;
	private Date fechaCierre;
	
	private static SecureRandom random = new SecureRandom();
	
	public Solicitud()
	{
		this.id = random.nextLong();
		this.estado = Estado.ABIERTA;
	}
	
	public void setId(Long id) {
	    this.id = id;
	}
	
	public Long getId() {
	    return id;
	}

	public Cliente getCliente() {
	    return cliente;
	}

	public void setCliente(Cliente cliente) {
	    this.cliente = cliente;
	}

	public String getDescripcion() {
	    return descripcion;
	}

	public void setDescripcion(String descripcion) {
	    this.descripcion = descripcion;
	}

	public Date getFechaCreacion() {
	    return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
	    this.fechaCreacion = fechaCreacion;
	}

	public Estado getEstado() {
	    return estado;
	}

	public void setEstado(Estado estado) {
	    this.estado = estado;
	}

	public Tecnico getTecnico() {
	    return tecnico;
	}

	public void setTecnico(Tecnico tecnico) {
		if(tecnico.isActivo())
			this.tecnico = tecnico;
	}

	public Date getFechaCierre() {
	    return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
	    this.fechaCierre = fechaCierre;
	}
	
	
	
	public void cerrar() {	
		if(this.estado == Estado.EN_PROCESO) {
			this.estado = Estado.CERRADA;
			this.fechaCierre = new Date();
		}
	}
	
	public void asignarTecnico(Tecnico tecnico) {
		if(tecnico.getActivo() && this.estado != Estado.CERRADA) {
			this.tecnico = tecnico;
			this.estado = Estado.EN_PROCESO;
		}
		
	}
}
