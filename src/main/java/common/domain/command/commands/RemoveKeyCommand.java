package common.domain.command.commands;

import common.data.models.HumanBeingModel.HumanBeing;
import common.data.models.Response;
import common.domain.command.Command;

import java.util.Hashtable;

public class RemoveKeyCommand extends Command {
    @Override
    public Response execute(Hashtable<Integer, HumanBeing> collection, String[] args) {
        Integer key = Integer.parseInt(args[0]);
        boolean keyExists = collection.keySet().stream().anyMatch(s -> s.equals(key));

        if (keyExists) {
            collection.remove(key);
            return new Response(true, "Объект с ID: %s удалён.\n".formatted(key), collection);
        } else {
            return new Response(false, "Элемент с таким ключом не существует\n", collection);
        }
    }

    @Override
    public int getArgsCount() {
        return 1;
    }
}
