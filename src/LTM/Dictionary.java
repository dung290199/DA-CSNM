package LTM;

import java.sql.*;
import java.util.ArrayList;

public class Dictionary {
    private Word word;
    private String name;
    private Connection connection;
    private ArrayList<String> arr;

    public Dictionary() throws SQLException {
        this.arr = new ArrayList<>();
        connect();
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/test";
        String username = "root";
        String password = "123456";

        connection = DriverManager.getConnection(url, username, password);
    }

    public String getData(Connection connection, String name, String word) throws SQLException {
        String sql = "SELECT * FROM `" + name + "` WHERE `word` = '" + word +"'";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);

        String meaning = "";
        if (result.next()){
             meaning = result.getString(3);
        }

        return meaning;
    }

    public ArrayList<String> getWords(Connection connection, String name, String string) throws SQLException {
        String sql = "SELECT * FROM `" + name + "` WHERE `word` LIKE '" + string +"%' LIMIT 10";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);

        arr.clear();
        while (result.next()){
            arr.add(result.getString(2));
        }
        return arr;
    }
}
