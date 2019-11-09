package takiuddin93.com.troubleticket;

import com.google.gson.annotations.SerializedName;

public class ComplainsPost {
    @SerializedName("response")
    private String Response;
    @SerializedName("phone")
    private String Phone;
    @SerializedName("subject")
    private String Subject;
    @SerializedName("message")
    private String Message;

    public ComplainsPost(String response, String phone, String subject, String message) {
        Response = response;
        Phone = phone;
        Subject = subject;
        Message = message;
    }

    public String getResponse() {
        return Response;
    }

    public String getPhone() {
        return Phone;
    }

    public String getSubject() {
        return Subject;
    }

    public String getMessage() {
        return Message;
    }
}
