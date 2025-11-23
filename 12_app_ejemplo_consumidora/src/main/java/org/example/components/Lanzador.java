package org.example.components;

public class Lanzador {

    public static void main(String[] args) {
        Consumidor consumidor = new Consumidor();
        consumidor.suscribe("topicTest");
    }
}
