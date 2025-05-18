package common.domain.command;

import common.domain.command.commands.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс Invoker, в котором инициализируется коллекция доступных команд
 */
public class Invoker {
    private static Invoker instance;
    private Map<String, Command> commandMap = new HashMap<>();

    private Invoker() {}

    /**
     * Реализация Singleton
     * @return
     */
    public static Invoker getInstance() {
        if (instance == null) {
            instance = new Invoker();
        }
        return instance;
    }


    /**
     * Инициализация доступных команд и запись в коллекцию
     */
    public void invokerInit() {
        commandMap.put("help", new HelpCommand());
        commandMap.put("info", new InfoCommand());
        commandMap.put("show", new ShowCommand());
        commandMap.put("insert", new InsertCommand());
        commandMap.put("update", new UpdateCommand());
        commandMap.put("remove_key", new RemoveKeyCommand());
        commandMap.put("clear", new ClearCommand());
//        commandMap.put("save", new SaveCommand()); TODO: отсутствует в клиенте :)
        commandMap.put("execute_script", new ExecuteScriptCommand());
//        commandMap.put("exit", new ExitCommand());
        commandMap.put("history", new HistoryCommand());
        commandMap.put("replace_if_greater", new ReplaceIfGreaterCommand());
        commandMap.put("remove_greater_key", new RemoveGreaterKeyCommand());
        commandMap.put("sum_of_impact_speed", new SumOfImpactSpeedCommand());
        commandMap.put("min_by_soundtrack_name", new MinBySoundtrackNameCommand());
        commandMap.put("group_counting_by_has_toothpick", new GroupCountingByHasToothpickCommand());
//        commandMap.put("load_collection")/**/
    }

    /**
     * Вощвращает коллекцию инициализированных команд.
     * @return
     */
    public Map<String, Command> getCommandMap() {
        return commandMap;
    }
}
