package model.sql;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import model.ui.ControllerUI;
import model.ui.StartPageUI;

import javax.swing.*;
import java.io.*;
import java.net.ConnectException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Ivan on 19.12.2015.
 */
public class ShopInitializer {

    private final String URLSQL = "jdbc:mysql://localhost:3306/";
    private final String URLDERBY = "jdbc:derby:C:/Temp/CarShopDb/carshop;";
    private final String USER = "root";
    private final String PASS = "root";

    private Connection connection;

    public ShopInitializer(String dbType) throws Exception{

        switch (dbType) {
            case "mySQL":
                initMySqlShop();
                break;
            case "derby":
                initDerbyShop();
                break;
        }

    }

    private void initDerbyShop() {

        String derbyInitDbUrl = "initDb/derbyCreate.sql";

        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            connection = DriverManager.getConnection(URLDERBY + "create=true");
            if (!connection.isClosed()) {
                System.out.println("Connection with database established. Now initializing db...");
            }

            StringBuilder builder = convertSqlFileToQueries(derbyInitDbUrl);
            dbExecutor(builder);

            connection.close();

            if (connection.isClosed()) {
                System.out.println("Initializing complete. Starting application...");
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == 30000) {
                //OK
            } else {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initMySqlShop() throws Exception {

        String sqlInitDbUrl = "initDb/mySqlCreate.sql";
        String sqlInitTablesUrl = "initDb/mySqlInit.sql";

        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(URLSQL, USER, PASS);
        if (!connection.isClosed()) {
            System.out.println("Connection with database established. Now initializing db...");
        }

        StringBuilder builder = convertSqlFileToQueries(sqlInitDbUrl);
        dbExecutor(builder);

        connection.close();
        connection = DriverManager.getConnection(URLSQL + "carshop", USER, PASS);

        System.out.println("Initializing tables...");
        builder = convertSqlFileToQueries(sqlInitTablesUrl);
        dbExecutor(builder);

        connection.close();

        if (connection.isClosed()) {
            System.out.println("Initializing complete. Starting application...");
        }
    }

    private StringBuilder convertSqlFileToQueries(String fileName) throws IOException {

        StringBuilder builder = new StringBuilder();

        InputStream input = ShopInitializer.class.getClassLoader().getResourceAsStream(fileName);
        InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(reader);

        String str;
        while ((str = bufferedReader.readLine()) != null) {
            builder.append(str);
        }

        input.close();
        reader.close();
        bufferedReader.close();

        return builder;
    }

    private void dbExecutor(StringBuilder builder) throws SQLException {

        String[] query = builder.toString().split(";");

        for (int i = 0; i < query.length; i++) {

            PreparedStatement statement = connection.prepareStatement(query[i]);
            statement.execute();
            statement.close();
        }
    }
}
