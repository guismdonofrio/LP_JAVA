package com.example.projetinho.dao;

import com.example.projetinho.db.ConexaoDB;
import com.example.projetinho.entidades.VendaBarraca;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Responsável pelo registro e consulta das vendas realizadas em barracas de feira.
public class VendaBarracaDAO {

    // Registra uma nova venda e retorna o objeto com ID gerado.
    public VendaBarraca inserir(VendaBarraca v) {
        String sql = "INSERT INTO venda_barraca (id_barraca, produto, quantidade, valor_total, data) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = ConexaoDB.getConexao()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt   (1, v.getIdBarraca());
            ps.setString(2, v.getProduto());
            ps.setInt   (3, v.getQuantidade());
            ps.setDouble(4, v.getValorTotal());
            ps.setDate  (5, Date.valueOf(v.getData()));
            ps.executeUpdate();

            ResultSet chaves = ps.getGeneratedKeys();
            if (chaves.next()) v.setId(chaves.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao registrar venda: " + e.getMessage(), e);
        }
        return v;
    }

    // Lista todas as vendas de uma barraca, da mais recente para a mais antiga.
    public List<VendaBarraca> listarPorBarraca(int idBarraca) {
        List<VendaBarraca> lista = new ArrayList<>();
        String sql = "SELECT id, id_barraca, produto, quantidade, valor_total, data "
                   + "FROM venda_barraca WHERE id_barraca = ? ORDER BY data DESC, id DESC";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, idBarraca);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar vendas: " + e.getMessage(), e);
        }
        return lista;
    }

    // Lista todas as vendas de todas as barracas.
    public List<VendaBarraca> listarTodas() {
        List<VendaBarraca> lista = new ArrayList<>();
        String sql = "SELECT id, id_barraca, produto, quantidade, valor_total, data "
                   + "FROM venda_barraca ORDER BY data DESC, id DESC";
        try (Statement st = ConexaoDB.getConexao().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todas as vendas: " + e.getMessage(), e);
        }
        return lista;
    }

    // Busca uma venda específica pelo ID.
    public Optional<VendaBarraca> buscarPorId(int id) {
        String sql = "SELECT id, id_barraca, produto, quantidade, valor_total, data FROM venda_barraca WHERE id = ?";
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
    public boolean atualizar(VendaBarraca v) {
        String sql = "UPDATE venda_barraca SET produto = ?, quantidade = ?, valor_total = ?, data = ? WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setString(1, v.getProduto());
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
        String sql = "DELETE FROM venda_barraca WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar venda: " + e.getMessage(), e);
        }
    }

    // Soma o valor total de vendas de uma barraca em um período.
    public double somarVendasPorBarraca(int idBarraca, LocalDate inicio, LocalDate fim) {
        String sql = "SELECT COALESCE(SUM(valor_total), 0) AS total FROM venda_barraca "
                   + "WHERE id_barraca = ? AND data BETWEEN ? AND ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt (1, idBarraca);
            ps.setDate(2, Date.valueOf(inicio));
            ps.setDate(3, Date.valueOf(fim));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble("total");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao somar vendas: " + e.getMessage(), e);
        }
        return 0.0;
    }

    private VendaBarraca mapear(ResultSet rs) throws SQLException {
        return new VendaBarraca(
                rs.getInt   ("id"),
                rs.getInt   ("id_barraca"),
                rs.getString("produto"),
                rs.getInt   ("quantidade"),
                rs.getDouble("valor_total"),
                rs.getDate  ("data").toLocalDate()
        );
    }
}
