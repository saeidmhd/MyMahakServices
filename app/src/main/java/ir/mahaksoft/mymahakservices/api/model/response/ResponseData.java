package ir.mahaksoft.mymahakservices.api.model.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * Created by admin1 on 4/9/17.
 */
@Root(name = "ValidateUserResponse", strict = false)
@Namespace(reference = "http://tempuri.org/")
public class ResponseData {

    @Element(name = "ValidateUserResult", required = false)
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


}

