package com.example.calcmqtt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    MqttAndroidClient client;

    static String HOST = "tcp://192.168.1.105:1883";
    String user = "";
    String senha = "";

    String topico = "calc";
    String topicoResultado = "resultado";
    String mensagem = "";

    TextView visor;
    TextView visorPrincipal;

    public String marcador = "";


    TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(this.getApplicationContext(), HOST,
                clientId);

        visor = findViewById(R.id.visor);
        visorPrincipal = findViewById(R.id.visorPrincipal);


        MqttConnectOptions options = new MqttConnectOptions();

//        options.setUserName(user);
//        options.setPassword(senha.toCharArray());


        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(MainActivity.this, "Conectado", Toast.LENGTH_SHORT).show();
                    setSub();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(MainActivity.this, "Falha na conexão", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                visorPrincipal.setText(new String(message.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

    }

    public void soma(String num1, String num2) {
        mensagem = num1 + ";+;" + num2;
        publicar();
    }

    public void sub(String num1, String num2) {
        mensagem = num1 + ";-;" + num2;
        publicar();
    }

    public void div(String num1, String num2) {
        mensagem = num1 + ";/;" + num2;
        publicar();
    }

    public void mult(String num1, String num2) {
        mensagem = num1 + ";*;" + num2;
        publicar();
    }


    public void publicar() {

        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = mensagem.getBytes();
            MqttMessage message = new MqttMessage(encodedPayload);
            client.publish(topico, message);
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    public void setSub() {
        try {
            client.subscribe(topicoResultado, 0);

        } catch (MqttException e) {
            e.printStackTrace();
        }

    }


    public void btnUm(View v) {
        visor.setText(visor.getText().toString() + "1");
    }

    public void btnDois(View v) {
        visor.setText(visor.getText().toString() + "2");
    }

    public void btnTres(View v) {
        visor.setText(visor.getText().toString() + "3");
    }

    public void btnQuatro(View v) {
        visor.setText(visor.getText().toString() + "4");
    }

    public void btnCinco(View v) {
        visor.setText(visor.getText().toString() + "5");
    }

    public void btnSeis(View v) {
        visor.setText(visor.getText().toString() + "6");
    }

    public void btnSete(View v) {
        visor.setText(visor.getText().toString() + "7");
    }

    public void btnOito(View v) {
        visor.setText(visor.getText().toString() + "8");
    }

    public void btnNove(View v) {
        visor.setText(visor.getText().toString() + "9");
    }

    public void btnZero(View v) {
        visor.setText(visor.getText().toString() + "0");
    }

    public void btnApagar(View v) {
        if (visor.length() > 0) {
            visor.setText(visor.getText().toString().substring(0, visor.length() - 1));
        }
            }

    public void btnZerar(View v) {
        visor.setText("");
        visorPrincipal.setText("");
    }

    public void btnMais(View v) {
        visor.setText(visor.getText().toString() + "+");
        marcador = "+";
    }

    public void btnMenos(View v) {
        visor.setText(visor.getText().toString() + "-");
        marcador = "-";
    }

    public void btnVezes(View v) {
        visor.setText(visor.getText().toString() + "*");
        marcador = "*";
    }

    public void btnDiv(View v) {
        visor.setText(visor.getText().toString() + "/");
        marcador = "/";
    }

    public void btnIgual(View v) {
        String conta = visor.getText().toString();

        String numeros[] = conta.split("[\\W]");
        String num1 = numeros[0];
        String num2 = numeros[1];

        switch (marcador) {
            case "+":
                soma(num1, num2);
                break;
            case "-":
                sub(num1, num2);
                break;
            case "*":
                mult(num1, num2);
                break;
            case "/":
                div(num1, num2);
                break;


        }
    }

}