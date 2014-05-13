package net.gamersbug.main.model;

import net.gamersbug.main.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServerModel {

    public static boolean serverExists(String category, Integer number) {

        Boolean serverExists = false;

        try {

            Connection dbConnection = Database.getConnection();

            PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT COUNT(id) AS rows FROM `gamersbug_server` WHERE `category` = ? && `number` = ? LIMIT 1");

            preparedStatement.setString(1, category);
            preparedStatement.setInt(2, number);

            preparedStatement.executeQuery();

            ResultSet resultSet = preparedStatement.getResultSet();

            if(resultSet.next()) {

                serverExists = (resultSet.getInt("rows") >= 1);

            }

            resultSet.close();
            preparedStatement.close();

        }catch(SQLException e) {

            e.printStackTrace();

        }

        return serverExists;

    }

    public static void createServer(String category, Integer number, String map, String game, Integer onlineState, Integer players, Integer playing) {

        try {

            Connection dbConnection = Database.getConnection();

            String createServerQuery = "INSERT INTO `gamersbug_server` (`category`, `number`, `map`, `game`, `online_state`, `players`, `playing`) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = dbConnection.prepareStatement(createServerQuery);

            preparedStatement.setString(1, category);
            preparedStatement.setInt(2, number);
            preparedStatement.setString(3, map);
            preparedStatement.setString(4, game);
            preparedStatement.setInt(5, onlineState);
            preparedStatement.setInt(6, players);
            preparedStatement.setInt(7, playing);

            preparedStatement.executeUpdate();

            preparedStatement.close();

        }catch(SQLException e) {

            e.printStackTrace();

        }

    }

    public static void updateAll(String category, Integer number, String map, String game, Integer onlineState, Integer players, Integer playing) {

        try {

            Connection dbConnection = Database.getConnection();

            String createServerQuery = "UPDATE `gamersbug_server` SET `map` = ?, `game` = ?, `online_state` = ?, `players` = ?, `playing` = ? WHERE `category` = ? && `number` = ?";

            PreparedStatement preparedStatement = dbConnection.prepareStatement(createServerQuery);

            preparedStatement.setString(1, map);
            preparedStatement.setString(2, game);
            preparedStatement.setInt(3, onlineState);
            preparedStatement.setInt(4, players);
            preparedStatement.setInt(5, playing);
            preparedStatement.setString(6, category);
            preparedStatement.setInt(7, number);

            preparedStatement.executeUpdate();

            preparedStatement.close();

        }catch(SQLException e) {

            e.printStackTrace();

        }

    }

    public static void updateMapAndGame(String category, Integer number, String map, String game) {

        try {

            Connection dbConnection = Database.getConnection();

            String createServerQuery = "UPDATE `gamersbug_server` SET `map` = ?, `game` = ? WHERE `category` = ? && `number` = ?";

            PreparedStatement preparedStatement = dbConnection.prepareStatement(createServerQuery);

            preparedStatement.setString(1, map);
            preparedStatement.setString(2, game);
            preparedStatement.setString(3, category);
            preparedStatement.setInt(4, number);

            preparedStatement.executeUpdate();

            preparedStatement.close();

        }catch(SQLException e) {

            e.printStackTrace();

        }

    }

    public static void updateOnlineState(String category, Integer number, Integer onlineState) {

        try {

            Connection dbConnection = Database.getConnection();

            String createServerQuery = "UPDATE `gamersbug_server` SET `online_state` = ? WHERE `category` = ? && `number` = ?";

            PreparedStatement preparedStatement = dbConnection.prepareStatement(createServerQuery);

            preparedStatement.setInt(1, onlineState);
            preparedStatement.setString(2, category);
            preparedStatement.setInt(3, number);

            preparedStatement.executeUpdate();

            preparedStatement.close();

        }catch(SQLException e) {

            e.printStackTrace();

        }

    }

    public static void updatePlayers(String category, Integer number, Integer players) {

        try {

            Connection dbConnection = Database.getConnection();

            String createServerQuery = "UPDATE `gamersbug_server` SET `players` = ? WHERE `category` = ? && `number` = ?";

            PreparedStatement preparedStatement = dbConnection.prepareStatement(createServerQuery);

            preparedStatement.setInt(1, players);
            preparedStatement.setString(2, category);
            preparedStatement.setInt(3, number);

            preparedStatement.executeUpdate();

            preparedStatement.close();

        }catch(SQLException e) {

            e.printStackTrace();

        }

    }

    public static void updatePlayingState(String category, Integer number, Integer playing) {

        try {

            Connection dbConnection = Database.getConnection();

            String createServerQuery = "UPDATE `gamersbug_server` SET `playing` = ? WHERE `category` = ? && `number` = ?";

            PreparedStatement preparedStatement = dbConnection.prepareStatement(createServerQuery);

            preparedStatement.setInt(1, playing);
            preparedStatement.setString(2, category);
            preparedStatement.setInt(3, number);

            preparedStatement.executeUpdate();

            preparedStatement.close();

        }catch(SQLException e) {

            e.printStackTrace();

        }

    }

}
