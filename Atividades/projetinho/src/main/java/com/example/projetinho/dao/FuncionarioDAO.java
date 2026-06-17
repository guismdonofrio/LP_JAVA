package com.example.projetinho.dao;

import com.example.projetinho.db.ConexaoDB;
import com.example.projetinho.entidades.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Responsável por todas as operações de banco de dados relacionadas a Funcionario de salão.
public class FuncionarioDAO {

    // Cadastra um novo funcionário vinculado a um salão.
    public Funcionario inserir(Funcionario f) {
        String sql = "INSERT INTO funcionario (id_salao, nome, cargo, salario, telefone) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = ConexaoDB.getConexao()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt   (1, f.getIdSalao());
            ps.setString(2, f.getNome());
            ps.setString(3, f.getCargo());
            ps.setDouble(4, f.getSalario());
            ps.setString(5, f.getTelefone());
            ps.executeUpdate();

            ResultSet chaves = ps.getGeneratedKeys();
            if (chaves.next()) f.setId(chaves.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir funcionário: " + e.getMessage(), e);
        }
        return f;
    }

    // Lista todos os funcionários de um salão específico.
    public List<Funcionario> listarPorSalao(int idSalao) {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT id, id_salao, nome, cargo, salario, telefone FROM funcionario WHERE id_salao = ? ORDER BY nome";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, idSalao);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar funcionários: " + e.getMessage(), e);
        }
        return lista;
    }

    // Lista todos os funcionários de todos os salões.
    public List<Funcionario> listarTodos() {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT id, id_salao, nome, cargo, salario, telefone FROM funcionario ORDER BY id_salao, nome";
        try (Statement st = ConexaoDB.getConexao().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todos os funcionários: " + e.getMessage(), e);
        }
        return lista;
    }

    // Busca um funcionário pelo ID.
    public Optional<Funcionario> buscarPorId(int id) {
        String sql = "SELECT id, id_salao, nome, cargo, salario, telefone FROM funcionario WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionário: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    // Atualiza nome, cargo, salário e telefone do funcionário.
    public boolean atualizar(Funcionario f) {
        String sql = "UPDATE funcionario SET nome = ?, cargo = ?, salario = ?, telefone = ? WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setString(1, f.getNome());
            ps.setString(2, f.getCargo());
            ps.setDouble(3, f.getSalario());
            ps.setString(4, f.getTelefone());
            ps.setInt   (5, f.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar funcionário: " + e.getMessage(), e);
        }
    }

    // Remove um funcionário pelo ID.
    public boolean deletar(int id) {
        String sql = "DELETE FROM funcionario WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar funcionário: " + e.getMessage(), e);
        }
    }

    // Conta quantos funcionários existem em um salão (útil para atualizar o campo funcionarios na tabela salao).
    public int contarPorSalao(int idSalao) {
        String sql = "SELECT COUNT(*) FROM funcionario WHERE id_salao = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, idSalao);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar funcionários: " + e.getMessage(), e);
        }
        return 0;
    }

    private Funcionario mapear(ResultSet rs) throws SQLException {
        return new Funcionario(
                rs.getInt   ("id"),
                rs.getInt   ("id_salao"),
                rs.getString("nome"),
                rs.getString("cargo"),
                rs.getDouble("salario"),
                rs.getString("telefone")
        );
    }
}
