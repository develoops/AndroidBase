package model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by Alvaro on 2/16/15.
 */
@ParseClassName("Emision")
public class Emision extends ParseObject {



    public ParseUser getEmisor() {
        return (ParseUser) get("emisor");
    }

    public String getTitulo() {
        return getString("titulo");
    }
    public String getSubtitulo() {
        return getString("subtitulo");
    }

    public Actividad getAct() {
        return (Actividad) get("actividad");
    }

    public String getMensajeTexto() {
        return getString("mensajeTexto");
    }

    public void setAct(Actividad actividad) {
        put("actividad", actividad);
    }


    public void setMensajeTexto(String texto) {
        put("mensajeTexto", texto);
    }

    public Number getLikes() {
        return getNumber("likes");
    }

    public void setLikes(Number likes){ put("likes", likes);}





    public static ParseQuery<Emision> getQuery() {
        return ParseQuery.getQuery(Emision.class);
    }
}
