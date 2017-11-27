package fragments;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import mc.sms.R;
import model.ActContAct;
import model.Actividad;
import model.Media;
import model.Org;
import model.Persona;
import model.PersonaRolAct;
import model.PersonaRolOrg;
import model.PreguntaEncuesta;

/**
 * Created by Alvaro on 2/25/15.
 */

public class SplashEventFragment extends Fragment {

    public static Actividad meetingApp;
    public static List <Actividad> actividades;
    public static View RootView;
    public static ParseImageView splash;
    public static List <Media> media;


    public static List<Org> organizaciones = new ArrayList<>();
    public static List<Persona> persons = new ArrayList<>();
    public static List<PersonaRolOrg> comite = new ArrayList<>();
    public static List<PreguntaEncuesta> preguntaEncuestas = new ArrayList<>();
    //public static List <String> ids = new ArrayList<>();


    public static SplashEventFragment newInstance(Actividad app) {

        // Instantiate a new fragment

        SplashEventFragment fragment = new SplashEventFragment();
        meetingApp = app;

        fragment.setRetainInstance(true);
        return fragment;

    }

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RootView = inflater.inflate(R.layout.splash_layout, container, false);
        /*
        for(Stand stand: meetingApp.getStands()){
            stand.getCompanyLogo().getDataInBackground();
            stand.getCompanyHeader().getDataInBackground();
        }*/

        //startLoading();

        final ProgressBar bar = (ProgressBar) RootView.findViewById(R.id.progressBar);

        ImageView splash_first = (ImageView) RootView.findViewById(R.id.splash_first);
        splash_first.setVisibility(View.VISIBLE);


//        if(meetingApp.getSplashMeeting()!=null){
//            ParseFile splashFile = meetingApp.getSplashMeeting().getParseFileV1();
//            if (splashFile != null) {
//                //Get singleton instance of ImageLoader
//                ImageLoader imageLoader = ImageLoader.getInstance();
//                //Load the image from the url into the ImageView.
//
//                imageLoader.displayImage(splashFile.getUrl(), splash);
//            } else {
//
//            }
//        }




                //Do something after 100ms
                //ParseQuery<ActContAct>

                //                        Log.i("ACTIVIDADESSSSS", String.valueOf(objects));


                ParseQuery<Media> queryMedia = ParseQuery.getQuery(Media.class);
                queryMedia.fromPin("media");
                queryMedia.findInBackground(new FindCallback<Media>() {
                    @Override
                    public void done(List<Media> objects, ParseException e) {
                        media=objects;
                    }
                });

                ParseQuery<PersonaRolOrg> personaRolOrgParseQuery = ParseQuery.getQuery(PersonaRolOrg.class);
                personaRolOrgParseQuery.fromLocalDatastore();
                personaRolOrgParseQuery.fromPin("comite");
                personaRolOrgParseQuery.whereEqualTo("congreso",meetingApp);
                personaRolOrgParseQuery.findInBackground(new FindCallback<PersonaRolOrg>() {
                    @Override
                    public void done(List<PersonaRolOrg> objects, ParseException e) {
                                if(objects!=null){
                                    comite=objects;
                                }

                    }
                });





                ParseQuery<PersonaRolAct> queryPersonaRolAct = ParseQuery.getQuery(PersonaRolAct.class);
                queryPersonaRolAct.include("persona");
                queryPersonaRolAct.include("congreso");
                queryPersonaRolAct.whereEqualTo("congreso",meetingApp);
                queryPersonaRolAct.fromPin("personasRol");
                queryPersonaRolAct.fromLocalDatastore();
                queryPersonaRolAct.findInBackground(new FindCallback<PersonaRolAct>() {
                    @Override
                    public void done(List<PersonaRolAct> objects, ParseException e) {
                        if(objects!=null){
                            List <Persona> personas = new ArrayList<>();
                            List <String> ids = new ArrayList<>();
                            for(PersonaRolAct personaRolAct:objects){
                                    Integer count = 0;
                                    ids.add(personaRolAct.getPerson().getObjectId());
                                    for(String id:ids){
                                        if(id.equals(personaRolAct.getPerson().getObjectId())){
                                                count++;
                                        }
                                    }
                                if(count==1){
                                    personas.add(personaRolAct.getPerson());
                                }
                            }

                            persons= personas;
                            //Log.i("PERSONASSSSSSs", String.valueOf(persons.size()));

                        }
                    }
                });


                ParseQuery<Org> queryOrg2 = ParseQuery.getQuery(Org.class);
                queryOrg2.whereEqualTo("tipo","patrocinador");
                queryOrg2.fromLocalDatastore();
                queryOrg2.fromPin("patrocinadores");
                queryOrg2.findInBackground(new FindCallback<Org>() {
                    @Override
                    public void done(List<Org> objects, ParseException e) {
                      /*  for(Org org:objects){
                            org.getimgFondo().getDataInBackground();
                            org.getimgPerfil().getDataInBackground();
                        }*/

                        organizaciones = objects;
                        Log.i("CANTIDADPATR2", String.valueOf(organizaciones.size()));
                    }
                });

                ParseQuery<PreguntaEncuesta> preguntaEncuestaParseQuery = ParseQuery.getQuery(PreguntaEncuesta.class);
                preguntaEncuestaParseQuery.fromLocalDatastore();
                preguntaEncuestaParseQuery.whereEqualTo("tipo","general");
                preguntaEncuestaParseQuery.addAscendingOrder("posicion");
                preguntaEncuestaParseQuery.findInBackground(new FindCallback<PreguntaEncuesta>() {
                    @Override
                    public void done(List<PreguntaEncuesta> objects, ParseException e) {
                        preguntaEncuestas=objects;

                    }
                });

                ParseQuery<ActContAct> queryContenido = ParseQuery.getQuery(ActContAct.class);
                queryContenido.include("contenido.lugar");
                queryContenido.include ("contenedor");
                queryContenido.fromLocalDatastore();
                queryContenido.fromPin("ActConAct"); //estaba uno antes
                queryContenido.whereEqualTo("contenedor", meetingApp);
                queryContenido.findInBackground(new FindCallback<ActContAct>() {
                    @Override
                    public void done(List<ActContAct> objects, ParseException e) {

                        List <Actividad> actividads = new ArrayList<>();
                            for(ActContAct actContAct:objects){
                                if(actContAct.getContenido()!=null){
                                    actividads.add(actContAct.getContenido());
                                }

                            }
                        Log.i("CANTIDADPATR2", String.valueOf(organizaciones.size()));
                        Log.i("CANTIDADSPEAKR", String.valueOf(persons.size()));
                        bar.setVisibility(View.INVISIBLE);
                        actividades=actividads;
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Fragment fragment = MeetingAppViewPagerFragment.newInstance(meetingApp,actividades, persons, organizaciones,comite,preguntaEncuestas,media);
                                final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.container, fragment);
                                ft.addToBackStack(null);
                                ft.commit();
                            }
                        }, 5000);



                    }
                });





        return RootView;
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onResume() {
        super.onResume();


//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //Do something after 100ms
//
//                Fragment fragment = MeetingAppViewPagerFragment.newInstance(meetingApp);
//                final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.container, fragment);
//                ft.addToBackStack(null);
//                ft.commit();
//            }
//        }, 100);


        /*
        View v = mTabHost.getTabWidget().getChildAt(0);
        v.setBackgroundResource(R.drawable.programa);
*/
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener

                    return true;
                }
                return false;
            }
        });

    }



}

