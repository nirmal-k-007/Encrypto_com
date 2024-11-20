package com.chat.ChatApp.Repositary;

import com.chat.ChatApp.Models.UpdateData;
import com.chat.ChatApp.Models.UserData;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.*;

@Repository
public class JDBCRepositary {

    static String url = "jdbc:mysql://localhost:3306/ChatApp";
    static String user = "root";
    static String password = "NirmalK007#";


        public static boolean availability(String email,String tableName,String emailColumn){

            String query = "SELECT username FROM " + tableName + " WHERE " + emailColumn + " = ?";

            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                // Set the parameters in the query
                stmt.setString(1, email);

                // Execute the query
                ResultSet rs = stmt.executeQuery();

                // If data is found, return the corresponding username
                if (rs.next()) {
                    return true;
                } else {
                    return false;  // Return null if no match is found
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;  // Return null if there is an exception
            }

        }


        public static String checkCredentials(String tableName, String emailColumn, String passwordColumn, String usernameColumn, String email, String pwd) {

            String query = "SELECT " + usernameColumn + " FROM " + tableName + " WHERE " + emailColumn + " = ? AND " + passwordColumn + " = ?";

            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                // Set the parameters in the query
                stmt.setString(1, email);
                stmt.setString(2, pwd);

                // Execute the query
                ResultSet rs = stmt.executeQuery();

                // If data is found, return the corresponding username
                if (rs.next()) {
                    return rs.getString(usernameColumn);
                } else {
                    return null;  // Return null if no match is found
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;  // Return null if there is an exception
            }
        }



    public int registerUserRepo(UserData data) {
        int rowsAffected = 0;

        String insertQuery = "INSERT INTO Users (username, email, password,name,public_key) VALUES (?, ?, ?, ?,?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            // Set the values for the query parameters
            preparedStatement.setString(1, data.getUname());    // Set username
            preparedStatement.setString(2, data.getEmail()); // Set email
            preparedStatement.setString(3, data.getPwd()); // Set password
            preparedStatement.setString(4, data.getName()); // Set password
            preparedStatement.setString(5, data.getPublic_key()); // Public_Key
//            preparedStatement.setString(5, "FALSE"); // Public_Key



            // Execute the query
            rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Updated : " + Integer.toString(rowsAffected));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Output the result
        return rowsAffected;
    }

    public String loginUserRepositary(UserData data) {

        boolean check = availability(data.getEmail(),"Users","email");

        if(check){
            String res = checkCredentials("Users","email","password", "username", data.getEmail(), data.getPwd());
            if(res!=null){
                return res;
            }
            else
            {
                return "Wrong Password Entered";
            }
        }
        else {
            return "User Not Registered\nKindly Sign Up";
        }
    }


    public int updateUserCredentialsRepo(UpdateData data) {
            int rowsUpdated = 0;
            String updateQuery = "UPDATE Users SET public_key = ? WHERE email = ? AND password = ?";

            try {
                // Get the database connection
                Connection connection = DriverManager.getConnection(url,user,password); // Adjust according to your connection setup

                // Prepare the statement
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.setString(1, data.getPublic_key()); // Set public key
                preparedStatement.setString(2, data.getEmail());     // Set email
                preparedStatement.setString(3, data.getPwd());  // Set password

                // Execute update
                rowsUpdated = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        System.out.println(rowsUpdated);
            return rowsUpdated;
        }

    }
