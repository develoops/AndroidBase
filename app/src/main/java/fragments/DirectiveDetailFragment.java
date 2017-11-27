package fragments;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import adapters.DirectiveListViewAdapter3;
import mc.sms.R;
import model.PersonaRolOrg;

/**
 * Created by Alvaro on 3/1/15.
 */
public class DirectiveDetailFragment extends Fragment {

    public static PersonaRolOrg actorEvent;
    public static Boolean bool;
    DirectiveListViewAdapter3 adapter;
    public ListView eventsofSpeaker,speakercell;
    public TextView speaker_bio;

    public static DirectiveDetailFragment newInstance(PersonaRolOrg actor, boolean b) {

        // Instantiate a new fragment
        bool = b;
         //Para Alfonso
        DirectiveDetailFragment fragment = new DirectiveDetailFragment();
        actorEvent = actor;

        fragment.setRetainInstance(true);
        return fragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View RootView = inflater.inflate(R.layout.eventdetail_layout, container , false);
        //TextView speaker_name = (TextView)RootView.findViewById(R.id.speaker_name);
        //TextView institution = (TextView)RootView.findViewById(R.id.institution);
        //TextView country = (TextView)RootView.findViewById(R.id.country);

        speakercell = (ListView) RootView.findViewById(R.id.commonListView);
        speaker_bio = (TextView)RootView.findViewById(R.id.content);
        RelativeLayout footer = (RelativeLayout)RootView.findViewById(R.id.footer);
        //RoundedImageView image = (RoundedImageView) RootView.findViewById(R.id.image_speaker);
        eventsofSpeaker = (ListView)RootView.findViewById(R.id.events_list_view);
        Log.i("ASD",String.valueOf(actorEvent));
        //speaker_name.setText(actorEvent.getPerson().getSalutation() + " " + actorEvent.getPerson().getFirstName() + " " + actorEvent.getPerson().getLastName());
        //institution.setText(actorEvent.getCompany().getName());
        footer.setVisibility(View.GONE);

        //country.setText(actorEvent.getCompany().getCountry());
        //ParseFile photoFile = actorEvent.getPerson().getProfilePicture();
        //image.setParseFile(photoFile);
        //image.loadInBackground();



        Toolbar toolbar = (Toolbar) RootView.findViewById(R.id.event_detail_toolbar);

            toolbar.setTitle(actorEvent.getPerson().getSalutation()+ " " +actorEvent.getPerson().getFirstName()+ " " +
                    actorEvent.getPerson().getLastName());


        toolbar.setNavigationIcon(R.drawable.left);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();

            }
        });
        toolbar.setBackgroundColor(getResources().getColor(R.color.companySecundario));

        return RootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        ArrayList<PersonaRolOrg> speakers = new ArrayList<>();
        speakers.add(0,actorEvent);
        adapter = new DirectiveListViewAdapter3(getActivity(),speakers,false);
        speakercell.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    @Override
    public void onResume() {
        super.onResume();
        /*
        View v = mTabHost.getTabWidget().getChildAt(0);
//        v.setBackgroundResource(R.drawable.programa);
//*/      speaker_bio.setText(actorEvent.getPerson().getBio());
//        if(actorEvent.getPerson().getEvents()!=null){
//            if(actorEvent.getPerson().getEvents().size()>0){
//                HetpinProgramListViewAdapter adapter = new HetpinProgramListViewAdapter(getActivity(),actorEvent.getEvents(),meetingApp,false,true);
//                eventsofSpeaker.setAdapter(adapter);
//                eventsofSpeaker.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        ParseObject object = (ParseObject)(eventsofSpeaker.getItemAtPosition(position));
//                        Event event = ParseObject.createWithoutData(Event.class, object.getObjectId());
//                        Fragment fragment = EventDetailFragment.newInstance(event, meetingApp);
//                        final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                        ft.replace(R.id.container, fragment);
//                        ft.addToBackStack(null);
//                        ft.commit();
//                    }
//                });
//            }
//        }
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                Log.i("HOLA",String.valueOf(keyCode));
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    // handle back button's click listener

                    if(bool){
                        getActivity().onBackPressed();
                    }
                    else {
                        getActivity().onBackPressed();
                    }


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


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {

            case android.R.id.home:

                if(bool){
                    getActivity().onBackPressed();
                }
                else {
                    getActivity().onBackPressed();
                }


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
