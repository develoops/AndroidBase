package fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import adapters.HetpinProgramListViewAdapter;

import mc.sms.R;
import mc.sms.myApp;
import model.ActFavUser;
import model.Actividad;
import model.PersonaRolAct;

/**
 * Created by Alvaro on 2/25/15.
 */
public class FavouritesFragment extends Fragment {

    public static List <Actividad> events2;
    public static List <PersonaRolAct> roles;

    HetpinProgramListViewAdapter adapter;
    ListView listview;
    public static Actividad meetingApp;

    public myApp myapp;
    public static FavouritesFragment newInstance(Actividad mApp) {

        // Instantiate a new fragment

        FavouritesFragment fragment = new FavouritesFragment();
        meetingApp = mApp;

        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View RootView = inflater.inflate(R.layout.common_list_layout, container , false);
        //final TextView textView = (TextView)RootView.findViewById(R.id.textView);

        this.myapp = (myApp) getActivity().getApplicationContext();


        /*


*/

        //events = (List<Event>) ParseUser.getCurrentUser().get("favourites");



        listview = (ListView)RootView.findViewById(R.id.commonListView);

        ParseQuery<ActFavUser> query = ParseQuery.getQuery(ActFavUser.class);
        query.whereEqualTo("user",ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<ActFavUser>() {
            @Override
            public void done(List<ActFavUser> ratings, ParseException e) {
                Log.i("HELLO","HELLASDASD");
                ArrayList<String> ids = new ArrayList<>();

                if(ratings!=null){
                    for(ActFavUser rating:ratings){
                        Log.i("IDD",String.valueOf(rating.getActividad().getObjectId()));
                        ids.add(rating.getActividad().getObjectId());

                    }

                    Log.i("IDDSS",String.valueOf(ids));
                    ParseQuery<Actividad> query2 = ParseQuery.getQuery(Actividad.class);
                    query2.whereContainedIn("objectId", ids);

                    query2.findInBackground(new FindCallback<Actividad>() {
                        @Override
                        public void done(List<Actividad> events, ParseException e) {
                            Log.i("PASEEEAFSADF","SDAFNVV");
                            Log.i("EVENTS",String.valueOf(events));
                            events2 = events;
                            Collections.sort(events2,new Comparator<Actividad>() {
                                @Override
                                public int compare(Actividad lhs, Actividad rhs) {
                                    return lhs.getStartDate().compareTo(rhs.getStartDate());
                                }
                            });
                            Log.i("EVENTS2",String.valueOf(events2));
                            if(events2!=null){
                                if(getActivity()!=null){
                                    adapter = new HetpinProgramListViewAdapter(getActivity(),events2,meetingApp,false,true);
                                    listview.setAdapter(adapter);
                                }

                            }
                            else {
                                       // textView.setVisibility(View.VISIBLE);
                            }

                            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                  /*  List<Actividad> eventosAnidados= new ArrayList<>();

                                    personaRolActParseQuery.include("persona.pais");
                                    personaRolActParseQuery.include("actividad.lugar");
                                    personaRolActParseQuery.fromLocalDatastore();
                                    personaRolActParseQuery.fromPin("personasRol");
                                    personaRolActParseQuery.whereEqualTo("act",event);
                                    personaRolActParseQuery.findInBackground(new FindCallback<PersonaRolAct>() {
                                        @Override
                                        public void done(List<PersonaRolAct> objects, ParseException e) {
                                            if(objects!=null){

                                                roles=objects;
                                            }

                                        }
                                    });*/




                                    ParseObject object = (ParseObject)(listview.getItemAtPosition(position));
                                    final Actividad event = ParseObject.createWithoutData(Actividad.class, object.getObjectId());
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

                                            }

                                            Fragment fragment = EventDetailFragment.newInstance(event, meetingApp,roles);
                                            final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                            ft.replace(R.id.container, fragment);
                                            ft.addToBackStack(null);
                                            ft.commit();

                                        }
                                    });

                                }
                            });

                        }
                    });
                }


            }
        });



        return RootView;
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

}
