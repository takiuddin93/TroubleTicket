package tawhidnoor.com.troubleticket;

public class Model {

    public static final int IMAGE_TYPE = 1;
    public String id, name, phone, subject, message, resolved;
    public int type;

    public Model(int mtype, String id, String name, String phone, String subject, String message, String resolved) {
        this.type = mtype;
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.subject = subject;
        this.message = message;
        this.resolved = resolved;
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String phone() {
        return phone;
    }

    public String subject() {
        return subject;
    }

    public String message() {
        return message;
    }

    public String resolved() {
        return resolved;
    }

}
