package major_project.Model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import major_project.Model.HTTP.Input.InputProtocol;
import major_project.Model.HTTP.Input.content_search.Content;
import major_project.Model.HTTP.Input.tag_search.Tag;
import major_project.Model.HTTP.Output.OutputProtocol;
import major_project.Model.HTTP.Output.Reddit.RedditOutput;
import major_project.Model.SQL.MainSQL;

public class Engine {
    InputProtocol input;
    OutputProtocol output;
    String selectedTag;
    String selectedContent;
    List<Content> previousSearch;
    List<Tag> previousTag;
    RedditOutput reddit = new RedditOutput();
    String longOutput;

    public Engine(InputProtocol input, OutputProtocol output) {
        this.input = input;
        this.output = output;
    }

    
    /** 
     * @param search
     * @return List<Tag>
     */
    public List<Tag> searchTags(String search) {

        // saving tag results
        this.previousTag = input.searchTags(search);

        List<Tag> alreadyCachedTags = new ArrayList<Tag>();

        for (Tag tag : this.previousTag) {
            if (MainSQL.doesTagExist(tag.getId()) == false) {
                System.out.println("does not exist");
                MainSQL.addTag(tag.getId());
            } else {

                alreadyCachedTags.add(tag);
            }
        }
        // returns list of tags already in cache
        return alreadyCachedTags;
    }

    
    /** 
     * @param tag
     * @param search
     * @return List<Content>
     */
    public List<Content> searchContent(String tag, String search) {
        List<Content> alreadyCachedContent = new ArrayList<>();
        this.previousSearch = input.searchContent(tag, search);

        if (MainSQL.doesSearchExist(tag, search)) {
            for(Content c: MainSQL.getSearchContent(MainSQL.getContentId(tag, search))){
                alreadyCachedContent.add(c);
            }

        }

        else{
            MainSQL.addSearch(tag, search);


            for (Content content : this.previousSearch) {
                String setup = content.getWebTitle();
                if (MainSQL.doesContentExist(setup) == false) {
                    System.out.println("does not exist");

                    // adding the content to the content table, referencing the correct search
                    MainSQL.addContent(content.getWebTitle(), content.getWebPublicationDate(), content.getWebUrl(), MainSQL.getContentId(tag, search));
                } else {

                    alreadyCachedContent.add(content);
                }
            }
        }

        

        return alreadyCachedContent;

        /*
         * else{
         * 
         * }
         */
    }

    
    /** 
     * @return List<Content>
     */
    public List<Content> getPreviousSearch() {
        return this.previousSearch;
    }

    
    /** 
     * @param content
     * @return String
     */
    public String getContentFromCache(String content) {
        return MainSQL.getContent(content);
    }

    
    /** 
     * @return List<Tag>
     */
    public List<Tag> getPreviousTags() {
        return this.previousTag;
    }

    
    /** 
     * @param tag
     */
    public void setSelectedTag(String tag) {
        this.selectedTag = tag;

        System.out.println("Selected tag:" + this.selectedTag);
    }

    
    /** 
     * @return String
     */
    public String getSelectedTag() {
        return this.selectedTag;
    }

    
    /** 
     * @param s
     */
    public void setSelectedContent(String s) {
        this.selectedContent = s;
    }

    
    /** 
     * @return String
     */
    public String getSelectedContent() {
        return this.selectedContent;
    }

    
    /** 
     * @return String
     */
    public String pasteShort() {

        return output.createPaste(this.selectedTag + " " + this.selectedContent);
    }

    
    /** 
     * @return String
     */
    public String pasteLong() {
        String allContent = "";
        for (Content content : getPreviousSearch()) {
            allContent = allContent + content.getWebTitle() + " \n";
        }
        longOutput = this.selectedTag + " " + allContent;
        return output.createPaste(longOutput);
    }

    public void deleteDB() {
        MainSQL.deleteTable();
        MainSQL.setupDB();
    }

    public void makePost() {
        reddit.makePost(reddit.getAuthKey(), longOutput);
    }

}
