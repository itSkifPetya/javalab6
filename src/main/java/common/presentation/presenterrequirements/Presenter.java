package common.presentation.presenterrequirements;

/**
 * Интерфейс трансфера информации между пользователем и программой
 */
public interface Presenter {
    void put(String msg);
    String get();
    String get(String msg);
}
