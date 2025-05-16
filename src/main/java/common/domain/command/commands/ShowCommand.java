package common.domain.command.commands;

import common.data.models.HumanBeingModel.HumanBeing;
import common.data.models.Result;
import common.domain.command.Command;

import java.util.Hashtable;

public class ShowCommand extends Command {
    @Override
    public Result execute(Hashtable<Integer, HumanBeing> collection, String[] args) {
        return new Result(true, "", collection);
    }
}
