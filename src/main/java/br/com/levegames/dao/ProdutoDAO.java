
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

public class ProdutoDAO {
    
    public List<Produto> getProdutos() {
	
	Connection con = ConexaoDB.obterConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Produto> produtos = new ArrayList<>();
        
        try{
            stmt = con.prepareStatement("SELECT * FROM PRODUTOS;");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setDescricao_curta(rs.getString("descricao_curta"));
                p.setDescricao_detalhada(rs.getString("descricao_detalhada"));
                p.setPreco(rs.getFloat("preco"));
                p.setQtde(rs.getInt("qtde"));
                p.setAtivo(rs.getBoolean("ativo"));
                p.setConsole_id(rs.getInt("console_id"));
                produtos.add(p);
            }
        }
        catch(SQLException ex){
           Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            ConexaoDB.fecharConexao(con,stmt,rs);
        }
       return produtos;
    }
    
}
