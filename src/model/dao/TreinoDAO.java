package model.dao;

import model.entities.Treino;
import model.entities.Usuario;
import model.exceptions.DatabaseException;
import util.ConexaoBD;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.entities.TreinoExercicio;

public class TreinoDAO implements BaseDAO<Treino> {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private TreinoExercicioDAO treinoExercicioDAO = new TreinoExercicioDAO();

    @Override
    public void insert(Treino treino) throws DatabaseException {
        String sql = "INSERT INTO Treinos (usuario_id, nome, descricao) VALUES (?, ?, ?)";
        
        try (Connection conn = ConexaoBD.criarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setLong(1, treino.getUsuario().getId());
            stmt.setString(2, treino.getNome());
            stmt.setString(3, treino.getDescricao());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DatabaseException("Falha ao criar treino, nenhuma linha afetada.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    treino.setId(generatedKeys.getLong(1));
                    
                    // Insere os exercícios do treino
                    for (TreinoExercicio te : treino.getExercicios()) {
                        te.setTreino(treino);
                        treinoExercicioDAO.insert(te);
                    }
                } else {
                    throw new DatabaseException("Falha ao criar treino, nenhum ID obtido.");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao inserir treino: " + e.getMessage());
        }
    }

    @Override
    public void update(Treino treino) throws DatabaseException {
        String sql = "UPDATE Treinos SET usuario_id = ?, nome = ?, descricao = ? WHERE treino_id = ?";
        
        try (Connection conn = ConexaoBD.criarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, treino.getUsuario().getId());
            stmt.setString(2, treino.getNome());
            stmt.setString(3, treino.getDescricao());
            stmt.setLong(4, treino.getId());
            
            stmt.executeUpdate();
            
            // Atualiza os exercícios do treino
            treinoExercicioDAO.deleteByTreinoId(treino.getId());
            for (TreinoExercicio te : treino.getExercicios()) {
                te.setTreino(treino);
                treinoExercicioDAO.insert(te);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao atualizar treino: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) throws DatabaseException {
        // Primeiro deleta os exercícios do treino
        treinoExercicioDAO.deleteByTreinoId(id);
        
        // Depois deleta o treino
        String sql = "DELETE FROM Treinos WHERE treino_id = ?";
        
        try (Connection conn = ConexaoBD.criarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao deletar treino: " + e.getMessage());
        }
    }

    @Override
    public Treino findById(Long id) throws DatabaseException {
        String sql = "SELECT * FROM Treinos WHERE treino_id = ?";
        Treino treino = null;
        
        try (Connection conn = ConexaoBD.criarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Usuario usuario = usuarioDAO.findById(rs.getLong("usuario_id"));
                
                treino = new Treino(
                    usuario,
                    rs.getString("nome"),
                    rs.getString("descricao")
                );
                treino.setId(rs.getLong("treino_id"));
                treino.setDataCriacao(rs.getTimestamp("data_criacao").toLocalDateTime());
                
                // Carrega os exercícios do treino
                treino.getExercicios().addAll(treinoExercicioDAO.findByTreinoId(treino.getId()));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar treino por ID: " + e.getMessage());
        }
        
        return treino;
    }

    @Override
    public List<Treino> findAll() throws DatabaseException {
        String sql = "SELECT * FROM Treinos";
        List<Treino> treinos = new ArrayList<>();
        
        try (Connection conn = ConexaoBD.criarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Usuario usuario = usuarioDAO.findById(rs.getLong("usuario_id"));
                
                Treino treino = new Treino(
                    usuario,
                    rs.getString("nome"),
                    rs.getString("descricao")
                );
                treino.setId(rs.getLong("treino_id"));
                treino.setDataCriacao(rs.getTimestamp("data_criacao").toLocalDateTime());
                
                // Carrega os exercícios do treino
                treino.getExercicios().addAll(treinoExercicioDAO.findByTreinoId(treino.getId()));
                
                treinos.add(treino);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar todos os treinos: " + e.getMessage());
        }
        
        return treinos;
    }

    public List<Treino> findByUsuarioId(Long usuarioId) throws DatabaseException {
        String sql = "SELECT * FROM Treinos WHERE usuario_id = ?";
        List<Treino> treinos = new ArrayList<>();
        
        try (Connection conn = ConexaoBD.criarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Usuario usuario = usuarioDAO.findById(usuarioId);
                
                Treino treino = new Treino(
                    usuario,
                    rs.getString("nome"),
                    rs.getString("descricao")
                );
                treino.setId(rs.getLong("treino_id"));
                treino.setDataCriacao(rs.getTimestamp("data_criacao").toLocalDateTime());
                
                // Carrega os exercícios do treino
                treino.getExercicios().addAll(treinoExercicioDAO.findByTreinoId(treino.getId()));
                
                treinos.add(treino);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar treinos por usuário: " + e.getMessage());
        }
        
        return treinos;
    }
}