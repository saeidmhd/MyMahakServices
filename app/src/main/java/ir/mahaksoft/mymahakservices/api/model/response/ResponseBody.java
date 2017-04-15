package ir.mahaksoft.mymahakservices.api.model.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by admin1 on 4/9/17.
 */
@Root(name = "Body", strict = false)
public class ResponseBody {

    @Element(name = "ValidateUserResponse", required = false)
    private ResponseData data;

    public ResponseData getData() {
        return data;
    }

    public void setData(ResponseData data) {
        this.data = data;
    }
}



