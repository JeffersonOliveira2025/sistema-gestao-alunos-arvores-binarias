package com.unicsul.projeto.service;

import com.unicsul.projeto.model.NoAluno;
import java.util.ArrayList;
import java.util.List;

public class ArvoreBinariaCampus {

    private NoAluno raiz;

    public ArvoreBinariaCampus() {
        this.raiz = null;
    }

    public boolean estaVazia() {
        return this.raiz == null;
    }

    public boolean inserir(String nome) {
        NoAluno novoNo = new NoAluno(nome);
        if (estaVazia()) {
            raiz = novoNo;
            return true;
        } else {
            return inserirRecursivo(raiz, novoNo);
        }
    }

    private boolean inserirRecursivo(NoAluno atual, NoAluno novoNo) {
        int comparacao = novoNo.getNome().compareToIgnoreCase(atual.getNome());
        
        if (comparacao < 0) {
            if (atual.esquerda == null) {
                atual.esquerda = novoNo;
                return true;
            } else {
                return inserirRecursivo(atual.esquerda, novoNo);
            }
        } else if (comparacao > 0) {
            if (atual.direita == null) {
                atual.direita = novoNo;
                return true;
            } else {
                return inserirRecursivo(atual.direita, novoNo);
            }
        } else {
            return false; // Nome já existe neste campus
        }
    }

    public boolean buscar(String nome) {
        return buscarRecursivo(raiz, nome);
    }

    private boolean buscarRecursivo(NoAluno atual, String nome) {
        if (atual == null) {
            return false;
        }
        int comparacao = nome.compareToIgnoreCase(atual.getNome());
        if (comparacao == 0) {
            return true;
        } else if (comparacao < 0) {
            return buscarRecursivo(atual.esquerda, nome);
        } else {
            return buscarRecursivo(atual.direita, nome);
        }
    }

    public List<String> listarEmOrdem() {
        List<String> lista = new ArrayList<>();
        emOrdemRecursivo(raiz, lista);
        return lista;
    }

    private void emOrdemRecursivo(NoAluno atual, List<String> lista) {
        if (atual != null) {
            emOrdemRecursivo(atual.esquerda, lista);
            lista.add(atual.getNome());
            emOrdemRecursivo(atual.direita, lista);
        }
    }
}