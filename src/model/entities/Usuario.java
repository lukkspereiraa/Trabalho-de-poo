package model.entities;

import java.time.LocalDate;

public class Usuario extends BaseEntity {
    private String nome;
    private String email;
    private String senhaHash;
    private LocalDate dataNascimento;
    private String genero;

    // Construtor
    public Usuario(String nome, String email, String senhaHash, LocalDate dataNascimento, String genero) {
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
