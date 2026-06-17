package com.example.projetinho.dao;

import com.example.projetinho.db.ConexaoDB;
import com.example.projetinho.entidades.Farmacia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FarmaciaDAO {

    // Insere uma nova farmácia e retorna o objeto com o ID gerado pelo banco.
    // Usa RETURN_GENERATED_KEYS para obter o AUTO_INCREMENT corretamente.
    public Farmacia inserir(Farmacia f) {
        String sql = "INSERT INTO farmacia (nome, endereco, estoque) VALUES (?, ?, ?)";
        try (PreparedStatement ps = ConexaoDB.getConexao()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, f.getNome());
            ps.setString(2, f.getEndereco());
            ps.setInt   (3, f.getEstoque());
            ps.executeUpdate();

            ResultSet chaves = ps.getGeneratedKeys();
            if (chaves.next()) f.setId(chaves.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir farmácia: " + e.getMessage(), e);
        }
        return f;
    }

    // Retorna todas as farmácias cadastradas, ordenadas por ID.
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

    // Busca uma farmácia específica pelo ID.
    // Retorna Optional para indicar que pode não encontrar nada.
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

    // Atualiza todos os campos da farmácia no banco usando seu ID como chave.
    // @return true se alguma linha foi alterada
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

    // Remove a farmácia com o ID informado.
    // @return true se alguma linha foi removida
    public boolean deletar(int id) {
        String sql = "DELETE FROM farmacia WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar farmácia: " + e.getMessage(), e);
        }
    }

    // Converte uma linha do ResultSet em um objeto Farmacia.
    // Centraliza o mapeamento para não repetir esse código em cada método.
    private Farmacia mapear(ResultSet rs) throws SQLException {
        return new Farmacia(
                rs.getInt   ("id"),
                rs.getString("nome"),
                rs.getString("endereco"),
                rs.getInt   ("estoque")
        );
    }
}
