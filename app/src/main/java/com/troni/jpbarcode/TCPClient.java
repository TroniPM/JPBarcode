package com.troni.jpbarcode;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPClient {


    public static void sendData(final Activity context, Danfe nfe) {
        Log.v("TPC Client", "sendData");
        String sentence = null;
        String modifiedSentence = null;
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        Socket clientSocket = null;
        try {
            clientSocket = new Socket(MainActivity.db.getIp(), MainActivity.ipPort);
            Log.v("TPC Client", "Abriu socket");
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            sentence = nfe.convertToString();
            Log.v("TPC Client", "Enviará pro socket: "+sentence);
            outToServer.writeBytes(sentence + "\n");
            Log.v("TPC Client", "Escreveu no socket");
            outToServer.flush();
            Log.v("TPC Client", "Flush no socket");
            //modifiedSentence = inFromServer.readLine();
            //System.out.println(modifiedSentence);
            //Log.v("TPC Client", "Resultado: "+modifiedSentence);

            clientSocket.close();
            Log.v("TPC Client", "Fechou socket");

        } catch (IOException ex) {
            //System.out.println("Conexão fechada");
            /*context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "Conexão aparenta estar fechada.", Toast.LENGTH_SHORT).show();
                }
            });*/
        } catch (NullPointerException ex) {
            //System.out.println("Conexão fechada");
           /* context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "Erro ao tentar enviar.", Toast.LENGTH_SHORT).show();
                }
            });*/
        }


        /*try {
            //Para ler do servidor (resposta)
            modifiedSentence = inFromServer.readLine();
        } catch (IOException ex) {
            Toast.makeText(context, "Erro ao tentar enviar", Toast.LENGTH_SHORT).show();
            Log.e(TCPClient.class.getName(), "", ex);
        }*/















/*
        String sentence = "";
        String modifiedSentence = "";
		
        sentence = nfe.convertToString();
        try {
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

            Socket clientSocket = new Socket(MainActivity.db.getIp(), MainActivity.ipPort);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            //System.out.print("CLIENT: ");
            sentence = nfe.convertToString();
            outToServer.writeBytes(sentence + "\n");
            outToServer.flush();

            //Para ler do servidor (resposta)
            modifiedSentence = inFromServer.readLine();

            //Para imprimir resposta no cliente
            System.out.println(modifiedSentence);

            clientSocket.close();
			
			return true;
        } catch (IOException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		return false;*/

    }
}
