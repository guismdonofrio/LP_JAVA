package com.example.projetinho.entidades;

import java.time.LocalDate;

// Representa uma venda realizada em uma barraca de feira.
// Registra qual produto foi vendido, a quantidade e o valor total.
public class VendaBarraca {

    private int        id;
    private int        idBarraca;
    private String     produto;
    private int        quantidade;
    private double     valorTotal;
    private LocalDate  data;

    public VendaBarraca(int idBarraca, String produto, int quantidade, double valorTotal, LocalDate data) {
        this(0, idBarraca, produto, quantidade, valorTotal, data);
    }

    public VendaBarraca(int id, int idBarraca, String produto, int quantidade, double valorTotal, LocalDate data) {
        this.id         = id;
        this.idBarraca  = idBarraca;
        this.produto    = produto;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
        this.data       = data;
    }

    public String resumo() {
        return "[#" + id + "] Barraca #" + idBarraca + " | " + produto
                + " x" + quantidade + " | R$ " + String.format("%.2f", valorTotal)
                + " | " + data;
    }

    public int       getId()               { return id; }
    public void      setId(int id)         { this.id = id; }

    public int       getIdBarraca()            { return idBarraca; }
    public void      setIdBarraca(int v)       { this.idBarraca = v; }

    public String    getProduto()          { return produto; }
    public void      setProduto(String p)  { this.produto = p; }

    public int       getQuantidade()           { return quantidade; }
    public void      setQuantidade(int q)      { this.quantidade = q; }

    public double    getValorTotal()           { return valorTotal; }
    public void      setValorTotal(double v)   { this.valorTotal = v; }

    public LocalDate getData()             { return data; }
    public void      setData(LocalDate d)  { this.data = d; }
}
