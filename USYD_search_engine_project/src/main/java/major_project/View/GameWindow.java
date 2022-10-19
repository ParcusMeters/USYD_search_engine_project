package major_project.View;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import major_project.Model.Engine;
import major_project.Model.HTTP.Input.content_search.Content;
import major_project.Model.HTTP.Input.tag_search.Tag;
import major_project.Presenter.Presenter;

//LISTS NEED TO BE MADE WITHIN THE MODEL NOT THE VIEW
//PRESENTER WILL UPDATE THESE LISTS AND HANDLE THEM
//oath libraries to obtain query, use general oath library
//need to move hostservices, create it within main and pass it to the view class. (DONE)

/* 
Store and display the currently selected tag somewhere outside the original list so the user can see if and exactly what tag has been selected. This tag selection should stay selected and displayed until a new tag replaces it (right now after 1 content search the tag selection appears to be lost). (DONE)

Replace the OK buttons on both the list of tags and list of content with a double click listener, so it is obvious what the buttons are supposed to do  (DONE)

Ensure there is a button to click to send the pastebin report, don't send it automatically (DONE)

With your caching (not part of the marked commits but reviewed in the readme commit), ensure that it follows the process that the request is made from the UI (with no mention of caching), the cache is checked first, if and only if there is a cache hit, go back and ask the user which to use, cache or API - for that specific query. (NEED TO CACHE THE RESULTS AND ASSOCIATE THEM WITH THE SEARCH PARAMETERS INCLUDING SLECTED TAG AND CONTENT SEARCH)
 */

public class GameWindow {

    private Engine model;
    private BorderPane root = new BorderPane();
    private Scene scene = new Scene(root);
    private List<Tag> tags = null;
    private List<Content> contents = null;
    private List<Content> cachedContent = null;
    private ListView<String> displayedContentList;
    private ListView<String> displayedTagList;
    private ListView<String> displayedCacheList;
    private Task<Integer> task;
    private ProgressIndicator progressIndicator;
    private Presenter presenter;
    private Label selectedTagLabel;
    private ListView<String> viewLaterList;

    private Button searchTagBtn;
    private Button searchContentBtn;
    private HostServices hostServices;

    private final ExecutorService pool = Executors.newFixedThreadPool(2, runnable -> {
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        return thread;
    });

    public GameWindow(Engine model) {
        this.model = model;
        this.displayedTagList = new ListView<String>();
        this.displayedCacheList = new ListView<String>();
        buildGrid();
    }

    public GameWindow(Engine model, HostServices hostServices) {
        this.model = model;
        this.displayedTagList = new ListView<String>();
        this.displayedCacheList = new ListView<String>();
        this.hostServices = hostServices;
        buildGrid();
    }

    public void buildGrid() {

        this.root.getChildren().removeAll(this.root.getChildren());

        buildTagSearch();
        buildContentSearch();
        buildCacheHandler();

        progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(false);
        root.setLeft(progressIndicator);

    }

    public void buildTagSearch() {
        VBox tagBox = new VBox();
        HBox structure = new HBox();

        displayedTagList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        displayedTagList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        selectTagAction(displayedTagList);
                    }
                }
            }
        });

        TextField tagSearchBox = new TextField();

        // buttons
        searchTagBtn = new Button("Search");
        searchTagBtn.setOnAction((event) -> tagSearchAction(tagSearchBox.getText()));
        /*
         * //task button
         * Button taskButton = new Button("Execute Task");
         * taskButton.setOnAction((event) -> runTask(tagSearchBox.getText()));
         */

        Label tagLabel = new Label("Tag Search");
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Menus");
        MenuItem about = new MenuItem("about");

        menuBar.getMenus().add(menu);
        menu.getItems().add(about);

        about.setOnAction((event) -> loadAbout());

        structure.getChildren().addAll(tagLabel, tagSearchBox, searchTagBtn);
        selectedTagLabel = new Label("No tag selected");
        tagBox.getChildren().addAll(structure, displayedTagList, selectedTagLabel);
        root.setTop(menuBar);
        root.setCenter(tagBox);

    }

    /**
     * @param search
     */
    public void tagSearchAction(String search) {
        System.out.println("Search: " + search);

        // managing gui list
        // first clearing content search
        this.displayedContentList.getItems().removeAll(this.displayedContentList.getItems());
        this.contents = null;
        // then clearing tag search
        if (displayedTagList.getItems().size() > 0) {
            displayedTagList.getItems().removeAll(displayedTagList.getItems());
        }
        progressIndicator.setVisible(true);

        searchTagBtn.setDisable(true);
        searchContentBtn.setDisable(true);
        task = new Task<>() {
            @Override
            protected Integer call() {
                System.out.println("Task is runnning.");
                // search
                try {
                    presenter.searchTags(search);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                // getting all tag search search results from this search
                tags = presenter.getPreviousTags();

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("Task is still running! ");
                // displaying tags
                Platform.runLater(() -> {
                    for (Tag tag : tags) {
                        displayedTagList.getItems().add(tag.getId());
                    }
                });

                /*
                 * try {
                 * Thread.sleep(500);
                 * } catch (InterruptedException ignored) {
                 * if (isCancelled()) {
                 * break;
                 * }
                 * }
                 */
                System.out.println("Task is still running! ");
                searchTagBtn.setDisable(false);
                searchContentBtn.setDisable(false);
                progressIndicator.setVisible(false);

                return 0;
            }

        };

        pool.execute(task);

    }

    public void buildCacheHandler() {
        VBox tagBox = new VBox();
        HBox structure = new HBox();

        displayedCacheList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        Button okBtn = new Button("ok");
        okBtn.setOnAction((event) -> selectLoadFromCache());
        Button doneBtn = new Button("Done");
        doneBtn.setOnAction((event) -> doneAction());

        Button clearBtn = new Button("Clear cache");
        clearBtn.setOnAction((event) -> clearCache());

        Label tagLabel = new Label("Select tags you would like to load from cache.");

        structure.getChildren().addAll(tagLabel, okBtn, doneBtn, clearBtn);

        tagBox.getChildren().addAll(structure, displayedCacheList);

        root.setBottom(tagBox);

    }

    public void loadFromCacheSelection() {

        this.displayedCacheList.getItems().removeAll(this.displayedCacheList.getItems());

        for (Content c : this.cachedContent) {
            System.out.println("Cached item " + c);
            this.displayedCacheList.getItems().add(c.getWebTitle() + "\n" + c.getWebPublicationDate());
        }

    }

    public void doneAction() {
        if (displayedCacheList.getItems().size() > 0) {
            for (Content c : this.cachedContent) {
                if (this.displayedContentList.getItems()
                        .contains(c.getWebTitle() + "\n" + c.getWebPublicationDate()) == false) {
                    this.displayedContentList.getItems().add(c.getWebTitle() + "\n" + c.getWebPublicationDate());
                }
            }

            this.displayedCacheList.getItems().removeAll(this.displayedCacheList.getItems());
        }
    }

    public void selectLoadFromCache() {
        try {
            String selected = this.displayedCacheList.getSelectionModel().getSelectedItem();

            if (selected.equals("")) {
                return;
            }
            String[] content = selected.split("\n");

            this.displayedContentList.getItems().add(presenter.getContentFromCache(content[0]));
            System.out.println("selected cache item " + content[0]);
            this.displayedCacheList.getItems().remove(selected);

        } catch (Exception e) {
            System.out.println("Error: no tag selected");
        }
    }

    public void clearCache() {
        this.displayedCacheList.getItems().removeAll(this.displayedCacheList.getItems());
        presenter.deleteDB();
    }

    /**
     * @param displayedTagList
     */
    public void selectTagAction(ListView<String> displayedTagList) {
        try {
            String selected = displayedTagList.getSelectionModel().getSelectedItem();

            if (selected.equals("")) {
                return;
            }

            presenter.setSelectedTag(selected);
            selectedTagLabel.setText("Selected tag: " + presenter.getSelectedTag());
        } catch (Exception e) {
            System.out.println("Error: no tag selected");
        }

    }

    public void buildContentSearch() {
        VBox contentBox = new VBox();
        HBox structure = new HBox();

        this.displayedContentList = new ListView<>();
        this.displayedContentList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.displayedContentList.setMinWidth(500);
        this.displayedContentList.autosize();

        this.viewLaterList = new ListView<>();

        displayedContentList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        loadAndDisplayContentAction(false);
                    }
                }
            }
        });

        TextField contentSearchBox = new TextField();
        searchContentBtn = new Button("Search");
        searchContentBtn.setOnAction((event) -> contentSearchAction(contentSearchBox.getText()));
        Label contentLabel = new Label("Content Search");

        Button addToViewLaterBtn = new Button("Add to view later");
        addToViewLaterBtn.setOnAction((event) -> {
            if (displayedContentList.getSelectionModel().getSelectedItem() != null) {
                if (!viewLaterList.getItems().contains(displayedContentList.getSelectionModel().getSelectedItem())) {
                    viewLaterList.getItems().add(displayedContentList.getSelectionModel().getSelectedItem());
                }
            }
        });

        // left VBOX
        structure.getChildren().addAll(contentLabel, contentSearchBox, searchContentBtn, addToViewLaterBtn);
        contentBox.getChildren().addAll(structure, displayedContentList);

        // right VBOX
        VBox viewLaterBox = new VBox();
        HBox viewLaterInfo = new HBox();
        Button removeFromViewLaterBtn = new Button("remove");
        removeFromViewLaterBtn.setOnAction((event) -> {
            viewLaterList.getItems().remove(viewLaterList.getSelectionModel().getSelectedItem());
        });
        Label viewLaterTitle = new Label("Articles to read");
        viewLaterInfo.getChildren().addAll(viewLaterTitle, removeFromViewLaterBtn);
        viewLaterBox.getChildren().addAll(viewLaterInfo, viewLaterList);

        // combined LEFT and RIGHT HBOX
        HBox container = new HBox();
        container.getChildren().addAll(contentBox, viewLaterBox);
        root.setRight(container);

        // adding display url functionality to saved articles
        viewLaterList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        loadAndDisplayContentAction(true);
                    }
                }
            }
        });
    }

    /**
     * @param search
     */
    // need to make cache list update properly, right now doesnt see content as
    // matching because of differeing constructors.
    private void contentSearchAction(String search) {

        searchContentBtn.setDisable(true);
        searchTagBtn.setDisable(true);
        // clear display list on new search
        if (displayedContentList.getItems().size() > 0) {
            displayedContentList.getItems().removeAll(displayedContentList.getItems());
        }
        progressIndicator.setVisible(true);

        task = new Task<>() {
            @Override
            protected Integer call() {
                System.out.println("Task is runnning.");
                // search
                try {
                    presenter.searchTags(search);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                // getting all tag search search results from this search
                try {
                    cachedContent = presenter.searchContent(presenter.getSelectedTag(), search);
                    System.out.println("Saving cached content to display");
                    for (Content c : cachedContent) {
                        System.out.println("cached content " + c.getWebTitle());
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                contents = presenter.getPreviousSearch();

                tags = presenter.getPreviousTags();

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                // displaying search results
                Platform.runLater(() -> {
                    for (Content content : contents) {
                        boolean contains = false;
                        for (Content contentCached : cachedContent) {
                            if (contentCached.getWebUrl().equals(content.getWebUrl())) {
                                contains = true;
                            }
                        }
                        if (!contains) {
                            displayedContentList.getItems()
                                    .add(content.getWebTitle() + "\n" + content.getWebPublicationDate());
                        }
                        loadFromCacheSelection();
                    }

                    /*
                     * for (Content content : contents) {
                     * if (cachedContent.contains(content) == false) {
                     * System.out.println("content not cached");
                     * displayedContentList.getItems()
                     * .add(content.getWebTitle() + "\n" + content.getWebPublicationDate());
                     * }
                     * loadFromCacheSelection();
                     * }
                     */
                });

                System.out.println("Task is still running! ");
                searchContentBtn.setDisable(false);
                searchTagBtn.setDisable(false);
                progressIndicator.setVisible(false);

                return 0;
            }

        };

        pool.execute(task);

    }

    public void loadAndDisplayContentAction(boolean loadFromViewLater) {
        try {
            String selected = "";
            if (!loadFromViewLater) {
                selected = displayedContentList.getSelectionModel().getSelectedItem();
            } else {
                selected = viewLaterList.getSelectionModel().getSelectedItem();
            }

            String[] ls = selected.split("\n");
            String selectedTitle = ls[0];

            presenter.setSelectedContent(selectedTitle);

            if (selected.equals("")) {
                return;
            }

            for (Content content : presenter.getPreviousSearch()) {
                if (content.getWebTitle().equals(selectedTitle)) {
                    String url = content.getWebUrl();
                    System.out.println("Api URL: " + content.getApiUrl());
                    showWebsite(url);
                }
            }

            if (!loadFromViewLater) {
                displayLinks();
            }

        } catch (Exception e) {
            System.out.println("Error: no content selected");
        }
    }

    public void displayLinks() {
        PasteBin links = new PasteBin(presenter);
    }

    public void loadAbout() {
        About about = new About();

    }

    /**
     * @return Scene
     */
    public Scene getScene() {
        return this.scene;
    }

    /**
     * @return ListView<String>
     */
    public ListView<String> getTagList() {
        return this.displayedTagList;
    }

    /**
     * @param p
     */
    public void addPresenter(Presenter p) {
        this.presenter = p;
    }

    /**
     * @return HostServices
     */
    public HostServices getHostServices() {
        return this.hostServices;
    }

    /**
     * @param uri
     */
    public void showWebsite(String uri) {
        System.out.println("Showing website " + uri);
        getHostServices().showDocument(uri);
    }

    /**
     * @return String
     */
    public String getURI() {
        System.out.println(getHostServices().getDocumentBase());
        return getHostServices().getDocumentBase();
    }

}
