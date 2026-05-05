package com.mgcss.domain.solicitud; 

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.mgcss.domain.tecnico.Tecnico;
import com.mgcss.domain.cliente.Cliente;
import com.mgcss.domain.enums.Estado;

public class Solicitud {
    private Long id;
    private Cliente cliente;
    private String descripcion;
    private Date fechaCreacion;
    private Estado estado;
    private Tecnico tecnico;
    private Date fechaCierre;
    private List<EstadoChange> historico = new ArrayList<>();
    
    private static final SecureRandom random = new SecureRandom();

    public record EstadoChange(Estado estado, Date fecha) {}
    
    public Solicitud() {
        this.id = random.nextLong();
        this.estado = Estado.ABIERTA;
        registrarCambio(Estado.ABIERTA);
    }

    private void registrarCambio(Estado nuevoEstado) {
        this.historico.add(new EstadoChange(nuevoEstado, new Date()));
    }

    public void cerrar() {    
        if (this.estado == Estado.EN_PROCESO) {
            this.estado = Estado.CERRADA;
            this.fechaCierre = new Date();
            registrarCambio(Estado.CERRADA);
        }
    }
    
    public void reabrir() {
        if (this.estado == Estado.CERRADA) {
            this.estado = Estado.EN_PROCESO;
            this.fechaCierre = null;
            registrarCambio(Estado.EN_PROCESO);
        }
    }
    
    public void asignarTecnico(Tecnico tecnico) {
        if (tecnico.getActivo() && this.estado != Estado.CERRADA) {
            this.tecnico = tecnico;
            this.estado = Estado.EN_PROCESO;
            registrarCambio(Estado.EN_PROCESO);
        }
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

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) {
        this.estado = estado;
        registrarCambio(estado);
    }

    public Tecnico getTecnico() { return tecnico; }
    public void setTecnico(Tecnico tecnico) {
        if (tecnico.isActivo()) {
            this.tecnico = tecnico;
        }
    }

    public Date getFechaCierre() { return fechaCierre; }
    public void setFechaCierre(Date fechaCierre) { this.fechaCierre = fechaCierre; }
}