package model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by Alvaro on 2/16/15.
 */
@ParseClassName("Media")
public class Media extends ParseObject {



    public ParseFile getArchivo() {
        return (ParseFile)get("archivo");
    }

    public String getNombre() {
        return getString("nombre");
    }

    public String getTipo() {
        return getString("tipo");
    }
    public String getUrl() {
        return getString("url");
    }

    public String getDescripcion() {
        return getString("descripcion");
    }








    public static ParseQuery<Media> getQuery() {
        return ParseQuery.getQuery(Media.class);
    }
}
