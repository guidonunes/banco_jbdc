package org.fiap.view;

import org.fiap.dao.ProdutoDao;
import org.fiap.exception.EntidadeNaoEncontradaException;

import java.sql.SQLException;

public class RemocaoProdutoView {
    public static void main(String[] args) {
        try {
            ProdutoDao dao = new ProdutoDao();
            dao.remover(1);
            dao.fecharConexao();
            System.out.println("Produto Removido");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Produto não encontrado");
        }
    }
}
