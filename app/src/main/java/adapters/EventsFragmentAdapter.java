package adapters;

import android.database.DataSetObserver;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import fragments.ChildPagerMeetingsFragment;


import fragments.CurrentEventsFragment;
import fragments.DirectiveFragment;
import fragments.DirectiveFragment2;
import fragments.EncuestaGeneralFragment;
import fragments.FavouritesFragment;
import fragments.LoadDataFragment;
import fragments.MoreFragment;
import fragments.SpeakersFragment;
import fragments.SponsorsFragment;

import mc.sms.myApp;
import model.Actividad;
import model.Media;
import model.Org;
import model.Persona;
import model.PersonaRolOrg;
import model.PreguntaEncuesta;
import views.CustomViewPager;

/**
 * Created by Alvaro on 3/3/15.
 */
public class EventsFragmentAdapter extends FragmentStatePagerAdapter

{
    private CustomViewPager custom_pager;
    public static Actividad mApp;
    public static Long time;
    public static List<Actividad> events, eventos = new ArrayList<>();
    public static List<Org> organizaciones = new ArrayList<>();
    public static List<Persona> speakers = new ArrayList<>();
    public static List<Media> media = new ArrayList<>();
    public static List<PreguntaEncuesta> pEncuestas = new ArrayList<>();

    public static List<PersonaRolOrg> academic = new ArrayList<>();


    public Boolean now = false;
    myApp app;

    List<String> tabUIs; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created

    public EventsFragmentAdapter(FragmentManager fm, List<String> mTitles, int mNumbOfTabsumb, Actividad meetingApp, List<Actividad> actividades, List<Persona> personas, List <Org> orgs, List<PersonaRolOrg> comite, List<Media> doc, List<PreguntaEncuesta> preguntaEncuestas, CustomViewPager pager, myApp app) {
        super(fm);
        events = actividades;
        organizaciones= orgs;
        mApp=meetingApp;
        speakers = personas;
        academic=comite;
        media=doc;
        pEncuestas = preguntaEncuestas;
        Log.i("EVENTOOOOOOOOOsANTES", String.valueOf(events));
        //this.events = LoadDataFragment.eventos;
        this.tabUIs= mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.custom_pager = pager;
        eventos.clear();
        Log.i("MOBIFILE2", String.valueOf(media));

        this.app = app;




        Log.i("EVENTOOOOOOOOOs", String.valueOf(mApp));
        // Log.i("EVENTOOOOOOOOOs", String.valueOf(LoadDataFragment.actividades));
        Log.i("EVENTOOOOOOOOOs22222222", String.valueOf(events));

        Log.i("SPEAKERSADAPTER", String.valueOf(speakers.size()));

    }

    @Override
    public int getCount()
    {
        return NumbOfTabs;
    }
    @Override
    public Fragment getItem(int position)
    {

        //List<Actividad> events2 = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        //Log.i("MAPPID",mApp.getObjectId());
        if(tabUIs.get(position).equals("Programa")){
            Log.e("THANHNX", "CHILD PAGER ChildPagerMeetingsFragment 0");
            if(timeZone().equals("-03:00")){
                time = cal.getTimeInMillis() - 10800000;
            }
            else if(timeZone().equals("-04:00")){
                time = cal.getTimeInMillis() - 14400000;
            }
            else if(timeZone().equals("-05:00")){
                time = cal.getTimeInMillis() -18000000;
            }
            else if(timeZone().equals("-06:00")){
                time = cal.getTimeInMillis() -21600000;
            }
            else if(timeZone().equals("-07:00")){
                time = cal.getTimeInMillis() -25200000;
            }

            else if(timeZone().equals("-08:00")){
                time = cal.getTimeInMillis() -28800000;

            }
            else if(timeZone().equals("-02:00")){
                time = cal.getTimeInMillis() -7200000;
            }
            else if(timeZone().equals("-01:00")){
                time = cal.getTimeInMillis() -3600000;
            }
            else if(timeZone().equals("00:00")){
                time = cal.getTimeInMillis();
            }

            Log.i("TIME",String.valueOf(cal.getTimeInMillis()));
            try{
                Date now = new Date();
                //This block of comment just for test a day in 18th April
//		        Calendar c = Calendar.getInstance();
//		        c.set(Calendar.DAY_OF_MONTH, 18);
//		        now = c.getTime();
                List<Actividad> list = events;
                for (int i = 0; i <list.size(); i++) {
                    Actividad event = list.get(i);
                    if(event.getStartDate().getTime()<=time
                            && time<=event.getEndDate().getTime() ) {

                        if(event.getType().equals("Curso")){

                        }
                        else {
                            eventos.add(event);
                        }

                        Log.e("THANHNXNOW1", "Added " + event.getStartDate().toString());
                    }
                }
            } catch (NullPointerException e){
                Log.e("THANHNX events.add()", e.toString());
            }
            Log.e("Thanhnx", "events size " + eventos.size());
            Log.i("NOWLOCO", String.valueOf(app.getNow()));
            if (app.getNow())
                return CurrentEventsFragment.newInstance(mApp, eventos);
            else
                return ChildPagerMeetingsFragment.newInstance(mApp, events,this.custom_pager);
        }
        else if(tabUIs.get(position).equals("Ponentes")){
            return SpeakersFragment.newInstance(mApp, speakers);
        }
        else if(tabUIs.get(position).equals("Favoritos")){
            return FavouritesFragment.newInstance(mApp);
        }
        else if(tabUIs.get(position).equals("Patrocinadores")){
            return  SponsorsFragment.newInstance(mApp,organizaciones);
        }
        else if(tabUIs.get(position).equals("Autoridades del Congreso")){
            return  DirectiveFragment.newInstance(academic);
        }
        else if(tabUIs.get(position).equals("Encuesta General")){
            return  EncuestaGeneralFragment.newInstance(mApp,pEncuestas);
        }
        else if(tabUIs.get(position).equals("Materiales")) {
            return MoreFragment.newInstance(mApp,media);
        }
        else {
            return DirectiveFragment2.newInstance(LoadDataFragment.staff);
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public String getPageTitle(int position) {
        try {
            for(Actividad event: events){
                if(event.getStartDate().getTime()<=getTime()
                        && getTime()<=event.getEndDate().getTime()){
                    now=true;
                }
            }
        } catch (NullPointerException e){
            Log.e("THANHNX now = true", e.toString());
        }




        return tabUIs.get(position);









    }





    public static String timeZone()
    {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault());
        String   timeZone = new SimpleDateFormat("Z").format(calendar.getTime());
        return timeZone.substring(0, 3) + ":"+ timeZone.substring(3, 5);
    }

    public Long getTime(){

        Calendar cal = Calendar.getInstance();
        if(timeZone().equals("-03:00")){
            time = cal.getTimeInMillis() - 10800000;
        }
        else if(timeZone().equals("-04:00")){
            time = cal.getTimeInMillis() - 14400000;
        }
        else if(timeZone().equals("-05:00")){
            time = cal.getTimeInMillis() -18000000;
        }
        else if(timeZone().equals("-06:00")){
            time = cal.getTimeInMillis() -21600000;
        }
        else if(timeZone().equals("-07:00")){
            time = cal.getTimeInMillis() -25200000;
        }

        else if(timeZone().equals("-08:00")){
            time = cal.getTimeInMillis() -28800000;

        }
        else if(timeZone().equals("-02:00")){
            time = cal.getTimeInMillis() -7200000;
        }
        else if(timeZone().equals("-01:00")){
            time = cal.getTimeInMillis() -3600000;
        }
        else if(timeZone().equals("00:00")){
            time = cal.getTimeInMillis();
        }

        return time;
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (observer != null) {
            super.unregisterDataSetObserver(observer);
        }
        else{
            Log.i("LOG","LOG");
        }

    }


}