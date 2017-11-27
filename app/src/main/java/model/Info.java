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
@ParseClassName("Info")
public class Info extends ParseObject {


    public String getContent() {
        return getString("cuerpotxt");
    }


    public ParseFile getImage() {
        return (ParseFile)get("imgPerfil");
    }

    public String getTitle() {
        return getString("titulo");
    }





    public static ParseQuery<Info> getQuery() {
        return ParseQuery.getQuery(Info.class);
    }
}
