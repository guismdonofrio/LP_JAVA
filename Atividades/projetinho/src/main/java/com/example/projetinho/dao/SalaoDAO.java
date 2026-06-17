package com.example.projetinho.dao;

import com.example.projetinho.db.ConexaoDB;
import com.example.projetinho.entidades.Salao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SalaoDAO {

    // Insere um novo salão e retorna o objeto com o ID gerado pelo banco.
    // Usa RETURN_GENERATED_KEYS para obter o AUTO_INCREMENT corretamente.
    public Salao inserir(Salao s) {
        String sql = "INSERT INTO salao (nome, funcionarios, servico) VALUES (?, ?, ?)";
        try (PreparedStatement ps = ConexaoDB.getConexao()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, s.getNome());
            ps.setInt   (2, s.getFuncionarios());
            ps.setString(3, s.getServico());
            ps.executeUpdate();

            ResultSet chaves = ps.getGeneratedKeys();
            if (chaves.next()) s.setId(chaves.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir salão: " + e.getMessage(), e);
        }
        return s;
    }

    // Retorna todos os salões cadastrados, ordenados por ID.
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

    // Busca um salão específico pelo ID.
    // Retorna Optional para indicar que pode não encontrar nada.
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

    // Atualiza todos os campos do salão no banco usando seu ID como chave.
    // @return true se alguma linha foi alterada
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

    // Remove o salão com o ID informado.
    // @return true se alguma linha foi removida
    public boolean deletar(int id) {
        String sql = "DELETE FROM salao WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar salão: " + e.getMessage(), e);
        }
    }

    // Converte uma linha do ResultSet em um objeto Salao.
    // Centraliza o mapeamento para não repetir esse código em cada método.
    private Salao mapear(ResultSet rs) throws SQLException {
        return new Salao(
                rs.getInt   ("id"),
                rs.getString("nome"),
                rs.getInt   ("funcionarios"),
                rs.getString("servico")
        );
    }
}
