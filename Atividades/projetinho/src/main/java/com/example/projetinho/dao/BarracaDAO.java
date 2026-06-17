package com.example.projetinho.dao;

import com.example.projetinho.db.ConexaoDB;
import com.example.projetinho.entidades.Barraca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Usar PreparedStatement (em vez de Statement simples) previne
// SQL Injection: os parâmetros são tratados como dados, não como parte do comando SQL.

public class BarracaDAO {

    // Insere uma nova barraca no banco e devolve o objeto com o ID gerado.
    // RETURN_GENERATED_KEYS diz ao JDBC para retornar o id criado automaticamente pelo AUTO_INCREMENT do MySQL.
    public Barraca inserir(Barraca b) {
        String sql = "INSERT INTO barraca (nome, produto, estoque) VALUES (?, ?, ?)";
        try (PreparedStatement ps = ConexaoDB.getConexao()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, b.getNome());
            ps.setString(2, b.getProduto());
            ps.setInt   (3, b.getEstoque());
            ps.executeUpdate();

            // Recupera o id gerado pelo banco e atribui ao objeto
            ResultSet chaves = ps.getGeneratedKeys();
            if (chaves.next()) {
                b.setId(chaves.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir barraca: " + e.getMessage(), e);
        }
        return b;
    }

    // Retorna todas as barracas cadastradas, ordenadas por ID.
    // Usado para popular a TableView ao iniciar e após cada operação.

    public List<Barraca> listarTodas() {
        List<Barraca> lista = new ArrayList<>();
        String sql = "SELECT id, nome, produto, estoque FROM barraca ORDER BY id";
        try (Statement st = ConexaoDB.getConexao().createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            // Percorre cada linha do resultado e transforma em objeto Barraca
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar barracas: " + e.getMessage(), e);
        }
        return lista;
    }

    // Busca uma barraca específica pelo ID.
    // Retorna Optional para indicar que pode não encontrar nada.
    public Optional<Barraca> buscarPorId(int id) {
        String sql = "SELECT id, nome, produto, estoque FROM barraca WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar barraca: " + e.getMessage(), e);
        }
        return Optional.empty(); // não encontrou
    }

    // Atualiza todos os campos da barraca no banco usando seu ID como chave.
    // @return true se alguma linha foi alterada
    public boolean atualizar(Barraca b) {
        String sql = "UPDATE barraca SET nome = ?, produto = ?, estoque = ? WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setString(1, b.getNome());
            ps.setString(2, b.getProduto());
            ps.setInt   (3, b.getEstoque());
            ps.setInt   (4, b.getId()); // cláusula WHERE: só altera a barraca com esse id
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar barraca: " + e.getMessage(), e);
        }
    }

    //Remove a barraca com o ID informado.
    // @return true se alguma linha foi removida
    public boolean deletar(int id) {
        String sql = "DELETE FROM barraca WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar barraca: " + e.getMessage(), e);
        }
    }

    // Converte uma linha do ResultSet em um objeto Barraca.
    //Centraliza o mapeamento para não repetir esse código em cada método.
    private Barraca mapear(ResultSet rs) throws SQLException {
        return new Barraca(
                rs.getInt   ("id"),
                rs.getString("nome"),
                rs.getString("produto"),
                rs.getInt   ("estoque")
        );
    }
}