package common.data.models.HumanBeingModel;

import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Модель класса HumanBeing. С этим типом данных осуществляется работа в коллекции
 */
public class HumanBeing implements Comparable<HumanBeing> {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Boolean realHero; //Поле не может быть null
    private Boolean hasToothpick; //Поле не может быть null
    private double impactSpeed;
    private String soundtrackName; //Поле не может быть null
    private long minutesOfWaiting;
    private WeaponType weaponType; //Поле не может быть null
    private Car car; //Поле может быть null

    private HumanBeing(Integer id, String name, Coordinates coordinates, LocalDate creationDate, Boolean realHero, Boolean hasToothpick, double impactSpeed, String soundtrackName, long minutesOfWaiting, WeaponType weaponType, Car car) throws NullPointerException {
        if (name == null) throw new NullPointerException("Поле name не может быть пустым!");
        if (coordinates == null) throw new NullPointerException("Поле coordinates не может быть пустым!");
        if (realHero == null) throw new NullPointerException("Поле realHero не может быть пустым!");
        if (hasToothpick == null) throw new NullPointerException("Поле hasToothpick не может быть пустым!");
        if (soundtrackName == null) throw new NullPointerException("Поле soundtrackName не может быть пустым!");
        if (weaponType == null) throw new NullPointerException("Поле weaponType не может быть пустым!");
        if (car == null) throw new NullPointerException("Поле car не может быть пустым!");
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;
        this.impactSpeed = impactSpeed;
        this.soundtrackName = soundtrackName;
        this.minutesOfWaiting = minutesOfWaiting;
        this.weaponType = weaponType;
        this.car = car;
    }


    public HumanBeing(String name, Coordinates coordinates, Boolean realHero, Boolean hasToothpick, double impactSpeed, String soundtrackName, long minutesOfWaiting, WeaponType weaponType, Car car) throws NullPointerException {
        UUID uuid = UUID.randomUUID();
        this.id = ByteBuffer.wrap(uuid.toString().getBytes()).getInt();
        if (name == null) throw new NullPointerException("Поле name не может быть пустым!");
        if (coordinates == null) throw new NullPointerException("Поле coordinates не может быть пустым!");
        if (realHero == null) throw new NullPointerException("Поле realHero не может быть пустым!");
        if (hasToothpick == null) throw new NullPointerException("Поле hasToothpick не может быть пустым!");
        if (soundtrackName == null) throw new NullPointerException("Поле soundtrackName не может быть пустым!");
        if (weaponType == null) throw new NullPointerException("Поле weaponType не может быть пустым!");
        if (car == null) throw new NullPointerException("Поле car не может быть пустым!");
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;
        this.impactSpeed = impactSpeed;
        this.soundtrackName = soundtrackName;
        this.minutesOfWaiting = minutesOfWaiting;
        this.weaponType = weaponType;
        this.car = car;
    }

//    public static HumanBeing parseHumanBeing(Integer id, String name, Coordinates coordinates, LocalDate creationDate, Boolean realHero, Boolean hasToothpick, double impactSpeed, String soundtrackName, long minutesOfWaiting, WeaponType weaponType, Car car) {
//        return new HumanBeing(id, name, coordinates, creationDate, realHero, hasToothpick, impactSpeed, soundtrackName, minutesOfWaiting, weaponType, car);
//    }

    /**
     * Возвращает новый HumanBeing. Используется при замещении в коллекции по ID
     * @param id
     * @param name
     * @param coordinates
     * @param realHero
     * @param hasToothpick
     * @param impactSpeed
     * @param soundtrackName
     * @param minutesOfWaiting
     * @param weaponType
     * @param car
     * @return
     */
    public static HumanBeing insertHumanBeing(Integer id, String name, Coordinates coordinates, Boolean realHero, Boolean hasToothpick, double impactSpeed, String soundtrackName, long minutesOfWaiting, WeaponType weaponType, Car car) {
        return new HumanBeing(id, name, coordinates, LocalDate.now(), realHero, hasToothpick, impactSpeed, soundtrackName, minutesOfWaiting, weaponType, car);
    }

    @Override
    public int compareTo(HumanBeing other) {
        if (this.coordinates.getRadiusVector() > other.coordinates.getRadiusVector()) {
            return 1;
        } else {
            return -1;
        }
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(int x, double y) {
        this.coordinates = new Coordinates(x, y);
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setRealHero(Boolean realHero) {
        this.realHero = realHero;
    }

    public void setHasToothpick(Boolean hasToothpick) {
        this.hasToothpick = hasToothpick;
    }

    public void setImpactSpeed(double impactSpeed) {
        this.impactSpeed = impactSpeed;
    }

    public void setSoundtrackName(String soundtrackName) {
        this.soundtrackName = soundtrackName;
    }

    public void setMinutesOfWaiting(long minutesOfWaiting) {
        this.minutesOfWaiting = minutesOfWaiting;
    }

    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public String toString() {
        return "HumanBeing{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", realHero=" + realHero +
                ", hasToothpick=" + hasToothpick +
                ", impactSpeed=" + impactSpeed +
                ", soundtrackName='" + soundtrackName + '\'' +
                ", minutesOfWaiting=" + minutesOfWaiting +
                ", weaponType=" + weaponType +
                ", car=" + car +
                '}';
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Boolean getRealHero() {
        return realHero;
    }

    public Boolean getHasToothpick() {
        return hasToothpick;
    }

    public double getImpactSpeed() {
        return impactSpeed;
    }

    public String getSoundtrackName() {
        return soundtrackName;
    }

    public long getMinutesOfWaiting() {
        return minutesOfWaiting;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public Car getCar() {
        return car;
    }
}