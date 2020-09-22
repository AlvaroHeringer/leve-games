package br.com.levegames.dao;

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

}
