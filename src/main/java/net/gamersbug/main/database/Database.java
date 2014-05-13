package net.gamersbug.main.database;

import java.sql.Connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.gamersbug.main.util.PluginUtil;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.logging.Level;

public class Database {

    private static boolean isConnected = false;

    private static Connection dbConnection;

    public static void initConnection() {

        if(!isConnected) {

            HikariConfig config = new HikariConfig(PluginUtil.getHikariConfig());

            config.setMaximumPoolSize(100);

            HikariDataSource dataSource = new HikariDataSource(config);

            try {

                dbConnection = dataSource.getConnection();

                isConnected = true;

            } catch (SQLException e) {

                e.printStackTrace();

            }

        }

    }

    public static Connection getConnection() {

        if(!isConnected()) {

            initConnection();

        }

        return dbConnection;

    }

    public static Boolean isConnected() {

        return isConnected;

    }

}