package client.domain;

import common.data.models.Query;
import common.domain.command.Command;
import common.domain.command.Invoker;

public class CommandHandler {
    private static CommandHandler instance;

    private CommandHandler() {}

    public static CommandHandler getInstance() {
        if (instance == null) {
            instance = new CommandHandler();
        }
        return instance;
    }

    public Query collectQuery(String commandName, String[] args) {
        Command command = handle(commandName, args);

        return new Query(command, args);
    }

    private Command handle(String commandName, String[] args) {
        Invoker invoker = Invoker.getInstance();
        Command command = null;
        try {
            command = invoker.getCommandMap().get(commandName);


        } catch (Exception e) {
            System.out.println("Такой команды нет. Для подробной информации используйте help");
        }
        if (command != null && args.length != command.getArgsCount()) {
            System.out.println("Некорректное количество аргументов");
            return null;
        }

        return command;
    }

//    private String[] argsBuilder() {
//
//    }


}
