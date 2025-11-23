package org.example.components;

import java.time.LocalDateTime;

public class Lanzador {

    public static void main(String[] args) throws InterruptedException {
        Productor productor = new Productor();
        for (int i = 0; i < 10; i++) {
            productor.send("topicTest", "Mensaje generado a las " + LocalDateTime.now());
            Thread.sleep(100);
        }
        productor.close();
    }
}
