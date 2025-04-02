package model.entities;

public class Maquina extends BaseEntity {
    private String nome;
    private String descricao;
    private String fabricante;

    // Construtor
    public Maquina(String nome, String descricao, String fabricante) {
        this.nome = nome;
        this.descricao = descricao;
        this.fabricante = fabricante;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }
}