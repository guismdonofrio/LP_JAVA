package com.example.projetinho.entidades;

// Representa um medicamento cadastrado no estoque de uma farmácia.
// Guarda informações sobre dosagem, fabricante e quantidade disponível.
public class Medicamento {

    private int    id;
    private int    idFarmacia;
    private String nome;
    private String fabricante;
    private String dosagem;
    private int    estoque;
    private double preco;

    public Medicamento(int idFarmacia, String nome, String fabricante, String dosagem, int estoque, double preco) {
        this(0, idFarmacia, nome, fabricante, dosagem, estoque, preco);
    }

    public Medicamento(int id, int idFarmacia, String nome, String fabricante,
                       String dosagem, int estoque, double preco) {
        this.id         = id;
        this.idFarmacia = idFarmacia;
        this.nome       = nome;
        this.fabricante = fabricante;
        this.dosagem    = dosagem;
        this.estoque    = estoque;
        this.preco      = preco;
    }

    // Dispensa (vende) uma quantidade do medicamento.
    public String dispensar(int qtd) {
        if (qtd <= 0) return "Quantidade inválida.";
        if (estoque < qtd) return "Estoque insuficiente. Disponível: " + estoque;
        estoque -= qtd;
        return qtd + " unidade(s) dispensada(s). Estoque atual: " + estoque;
    }

    // Repõe unidades no estoque.
    public String repor(int qtd) {
        if (qtd <= 0) return "Quantidade inválida.";
        estoque += qtd;
        return "Estoque reposto. Total agora: " + estoque;
    }

    public String resumo() {
        return "[#" + id + "] " + nome + " " + dosagem + " | " + fabricante
                + " | Estoque: " + estoque + " | R$ " + String.format("%.2f", preco);
    }

    public int    getId()                  { return id; }
    public void   setId(int id)            { this.id = id; }

    public int    getIdFarmacia()              { return idFarmacia; }
    public void   setIdFarmacia(int v)         { this.idFarmacia = v; }

    public String getNome()                { return nome; }
    public void   setNome(String n)        { this.nome = n; }

    public String getFabricante()              { return fabricante; }
    public void   setFabricante(String f)      { this.fabricante = f; }

    public String getDosagem()             { return dosagem; }
    public void   setDosagem(String d)     { this.dosagem = d; }

    public int    getEstoque()             { return estoque; }
    public void   setEstoque(int e)        { this.estoque = e; }

    public double getPreco()               { return preco; }
    public void   setPreco(double p)       { this.preco = p; }
}
