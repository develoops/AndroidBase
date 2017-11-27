package fragments;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import adapters.HetpinProgramListViewAdapter;
import adapters.SpeakerDetailAdapter;
import mc.sms.R;
import model.ActContAct;
import model.Actividad;
import model.Persona;
import model.PersonaRolAct;

/**
 * Created by Alvaro on 3/1/15.
 */
public class SpeakerDetailFragment extends Fragment {

    public static Persona actorEvent;
    public static Boolean bool;
    SpeakerDetailAdapter adapter;
    public static Actividad meetingApp,evento;
    public static List<Actividad> eventosSpeaker = new ArrayList<>();
    public static List<PersonaRolAct> roles = new ArrayList<>();


    public static SpeakerDetailFragment newInstance(Persona actor, List<Actividad> eventos, Actividad mApp, boolean b) {

        // Instantiate a new fragment
        bool = b;
        meetingApp=mApp; //Para Alfonso
        SpeakerDetailFragment fragment = new SpeakerDetailFragment();
        actorEvent = actor;
        //eventosSpeaker.clear();
        eventosSpeaker=eventos;
        fragment.setRetainInstance(true);
        return fragment;

    }

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);
        //this._id = getArguments().getInt(INDEX);

        //((ActionBarActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.left);
        setHasOptionsMenu(true);


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
//        TextView speaker_name = (TextView)RootView.findViewById(R.id.speaker_name);
//        TextView institution = (TextView)RootView.findViewById(R.id.institution);
//        TextView country = (TextView)RootView.findViewById(R.id.country);
        ArrayList<Persona> speakers = new ArrayList<>();
        speakers.add(0,actorEvent);
        adapter = new SpeakerDetailAdapter(getActivity(),speakers,false);
        ListView speakercell = (ListView) RootView.findViewById(R.id.commonListView);
        ListView speakerlist = (ListView) RootView.findViewById(R.id.speakers_list_view);

        speakercell.setAdapter(adapter);

        TextView speaker_bio = (TextView)RootView.findViewById(R.id.content);
        RelativeLayout footer = (RelativeLayout)RootView.findViewById(R.id.footer);
        //RoundedImageView image = (RoundedImageView) RootView.findViewById(R.id.image_speaker);
        final ListView eventsofSpeaker = (ListView)RootView.findViewById(R.id.events_list_view);
        Log.i("ASD",String.valueOf(actorEvent));
        //speaker_name.setText(actorEvent.getPerson().getSalutation() + " " + actorEvent.getPerson().getFirstName() + " " + actorEvent.getPerson().getLastName());
        //institution.setText(actorEvent.getCompany().getName());
        footer.setVisibility(View.GONE);
        if(actorEvent!=null){
            if(actorEvent.getBio()==null || actorEvent.getBio().isEmpty()){
                speaker_bio.setVisibility(View.GONE);
                speakerlist.setVisibility(View.GONE);

            }
            else {
                speaker_bio.setText(actorEvent.getBio());
                speaker_bio.setTextColor(getResources().getColor(R.color.negro));
                Log.i("SPEAJrrrER","SP");
            }
        }

        if(eventosSpeaker!=null){
            Log.i("EVENTOSSPEAKERSDETALLE", String.valueOf(eventosSpeaker));
            HetpinProgramListViewAdapter adapter = new HetpinProgramListViewAdapter(getActivity(),eventosSpeaker,meetingApp,false,true);
            eventsofSpeaker.setAdapter(adapter);
            eventsofSpeaker.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ParseObject object = (ParseObject)(eventsofSpeaker.getItemAtPosition(position));
                    Actividad event = ParseObject.createWithoutData(Actividad.class, object.getObjectId());
                    evento = event;
                    roles.clear();

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
                                        roles.add(personaRolAct);
                                    }
                                }

                                Fragment fragment = EventDetailFragment.newInstance(evento, meetingApp,roles);
                                final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.container, fragment);
                                ft.addToBackStack(null);
                                ft.commit();
                            }

                        }
                    });



                }
            });
        }




        //country.setText(actorEvent.getCompany().getCountry());
        //ParseFile photoFile = actorEvent.getPerson().getProfilePicture();
        //image.setParseFile(photoFile);
        //image.loadInBackground();
//        ParseQuery<PersonaRolAct> personaRolActParseQuery = ParseQuery.getQuery(PersonaRolAct.class);
//        personaRolActParseQuery.include("persona.pais");
//        personaRolActParseQuery.include("actividad.lugar");
//        personaRolActParseQuery.fromLocalDatastore();
//        personaRolActParseQuery.fromPin("personasRol");
//        personaRolActParseQuery.whereEqualTo("persona",actorEvent);
//        personaRolActParseQuery.findInBackground(new FindCallback<PersonaRolAct>() {
//            @Override
//            public void done(List<PersonaRolAct> objects, ParseException e) {
//                List<Actividad> eventSpeaker = new ArrayList<>();
//                if(objects!=null){
//                    for(PersonaRolAct personaRolAct:objects){
//                        eventSpeaker.add(personaRolAct.getAct());
//                    }
//
//                    if(objects!=null && eventSpeaker!=null){
//                        if(eventSpeaker.size()>0){
//                            Log.i("EVENTOSSPKERR1", String.valueOf(eventSpeaker.size()));
//
//                            HetpinProgramListViewAdapter adapter = new HetpinProgramListViewAdapter(getActivity(),eventSpeaker,meetingApp,false,true);
//                            eventsofSpeaker.setAdapter(adapter);
//                            eventsofSpeaker.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                    ParseObject object = (ParseObject)(eventsofSpeaker.getItemAtPosition(position));
//                                    Actividad event = ParseObject.createWithoutData(Actividad.class, object.getObjectId());
//                                    evento = event;
//                                    ParseQuery<ActContAct> queryContenido = ParseQuery.getQuery(ActContAct.class);
//                                    queryContenido.include("contenido.lugar");
//                                    queryContenido.include ("contenedor");
//                                    queryContenido.fromPin("ActconAct2");
//                                    queryContenido.fromLocalDatastore();
//                                    queryContenido.whereEqualTo("contenedor", event);
//                                    queryContenido.findInBackground(new FindCallback<ActContAct>() {
//                                        @Override
//                                        public void done(List<ActContAct> objects, ParseException e) {
//                                            List<Actividad> eventosAnidados = new ArrayList<>();
//                                            for(ActContAct actContAct:objects){
//                                                Log.i("NOSFUIMOSALAB", "PASASTE");
//                                                eventosAnidados.add(actContAct.getContenido());
//                                            }
//
//                                            Log.i("NOPASOOOOOOOASDFSADF", String.valueOf(eventosAnidados.size()));
//                                            Fragment fragment = EventDetailFragment.newInstance(evento, meetingApp,eventosAnidados);
//                                            final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                                            ft.replace(R.id.container, fragment);
//                                            ft.addToBackStack(null);
//                                            ft.commit();
//
//
//                                        }
//                                    });
//                                }
//                            });
//                        }
//                    }
//                }
//            }
//        });




        Toolbar toolbar = (Toolbar) RootView.findViewById(R.id.event_detail_toolbar);
        if(actorEvent!=null){
            if(actorEvent.getSalutation()!=null || !actorEvent.getSalutation().isEmpty() ){
                toolbar.setTitle(actorEvent.getSalutation()+ " " +actorEvent.getFirstName()+ " " +
                        actorEvent.getLastName());
            }
            else {
                toolbar.setTitle(actorEvent.getFirstName()+ " " +
                        actorEvent.getLastName());
            }

        }

        toolbar.setNavigationIcon(R.drawable.left);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eventosSpeaker!=null){
                    eventosSpeaker.clear();

                }
                getActivity().onBackPressed();

            }
        });
        toolbar.setBackgroundColor(getResources().getColor(R.color.companySecundario));

        return RootView;
    }

    @Override
    public void onStart() {
        super.onStart();



    }


    @Override
    public void onResume() {
        super.onResume();
        /*
        View v = mTabHost.getTabWidget().getChildAt(0);
        v.setBackgroundResource(R.drawable.programa);
*/
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                Log.i("HOLA",String.valueOf(keyCode));
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    // handle back button's click listener

                    if(eventosSpeaker!=null){
                        eventosSpeaker.clear();

                    }
                    getActivity().onBackPressed();

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
        //eventosSpeaker.clear();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {

            case android.R.id.home:

                getActivity().onBackPressed();



                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
