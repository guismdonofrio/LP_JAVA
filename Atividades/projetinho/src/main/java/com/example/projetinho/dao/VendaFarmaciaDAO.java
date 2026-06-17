package com.example.projetinho.dao;

import com.example.projetinho.db.ConexaoDB;
import com.example.projetinho.entidades.VendaFarmacia;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Responsável pelo registro e consulta das dispensações (vendas) de medicamentos.
public class VendaFarmaciaDAO {

    // Registra uma nova dispensação de medicamento.
    public VendaFarmacia inserir(VendaFarmacia v) {
        String sql = "INSERT INTO venda_farmacia (id_farmacia, medicamento, quantidade, valor_total, data) "
                   + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = ConexaoDB.getConexao()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt   (1, v.getIdFarmacia());
            ps.setString(2, v.getMedicamento());
            ps.setInt   (3, v.getQuantidade());
            ps.setDouble(4, v.getValorTotal());
            ps.setDate  (5, Date.valueOf(v.getData()));
            ps.executeUpdate();

            ResultSet chaves = ps.getGeneratedKeys();
            if (chaves.next()) v.setId(chaves.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao registrar venda de farmácia: " + e.getMessage(), e);
        }
        return v;
    }

    // Lista todas as vendas de uma farmácia específica.
    public List<VendaFarmacia> listarPorFarmacia(int idFarmacia) {
        List<VendaFarmacia> lista = new ArrayList<>();
        String sql = "SELECT id, id_farmacia, medicamento, quantidade, valor_total, data "
                   + "FROM venda_farmacia WHERE id_farmacia = ? ORDER BY data DESC, id DESC";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, idFarmacia);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar vendas da farmácia: " + e.getMessage(), e);
        }
        return lista;
    }

    // Lista todas as vendas de todas as farmácias.
    public List<VendaFarmacia> listarTodas() {
        List<VendaFarmacia> lista = new ArrayList<>();
        String sql = "SELECT id, id_farmacia, medicamento, quantidade, valor_total, data "
                   + "FROM venda_farmacia ORDER BY data DESC, id DESC";
        try (Statement st = ConexaoDB.getConexao().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todas as vendas: " + e.getMessage(), e);
        }
        return lista;
    }

    // Busca uma venda específica pelo ID.
    public Optional<VendaFarmacia> buscarPorId(int id) {
        String sql = "SELECT id, id_farmacia, medicamento, quantidade, valor_total, data FROM venda_farmacia WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar venda: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    // Atualiza os dados de uma venda (correção de lançamento).
    public boolean atualizar(VendaFarmacia v) {
        String sql = "UPDATE venda_farmacia SET medicamento = ?, quantidade = ?, valor_total = ?, data = ? WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setString(1, v.getMedicamento());
            ps.setInt   (2, v.getQuantidade());
            ps.setDouble(3, v.getValorTotal());
            ps.setDate  (4, Date.valueOf(v.getData()));
            ps.setInt   (5, v.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar venda: " + e.getMessage(), e);
        }
    }

    // Remove um registro de venda pelo ID.
    public boolean deletar(int id) {
        String sql = "DELETE FROM venda_farmacia WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar venda: " + e.getMessage(), e);
        }
    }

    // Soma o valor total de vendas de uma farmácia em um período.
    public double somarVendasPorFarmacia(int idFarmacia, LocalDate inicio, LocalDate fim) {
        String sql = "SELECT COALESCE(SUM(valor_total), 0) AS total FROM venda_farmacia "
                   + "WHERE id_farmacia = ? AND data BETWEEN ? AND ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt (1, idFarmacia);
            ps.setDate(2, Date.valueOf(inicio));
            ps.setDate(3, Date.valueOf(fim));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble("total");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao somar vendas da farmácia: " + e.getMessage(), e);
        }
        return 0.0;
    }

    private VendaFarmacia mapear(ResultSet rs) throws SQLException {
        return new VendaFarmacia(
                rs.getInt   ("id"),
                rs.getInt   ("id_farmacia"),
                rs.getString("medicamento"),
                rs.getInt   ("quantidade"),
                rs.getDouble("valor_total"),
                rs.getDate  ("data").toLocalDate()
        );
    }
}
