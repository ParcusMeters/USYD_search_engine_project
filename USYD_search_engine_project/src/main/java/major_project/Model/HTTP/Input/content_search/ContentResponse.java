package major_project.Model.HTTP.Input.content_search;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContentResponse {

@SerializedName("status")
@Expose
private String status;
@SerializedName("userTier")
@Expose
private String userTier;
@SerializedName("total")
@Expose
private Integer total;
@SerializedName("startIndex")
@Expose
private Integer startIndex;
@SerializedName("pageSize")
@Expose
private Integer pageSize;
@SerializedName("currentPage")
@Expose
private Integer currentPage;
@SerializedName("pages")
@Expose
private Integer pages;
@SerializedName("orderBy")
@Expose
private String orderBy;
@SerializedName("results")
@Expose
private List<Content> results = null;

/**
* No args constructor for use in serialization
*
*/
public ContentResponse() {
}

/**
*
* @param userTier
* @param total
* @param startIndex
* @param pages
* @param pageSize
* @param orderBy
* @param currentPage
* @param results
* @param status
*/
public ContentResponse(String status, String userTier, Integer total, Integer startIndex, Integer pageSize, Integer currentPage, Integer pages, String orderBy, List<Content> results) {
super();
this.status = status;
this.userTier = userTier;
this.total = total;
this.startIndex = startIndex;
this.pageSize = pageSize;
this.currentPage = currentPage;
this.pages = pages;
this.orderBy = orderBy;
this.results = results;
}


/** 
 * @return String
 */
public String getStatus() {
return status;
}


/** 
 * @param status
 */
public void setStatus(String status) {
this.status = status;
}


/** 
 * @return String
 */
public String getUserTier() {
return userTier;
}


/** 
 * @param userTier
 */
public void setUserTier(String userTier) {
this.userTier = userTier;
}


/** 
 * @return Integer
 */
public Integer getTotal() {
return total;
}


/** 
 * @param total
 */
public void setTotal(Integer total) {
this.total = total;
}


/** 
 * @return Integer
 */
public Integer getStartIndex() {
return startIndex;
}


/** 
 * @param startIndex
 */
public void setStartIndex(Integer startIndex) {
this.startIndex = startIndex;
}


/** 
 * @return Integer
 */
public Integer getPageSize() {
return pageSize;
}


/** 
 * @param pageSize
 */
public void setPageSize(Integer pageSize) {
this.pageSize = pageSize;
}


/** 
 * @return Integer
 */
public Integer getCurrentPage() {
return currentPage;
}


/** 
 * @param currentPage
 */
public void setCurrentPage(Integer currentPage) {
this.currentPage = currentPage;
}


/** 
 * @return Integer
 */
public Integer getPages() {
return pages;
}


/** 
 * @param pages
 */
public void setPages(Integer pages) {
this.pages = pages;
}


/** 
 * @return String
 */
public String getOrderBy() {
return orderBy;
}


/** 
 * @param orderBy
 */
public void setOrderBy(String orderBy) {
this.orderBy = orderBy;
}


/** 
 * @return List<Content>
 */
public List<Content> getResults() {
return results;
}


/** 
 * @param results
 */
public void setResults(List<Content> results) {
this.results = results;
}

}
