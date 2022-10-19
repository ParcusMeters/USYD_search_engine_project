package major_project.Model.HTTP.Input;

import java.util.List;

import major_project.Model.HTTP.Input.content_search.Content;
import major_project.Model.HTTP.Input.tag_search.Tag;

public interface InputProtocol {
    
    public List<Tag> searchTags(String search);

    public List<Content> searchContent(String tag, String search);
}
