package model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.entities.Usuario;
import model.exceptions.DatabaseException;
import util.ConexaoBD;

public class UsuarioDAO implements BaseDAO<Usuario> {

    @Override
    public void insert(Usuario usuario) throws DatabaseException {
        String sql = "INSERT INTO Usuarios (nome, email, senha_hash, data_nascimento, genero) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexaoBD.criarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenhaHash());
            stmt.setDate(4, Date.valueOf(usuario.getDataNascimento()));
            stmt.setString(5, usuario.getGenero());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DatabaseException("Falha ao criar usuário, nenhuma linha afetada.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuario.setId(generatedKeys.getLong(1));
                } else {
                    throw new DatabaseException("Falha ao criar usuário, nenhum ID obtido.");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao inserir usuário: " + e.getMessage());
        }
    }

    @Override
    public void update(Usuario usuario) throws DatabaseException {
        String sql = "UPDATE Usuarios SET nome = ?, email = ?, senha_hash = ?, data_nascimento = ?, genero = ? WHERE usuario_id = ?";
        
        try (Connection conn = ConexaoBD.criarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenhaHash());
            stmt.setDate(4, Date.valueOf(usuario.getDataNascimento()));
            stmt.setString(5, usuario.getGenero());
            stmt.setLong(6, usuario.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) throws DatabaseException {
        String sql = "DELETE FROM Usuarios WHERE usuario_id = ?";
        
        try (Connection conn = ConexaoBD.criarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao deletar usuário: " + e.getMessage());
        }
    }

    @Override
    public Usuario findById(Long id) throws DatabaseException {
        String sql = "SELECT * FROM Usuarios WHERE usuario_id = ?";
        Usuario usuario = null;
        
        try (Connection conn = ConexaoBD.criarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                usuario = new Usuario(
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("senha_hash"),
                    rs.getDate("data_nascimento").toLocalDate(),
                    rs.getString("genero")
                );
                usuario.setId(rs.getLong("usuario_id"));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar usuário por ID: " + e.getMessage());
        }
        
        return usuario;
    }

    @Override
    public List<Usuario> findAll() throws DatabaseException {
        String sql = "SELECT * FROM Usuarios";
        List<Usuario> usuarios = new ArrayList<>();
        
        try (Connection conn = ConexaoBD.criarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Usuario usuario = new Usuario(
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("senha_hash"),
                    rs.getDate("data_nascimento").toLocalDate(),
                    rs.getString("genero")
                );
                usuario.setId(rs.getLong("usuario_id"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar todos os usuários: " + e.getMessage());
        }
        
        return usuarios;
    }

    public Usuario findByEmail(String email) throws DatabaseException {
        String sql = "SELECT * FROM Usuarios WHERE email = ?";
        Usuario usuario = null;
        
        try (Connection conn = ConexaoBD.criarConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                usuario = new Usuario(
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("senha_hash"),
                    rs.getDate("data_nascimento").toLocalDate(),
                    rs.getString("genero")
                );
                usuario.setId(rs.getLong("usuario_id"));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar usuário por email: " + e.getMessage());
        }
        
        return usuario;
    }
}