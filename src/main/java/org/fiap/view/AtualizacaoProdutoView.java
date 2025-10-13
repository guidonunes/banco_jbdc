package org.fiap.view;

import org.fiap.dao.ProdutoDao;
import org.fiap.exception.EntidadeNaoEncontradaException;
import org.fiap.model.Produto;

import java.sql.SQLException;

public class AtualizacaoProdutoView {
    public static void main(String[] args) {
        try {
            ProdutoDao dao = new ProdutoDao();
            Produto produto = dao.pesquisar(1);
            produto.setNome("Camisa Polo");
            produto.setDescricao("Camisa Polo Azul");
            produto.setEstoque(10);
            produto.setValor(50.0);
            dao.atualizar(produto);
            dao.fecharConexao();
            System.out.println("Produto atualizado com sucesso");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Produto não encontrado");
        }

    }
}
