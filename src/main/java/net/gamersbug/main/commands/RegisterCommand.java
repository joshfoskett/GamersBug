package net.gamersbug.main.commands;

import net.gamersbug.main.MessageManager;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RegisterCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {

            return true;

        }

        Player player = (Player) sender;

        if(args.length != 2) {

            return false;

        }

        MessageManager.playerActionMessage("Register", "Attempting to register your account.", ChatColor.AQUA, ChatColor.YELLOW, player);

        if(args[1].length() < 6 || args[1].length() > 64) {

            MessageManager.playerActionMessage("Register", "Your password must be 6 to 64 characters long.", ChatColor.AQUA, ChatColor.RED, player);

            return true;

        }

        HttpPost httppost = new HttpPost("http://gamersbug.net/api/gamersbug/");

        List<BasicNameValuePair> parameters = new ArrayList<>();

        parameters.add(new BasicNameValuePair("uuid",      player.getUniqueId().toString()));
        parameters.add(new BasicNameValuePair("username",  player.getName()));
        parameters.add(new BasicNameValuePair("email",     args[0]));
        parameters.add(new BasicNameValuePair("password",  args[1]));
        parameters.add(new BasicNameValuePair("client_id", "e7YGBR3Cx2uJN3g6PxXx8U007yRuflVx"));

        try {

            httppost.setEntity(new UrlEncodedFormEntity(parameters));

            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse = httpclient.execute(httppost);
            HttpEntity resEntity = httpResponse.getEntity();

            // Get the HTTP Status Code
            int statusCode = httpResponse.getStatusLine().getStatusCode();

            // Get the contents of the response
            InputStream input = resEntity.getContent();
            String responseBody = IOUtils.toString(input);
            input.close();

            JSONParser parser = new JSONParser();

            JSONObject jsonObject = (JSONObject) parser.parse(responseBody);

            if(jsonObject.get("errors") == null) {

                MessageManager.playerActionMessage("Register", "You have successfully registered.", ChatColor.AQUA, ChatColor.GREEN, player);

            }else{

                JSONObject errorsObject = (JSONObject) jsonObject.get("errors");

                Set<String> errorKeys = errorsObject.keySet();

                for(String errorKey : errorKeys) {

                    String errorMessage = errorsObject.get(errorKey).toString();

                    MessageManager.playerActionMessage("Register", errorMessage, ChatColor.AQUA, ChatColor.RED, player);

                }

            }

        } catch (IOException | ParseException e) {

            MessageManager.playerActionMessage("Register", "Something went wrong, please try again later.", ChatColor.AQUA, ChatColor.RED, player);

            e.printStackTrace();

        }

        return true;

    }

}
