package br.com.compass.controllers;

import br.com.compass.entities.Conta;
import br.com.compass.entities.Historico;
import br.com.compass.services.interfaces.ContaService;
import br.com.compass.services.interfaces.HistoricoService;

import java.util.List;
import java.util.Scanner;


public class BankMenuController {
    private final Scanner scanner;
    private final ContaService contaService;
    private final HistoricoService historicoService;
    private final Conta conta;

    public BankMenuController(ContaService contaService, Scanner scanner, HistoricoService historicoService, Conta conta) {
        this.contaService = contaService;
        this.scanner = scanner;
        this.historicoService = historicoService;
        this.conta = conta;
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
            float amount = scanner.nextFloat();
            //busca a conta pelo usuario
            Float saldo = contaService.amountDeposit(conta, amount);
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
            Float saldo = contaService.amountWithdraw(conta, amount);
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
        System.out.println("Check Balance: " + conta.getSaldo());
        System.out.println("=============================");
    }

    private void transfer() {
        try {
            System.out.println();
            System.out.println("============================= Transfer =============================");
            System.out.print("- Enter the number account to be transferred: ");
            String contaNumeroDestino = scanner.next();
            System.out.print("- Enter the amount to be transferred: ");
            float amount = scanner.nextFloat();

            Conta result = contaService.transfer(conta, contaNumeroDestino, amount);
            if (result == null) {
            }

            System.out.println("Transfer successful! New account balance: " + conta.getSaldo());
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
        List<Historico> extratos = historicoService.consultarPorConta(conta.getId());

        for (Historico historico : extratos) {
            System.out.println();
            System.out.println("=======================================");
            System.out.println("Operation: " + historico.getAcao());
            System.out.println("Balance: " + historico.getSaldo());
            System.out.println("Date: " + historico.getDataCriacao());

        }
    }
}

