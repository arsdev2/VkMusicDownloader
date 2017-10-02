package ua.pp.arsdev.net;
import java.net.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Net{
    public static final String USERNAME = "ТУТ ВАШ ЛОГИН", PASSWORD= "ТУТ ВАШ ПАРОЛЬ", ACCESS_TOKEN="ТУТ ВАШ ТОКЕН",USER_AGENT="ТУТ ВАШ ЮЗЕР АГЕНТ";
    public static int MODE = 0;
    public static void main(String[] args) throws IOException{
        try{
            String ad = String.format(Locale.US, "https://oauth.vk.com/token?%s=password&%s=2274003&%s=hHbZxrka2uZ6jB1inYsH&username="+USERNAME+"&password="+PASSWORD + "&v=%s&%s=1", new Object[]{URLEncoder.encode("grant_type", "UTF-8"),URLEncoder.encode("client_id", "UTF-8"), URLEncoder.encode("client_secret", "UTF-8") ,URLEncoder.encode("5.64", "UTF-8"),URLEncoder.encode("2fa_supported", "UTF-8")} );
            String audio =  String.format(Locale.US, "https://api.vk.com/method/audio.get?%s=%s", new Object[]{URLEncoder.encode("access_token", "UTF-8"),URLEncoder.encode(ACCESS_TOKEN, "UTF-8")} );
            System.out.println(audio);
            URL url = new URL(audio);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = con.getResponseCode();
            System.out.println(responseCode);
            StringBuilder b = new StringBuilder();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            b.append(reader.readLine());
            if(MODE == 0){
                System.out.println(b.toString());
            }else if(MODE == 1){
            String input = b.toString();
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(input);
            JSONObject json = (JSONObject) obj;
            JSONArray array = (JSONArray) json.get("response");
            for(int i = 0;i< array.size();i++){
                Object el = array.get(i);
                JSONObject element = (JSONObject) el;
                String filename = element.get("title") + "_" + element.get("artist") + ".mp3";
                try {
                    String music_url = element.get("url").toString();
                    System.out.println("Download Start!");
                    downloadFile(music_url, filename);
                    System.out.println("Artist is: " + element.get("artist") + " Title is: " + element.get("title") + " URL is: " + element.get("url"));
                    System.out.println("Download Finished!");
                }catch (NullPointerException e){
                    System.out.println("There is not url for: " + element.get("artist"));
                }
            }
            }
        }catch(Exception e){e.printStackTrace();}
        
    }
    public static void downloadFile(String _url, String _name) {
        try {
            File file = new File("/vkmusic/download/");
            if(!file.exists()){
                file.createNewFile();
            }            URL url = new URL(_url);
            final Path dstFile = Paths.get("/vkmusic/download/" + _name);
            Files.copy(url.openStream(), dstFile);
            java.lang.Runtime.getRuntime().runFinalization();
            java.lang.Runtime.getRuntime().gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
