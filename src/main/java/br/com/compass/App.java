package br.com.compass;

import br.com.compass.dao.implement.ContaDaoJPA;
import br.com.compass.dao.implement.UsuarioDaoJPA;
import br.com.compass.services.implement.ContaServiceImp;
import br.com.compass.services.implement.UsuarioServiceImp;
import br.com.compass.services.interfaces.ContaService;
import br.com.compass.services.interfaces.UsuarioService;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        UsuarioServiceImp usuarioService = new UsuarioServiceImp(new UsuarioDaoJPA());
        ContaServiceImp contaService = new ContaServiceImp(new ContaDaoJPA());

        Scanner scanner = new Scanner(System.in);
        mainMenu(scanner, usuarioService, contaService);
        scanner.close();
        System.out.println("Application closed");
    }

    public static void mainMenu(Scanner scanner, UsuarioService usuarioService, ContaService contaService) {
        boolean running = true;

        while (running) {
            System.out.println("========= Main Menu =========");
            System.out.println("|| 1. Login                ||");
            System.out.println("|| 2. Account Opening      ||"); //Já é cliente? Sim e Não (Se for, faça login, se não fluxo de abertura normal) //Fez login - mostra tela normal
            System.out.println("|| 0. Exit                 ||");
            System.out.println("=============================");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    bankMenu(scanner);
                    return;
                case 2:
                    // ToDo...
                    System.out.println("Account Opening.");

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