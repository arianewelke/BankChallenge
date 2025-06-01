package br.com.compass.controllers;

import br.com.compass.dao.implement.AccountDaoJPA;
import br.com.compass.dao.implement.StatementDaoJPA;
import br.com.compass.dao.implement.UserDaoJPA;
import br.com.compass.entities.Account;
import br.com.compass.entities.User;
import br.com.compass.services.implement.AccountServiceImp;
import br.com.compass.services.implement.StatementServiceImp;
import br.com.compass.services.implement.UserServiceImp;
import br.com.compass.services.interfaces.AccountService;
import br.com.compass.services.interfaces.StatementService;
import br.com.compass.services.interfaces.UserService;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class MainMenuController {
    private final Scanner scanner;
    private final UserService userService;
    private final AccountService accountService;
    private final StatementService statementService;

    public MainMenuController() {
        this.statementService = new StatementServiceImp(new StatementDaoJPA());
        this.accountService = new AccountServiceImp(new AccountDaoJPA(), (StatementServiceImp) this.statementService);
        this.userService = new UserServiceImp(new UserDaoJPA());
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



            String option = scanner.nextLine();

            switch (option) {
                //BankMenu com login
                case "1":
                    Login();
                    break;

                case "2":
                    OpenAccount();
                    break;

                case "0":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public void Login() {
        System.out.println();
        System.out.println("========== Bank Menu - Login ==========");
        System.out.print("- Enter your CPF: ");
        String cpfLogin = scanner.next();
        System.out.print("- Enter your password: ");
        String passwordLogin = scanner.next();
        String type = "";
        boolean validType = false;
        while (!validType) {
            System.out.print("- Enter your account type ");
            System.out.print("(current, savings, salary): ");
            type = scanner.next().toLowerCase();

            if (accountService.isValidAccountType(type)) {
                validType = true;
            } else {
                System.out.println("Invalid account type. Please enter 'current', 'savings' or 'salary'.");
            }
        }
        System.out.println("=======================================");

        Account account;
        try {
            // Tenta encontrar o usuário pelo CPF e senha
            User user = userService.findByCpfAndPassword(cpfLogin, passwordLogin);
            if (user != null) {
                // Busca a conta do usuário pelo número informado
                String contaNumero = (cpfLogin + "-" + type);
                account = accountService.findByUsuarioIdAndNumero(user.getId(), contaNumero);
                if (account != null) {
                    // Login bem-sucedido, redireciona para o menu bancário
                    System.out.println();
                    System.out.println("Login successful! Welcome, " + user.getName() + ".");
                    new BankMenuController(accountService, scanner, statementService, account).run(); // Passa o usuário autenticado para o menu
                } else {
                    System.out.println("Account not found. Please check the account number.");
                }
            } else {
                System.out.println("Invalid CPF or password. Please try again.");
            }
        } catch (NoResultException e) {
            // Trata o caso em que a consulta ao banco não retorna resultados
            System.out.println("No user or account found for the provided credentials.");
        } catch (IllegalArgumentException e) {
            // Trata o caso de problemas nos parametros
            System.out.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            // Trata outros erros inesperados
            System.out.println("An error occurred: " + e.getMessage());
        }
        scanner.nextLine();
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
                    scanner.nextLine();
                    System.out.print("Enter your CPF: ");
                    String cpf = scanner.nextLine();
                    while (!userService.isValidCpf(cpf)) {
                        System.out.println("Invalid CPF. It must contain exactly 11 digits. Please try again.");

                        System.out.print("Enter your CPF: ");
                        cpf= scanner.nextLine();
                    }

                    System.out.print("Enter your password: ");
                    String password = scanner.nextLine();
                    while (!userService.isValidPassword(password)) {
                        System.out.println("Invalid password. It must contain exactly 4 digits. Please try again.");

                        System.out.print("Enter your password: ");
                        password = scanner.nextLine();
                    }

                    User user = userService.findByCpfAndPassword(cpf, password);
                    if (user != null) {
                        System.out.println("User found: " + user.getName());
                        System.out.print("Enter the type of account (current, savings, salary): ");
                        String accountType = scanner.nextLine().toLowerCase();
                        while(!accountService.isValidAccountType(accountType)) {
                            System.out.println("Invalid account. Please enter 'current', 'savings' or 'salary'.");

                            System.out.print("Enter the type of account (current, savings, salary): ");
                            accountType = scanner.nextLine().toLowerCase();
                        }

                        String numberAccount = accountService.create(0.0f, accountType, user);
                        System.out.println("Account created successfully! Account number: " + numberAccount);

                        new MainMenuController().run();

                        isCreatingAccount = false; // Finaliza o loop

                    } else {
                        System.out.println("User not found. Please check the CPF or create a new account.");
                    }
                } else if (accountOption.equalsIgnoreCase("n")) {

                    System.out.println("============================ Account Opening ====================");
                    scanner.nextLine(); // Consumir a quebra de linha
                    System.out.print("Enter your name: ");
                    String name = scanner.nextLine();
                    while (!userService.isValidName(name)) {
                        System.out.println("Invalid name. It must have at least 3 letters and contain only letters and spaces.");

                        System.out.print("Enter your name: ");
                        name = scanner.nextLine();
                    }

                    System.out.print("Enter your CPF: ");
                    String cpf = scanner.nextLine();
                    while (!userService.isValidCpf(cpf)) {
                        System.out.println("Invalid CPF. It must contain exactly 11 digits. Please try again.");

                        System.out.print("Enter your CPF: ");
                        cpf= scanner.nextLine();
                    }

                    System.out.print("Enter your phone: ");
                    String phone = scanner.nextLine();
                    while (!userService.isValidPhone(phone)) {
                        System.out.println("Invalid phone. It must contain 10 or 11 digits. Please try again.");

                        System.out.print("Enter your phone: ");
                        phone = scanner.nextLine();
                    }

                    System.out.print("Enter the type of account (current, savings, salary): ");
                    String accountType = scanner.nextLine().toLowerCase();
                    while(!accountService.isValidAccountType(accountType)) {
                        System.out.println("Invalid account. Please enter 'current', 'savings' or 'salary'.");

                        System.out.print("Enter the type of account (current, savings, salary): ");
                        accountType = scanner.nextLine().toLowerCase();
                    }

                    System.out.print("Enter your password: ");
                    String senha = scanner.nextLine();
                    while (!userService.isValidPassword(senha)) {
                        System.out.println("Invalid password. It must contain exactly 4 digits. Please try again.");

                        System.out.print("Enter your password: ");
                        senha = scanner.nextLine();
                    }

                    System.out.print("Enter your date of birth (yyyy-MM-dd): ");
                    LocalDate dataNascimento = null;
                    boolean dataValida = false;
                    while (!dataValida) {
                        try {
                            dataNascimento = LocalDate.parse(scanner.nextLine());
                            dataValida = true;
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please try again.");
                            // O loop continuara pedindo a data até o formato valido
                        }
                    }

                    User user = userService.create(name, phone, cpf, dataNascimento, senha);
                    String contaNumero = accountService.create(0.0f, accountType, user);
                    System.out.println();
                    System.out.println("Account created successfully!");
                    System.out.println("Account Number: " + contaNumero);
                    System.out.println("=================================================================");
                    isCreatingAccount = false; // Finaliza o loop

                }
            } catch (IllegalArgumentException e) {
                // Trata o caso em de problemas nos parametros
                System.out.println("Erro: " + e.getMessage());
            } catch (Exception e) {
                // Trata outros erros inesperados
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }
}

