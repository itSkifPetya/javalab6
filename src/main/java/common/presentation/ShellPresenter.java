package common.presentation;

import common.presentation.presenterrequirements.Presenter;

import java.util.Scanner;

/**
 * Реализация Presenter для работы в консоли
 */
public class ShellPresenter implements Presenter {
    private static ShellPresenter instance;

    private ShellPresenter() {}

    /**
     * Реализация Singleton
     * @return
     */
    public static ShellPresenter getInstanse() {
        if(instance == null) {
            instance = new ShellPresenter();
        }
        return instance;
    }

    /**
     * Передать сообщение в вывод
     * @param msg сообщение
     */
    @Override
    public void put(String msg) {
        System.out.println(msg);
    }

    /**
     * Получить пользовательский ввод
     * @return пользовательский ввод
     */
    @Override
    public String get() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * Получить пользовательский ввод по сообщению
     * @param msg сообщение, отображаемое у пользователя
     * @return пользовательский ввод
     */
    @Override
    public String get(String msg) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(msg);
        return scanner.nextLine();
    }
}
