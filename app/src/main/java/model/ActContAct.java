package model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Date;

/**
 * Created by Alvaro on 2/16/15.
 */
@ParseClassName("ActContAct")
public class ActContAct extends ParseObject {


    public Actividad getContenedor() {
        return (Actividad) get("contenedor");
    }

    public Actividad getContenido() {
        return (Actividad) get("contenido");
    }




    public static ParseQuery<ActContAct> getQuery() {
        return ParseQuery.getQuery(ActContAct.class);
    }
}


