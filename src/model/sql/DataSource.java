package model.sql;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Ivan on 19.12.2015.
 */
public class DataSource {

    private final String URLSQL = "jdbc:mysql://localhost:3306/carshop";
    private final String URLDERBY = "jdbc:derby:C:/Temp/CarShopDb/carshop;";
    private final String USER = "root";
    private final String PASS = "root";

    private ComboPooledDataSource pooledDataSource;

    String dbType;

    public DataSource(String dbType) {

        this.dbType = dbType;

        switch (dbType) {
            case "mySQL":
                createMySqlDS();
                break;
            case "derby":
                createDerbyDS();
                break;
        }
    }

    private void createMySqlDS() {

        try {
            pooledDataSource = new ComboPooledDataSource();
            pooledDataSource.setDriverClass("com.mysql.jdbc.Driver");
            pooledDataSource.setJdbcUrl(URLSQL);
            pooledDataSource.setUser(USER);
            pooledDataSource.setPassword(PASS);

//            setDSParameters();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createDerbyDS() {

        pooledDataSource = new ComboPooledDataSource();

        try {
            pooledDataSource.setDriverClass("org.apache.derby.jdbc.EmbeddedDriver");
            pooledDataSource.setJdbcUrl(URLDERBY);

//            setDSParameters();

        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    private void setDSParameters() {
        pooledDataSource.setMinPoolSize(5);
        pooledDataSource.setAcquireIncrement(5);
        pooledDataSource.setMaxPoolSize(20);
        pooledDataSource.setMaxStatements(50);
    }

    public DataSource getInstance() {
        return this;
    }

    public Connection getConnection() throws SQLException {
        return this.pooledDataSource.getConnection();
    }


}
