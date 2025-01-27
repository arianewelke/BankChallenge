package br.com.compass.controllers;

import br.com.compass.dao.implement.ContaDaoJPA;
import br.com.compass.dao.implement.HistoricoDaoJPA;
import br.com.compass.dao.implement.UsuarioDaoJPA;
import br.com.compass.entities.Conta;
import br.com.compass.entities.Usuario;
import br.com.compass.services.implement.ContaServiceImp;
import br.com.compass.services.implement.HistoricoServiceImp;
import br.com.compass.services.implement.UsuarioServiceImp;
import br.com.compass.services.interfaces.ContaService;
import br.com.compass.services.interfaces.HistoricoService;
import br.com.compass.services.interfaces.UsuarioService;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.Scanner;

public class MainMenuController {
    private final Scanner scanner;
    private final UsuarioService usuarioService;
    private final ContaService contaService;
    private final HistoricoService historicoService;

    public MainMenuController() {
        this.historicoService = new HistoricoServiceImp(new HistoricoDaoJPA());
        this.contaService = new ContaServiceImp(new ContaDaoJPA(), (HistoricoServiceImp) this.historicoService);
        this.usuarioService = new UsuarioServiceImp(new UsuarioDaoJPA());
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        boolean running = true;

        while (running) {
            System.out.println("========= Main Menu =========");
            System.out.println("|| 1. Login                ||");
            System.out.println("|| 2. Account Opening      ||");
            System.out.println("|| 0. Exit                 ||");
            System.out.println("=============================");
            System.out.print("Choose an option: ");


            int option = scanner.nextInt();

            switch (option) {
                case 1: //BankMenu com login
                    System.out.println();
                    System.out.println("========== Bank Menu - Login ==========");
                    System.out.print("- Enter your CPF: ");
                    String cpfLogin = scanner.next();
                    System.out.print("- Enter your password: ");
                    String senhaLogin = scanner.next();
                    System.out.println("- Enter your account type ");
                    System.out.print("(current, savings, salary): ");
                    String tipo = scanner.next();
                    System.out.println("=======================================");

                    Conta conta = null;
                    try {
                        // Tenta encontrar o usuário pelo CPF e senha
                        Usuario usuario = usuarioService.findByCpfAndPassword(cpfLogin, senhaLogin);
                        if (usuario != null) {
                            // Busca a conta do usuário pelo número informado
                            String contaNumero = (cpfLogin + "-" + tipo);
                            conta = contaService.findByUsuarioIdAndNumero(usuario.getId(), contaNumero);
                            if (conta != null) {
                                // Login bem-sucedido, redireciona para o menu bancário
                                System.out.println();
                                System.out.println("Login successful! Welcome, " + usuario.getNome() + ".");
                                new BankMenuController(contaService, scanner, historicoService, conta).run(); // Passa o usuário autenticado para o menu
                            } else {
                                System.out.println("Account not found. Please check the account number.");
                            }
                        } else {
                            System.out.println("Invalid CPF or password. Please try again.");
                        }
                    } catch (NoResultException e) {
                        // Trata o caso em que a consulta ao banco não retorna resultados
                        System.out.println("No user or account found for the provided credentials.");
                    } catch (Exception e) {
                        // Trata outros erros inesperados
                        System.out.println("An error occurred: " + e.getMessage());
                    }
                    new BankMenuController(contaService, scanner, historicoService, conta);
                    break;

                case 2:
                    OpenAccount();
                    break;

                case 0:
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public void OpenAccount() {
        boolean isCreatingAccount = true;
        while (isCreatingAccount) {
            try {
                System.out.println();
                System.out.println("============================ Account Opening ====================");
                System.out.print("Are you a bank customer? (Y/N): ");
                String accountOption = scanner.next();

                while (!accountOption.equalsIgnoreCase("y") && !accountOption.equalsIgnoreCase("n")) {
                    System.out.println("Invalid option! Please enter 'Y' or 'N'.");
                    System.out.print("Are you a bank customer? (Y/N): ");
                    accountOption = scanner.next();
                }

                if (accountOption.equalsIgnoreCase("y")) {
                    System.out.print("Enter your CPF: ");
                    String cpf = scanner.next();
                    System.out.print("Enter password: ");
                    String senha = scanner.next();

                    Usuario usuario = usuarioService.findByCpfAndPassword(cpf, senha);
                    if (usuario != null) {
                        System.out.println("User found: " + usuario.getNome());
                        System.out.print("Enter the type of account (current, savings, salary): ");
                        scanner.nextLine(); // Consumir a quebra de linha
                        String tipoConta = scanner.nextLine().toLowerCase(); // Converter string em minusculas

                        String contaNumero = contaService.create(0.0f, tipoConta, usuario);
                        System.out.println("Account created successfully! Account number: " + contaNumero);

                        new MainMenuController().run();

                        isCreatingAccount = false; // Finaliza o loop

                    } else {
                        System.out.println("User not found. Please check the CPF or create a new account.");
                    }
                } else if (accountOption.equalsIgnoreCase("n")) {

                    System.out.println("============================ Account Opening ====================");
                    scanner.nextLine(); // Consumir a quebra de linha
                    System.out.print("Enter your name: ");
                    String nome = scanner.nextLine();

                    System.out.print("Enter your CPF: ");
                    String cpf = scanner.nextLine();

                    System.out.print("Enter your phone: ");
                    String telefone = scanner.nextLine();

                    System.out.print("Enter the type of account (current, savings, salary): ");
                    String tipoOpen = scanner.nextLine().toLowerCase();

                    System.out.print("Enter your password: ");
                    String senha = scanner.nextLine();

                    System.out.print("Enter your date of birth (yyyy-MM-dd): ");
                    LocalDate dataNascimento = null;
                    try {
                        dataNascimento = LocalDate.parse(scanner.nextLine());
                    } catch (Exception e) {
                        System.out.println("Invalid date format. Please try again.");
                        continue; // Recomeça o loop
                    }

                    Usuario usuario = usuarioService.create(nome, telefone, cpf, dataNascimento, senha);
                    String contaNumero = contaService.create(0.0f, tipoOpen, usuario);
                    System.out.println("Account created successfully!");
                    System.out.println("Account Number: " + contaNumero);
                    System.out.println("=================================================================");
                    isCreatingAccount = false; // Finaliza o loop

                }
            } catch (Exception e) {
                System.err.println("Error during account creation: " + e.getMessage());
                break;
            }
        }
    }
}

