package fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import adapters.SpeakersListViewAdapter;

import mc.sms.R;

import model.Actividad;

import model.Persona;
import model.PersonaRolAct;
import model.PersonaRolOrg;

/**
 * Created by Alvaro on 2/19/15.
 */
public class SpeakersFragment extends Fragment implements SearchView.OnQueryTextListener {

    ListView listview;
    private SearchView mSearchView;
    SpeakersListViewAdapter adapter;
    public static List<Persona> speakers = new ArrayList<>();
    public static List<Actividad> eventosSpeaker = new ArrayList<>();
    public static Actividad mApp;
    public static Persona personars;
    SwipeDetector swipeDetector;
    public static PersonaRolOrg actorEvent;
    public static SpeakersFragment newInstance(Actividad meetingApp, List<Persona> persons) {

        // Instantiate a new fragment

        SpeakersFragment fragment = new SpeakersFragment();

        //persons = meetingApp.getPersons();
        mApp = meetingApp;
        //speakers.clear();
        speakers= persons;

        fragment.setRetainInstance(true);
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
//        ParseQuery<PersonaRolAct> queryPersonaRolAct = ParseQuery.getQuery(PersonaRolAct.class);
//        queryPersonaRolAct.include("persona");
//        queryPersonaRolAct.include("congreso");
//        queryPersonaRolAct.whereEqualTo("congreso",mApp);
//        queryPersonaRolAct.fromPin("personasRol");
//        queryPersonaRolAct.fromLocalDatastore();
//        queryPersonaRolAct.findInBackground(new FindCallback<PersonaRolAct>() {
//            @Override
//            public void done(List<PersonaRolAct> objects, ParseException e) {
//                if(objects!=null){
//                    for(PersonaRolAct personaRolAct:objects){
//                        Log.i("NOSFUIMOSALAB", "PASASTE");
//                        persons.add(personaRolAct.getPerson());
//
//                    }
//
//                    if(persons!=null){
//                        List<Persona> persons1 = persons;
//
//                        Log.i("NOSFUIMOSALAB2", String.valueOf(persons1.size()));
//
//                        Collections.sort(persons1,new Comparator<Persona>() {
//                            @Override
//                            public int compare(Persona lhs, Persona rhs) {
//                                return lhs.getLastName().compareTo(rhs.getLastName());
//                            }
//                        });
//                        adapter = new SpeakersListViewAdapter(getActivity(),persons1,mApp,true);
//                        // Binds the Adapter to the ListView
//                        listview.setAdapter(adapter);
//                        listview.setTextFilterEnabled(true);
//                        setupSearchView();
//                        listview.setOnTouchListener(swipeDetector);
//
//                        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                ParseObject object = (ParseObject)(listview.getItemAtPosition(position));
//                                Persona person = ParseObject.createWithoutData(Persona.class, object.getObjectId());
//                                Fragment fragment = SpeakerDetailFragment.newInstance(person, mApp, true);
//                                final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                                ft.replace(R.id.container,fragment);
//                                ft.addToBackStack(null);
//                                ft.commit();
//                            }
//
//
//                        });
//                    }
//                    else {
//                        Log.i("NO","NO");
//                    }
//
//
//
//
//
//                }
//
//            }
//        });



    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(getClass().getName(), "onCreateView");
        final View RootView = inflater.inflate(R.layout.speakers_layout, container , false);
        listview = (ListView)RootView.findViewById(R.id.commonListView);
        mSearchView=(SearchView) RootView.findViewById(R.id.search);

        if(speakers!=null){
            Collections.sort(speakers, new Comparator<Persona>() {
                @Override
                public int compare(Persona lhs, Persona rhs) {
                    return lhs.getLastName().compareTo(rhs.getLastName());
                }
            });
        }

        adapter = new SpeakersListViewAdapter(getActivity(),speakers,mApp,true);
        // Binds the Adapter to the ListView
        listview.setAdapter(adapter);
        listview.setTextFilterEnabled(true);
        setupSearchView();
        listview.setOnTouchListener(swipeDetector);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ParseObject object = (ParseObject)(listview.getItemAtPosition(position));
                Persona person = ParseObject.createWithoutData(Persona.class, object.getObjectId());
                personars = person;
                ParseQuery<PersonaRolAct> personaRolActParseQuery = ParseQuery.getQuery(PersonaRolAct.class);
                personaRolActParseQuery.include("persona.pais");
                personaRolActParseQuery.include("actividad.lugar");
                personaRolActParseQuery.fromLocalDatastore();
                personaRolActParseQuery.fromPin("personasRol");
                personaRolActParseQuery.whereEqualTo("persona",person);
                personaRolActParseQuery.findInBackground(new FindCallback<PersonaRolAct>() {
                    @Override
                    public void done(List<PersonaRolAct> objects, ParseException e) {
                        if(objects!=null){
                            List <String> nombre = new ArrayList<>();
                            for(PersonaRolAct personaRolAct:objects){
                                if(personaRolAct.getAct()!=null){
                                    Integer count = 0;
                                    nombre.add(personaRolAct.getAct().getTitle());
                                    for(String id:nombre){
                                        if(id.equals(personaRolAct.getAct().getTitle())){
                                            count++;
                                        }
                                    }
                                    if(count==1){
                                        eventosSpeaker.add(personaRolAct.getAct());
                                    }
                                }

                            }
                        }

                        Fragment fragment = SpeakerDetailFragment.newInstance(personars,eventosSpeaker,mApp, true);
                        final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.container,fragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    }
                });

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

    @Override
    public void onStop() {
        super.onStop();
    }


    private void setupSearchView()
    {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Busca aquí");
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {

        if (TextUtils.isEmpty(newText)) {
            listview.clearTextFilter();
        } else {
            listview.setFilterText(newText.toString());
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        return false;
    }


}
