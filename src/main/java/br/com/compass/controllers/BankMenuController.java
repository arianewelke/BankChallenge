package br.com.compass.controllers;

import br.com.compass.entities.Account;
import br.com.compass.entities.Statement;
import br.com.compass.services.interfaces.AccountService;
import br.com.compass.services.interfaces.StatementService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;


public class BankMenuController {
    private final Scanner scanner;
    private final AccountService accountService;
    private final StatementService statementService;
    private final Account account;

    public BankMenuController(AccountService accountService, Scanner scanner, StatementService statementService, Account account) {
        this.accountService = accountService;
        this.scanner = scanner;
        this.statementService = statementService;
        this.account = account;
    }

    public void run() {
        boolean running = true;

        while (running) {
            System.out.println();
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
                    deposit();
                    break;
                case 2:
                    withdraw();
                    break;
                case 3:
                    checkBalance();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    bankStatement();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private void deposit() {
        try {
            System.out.println();
            System.out.println("================== Deposit ==================");
            System.out.print("- Enter the amount to deposit: ");
            float amount = Float.parseFloat(scanner.nextLine());
            //busca a conta pelo usuario
            Float saldo = accountService.amountDeposit(account, amount);
            System.out.println();
            System.out.println("Deposit successful! New account balance: " + saldo);
            System.out.println("=============================================");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void withdraw() {
        try {
            System.out.println();
            System.out.println("================== Withdraw ==================");
            System.out.print("- Enter the amount to withdraw: ");
            float amount = scanner.nextFloat();
            Float saldo = accountService.amountWithdraw(account, amount);
            System.out.println();
            System.out.println("Withdraw successful! New account balance: " + saldo);
            System.out.println("==============================================");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void checkBalance() {
        System.out.println();
        System.out.println("====== Check Balance ========");
        System.out.println("Check Balance: " + account.getBalance());
        System.out.println("=============================");
    }

    private void transfer() {
        try {
            System.out.println();
            System.out.println("============================= Transfer =============================");
            System.out.print("- Enter the number account to be transferred: ");
            String destinyNumberAccount = scanner.next();
            System.out.print("- Enter the amount to be transferred: ");
            float amount = scanner.nextFloat();

            //teste
            System.out.print("Confirm operation? (Y/N): ");
            String confirm = scanner.next().toLowerCase();
            if (!confirm.equals("y")) {
                System.out.println("Operation cancelled.");
                return;
            }
            //teste

            Account result = accountService.transfer(account, destinyNumberAccount, amount);
            if (result == null) {
                return;
            }

            System.out.println("Transfer successful! New account balance: " + account.getBalance());
            System.out.println("=======================================================================");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void bankStatement() {
        System.out.println();
        System.out.print("============== Bank Statement ==============");
        System.out.println();
        List<Statement> statements = statementService.consultarPorConta(account.getId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Statement statement : statements) {
            System.out.println();
            System.out.println("Operation: " + statement.getAction());
            System.out.printf("Balance: R$ %.2f%n", statement.getBalance());
            System.out.println("Date: " + statement.getDateCreation().format(formatter));
            System.out.println("Message: " + statement.getMessage());
            System.out.println("=======================================");
        }
    }
}

