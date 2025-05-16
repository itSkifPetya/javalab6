package common.domain.command.commands;

import common.data.models.HumanBeingModel.HumanBeing;
import common.data.models.Result;
import common.domain.command.Command;

import java.util.Hashtable;

public class HelpCommand extends Command {
    @Override
    public Result execute(Hashtable<Integer, HumanBeing> collection, String[] args) {
        String help = """
                help: вывести справку по доступным командам\n
                info: информация о коллекци\n
                show: вывести коллекцию\n
                insert {key}: добавить элемент по ключу\n
                update {id}: обновить элемент по его id\n
                remove_key {key}: удалить элемент из коллекции по его ключу\n
                clear: очистить коллекцию\n
                execute_script {file_path}: выполнить скрипт\n
                exit: завершить работу клиента\n
                history: вывести последние 5 команд\n
                replace_if_greater {key}: заменить элемент по ключу, если новый больше старого\n
                remove_greater_key {key}: удалить все элементы, ключ которых превышает данный\n
                sum_of_impact_speed: вывести сумму значений поля impactSpeed для всех элементов коллекции\n
                min_by_soundtrack_name: вывести любой элемент коллекции с минимальным полем soundtrackName\n
                group_counting_by_has_toothpick: сгруппировать элементы коллекции по значению поля hasToothpick, вывести количество элементов в каждой группе\n
                """;
        return new Result(true, help, collection);
    }
}
