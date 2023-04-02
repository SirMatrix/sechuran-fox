package Config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfigUploader {
    private static SQLDriver sqlDriver;

    public ConfigUploader(SQLDriver sqlDriver) {
        this.sqlDriver = sqlDriver;
    }

    public void saveConfig(String serverID, String commandName, String channelID) {
        String query = "INSERT INTO config (server_id, command_name, channel_id) VALUES (?, ?, ?)";
        try (Connection connection = sqlDriver.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, serverID);
            statement.setString(2, commandName);
            statement.setString(3, channelID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeCommandRestriction(String serverID, String commandName) {
        String query = "DELETE FROM config WHERE server_id='" + serverID + "' AND command_name='" + commandName + "'";
        sqlDriver.executeUpdate(query);
    }

    public static String getRestrictedChannel(String serverID, String commandName) {
        String query = "SELECT channel_id FROM config WHERE server_id=? AND command_name=?";
        try (Connection connection = sqlDriver.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, serverID);
            statement.setString(2, commandName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("channel_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isCommandRestricted(String serverID, String commandName) {
        String query = "SELECT channel_id FROM config WHERE server_id=? AND command_name=?";
        try (Connection connection = sqlDriver.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, serverID);
            statement.setString(2, commandName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}