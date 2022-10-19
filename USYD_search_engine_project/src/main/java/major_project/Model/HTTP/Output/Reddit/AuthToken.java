package major_project.Model.HTTP.Output.Reddit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AuthToken {

@SerializedName("access_token")
@Expose
private String accessToken;
@SerializedName("expires_in")
@Expose
private Integer expiresIn;
@SerializedName("scope")
@Expose
private String scope;
@SerializedName("token_type")
@Expose
private String tokenType;

/**
* No args constructor for use in serialization
*
*/
public AuthToken() {
}

/**
*
* @param expiresIn
* @param scope
* @param accessToken
* @param tokenType
*/
public AuthToken(String accessToken, Integer expiresIn, String scope, String tokenType) {
super();
this.accessToken = accessToken;
this.expiresIn = expiresIn;
this.scope = scope;
this.tokenType = tokenType;
}

public String getAccessToken() {
return accessToken;
}

public void setAccessToken(String accessToken) {
this.accessToken = accessToken;
}

public Integer getExpiresIn() {
return expiresIn;
}

public void setExpiresIn(Integer expiresIn) {
this.expiresIn = expiresIn;
}

public String getScope() {
return scope;
}

public void setScope(String scope) {
this.scope = scope;
}

public String getTokenType() {
return tokenType;
}

public void setTokenType(String tokenType) {
this.tokenType = tokenType;
}

}
