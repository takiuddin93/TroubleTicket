package tawhidnoor.com.troubleticket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Unresolved {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("vendor_id")
    @Expose
    private String vendor_id;
    @SerializedName("vendor_phone")
    @Expose
    private String vendor_phone;
    @SerializedName("complain_id")
    @Expose
    private String complain_id;
    @SerializedName("complain_post_time")
    @Expose
    private String complain_post_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
    }

    public String getVendor_phone() {
        return vendor_phone;
    }

    public void setVendor_phone(String vendor_phone) {
        this.vendor_phone = vendor_phone;
    }

    public String getComplain_id() {
        return complain_id;
    }

    public void setComplain_id(String complain_id) {
        this.complain_id = complain_id;
    }


    public String getComplain_post_time() {
        return complain_post_time;
    }

    public void setComplain_post_time(String complain_post_time) {
        this.complain_post_time = complain_post_time;
    }

}