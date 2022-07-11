package me.aq.plugin.ntirTP.Data.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLMain {
    private final String host = "localhost";
    private final String port = "3306";
    private final String database = "ntir_tpdata";
    private final String user = "NatsuServer";
    private final String password = "TaiWanIsVeryGood";


    private Connection connection;

    public boolean isConnected(){

        return (connection != null);

    }

    public  void connect() throws ClassNotFoundException, SQLException {
        if(!isConnected()) {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":"+ port + "/"
                    + database + "?useSSL=false&autoReconnect=true&failOverReadOnly=false", user, password);
        }
    }

    public  void disconnect(){
        if(isConnected())
            try {
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
    }
    public  Connection getConnection(){
        return connection;
    }
}
