package Service;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Connection {


public String getJokes(String url){
    List<String> jokes = new ArrayList<>();
    while (jokes.size() < 10) {
        String joke;
        try {
            joke = getSingleJoke(url);
            if (!jokes.contains(joke)) {
                jokes.add(joke);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    return prepareJokesToPrint(jokes);
}

    private String prepareJokesToPrint(List<String> jokes) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < jokes.size(); i++) {
            sb
                    .append(i + 1)
                    .append(". ")
                    .append(jokes.get(i))
                    .append("\n\n");
        }
        return sb.toString();
    }


    private String getSingleJoke(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

       Gson gson = new Gson();
       HashMap<String, String> JSONresponse = gson.fromJson(response.toString(), HashMap.class);
       return JSONresponse.get("value");


    }


}