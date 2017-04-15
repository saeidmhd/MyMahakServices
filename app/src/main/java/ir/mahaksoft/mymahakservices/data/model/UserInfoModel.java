
package ir.mahaksoft.mymahakservices.data.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfoModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("UserInfo")
    @Expose
    private List<UserInfo> userInfo = null;
    @SerializedName("PackInfo")
    @Expose
    private List<PackInfo> packInfo = null;
    @SerializedName("Token")
    @Expose
    private String token;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<UserInfo> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(List<UserInfo> userInfo) {
        this.userInfo = userInfo;
    }

    public List<PackInfo> getPackInfo() {
        return packInfo;
    }

    public void setPackInfo(List<PackInfo> packInfo) {
        this.packInfo = packInfo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
