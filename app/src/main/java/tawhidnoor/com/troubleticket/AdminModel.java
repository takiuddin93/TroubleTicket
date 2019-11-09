package tawhidnoor.com.troubleticket;

public class AdminModel {

    public static final int IMAGE_TYPE = 1;
    public String id, vendor_id, vendor_phone, complain_id, complain_post_time;
    public int type;

    public AdminModel(int mtype, String id, String vendor_id, String vendor_phone, String complain_id, String complain_post_time) {
        this.type = mtype;
        this.id = id;
        this.vendor_id = vendor_id;
        this.vendor_phone = vendor_phone;
        this.complain_id = complain_id;
        this.complain_post_time = complain_post_time;
    }

    public String id() {
        return id;
    }

    public String vendor_id() {
        return vendor_id;
    }

    public String vendor_phone() {
        return vendor_phone;
    }

    public String complain_id() {
        return complain_id;
    }

    public String complain_post_time() {
        return complain_post_time;
    }
}
