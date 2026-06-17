package com.example.projetinho.dao;

import com.example.projetinho.db.ConexaoDB;
import com.example.projetinho.entidades.Agendamento;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Responsável por todas as operações de banco de dados relacionadas a Agendamento de salão.
public class AgendamentoDAO {

    // Registra um novo agendamento.
    public Agendamento inserir(Agendamento a) {
        String sql = "INSERT INTO agendamento (id_salao, nome_cliente, servico, data, horario, status) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = ConexaoDB.getConexao()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt   (1, a.getIdSalao());
            ps.setString(2, a.getNomeCliente());
            ps.setString(3, a.getServico());
            ps.setDate  (4, Date.valueOf(a.getData()));
            ps.setString(5, a.getHorario());
            ps.setString(6, a.getStatus());
            ps.executeUpdate();

            ResultSet chaves = ps.getGeneratedKeys();
            if (chaves.next()) a.setId(chaves.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir agendamento: " + e.getMessage(), e);
        }
        return a;
    }

    // Lista todos os agendamentos de um salão, ordenados por data e horário.
    public List<Agendamento> listarPorSalao(int idSalao) {
        List<Agendamento> lista = new ArrayList<>();
        String sql = "SELECT id, id_salao, nome_cliente, servico, data, horario, status "
                   + "FROM agendamento WHERE id_salao = ? ORDER BY data, horario";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, idSalao);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar agendamentos: " + e.getMessage(), e);
        }
        return lista;
    }

    // Lista todos os agendamentos de todos os salões.
    public List<Agendamento> listarTodos() {
        List<Agendamento> lista = new ArrayList<>();
        String sql = "SELECT id, id_salao, nome_cliente, servico, data, horario, status "
                   + "FROM agendamento ORDER BY data, horario";
        try (Statement st = ConexaoDB.getConexao().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todos os agendamentos: " + e.getMessage(), e);
        }
        return lista;
    }

    // Busca um agendamento pelo ID.
    public Optional<Agendamento> buscarPorId(int id) {
        String sql = "SELECT id, id_salao, nome_cliente, servico, data, horario, status FROM agendamento WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar agendamento: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    // Lista agendamentos de um salão para uma data específica (agenda do dia).
    public List<Agendamento> listarPorData(int idSalao, LocalDate data) {
        List<Agendamento> lista = new ArrayList<>();
        String sql = "SELECT id, id_salao, nome_cliente, servico, data, horario, status "
                   + "FROM agendamento WHERE id_salao = ? AND data = ? ORDER BY horario";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt (1, idSalao);
            ps.setDate(2, Date.valueOf(data));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar agendamentos por data: " + e.getMessage(), e);
        }
        return lista;
    }

    // Atualiza os dados do agendamento (permite remarcar ou alterar status).
    public boolean atualizar(Agendamento a) {
        String sql = "UPDATE agendamento SET nome_cliente = ?, servico = ?, data = ?, horario = ?, status = ? WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setString(1, a.getNomeCliente());
            ps.setString(2, a.getServico());
            ps.setDate  (3, Date.valueOf(a.getData()));
            ps.setString(4, a.getHorario());
            ps.setString(5, a.getStatus());
            ps.setInt   (6, a.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar agendamento: " + e.getMessage(), e);
        }
    }

    // Remove um agendamento pelo ID.
    public boolean deletar(int id) {
        String sql = "DELETE FROM agendamento WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar agendamento: " + e.getMessage(), e);
        }
    }

    private Agendamento mapear(ResultSet rs) throws SQLException {
        return new Agendamento(
                rs.getInt   ("id"),
                rs.getInt   ("id_salao"),
                rs.getString("nome_cliente"),
                rs.getString("servico"),
                rs.getDate  ("data").toLocalDate(),
                rs.getString("horario"),
                rs.getString("status")
        );
    }
}
