package br.com.compass;

import br.com.compass.controllers.MainMenuController;

public class App {

    public static void main(String[] args) {

        MainMenuController mainMenuController = new MainMenuController();
        mainMenuController.run();
        System.out.println("Application closed.");
    }
}