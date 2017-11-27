package model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by alvaro on 04-12-14.
 */
@ParseClassName("PersonaRolOrg")
public class PersonaRolOrg extends ParseObject {





    public Persona getPerson() {
        return (Persona)get("persona");
    }
    public Org getOrg(){return (Org)get("org");}
    public String getRol() {
        return getString("rol");
    }
    public String getTipo() {
        return getString("tipo");
    }

    public String getBios() {
        return getString("bios");
    }

    public String getOrder() {
        return getString("order");
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
    public static ParseQuery<PersonaRolOrg> getQuery() {
        return ParseQuery.getQuery(PersonaRolOrg.class);
    }

}