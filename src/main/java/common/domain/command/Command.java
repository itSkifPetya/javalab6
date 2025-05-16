package common.domain.command;

import common.data.models.HumanBeingModel.HumanBeing;
import common.data.models.Result;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Интерфейс Команда. Часть реализации паттерна "Command"
 */
public abstract class Command implements Serializable {
    /**
     * Метод, запускающий выполнение команды, имплементирующей интерфейс
     * @param collection коллекция, с которой работает команда. Тип - Hashtable<Integer, HumanBeing>
     * @param args строковый массив, содержащий аргументы команды, если есть. В ином случае - пустой массив
     */
    public abstract Result execute(Hashtable<Integer, HumanBeing> collection, String[] args);

    /**
     * Возвращает количество аргументов команды. При помощи данного метода происходит проверка наличия
     * аргументов у команды при распознавании
     * @return
     */
    int getArgsCount() {
        return 0;
    }
}

