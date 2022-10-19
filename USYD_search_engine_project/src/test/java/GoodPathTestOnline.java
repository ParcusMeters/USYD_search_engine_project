import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.InaccessibleObjectException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;
import major_project.Main;
import major_project.Model.Engine;
import major_project.Model.HTTP.Input.InputOnline;
import major_project.Model.HTTP.Input.InputProtocol;
import major_project.Model.HTTP.Input.content_search.Content;
import major_project.Model.HTTP.Input.tag_search.Tag;
import major_project.Model.HTTP.Output.OutputOnline;
import major_project.Model.HTTP.Output.OutputProtocol;
import major_project.Model.SQL.MainSQL;
import major_project.Presenter.Presenter;
import major_project.View.GameWindow;

public class GoodPathTestOnline {
    OutputProtocol outputMock = mock(OutputOnline.class);
    InputProtocol inputMock = mock(InputOnline.class);
    Engine model = new Engine(inputMock, outputMock);

    List<Tag> offlineTags = createTag();
    List<Content> contentList = createContent();

    Engine modelMock = mock(Engine.class);

    @BeforeEach
    public void init() {

    }

    public List<Tag> createTag() {
        offlineTags = new ArrayList<>();

        Tag t = new Tag("katine/football", "type", "Katine", "Katine", "Football",
                "http://www.theguardian.com/katine/football", "http://beta.content.guardianapis.com/katine/football",
                "description");

        offlineTags.add(t);

        return offlineTags;
    }

    public List<Content> createContent() {
        List<Content> contentList = new ArrayList<Content>();

        Content c = new Content(
                "football/2020/sep/08/arsenal-hector-bellerin-invests-in-forest-green-in-eco-friendly-push", "football",
                "Football", "2020-09-08T07:35:08Z", "webTitle",
                "https://www.theguardian.com/football/2020/sep/08/arsenal-hector-bellerin-invests-in-forest-green-in-eco-friendly-push",
                "https://content.guardianapis.com/football/2020/sep/08/arsenal-hector-bellerin-invests-in-forest-green-in-eco-friendly-push");
        contentList.add(c);

        return contentList;
    }

    @Test
    public void testNothing() {

    }

    @Test
    public void testTagSearch() {
        when(inputMock.searchTags(anyString())).thenReturn(offlineTags);

        try {
            assertThat(model.searchTags("blah").size(), equalTo(0));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        verify(inputMock).searchTags("blah");
        assertThat(MainSQL.doesTagExist("blah"), equalTo(false));

        assertThat(model.getPreviousTags(), equalTo(offlineTags));

   

    }

    @Test
    public void testContentSearch() {

        model.deleteDB();

        when(inputMock.searchTags(anyString())).thenReturn(offlineTags);

        when(inputMock.searchContent(anyString(), anyString()))
                .thenReturn(contentList);

        try {
            assertThat(model.searchContent("deez", "guacamole").size(), equalTo(0));
            assertThat(MainSQL.doesContentExist("blah"), equalTo(false));

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        verify(inputMock).searchContent("deez", "guacamole");

        assertThat(model.getPreviousSearch(), equalTo(contentList));

    }

    @Test
    public void testPaste() {

        model.setSelectedTag("Sometimes I dream of cheese");
        model.setSelectedContent("but only on alternating tuesdays");

        try {
            model.pasteShort();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        verify(outputMock, times(1))
                .createPaste("Sometimes I dream of cheese" + " " + "but only on alternating tuesdays");
    }

    @Test
    public void testPasteLong() {

        when(inputMock.searchTags(anyString())).thenReturn(offlineTags);

        when(inputMock.searchContent(anyString(), anyString()))
                .thenReturn(contentList);

        model.setSelectedTag("Sometimes I dream of cheese");
        try {
            model.searchContent("bruh", "momentum");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        model.pasteLong();

        verify(outputMock).createPaste("Sometimes I dream of cheese webTitle \n");

    }

    @Test
    public void databaseTagTest() {
        try (MockedStatic mocked = mockStatic(MainSQL.class)) {

            // Mocking

            mocked.when(() -> MainSQL.doesTagExist(anyString())).thenReturn(false);
            mocked.when(() -> MainSQL.doesContentExist(anyString())).thenReturn(true);
            mocked.when(() -> MainSQL.getContent(anyString())).thenReturn("true");

            when(inputMock.searchTags(anyString())).thenReturn(offlineTags);

            try {
                assertThat(model.searchTags("blah").size(), equalTo(0));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            verify(inputMock).searchTags("blah");
            mocked.verify(() -> MainSQL.doesTagExist("katine/football"));
            mocked.verify(() -> MainSQL.addTag("katine/football"));

            mocked.when(() -> MainSQL.doesTagExist(anyString())).thenReturn(true);

            try {
                assertThat(model.searchTags("blah").size(), equalTo(1));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            model.deleteDB();
            mocked.verify(() -> MainSQL.deleteTable());
            mocked.verify(() -> MainSQL.setupDB());

        }
    }

    @Test
    public void databaseContentTest() {
        try (MockedStatic mocked = mockStatic(MainSQL.class)) {

            // Mocking

            mocked.when(() -> MainSQL.doesTagExist(anyString())).thenReturn(false);
            mocked.when(() -> MainSQL.doesContentExist(anyString())).thenReturn(false);
            mocked.when(() -> MainSQL.getContent(anyString())).thenReturn("true");

            when(inputMock.searchContent(anyString(), anyString()))
                    .thenReturn(contentList);

            try {
                assertThat(model.searchContent("deez", "guacamole").size(), equalTo(0));
                assertThat(MainSQL.doesContentExist("blah"), equalTo(false));

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

     
  
            assertThat(model.getPreviousSearch(), equalTo(contentList));


            assertThat(model.getContentFromCache("content"), equalTo("true"));

            mocked.verify(() -> MainSQL.getContent("content"));

        }
    }

}
