package fragments;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import adapters.DirectiveListViewAdapter2;
import adapters.GridDocumentsAdapter;
import adapters.HetpinProgramListViewAdapter;
import mc.sms.BroadcastAlarma;
import mc.sms.R;
import mc.sms.myApp;
import model.ActContAct;
import model.ActFavUser;
import model.Actividad;
import model.Persona;
import model.PersonaRolAct;
import model.Rating;

/**
 * Created by Alvaro on 3/1/15.
 */
public class EventDetailFragment2 extends Fragment {

    public static Actividad selectedEvent;
    ListView listview,speakers_listview,events_listview,fileslistview;
    HetpinProgramListViewAdapter adapter,adapter2;
    private RatingBar ratingBar;
    RelativeLayout footer;
    DirectiveListViewAdapter2 speaker_adapter;
    Button makeFavourite,rate,ask,map,resumenes;
    public static List <Actividad> events, eventosAnidados, eventsSpeaker;
    public static List <Actividad> eventosSpeaker = new ArrayList<>();
    public static List <Persona> speakers = new ArrayList<>();
    public static Persona personars;
    public static Actividad evento;
    public static List <PersonaRolAct> roles,roles2 = new ArrayList<>();
   // public static Integer counter=0;
    public myApp myapp;
    public static Actividad mApp;
    GridDocumentsAdapter docsadapter;

    ParseImageView header;
    private PendingIntent pendingIntent;

    public static EventDetailFragment2 newInstance(Actividad event, Actividad meetingApp, List<PersonaRolAct> rols, List<Actividad> anidados) {

        // Instantiate a new fragment

        EventDetailFragment2 fragment = new EventDetailFragment2();

        selectedEvent = event;
        mApp = meetingApp; //para Alfonso
        eventosAnidados=anidados;
        roles= rols;
        fragment.setRetainInstance(true);
        return fragment;

    }

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);
        //this._id = getArguments().getInt(INDEX);

        //((ActionBarActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.left);
        setHasOptionsMenu(true);
        Calendar cal = Calendar.getInstance();
        Log.i("DATe1", String.valueOf(selectedEvent.getStartDate().getTime()));
        Log.i("CAL1", String.valueOf(cal.getTimeInMillis()));


        events = (List<Actividad>) ParseUser.getCurrentUser().get("favoritos");


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retain this fragment across configuration changes.
        setRetainInstance(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View RootView = inflater.inflate(R.layout.eventdetail_layout, container , false);
        setHasOptionsMenu(true);

        makeFavourite = (Button) RootView.findViewById(R.id.makefavourite);
        rate = (Button) RootView.findViewById(R.id.rate);
        ask = (Button) RootView.findViewById(R.id.ask);
        map = (Button) RootView.findViewById(R.id.map);
        //checkin = (Button) RootView.findViewById(R.id.checkin);
        resumenes = (Button) RootView.findViewById(R.id.resumenes);
        this.myapp = (myApp) getActivity().getApplicationContext();
        listview = (ListView)RootView.findViewById(R.id.commonListView);
        fileslistview = (ListView)RootView.findViewById(R.id.filesListView);
        speakers_listview = (ListView)RootView.findViewById(R.id.speakers_list_view);
        events_listview= (ListView)RootView.findViewById(R.id.events_list_view);
        header= (ParseImageView)RootView.findViewById(R.id.header);
        footer = (RelativeLayout)RootView.findViewById(R.id.footer);

        footer.setBackgroundColor(getResources().getColor(R.color.companySecundario));



        Toolbar toolbar = (Toolbar) RootView.findViewById(R.id.event_detail_toolbar);
        if(Locale.getDefault().getLanguage().equals("en")){
            toolbar.setTitle(selectedEvent.getTitle());
        }
        else {
            toolbar.setTitle(selectedEvent.getTitle());
        }

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();

            }
        });
        if(eventosAnidados==null || eventosAnidados.isEmpty()){
            if(selectedEvent.getType().equals("conferencia")){
                toolbar.setBackgroundColor(getResources().getColor(R.color.conferencia));

            }
            else if(selectedEvent.getType().equals("modulo")||selectedEvent.getType().equals("simposio")){
                toolbar.setBackgroundColor(getResources().getColor(R.color.companySecundario));

            }

            else if(selectedEvent.getType().equals("social")||selectedEvent.getType().equals("break")){
                toolbar.setBackgroundColor(getResources().getColor(R.color.brk));
            }

            else {
                toolbar.setBackgroundColor(getResources().getColor(R.color.conferencia));
            }
        }
        else {
            footer.setVisibility(View.GONE);
        }

        List<Button> buttons = new ArrayList<>();


        if(selectedEvent.getParseFileV1()!=null){

            ParseFile hdr = selectedEvent.getParseFileV1();
            if (hdr != null) {
                //Get singleton instance of ImageLoader
                ImageLoader imageLoader = ImageLoader.getInstance();
                //Load the image from the url into the ImageView.
                imageLoader.displayImage(hdr.getUrl(), header);
            }

        }

        /*
        List <TabUI> tabUIs= selectedEvent.getToolbar().getUITabs();
        for(TabUI tabUI:tabUIs){
            if(tabUI.getViewPointer().getnameView().equals("funRoomEvent")){
                map.setVisibility(View.VISIBLE);
            }
            else if(tabUI.getViewPointer().getnameView().equals("funRating")){
                rate.setVisibility(View.VISIBLE);
            }
            else if(tabUI.getViewPointer().getnameView().equals("funFavorite")){
                favourite.setVisibility(View.VISIBLE);
            }
            else if(tabUI.getViewPointer().getnameView().equals("navLibrary")){
            }
        }*/


        rate.setTextColor(Color.WHITE);
        ask.setTextColor(Color.WHITE);
        map.setTextColor(Color.WHITE);
        //checkin.setTextColor(Color.WHITE);
        makeFavourite.setTextColor(Color.WHITE);
        ask.setVisibility(View.VISIBLE);
        //checkin.setVisibility(View.INVISIBLE);

        if(selectedEvent.getPlace()!=null) {
            if (selectedEvent.getPlace().getGeoPoint() != null) {

            }
            else {
                map.setVisibility(View.INVISIBLE);
            }
        }
        else {
            map.setVisibility(View.INVISIBLE);
        }

        /*
        if(selectedEvent.getEventFiles()!=null){

        }
*/

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        rate.getLayoutParams().width = (width/5);
        ask.getLayoutParams().width = (width/5);
        map.getLayoutParams().width = (width/5);
        //checkin.getLayoutParams().width = (width/5);
        makeFavourite.getLayoutParams().width = (width/5);


        TextView description = (TextView) RootView.findViewById(R.id.content);
        if(selectedEvent.getDetails()!=null){
            description.setText(selectedEvent.getDetails());
        }
        else {
            description.setVisibility(View.GONE);
        }

        Log.i("DESCRIPTION",String.valueOf(selectedEvent.getDetails()));
        if(myapp.getBooleanApp(selectedEvent.getObjectId())){
            rate.setVisibility(View.INVISIBLE);
        }

//        if(selectedEvent.getEventFiles()!=null){
//
//            if(selectedEvent.getType().equals("Trabajos Libres")){
//                fileslistview.setVisibility(View.VISIBLE);
//                docsadapter = new GridDocumentsAdapter(getActivity(),R.layout.cell_document,selectedEvent.getEventFiles());
//                fileslistview.setAdapter(docsadapter);
//
//                fileslistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//
//
//                        ParseObject object = (ParseObject)(fileslistview.getItemAtPosition(position));
//                        MobiFile mobiFile= ParseObject.createWithoutData(MobiFile.class, object.getObjectId());
//                        Fragment fragment = DocumentDetailFragment.newInstance(mobiFile);
//                        final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                        ft.replace(R.id.container,fragment);
//                        ft.addToBackStack(null);
//                        ft.commit();
//
//                    }
//                });
//
//
//            }
//            else {
//                checkin.setVisibility(View.VISIBLE);
//                checkin.setText("Docs");
//                checkin.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Fragment fragment = DocumentListFragment.newInstance(selectedEvent.getEventFiles());
//                        final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                        ft.replace(R.id.container, fragment);
//                        ft.addToBackStack(null);
//                        ft.commit();
//                    }
//                });
//
//            }
//
//        }


        if(eventosAnidados!=null || eventosAnidados.size()>0 ){


            List<Actividad> anidateEvents = eventosAnidados;



            Collections.sort(anidateEvents, new Comparator<Actividad>() {
                @Override
                public int compare(Actividad lhs, Actividad rhs) {


                    int date1Diff = lhs.getStartDate().compareTo(rhs.getStartDate());
                    if (date1Diff == 0) {
                        return (int) lhs.getEndDate().getTime() - (int) rhs.getEndDate().getTime();
                    } else {
                        return (int) lhs.getStartDate().getTime() - (int) rhs.getStartDate().getTime();
                    }

                }
            });
            if(roles!=null){
                Log.i("roles", String.valueOf(roles.size()));
                speaker_adapter = new DirectiveListViewAdapter2(getActivity(),roles,false);
            }
            else {
                speakers_listview.setVisibility(View.GONE);
            }
            events_listview.setVisibility(View.VISIBLE);
            adapter2 = new HetpinProgramListViewAdapter(getActivity(),anidateEvents,mApp,true,false);

            events_listview.setAdapter(adapter2);
            events_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    ParseObject object = (ParseObject) (events_listview.getItemAtPosition(position));
                    Actividad event = ParseObject.createWithoutData(Actividad.class, object.getObjectId());
                    //final List <Actividad> eventosAnidados2 = new ArrayList<>();
                    roles2.clear();

                    ParseQuery<PersonaRolAct> personaRolActParseQuery = ParseQuery.getQuery(PersonaRolAct.class);
                    personaRolActParseQuery.include("persona.pais");
                    personaRolActParseQuery.include("actividad.lugar");
                    personaRolActParseQuery.fromLocalDatastore();
                    personaRolActParseQuery.fromPin("personasRol");
                    personaRolActParseQuery.whereEqualTo("act",event);
                    personaRolActParseQuery.findInBackground(new FindCallback<PersonaRolAct>() {
                        @Override
                        public void done(List<PersonaRolAct> objects, ParseException e) {
                            if(objects!=null){
                                Log.i("roles2", String.valueOf(roles2.size()));

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
                                        roles2.add(personaRolAct);
                                    }
                                }                            }

                        }
                    });
                    evento = event;
                    ParseQuery<ActContAct> queryContenido = ParseQuery.getQuery(ActContAct.class);
                    queryContenido.include("contenido.lugar");
                    queryContenido.include ("contenedor");
                    queryContenido.fromPin("ActConAct2");
                    queryContenido.fromLocalDatastore();
                    queryContenido.whereEqualTo("contenedor", evento);
                    queryContenido.findInBackground(new FindCallback<ActContAct>() {
                        @Override
                        public void done(List<ActContAct> objects, ParseException e) {
                            List<Actividad> eventosAnidados2 = new ArrayList<>();

                            for(ActContAct actContAct:objects){
                                eventosAnidados2.add(actContAct.getContenido());
                                Log.i("eventosanidados", String.valueOf(eventosAnidados2.size()));

                            }


                            Fragment fragment = EventDetailFragment3.newInstance(evento, mApp,roles2,eventosAnidados2);
                            final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.container, fragment);
                            ft.addToBackStack(null);
                            ft.commit();
                        }
                    });






                }
            });
        }


        ArrayList<Actividad> list = new ArrayList<>();
        list.add(0,selectedEvent);



//        llama al adaptador con boolean false para indicar que es una celda de programa que se necesita mostrar
//        como encabezado para el detalle de evento
        adapter = new HetpinProgramListViewAdapter(getActivity(),list,mApp,false,false);
        listview.setAdapter(adapter);

        //Log.i("SPEAKERSEVENT", String.valueOf(roles.size()));




        speakers_listview.setAdapter(speaker_adapter);
        speakers_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ParseObject object = (ParseObject) (speakers_listview.getItemAtPosition(position));
                final PersonaRolAct actor = ParseObject.createWithoutData(PersonaRolAct.class, object.getObjectId());
                //Persona person = ParseObject.createWithoutData(Persona.class, object.getObjectId());
                ParseQuery<PersonaRolAct> personaRolActParseQuery = ParseQuery.getQuery(PersonaRolAct.class);
                personaRolActParseQuery.include("persona.pais");
                personaRolActParseQuery.include("actividad.lugar");
                personaRolActParseQuery.fromLocalDatastore();
                personaRolActParseQuery.fromPin("personasRol");
                personaRolActParseQuery.whereEqualTo("persona",actor.getPerson());
                personaRolActParseQuery.findInBackground(new FindCallback<PersonaRolAct>() {
                    @Override
                    public void done(List<PersonaRolAct> objects, ParseException e) {
                        if(objects!=null){
                            for(PersonaRolAct personaRolAct:objects){
                                if(personaRolAct.getAct()!=null){
                                    eventosSpeaker.add(personaRolAct.getAct());
                                }

                            }
                        }
                        Fragment fragment = SpeakerDetailFragment.newInstance(actor.getPerson(),eventosSpeaker, mApp, false);
                        final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.container, fragment);
                        ft.addToBackStack(null);
                        ft.commit();


                    }
                });
            }
        });


        if(myapp.checkConnection()){

            if(myapp.getFavoriteApp(selectedEvent.getObjectId())){
                if(Locale.getDefault().getLanguage().equals("en") ){
                    makeFavourite.setText("Cancelar Favorito");
                }
                else {
                    makeFavourite.setText("Cancelar Favorito");
                }
            }
            else {
                if(Locale.getDefault().getLanguage().equals("en") ){
                    makeFavourite.setText("Hacer Favorito");
                }
                else {
                    makeFavourite.setText("Hacer Favorito");
                }
            }





        }
        else {
            Log.i("HOLAEVENT","HOLA");
        }





        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialogo = new Dialog(getActivity());
                dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialogo.setContentView(R.layout.dialog_rate);
                Button btn_Ok = (Button) dialogo.findViewById(R.id.popOkRate);
                Button btn_Cancel = (Button)dialogo.findViewById(R.id.popCancelRate);
                TextView textView = (TextView)dialogo.findViewById(R.id.durationTitle);

                if(Locale.getDefault().getLanguage().equals("en")){
                    btn_Cancel.setText("Cancelar");
                    textView.setText("Evaluar");
                }
                else {
                    btn_Cancel.setText("Cancelar");
                    textView.setText("Evaluar");
                }
                ratingBar = (RatingBar) dialogo.findViewById(R.id.rating_bar);
                btn_Ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if ((int) ratingBar.getRating()>0){


                            saveRating((int) ratingBar.getRating());


/*
                            if(myapp.checkConnection()){
                                ParseUser user = ParseUser.getCurrentUser();
                                user.add("ratings",gameScore);
                                user.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                    }
                                });
                            }
                            else {
                                ParseUser user = ParseUser.getCurrentUser();
                                user.add("ratings",gameScore);
                                user.saveEventually();
                            }
*/



                            myapp.setBooleanAppTrue(selectedEvent.getObjectId());
                            dialogo.dismiss();
                            Toast toast =
                                    Toast.makeText(getActivity(),
                                            getString(R.string.rate), Toast.LENGTH_SHORT);
                            toast.show();
                            rate.setVisibility(View.INVISIBLE);

                        }


                    }
                });
                btn_Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogo.dismiss();
                    }
                });

                dialogo.getWindow().getAttributes().width = RelativeLayout.LayoutParams.MATCH_PARENT;
                dialogo.show();


            }
        });

        ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialogo = new Dialog(getActivity());
                dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialogo.setContentView(R.layout.map_box_layout);

//                if(selectedEvent.getPlace()!=null){
//                    if(selectedEvent.getPlace().getMaps()!=null){
//                        MobiFile map = selectedEvent.getPlace().getMaps().get(0);
//                        try {
//                            map.fetchIfNeeded();
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                        final Button done = (Button) dialogo.findViewById(R.id.btn_done_image_dialog);
//                        TouchImageView mapadialog = (TouchImageView) dialogo.findViewById(R.id.image_dialog);
//                        mapadialog.setMaxZoom(3f);
//                        mapadialog.setMinZoom(1f);
//                        if (map!= null) {
//                            ImageLoader imageLoader = ImageLoader.getInstance();
//                            //Load the image from the url into the ImageView.
//                            imageLoader.displayImage(map.getParseFileV1().getUrl(), mapadialog);
//                        }
//
//
//                        dialogo.getWindow().getAttributes().width = RelativeLayout.LayoutParams.MATCH_PARENT;
//                        done.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                dialogo.dismiss();
//
//                            }
//                        });
//
//                        dialogo.show();
//                    }
//                }
//                else {
//
//                }



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


        makeFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(makeFavourite.getText().equals("Hacer Favorito")){
                    Calendar cal = Calendar.getInstance();

                    if(selectedEvent.getStartDate().getTime()<=cal.getTimeInMillis()){
                        Toast toast1 = Toast.makeText(getActivity(),
                                getString(R.string.finished), Toast.LENGTH_SHORT);
                        toast1.show();
                    }
                    else {

                        if(selectedEvent.getStartDate().getTime()-900000<=cal.getTimeInMillis()){
                            Toast toast1 = Toast.makeText(getActivity(),
                                    getString(R.string.not_finished), Toast.LENGTH_SHORT);
                            toast1.show();
                        }
                        else {

                            saveFavorite();

                            myapp.setFavoriteAppTrue(selectedEvent.getObjectId());

                            SetAlarmaEvento(selectedEvent.getStartDate().getTime() - 900000, selectedEvent.getTitle());

                            if(Locale.getDefault().getLanguage().equals("en")){
                                makeFavourite.setText("Cancelar Favorito");

                            }
                            else {
                                makeFavourite.setText("Cancelar Favorito");
                            }
                        }



                    }



                }

                else if (makeFavourite.getText().equals("Cancelar Favorito")||
                        makeFavourite.getText().equals("Cancelar Favorito")){
                    if(Locale.getDefault().getLanguage().equals("en")){
                        makeFavourite.setText("Hacer Favorito");
                    }
                    else {
                        makeFavourite.setText("Hacer Favorito");
                    }
                    deleteFavorite();
                    cancelarAlarmaEvento(selectedEvent.getTitle());

                }
                else {

                }


            }
        });


        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                Log.i("HOLA",String.valueOf(keyCode));
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    // handle back button's click listener

                    Log.i("AMAAAA", "BACKKK");
                    Log.i("AMAAA",String.valueOf(myapp.getFromDetail()));
                    getActivity().onBackPressed();
//                    if(myapp.getFromDetail()){
//                        getActivity().onBackPressed();
//                    }
//                    else {
//                        myapp.setFromDetail(true);
//                        Fragment fragment = MeetingAppViewPagerFragment.newInstance(mApp,LoadDataFragment.eventos, LoadDataFragment.orgs);
//                        final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                        ft.replace(R.id.container, fragment);
//                        ft.addToBackStack(null);
//                        ft.commitAllowingStateLoss();
//                    }

                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        if (!getActivity().isFinishing()){
            Log.i("DAFUCk","DADA");

        }
    }


    @Override
    public void onStop() {
        super.onStop();
//        roles.clear();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {

            case android.R.id.home:
                ((ActionBarActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.directorio);
                getActivity().onBackPressed();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void SetAlarmaEvento(long milis,String mensaje){

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milis);



        Intent myIntent = new Intent(getActivity(), BroadcastAlarma.class);
        myIntent.setAction("My.Action.Alarm");
        myIntent.putExtra("alarma",mensaje);
        pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)getActivity().getApplicationContext().getSystemService(getActivity().getApplicationContext().ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Toast toast2 =
                Toast.makeText(getActivity(),
                        getString(R.string.alarm), Toast.LENGTH_SHORT);

        toast2.show();


    }

    private void cancelarAlarmaEvento(String mensaje){
        Intent myIntent = new Intent(getActivity(), BroadcastAlarma.class);
        myIntent.setAction("My.Action.Alarm");
        myIntent.putExtra("alarma",mensaje);
        pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)getActivity().getApplicationContext().getSystemService(getActivity().getApplicationContext().ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        Toast toast2 =
                Toast.makeText(getActivity(),
                        getString(R.string.alarm_cancel), Toast.LENGTH_SHORT);

        toast2.show();
    }



    private void saveFavorite(){

        /*
        final ParseObject gameScore = new ParseObject("Favorite");
        gameScore.put("event_", selectedEvent);
        gameScore.put("comment",selectedEvent.getTitle());
        gameScore.put("type","fav");
        gameScore.put("user", ParseUser.getCurrentUser());
        new Thread(new Runnable() {
            public void run() {
                gameScore.saveInBackground();
            }
        }).start();
*/
        final ActFavUser favorito = new ActFavUser();
        favorito.put("actividad",selectedEvent);
        favorito.put("congreso",mApp);
        favorito.put("user",ParseUser.getCurrentUser());
        new Thread(new Runnable() {
            public void run() {
                favorito.saveEventually();
            }
        }).start();

    }

    private void deleteFavorite(){


        Log.i("OBJECTID",String.valueOf(selectedEvent.getObjectId()));

        ParseQuery<ActFavUser> query = ParseQuery.getQuery(ActFavUser.class);
        query.whereEqualTo("user",ParseUser.getCurrentUser());
        query.whereEqualTo("actividad",selectedEvent);
        query.getFirstInBackground(new GetCallback<ActFavUser>() {
            @Override
            public void done(ActFavUser favorito, ParseException e) {
                Log.i("RATING", String.valueOf(favorito));
                favorito.deleteInBackground();
            }
        });

        myapp.setFavoriteAppFalse(selectedEvent.getObjectId());

        Toast toast1 = Toast.makeText(getActivity(),
                getString(R.string.cancelled), Toast.LENGTH_SHORT);
        toast1.show();
    }

    private void saveRating(Integer rating){
        final Rating gameScore = new Rating();
        //gameScore.setComment(selectedEvent.getTitle());
        //gameScore.setUser(ParseUser.getCurrentUser());
        gameScore.put("evento", selectedEvent);
        gameScore.setRating(rating);
        new Thread(new Runnable() {
            public void run() {
                gameScore.saveEventually();
            }

        }).start();
    }


}