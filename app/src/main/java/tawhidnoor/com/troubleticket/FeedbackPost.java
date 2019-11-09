package tawhidnoor.com.troubleticket;

import com.google.gson.annotations.SerializedName;

public class FeedbackPost {
    @SerializedName("response")
    private String Response;
    @SerializedName("vendorID")
    private String VendorID;
    @SerializedName("complainID")
    private String ComplainID;
    @SerializedName("feedback")
    private String Feedback;
    @SerializedName("resolvedState")
    private String ResolvedState;

    public FeedbackPost(String response, String vendorID, String complainID, String feedback,  String resolvedState) {
        Response = response;
        VendorID = vendorID;
        ComplainID = complainID;
        Feedback = feedback;
        ResolvedState = resolvedState;
    }

    public String getResponse() {
        return Response;
    }

    public String getVendorID() {
        return VendorID;
    }

    public String getComplainID() {
        return ComplainID;
    }

    public String getFeedback() {
        return Feedback;
    }

    public String getResolvedState() {
        return ResolvedState;
    }
}
