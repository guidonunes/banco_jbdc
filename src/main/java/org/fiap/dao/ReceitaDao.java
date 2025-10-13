package org.fiap.dao;
import org.fiap.exception.EntidadeNaoEncontradaException;
import org.fiap.factory.ConnectionFactory;
import org.fiap.model.Receita;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class ReceitaDao {
    private Connection conexao;
    public ReceitaDao() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }
    public void cadastrar(Receita receita) throws SQLException {
        PreparedStatement stm = conexao.prepareStatement ("INSERT INTO tb_receita (cd_receita, nm_receita, ds_receita, vl_receita) VALUES (seq_receita.nextval,?,?,?)");
        stm.setString(1, receita.getNome());
        stm.setString(2, receita.getDescricao());
        stm.setDouble(3, receita.getValor());
        stm.executeUpdate();
    }

    public Receita pesquisar(long codigo) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stm = conexao.prepareStatement("SELECT * FROM tb_receita WHERE cd_receita = ?");
        stm.setLong(1, codigo);
        ResultSet result = stm.executeQuery();
        if(!result.next())
            throw new EntidadeNaoEncontradaException("Produto não encontrado");

        return parseReceita(result);
    }

    private Receita parseReceita(ResultSet result) throws SQLException {
        Long id = result.getLong("cd_receita");
        String nome = result.getString("nm_receita");
        String descricao = result.getString("ds_receita");
        double valor = result.getDouble("vl_receita");
        return new Receita(id, nome, descricao, valor);
    }

    public List<Receita> listar() throws SQLException {
        PreparedStatement stm = conexao.prepareStatement("SELECT * FROM tb_receita");
        ResultSet result = stm.executeQuery();
        List<Receita> lista = new ArrayList<>();
        while (result.next()) {
            lista.add(parseReceita(result));
        }
        return lista;
    }

    public void atualizar(Receita receita) throws SQLException {
        PreparedStatement stm = conexao.prepareStatement("UPDATE tb_receita SET nm_receita= ?, ds_receita = ?, vl_produto = ? where cd_receita = ?");
        stm.setString(1, receita.getNome());
        stm.setString(2, receita.getDescricao());
        stm.setDouble(3, receita.getValor());
        stm.setLong(4, receita.getCodigo());
        stm.executeUpdate();
    }

    public void remover(long codigo) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stm = conexao.prepareStatement("DELETE FROM tb_receita WHERE cd_receita = ?");
        stm.setLong(1, codigo);
        int linha = stm.executeUpdate();
        if(linha == 0)
            throw new EntidadeNaoEncontradaException("Receita a ser removida não foi encontrada");
    }

    public void fecharConexao() throws SQLException {
        conexao.close();
    }
}