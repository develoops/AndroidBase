package model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by Alvaro on 2/16/15.
 */
@ParseClassName("ActFavUser")
public class ActFavUser extends ParseObject {


    public Actividad getActividad() {
        return (Actividad) get("actividad");
    }

    public Actividad getCongreso() {
        return (Actividad) get("congreso");
    }







    public static ParseQuery<ActFavUser> getQuery() {
        return ParseQuery.getQuery(ActFavUser.class);
    }
}


