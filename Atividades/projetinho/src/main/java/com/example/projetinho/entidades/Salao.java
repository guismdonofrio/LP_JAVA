package com.example.projetinho.entidades;

public class Salao {

    private int    id;
    private String nome;
    private int    funcionarios;
    private String servico;

    public Salao(String nome, int funcionarios, String servico) {
        this(0, nome, funcionarios, servico);
    }

    public Salao(int id, String nome, int funcionarios, String servico) {
        this.id           = id;
        this.nome         = nome;
        this.funcionarios = funcionarios;
        this.servico      = servico;
    }

    public String prestarServico() {
        return "Serviço realizado: " + servico;
    }

    public String contratar(int qtd) {
        if (qtd <= 0) return "Quantidade inválida.";
        funcionarios += qtd;
        return qtd + " funcionário(s) contratado(s). Total: " + funcionarios;
    }

    public String mostrarInfo() {
        return "[#" + id + "] " + nome + " | Funcionários: " + funcionarios + " | Serviço: " + servico;
    }


    public int    getId()          { return id; }
    public void   setId(int id)    { this.id = id; }

    public String getNome()             { return nome; }
    public void   setNome(String n)     { this.nome = n; }

    public int  getFuncionarios()        { return funcionarios; }
    public void setFuncionarios(int f)   { this.funcionarios = f; }

    public String getServico()              { return servico; }
    public void   setServico(String s)      { this.servico = s; }
}
