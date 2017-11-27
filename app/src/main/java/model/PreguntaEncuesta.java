package model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by alvaro on 04-12-14.
 */
@ParseClassName("PreguntaDeEncuesta")
public class PreguntaEncuesta extends ParseObject {





    public Persona getPerson() {
        return (Persona)get("persona");
    }
    public Encuesta getEncuesta(){return (Encuesta) get("encuesta");}
    public String getPreguntaTexto() {
        return getString("preguntaTexto");
    }

    public Number getPosicion() {
        return getNumber("posicion");
    }

    public String getTipo() {
        return getString("tipo");
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
    public static ParseQuery<PreguntaEncuesta> getQuery() {
        return ParseQuery.getQuery(PreguntaEncuesta.class);
    }

}