package model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by Alvaro on 2/16/15.
 */

@ParseClassName("Lugar")
public class Lugar extends ParseObject {



    public String getDescription() {
        return getString("descripcion");
    }

    public ParseGeoPoint getGeoPoint() {
        return getParseGeoPoint("geoPoint");
    }

    public String getName() {
        return getString("nombre");
    }

    public ParseFile getImgPerfil() {
        return (ParseFile)get("imgPerfil");
    }


    public String getType() {
        return getString("tipo");
    }


/*
    public String getCity() {
        return getString("city");
    }
    public String getDescription() {
        return getString("descriptionPlace");
    }
    public Image getMap() {
        return (Image)get("map");
    }
    public String getCountry() {
        return getString("country");
    }
    public String getVenue() {
        return getString("venue");
    }
    public String getRoom() {
        return getString("room");
    }
    public String getHeadQuarter() {
        return getString("name");
    }
    public String getKind() {
        return getString("tipo");
    }
*/
    public static ParseQuery<Lugar> getQuery() {
        return ParseQuery.getQuery(Lugar.class);
    }
}
