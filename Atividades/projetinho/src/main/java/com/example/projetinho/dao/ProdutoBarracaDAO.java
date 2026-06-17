package com.example.projetinho.dao;

import com.example.projetinho.db.ConexaoDB;
import com.example.projetinho.entidades.ProdutoBarraca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Responsável por todas as operações de banco de dados relacionadas a ProdutoBarraca.
// Um produto pertence a uma Barraca (idBarraca é a chave estrangeira).
public class ProdutoBarracaDAO {

    // Insere um novo produto e retorna o objeto com o ID gerado.
    public ProdutoBarraca inserir(ProdutoBarraca p) {
        String sql = "INSERT INTO produto_barraca (id_barraca, nome, preco, estoque) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = ConexaoDB.getConexao()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt   (1, p.getIdBarraca());
            ps.setString(2, p.getNome());
            ps.setDouble(3, p.getPreco());
            ps.setInt   (4, p.getEstoque());
            ps.executeUpdate();

            ResultSet chaves = ps.getGeneratedKeys();
            if (chaves.next()) p.setId(chaves.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir produto da barraca: " + e.getMessage(), e);
        }
        return p;
    }

    // Lista todos os produtos de uma barraca específica.
    public List<ProdutoBarraca> listarPorBarraca(int idBarraca) {
        List<ProdutoBarraca> lista = new ArrayList<>();
        String sql = "SELECT id, id_barraca, nome, preco, estoque FROM produto_barraca WHERE id_barraca = ? ORDER BY nome";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, idBarraca);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos da barraca: " + e.getMessage(), e);
        }
        return lista;
    }

    // Lista todos os produtos de todas as barracas.
    public List<ProdutoBarraca> listarTodos() {
        List<ProdutoBarraca> lista = new ArrayList<>();
        String sql = "SELECT id, id_barraca, nome, preco, estoque FROM produto_barraca ORDER BY id_barraca, nome";
        try (Statement st = ConexaoDB.getConexao().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos: " + e.getMessage(), e);
        }
        return lista;
    }

    // Busca um produto pelo ID.
    public Optional<ProdutoBarraca> buscarPorId(int id) {
        String sql = "SELECT id, id_barraca, nome, preco, estoque FROM produto_barraca WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    // Atualiza nome, preço e estoque do produto.
    public boolean atualizar(ProdutoBarraca p) {
        String sql = "UPDATE produto_barraca SET nome = ?, preco = ?, estoque = ? WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.setDouble(2, p.getPreco());
            ps.setInt   (3, p.getEstoque());
            ps.setInt   (4, p.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar produto: " + e.getMessage(), e);
        }
    }

    // Remove o produto pelo ID.
    public boolean deletar(int id) {
        String sql = "DELETE FROM produto_barraca WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar produto: " + e.getMessage(), e);
        }
    }

    private ProdutoBarraca mapear(ResultSet rs) throws SQLException {
        return new ProdutoBarraca(
                rs.getInt   ("id"),
                rs.getInt   ("id_barraca"),
                rs.getString("nome"),
                rs.getDouble("preco"),
                rs.getInt   ("estoque")
        );
    }
}
