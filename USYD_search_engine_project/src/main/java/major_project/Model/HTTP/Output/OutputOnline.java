package major_project.Model.HTTP.Output;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;


public class OutputOnline implements OutputProtocol{
    



    
    /** 
     * @param paste
     * @return String
     */
    public String createPaste(String paste){

        String responseString = "";
        try {

            Gson gson = new Gson();
            String pasteJSON = gson.toJson(paste);
            System.out.println(pasteJSON);

            String s = "api_dev_key=aDkACNGOqD1TsrHU_b2ga0yTJvqhzxpJ&api_paste_code=" + paste + "&api_option=paste";

            HttpRequest request = HttpRequest
                    .newBuilder(new URI("https://pastebin.com/api/api_post.php"))
                    .headers("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(s))
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response status code was: " + response.statusCode());
            System.out.println("Response body was:\n" + response.body());

            responseString = response.body();

            

        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
            System.out.println(e.getMessage());
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you
            // use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
        }

        return responseString;
    }
}
