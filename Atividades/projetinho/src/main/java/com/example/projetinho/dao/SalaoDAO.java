package com.example.projetinho.dao;

import com.example.projetinho.db.ConexaoDB;
import com.example.projetinho.entidades.Salao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SalaoDAO {

    private int proximoId() {
        String sql = """
            SELECT MIN(t.id + 1) AS proximo
            FROM (SELECT 0 AS id UNION SELECT id FROM salao) t
            WHERE t.id + 1 NOT IN (SELECT id FROM salao)
            """;
        try (Statement st = ConexaoDB.getConexao().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt("proximo");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao calcular próximo ID: " + e.getMessage(), e);
        }
        return 1;
    }

    public Salao inserir(Salao s) {
        int id = proximoId();
        String sql = "INSERT INTO salao (id, nome, funcionarios, servico) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt   (1, id);
            ps.setString(2, s.getNome());
            ps.setInt   (3, s.getFuncionarios());
            ps.setString(4, s.getServico());
            ps.executeUpdate();
            s.setId(id);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir salão: " + e.getMessage(), e);
        }
        return s;
    }

    public List<Salao> listarTodos() {
        List<Salao> lista = new ArrayList<>();
        String sql = "SELECT id, nome, funcionarios, servico FROM salao ORDER BY id";
        try (Statement st = ConexaoDB.getConexao().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar salões: " + e.getMessage(), e);
        }
        return lista;
    }

    public Optional<Salao> buscarPorId(int id) {
        String sql = "SELECT id, nome, funcionarios, servico FROM salao WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar salão: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    public boolean atualizar(Salao s) {
        String sql = "UPDATE salao SET nome = ?, funcionarios = ?, servico = ? WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setString(1, s.getNome());
            ps.setInt   (2, s.getFuncionarios());
            ps.setString(3, s.getServico());
            ps.setInt   (4, s.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar salão: " + e.getMessage(), e);
        }
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM salao WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar salão: " + e.getMessage(), e);
        }
    }

    private Salao mapear(ResultSet rs) throws SQLException {
        return new Salao(rs.getInt("id"), rs.getString("nome"),
                rs.getInt("funcionarios"), rs.getString("servico"));
    }
}
