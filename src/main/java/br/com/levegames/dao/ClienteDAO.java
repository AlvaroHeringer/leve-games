/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.levegames.dao;

import br.com.levegames.model.Cliente;
import br.com.levegames.utils.ConexaoDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author victoria.sousa
 */
public class ClienteDAO {
    
    public List<Cliente> getCliente() {

    Connection con = ConexaoDB.obterConexao();
    PreparedStatement stmt = null;
    ResultSet rs = null;

    List<Cliente> cliente = new ArrayList<>();

    try {
      stmt = con.prepareStatement("SELECT * FROM CLIENTES where registro_deletado = false;");
      rs = stmt.executeQuery();

      while (rs.next()) {
        Cliente c = new Cliente();
        c.setId(rs.getInt("id"));
        c.setNome(rs.getString("nome"));
        c.setCpf(rs.getString("cpf"));
        c.setTelefone(rs.getString("telefone"));
        c.setEmail(rs.getString("email"));
        c.setSenha(rs.getString("senha"));
        c.setRegistro_deletado(rs.getBoolean("registro_deletado"));
        cliente.add(c);
      }
    } catch (SQLException ex) {
      Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      ConexaoDB.fecharConexao(con, stmt, rs);
    }
    return cliente;
  }
    
    public void removeCliente(int id) {
    Connection con = ConexaoDB.obterConexao();
    PreparedStatement stmt = null;

    try {
      stmt = con.prepareStatement("update cliente set registro_deletado = true where id = ?");

      stmt.setInt(1, id);

      stmt.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      ConexaoDB.fecharConexao(con, stmt);
    }
  }
    
    public void salvarProduto(Cliente c) {
    Connection con = ConexaoDB.obterConexao();
    PreparedStatement stmt = null;

    try {
      stmt = con.prepareStatement("insert into cliente (nome,cpf,telefone,email,senha,registro_deletado) values ( ?, ?, ?, ?, ?, ?,  false);");

      stmt.setString(1, c.getNome());
      stmt.setString(2, c.getCpf());
      stmt.setString(3, c.getTelefone());
      stmt.setString(4, c.getEmail());
      stmt.setString(5, c.getSenha());
      
     
      

      stmt.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      ConexaoDB.fecharConexao(con, stmt);
    }
    }
    
    public int getUltimoCliente() {
    Connection con = ConexaoDB.obterConexao();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    int cliente_id = 0;

    try {
      stmt = con.prepareStatement("SELECT MAX(id) as id FROM CLIENTE;");
      rs = stmt.executeQuery();

      while (rs.next()) {
        cliente_id = rs.getInt("id");

      }
    } catch (SQLException ex) {
      Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      ConexaoDB.fecharConexao(con, stmt, rs);
    }
    return cliente_id;
  }
    
    public Cliente getCliente(int id) {
    Connection con = ConexaoDB.obterConexao();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    Cliente c = new Cliente();

    try {
      stmt = con.prepareStatement("SELECT * FROM CLIENTE WHERE id = " + id);
      rs = stmt.executeQuery();

      rs.next();

      
        c.setId(rs.getInt("id"));
        c.setNome(rs.getString("nome"));
        c.setCpf(rs.getString("cpf"));
        c.setTelefone(rs.getString("telefone"));
        c.setEmail(rs.getString("email"));
        c.setSenha(rs.getString("senha"));
        c.setRegistro_deletado(rs.getBoolean("registro_deletado"));
        

    } catch (SQLException ex) {
      Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      ConexaoDB.fecharConexao(con, stmt, rs);
    }
    return c;
  }
    
    public void alterarCliente(Cliente c) {
    Connection con = ConexaoDB.obterConexao();
    PreparedStatement stmt = null;

    try {
      stmt = con.prepareStatement("update produtos set nome = ?, cpf = ?, telefone = ?, email = ?, senha = ?,  where id = ?;");

      stmt.setString(1, c.getNome());
      stmt.setString(2, c.getCpf());
      stmt.setString(3, c.getTelefone());
      stmt.setString(4, c.getEmail());
      stmt.setString(5, c.getSenha());
      stmt.setInt(6, c.getId());
      stmt.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      ConexaoDB.fecharConexao(con, stmt);
    }
  }
}
