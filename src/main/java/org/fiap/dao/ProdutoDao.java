package org.fiap.dao;
import org.fiap.factory.ConnectionFactory;
import org.fiap.model.Produto;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.List;

public class ProdutoDao {
    private Connection conexao;
    public ProdutoDao() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }
    public void cadastrar(Produto produto) throws SQLException {
        PreparedStatement stm = conexao.prepareStatement ("INSERT INTO tb_produto (cd_produto, nm_produto, ds_produto, vl_produto, nr_estoque) VALUES (seq_produto.nextval,?,?,?,?)");
        stm.setString(1, produto.getNome());
        stm.setString(2, produto.getDescricao());
        stm.setDouble(3, produto.getValor());
        stm.setInt(4, produto.getEstoque());
        stm.executeUpdate();
    }
    public void fecharConexao() throws SQLException {
        conexao.close();
    }
//...
}