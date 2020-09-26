package ru.job4j.jms;
import org.json.JSONObject;
import java.io.*;
/**
 * Class RunClients - Эмуляция нагрузки на сервер. Решение задач уровня Middle. Части 011. Multithreading.
 * 7. Проект - Pooh JMS[#283088]
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 26.09.2020
 * @version 1
 */
public class RunClients {
    public static void main(String[] args) throws IOException, InterruptedException {
        JSONObject messageQueue = new JSONObject();
        messageQueue.put("queue", "weather");
        messageQueue.put("text", "temperature +18 C");
        Thread producer1 = new Thread(new Producer(messageQueue, "POST /queue"));
        producer1.start();
        JSONObject messageTopic = new JSONObject();
        messageTopic.put("topic", "weather");
        messageTopic.put("text", "temperature +18 C");
        Thread producer2 = new Thread(new Producer(messageTopic, "POST /topic"));
        producer2.start();
        Thread.sleep(1000);
        Thread consumer1 = new Thread(new Consumer("GET /queue/weather"));
        Thread consumer2 = new Thread(new Consumer("GET /queue/weather"));
        Thread consumer3 = new Thread(new Consumer("GET /topic/weather"));
        Thread consumer4 = new Thread(new Consumer("GET /topic/weather"));
        consumer1.start();
        consumer2.start();
        consumer3.start();
        consumer4.start();
        consumer1.join();
        consumer2.join();
        consumer3.join();
        consumer4.join();
    }
}