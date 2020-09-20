package br.com.levegames.dao;

import br.com.levegames.model.Console;
import br.com.levegames.utils.ConexaoDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsoleDAO {

  public List<Console> getConsoles() {

    Connection con = ConexaoDB.obterConexao();
    PreparedStatement stmt = null;
    ResultSet rs = null;

    List<Console> consoles = new ArrayList<>();

    try {
      stmt = con.prepareStatement("SELECT * FROM CONSOLES;");
      rs = stmt.executeQuery();

      while (rs.next()) {
        Console c = new Console();
        c.setId(rs.getInt("id"));
        c.setNome(rs.getString("nome"));
        consoles.add(c);
      }
    } catch (SQLException ex) {
      Logger.getLogger(ConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      ConexaoDB.fecharConexao(con, stmt, rs);
    }
    return consoles;
  }

}
