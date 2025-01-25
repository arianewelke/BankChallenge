package br.com.compass;

import br.com.compass.dao.implement.ContaDaoJPA;
import br.com.compass.dao.implement.UsuarioDaoJPA;
import br.com.compass.entities.Usuario;
import br.com.compass.entities.Conta;
import br.com.compass.services.implement.ContaServiceImp;
import br.com.compass.services.implement.UsuarioServiceImp;
import br.com.compass.services.interfaces.ContaService;
import br.com.compass.services.interfaces.UsuarioService;

import java.time.LocalDate;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        UsuarioService usuarioService = new UsuarioServiceImp(new UsuarioDaoJPA());
        ContaService contaService = new ContaServiceImp(new ContaDaoJPA());

        mainMenu(scanner, usuarioService, contaService);
        scanner.close();
        System.out.println("Application closed");
    }

    public static void mainMenu(Scanner scanner, UsuarioService usuarioService, ContaService contaService) {
        boolean running = true;

        while (running) {
            System.out.println("=================================");
            System.out.println("|| 1. Bank Menu                ||");
            System.out.println("|| 2. Account Opening          ||");
            System.out.println("|| 0. Exit                     ||");
            System.out.println("=================================");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    bankMenu(scanner);
                    return;

                case 2:
                    boolean isCreatingAccount = true;
                    while (isCreatingAccount) {
                        try {
                            System.out.println();
                            System.out.println("============================ Open Account ====================");
                            System.out.print("Are you a bank customer? (Y/N): ");
                            String accountOption = scanner.next();

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
                                    String tipo = scanner.nextLine().toLowerCase();

                                    String contaNumero = contaService.create(0.0f, tipo, usuario);
                                    System.out.println("Account created successfully! Account number: " + contaNumero);

                                    mainMenu(scanner, usuarioService, contaService);
                                    isCreatingAccount = false; // Finaliza o loop
                                } else {
                                    System.out.println("User not found. Please check the CPF or create a new account.");
                                }
                            } else if (accountOption.equalsIgnoreCase("n")) {
                                System.out.println("============================ Open Account ====================");
                                scanner.nextLine(); // Consumir a quebra de linha
                                System.out.print("Enter your name: ");
                                String nome = scanner.nextLine();

                                System.out.print("Enter your CPF: ");
                                String cpf = scanner.nextLine();

                                System.out.print("Enter your phone: ");
                                String telefone = scanner.nextLine();

                                System.out.print("Enter the type of account (current, savings, salary): ");
                                String tipo = scanner.nextLine().toLowerCase();

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
                                String contaNumero = contaService.create(0.0f, tipo, usuario);
                                System.out.println("Account created successfully!");
                                System.out.println("Account Number: " + contaNumero);
                                System.out.println("===============================================================");
                                isCreatingAccount = false; // Finaliza o loop
                            } else {
                                System.out.println("Invalid option! Please enter 'Y' or 'N'.");
                            }

                        } catch (Exception e) {
                            System.err.println("Error during account creation: " + e.getMessage());
                        }
                    }
                    break;

                case 0:
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    public static void bankMenu(Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.println("========= Bank Menu =========");
            System.out.println("|| 1. Deposit              ||");
            System.out.println("|| 2. Withdraw             ||");
            System.out.println("|| 3. Check Balance        ||");
            System.out.println("|| 4. Transfer             ||");
            System.out.println("|| 5. Bank Statement       ||");
            System.out.println("|| 0. Exit                 ||");
            System.out.println("=============================");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    // ToDo...
                    System.out.println("Deposit.");
                    break;
                case 2:
                    // ToDo...
                    System.out.println("Withdraw.");
                    break;
                case 3:
                    // ToDo...
                    System.out.println("Check Balance.");
                    break;
                case 4:
                    // ToDo...
                    System.out.println("Transfer.");
                    break;
                case 5:
                    // ToDo...
                    System.out.println("Bank Statement.");
                    break;
                case 0:
                    // ToDo...
                    System.out.println("Exiting...");
                    running = false;
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }
}
