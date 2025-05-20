package common.domain.command.commands;

import common.data.models.HumanBeingModel.HumanBeing;
import common.data.models.Response;
import common.domain.command.Command;

import java.util.Hashtable;
import java.util.Map;
import java.util.stream.Collectors;

public class RemoveGreaterKeyCommand extends Command {
    @Override
    public Response execute(Hashtable<Integer, HumanBeing> collection, String[] args) {
        Integer key = Integer.parseInt(args[0]);
        System.out.println(key);

//        Hashtable<Integer, HumanBeing> newCol = new Hashtable<>(collection); // Полная копия
//        newCol.keySet().removeIf(k -> k >= key); // Удаляем ненужные элементы
        Hashtable<Integer, HumanBeing> newCol = collection
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().getId() < key)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (existing, replacement) -> existing,  // Решение конфликтов
                        Hashtable::new  // Более короткая запись
                ));
//        for (HumanBeing hb : newCol.values()) {
//            System.out.println(hb.toPrettyString());
//        }
        return new Response(true, "Команда выполнена", newCol);
    }

    @Override
    public int getArgsCount() {
        return 1;
    }
}
