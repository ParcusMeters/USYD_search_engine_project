package major_project.Model.HTTP.Input;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import major_project.Model.HTTP.Input.content_search.Content;
import major_project.Model.HTTP.Input.content_search.ContentResponse;
import major_project.Model.HTTP.Input.tag_search.Tag;
import major_project.Model.HTTP.Input.tag_search.TagResponse;

public class InputOnline implements InputProtocol {

    
    /** 
     * @param search
     * @return List<Tag>
     */
    public List<Tag> searchTags(String search) {

        List<Tag> results = null;

        try {
            Gson gson = new Gson();
            HttpRequest request = HttpRequest
                    .newBuilder(new URI(
                            "https://content.guardianapis.com/tags?page-size=5&web-title=" + search
                                    + "&api-key=68cab2b3-9bca-4ef3-a3b4-a0aea8c324bb"))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Response status code was: " + response.statusCode());
            System.out.println("Response body was:\n" + response.body());

            JsonElement element = gson.fromJson(response.body(), JsonElement.class);
            JsonObject jsonObj = element.getAsJsonObject();

            TagResponse r = gson.fromJson(jsonObj.get("response"), TagResponse.class);
            results = r.getResults();

            System.out.println();

            for (int i = 0; i < results.size(); i++) {
                System.out.print("Tag: " + results.get(i).getId() + " ");
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
            System.out.println(e.getMessage());
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you
            // use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
        }

        return results;

    }

    
    /** 
     * @param tag
     * @param search
     * @return List<Content>
     */
    public List<Content> searchContent(String tag, String search) {

        List<Content> results = null;

        try {

            System.out.println("URI: https://content.guardianapis.com/search?page-size=5&tag=" + tag + "&q=" + search
                    + "&api-key=68cab2b3-9bca-4ef3-a3b4-a0aea8c324bb");
            Gson gson = new Gson();
            HttpRequest request = HttpRequest
                    .newBuilder(new URI(
                            "https://content.guardianapis.com/search?page-size=5&tag=" + tag + "&q=" + search
                                    + "&api-key=68cab2b3-9bca-4ef3-a3b4-a0aea8c324bb"))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Response status code was: " + response.statusCode());
            System.out.println("Response body was:\n" + response.body());

            JsonElement element = gson.fromJson(response.body(), JsonElement.class);
            JsonObject jsonObj = element.getAsJsonObject();

            ContentResponse r = gson.fromJson(jsonObj.get("response"), ContentResponse.class);
            results = r.getResults();

            System.out.println();

            for (int i = 0; i < results.size(); i++) {
                System.out.print("Content: " + results.get(i).getId() + " ");
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
            System.out.println(e.getMessage());
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you
            // use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
        }

        return results;

    }
}
