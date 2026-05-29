package com.mgcss.domain.solicitud; 

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.mgcss.domain.tecnico.Tecnico;

import lombok.NoArgsConstructor;

import com.mgcss.domain.cliente.Cliente;
import com.mgcss.domain.enums.Estado;

@NoArgsConstructor
public class Solicitud {
    private Long id;
    private Cliente cliente;
    private String descripcion;
    private Date fechaCreacion;
    private Estado estado;
    private Tecnico tecnico;
    private Date fechaCierre;
    private List<EstadoChange> historico = new ArrayList<>();
    
    public record EstadoChange(Estado estado, Date fecha) {}
    
    private void registrarCambio(Estado nuevoEstado) {
        this.historico.add(new EstadoChange(nuevoEstado, new Date()));
    }
    
    public static Solicitud nuevaSolicitud(String descripcion) {
        Solicitud solicitud = new Solicitud();
        solicitud.descripcion = descripcion;
        solicitud.fechaCreacion = new Date();
        solicitud.estado = Estado.ABIERTA;
        solicitud.historico.add(new EstadoChange(Estado.ABIERTA, solicitud.fechaCreacion));
        return solicitud;
    }

    // --- Lógica de Negocio Refactorizada ---

    public void cerrar() {    
        if (this.estado == Estado.EN_PROCESO) {
            setEstado(Estado.CERRADA); // El setter se encarga del histórico
            this.fechaCierre = new Date();
        }
    }
    
    public void reabrir() {
        if (this.estado == Estado.CERRADA) {
            setEstado(Estado.EN_PROCESO); // El setter se encarga del histórico
            this.fechaCierre = null;
        }
    }
    
    public void asignarTecnico(Tecnico tecnico) {
        if (tecnico.isActivo() && this.estado != Estado.CERRADA) {
            this.tecnico = tecnico;
            setEstado(Estado.EN_PROCESO); // El setter se encarga del histórico
        }
    }
    
    public void asignarCliente(Cliente cliente) {
    	if (cliente != null) {
    		this.cliente = cliente;
    	}
    }

    // --- Getters y Setters ---

    public Estado getEstado() { return estado; }
    
    public void setEstado(Estado estado) {
        this.estado = estado;
        registrarCambio(estado);
    }

    public List<EstadoChange> getHistorico() {
        return Collections.unmodifiableList(historico);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public Tecnico getTecnico() { return tecnico; }
    public void setTecnico(Tecnico tecnico) {
        if (tecnico.isActivo()) {
            this.tecnico = tecnico;
        }
    }

    public Date getFechaCierre() { return fechaCierre; }
    public void setFechaCierre(Date fechaCierre) { this.fechaCierre = fechaCierre; }
}