package amador.com.apislim;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by usuario on 9/02/17.
 */

public class Site implements Serializable {


    @SerializedName("name")
    private String name;
    @SerializedName("link")
    private String link;
    @SerializedName("email")
    private String email;

    public Site(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void serLink(String link) {
        this.link = link;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
