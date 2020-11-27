package LTM;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;

public class Server1 {
    public static void main(String[] args){
        new Server1();
    }

    DatagramSocket socket;
    DataInputStream in;
    Dictionary dictionary;
    ArrayList<String> arr;
    PrintStream out;

    public Server1(){
        try {
            this.dictionary = new Dictionary();
            this.dictionary.setName("anh_viet");
            this.arr = new ArrayList<>();
            this.socket = new DatagramSocket(5000);
//            Socket socket = server.accept();
//            this.in = new DataInputStream(socket.getInputStream());
//            this.out = new PrintStream(socket.getOutputStream());

            while (true){
                DatagramPacket recieve = new DatagramPacket(new byte[1000], 1000);
                socket.receive(recieve);
                String string = new String(recieve.getData()).substring(0, recieve.getLength());
                System.out.println(string);

                if (string.equals("anh_viet") || string.equals("viet_anh")) {
                    this.dictionary.setName(string);
                } else if (string.startsWith("search")){
                    sendData(this.dictionary.getName(), string.substring(6));
                } else {
                    sendWord(this.dictionary.getName(), string);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void sendWord(String name, String string) throws SQLException, IOException {
        arr.clear();
        arr = this.dictionary.getWords(this.dictionary.getConnection(), name, string);
        System.out.println("===================Send words======================");
        if (arr.size() != 0) {
            String s = "";
            for (String item : arr) {
                s += item + "\n";
                System.out.println(item);
            }
            sendToClient(s);
            System.out.println("Send this string: " + s);
//            this.out.println(s);
        } else {
            sendToClient(notFound(this.dictionary.getName()));
//            this.out.println(notFound(this.dictionary.getName()));
        }

    }

    private void sendData(String name, String string) throws SQLException, IOException {
        String meaning = this.dictionary.getData(this.dictionary.getConnection(), name, string) + "\n";
        System.out.println("===================Send data======================");

        if (!meaning.equals("")){
            sendToClient(meaning);
//            this.out.println(meaning);
            System.out.println("Send this data: " + meaning);
        } else {
            String result = notFound(this.dictionary.getName());
            sendToClient(result);
//            this.out.println(notFound(this.dictionary.getName()) + "\n");
            System.out.println("Send this data not found");
        }
    }

    public String notFound(String dic){
        System.out.println("Not found!");
        if (dic.equals("anh_viet")){
            return "Not found\n";
        }
        return "Không tìm thấy\n";
    }

    public void sendToClient(String string) throws IOException {
        DatagramPacket send = new DatagramPacket(string.getBytes(), string.length(), InetAddress.getByName("localhost"), 5000);
        socket.send(send);
    }
}
