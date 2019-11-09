package takiuddin93.com.troubleticket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Users {
    @SerializedName("response")
    @Expose
    private String Response;
    @SerializedName("id")
    @Expose
    private String ID;
    @SerializedName("name")
    private String Name;
    @SerializedName("phone")
    private String Phone;
    @SerializedName("email")
    private String Email;
    @SerializedName("role")
    private String Role;
    @SerializedName("password")
    private String Password;
    @SerializedName("token")
    private String Token;

    public Users(String response, String id, String name, String phone, String email, String role, String password, String token) {
        Response = response;
        ID = id;
        Name = name;
        Phone = phone;
        Email = email;
        Role = role;
        Password = password;
        Token = token;
    }

    public String getResponse() {
        return Response;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public String getPhone() {
        return Phone;
    }

    public String getEmail() {
        return Email;
    }

    public String getRole() {
        return Role;
    }

    public String getPassword() {
        return Password;
    }

    public String getToken() {
        return Token;
    }
}
