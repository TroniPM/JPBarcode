/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.troni.jpbarcode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;

import java.util.Calendar;

/**
 * @author Morgan
 */
public class Danfe implements Serializable {

    private String numeracao = "", ip = "", key = "", data = "";

    public String getData() {
        return data;
    }

    public Danfe(String numeracao, String ip, String key) {
        this.numeracao = numeracao;
        this.ip = ip;
        this.key = key;

        this.data = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    public Danfe() {
    }

    @Override
    public String toString() {
        return "Danfe [numeracao=" + numeracao + ", ip=" + ip + ", key=" + key + "]";
    }

    public String convertToString() {
        try {

            String str;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            byte[] objeto = baos.toByteArray();
            str = Base64.encode(objeto);
            oos.close();
            return str;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Danfe convertFromString(String str) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decode(str));
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Danfe) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

    public String getNumeroDanfeFormatted() {
        char[] aux = getNumeracao().toCharArray();

        String str = "";
        int j = 0;

        for (int i = 0; i < aux.length; i++) {
            str = str + aux[i];
            if (j == 3) {
                j = -1;
                if (i != aux.length - 1) {
                    str += ".";
                }
            }
            j++;
        }

        return str;
    }

    public String getNumeracao() {
        return numeracao;
    }

    public void setNumeracao(String numeracao) {
        this.numeracao = numeracao;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
