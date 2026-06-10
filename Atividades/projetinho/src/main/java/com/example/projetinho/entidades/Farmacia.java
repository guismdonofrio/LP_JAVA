package com.example.projetinho.entidades;

public class Farmacia {

    private int    id;
    private String nome;
    private String endereco;
    private int    estoque;

    public Farmacia(String nome, String endereco, int estoque) {
        this(0, nome, endereco, estoque);
    }

    public Farmacia(int id, String nome, String endereco, int estoque) {
        this.id       = id;
        this.nome     = nome;
        this.endereco = endereco;
        this.estoque  = estoque;
    }

    public String vender(int qtd) {
        if (qtd <= 0) return "Quantidade inválida.";
        if (estoque >= qtd) {
            estoque -= qtd;
            return qtd + " medicamento(s) vendido(s). Estoque atual: " + estoque;
        }
        return "Estoque insuficiente. Disponível: " + estoque;
    }

    public String comprar(int qtd) {
        if (qtd <= 0) return "Quantidade inválida.";
        estoque += qtd;
        return qtd + " medicamento(s) comprado(s). Estoque atual: " + estoque;
    }

    public String mostrarEstoque() {
        return "[#" + id + "] " + nome + " | Endereço: " + endereco + " | Estoque: " + estoque;
    }


    public int    getId()          { return id; }
    public void   setId(int id)    { this.id = id; }

    public String getNome()             { return nome; }
    public void   setNome(String n)     { this.nome = n; }

    public String getEndereco()             { return endereco; }
    public void   setEndereco(String e)     { this.endereco = e; }

    public int  getEstoque()        { return estoque; }
    public void setEstoque(int e)   { this.estoque = e; }
}
