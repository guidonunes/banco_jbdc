package org.fiap.dao;
import org.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Produto;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ProdutoDao {
    private Connection conexao;
    public ProdutoDao() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }
    public void cadastrar(Produto produto) throws SQLException {
        Statement stm = conexao.createStatement();
        stm.executeUpdate("INSERT INTO tb_produto (cd_produto, nm_produto, ds_produto, vl_produto, nr_estoque) VALUES (seq_produto.nextval,' "
                + produto.getNome() + " ' , ' " + produto.getDescricao() + " ' ," + produto.getValor() + "," + produto.getValor() + ")");
    }
    public void fecharConexao() throws SQLException {
        conexao.close();
    }
//...
}