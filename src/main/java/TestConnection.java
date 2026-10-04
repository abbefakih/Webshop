import database.DatabaseConnection;

import java.sql.Connection;


public class TestConnection {


    public static void main(String[] args) {


        try {


            Connection connection =
                    DatabaseConnection.getConnection();


            System.out.println(
                    "Database connected!"
            );


        } catch (Exception e) {


            e.printStackTrace();


        }


    }

}