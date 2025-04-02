package model.dao;

import model.entities.Exercicio;
import model.entities.Treino;
import model.entities.TreinoExercicio;
import model.exceptions.DatabaseException;
import util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TreinoExercicioDAO implements BaseDAO<TreinoExercicio> {

    private ExercicioDAO exercicioDAO = new ExercicioDAO();

    @Override
    public void insert(TreinoExercicio treinoExercicio) throws DatabaseException {
        String sql = "INSERT INTO Treino_Exercicios (treino_id, exercicio_id, series, repeticoes, carga, dia_semana) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexaoBD.criarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setLong(1, treinoExercicio.getTreino().getId());
            stmt.setLong(2, treinoExercicio.getExercicio().getId());
            stmt.setInt(3, treinoExercicio.getSeries());
            stmt.setInt(4, treinoExercicio.getRepeticoes());
            stmt.setObject(5, treinoExercicio.getCarga());
            stmt.setString(6, treinoExercicio.getDiaSemana());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DatabaseException("Falha ao criar relação treino-exercício, nenhuma linha afetada.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    treinoExercicio.setId(generatedKeys.getLong(1));
                } else {
                    throw new DatabaseException("Falha ao criar relação treino-exercício, nenhum ID obtido.");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao inserir relação treino-exercício: " + e.getMessage());
        }
    }

    @Override
    public void update(TreinoExercicio treinoExercicio) throws DatabaseException {
        String sql = "UPDATE Treino_Exercicios SET treino_id = ?, exercicio_id = ?, series = ?, repeticoes = ?, carga = ?, dia_semana = ? WHERE treino_exercicio_id = ?";
        
        try (Connection conn = ConexaoBD.criarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, treinoExercicio.getTreino().getId());
            stmt.setLong(2, treinoExercicio.getExercicio().getId());
            stmt.setInt(3, treinoExercicio.getSeries());
            stmt.setInt(4, treinoExercicio.getRepeticoes());
            stmt.setObject(5, treinoExercicio.getCarga());
            stmt.setString(6, treinoExercicio.getDiaSemana());
            stmt.setLong(7, treinoExercicio.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao atualizar relação treino-exercício: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) throws DatabaseException {
        String sql = "DELETE FROM Treino_Exercicios WHERE treino_exercicio_id = ?";
        
        try (Connection conn = ConexaoBD.criarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao deletar relação treino-exercício: " + e.getMessage());
        }
    }

    public void deleteByTreinoId(Long treinoId) throws DatabaseException {
        String sql = "DELETE FROM Treino_Exercicios WHERE treino_id = ?";
        
        try (Connection conn = ConexaoBD.criarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, treinoId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao deletar relações treino-exercício por treino: " + e.getMessage());
        }
    }

    @Override
    public TreinoExercicio findById(Long id) throws DatabaseException {
        String sql = "SELECT * FROM Treino_Exercicios WHERE treino_exercicio_id = ?";
        TreinoExercicio treinoExercicio = null;
        
        try (Connection conn = ConexaoBD.criarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Treino treino = new TreinoDAO().findById(rs.getLong("treino_id"));
                Exercicio exercicio = exercicioDAO.findById(rs.getLong("exercicio_id"));
                
                treinoExercicio = new TreinoExercicio(
                    treino,
                    exercicio,
                    rs.getInt("series"),
                    rs.getInt("repeticoes"),
                    rs.getObject("carga", Double.class),
                    rs.getString("dia_semana")
                );
                treinoExercicio.setId(rs.getLong("treino_exercicio_id"));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar relação treino-exercício por ID: " + e.getMessage());
        }
        
        return treinoExercicio;
    }

    @Override
    public List<TreinoExercicio> findAll() throws DatabaseException {
        String sql = "SELECT * FROM Treino_Exercicios";
        List<TreinoExercicio> treinoExercicios = new ArrayList<>();
        
        try (Connection conn = ConexaoBD.criarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Treino treino = new TreinoDAO().findById(rs.getLong("treino_id"));
                Exercicio exercicio = exercicioDAO.findById(rs.getLong("exercicio_id"));
                
                TreinoExercicio treinoExercicio = new TreinoExercicio(
                    treino,
                    exercicio,
                    rs.getInt("series"),
                    rs.getInt("repeticoes"),
                    rs.getObject("carga", Double.class),
                    rs.getString("dia_semana")
                );
                treinoExercicio.setId(rs.getLong("treino_exercicio_id"));
                
                treinoExercicios.add(treinoExercicio);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar todas as relações treino-exercício: " + e.getMessage());
        }
        
        return treinoExercicios;
    }

    public List<TreinoExercicio> findByTreinoId(Long treinoId) throws DatabaseException {
        String sql = "SELECT * FROM Treino_Exercicios WHERE treino_id = ?";
        List<TreinoExercicio> treinoExercicios = new ArrayList<>();
        
        try (Connection conn = ConexaoBD.criarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, treinoId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Treino treino = new TreinoDAO().findById(treinoId);
                Exercicio exercicio = exercicioDAO.findById(rs.getLong("exercicio_id"));
                
                TreinoExercicio treinoExercicio = new TreinoExercicio(
                    treino,
                    exercicio,
                    rs.getInt("series"),
                    rs.getInt("repeticoes"),
                    rs.getObject("carga", Double.class),
                    rs.getString("dia_semana")
                );
                treinoExercicio.setId(rs.getLong("treino_exercicio_id"));
                
                treinoExercicios.add(treinoExercicio);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar relações treino-exercício por treino: " + e.getMessage());
        }
        
        return treinoExercicios;
    }
}