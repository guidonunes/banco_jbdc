package org.fiap.view;
import org.fiap.dao.ProdutoDao;
import org.fiap.model.Produto;
import java.sql.SQLException;
public class CadastroProdutoView {
    public static void main(String[] args) {
        try {
            ProdutoDao dao = new ProdutoDao();
            Produto produto = new Produto("Calça Jeans", "Calça Jeans azul", 50.0, 10);
            dao.cadastrar(produto);
            dao.fecharConexao();
            System.out.println("Produto cadastrado!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}