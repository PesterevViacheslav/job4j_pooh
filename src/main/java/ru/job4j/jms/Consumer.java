package ru.job4j.jms;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
/**
 * Class Consumer - Получатель. Решение задач уровня Middle. Части 011. Multithreading.
 * 7. Проект - Pooh JMS[#283088]
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 26.09.2020
 * @version 1
 */
public class Consumer implements Runnable {
    private final String request;
    public Consumer(String request) {
        this.request = request;
    }
    @Override
    public void run() {
        try (final Socket s = new Socket(InetAddress.getByName("127.0.0.1"), 1111)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            out.println(this.request);
            out.flush();
            String str = reader.readLine();
            System.out.println(str);
            out.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}