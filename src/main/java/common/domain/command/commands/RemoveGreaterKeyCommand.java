package common.domain.command.commands;

import common.data.models.HumanBeingModel.HumanBeing;
import common.data.models.Result;
import common.domain.command.Command;

import java.util.Hashtable;
import java.util.Map;
import java.util.stream.Collectors;

public class RemoveGreaterKeyCommand extends Command {
    @Override
    public Result execute(Hashtable<Integer, HumanBeing> collection, String[] args) {
        Integer key = Integer.parseInt(args[0]);
        Hashtable<Integer, HumanBeing> newCol = collection
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey() <= key)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, Hashtable<Integer, HumanBeing>::new));
        return new Result(true, "", collection);
    }
}
