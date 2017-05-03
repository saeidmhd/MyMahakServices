package ir.mahaksoft.mymahakservices.data.model;

/**
 * Created by Saeid.mhd-at-Gmail.com on 5/3/17.
 */

public class Message {

    private String Address;
    private String Body;
    private String Title;
    private Long Date;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Long getDate() {
        return Date;
    }

    public void setDate(Long date) {
        Date = date;
    }
}
