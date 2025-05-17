package common.domain.command.commands;

import common.data.models.HumanBeingModel.HumanBeing;
import common.data.models.Result;
import common.domain.command.Command;
import common.domain.command.Invoker;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ExecuteScriptCommand extends Command {
    @Override
    public Result execute(Hashtable<Integer, HumanBeing> collection, String[] args) {
        String path = args[0];
        Invoker invoker = Invoker.getInstance();
        Map<String, Command> commandMap = invoker.getCommandMap();
        String message = "";
        Result result = null;

        try (Scanner sc = new Scanner(new FileReader(path))) {
            String inp;
            while (sc.hasNextLine()) {
                inp = sc.nextLine();
                List<String> inpArray = List.of(inp.split(" "));
                Command command = commandMap.get(inpArray.getFirst());

                if (command == null) {
                    message += "Неизвестная команда: %s\n".formatted(inpArray.getFirst());
                    continue;
                }
                if (command.getArgsCount() != inpArray.size()) {
                    message += "Команда %s не имеет аргументов или их количество некорректно\n".formatted(inpArray.getFirst());
                    continue;
                }
                result = command.execute(collection, new String[]{inpArray.removeFirst()});
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (result == null) result = new Result(false, "", collection);

        return new Result(true, message, result.getData());
    }
}
