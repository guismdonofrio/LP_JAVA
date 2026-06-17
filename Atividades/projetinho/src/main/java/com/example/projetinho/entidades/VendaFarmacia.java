package com.example.projetinho.entidades;

import java.time.LocalDate;

// Registra a dispensação (venda) de um medicamento em uma farmácia.
// Serve como histórico de movimentação de estoque e receita.
public class VendaFarmacia {

    private int        id;
    private int        idFarmacia;
    private String     medicamento;
    private int        quantidade;
    private double     valorTotal;
    private LocalDate  data;

    public VendaFarmacia(int idFarmacia, String medicamento, int quantidade, double valorTotal, LocalDate data) {
        this(0, idFarmacia, medicamento, quantidade, valorTotal, data);
    }

    public VendaFarmacia(int id, int idFarmacia, String medicamento, int quantidade,
                         double valorTotal, LocalDate data) {
        this.id          = id;
        this.idFarmacia  = idFarmacia;
        this.medicamento = medicamento;
        this.quantidade  = quantidade;
        this.valorTotal  = valorTotal;
        this.data        = data;
    }

    public String resumo() {
        return "[#" + id + "] Farmácia #" + idFarmacia + " | " + medicamento
                + " x" + quantidade + " | R$ " + String.format("%.2f", valorTotal)
                + " | " + data;
    }

    public int       getId()                   { return id; }
    public void      setId(int id)             { this.id = id; }

    public int       getIdFarmacia()               { return idFarmacia; }
    public void      setIdFarmacia(int v)          { this.idFarmacia = v; }

    public String    getMedicamento()          { return medicamento; }
    public void      setMedicamento(String m)  { this.medicamento = m; }

    public int       getQuantidade()               { return quantidade; }
    public void      setQuantidade(int q)          { this.quantidade = q; }

    public double    getValorTotal()               { return valorTotal; }
    public void      setValorTotal(double v)       { this.valorTotal = v; }

    public LocalDate getData()                 { return data; }
    public void      setData(LocalDate d)      { this.data = d; }
}
