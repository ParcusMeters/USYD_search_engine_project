package major_project.Presenter;

import java.util.List;

import javafx.application.HostServices;
import major_project.Model.Engine;
import major_project.Model.HTTP.Input.content_search.Content;
import major_project.Model.HTTP.Input.tag_search.Tag;
import major_project.View.GameWindow;


public class Presenter{
    private Engine model;
    private GameWindow view;

    public Presenter(Engine model, GameWindow view){
        this.model =model;
        this.view = view;
    }

    
    /** 
     * @param search
     * @return List<Tag>
     * @throws Exception
     */
    public List<Tag> searchTags(String search) throws Exception{
        if (search == null){
            throw new Exception("null type input");
        }
        return model.searchTags(search);
    }

    
    /** 
     * @param tag
     * @param search
     * @return List<Content>
     * @throws Exception
     */
    public List<Content> searchContent(String tag, String search) throws Exception{
        if (tag == null || tag == search){
            throw new Exception("null type input");
        }
        return model.searchContent(tag, search);
    }

    
    /** 
     * @return List<Tag>
     */
    public List<Tag> getPreviousTags() {
        return model.getPreviousTags();
    }



    
    /** 
     * @return List<Content>
     */
    public List<Content> getPreviousSearch() {
        return model.getPreviousSearch();
    }

    
    /** 
     * @param content
     * @return String
     */
    public String getContentFromCache(String content) {
        return model.getContentFromCache(content);
    }

    
    /** 
     * @param tag
     */
    public void setSelectedTag(String tag) {
        model.setSelectedTag(tag);
    }

    
    /** 
     * @return String
     */
    public String getSelectedTag() {
        return model.getSelectedTag();
    }

    
    /** 
     * @param s
     */
    public void setSelectedContent(String s) {
        model.setSelectedContent(s);
    }

    
    /** 
     * @return String
     */
    public String getSelectedContent() {
        return model.getSelectedContent();
    }

    
    /** 
     * @return String
     */
    public String pasteShort(){

        return model.pasteShort();
    }

    
    /** 
     * @return String
     */
    public String pasteLong() {
        return model.pasteLong();
    }

    public void deleteDB() {
        model.deleteDB();
    }

    public void makePost(){
        model.makePost();
    }
}
