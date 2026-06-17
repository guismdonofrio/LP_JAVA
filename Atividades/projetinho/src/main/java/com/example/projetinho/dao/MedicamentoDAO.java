package com.example.projetinho.dao;

import com.example.projetinho.db.ConexaoDB;
import com.example.projetinho.entidades.Medicamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Responsável por todas as operações de banco de dados relacionadas a Medicamento.
public class MedicamentoDAO {

    // Cadastra um novo medicamento no estoque de uma farmácia.
    public Medicamento inserir(Medicamento m) {
        String sql = "INSERT INTO medicamento (id_farmacia, nome, fabricante, dosagem, estoque, preco) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = ConexaoDB.getConexao()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt   (1, m.getIdFarmacia());
            ps.setString(2, m.getNome());
            ps.setString(3, m.getFabricante());
            ps.setString(4, m.getDosagem());
            ps.setInt   (5, m.getEstoque());
            ps.setDouble(6, m.getPreco());
            ps.executeUpdate();

            ResultSet chaves = ps.getGeneratedKeys();
            if (chaves.next()) m.setId(chaves.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir medicamento: " + e.getMessage(), e);
        }
        return m;
    }

    // Lista todos os medicamentos de uma farmácia específica.
    public List<Medicamento> listarPorFarmacia(int idFarmacia) {
        List<Medicamento> lista = new ArrayList<>();
        String sql = "SELECT id, id_farmacia, nome, fabricante, dosagem, estoque, preco "
                   + "FROM medicamento WHERE id_farmacia = ? ORDER BY nome";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, idFarmacia);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar medicamentos: " + e.getMessage(), e);
        }
        return lista;
    }

    // Lista todos os medicamentos de todas as farmácias.
    public List<Medicamento> listarTodos() {
        List<Medicamento> lista = new ArrayList<>();
        String sql = "SELECT id, id_farmacia, nome, fabricante, dosagem, estoque, preco "
                   + "FROM medicamento ORDER BY id_farmacia, nome";
        try (Statement st = ConexaoDB.getConexao().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todos os medicamentos: " + e.getMessage(), e);
        }
        return lista;
    }

    // Busca um medicamento pelo ID.
    public Optional<Medicamento> buscarPorId(int id) {
        String sql = "SELECT id, id_farmacia, nome, fabricante, dosagem, estoque, preco FROM medicamento WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar medicamento: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    // Busca medicamentos pelo nome (busca parcial, útil para auto-complete).
    public List<Medicamento> buscarPorNome(String nome) {
        List<Medicamento> lista = new ArrayList<>();
        String sql = "SELECT id, id_farmacia, nome, fabricante, dosagem, estoque, preco "
                   + "FROM medicamento WHERE nome LIKE ? ORDER BY nome";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setString(1, "%" + nome + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar medicamento por nome: " + e.getMessage(), e);
        }
        return lista;
    }

    // Atualiza todos os campos do medicamento.
    public boolean atualizar(Medicamento m) {
        String sql = "UPDATE medicamento SET nome = ?, fabricante = ?, dosagem = ?, estoque = ?, preco = ? WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setString(1, m.getNome());
            ps.setString(2, m.getFabricante());
            ps.setString(3, m.getDosagem());
            ps.setInt   (4, m.getEstoque());
            ps.setDouble(5, m.getPreco());
            ps.setInt   (6, m.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar medicamento: " + e.getMessage(), e);
        }
    }

    // Remove um medicamento pelo ID.
    public boolean deletar(int id) {
        String sql = "DELETE FROM medicamento WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar medicamento: " + e.getMessage(), e);
        }
    }

    private Medicamento mapear(ResultSet rs) throws SQLException {
        return new Medicamento(
                rs.getInt   ("id"),
                rs.getInt   ("id_farmacia"),
                rs.getString("nome"),
                rs.getString("fabricante"),
                rs.getString("dosagem"),
                rs.getInt   ("estoque"),
                rs.getDouble("preco")
        );
    }
}
