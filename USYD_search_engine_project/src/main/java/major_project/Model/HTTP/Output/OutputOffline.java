package major_project.Model.HTTP.Output;

public class OutputOffline implements OutputProtocol{

    
    /** 
     * @param paste
     * @return String
     */
    @Override
    public String createPaste(String paste) {
        // TODO Auto-generated method stub
        return "https://pastebin.com/";
    }
    
    
}
