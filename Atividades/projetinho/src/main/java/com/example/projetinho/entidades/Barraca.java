package com.example.projetinho.entidades;

public class Barraca {

    private String nome;
    private String produto;
    private int estoque;

    public Barraca(String nome, String produto, int estoque) {
        this.nome = nome;
        this.produto = produto;
        this.estoque = estoque;
    }

    public String vender(int qtd) {
        if (qtd <= 0) return "Quantidade inválida.";

        if (estoque >= qtd) {
            estoque -= qtd;
            return qtd + " produto(s) vendido(s).";
        }

        else {
            return "Estoque insuficiente.";
        }
    }

    public String repor(int qtd) {
        if (qtd <= 0) return "Quantidade inválida.";

        estoque += qtd;
        return qtd + " produto(s) adicionado(s).";
    }

    public String mostrarEstoque() {
        return nome + " | Produto: " + produto + " | Estoque: " + estoque;
    }
}