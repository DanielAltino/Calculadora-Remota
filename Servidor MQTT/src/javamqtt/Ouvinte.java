package javamqtt;

import java.util.Arrays;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Ouvinte implements IMqttMessageListener {

    public Main MainResultado = new Main();
    private ClienteMQTT clienteMQTT;

    float resultado = 0;

    public Ouvinte(ClienteMQTT clienteMQTT, String topico, int qos) {
        clienteMQTT.subscribe(qos, this, topico);
        this.clienteMQTT = clienteMQTT;
    }

    @Override
    public void messageArrived(String topico, MqttMessage mm) throws Exception {

        String msg = new String(mm.getPayload());
        operacao(msg);
        System.out.println(msg);

//        String mensagem = String.valueOf(resultado);
//
//        clienteMQTT.publicar("resultado", mensagem.getBytes(), 0);

    }

    public void operacao(String mensagem) {

        String[] textoSeparado = mensagem.split(";");
        String num1 = textoSeparado[0];
        String num2 = textoSeparado[2];
        String op = textoSeparado[1];

        switch (op) {
            case "+":
                resultado = soma(Float.parseFloat(num1), Float.parseFloat(num2));
                break;
            case "-":
                resultado = subtracao(Float.parseFloat(num1), Float.parseFloat(num2));
                break;
            case "*":
                resultado = multiplicacao(Float.parseFloat(num1), Float.parseFloat(num2));
                break;
            case "/":
                resultado = divisao(Float.parseFloat(num1), Float.parseFloat(num2));
                break;
            default:
                break;
        }

        System.out.println("O resultado de " + num1 + " " + op + " " + num2 + ": " + resultado);
        System.out.println("");
        clienteMQTT.publicar("resultado", String.valueOf(resultado).getBytes(), 1, false);

    }

    public float soma(float n1, float n2) {
        return n1 + n2;
    }

    public float subtracao(float n1, float n2) {
        return n1 - n2;
    }

    public float multiplicacao(float n1, float n2) {
        return n1 * n2;
    }

    public float divisao(float n1, float n2) {
        return n1 / n2;
    }

}
