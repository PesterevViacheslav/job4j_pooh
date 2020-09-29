package ru.job4j.jms;
import org.json.JSONObject;
import java.util.concurrent.ConcurrentHashMap;
/**
 * Class QueueMQ - Обработчик topic. Решение задач уровня Middle. Части 011. Multithreading.
 * 7. Проект - Pooh JMS[#283088]
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 29.09.2020
 * @version 1
 */
public class TopicMQ implements BaseMQ {
        ConcurrentHashMap<String, Message> topic = new ConcurrentHashMap<>();

        @Override
        public void send(String msg) {
            JSONObject json = new JSONObject(msg.substring(msg.indexOf('{'), msg.indexOf('}') + 1));
            topic.put(json.get("topic").toString(), new Message(json.get("topic").toString(), json));
        }

        @Override
        public String receive(String msg) {
            String key = msg.substring(msg.lastIndexOf('/') + 1);
            String res = "";
            if (topic.containsKey(key)) {
                res = topic.get(key).getMessage().toString();
            }
            return res;
        }
}
