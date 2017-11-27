package fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import adapters.EventsFragmentAdapter;
import fragments.sliding_tab.SlidingTabLayout;
import mc.sms.MainActivity;
import mc.sms.R;
import mc.sms.myApp;
import model.Actividad;
import model.Info;

import model.Media;
import model.Org;
import model.Persona;
import model.PersonaRolOrg;
import model.PreguntaEncuesta;
import utils.MUtil;
import views.CustomViewPager;

/**
 * Created by Alvaro on 3/3/15.
 */
public class MeetingAppViewPagerFragment extends Fragment {

    private CustomViewPager pager;
    EventsFragmentAdapter pageAdapter;
    public static Actividad meetingApp;
    private SlidingTabLayout mSlidingTabLayout;
    ListView listview;
    public static List<Actividad> events = new ArrayList<>();
    public static List<Org> organizaciones = new ArrayList<>();
   // public static List<Actividad> actividades = new ArrayList<>();
   public static List<PersonaRolOrg> comite = new ArrayList<>();
    public static List<Media> media = new ArrayList<>();

    public static List<PreguntaEncuesta> pEncuestas = new ArrayList<>();
    myApp app;
    public static List<Persona> speakers = new ArrayList<>();
    public static List<Info> news ;

    public static Long time;
    public List<String> Titles = new ArrayList<>();

    public static MeetingAppViewPagerFragment newInstance(Actividad app, List<Actividad> actividades, List <Persona> spk, List<Org> orgs, List<PersonaRolOrg> academic,  List<PreguntaEncuesta> preguntasEncuestas, List<Media> listMedia) {
        // Instantiate a new fragment
        MeetingAppViewPagerFragment fragment = new MeetingAppViewPagerFragment();
        meetingApp = app;
        events = actividades;
        organizaciones = orgs;
        speakers = spk;
        comite=academic;
        media = listMedia;
        pEncuestas = preguntasEncuestas;
        //fragment.setRetainInstance(true);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Titles.add("Programa");
        Titles.add("Ponentes");
        Titles.add("Favoritos");
        Titles.add("Patrocinadores");
        Titles.add("Autoridades del Congreso");
        Titles.add("Materiales");
        Titles.add("Encuesta General");


      /*  Titles.add("Materiales");
        Titles.add("Comité Académico");*/
        setRetainInstance(true);
        ParseQuery<Info> noticias = ParseQuery.getQuery(Info.class);
        noticias.findInBackground(new FindCallback<Info>() {
            @Override
            public void done(List<Info> objects, ParseException e) {
                news = objects;
            }
        });



//

//        Log.i("HOLAAAAAAASDAFSDFSFSDF", String.valueOf(actividades));
//        Log.i("COUNTEER", String.valueOf(actividades.size()));
        Log.i("POR ", meetingApp.getObjectId());
//        ParseQuery<ActContAct> queryContenido = ParseQuery.getQuery(ActContAct.class);
//        queryContenido.include("contenedor");
//        queryContenido.include("contenido.lugar");
//        queryContenido.whereEqualTo("contenedor",meetingApp);
//        queryContenido.findInBackground(new FindCallback<ActContAct>() {
//            @Override
//            public void done(List<ActContAct> objects, ParseException e) {
//                Log.i("POR LAHCHCUA", String.valueOf(objects));
//
//
//                for(ActContAct actContAct:objects){
//                    //Log.i("Actividad", String.valueOf(actContAct.getContenido().getObjectId()));
//                    actividades.add(actContAct.getContenido());
//                    //Log.i("Actividad", String.valueOf(actContAct.getContenido().getObjectId()));
//
//                }
//
//            }
//        });



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        this.app = (myApp) getActivity().getApplicationContext();
        app.setNow(false);
        app.setNowClickable(true);
        View v = inflater.inflate(R.layout.activity_text, container, false);
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.directorio);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPagerFragment loadDataFragment = new ViewPagerFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, loadDataFragment);
                ft.commitAllowingStateLoss();
            }
        });
        toolbar.setBackgroundColor(getResources().getColor(R.color.companySecundario));
        toolbar.inflateMenu(R.menu.menuprogram);
        View target = v.findViewById(R.id.news);

        final BadgeView badge = new BadgeView(getActivity(), target);
        if(meetingApp!=null){

            if(news!=null){
                if (news.size() - app.getintNews() > 0) {
                    app.ingresoNews();
                }
                if (app.getNews()) {
                    badge.setText("0");
                    badge.increment(news.size() - app.getintNews());
                    badge.show();
                } else {
                    badge.hide();
                }
            }

        }



        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                //Calendar cal = Calendar.getInstance();
                if (menuItem.getItemId() == R.id.news) {
                    if(news!=null){
                        app.setNews(news.size());
                        app.saleNews();
                    }

                    if(news!=null){

                        Log.i("NEWSSSS", String.valueOf(news));
                        NewsFragment newsfragment = NewsFragment.newInstance(news);
                        final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        getActivity().getFragmentManager().executePendingTransactions();
                        fragmentTransaction.replace(R.id.container, newsfragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                    else {

                    }

                    return true;
                }

                if (menuItem.getItemId() == R.id.search) {
                    SearchViewFragment newsfragment = SearchViewFragment.newInstance(meetingApp, events);
                    final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    getActivity().getFragmentManager().executePendingTransactions();
                    fragmentTransaction.replace(R.id.container, newsfragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }

                if (menuItem.getItemId() == R.id.now) {
                    if (app.getNowClickable()) {
                        app.setNowClickable(false);
                        menuItem.setChecked(!menuItem.isChecked());
                        menuItem.setIcon(menuItem.isChecked() ? R.drawable.program : R.drawable.emm);
                        app.setNow(menuItem.isChecked());
                        reloadView();
                    }
                }

                return false;
            }
        });

        pager = (CustomViewPager) v.findViewById(R.id.pager);
        //pager.getAdapter().notifyDataSetChanged();
        mSlidingTabLayout = (SlidingTabLayout) v.findViewById(R.id.tabs);
        // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View



        // insert all tabs from pagerAdapter data

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(meetingApp!=null){



            int Numboftabs = Titles.size();
//            Collections.sort(Titles, new Comparator<model.View>() {
//                @Override
//                public int compare(model.View lhs, model.View rhs) {
//                    return lhs.getsortingAux() - rhs.getsortingAux();
//
//                }
//            });


            pager.setOffscreenPageLimit(4);
            Log.i("ACTIVIDADESSSSS222222", String.valueOf(events));
            pager.setMaxChildProgramId(MUtil.divideEventByGroupSize(events) - 1);
            pageAdapter = new EventsFragmentAdapter(getChildFragmentManager(), Titles, Numboftabs, meetingApp, events, speakers, organizaciones, comite, media,pEncuestas,pager, app);
            pager.setAdapter(pageAdapter);
            mSlidingTabLayout.setViewPager(pager);


        }

        else {
            Intent intent = new Intent(getActivity(),
                    MainActivity.class);

            startActivity(intent);
        }

    }
    public void reloadView(){
        if(meetingApp!=null){

            int Numboftabs = Titles.size();
//            Collections.sort(Titles, new Comparator<model.View>() {
//                @Override
//                public int compare(model.View lhs, model.View rhs) {
//                    return lhs.getsortingAux() - rhs.getsortingAux();
//                }
//            });
            Log.i("CANTIDADSPEAKR2", String.valueOf(speakers.size()));

            pager.setOffscreenPageLimit(4);
            pager.setMaxChildProgramId(MUtil.divideEventByGroupSize(events) - 1);
            pageAdapter = new EventsFragmentAdapter(getChildFragmentManager(), Titles, Numboftabs, meetingApp, events, speakers,organizaciones,comite,media,pEncuestas,pager, app);
            pager.setAdapter(pageAdapter);
            mSlidingTabLayout.setViewPager(pager);
        }

        else {
            Intent intent = new Intent(getActivity(),
                    MainActivity.class);

            startActivity(intent);
        }
        app.setNowClickable(true);
    }
}