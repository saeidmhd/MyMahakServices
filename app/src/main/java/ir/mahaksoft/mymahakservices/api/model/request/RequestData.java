package ir.mahaksoft.mymahakservices.api.model.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * Created by admin1 on 4/9/17.
 */
@Root(name = "ValidateUser", strict = false)
@Namespace(reference = "http://tempuri.org/")
public class RequestData {

    @Element(name = "AppSign", required = false )
    private String AppSign;

    @Element(name = "jsonString", required = false )
    private String jsonString;



    public String getAppSign() {
        return AppSign;
    }

    public void setAppSign(String AppSign) {
        this.AppSign = AppSign;
    }


    public String getjsonString() {
        return jsonString;
    }

    public void setjsonString(String jsonString) {
        this.jsonString = jsonString;
    }
}
