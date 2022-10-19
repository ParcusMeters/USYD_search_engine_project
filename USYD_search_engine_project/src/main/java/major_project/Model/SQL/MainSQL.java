package major_project.Model.SQL;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import major_project.Model.HTTP.Input.content_search.Content;

public class MainSQL {
    private static final String dbName = "main.db";
    private static final String dbURL = "jdbc:sqlite:" + dbName;

    
    /** 
     * @param args
     */
    public static void main(String[] args) {
        createDB();
        setupDB();
    }

    public static void createDB() {
        File dbFile = new File(dbName);
        if (dbFile.exists()) {
            System.out.println("Database already created");
            return;
        }
        try (Connection ignored = DriverManager.getConnection(dbURL)) {
            // If we get here that means no exception raised from getConnection - meaning it
            // worked
            System.out.println("A new database has been created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    public static void removeDB() {
        File dbFile = new File(dbName);
        if (dbFile.exists()) {
            boolean result = dbFile.delete();
            if (!result) {
                System.out.println("Couldn't delete existing db file");
                System.exit(-1);
            } else {
                System.out.println("Removed existing DB file.");
            }
        } else {
            System.out.println("No existing DB file.");
        }
    }

    public static void deleteTable(){
        String deleteCacheTable = """
                DROP TABLE IF EXISTS tags
                    """;

        String deleteContentCacheTable = """
                DROP TABLE IF EXISTS content
                """;
        
        String deleteSearchCacheTable = """
                DROP TABLE IF EXISTS search
                """;

        try (Connection conn = DriverManager.getConnection(dbURL);
            Statement statement = conn.createStatement()) {
            statement.execute(deleteCacheTable);
            statement.execute(deleteContentCacheTable);
            statement.execute(deleteSearchCacheTable);


            System.out.println("Deleted tags table");
        } catch (SQLException e) {
            System.out.println("setup broken");

            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }


    public static void setupDB() {
        String createCacheTable = """
                CREATE TABLE IF NOT EXISTS tags(
                    id INTEGER PRIMARY KEY,
                    tag TEXT NOT NULL
                );
                    """;

        String createContentCacheTable = """
                CREATE TABLE IF NOT EXISTS content(
                    id INTEGER PRIMARY KEY,
                    content TEXT NOT NULL,
                    date TEXT NOT NULL,
                    address TEXT NOT NULL,
                    search_id INTEGER,
                    FOREIGN KEY (search_id) REFERENCES seach(id) ON DELETE CASCADE
                );
                """;

        String createSearchHistoryTable = """
                CREATE TABLE IF NOT EXISTS search(
                    id INTEGER PRIMARY KEY,
                    tag TEXT NOT NULL,
                    search TEXT NOT NULL
                );
                """;

        try (Connection conn = DriverManager.getConnection(dbURL);
            Statement statement = conn.createStatement()) {
            statement.execute(createCacheTable);
            statement.execute(createContentCacheTable);
            statement.execute(createSearchHistoryTable);

            System.out.println("Created tags table");
        } catch (SQLException e) {
            System.out.println("setup broken");

            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    
    /** 
     * @param tag
     * @return boolean
     */
    public static boolean doesTagExist(String tag) {
        String findTag = """
                SELECT tag FROM tags WHERE tag = ?
                    """;
        try (Connection conn = DriverManager.getConnection(dbURL);
            PreparedStatement preparedStatement = conn.prepareStatement(findTag)) {
            preparedStatement.setString(1, tag);

            ResultSet result = preparedStatement.executeQuery();
    
            if (result.isClosed()) {
                return false;
            } else {
                return true;
            }

        } catch (SQLException e) {
            System.out.println("tagExists broken");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return false;
    }

    
    /** 
     * @param tag
     */
    public static void addTag(String tag) {
        String addTag = """
                INSERT INTO tags(tag) VALUES
                    (?)
                    """;
        try (Connection conn = DriverManager.getConnection(dbURL);
                PreparedStatement preparedStatement = conn.prepareStatement(addTag)) {
            preparedStatement.setString(1, tag);
            preparedStatement.executeUpdate();

            System.out.println("Added new tag");
        } catch (SQLException e) {
            System.out.println("addTag broken");

            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    
    /** 
     * @param content
     * @return boolean
     */
    public static boolean doesContentExist(String content) {
        String findContent = """
                SELECT content FROM content WHERE content = ?
                    """;
        try (Connection conn = DriverManager.getConnection(dbURL);
            PreparedStatement preparedStatement = conn.prepareStatement(findContent)) {
            preparedStatement.setString(1, content);

            ResultSet result = preparedStatement.executeQuery();
    
            if (result.isClosed()) {
                return false;
            } else {
                return true;
            }

        } catch (SQLException e) {
            System.out.println("does content exist broken");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return false;
    }

    
    /** 
     * @param content
     * @param date
     * @param address
     * @param id
     */
    public static void addContent(String content, String date, String address, int id) {
        String addContent = """
                INSERT INTO content(content, date, address, search_id) VALUES
                    (?, ?, ?, ?)
                    """;
        try (Connection conn = DriverManager.getConnection(dbURL);
                PreparedStatement preparedStatement = conn.prepareStatement(addContent)) {
            preparedStatement.setString(1, content);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, address);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();

            System.out.println("Added new content");
        } catch (SQLException e) {
            System.out.println("add content broken");

            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    
    /** 
     * @param content
     * @return String
     */
    public static String getContent(String content){
        String getContent = """
                SELECT content FROM content WHERE content = ?
                    """;

        try (Connection conn = DriverManager.getConnection(dbURL);
                PreparedStatement preparedStatement = conn.prepareStatement(getContent)) {
            preparedStatement.setString(1, content);
            ResultSet results = preparedStatement.executeQuery();


            if (results.isClosed()) {
                return null;
            } else {
                return results.getString("content");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return null;
    }

    
    /** 
     * @param tag
     * @param search
     * @return boolean
     */
    public static boolean doesSearchExist(String tag, String search) {
        String findContent = """
                SELECT id FROM search WHERE tag = ? AND search = ?
                    """;
        try (Connection conn = DriverManager.getConnection(dbURL);
            PreparedStatement preparedStatement = conn.prepareStatement(findContent)) {
            preparedStatement.setString(1, tag);
            preparedStatement.setString(2, search);

            ResultSet result = preparedStatement.executeQuery();
    
            if (result.isClosed()) {
                return false;
            } else {
                return true;
            }

        } catch (SQLException e) {
            System.out.println("tagExists broken");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return false;
    }

    
    /** 
     * @param tag
     * @param search
     */
    public static void addSearch(String tag, String search) {
        String addContent = """
                INSERT INTO search(tag, search) VALUES
                    (?, ?)
                    """;
        try (Connection conn = DriverManager.getConnection(dbURL);
                PreparedStatement preparedStatement = conn.prepareStatement(addContent)) {
            preparedStatement.setString(1, tag);
            preparedStatement.setString(2, search);
            preparedStatement.executeUpdate();

            System.out.println("Added search to search history");
        } catch (SQLException e) {
            System.out.println("addTag broken");

            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    
    /** 
     * @param tag
     * @param search
     * @return int
     */
    public static int getContentId(String tag, String search) {
        String findContent = """
                SELECT id FROM search WHERE tag = ? AND search = ?
                    """;
        try (Connection conn = DriverManager.getConnection(dbURL);
            PreparedStatement preparedStatement = conn.prepareStatement(findContent)) {
            preparedStatement.setString(1, tag);
            preparedStatement.setString(2, search);

            ResultSet result = preparedStatement.executeQuery();
    
            return result.getInt("id");

        } catch (SQLException e) {
            System.out.println("tagExists broken");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return 0;
    }

    
    /** 
     * @param id
     * @return List<Content>
     */
    public static List<Content> getSearchContent(int id){
        //need to update so that returns a list of content
        ArrayList<Content> searchResults = new ArrayList<>();

        String getContent = """
                SELECT content,date,address FROM content WHERE search_id = ?
                    """;

        try (Connection conn = DriverManager.getConnection(dbURL);
            PreparedStatement preparedStatement = conn.prepareStatement(getContent)) {
            preparedStatement.setInt(1, id);
            ResultSet results = preparedStatement.executeQuery();


            while (results.next()) {
                System.out.println();
                System.out.println("Printing content");
                System.out.println(results.getString("content") + results.getString("date") + results.getString("address"));

                String content = results.getString("content");
                String date = results.getString("date");
                String address = results.getString("address");
                searchResults.add(new Content(date, content, address));
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return searchResults;
    }

  /*  public static List<Content> convertStringToContentObject(List<String> content){
        Gson gson = new Gson();


    } */





    



}
