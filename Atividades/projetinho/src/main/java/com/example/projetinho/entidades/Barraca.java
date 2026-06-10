package com.example.projetinho.entidades;

public class Barraca {

    private int    id;      // 0 = ainda não persistida
    private String nome;
    private String produto;
    private int    estoque;

    // Construtores
    public Barraca(String nome, String produto, int estoque) {
        this(0, nome, produto, estoque);
    }

    public Barraca(int id, String nome, String produto, int estoque) {
        this.id      = id;
        this.nome    = nome;
        this.produto = produto;
        this.estoque = estoque;
    }

    // Regras de negócio

    public String vender(int qtd) {
        if (qtd <= 0) return "Quantidade inválida.";
        if (estoque >= qtd) {
            estoque -= qtd;
            return qtd + " produto(s) vendido(s). Estoque atual: " + estoque;
        }
        return "Estoque insuficiente. Disponível: " + estoque;
    }

    public String repor(int qtd) {
        if (qtd <= 0) return "Quantidade inválida.";
        estoque += qtd;
        return qtd + " produto(s) adicionado(s). Estoque atual: " + estoque;
    }

    public String mostrarEstoque() {
        return "[#" + id + "] " + nome + " | Produto: " + produto + " | Estoque: " + estoque;
    }

    // Getters e Setter

    public int    getId()      { return id; }
    public void   setId(int id){ this.id = id; }

    public String getNome()         { return nome; }
    public void   setNome(String n) { this.nome = n; }

    public String getProduto()           { return produto; }
    public void   setProduto(String p)   { this.produto = p; }

    public int  getEstoque()        { return estoque; }
    public void setEstoque(int e)   { this.estoque = e; }
}
