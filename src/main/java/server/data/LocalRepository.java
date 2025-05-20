package server.data;

import common.data.models.HumanBeingModel.Car;
import common.data.models.HumanBeingModel.Coordinates;
import common.data.models.HumanBeingModel.HumanBeing;
import common.data.models.HumanBeingModel.WeaponType;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Scanner;

public class LocalRepository {
//    private static LocalRepository instance;
    private final String path;
    private final Hashtable<Integer, HumanBeing> zeroCollection = new Hashtable<>();
    private FileWriter fileWriter;
    private String fileName = "FileName";

    public LocalRepository(String path) {
        this.path = path;
    }

//    public static LocalRepository getInstance(String path) {
//         return new LocalRepository(path);
//    }

    public void fileWriterInit(String filepath) {
        try {
            fileWriter = new FileWriter(filepath);
            fileWriter.write("id,name,coordX,coordY,creationDate,realHero,hasToothpick,impactSpeed,soundtrackName,miutesOfWaiting,weaponType,car\n");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private ArrayList<String> humanBeingForm(Hashtable<Integer, HumanBeing> collection) {
        ArrayList<String> strCollection = new ArrayList<>();
//        String[] strCollection = new String[];
        collection.forEach((k, v) -> {
            strCollection.add(String.join(",", String.valueOf(k), v.getName(), String.valueOf(v.getCoordinates().getX()), String.valueOf(v.getCoordinates().getY()), v.getCreationDate().toString(), v.getRealHero().toString(), v.getHasToothpick().toString(), String.valueOf(v.getImpactSpeed()), v.getSoundtrackName(), String.valueOf(v.getMinutesOfWaiting()), v.getWeaponType().toString(), String.valueOf(v.getCar().getCool())));
        });
        return strCollection;
    }

    public void writeData(Hashtable<Integer, HumanBeing> collection) {
        again:
        try (BufferedWriter writer = new BufferedWriter(fileWriter)) {
//            System.out.println(collection);
            ArrayList<String> strCollection = humanBeingForm(collection);
            for (String x : strCollection) {
                writer.write(x + "\n");
            }
        } catch (NullPointerException | IOException e) {
//            System.out.println(e);
//            System.out.println(collection);
            fileWriterInit(fileName);
//            break again;
        }
    }

    public Hashtable<Integer, HumanBeing> getData() {
        zeroCollection.put(HumanBeing.zeroHumanBeing.getId(), HumanBeing.zeroHumanBeing);
        if (path == null) return null;
        Hashtable<Integer, HumanBeing> collection = new Hashtable<>();
        while (true) {
            try (Scanner sc = new Scanner(new FileReader(path))) {
                String line = "";
                boolean isHeader = true;
                while (sc.hasNextLine()) {
                    line = sc.nextLine().trim();
                    if (line.isEmpty() || isHeader) {
                        isHeader = false;
                        continue;
                    }

                    String[] fields = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                    if (fields.length != 12) { // Проверяем количество полей
                        System.err.println("Ошибка: неверное количество полей в строке: " + line);
                        return null;
                    }

                    try {
                        // Валидация и парсинг каждого поля
                        int id = validateAndParseInt(fields[0], "ID");
                        String name = validateString(fields[1], "Name");
                        int x = validateAndParseInt(fields[2], "Coordinate X");
                        double y = validateAndParseDouble(fields[3], "Coordinate Y");
                        LocalDate creationDate = validateAndParseDate(fields[4], "Creation Date");
                        boolean realHero = validateAndParseBoolean(fields[5], "Real Hero");
                        boolean hasToothpick = validateAndParseBoolean(fields[6], "Has Toothpick");
                        double impactSpeed = validateAndParseDouble(fields[7], "Impact Speed");
                        String soundtrackName = validateString(fields[8], "Soundtrack Name");
                        long minutesOfWaiting = validateAndParseLong(fields[9], "Minutes of Waiting");
                        WeaponType weaponType = validateAndParseWeaponType(fields[10], "Weapon Type");
                        Boolean carCool = validateNullableBoolean(fields[11], "Car");

                        // Создание объектов
                        Coordinates coordinates = new Coordinates(x, y);
                        Car car = (carCool != null) ? new Car(carCool) : null;

                        HumanBeing human = new HumanBeing(
                                name, coordinates, realHero, hasToothpick,
                                impactSpeed, soundtrackName, minutesOfWaiting,
                                weaponType, car
                        );
                        collection.put(human.getId(), human);

                    } catch (ValidationException e) {
                        System.err.println("Ошибка в строке: " + line + " | " + e.getMessage());
                        System.out.println("Загружаю пустую коллекцию");
                        return zeroCollection;
                    }
                }

                break;
            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден. Загружаю пустую коллекцию");
                break;
            }
        }
        return collection.isEmpty() ? zeroCollection : collection; // Возвращаем null если коллекция пуста
    }

    //--- Вспомогательные методы валидации ---
    private static int validateAndParseInt(String value, String fieldName) throws ValidationException {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new ValidationException(fieldName + " должно быть целым числом");
        }
    }

    private static double validateAndParseDouble(String value, String fieldName) throws ValidationException {
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            throw new ValidationException(fieldName + " должно быть числом с плавающей точкой");
        }
    }

    private static LocalDate validateAndParseDate(String value, String fieldName) throws ValidationException {
        try {
            return LocalDate.parse(value.trim());
        } catch (DateTimeParseException e) {
            throw new ValidationException(fieldName + " должно быть в формате YYYY-MM-DD");
        }
    }

    private static boolean validateAndParseBoolean(String value, String fieldName) throws ValidationException {
        String trimmed = value.trim();
        if (!trimmed.equalsIgnoreCase("true") && !trimmed.equalsIgnoreCase("false")) {
            throw new ValidationException(fieldName + " должно быть 'true' или 'false'");
        }
        return Boolean.parseBoolean(trimmed);
    }

    private static WeaponType validateAndParseWeaponType(String value, String fieldName) throws ValidationException {
        try {
            return WeaponType.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException(fieldName + " содержит недопустимое значение. Допустимые: " + Arrays.toString(WeaponType.values()));
        }
    }

    private static Boolean validateNullableBoolean(String value, String fieldName) throws ValidationException {
        String trimmed = value.trim();
        if (trimmed.equalsIgnoreCase("null")) return null;
        return validateAndParseBoolean(value, fieldName);
    }

    private static String validateString(String value, String fieldName) throws ValidationException {
        if (value.trim().isEmpty()) {
            throw new ValidationException(fieldName + " не может быть пустым");
        }
        return value.trim();
    }

    /**
     * Валидирует и парсит строку в long
     *
     * @param value     строка для парсинга
     * @param fieldName название поля для сообщения об ошибке
     * @return распарсенное значение
     * @throws ValidationException если значение невалидно
     */
    private static long validateAndParseLong(String value, String fieldName) throws ValidationException {
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException e) {
            throw new ValidationException(fieldName + " должно быть целым числом (long)");
        }
    }

    // Кастомное исключение для валидации
    private static class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }
    }

}
