package common.domain.command.commands;

import common.data.models.HumanBeingModel.HumanBeing;
import common.data.models.Result;
import common.domain.command.Command;

import java.util.Hashtable;

public class RemoveKeyCommand extends Command {
    @Override
    public Result execute(Hashtable<Integer, HumanBeing> collection, String[] args) {
        Integer key = Integer.parseInt(args[0]);
        boolean keyExists = collection.keySet().stream().anyMatch(s -> s.equals(key));

        if (keyExists) {
            collection.remove(key);
            return new Result(true, "", collection);
        } else {
            return new Result(false, "Элемент с таким ключом не существует", collection);
        }
    }
}
