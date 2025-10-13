package org.fiap.view;

import org.fiap.dao.DespesaDao;
import org.fiap.exception.EntidadeNaoEncontradaException;
import org.fiap.model.Despesa;
import org.fiap.model.Receita;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class DespesaView {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DespesaDao dao;
        System.out.println("Bem vindo à Upside! Administre suas depesas aqui:");
        try {
            dao = new DespesaDao();
            int escolha;
            do {
                System.out.println("Escolha uma opção: \n1-Cadastrar despesa \n2-Pesquisar despesa por código \n3-Listar despesas \n4-Atualizar despesa \n5-Remover despesa \n0-Sair");
                escolha = scanner.nextInt();

                switch (escolha) {
                    case 1:
                        cadastrar(scanner, dao);
                        break;
                    case 2:
                        pesquisarDespesa(scanner, dao);
                        break;
                    case 3:
                        listar(dao);
                        break;
                    case 4:
                        atualizar(scanner, dao);
                        break;
                    case 5:
                        removerProduto(scanner, dao);
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente!");
                }
            } while (escolha != 0);
            dao.fecharConexao();
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }
    private static void cadastrar(Scanner scanner, DespesaDao dao) {
        System.out.println("Digite o nome da despesa: ");
        String nome = scanner.next() + scanner.nextLine();
        System.out.println("Digite a descrição da despesa: ");
        String descricao = scanner.next() + scanner.nextLine();
        System.out.println("Digite o valor da despesa: ");
        double valor = scanner.nextDouble();
        Despesa novoDespesa = new Despesa(nome, descricao, valor);
        try {
            dao.cadastrar(novoDespesa);
            System.out.println("Despesa cadastrada com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar despesa: " + e.getMessage());
        }
    }
    private static void pesquisarDespesa(Scanner scanner, DespesaDao dao) {
        System.out.println("Digite o código da despesa: ");
        long codigo = scanner.nextLong();
        try {
            Despesa despesa = dao.pesquisar(codigo);
            System.out.println("Despesa encontrada:");
            System.out.println(despesa.getCodigo() + " - " + despesa.getNome() + ", " + despesa.getDescricao() + ", R$" + despesa.getValor());
        } catch (SQLException | EntidadeNaoEncontradaException e) {
            System.err.println("Erro ao pesquisar despesa: " + e.getMessage());
        }
    }
    private static void listar(DespesaDao dao) {
        try {
            List<Despesa> despesas = dao.listar();
            System.out.println("Lista de despesas do mês:");
            for (Despesa despesa : despesas) {
                System.out.println(despesa.getCodigo() + " - " + despesa.getNome() + ", " + despesa.getDescricao() + ", R$" + despesa.getValor());
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar receitas: " + e.getMessage());
        }
    }
    private static void atualizar(Scanner scanner, DespesaDao dao) {
        System.out.println("Digite o código da despesa para atualizar: ");
        long codigo = scanner.nextLong();
        try {
            Despesa despesa = dao.pesquisar(codigo);
            System.out.println("Digite o nome da despesa para atualizar: ");
            String nome = scanner.next() + scanner.nextLine();
            System.out.println("Digite uma nova descrição:");
            String descricao = scanner.next() + scanner.nextLine();
            System.out.println("Digite o valor para atualizar: ");
            double valor = scanner.nextDouble();
            despesa.setNome(nome);
            despesa.setDescricao(descricao);
            despesa.setValor(valor);
            dao.atualizar(despesa);
            System.out.println("Despesa atualizada com sucesso!");

        } catch (SQLException | EntidadeNaoEncontradaException e) {
            System.err.println("Erro ao pesquisar despesa: " + e.getMessage());
        }
    }
    private static void removerProduto(Scanner scanner, DespesaDao dao) {
        System.out.println("Digite o codigo da despesa para remover: ");
        long codigo = scanner.nextLong();
        try {
            dao.remover(codigo);
            System.out.println("Despesa removida com sucesso!");
        } catch (SQLException | EntidadeNaoEncontradaException e) {
            System.err.println("Erro ao remover despesa: " + e.getMessage());
        }
    }
}
