package model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by alvaro on 04-12-14.
 */
@ParseClassName("Encuesta")
public class Encuesta extends ParseObject {







    public Actividad getAct(){return (Actividad) get("actividad");}

    public String getTexto() {
        return getString("texto");
    }

    public Number getPosicion() {
        return getNumber("posicion");
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
    public static ParseQuery<Encuesta> getQuery() {
        return ParseQuery.getQuery(Encuesta.class);
    }

}