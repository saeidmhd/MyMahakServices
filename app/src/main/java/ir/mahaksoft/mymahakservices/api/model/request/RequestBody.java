package ir.mahaksoft.mymahakservices.api.model.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by admin1 on 4/9/17.
 */
@Root(name = "soap12:Body", strict = false)
public class RequestBody {

    @Element(name = "ValidateUser",required = false)
    private RequestData RequestData;

    public RequestData getRequestData() {
        return RequestData;
    }

    public void setRequestData(RequestData RequestData) {
        this.RequestData = RequestData;
    }
}
