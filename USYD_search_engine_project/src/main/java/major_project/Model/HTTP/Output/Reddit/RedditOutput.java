package major_project.Model.HTTP.Output.Reddit;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import major_project.Model.Engine;

public class RedditOutput {


    public RedditOutput(){
    }

    public String getAuthKey(){
        String authString = "";

        try {

            Gson gson = new Gson();
            String json = gson.toJson(null);
  
            String s = "client_id=3uPUmXAYwkf8RUsUorvMHw&response_type=code&state=wadawd&redirect_uri=http://www.example.com/unused/redirect/uri&duration=temporary&scope=submit";
            
            String userCred = "G2KPFA-DIMmAQ1XeTtOYuw:olNcGx_tqISlTxT_grlDiEQa0zMbTg";
            String cred = new String(Base64.getEncoder().encodeToString(userCred.getBytes()));

            HttpRequest request = HttpRequest
                    .newBuilder(new URI("https://www.reddit.com/api/v1/access_token"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("User-Agent", "major_project/v0.1")
                    .header("Authorization", "Basic " + cred)
                    .POST(HttpRequest.BodyPublishers.ofString("grant_type=password&username=muscleman1997&password=Symerevert2001"))
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response status code was: " + response.statusCode());
            System.out.println("Response body was:\n" + response.body());

           
            JsonElement element = gson.fromJson(response.body(), JsonElement.class);
            JsonObject jsonObj = element.getAsJsonObject();

            String authToken = gson.fromJson(jsonObj.get("access_token"), String.class);
            authString = authToken;

            return authString;

            

        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
            System.out.println(e.getMessage());
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you
            // use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
        }

        return authString;
    }

    public void makePost(String token, String post){
        try {

            Gson gson = new Gson();
            String json = gson.toJson(null);
  
            String s = "client_id=3uPUmXAYwkf8RUsUorvMHw&response_type=code&state=wadawd&redirect_uri=http://www.example.com/unused/redirect/uri&duration=temporary&scope=submit";
            
            String userCred = "G2KPFA-DIMmAQ1XeTtOYuw:olNcGx_tqISlTxT_grlDiEQa0zMbTg";
            String cred = new String(Base64.getEncoder().encodeToString(userCred.getBytes()));

            HttpRequest request = HttpRequest
                    .newBuilder(new URI("https://oauth.reddit.com/api/submit"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("User-Agent", "major_project/v0.1")
                    .header("Authorization", "Bearer " + token)
                    .POST(HttpRequest.BodyPublishers.ofString("kind=self&sr=u_muscleman1997&title=Search Results&resubmit=true&send_replies=true&text=" + post))
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response status code was: " + response.statusCode());
            System.out.println("Response body was:\n" + response.body());

           
            JsonElement element = gson.fromJson(response.body(), JsonElement.class);
            JsonObject jsonObj = element.getAsJsonObject();

            String authToken = gson.fromJson(jsonObj.get("access_token"), String.class);
           

            

        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
            System.out.println(e.getMessage());
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you
            // use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
        }

    }


    
    
    
}
