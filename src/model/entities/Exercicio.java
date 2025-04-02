package model.entities;

public abstract class Exercicio extends BaseEntity {
    protected String nome;
    protected String grupoMuscular;
    protected String descricao;
    
    
    public Exercicio(String nome, String grupoMuscular, String descricao) {
        this.nome = nome;
        this.grupoMuscular = grupoMuscular;
        this.descricao = descricao;
    }
    
    public abstract String getTipo();
    
    // Getters e Setters
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getGrupoMuscular() {
        return grupoMuscular;
    }
    
    public void setGrupoMuscular(String grupoMuscular) {
        this.grupoMuscular = grupoMuscular;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


}