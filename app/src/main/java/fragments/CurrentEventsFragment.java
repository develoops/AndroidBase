package fragments;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import adapters.HetpinProgramListViewAdapter;

import mc.sms.R;
import mc.sms.myApp;
import model.ActContAct;
import model.Actividad;
import model.PersonaRolAct;

/**
 * Created by hetpin on 4/16/15.
 */
public class CurrentEventsFragment extends Fragment {
    ListView listview;
    HetpinProgramListViewAdapter adapter;
    public static Actividad meetingApp;
    public static List<Actividad> eventList;
    public static List<PersonaRolAct> roles;

    myApp app;

    public static CurrentEventsFragment newInstance(Actividad mApp, List<Actividad> events) {
        // Instantiate a new fragment
        CurrentEventsFragment fragment = new CurrentEventsFragment();
        eventList = events;
        meetingApp = mApp;
        //fragment.setRetainInstance(true);
        Log.e("SIZEEVENTCURENTE", "events size " + eventList.size());

        return fragment;

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retain this fragment across configuration changes.
        setRetainInstance(true);

    }

    //current_event_toolbar
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View RootView = inflater.inflate(R.layout.hetpin_current_events_fragment, container, false);
        setHasOptionsMenu(true);
        this.app = (myApp) getActivity().getApplicationContext();
        Toolbar toolbar = (Toolbar) RootView.findViewById(R.id.current_event_toolbar);
        if(Locale.getDefault().getLanguage().equals("en")){
            toolbar.setTitle("En este momento");
        }
        else {
            toolbar.setTitle("En este momento");
        }

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setBackgroundColor(getResources().getColor(R.color.companySecundario));
        listview = (ListView) RootView.findViewById(R.id.commonListView);
        if (meetingApp != null) {
            adapter = new HetpinProgramListViewAdapter(getActivity(), eventList, meetingApp, true, false);
            listview.setAdapter(adapter);
            listview.setCacheColorHint(0);
        }


        return RootView;
    }

    @Override
    public void onResume() {
        super.onResume();
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

    @Override
    public void onStart() {
        super.onStart();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ParseObject object = (ParseObject) (listview.getItemAtPosition(position));
                final Actividad event = ParseObject.createWithoutData(Actividad.class, object.getObjectId());
                if(event.getType()!=null){
                    if (event.getType().equals("break")) {

                    } else {
                       /* final List <Actividad> eventosAnidados = new ArrayList<>();
                        ParseQuery<ActContAct> queryContenido = ParseQuery.getQuery(ActContAct.class);
                        queryContenido.include("contenido.lugar");
                        queryContenido.include ("contenedor");
                        queryContenido.fromLocalDatastore();
                        queryContenido.fromPin("ActConAct"); // estaba uno antes
                        queryContenido.whereEqualTo("contenedor", event);
                        queryContenido.findInBackground(new FindCallback<ActContAct>() {
                            @Override
                            public void done(List<ActContAct> objects, ParseException e) {
                                for(ActContAct actContAct:objects){
                                    eventosAnidados.add(actContAct.getContenido());
                                }
                            }
                        });*/

                        ParseQuery<PersonaRolAct> personaRolActParseQuery = ParseQuery.getQuery(PersonaRolAct.class);
                        personaRolActParseQuery.include("persona.pais");
                        personaRolActParseQuery.include("actividad.lugar");
                        personaRolActParseQuery.fromPin("personasRol");
                        personaRolActParseQuery.fromLocalDatastore();
                        personaRolActParseQuery.whereEqualTo("act",event);
                        personaRolActParseQuery.findInBackground(new FindCallback<PersonaRolAct>() {
                            @Override
                            public void done(List<PersonaRolAct> objects, ParseException e) {
                                if(objects!=null){
                                    roles=objects;
                                    app.setFromDetail(false);
                                    Fragment fragment = EventDetailFragment.newInstance(event, meetingApp,roles);
                                    final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                    ft.replace(R.id.container, fragment);
                                    ft.addToBackStack(null);
                                    ft.commit();
                                }

                            }
                        });


                    }

                }

            }
        });
    }
}