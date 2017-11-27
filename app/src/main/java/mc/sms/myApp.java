package mc.sms;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.parse.Parse;
//import com.parse.ParseCrashReporting;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseLiveQueryClient;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONObject;

import java.net.URI;

import model.ActContAct;
import model.ActFavUser;
import model.Actividad;
import model.Emision;
import model.Encuesta;
import model.Info;
import model.Lugar;
import model.Media;
import model.Org;
import model.Persona;
import model.PersonaRolAct;
import model.PersonaRolOrg;
import model.PreguntaEncuesta;
import model.Rating;
import model.RespuestaEncuesta;


/**
 * Created by Alvaro on 2/18/15.
 */
public class myApp extends Application {

    private static Context mContext;
    private Boolean firstTime = null;
    private Boolean off = null;

    @Override
    public void onCreate() {
        mContext = this;

        super.onCreate();


        ParseObject.registerSubclass(ActContAct.class);
        ParseObject.registerSubclass(ActFavUser.class);
        ParseObject.registerSubclass(Actividad.class);
        ParseObject.registerSubclass(Emision.class);
        ParseObject.registerSubclass(Encuesta.class);
        ParseObject.registerSubclass(Info.class);
        ParseObject.registerSubclass(Lugar.class);
        ParseObject.registerSubclass(Media.class);
        ParseObject.registerSubclass(Org.class);
        ParseObject.registerSubclass(Persona.class);
        ParseObject.registerSubclass(PersonaRolAct.class);
        ParseObject.registerSubclass(PreguntaEncuesta.class);
        ParseObject.registerSubclass(PersonaRolOrg.class);
        ParseObject.registerSubclass(Rating.class);
        ParseObject.registerSubclass(RespuestaEncuesta.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("ClUXXsCBfTmS6E9zxXKck1oX4hYSC2pyHarv4U8E")
                .clientKey("IZ08jbbJwcLnDlEh79edrsxNIcU0iM9FG6Uwpj92")
                .server("https://parseapi.back4app.com/").enableLocalDataStore().build()

        );
       // ParseLiveQueryClient parseLiveQueryClient = ParseLiveQueryClient.Factory.getClient(new URI("wss://smsdemo.back4app.io"));


      /*  LiveQueryClient.init("wss://smsdemo.back4app.io", "ClUXXsCBfTmS6E9zxXKck1oX4hYSC2pyHarv4U8E", true);
        LiveQueryClient.connect();

        final Subscription sub = new BaseQuery.Builder("Emision")
                .addField("actividad")
                .addField("mensajeTexto")
                .addField("likes")
                .build()
                .subscribe();

        sub.on(LiveQueryEvent.CREATE, new OnListener() {
            @Override
            public void on(final JSONObject object) {
                Log.e("CREATED", object.toString());
            }
        });*/

        ParseInstallation parseInstallation = ParseInstallation.getCurrentInstallation();
        parseInstallation.put("GCMSenderId",getString(R.string.google_project_number));

        parseInstallation.saveInBackground();


        ParseUser.enableAutomaticUser();










        //ParseObject.registerSubclass(File.class);

        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    Log.i("USUARIO REGISTRADO", "YEI");
                }
                else {
                    Log.i("CAPTCHA","CAPTCHA");
                }
            }
        });




        ParsePush.subscribeInBackground("sms", new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if (e != null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }

            }
        });

        //Create image options.
        DisplayImageOptions options = new DisplayImageOptions.Builder()

                .cacheInMemory(true)
                .cacheOnDisk(true)

                .imageScaleType(ImageScaleType.EXACTLY)
                .build();

        //Create a config with those options.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext()).threadPriority(Thread.MAX_PRIORITY)

                .memoryCache(new WeakMemoryCache())

                .denyCacheImageMultipleSizesInMemory().threadPoolSize(5)
                .defaultDisplayImageOptions(options)
                .tasksProcessingOrder(QueueProcessingType.LIFO)// .enableLogging()
                .build();


        ImageLoader.getInstance().init(config);




    }

    public void setBooleanAppTrue (String objectId){
        SharedPreferences prefs = mContext.getSharedPreferences("Descargado", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(objectId,true);
        editor.commit();
        return;
    }



    public void setPreguntaBooleanTrue (String objectId){
        SharedPreferences prefs = mContext.getSharedPreferences("Pregunta", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(objectId,true);
        editor.commit();
        return;
    }

    public void setPreguntaBooleanFalse (String objectId){
        SharedPreferences prefs = mContext.getSharedPreferences("Pregunta", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(objectId,false);
        editor.commit();
        return;
    }

    public boolean getPreguntaBoolean (String objectId){
        SharedPreferences prefs = mContext.getSharedPreferences("Pregunta", 0);
        boolean rb0 = prefs.getBoolean(objectId,false);
        return rb0;
    }

    public void setFavoriteAppTrue (String objectId){
        SharedPreferences prefs = mContext.getSharedPreferences("Favorite", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(objectId,true);
        editor.commit();
        return;
    }

    public void setEncuestaTrue (String objectId){
        SharedPreferences prefs = mContext.getSharedPreferences("Encuesta", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(objectId,true);
        editor.commit();
        return;
    }

    public boolean getEncuestaTrue (String objectId){
        SharedPreferences prefs = mContext.getSharedPreferences("Encuesta", 0);
        boolean rb0 = prefs.getBoolean(objectId,false);
        return rb0;
    }

    public void setFavoriteAppFalse (String objectId){
        SharedPreferences prefs = mContext.getSharedPreferences("Favorite", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(objectId,false);
        editor.commit();
        return;
    }

    public boolean getFavoriteApp (String objectId){
        SharedPreferences prefs = mContext.getSharedPreferences("Favorite", 0);
        boolean rb0 = prefs.getBoolean(objectId,false);
        return rb0;
    }

    public void setBooleanAppFalse (String objectId){
        SharedPreferences prefs = mContext.getSharedPreferences("Descargado", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(objectId,false);
        editor.commit();
        return;
    }

    public boolean getBooleanApp (String objectId){
        SharedPreferences prefs = mContext.getSharedPreferences("Descargado", 0);
        boolean rb0 = prefs.getBoolean(objectId,false);
        return rb0;
    }

    public boolean checkConnection() {
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) this.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // No sólo wifi, también GPRS
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        // este bucle debería no ser tan ñapa
        for (int i = 0; i < redes.length; i++) {
            // ¿Tenemos conexión? ponemos a true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                bConectado = true;
            }
        }
        return bConectado;
    }



    public boolean isFirstTime() {
        if (firstTime == null) {
            SharedPreferences mPreferences = this.getSharedPreferences("first_time", Context.MODE_PRIVATE);
            firstTime = mPreferences.getBoolean("firstTime", true);
            if (firstTime) {
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putBoolean("firstTime", false);
                editor.commit();

            }

        }
        return firstTime;
    }

    public boolean isFirstPass() {
        if (firstTime == null) {
            SharedPreferences mPreferences = this.getSharedPreferences("first_pass", Context.MODE_PRIVATE);
            firstTime = mPreferences.getBoolean("first_pass", true);

        }
        return firstTime;
    }

    public void setSecondPass(){
        SharedPreferences mPreferences = this.getSharedPreferences("first_pass", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean("first_pass",false);
        editor.commit();
        return;
    }

    public void setFirstTime(){
        SharedPreferences mPreferences = this.getSharedPreferences("first_time", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean("firstTime",true);
        editor.commit();
        return;
    }

    public void usuarioAsignado(){
        SharedPreferences mPreferences = this.getSharedPreferences("usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean("usuario",true);
        editor.commit();
        return;
    }

    public boolean getUsuarioAsignado() {
        SharedPreferences mPreferences = this.getSharedPreferences("usuario", Context.MODE_PRIVATE);
        Boolean setFromDetail = mPreferences.getBoolean("usuario", false);
        return setFromDetail;
    }

    public void setSecondTime(){
        SharedPreferences mPreferences = this.getSharedPreferences("first_time", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean("firstTime",false);
        editor.commit();
        return;
    }

    public void ingresoNews(){
        SharedPreferences mPreferences = this.getSharedPreferences("news", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean("news",true);
        editor.commit();
        return;
    }

    public void saleNews(){
        SharedPreferences mPreferences = this.getSharedPreferences("news", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean("news",false);
        editor.commit();
        return;
    }

    public void setFromDetail(boolean fromDetail){
        SharedPreferences mPreferences = this.getSharedPreferences("fromdetail", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean("fromdetail",fromDetail);
        editor.commit();
        return;
    }

    public boolean getFromDetail() {
        SharedPreferences mPreferences = this.getSharedPreferences("fromdetail", Context.MODE_PRIVATE);
        Boolean setFromDetail = mPreferences.getBoolean("fromdetail", false);
        return setFromDetail;
    }

    public boolean getNews() {

        SharedPreferences mPreferences = this.getSharedPreferences("news", Context.MODE_PRIVATE);
        off = mPreferences.getBoolean("news", true);
        return off;
    }

    public void setNews (int size){
        SharedPreferences prefs = this.getSharedPreferences("newsview", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("newsviews", size);
        editor.commit();
        return;
    }

    public int getintNews(){
        SharedPreferences prefs = this.getSharedPreferences("newsview", Context.MODE_PRIVATE);

        int newsviews = prefs.getInt("newsviews",0);
        return newsviews;
    }
    public void setNow(boolean isNow){
        SharedPreferences mPreferences = this.getSharedPreferences("now", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean("now",isNow);
        editor.commit();
        return;
    }

    public boolean getNow() {
        SharedPreferences mPreferences = this.getSharedPreferences("now", Context.MODE_PRIVATE);
        Boolean isNow = mPreferences.getBoolean("now", false);
        return isNow;
    }
    public void setNowClickable(boolean isNow){
        SharedPreferences mPreferences = this.getSharedPreferences("now", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean("nowClickable",isNow);
        editor.commit();
        return;
    }

    public boolean getNowClickable() {
        SharedPreferences mPreferences = this.getSharedPreferences("now", Context.MODE_PRIVATE);
        Boolean isNow = mPreferences.getBoolean("nowClickable", false);
        return isNow;
    }

}