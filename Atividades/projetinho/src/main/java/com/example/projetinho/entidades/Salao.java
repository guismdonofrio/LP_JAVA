package com.example.projetinho.entidades;

public class Salao {

    private String nome;
    private int funcionarios;
    private String servico;

    public Salao(String nome, int funcionarios, String servico) {
        this.nome = nome;
        this.funcionarios = funcionarios;
        this.servico = servico;
    }

    public String prestarServico() {
        return "Serviço realizado: " + servico;
    }

    public String contratar(int qtd) {
        if (qtd <= 0) return "Quantidade inválida.";

        funcionarios += qtd;
        return qtd + " funcionário(s) contratado(s).";
    }

    public String mostrarInfo() {
        return nome + " | Funcionários: " + funcionarios + " | Serviço: " + servico;
    }
}