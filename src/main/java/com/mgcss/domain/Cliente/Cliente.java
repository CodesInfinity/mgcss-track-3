package com.mgcss.domain.Cliente;

import java.util.Random;

import com.mgcss.domain.Enums.TipoCliente;

public class Cliente {
	private Long id;
	private String nombre;
	private String email;
	private TipoCliente tipo;
	
	private static Random random = new Random();
	
	public Cliente(String nombre, String email, TipoCliente tipo) {
		this.id = random.nextLong();
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


	
	
}
