package org.fiap.dao;
import org.fiap.exception.EntidadeNaoEncontradaException;
import org.fiap.factory.ConnectionFactory;
import org.fiap.model.Produto;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
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

    public Produto pesquisar(long codigo) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stm = conexao.prepareStatement("SELECT * FROM tb_produto WHERE cd_produto = ?");
        stm.setLong(1, codigo);
        ResultSet result = stm.executeQuery();
        if(!result.next())
            throw new EntidadeNaoEncontradaException("Produto não encontrado");

        return parseProduto(result);
    }

    private Produto parseProduto(ResultSet result) throws SQLException {
        Long id = result.getLong("cd_produto");
        String nome = result.getString("nm_produto");
        String descricao = result.getString("ds_produto");
        double valor = result.getDouble("vl_produto");
        int estoque = result.getInt("nr_estoque");
        return new Produto(id, nome, descricao, valor, estoque);
    }

    public List<Produto> listar() throws SQLException {
        PreparedStatement stm = conexao.prepareStatement("SELECT * FROM tb_produto");
        ResultSet result = stm.executeQuery();
        List<Produto> lista = new ArrayList<>();
        while (result.next()) {
            lista.add(parseProduto(result));
        }
        return lista;
    }

    public void atualizar(Produto produto) throws SQLException {
        PreparedStatement stm = conexao.prepareStatement("UPDATE tb_produto SET nm_produto= ?, ds_produto = ?, vl_produto = ?, nr_estoque = ? where cd_produto = ?");
        stm.setString(1, produto.getNome());
        stm.setString(2, produto.getDescricao());
        stm.setDouble(3, produto.getValor());
        stm.setInt(4, produto.getEstoque());
        stm.setLong(5, produto.getCodigo());
        stm.executeUpdate();
    }

    public void remover(long codigo) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stm = conexao.prepareStatement("DELETE FROM tb_produto WHERE cd_produto = ?");
        stm.setLong(1, codigo);
        int linha = stm.executeUpdate();
        if(linha == 0)
            throw new EntidadeNaoEncontradaException("Produto a ser removido não foi encontrado");
    }

    public void fecharConexao() throws SQLException {
        conexao.close();
    }
//...
}