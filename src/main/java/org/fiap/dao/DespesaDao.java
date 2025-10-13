package org.fiap.dao;
import org.fiap.exception.EntidadeNaoEncontradaException;
import org.fiap.factory.ConnectionFactory;
import org.fiap.model.Despesa;
import org.fiap.model.Receita;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class DespesaDao {
    private Connection conexao;
    public DespesaDao() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }
    public void cadastrar(Despesa despesa) throws SQLException {
        PreparedStatement stm = conexao.prepareStatement ("INSERT INTO tb_despesa (cd_despesa, nm_despesa, ds_despesa, vl_despesa) VALUES (seq_despesa.nextval,?,?,?)");
        stm.setString(1, despesa.getNome());
        stm.setString(2, despesa.getDescricao());
        stm.setDouble(3, despesa.getValor());
        stm.executeUpdate();
    }

    public Despesa pesquisar(long codigo) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stm = conexao.prepareStatement("SELECT * FROM tb_despesa WHERE cd_despesa = ?");
        stm.setLong(1, codigo);
        ResultSet result = stm.executeQuery();
        if(!result.next())
            throw new EntidadeNaoEncontradaException("Produto não encontrado");

        return parseDespesa(result);
    }

    private Despesa parseDespesa(ResultSet result) throws SQLException {
        Long id = result.getLong("cd_despesa");
        String nome = result.getString("nm_despesa");
        String descricao = result.getString("ds_despesa");
        double valor = result.getDouble("vl_despesa");
        return new Despesa(id, nome, descricao, valor);
    }

    public List<Despesa> listar() throws SQLException {
        PreparedStatement stm = conexao.prepareStatement("SELECT * FROM tb_despesa");
        ResultSet result = stm.executeQuery();
        List<Despesa> lista = new ArrayList<>();
        while (result.next()) {
            lista.add(parseDespesa(result));
        }
        return lista;
    }

    public void atualizar(Despesa despesa) throws SQLException {
        PreparedStatement stm = conexao.prepareStatement("UPDATE tb_despesa SET nm_despesa= ?, ds_despesa = ?, vl_despesa = ? where cd_despesa = ?");
        stm.setString(1, despesa.getNome());
        stm.setString(2, despesa.getDescricao());
        stm.setDouble(3, despesa.getValor());
        stm.setLong(4, despesa.getCodigo());
        stm.executeUpdate();
    }

    public void remover(long codigo) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stm = conexao.prepareStatement("DELETE FROM tb_despesa WHERE cd_despesa = ?");
        stm.setLong(1, codigo);
        int linha = stm.executeUpdate();
        if(linha == 0)
            throw new EntidadeNaoEncontradaException("Despesa a ser removida não foi encontrada");
    }

    public void fecharConexao() throws SQLException {
        conexao.close();
    }
}