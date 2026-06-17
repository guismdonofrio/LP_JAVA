package com.example.projetinho.entidades;

import java.time.LocalDate;

// agendamento de cliente em um salão de beleza.
// Liga o cliente a um serviço e uma data, opcionalmente a um funcionário.
public class Agendamento {

    private int       id;
    private int       idSalao;
    private String    nomeCliente;
    private String    servico;
    private LocalDate data;
    private String    horario;
    private String    status;

    public Agendamento(int idSalao, String nomeCliente, String servico,
                       LocalDate data, String horario, String status) {
        this(0, idSalao, nomeCliente, servico, data, horario, status);
    }

    public Agendamento(int id, int idSalao, String nomeCliente, String servico,
                       LocalDate data, String horario, String status) {
        this.id          = id;
        this.idSalao     = idSalao;
        this.nomeCliente = nomeCliente;
        this.servico     = servico;
        this.data        = data;
        this.horario     = horario;
        this.status      = status;
    }

    // Conclui o agendamento se estiver no status correto.
    public String concluir() {
        if ("Agendado".equals(status)) {
            status = "Concluído";
            return "Agendamento concluído com sucesso.";
        }
        return "Não é possível concluir: status atual é '" + status + "'.";
    }

    // Cancela o agendamento se ainda estiver agendado.
    public String cancelar() {
        if ("Agendado".equals(status)) {
            status = "Cancelado";
            return "Agendamento cancelado.";
        }
        return "Não é possível cancelar: status atual é '" + status + "'.";
    }

    public String resumo() {
        return "[#" + id + "] " + nomeCliente + " | " + servico
                + " | " + data + " " + horario + " | " + status;
    }

    public int       getId()                   { return id; }
    public void      setId(int id)             { this.id = id; }

    public int       getIdSalao()              { return idSalao; }
    public void      setIdSalao(int v)         { this.idSalao = v; }

    public String    getNomeCliente()              { return nomeCliente; }
    public void      setNomeCliente(String n)      { this.nomeCliente = n; }

    public String    getServico()              { return servico; }
    public void      setServico(String s)      { this.servico = s; }

    public LocalDate getData()                 { return data; }
    public void      setData(LocalDate d)      { this.data = d; }

    public String    getHorario()              { return horario; }
    public void      setHorario(String h)      { this.horario = h; }

    public String    getStatus()               { return status; }
    public void      setStatus(String s)       { this.status = s; }
}
