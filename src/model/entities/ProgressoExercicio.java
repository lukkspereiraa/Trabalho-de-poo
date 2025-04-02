package model.entities;

import java.time.LocalDate;
import model.interfaces.Exercicio;

public class ProgressoExercicio extends BaseEntity {
    private Usuario usuario;
    private Exercicio exercicio;
    private LocalDate dataExecucao;
    private Double cargaUtilizada;
    private int seriesRealizadas;
    private int repeticoesRealizadas;
    private String observacoes;

    // Construtor
    public ProgressoExercicio(Usuario usuario, Exercicio exercicio, LocalDate dataExecucao, Double cargaUtilizada, 
                             int seriesRealizadas, int repeticoesRealizadas, String observacoes) {
        this.usuario = usuario;
        this.exercicio = exercicio;
        this.dataExecucao = dataExecucao;
        this.cargaUtilizada = cargaUtilizada;
        this.seriesRealizadas = seriesRealizadas;
        this.repeticoesRealizadas = repeticoesRealizadas;
        this.observacoes = observacoes;
    }

    // Getters e Setters
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Exercicio getExercicio() {
        return exercicio;
    }

    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    public LocalDate getDataExecucao() {
        return dataExecucao;
    }

    public void setDataExecucao(LocalDate dataExecucao) {
        this.dataExecucao = dataExecucao;
    }

    public Double getCargaUtilizada() {
        return cargaUtilizada;
    }

    public void setCargaUtilizada(Double cargaUtilizada) {
        this.cargaUtilizada = cargaUtilizada;
    }

    public int getSeriesRealizadas() {
        return seriesRealizadas;
    }

    public void setSeriesRealizadas(int seriesRealizadas) {
        this.seriesRealizadas = seriesRealizadas;
    }

    public int getRepeticoesRealizadas() {
        return repeticoesRealizadas;
    }

    public void setRepeticoesRealizadas(int repeticoesRealizadas) {
        this.repeticoesRealizadas = repeticoesRealizadas;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}