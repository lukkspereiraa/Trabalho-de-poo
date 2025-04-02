package model.entities;

import model.interfaces.Exercicio;

public class ExercicioLivre extends BaseEntity implements Exercicio {
    private String nome;
    private String grupoMuscular;
    private String descricao;

    // Construtor
    public ExercicioLivre(String nome, String grupoMuscular, String descricao) {
        this.nome = nome;
        this.grupoMuscular = grupoMuscular;
        this.descricao = descricao;
    }

    // Implementação da interface
    @Override
    public String getTipo() {
        return "Livre";
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
}
