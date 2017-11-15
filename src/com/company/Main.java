package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
public class Main {
    public static void main(String[] ar) throws IOException {
        PrintWriter filecrate = new PrintWriter("c:\\txt.txt", "UTF-8"); //YOUR PATH
        FileWriter writer = new FileWriter("c:\\txt.txt", false); //YOUR PATH
        int port = 1060;
        try {
            ServerSocket ss = new ServerSocket(port);
            System.out.println("Ожидание клиента...");

            Socket socket = ss.accept();
            System.out.println("Клиент подключился!:)");
            System.out.println();


            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();


            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            String line = "";
            String line2;
            String strhash = ""; //str i+2
            String hash1;
            String hash2; //hash i+2
            String stringofhash = "";
            start:
            {
                LOOP: while (true) {
                    line = "";
                    line2 = "";
                    strhash = "";
                    hash1 = "";
                    hash2 = "";
                    stringofhash = "";
                    line = in.readUTF();
                    hash1 = in.readUTF();
                    hash2 = in.readUTF();
                    System.out.println("Клиент отправил строку : " + line);
                    System.out.println("Hash1_C : " + hash1);
                    System.out.println("Hash2_C : " + hash2);
                    char[] chars = line.toCharArray();
                    int i = 1;
                    while (i < chars.length) {
                        stringofhash += String.valueOf(chars[i]);
                        i = i + 2;
                    }
                    System.out.println("Строка через букву: " + stringofhash);
                    stringofhash = MD5Custom.md5Custom(stringofhash);
                    line2 = MD5Custom.md5Custom(line);
                    //line2="simbad";
                    out.writeUTF(line2);
                    out.writeUTF(stringofhash);
                    System.out.println("Hash1_S : " + line2);
                    System.out.println("Hash2_S : " + stringofhash);
                    boolean res1 = line2.equals(hash1);
                    boolean res2 = stringofhash.equals(hash2);
                    System.out.println("Проверяем хэш-суммы строки и строки через буквы...");
                    System.out.println("Полная хэш-сумма: " + res1);
                    System.out.println("Хэш-сумма i+2: " + res2);
                    if (res1 == true && res2 == true) {
                        System.out.println("Отправляем её обратно...");
                        //out.writeUTF(line);
                        writer.write(line + " : ");
                        writer.write(line2 + " : ");
                        writer.write(stringofhash + "\n");
                        writer.flush();
                        out.flush();
                        filecrate.close();
                        System.out.println("Ждём следующую строку...");
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
