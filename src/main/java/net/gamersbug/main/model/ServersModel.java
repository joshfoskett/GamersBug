package net.gamersbug.main.model;

import net.gamersbug.main.database.Database;
import net.gamersbug.main.object.ServerObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServersModel {

    public static List<ServerObject> getServers()  {

        List<ServerObject> serverList = new ArrayList<>();

        try {

            Connection dbConnection = Database.getConnection();

            String getServersQuery = "SELECT * FROM `gamersbug_server` WHERE 1";

            PreparedStatement preparedStatement = dbConnection.prepareStatement(getServersQuery);

            preparedStatement.executeQuery();

            ResultSet resultSet = preparedStatement.getResultSet();

            while(resultSet.next()) {

                Integer id          = resultSet.getInt("id");
                String  category    = resultSet.getString("category");
                Integer number      = resultSet.getInt("number");
                String  map         = resultSet.getString("map");
                String  game        = resultSet.getString("game");
                Integer onlineState = resultSet.getInt("online_state");
                Integer players     = resultSet.getInt("players");
                Integer playing     = resultSet.getInt("playing");

                serverList.add(new ServerObject(id, category, number, map, game, onlineState, players, playing));

            }

            preparedStatement.close();

        }catch(SQLException e) {

            e.printStackTrace();

        }

        return serverList;

    }

}
