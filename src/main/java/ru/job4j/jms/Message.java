package ru.job4j.jms;
import org.json.JSONObject;
/**
 * Class Message - Сообщение. Решение задач уровня Middle. Части 011. Multithreading.
 * 7. Проект - Pooh JMS[#283088]
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 26.09.2020
 * @version 1
 */
public class Message {
    private final String type;
    private final JSONObject message;
    /**
     * Method Message. Конструктор
     * @param type Тип сообщения
     * @param message Сообщение
     */
    public Message(String type, JSONObject message) {
        this.type = type;
        this.message = message;
    }

    /**
     * Method getMessage. Получение сообщения
     * @return JSON
     */
    public JSONObject getMessage() {
        return this.message;
    }
    @Override
    public String toString() {
        return "Message{" + "type='" + type + '\'' + ", message=" + message + '}';
    }
}
