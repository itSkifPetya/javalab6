package common.domain.command.commands;

import common.data.models.HumanBeingModel.HumanBeing;
import common.data.models.Result;
import common.domain.command.Command;

import java.util.Comparator;
import java.util.Hashtable;

public class MinBySoundtrackNameCommand extends Command {
    @Override
    public Result execute(Hashtable<Integer, HumanBeing> collection, String[] args) {
        collection.entrySet().stream()
                .min(Comparator.comparingInt(entry -> entry.getValue().getSoundtrackName().length()));
        return new Result(true, "", collection);
    }
}
