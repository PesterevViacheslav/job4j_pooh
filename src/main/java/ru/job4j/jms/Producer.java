package ru.job4j.jms;
import org.json.JSONObject;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
/**
 * Class Producer - Отправитель. Решение задач уровня Middle. Части 011. Multithreading.
 * 7. Проект - Pooh JMS[#283088]
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 26.09.2020
 * @version 1
 */
public class Producer implements Runnable {
    private final JSONObject message;
    private final String request;
    /**
     * Method Producer. Конструктор
     * @param message Сообщение
     * @param request Запрос
     */
    public Producer(JSONObject message, String request) {
        this.message = message;
        this.request = request;
    }
    @Override
    public void run() {
        try (final Socket s = new Socket(InetAddress.getByName("127.0.0.1"), 1111)) {
            try (OutputStreamWriter out = new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8)) {
                out.write(this.request);
                out.write(this.message.toString());
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}