package com.example.projetinho.dao;

import com.example.projetinho.db.ConexaoDB;
import com.example.projetinho.entidades.Farmacia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FarmaciaDAO {

    private int proximoId() {
        String sql = """
            SELECT MIN(t.id + 1) AS proximo
            FROM (SELECT 0 AS id UNION SELECT id FROM farmacia) t
            WHERE t.id + 1 NOT IN (SELECT id FROM farmacia)
            """;
        try (Statement st = ConexaoDB.getConexao().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt("proximo");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao calcular próximo ID: " + e.getMessage(), e);
        }
        return 1;
    }

    public Farmacia inserir(Farmacia f) {
        int id = proximoId();
        String sql = "INSERT INTO farmacia (id, nome, endereco, estoque) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt   (1, id);
            ps.setString(2, f.getNome());
            ps.setString(3, f.getEndereco());
            ps.setInt   (4, f.getEstoque());
            ps.executeUpdate();
            f.setId(id);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir farmácia: " + e.getMessage(), e);
        }
        return f;
    }

    public List<Farmacia> listarTodas() {
        List<Farmacia> lista = new ArrayList<>();
        String sql = "SELECT id, nome, endereco, estoque FROM farmacia ORDER BY id";
        try (Statement st = ConexaoDB.getConexao().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar farmácias: " + e.getMessage(), e);
        }
        return lista;
    }

    public Optional<Farmacia> buscarPorId(int id) {
        String sql = "SELECT id, nome, endereco, estoque FROM farmacia WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar farmácia: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    public boolean atualizar(Farmacia f) {
        String sql = "UPDATE farmacia SET nome = ?, endereco = ?, estoque = ? WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setString(1, f.getNome());
            ps.setString(2, f.getEndereco());
            ps.setInt   (3, f.getEstoque());
            ps.setInt   (4, f.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar farmácia: " + e.getMessage(), e);
        }
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM farmacia WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar farmácia: " + e.getMessage(), e);
        }
    }

    private Farmacia mapear(ResultSet rs) throws SQLException {
        return new Farmacia(rs.getInt("id"), rs.getString("nome"),
                rs.getString("endereco"), rs.getInt("estoque"));
    }
}
