package org.fiap.view;

import org.fiap.dao.ProdutoDao;
import org.fiap.exception.EntidadeNaoEncontradaException;
import org.fiap.model.Produto;

import java.sql.SQLException;

public class PesquisaProdutoPorIdView {
    public static void main(String[] args) {
        try {
            ProdutoDao dao = new ProdutoDao();
            Produto produto = dao.pesquisar(1);
            System.out.println(produto.getCodigo()+ " " + produto.getNome() + ", " + produto.getDescricao());
            System.out.println("R$ " + produto.getValor() + ", " + produto.getEstoque());
            dao.fecharConexao();

        } catch (SQLException e ) {
            System.err.println(e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Codigo não existe na tabela");
        }
    }
}
