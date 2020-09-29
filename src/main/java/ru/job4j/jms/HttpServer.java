package ru.job4j.jms;
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
    ConcurrentHashMap<String, Message> topic = new ConcurrentHashMap<>();
    private final BaseMQ mq;
    private final int port;
    /**
     * Method HttpServer. Конструктор
     * @param mq Тип обработчика
     * @param port Порт
     */
    public HttpServer(BaseMQ mq, int port) {
        this.mq = mq;
        this.port = port;
    }

    /**
     * Method run. Запуск сервера
     * @throws IOException
     */
    public void run() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            System.out.println("Server started!");
            while (true) {
                Socket socket = serverSocket.accept();
                try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                     PrintWriter output = new PrintWriter(socket.getOutputStream())) {
                    String stringToParse = input.readLine();
                    System.out.println(stringToParse);
                    if (stringToParse.contains("POST /")) {
                        mq.send(stringToParse);
                    }
                    if (stringToParse.contains("GET /")) {
                        output.println(mq.receive(stringToParse));
                    }
                    output.flush();
                }
            }
        }
    }
    public static void main(String[] args) throws IOException {
        HttpServer serverQ = new HttpServer(new QueueMQ(), 1111);
        serverQ.run();
    }
}