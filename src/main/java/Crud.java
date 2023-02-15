
import java.sql.*;
import java.util.Scanner;

public class Crud {
    public static void main(String[] args) throws SQLException {
        Scanner input=new Scanner(System.in);
        boolean run=true;
        do {
            System.out.println("Choose one");
            System.out.println("1:Insert");
            System.out.println("2:Update");
            System.out.println("3:Select By Username");
            System.out.println("4:Select All");
            System.out.println("5:Delete");
            System.out.println("6:Exit");
            int num=input.nextInt();
            switch (num){
                case 1:
                    System.out.println("Enter username and password");
                    String username1=input.next();
                    String password1=input.next();
                    insert(username1,password1);
                    break;
                case 2:
                    System.out.println("Enter username,password and old_username");
                    String username=input.next();
                    String password=input.next();
                    String old_username=input.next();
                    update(username,password,old_username);
                    break;
                case 3:
                    System.out.println("Enter username");
                    String username3=input.next();
                    selectByUsername(username3);
                    break;
                case 4:
                    selectAll();
                    break;
                case 5:
                    System.out.println("Enter username");
                    String username2=input.next();
                    delete(username2);
                    break; case 6: run=false;break;
                default:
                    System.out.println("No option avaliable");

            }
        } while(run==true);
    }



    public static Connection connect(){
        String dbURL = "jdbc:mysql://localhost:3306/userdb";
        String username = "root";
        String password = "admin";
        Connection connection=null;
        try {
            connection = DriverManager.getConnection(dbURL, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    private static void delete(String username) throws SQLException {
        Connection connection=Crud.connect();
        String query="DELETE FROM users WHERE username=?";
        PreparedStatement preparedStatement= connection.prepareStatement(query);
        preparedStatement.setString(1,username);
        int rowsInserted= preparedStatement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A user was deleted successfully!");
        }
    }
    private static void selectAll() throws SQLException {
        Connection connection=Crud.connect();
        String query="SELECT * FROM users";
        Statement statement= connection.createStatement();
        ResultSet resultSet=statement.executeQuery(query);
        while (resultSet.next()){
            int userID=resultSet.getInt(1);
            String usernames=resultSet.getString(2);
            String passwords=resultSet.getString(3);
            System.out.println(userID +": "+usernames+" , "+passwords);
        }
    }
    private static void insert(String username,String password) throws SQLException {
        Connection connection=Crud.connect();
        String query="INSERT INTO users (username,password) VALUES (?,?)";
        PreparedStatement statement= connection.prepareStatement(query);
        statement.setString(1,username);
        statement.setString(2,password);
        int rowsInserted= statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new user was inserted successfully!");}
    }


    private static void selectByUsername(String username) throws SQLException {
        Connection connection=Crud.connect();
        String query="SELECT * FROM users WHERE username=?";
        PreparedStatement statement=connection.prepareStatement(query);
        statement.setString(1,username);
        ResultSet resultSet=statement.executeQuery();
        if (resultSet.next()){
            int userID=resultSet.getInt(1);
            String usernames=resultSet.getString(2);
            String passwords=resultSet.getString(3);
            System.out.println(userID +": "+usernames+" , "+passwords);
        }

    }

    private static void update(String username,String password,String old_username) throws SQLException {
        Connection connection=Crud.connect();
        String query="UPDATE users SET username=?,password=? WHERE username=?";
        PreparedStatement preparedStatement= connection.prepareStatement(query);
        preparedStatement.setString(1,username);
        preparedStatement.setString(2,password);
        preparedStatement.setString(3,old_username);
        int rowsInserted= preparedStatement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A user's information has been updated");
        }
    }

}

