package common.domain.command.commands;

import common.data.models.HumanBeingModel.Car;
import common.data.models.HumanBeingModel.Coordinates;
import common.data.models.HumanBeingModel.HumanBeing;
import common.data.models.HumanBeingModel.WeaponType;
import common.data.models.Response;
import common.domain.command.Command;
import common.domain.command.DataCollector;

import java.util.Hashtable;

public class InsertCommand extends Command implements DataCollector {
    @Override
    public Response execute(Hashtable<Integer, HumanBeing> collection, String[] args) {
        Integer key = Integer.parseInt(args[0]);
        boolean keyExists = collection.keySet().stream().anyMatch(k -> k.equals(key));
        if (keyExists) return new Response(false, "Такой ключ уже используется. Используйте update (подробнее - help).", collection);

        String name = args[1];
        int coordX = Integer.parseInt(args[2]);
        double coordY = Double.parseDouble(args[3]);
        Coordinates coordinates = new Coordinates(coordX, coordY);
        Boolean realHero = Boolean.parseBoolean(args[4]);
        Boolean hasToothpick = Boolean.parseBoolean(args[5]);
        double impactSpeed = Double.parseDouble(args[6]);
        String soundtrackName = args[7];
        long minutesOfWaiting = Long.parseLong(args[8]);
        WeaponType weaponType = WeaponType.valueOf(args[9]);
        Car car = new Car(Boolean.parseBoolean(args[10]));

        HumanBeing humanBeing = HumanBeing.insertHumanBeing(key, name, coordinates, realHero, hasToothpick, impactSpeed, soundtrackName, minutesOfWaiting, weaponType, car);

        collection.put(key, humanBeing);

        return new Response(true, "Объект добавлен", collection);
    }

    @Override
    public int getArgsCount() {
        return 1;
    }
}
