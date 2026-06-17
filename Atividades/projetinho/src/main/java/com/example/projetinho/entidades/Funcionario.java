package com.example.projetinho.entidades;

// Representa um funcionário de um salão de beleza.
// Registra nome, cargo (ex: cabeleireiro, manicure) e salário.
public class Funcionario {

    private int    id;
    private int    idSalao;
    private String nome;
    private String cargo;
    private double salario;
    private String telefone;

    public Funcionario(int idSalao, String nome, String cargo, double salario, String telefone) {
        this(0, idSalao, nome, cargo, salario, telefone);
    }

    public Funcionario(int id, int idSalao, String nome, String cargo, double salario, String telefone) {
        this.id       = id;
        this.idSalao  = idSalao;
        this.nome     = nome;
        this.cargo    = cargo;
        this.salario  = salario;
        this.telefone = telefone;
    }

    public String resumo() {
        return "[#" + id + "] " + nome + " | " + cargo
                + " | R$ " + String.format("%.2f", salario)
                + " | Tel: " + telefone;
    }

    public int    getId()                  { return id; }
    public void   setId(int id)            { this.id = id; }

    public int    getIdSalao()             { return idSalao; }
    public void   setIdSalao(int v)        { this.idSalao = v; }

    public String getNome()                { return nome; }
    public void   setNome(String n)        { this.nome = n; }

    public String getCargo()               { return cargo; }
    public void   setCargo(String c)       { this.cargo = c; }

    public double getSalario()             { return salario; }
    public void   setSalario(double s)     { this.salario = s; }

    public String getTelefone()            { return telefone; }
    public void   setTelefone(String t)    { this.telefone = t; }
}
