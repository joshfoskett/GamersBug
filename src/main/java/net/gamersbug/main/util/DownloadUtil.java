package net.gamersbug.main.util;

import com.github.axet.wget.WGet;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DownloadUtil {

    public void downloadDirectory(String urlPath, String destFolder) {

        try {

            Document doc = Jsoup.connect(urlPath).get();
            Elements links = doc.select("a[href]");

            for(Element linkElement : links) {

                String link = linkElement.attr("abs:href");

                if(!link.contains("?") && !(link.length() <= urlPath.length())) {

                    if(link.endsWith("/")) {

                        String nextFolder = link.replace(urlPath.replace(" ", "%20"), "");

                        this.downloadDirectory(link, destFolder + nextFolder);

                    }else{

                        this.downloadFile(link, destFolder);

                    }

                }

            }

        }catch(IOException ex) {

            // lolwat

        }

    }

    public void downloadFile(String urlPath, String destFolder) {

        try {

            URL url = new URL(urlPath);

            File target = new File(destFolder);

            if(!target.exists()) {

                target.mkdir();

            }

            WGet w = new WGet(url, target);

            w.download();

        } catch (MalformedURLException | RuntimeException e) {

            Bukkit.getLogger().log(Level.INFO, "[DownloadUtil] There was an issue downloading a file.");
            Bukkit.getLogger().log(Level.INFO, "[DownloadUtil] Couldn't fetch: {0}", urlPath);

        }

    }

}