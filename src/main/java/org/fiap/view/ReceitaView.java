package org.fiap.view;

import org.fiap.dao.ReceitaDao;
import org.fiap.exception.EntidadeNaoEncontradaException;
import org.fiap.model.Receita;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ReceitaView {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ReceitaDao dao;
        System.out.println("Bem vindo à Upside! Administre suas receitas aqui: ");
        try {
            dao = new ReceitaDao();
            int escolha;
            do {
                System.out.println("Escolha uma opção: \n1-Cadastrar receita \n2-Pesquisar receita por código \n3-Listar receitas \n4-Atualizar receitas \n5-Remover receita \n0-Sair");
                escolha = scanner.nextInt();

                switch (escolha) {
                    case 1:
                        cadastrar(scanner, dao);
                        break;
                    case 2:
                        pesquisarReceita(scanner, dao);
                        break;
                    case 3:
                        listar(dao);
                        break;
                    case 4:
                        atualizar(scanner, dao);
                        break;
                    case 5:
                        removerReceita(scanner, dao);
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
    private static void cadastrar(Scanner scanner, ReceitaDao dao) {
        System.out.println("Digite o nome da receita: ");
        String nome = scanner.next() + scanner.nextLine();
        System.out.println("Digite a descrição da receita: ");
        String descricao = scanner.next() + scanner.nextLine();
        System.out.println("Digite o valor da receita: ");
        double valor = scanner.nextDouble();
        Receita novoReceita = new Receita(nome, descricao, valor);
        try {
            dao.cadastrar(novoReceita);
            System.out.println("Receita cadastrada com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar receita: " + e.getMessage());
        }
    }
    private static void pesquisarReceita(Scanner scanner, ReceitaDao dao) {
        System.out.println("Digite o código da receita: ");
        long codigo = scanner.nextLong();
        try {
            Receita receita = dao.pesquisar(codigo);
            System.out.println("Receita encontrada:");
            System.out.println(receita.getCodigo() + " - " + receita.getNome() + ", " + receita.getDescricao() + ", R$" + receita.getValor());
        } catch (SQLException | EntidadeNaoEncontradaException e) {
            System.err.println("Erro ao pesquisar receita: " + e.getMessage());
        }
    }
    private static void listar(ReceitaDao dao) {
        try {
            List<Receita> receitas = dao.listar();
            System.out.println("Lista de receitas do mês:");
            for (Receita receita : receitas) {
                System.out.println(receita.getCodigo() + " - " + receita.getNome() + ", " + receita.getDescricao() + ", R$" + receita.getValor());
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar receitas: " + e.getMessage());
        }
    }
    private static void atualizar(Scanner scanner, ReceitaDao dao) {
        System.out.println("Digite o código da receita para atualizar: ");
        long codigo = scanner.nextLong();
        try {
            Receita receita = dao.pesquisar(codigo);
            System.out.println("Digite o nome da receita para atualizar: ");
            String nome = scanner.next() + scanner.nextLine();
            System.out.println("Digite uma nova descrição:");
            String descricao = scanner.next() + scanner.nextLine();
            System.out.println("Digite o valor para atualizar: ");
            double valor = scanner.nextDouble();
            receita.setNome(nome);
            receita.setDescricao(descricao);
            receita.setValor(valor);
            dao.atualizar(receita);
            System.out.println("Receita atualizada com sucesso!");

        } catch (SQLException | EntidadeNaoEncontradaException e) {
            System.err.println("Erro ao pesquisar receita: " + e.getMessage());
        }
    }
    private static void removerReceita(Scanner scanner, ReceitaDao dao) {
        System.out.println("Digite o codigo da receita para remover: ");
        long codigo = scanner.nextLong();
        try {
            dao.remover(codigo);
            System.out.println("Receita removida com sucesso!");
        } catch (SQLException | EntidadeNaoEncontradaException e) {
            System.err.println("Erro ao remover receita: " + e.getMessage());
        }
    }
}
