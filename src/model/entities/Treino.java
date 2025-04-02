package model.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Treino extends BaseEntity {
    private Usuario usuario;
    private String nome;
    private String descricao;
    private LocalDateTime dataCriacao;
    private List<TreinoExercicio> exercicios;

    // Construtor
    public Treino(Usuario usuario, String nome, String descricao) {
        this.usuario = usuario;
        this.nome = nome;
        this.descricao = descricao;
        this.dataCriacao = LocalDateTime.now();
        this.exercicios = new ArrayList<>();
    }

    // Getters e Setters
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

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

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public List<TreinoExercicio> getExercicios() {
        return exercicios;
    }

    public void adicionarExercicio(TreinoExercicio exercicio) {
        exercicios.add(exercicio);
    }

    public void removerExercicio(TreinoExercicio exercicio) {
        exercicios.remove(exercicio);
    }

    public void setDataCriacao(LocalDateTime toLocalDateTime) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}