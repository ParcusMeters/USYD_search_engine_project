package major_project.Model.HTTP.Input.tag_search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tag {

@SerializedName("id")
@Expose
private String id;
@SerializedName("type")
@Expose
private String type;
@SerializedName("sectionId")
@Expose
private String sectionId;
@SerializedName("sectionName")
@Expose
private String sectionName;
@SerializedName("webTitle")
@Expose
private String webTitle;
@SerializedName("webUrl")
@Expose
private String webUrl;
@SerializedName("apiUrl")
@Expose
private String apiUrl;
@SerializedName("description")
@Expose
private String description;

/**
* No args constructor for use in serialization
*
*/
public Tag() {
}

/**
*
* @param sectionName
* @param apiUrl
* @param webUrl
* @param webTitle
* @param description
* @param id
* @param sectionId
* @param type
*/
public Tag(String id, String type, String sectionId, String sectionName, String webTitle, String webUrl, String apiUrl, String description) {
super();
this.id = id;
this.type = type;
this.sectionId = sectionId;
this.sectionName = sectionName;
this.webTitle = webTitle;
this.webUrl = webUrl;
this.apiUrl = apiUrl;
this.description = description;
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
public String getType() {
return type;
}


/** 
 * @param type
 */
public void setType(String type) {
this.type = type;
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


/** 
 * @return String
 */
public String getDescription() {
return description;
}


/** 
 * @param description
 */
public void setDescription(String description) {
this.description = description;
}

}