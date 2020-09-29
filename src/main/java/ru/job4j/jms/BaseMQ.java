package ru.job4j.jms;
/**
 * Interface BaseMQ - Обработчик очередей. Решение задач уровня Middle. Части 011. Multithreading.
 * 7. Проект - Pooh JMS[#283088]
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 29.09.2020
 * @version 1
 */
public interface BaseMQ {
    void send(String msg);
    String receive(String msg);
}
