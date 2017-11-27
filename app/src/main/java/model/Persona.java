package model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by alvaro on 04-12-14.
 */
@ParseClassName("Persona")
public class Persona extends ParseObject {

    public String getBio() {
        return getString("descripcion");
    }

    public String getFirstName() {
        return getString("primerNombre");
    }
    public String getSecondName() {
        return getString("segundoNombre");
    }

    public String getSecondLastName() {
        return getString("segundoApellido");
    }




    public String getLastName() {
        return getString("primerApellido");
    }
    public String getMail() {
        return getString("mail");
    }
    public String getPhone() {
        return getString("phone");
    }
    public Lugar getPlace() {
        return (Lugar)get("pais");
    }
    public ParseFile getImage() {
        return (ParseFile)get("ImgPerfil");
    }
    public String getSalutation() {
        return getString("preNombre");
    }

    public Integer getSortingAux() {
        return getInt("sortingAux");
    }
    public String getTwitter() {
        return getString("twitter");
    }




  /*
    public List<File> getFiles() {
        return getList("archivos");
    }

    public Integer getOrder() {
        return getInt("orden");
    }

    public List<Actor> getCharacters() {
        return getList("personajes");
    }
    public Company getInstitution() {
        return (Company)get("institucion");
    }

    public Lugar getPlace() {
        return (Lugar)get("lugar");
    }

*/
    public static ParseQuery<Persona> getQuery() {
        return ParseQuery.getQuery(Persona.class);
    }

}