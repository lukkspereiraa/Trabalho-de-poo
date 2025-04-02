package model.entities;

import model.interfaces.Exercicio;

public class TreinoExercicio extends BaseEntity {
    private Treino treino;
    private Exercicio exercicio;
    private int series;
    private int repeticoes;
    private Double carga;
    private String diaSemana;

    // Construtor
    public TreinoExercicio(Treino treino, Exercicio exercicio, int series, int repeticoes, Double carga, String diaSemana) {
        this.treino = treino;
        this.exercicio = exercicio;
        this.series = series;
        this.repeticoes = repeticoes;
        this.carga = carga;
        this.diaSemana = diaSemana;
    }

    // Getters e Setters
    public Treino getTreino() {
        return treino;
    }

    public void setTreino(Treino treino) {
        this.treino = treino;
    }

    public Exercicio getExercicio() {
        return exercicio;
    }

    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getRepeticoes() {
        return repeticoes;
    }

    public void setRepeticoes(int repeticoes) {
        this.repeticoes = repeticoes;
    }

    public Double getCarga() {
        return carga;
    }

    public void setCarga(Double carga) {
        this.carga = carga;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }
}