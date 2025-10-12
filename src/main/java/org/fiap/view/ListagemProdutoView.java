package org.fiap.view;

import org.fiap.dao.ProdutoDao;
import org.fiap.model.Produto;

import java.sql.SQLException;
import java.util.List;

public class ListagemProdutoView {
    public static void main(String[] args) {
        try {
            ProdutoDao dao = new ProdutoDao();
            List<Produto> produtos = dao.listar();
            for (Produto produto : produtos) {
                System.out.println(produto.getCodigo() + " " + produto.getNome() +  " " + produto.getDescricao() + ", R$ " + produto.getValor() + ", " + produto.getEstoque());

            }
            dao.fecharConexao();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
