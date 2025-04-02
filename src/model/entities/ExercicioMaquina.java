package model.entities;

import model.interfaces.Exercicio;

public class ExercicioMaquina extends BaseEntity implements Exercicio {
    private String nome;
    private String grupoMuscular;
    private String descricao;
    private Maquina maquina;

    // Construtor
    public ExercicioMaquina(String nome, String grupoMuscular, String descricao, Maquina maquina) {
        this.nome = nome;
        this.grupoMuscular = grupoMuscular;
        this.descricao = descricao;
        this.maquina = maquina;
    }

    // Implementação da interface
    @Override
    public String getTipo() {
        return "Máquina";
    }

    // Getters e Setters
    @Override
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getGrupoMuscular() {
        return grupoMuscular;
    }

    public void setGrupoMuscular(String grupoMuscular) {
        this.grupoMuscular = grupoMuscular;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }
}