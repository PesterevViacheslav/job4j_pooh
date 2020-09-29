package ru.job4j.jms;
import org.json.JSONObject;
import java.util.concurrent.ConcurrentHashMap;
/**
 * Class QueueMQ - Обработчик queue. Решение задач уровня Middle. Части 011. Multithreading.
 * 7. Проект - Pooh JMS[#283088]
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 29.09.2020
 * @version 1
 */
public class QueueMQ implements BaseMQ {
    ConcurrentHashMap<String, Message> queue = new ConcurrentHashMap<>();

    @Override
    public void send(String msg) {
        JSONObject json = new JSONObject(msg.substring(msg.indexOf('{'), msg.indexOf('}') + 1));
        queue.put(json.get("queue").toString(), new Message(json.get("queue").toString(), json));
    }

    @Override
    public String receive(String msg) {
        String key = msg.substring(msg.lastIndexOf('/') + 1);
        String res = "";
        if (queue.containsKey(key)) {
            res = queue.get(key).getMessage().toString();
            queue.remove(key);
        }
        return res;
    }
}
