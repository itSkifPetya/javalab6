package common.domain.command.commands;

import common.data.models.HumanBeingModel.HumanBeing;
import common.data.models.Result;
import common.domain.command.Command;
import common.domain.command.HistoryKeeper;

import java.util.Hashtable;
import java.util.Stack;

public class HistoryCommand extends Command {
    @Override
    public Result execute(Hashtable<Integer, HumanBeing> collection, String[] args) {
        String message = "";
        HistoryKeeper historyKeeper = HistoryKeeper.getInstance();
        Stack<String> history = historyKeeper.getHistory();

        if (history.isEmpty()) {
            return new Result(true, "История команд пуста.", collection);
        }
        int hisLen = Math.min(history.size(), 5);
        for (int i = 0; i < hisLen; i++) {
            message += "%s\n".formatted(history.get(history.size() - 1- i));
        }
        return new Result(true, message, collection);
    }
}
