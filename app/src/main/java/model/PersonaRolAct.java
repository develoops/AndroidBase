package model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by alvaro on 04-12-14.
 */
@ParseClassName("PersonaRolAct")
public class PersonaRolAct extends ParseObject {





    public Persona getPerson() {
        return (Persona)get("persona");
    }
    public Actividad getAct(){return (Actividad) get("act");}
    public Actividad getCong(){return (Actividad) get("congreso");}
    public String getRol() {
        return getString("rol");
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
    public static ParseQuery<PersonaRolAct> getQuery() {
        return ParseQuery.getQuery(PersonaRolAct.class);
    }

}