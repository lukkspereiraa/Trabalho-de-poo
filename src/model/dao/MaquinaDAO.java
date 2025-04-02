package model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.entities.Maquina;
import model.exceptions.DatabaseException;
import util.ConexaoBD;

public class MaquinaDAO implements BaseDAO<Maquina> {

    @Override
    public void insert(Maquina maquina) throws DatabaseException {
        String sql = "INSERT INTO Maquinas (nome, descricao, fabricante) VALUES (?, ?, ?)";
        
        try (Connection conn = ConexaoBD.criarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, maquina.getNome());
            stmt.setString(2, maquina.getDescricao());
            stmt.setString(3, maquina.getFabricante());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DatabaseException("Falha ao criar máquina, nenhuma linha afetada.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    maquina.setId(generatedKeys.getLong(1));
                } else {
                    throw new DatabaseException("Falha ao criar máquina, nenhum ID obtido.");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao inserir máquina: " + e.getMessage());
        }
    }

    @Override
    public void update(Maquina maquina) throws DatabaseException {
        String sql = "UPDATE Maquinas SET nome = ?, descricao = ?, fabricante = ? WHERE maquina_id = ?";
        
        try (Connection conn = ConexaoBD.criarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, maquina.getNome());
            stmt.setString(2, maquina.getDescricao());
            stmt.setString(3, maquina.getFabricante());
            stmt.setLong(4, maquina.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao atualizar máquina: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) throws DatabaseException {
        String sql = "DELETE FROM Maquinas WHERE maquina_id = ?";
        
        try (Connection conn = ConexaoBD.criarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao deletar máquina: " + e.getMessage());
        }
    }

    @Override
    public Maquina findById(Long id) throws DatabaseException {
        String sql = "SELECT * FROM Maquinas WHERE maquina_id = ?";
        Maquina maquina = null;
        
        try (Connection conn = ConexaoBD.criarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                maquina = new Maquina(
                    rs.getString("nome"),
                    rs.getString("descricao"),
                    rs.getString("fabricante")
                );
                maquina.setId(rs.getLong("maquina_id"));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar máquina por ID: " + e.getMessage());
        }
        
        return maquina;
    }

    @Override
    public List<Maquina> findAll() throws DatabaseException {
        String sql = "SELECT * FROM Maquinas";
        List<Maquina> maquinas = new ArrayList<>();
        
        try (Connection conn = ConexaoBD.criarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Maquina maquina = new Maquina(
                    rs.getString("nome"),
                    rs.getString("descricao"),
                    rs.getString("fabricante")
                );
                maquina.setId(rs.getLong("maquina_id"));
                maquinas.add(maquina);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar todas as máquinas: " + e.getMessage());
        }
        
        return maquinas;
    }
}