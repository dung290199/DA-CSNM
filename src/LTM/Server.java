package LTM;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args){
        new Server();
    }

    ServerSocket server;
    DataInputStream in;
    Dictionary dictionary;
    ArrayList<String> arr;
    PrintStream out;

    public Server(){
        try {
            this.dictionary = new Dictionary();
            this.dictionary.setName("anh_viet");
            this.arr = new ArrayList<>();
            this.server = new ServerSocket(5000);
            Socket socket = server.accept();
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new PrintStream(socket.getOutputStream());

            while (true){
                String string = in.readUTF();

                if (string.equals("anh_viet") || string.equals("viet_anh")) {
                    this.dictionary.setName(string);
                } else if (string.startsWith("search")){
                    sendData(this.dictionary.getName(), string.substring(6));
                    System.out.println(string);
                } else {
                    sendWord(this.dictionary.getName(), string);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void sendWord(String name, String string) throws SQLException {
        arr.clear();
        arr = this.dictionary.getWords(this.dictionary.getConnection(), name, string);
        System.out.println("===================Send words===========================");
        if (arr.size() != 0) {
            String s = "";
            for (String item : arr) {
                s += item + "\n";
                System.out.println(item);
            }
            System.out.println("Send this string: " + string);
            this.out.println(s);
        } else {
            this.out.println(notFound(this.dictionary.getName()));
        }
    }

    private void sendData(String name, String string) throws SQLException, IOException {
        String meaning = this.dictionary.getData(this.dictionary.getConnection(), name, string) + "\n";
        if (!meaning.equals("")){
            this.out.println(meaning);
            System.out.println("Send this data: " + meaning);
        } else {
            this.out.println(notFound(this.dictionary.getName()) + "\n");
            System.out.println("Send this data not found");
        }
    }

    public String notFound(String dic){
        System.out.println("Not found!");
        if (dic.equals("anh_viet")){
            return "Not found";
        }
        return "Không tìm thấy";
    }
}
