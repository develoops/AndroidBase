package fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import java.util.List;

import adapters.HetpinProgramListViewAdapter;
import mc.sms.R;
import model.ActContAct;
import model.Actividad;
import model.PersonaRolAct;

/**
 * Created by Alvaro on 3/8/15.
 */
public class SearchViewFragment extends Fragment implements SearchView.OnQueryTextListener{
    ListView listview;
    private SearchView mSearchView;
    HetpinProgramListViewAdapter adapter;
    public static List<Actividad> eventList;
    public static List<PersonaRolAct> roles;

    public static Actividad mApp;

    public static SearchViewFragment newInstance(Actividad meetingApp, List<Actividad> actividads) {

        // Instantiate a new fragment

        SearchViewFragment fragment = new SearchViewFragment();

        eventList = actividads;
        mApp= meetingApp;

        return fragment;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View RootView = inflater.inflate(R.layout.search_layout, container , false);
        setHasOptionsMenu(true);


        Toolbar toolbar = (Toolbar) RootView.findViewById(R.id.searchtoolbar);
        toolbar.setTitle("Búsqueda");
        toolbar.setNavigationIcon(R.drawable.left);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();

            }
        });
        toolbar.setBackgroundColor(getResources().getColor(R.color.companySecundario));
        mSearchView=(SearchView) RootView.findViewById(R.id.searchView);
        listview = (ListView)RootView.findViewById(R.id.commonListView);
        listview.setTextFilterEnabled(true);
        setupSearchView();
        adapter = new HetpinProgramListViewAdapter(getActivity(),eventList, mApp, true,false);
        // lista de celdas programa
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ParseObject object = (ParseObject)(listview.getItemAtPosition(position));
                final Actividad event = ParseObject.createWithoutData(Actividad.class, object.getObjectId());
               /* final List <Actividad> eventosAnidados = new ArrayList<>();
                ParseQuery<ActContAct> queryContenido = ParseQuery.getQuery(ActContAct.class);
                queryContenido.include("contenido.lugar");
                queryContenido.include ("contenedor");
                queryContenido.fromLocalDatastore();
                queryContenido.fromPin("ActConAct");
                queryContenido.whereEqualTo("contenedor", event); //estaba uno antes
                queryContenido.findInBackground(new FindCallback<ActContAct>() {
                    @Override
                    public void done(List<ActContAct> objects, ParseException e) {
                        for(ActContAct actContAct:objects){
                            eventosAnidados.add(actContAct.getContenido());
                        }
                    }
                });

             ;*/
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

                            roles=objects;
                            Fragment fragment = EventDetailFragment.newInstance(event, mApp,roles);
                            final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.container, fragment);
                            ft.addToBackStack(null);
                            ft.commit();
                        }

                    }
                });

                /*
                MeetingApp meetingApp = ParseObject.createWithoutData(MeetingApp.class, object.getObjectId());
                Fragment fragment = SplashEventFragment.newInstance(meetingApp);
                final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container,fragment);
                ft.addToBackStack(null);
                ft.commit();*/

            }
        });

        //startLoading();

        return RootView;
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
