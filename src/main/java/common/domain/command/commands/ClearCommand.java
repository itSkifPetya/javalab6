package common.domain.command.commands;

import common.data.models.HumanBeingModel.HumanBeing;
import common.data.models.Response;
import common.domain.command.Command;

import java.util.Hashtable;

public class ClearCommand extends Command {
    @Override
    public Response execute(Hashtable<Integer, HumanBeing> collection, String[] args) {
        return new Response(true, "Коллекция очищена!", new Hashtable<Integer, HumanBeing>());
    }
}
