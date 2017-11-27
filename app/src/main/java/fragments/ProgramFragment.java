package fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import adapters.HetpinProgramListViewAdapter;
import adapters.PagerViewAdapter;
import mc.sms.R;

import model.ActContAct;
import model.Actividad;
import model.PersonaRolAct;


public class ProgramFragment extends Fragment {

    private static ViewPager viewPager;
    RelativeLayout barradias;
    private SearchView mSearchView;
    ListView listview;
    TextView day_program;
    HetpinProgramListViewAdapter adapter;
    PagerViewAdapter pg_adapter;
    ImageButton left, right;
    public static Actividad meetingApp, evento;
    public static Map<String, List<Actividad>> staticMap;
    public static List <PersonaRolAct> roles = new ArrayList<>();
    public List<Actividad> eventList;
    private  String headerDay = "";
    SharedPreferences mPrefs;
    final String welcomeScreenShownPref = "welcomeScreenShown";

    public static ProgramFragment newInstance(ViewPager pager, Map<String, List<Actividad>> map, String headerText, Actividad mApp) {
        // Instantiate a new fragment
        ProgramFragment fragment = new ProgramFragment();
        staticMap = map;
        viewPager = pager;
        meetingApp = mApp;
        Bundle args = new Bundle();
        fragment.setRetainInstance(true);
        args.putString("header", headerText);
        fragment.setArguments(args);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View RootView = inflater.inflate(R.layout.hetpin_program_layout, container, false);
        setHasOptionsMenu(true);
        headerDay = getArguments().getString("header");
        Log.e(getClass().getName(), "onCreateView + header: " + headerDay);
        barradias = (RelativeLayout) RootView.findViewById(R.id.navi_bar);
        barradias.setBackgroundColor(getResources().getColor(R.color.eventoTerciario));
        pg_adapter = new PagerViewAdapter(getChildFragmentManager());


        listview = (ListView) RootView.findViewById(R.id.commonListView);
//       listview.setTextFilterEnabled(true);

        day_program = (TextView) RootView.findViewById(R.id.dayProgram);


        return RootView;
    }


    @Override
    public void onStart() {
        super.onStart();




        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final ParseObject object = (ParseObject) (listview.getItemAtPosition(position));
                Actividad event = ParseObject.createWithoutData(Actividad.class, object.getObjectId());

                if(event.getType()!=null){
                    if(event.getType().equals("break")){

                    }
                    else {
                        Log.i("PORAQUIPASAA","NO");


                        evento = event;
                        Log.i("PORAQUIPASAA22222",String.valueOf(evento.getObjectId()));

                        ParseQuery<PersonaRolAct> personaRolActParseQuery = ParseQuery.getQuery(PersonaRolAct.class);
                        personaRolActParseQuery.include("persona.pais");
                        //personaRolActParseQuery.include("actividad.lugar");
                        personaRolActParseQuery.fromLocalDatastore();
                        personaRolActParseQuery.fromPin("personasRol");
                        personaRolActParseQuery.whereEqualTo("act",evento);
                        personaRolActParseQuery.findInBackground(new FindCallback<PersonaRolAct>() {
                            @Override
                            public void done(List<PersonaRolAct> objects, ParseException e) {
                                if(objects!=null){
                                    Fragment fragment = EventDetailFragment.newInstance(evento, meetingApp,objects);
                                    final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                    ft.replace(R.id.container, fragment);
                                    ft.addToBackStack(null);
                                    ft.commit();
                                }

                            }
                        });




                        //Log.i("NOPASOOOOOOOASDFSADF", String.valueOf(eventosAnidados.size()));


                        //Log.i("NOPASOOOOOOOASDFSADF", String.valueOf(eventosAnidados.size()));

                    }
                }

                else {
                    Log.i("PORAQUIPASAA","NO");

                    evento = event;
                    Fragment fragment = EventDetailFragment.newInstance(evento, meetingApp,roles);
                    final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.container, fragment);
                    ft.addToBackStack(null);
                    ft.commit();

                }



            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        if(meetingApp!=null){

            mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

            // second argument is the default to use if the preference can't be found
/*            Boolean welcomeScreenShown = mPrefs.getBoolean(welcomeScreenShownPref, false);

            if (!welcomeScreenShown) {
                // here you can launch another activity if you like
                // the code below will display a popup
                String title ="";
                String subtitle="";

//                List <New> news =  meetingApp.getWalls().get(0).getNews();
//                for (int i=0; i<news.size(); i++) {
//                   if(news.get(i).getTitle().equals("Bienvenida")){
//                      title = news.get(i).getTitle();
//                      subtitle = news.get(i).getContent();
//                   }
//                }
                                 //String whatsNewTitle = getResources().getString(R.string.whatsNewTitle);
                //String whatsNewText = getResources().getString(R.string.whatsNewText);
                new AlertDialog.Builder(getActivity()).setTitle(title).setMessage(subtitle).setPositiveButton(
                        "OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                SharedPreferences.Editor editor = mPrefs.edit();
                editor.putBoolean(welcomeScreenShownPref, true);
                editor.commit(); // Very important to save the preference
            }*/

            eventList = staticMap.get(headerDay);

            List<Actividad> events= eventList;

            Collections.sort(events, new Comparator<Actividad>() {
                @Override
                public int compare(Actividad lhs, Actividad rhs) {

                    int date1Diff = lhs.getStartDate().compareTo(rhs.getStartDate());
                    if(date1Diff==0){
                        return (int)lhs.getEndDate().getTime()-(int)rhs.getEndDate().getTime();
                    }
                    else {
                        return (int)lhs.getStartDate().getTime() - (int)rhs.getStartDate().getTime();
                    }

                }
            });


/*
            Collections.sort(events, new Comparator<Event>() {
                @Override
                public int compare(Event lhs, Event rhs) {

                    if(lhs.getPlace()==null){
                        return 0;
                    }
                    else if (rhs.getPlace()==null){
                        return 0;
                    }
                    else {
                        return lhs.getPlace().getName().toString().compareTo(rhs.getPlace().getName().toString());

                    }

                }
            });

*/


            headerDay = headerDay.replace("November", "Noviembre");


            day_program.setText(headerDay);
            //Llama al adaptador con boolean true para mostrar celda como deberia salir en el Programa
            // (no viene de evento detalle), en el adaptador se configura la celda

            adapter = new HetpinProgramListViewAdapter(getActivity(), events, meetingApp, true, false);
            // lista de celdas PROGRAMA˛≈≈X
            listview.setAdapter(adapter);
            //listview.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
            listview.setCacheColorHint(0);



            //startLoading();
            Log.e(getClass().getName(), "onCreateView end" + "header: " + headerDay);
//            for(Persona person: meetingApp.getPersons()){
//                if(person.getImage()!=null){
//                    person.getImage().getParseFileV1().getDataInBackground();
//                }
//
//            }
        }
        else {


        }


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

