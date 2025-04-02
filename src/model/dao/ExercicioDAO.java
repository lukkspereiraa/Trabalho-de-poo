package model.dao;

import model.entities.ExercicioLivre;
import model.entities.ExercicioMaquina;
import model.entities.Maquina;
import model.exceptions.DatabaseException;
import model.interfaces.Exercicio;
import util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExercicioDAO implements BaseDAO<Exercicio> {

    private MaquinaDAO maquinaDAO = new MaquinaDAO();

    @Override
    public void insert(Exercicio exercicio) throws DatabaseException {
        if (exercicio instanceof ExercicioLivre) {
            insertExercicioLivre((ExercicioLivre) exercicio);
        } else if (exercicio instanceof ExercicioMaquina) {
            insertExercicioMaquina((ExercicioMaquina) exercicio);
        }
    }

    private void insertExercicioLivre(ExercicioLivre exercicio) throws DatabaseException {
        String sql = "INSERT INTO Exercicios (nome, grupo_muscular, descricao, tipo) VALUES (?, ?, ?, 'Livre')";
        
        try (Connection conn = ConexaoBD.criarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, exercicio.getNome());
            stmt.setString(2, exercicio.getGrupoMuscular());
            stmt.setString(3, exercicio.getDescricao());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DatabaseException("Falha ao criar exercício livre, nenhuma linha afetada.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    exercicio.setId(generatedKeys.getLong(1));
                } else {
                    throw new DatabaseException("Falha ao criar exercício livre, nenhum ID obtido.");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao inserir exercício livre: " + e.getMessage());
        }
    }

    private void insertExercicioMaquina(ExercicioMaquina exercicio) throws DatabaseException {
        String sql = "INSERT INTO Exercicios (nome, grupo_muscular, descricao, tipo, maquina_id) VALUES (?, ?, ?, 'Máquina', ?)";
        
        try (Connection conn = ConexaoBD.criarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, exercicio.getNome());
            stmt.setString(2, exercicio.getGrupoMuscular());
            stmt.setString(3, exercicio.getDescricao());
            stmt.setLong(4, exercicio.getMaquina().getId());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DatabaseException("Falha ao criar exercício em máquina, nenhuma linha afetada.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    exercicio.setId(generatedKeys.getLong(1));
                } else {
                    throw new DatabaseException("Falha ao criar exercício em máquina, nenhum ID obtido.");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao inserir exercício em máquina: " + e.getMessage());
        }
    }

    @Override
    public void update(Exercicio exercicio) throws DatabaseException {
        if (exercicio instanceof ExercicioLivre) {
            updateExercicioLivre((ExercicioLivre) exercicio);
        } else if (exercicio instanceof ExercicioMaquina) {
            updateExercicioMaquina((ExercicioMaquina) exercicio);
        }
    }

    private void updateExercicioLivre(ExercicioLivre exercicio) throws DatabaseException {
        String sql = "UPDATE Exercicios SET nome = ?, grupo_muscular = ?, descricao = ?, tipo = 'Livre', maquina_id = NULL WHERE exercicio_id = ?";
        
        try (Connection conn = ConexaoBD.criarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, exercicio.getNome());
            stmt.setString(2, exercicio.getGrupoMuscular());
            stmt.setString(3, exercicio.getDescricao());
            stmt.setLong(4, exercicio.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao atualizar exercício livre: " + e.getMessage());
        }
    }

    private void updateExercicioMaquina(ExercicioMaquina exercicio) throws DatabaseException {
        String sql = "UPDATE Exercicios SET nome = ?, grupo_muscular = ?, descricao = ?, tipo = 'Máquina', maquina_id = ? WHERE exercicio_id = ?";
        
        try (Connection conn = ConexaoBD.criarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, exercicio.getNome());
            stmt.setString(2, exercicio.getGrupoMuscular());
            stmt.setString(3, exercicio.getDescricao());
            stmt.setLong(4, exercicio.getMaquina().getId());
            stmt.setLong(5, exercicio.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao atualizar exercício em máquina: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) throws DatabaseException {
        String sql = "DELETE FROM Exercicios WHERE exercicio_id = ?";
        
        try (Connection conn = ConexaoBD.criarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao deletar exercício: " + e.getMessage());
        }
    }

    @Override
    public Exercicio findById(Long id) throws DatabaseException {
        String sql = "SELECT * FROM Exercicios WHERE exercicio_id = ?";
        Exercicio exercicio = null;
        
        try (Connection conn = ConexaoBD.criarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String tipo = rs.getString("tipo");
                
                if ("Livre".equals(tipo)) {
                    exercicio = new ExercicioLivre(
                        rs.getString("nome"),
                        rs.getString("grupo_muscular"),
                        rs.getString("descricao")
                    );
                } else if ("Máquina".equals(tipo)) {
                    Long maquinaId = rs.getLong("maquina_id");
                    Maquina maquina = maquinaId != 0 ? maquinaDAO.findById(maquinaId) : null;
                    
                    exercicio = new ExercicioMaquina(
                        rs.getString("nome"),
                        rs.getString("grupo_muscular"),
                        rs.getString("descricao"),
                        maquina
                    );
                }
                
                if (exercicio != null) {
                    exercicio.setId(rs.getLong("exercicio_id"));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar exercício por ID: " + e.getMessage());
        }
        
        return exercicio;
    }

    @Override
    public List<Exercicio> findAll() throws DatabaseException {
        String sql = "SELECT * FROM Exercicios";
        List<Exercicio> exercicios = new ArrayList<>();
        
        try (Connection conn = ConexaoBD.criarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                String tipo = rs.getString("tipo");
                Exercicio exercicio;
                
                if ("Livre".equals(tipo)) {
                    exercicio = new ExercicioLivre(
                        rs.getString("nome"),
                        rs.getString("grupo_muscular"),
                        rs.getString("descricao")
                    );
                } else {
                    Long maquinaId = rs.getLong("maquina_id");
                    Maquina maquina = maquinaId != 0 ? maquinaDAO.findById(maquinaId) : null;
                    
                    exercicio = new ExercicioMaquina(
                        rs.getString("nome"),
                        rs.getString("grupo_muscular"),
                        rs.getString("descricao"),
                        maquina
                    );
                }
                exercicio.s
                exercicio.setId(rs.getLong("exercicio_id"));
                exercicios.add(exercicio);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar todos os exercícios: " + e.getMessage());
        }
        
        return exercicios;
    }
}