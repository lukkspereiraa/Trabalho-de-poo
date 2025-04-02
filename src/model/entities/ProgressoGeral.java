package model.entities;

import java.time.LocalDate;

public class ProgressoGeral extends BaseEntity {
    private Usuario usuario;
    private LocalDate dataRegistro;
    private Double peso;
    private Double percentualGordura;
    private String observacoes;

    // Construtor
    public ProgressoGeral(Usuario usuario, LocalDate dataRegistro, Double peso, Double percentualGordura, String observacoes) {
        this.usuario = usuario;
        this.dataRegistro = dataRegistro;
        this.peso = peso;
        this.percentualGordura = percentualGordura;
        this.observacoes = observacoes;
    }

    // Getters e Setters
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getPercentualGordura() {
        return percentualGordura;
    }

    public void setPercentualGordura(Double percentualGordura) {
        this.percentualGordura = percentualGordura;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}