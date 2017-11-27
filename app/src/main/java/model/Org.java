package model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by Alvaro on 2/16/15.
 */
@ParseClassName("Org")
public class Org extends ParseObject {


    public String getDetails() {
        return getString("descripcion");
    }

    public Lugar getLocation(){return (Lugar)get("location");}

    public String getName() {
        return getString("nombre");
    }

    public ParseFile getimgPerfil() {
        return (ParseFile)get("imgPerfil");
    }
    public ParseFile getimgFondo() {
        return (ParseFile)get("imgFondo");
    }

    public String getMail() {
        return getString("email");
    }

    public String getPhone() {
        return getString("telefono");
    }

    public String getType() {
        return getString("type");
    }
    public String getWeb() {
        return getString("sitioWeb");
    }






    /*

    public String getFacebook() {
        return getString("facebook");
    }
    public Image getHeader() {
        return (Image)get("header");
    }
    public String getLinkedIn() {
        return getString("linkedin");
    }
    public Image getLogo() {
        return (Image)get("logo");
    }
    public Lugar getPlace() {
        return (Lugar)get("lugar");
    }
    public String getMail() {
        return getString("mail");
    }
    public String getName() {
        return getString("nombre");
    }
    public List<Actor> getCharacters() {
        return getList("personajes");
    }

    public String getPhone() {
        return getString("telefono");
    }
    public String getKind() {
        return getString("tipo");
    }
    public String getTwitter() {
        return getString("twitter");
    }
    public String geturl() {
        return getString("urlWeb");
    }

    */

    public static ParseQuery<Org> getQuery() {
        return ParseQuery.getQuery(Org.class);
    }
}
