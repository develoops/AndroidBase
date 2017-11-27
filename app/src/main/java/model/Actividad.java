package model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Date;
import java.util.List;

/**
 * Created by Alvaro on 2/16/15.
 */
@ParseClassName("Actividad")
public class Actividad extends ParseObject {


    public Date getStartDate() {
        return getDate("inicio");
    }

    public Date getEndDate() {
        return getDate("fin");
    }

    public String getTitle() {
        return getString("nombre");
    }


    public Boolean isActive() {
        return getBoolean("active");
    }

    public String getType() {
        return getString("tipo");
    }

    public ParseFile getParseFileV1() {
        return (ParseFile)get("imgPerfil");
    }


    public Lugar getPlace(){return (Lugar)get("lugar");}

    public String getDetails() {
        return getString("descripcion");
    }



    public static ParseQuery<Actividad> getQuery() {
        return ParseQuery.getQuery(Actividad.class);
    }
}


