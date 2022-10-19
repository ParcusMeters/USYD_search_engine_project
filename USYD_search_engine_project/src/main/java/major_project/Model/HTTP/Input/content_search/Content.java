package major_project.Model.HTTP.Input.content_search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Content {

@SerializedName("id")
@Expose
private String id;
@SerializedName("sectionId")
@Expose
private String sectionId;
@SerializedName("sectionName")
@Expose
private String sectionName;
@SerializedName("webPublicationDate")
@Expose
private String webPublicationDate;
@SerializedName("webTitle")
@Expose
private String webTitle;
@SerializedName("webUrl")
@Expose
private String webUrl;
@SerializedName("apiUrl")
@Expose
private String apiUrl;

/**
* No args constructor for use in serialization
*
*/
public Content() {
}

/**
*
* @param sectionName
* @param webPublicationDate
* @param apiUrl
* @param webUrl
* @param webTitle
* @param id
* @param sectionId
*/
public Content(String id, String sectionId, String sectionName, String webPublicationDate, String webTitle, String webUrl, String apiUrl) {
super();
this.id = id;
this.sectionId = sectionId;
this.sectionName = sectionName;
this.webPublicationDate = webPublicationDate;
this.webTitle = webTitle;
this.webUrl = webUrl;
this.apiUrl = apiUrl;
}

public Content(String webPublicationDate, String webTitle, String webUrl){
    this.webPublicationDate = webPublicationDate;
    this.webTitle = webTitle;
    this.webUrl = webUrl;
}


/** 
 * @return String
 */
public String getId() {
return id;
}


/** 
 * @param id
 */
public void setId(String id) {
this.id = id;
}


/** 
 * @return String
 */
public String getSectionId() {
return sectionId;
}


/** 
 * @param sectionId
 */
public void setSectionId(String sectionId) {
this.sectionId = sectionId;
}


/** 
 * @return String
 */
public String getSectionName() {
return sectionName;
}


/** 
 * @param sectionName
 */
public void setSectionName(String sectionName) {
this.sectionName = sectionName;
}


/** 
 * @return String
 */
public String getWebPublicationDate() {
return webPublicationDate;
}


/** 
 * @param webPublicationDate
 */
public void setWebPublicationDate(String webPublicationDate) {
this.webPublicationDate = webPublicationDate;
}


/** 
 * @return String
 */
public String getWebTitle() {
return webTitle;
}


/** 
 * @param webTitle
 */
public void setWebTitle(String webTitle) {
this.webTitle = webTitle;
}


/** 
 * @return String
 */
public String getWebUrl() {
return webUrl;
}


/** 
 * @param webUrl
 */
public void setWebUrl(String webUrl) {
this.webUrl = webUrl;
}


/** 
 * @return String
 */
public String getApiUrl() {
return apiUrl;
}


/** 
 * @param apiUrl
 */
public void setApiUrl(String apiUrl) {
this.apiUrl = apiUrl;
}

}