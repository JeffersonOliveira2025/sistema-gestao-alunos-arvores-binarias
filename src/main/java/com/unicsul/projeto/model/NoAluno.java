package com.unicsul.projeto.model;

public class NoAluno {
    
    private String nome;
    
    // Ponteiros para a Árvore Binária
    public NoAluno esquerda;
    public NoAluno direita;

    public NoAluno(String nome) {
        this.nome = nome;
        this.esquerda = null;
        this.direita = null;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}