package ru.job4j.jms;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;
/**
 * Class HttpServer. Решение задач уровня Middle. Части 011. Multithreading.
 * 7. Проект - Pooh JMS[#283088]
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 26.09.2020
 * @version 1
 */
public class HttpServer {
    ConcurrentHashMap<String, Message> queue = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, Message> topic = new ConcurrentHashMap<>();
    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        try (ServerSocket serverSocket = new ServerSocket(1111)) {
            System.out.println("Server started!");
            while (true) {
                Socket socket = serverSocket.accept();
                try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                     PrintWriter output = new PrintWriter(socket.getOutputStream())) {
                    String stringToParse = input.readLine();
                    System.out.println(stringToParse);
                    if (stringToParse.contains("POST /queue")) {
                        JSONObject json = new JSONObject(stringToParse.substring(stringToParse.indexOf('{'), stringToParse.indexOf('}') + 1));
                        server.queue.put(json.get("queue").toString(), new Message(json.get("queue").toString(), json));
                    }
                    if (stringToParse.contains("POST /topic")) {
                        JSONObject json = new JSONObject(stringToParse.substring(stringToParse.indexOf('{'), stringToParse.indexOf('}') + 1));
                        server.topic.put(json.get("topic").toString(), new Message(json.get("topic").toString(), json));
                    }
                    if (stringToParse.contains("GET /queue")) {
                        String key = stringToParse.substring(stringToParse.lastIndexOf('/') + 1);
                        if (server.queue.containsKey(key)) {
                            output.println(server.queue.get(key).getMessage());
                            server.queue.remove(key);
                        }
                    }
                    if (stringToParse.contains("GET /topic")) {
                        String key = stringToParse.substring(stringToParse.lastIndexOf('/') + 1);
                        if (server.topic.containsKey(key)) {
                            output.println(server.topic.get(key).getMessage());
                        }
                    }
                    output.flush();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}