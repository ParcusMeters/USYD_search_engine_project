package major_project.Model.HTTP.Output.pastebin_post;

public class NewPaste {
   private String api_dev_key;
   private String api_option;
   private String api_paste_code;

    public NewPaste(String api_dev_key, String api_option, String api_paste_code) {
        this.api_dev_key = api_dev_key;
        this.api_option = api_option;
        this.api_paste_code = api_paste_code;
    }

    
    /** 
     * @return String
     */
    public String getApi_dev_key(){
        return this.api_dev_key;
    }

    
    /** 
     * @return String
     */
    public String getApi_option(){
        return this.api_option;
    }

    
    /** 
     * @return String
     */
    public String getApi_paste_code(){
        return this.api_paste_code;
    }


}

