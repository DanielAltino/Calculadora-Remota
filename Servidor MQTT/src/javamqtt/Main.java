package javamqtt;

import java.text.SimpleDateFormat;

public class Main {

    static ClienteMQTT clienteMQTT;
    
    public static void main(String[] args) throws InterruptedException {
        clienteMQTT = new ClienteMQTT("tcp://192.168.1.105:1883", null, null);
        clienteMQTT.iniciar();

        Ouvinte ouvinte = new Ouvinte(clienteMQTT, "calc", 0);

//        while (true) {
//            Thread.sleep(1000);
//            String mensagem = "10;-;20";
//
//            clienteMQTT.publicar("", mensagem.getBytes(), 0);
//        }

    }
    
//    public void publicar(String topic, byte[] payload, int qos, boolean retained){
//        clienteMQTT.publicar(topic, payload, qos, retained); 
//    };

}
