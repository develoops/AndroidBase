package model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by alvaro on 04-12-14.
 */
@ParseClassName("RespuestaEncuesta")
public class RespuestaEncuesta extends ParseObject {






    public void setPregunta(PreguntaEncuesta pregunta) {
        put("pregunta", pregunta);
    }

    public void setUser(ParseUser user) {
        put("user", user);
    }

    public void setEncuesta(Encuesta encuesta) {
        put("encuesta", encuesta);
    }

    public void setValoracion(Number valoracion) {
        put("valoracion", valoracion);
    }

    public void setEvent(Actividad actividad) {
        put("evento", actividad);
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
    public static ParseQuery<RespuestaEncuesta> getQuery() {
        return ParseQuery.getQuery(RespuestaEncuesta.class);
    }

}