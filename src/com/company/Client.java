package com.company;

import java.net.*;
import java.io.*;

public class Client {
    public static void main(String[] ar) {
        int serverPort = 1060;
        String address = "127.0.0.1";


        try {
            InetAddress ipAddress = InetAddress.getByName(address);
            System.out.println("IP: " + address + " Port: " + serverPort + "?");
            Socket socket = new Socket(ipAddress, serverPort);
            System.out.println("Подключено!");
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();


            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);


            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            String line = "";
            String line2;
            String strhash = ""; //str i+2
            String stringofhash;
            String hash1;
            String hash2; //hash i+2
            start:
            {

                LOOP: while (true) {
                    line = "";
                    line2 = "";
                    strhash = "";
                    hash1 = "";
                    hash2 = "";
                    stringofhash = "";
                    System.out.println("Введите строку и нажмите Enter");
                    System.out.println();
                    line = keyboard.readLine();
                    hash1 = MD5Custom.md5Custom(line);
                    char[] charh = line.toCharArray();
                    int i = 1;
                    while (i < charh.length) {
                        strhash += String.valueOf(charh[i]);
                        i = i + 2;
                    }
                    hash2 = String.valueOf(MD5Custom.md5Custom(strhash));
                    System.out.println("Отправляем строку на сервер...");
                    out.writeUTF(line);
                    //hash1="simbad";
                    out.writeUTF(hash1);
                    out.writeUTF(hash2);
                    out.flush();
                    line2 = in.readUTF();
                    stringofhash = in.readUTF();
                    System.out.println("Hash1_C : " + hash1);
                    System.out.println("Hash2_C : " + hash2);
                    System.out.println("Hash1_S : " + line2);
                    System.out.println("Hash2_S : " + stringofhash);
                    boolean res1 = hash1.equals(line2);
                    boolean res2 = hash2.equals(stringofhash);
                    System.out.println("Проверка MD5...");
                    System.out.println("Полная хэш-сумма : " + res1);
                    System.out.println("Хэш-сумма i+2 : " + res2);
                    if (res1 == true && res2 == true) {
                        System.out.println("Сервер вернул строку : " + line);
                        System.out.println("Сервер ожидает новую строку");
                        System.out.println();
                        continue LOOP;
                    } else {
                        System.out.println("Хэш-суммы не совпали. Вы пытаетесь меня обмануть?(");
                        continue LOOP;
                    }
                }
            }
        } catch (RuntimeException e) {
            System.err.println("catch RuntimeException");
        } catch (Exception x) { x.printStackTrace(); }
        catch (Throwable e) {
            System.err.println("catch Throwable");
        }
    }
}
