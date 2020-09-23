package br.com.levegames.dao;

import br.com.levegames.model.ImagemProduto;
import br.com.levegames.model.Produto;
import br.com.levegames.utils.ConexaoDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImagemProdutoDAO {

  public void salvarImagensProduto(int produto_id, String[] imagens) {
    Connection con = ConexaoDB.obterConexao();
    PreparedStatement stmt = null;

    try {
      for (int i=0 ; i<imagens.length ; i++) {
       stmt = con.prepareStatement("insert into imagens_produto(produto_id, url_imagem) values(" + produto_id + ", '" + imagens[i] + "');");
       stmt.executeUpdate();
      }

    } catch (SQLException ex) {
      Logger.getLogger(ImagemProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      ConexaoDB.fecharConexao(con, stmt);
    }
  }

  public List<ImagemProduto> getImagensProduto(int produto_id) {
    Connection con = ConexaoDB.obterConexao();
    PreparedStatement stmt = null;
    ResultSet rs = null;

    List<ImagemProduto> listaImagens = new ArrayList<>();

    try {
      stmt = con.prepareStatement("SELECT * FROM imagens_produto where produto_id = " + produto_id);
      rs = stmt.executeQuery();

      while (rs.next()) {
        ImagemProduto i = new ImagemProduto();
        i.setId(rs.getInt("id"));
        i.setProduto_id(produto_id);
        i.setUrl_imagem(rs.getString("url_imagem"));
        listaImagens.add(i);
      }
    } catch (SQLException ex) {
      Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      ConexaoDB.fecharConexao(con, stmt, rs);
    }
    return listaImagens;
  }

  public void alterarImagensProduto(int produto_id) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  public void deletarImagensProduto(int produto_id) {
    Connection con = ConexaoDB.obterConexao();
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
      stmt = con.prepareStatement("DELETE FROM imagens_produto where produto_id = ?;");
      stmt.setInt(1, produto_id);
      stmt.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      ConexaoDB.fecharConexao(con, stmt, rs);
    }
  }

}
