package com.example.projetinho.entidades;

public class Farmacia {

    private String nome;
    private String endereco;
    private int estoque;

    public Farmacia(String nome, String endereco, int estoque) {
        this.nome = nome;
        this.endereco = endereco;
        this.estoque = estoque;
    }

    public String vender(int qtd) {
        if (qtd <= 0) return "Quantidade inválida.";

        if (estoque >= qtd) {
            estoque -= qtd;
            return qtd + " medicamento(s) vendido(s).";
        }

        else {
            return "Estoque insuficiente.";
        }
    }

    public String comprar(int qtd) {
        if (qtd <= 0) return "Quantidade inválida.";

        estoque += qtd;
        return qtd + " medicamento(s) comprado(s).";
    }

    public String mostrarEstoque() {
        return nome + " | Endereço: " + endereco + " | Estoque: " + estoque;
    }
}