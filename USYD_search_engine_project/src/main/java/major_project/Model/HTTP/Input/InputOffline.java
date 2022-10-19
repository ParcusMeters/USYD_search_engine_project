package major_project.Model.HTTP.Input;

import java.util.ArrayList;
import java.util.List;

import major_project.Model.HTTP.Input.content_search.Content;
import major_project.Model.HTTP.Input.tag_search.Tag;

public class InputOffline implements InputProtocol{

    
    /** 
     * @param search
     * @return List<Tag>
     */
    @Override
    public List<Tag> searchTags(String search) {
        // TODO Auto-generated method stub

        List<Tag> offlineTags = new ArrayList<>();

        Tag t = new Tag("katine/football", "type", "Katine", "Katine", "Football", "http://www.theguardian.com/katine/football", "http://beta.content.guardianapis.com/katine/football", "description");

        offlineTags.add(t);

        return offlineTags;
    }

    
    /** 
     * @param tag
     * @param search
     * @return List<Content>
     */
    @Override
    public List<Content> searchContent(String tag, String search) {
        // TODO Auto-generated method stub
        List<Content> contentList = new ArrayList<Content>();

        Content c = new Content("football/2020/sep/08/arsenal-hector-bellerin-invests-in-forest-green-in-eco-friendly-push", "football", "Football", "2020-09-08T07:35:08Z", "webTitle", "https://www.theguardian.com/football/2020/sep/08/arsenal-hector-bellerin-invests-in-forest-green-in-eco-friendly-push", "https://content.guardianapis.com/football/2020/sep/08/arsenal-hector-bellerin-invests-in-forest-green-in-eco-friendly-push");
        contentList.add(c);

        return contentList;
    }
    
}
