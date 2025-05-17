package common.domain.command.commands;

import common.data.models.HumanBeingModel.HumanBeing;
import common.data.models.Result;
import common.domain.command.Command;

import java.util.Hashtable;

public class InfoCommand extends Command {
    @Override
    public Result execute(Hashtable<Integer, HumanBeing> collection, String[] args) {
        String info = """
                Количество элементов: %s\nТип коллекции: %s
                """.formatted(collection.size(), collection.getClass().getName());
        return new Result(true, info, collection);
    }

}
