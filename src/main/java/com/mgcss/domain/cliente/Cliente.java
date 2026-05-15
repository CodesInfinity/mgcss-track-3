package com.mgcss.domain.cliente;

import com.mgcss.domain.enums.TipoCliente;

public class Cliente {
	private Long id;
	private String nombre;
	private String email;
	private TipoCliente tipo;

	private static Long contadorId = 1L;
	
	public Cliente(String nombre, String email, TipoCliente tipo) {
		this.id = Cliente.contadorId++;
		this.nombre = nombre;
		this.email = email;
		this.tipo = tipo;
	}

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getEmail() {
		return email;
	}

	public TipoCliente getTipo() {
		return tipo;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
