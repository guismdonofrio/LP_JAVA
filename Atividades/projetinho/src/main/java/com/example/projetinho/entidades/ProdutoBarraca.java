package com.example.projetinho.entidades;

// Representa um produto específico vendido em uma barraca de feira.
// Cada produto pertence a uma barraca (via idBarraca) e possui preço e estoque próprios.
public class ProdutoBarraca {

    private int    id;
    private int    idBarraca;
    private String nome;
    private double preco;
    private int    estoque;

    public ProdutoBarraca(int idBarraca, String nome, double preco, int estoque) {
        this(0, idBarraca, nome, preco, estoque);
    }

    public ProdutoBarraca(int id, int idBarraca, String nome, double preco, int estoque) {
        this.id        = id;
        this.idBarraca = idBarraca;
        this.nome      = nome;
        this.preco     = preco;
        this.estoque   = estoque;
    }

    // Verifica se há estoque suficiente para a venda.
    public boolean temEstoque(int qtd) {
        return estoque >= qtd;
    }

    // Desconta do estoque e retorna o valor total da venda.
    public double vender(int qtd) {
        if (qtd <= 0 || !temEstoque(qtd)) throw new IllegalArgumentException("Estoque insuficiente ou quantidade inválida.");
        estoque -= qtd;
        return preco * qtd;
    }

    public String resumo() {
        return "[#" + id + "] " + nome + " | R$ " + String.format("%.2f", preco) + " | Estoque: " + estoque;
    }

    public int    getId()              { return id; }
    public void   setId(int id)        { this.id = id; }

    public int    getIdBarraca()           { return idBarraca; }
    public void   setIdBarraca(int v)      { this.idBarraca = v; }

    public String getNome()            { return nome; }
    public void   setNome(String n)    { this.nome = n; }

    public double getPreco()           { return preco; }
    public void   setPreco(double p)   { this.preco = p; }

    public int    getEstoque()         { return estoque; }
    public void   setEstoque(int e)    { this.estoque = e; }
}
