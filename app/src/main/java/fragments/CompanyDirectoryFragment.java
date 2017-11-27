package fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.ParseImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import adapters.SocietyLogoAdapter;
import mc.sms.R;
import model.Org;

/**
 * Created by Alvaro on 2/19/15.
 */
public class CompanyDirectoryFragment extends Fragment{

    public static boolean bool;
    public static Org com;
    public static String gmaps;
    SocietyLogoAdapter adapter;
    View RootView;
    public Button call,map,web,mail,makeFavourite;
    public String phone,email;
    public ParseImageView hdr;
    public RelativeLayout footer;
    public ListView logoynombre,listview;
    public TextView description;


    public static CompanyDirectoryFragment newInstance(Org company,boolean b) {

        // Instantiate a new fragment
        bool = b;

        CompanyDirectoryFragment fragment = new CompanyDirectoryFragment();



        com= company;
        Log.i("FRAGMENTID", String.valueOf(fragment.getId()));

        return fragment;

    }

    @Override
    public void onAttach(Activity activity) {


        super.onAttach(activity);

        //Log.i("COMPANYFRAGMENT2", String.valueOf(MainActivity.stand.getDescription()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(com!=null) {

            RootView = inflater.inflate(R.layout.eventdetail_layout, container, false);


            hdr = (ParseImageView) RootView.findViewById(R.id.header);
            Toolbar toolbar = (Toolbar) RootView.findViewById(R.id.event_detail_toolbar);
            description = (TextView) RootView.findViewById(R.id.content);
            listview = (ListView) RootView.findViewById(R.id.speakers_list_view);
            listview.setVisibility(View.GONE);

            logoynombre = (ListView) RootView.findViewById(R.id.commonListView);


            footer = (RelativeLayout) RootView.findViewById(R.id.footer);
            //description.setMovementMethod(new ScrollingMovementMethod());
            //TextView companyName = (TextView) RootView.findViewById(R.id.companyname);
            makeFavourite = (Button) RootView.findViewById(R.id.makefavourite);
            call = (Button) RootView.findViewById(R.id.map);
            web = (Button) RootView.findViewById(R.id.rate);
            mail = (Button) RootView.findViewById(R.id.ask);
            //map = (Button) RootView.findViewById(R.id.checkin);
//            map.setVisibility(View.GONE);


            ArrayList<Org> stands = new ArrayList<>();

            stands.add(0, com);
            if (stands != null) {
                logoynombre.setVisibility(View.VISIBLE);
                adapter = new SocietyLogoAdapter(getActivity(), stands);
                logoynombre.setAdapter(adapter);
            } else {
                Log.i("NOHAYNA", "SASDF");
                logoynombre.setVisibility(View.GONE);
            }

            //startLoading();

            if (bool && com != null) {

                toolbar.setNavigationIcon(R.drawable.left);

                toolbar.setTitle(com.getName());
                toolbar.setTitleTextColor(Color.WHITE);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().onBackPressed();

                    }
                });
                toolbar.setBackgroundColor(getResources().getColor(R.color.companySecundario));
            } else {
                toolbar.setVisibility(View.GONE);
            }

        }
        else {

        }

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



        if (com != null) {
            hdr.setVisibility(View.GONE);

            /*if(com.getimgFondo()!=null){
                hdr.setVisibility(View.GONE);

                ParseFile header = com.getimgFondo();
                if (header != null) {
                    //Get singleton instance of ImageLoader
                    ImageLoader imageLoader = ImageLoader.getInstance();
                    //Load the image from the url into the ImageView.
                    imageLoader.displayImage(header.getUrl(), hdr);
                }
                else{
                    Log.i("NO HAY HEADER1","LOG");
                    Log.i("LOG","LOG");
                }
            }

            else{
                hdr.setVisibility(View.GONE);
                //hdr.setImageDrawable(null);
                Log.i("NO HAY HEADER0","LOG");
            }*/

            //ParseFile logo = com.getLogo().getParseFileV1();

            //lgo.setParseFile(logo);
            //lgo.loadInBackground();

            description.setText(com.getDetails()+"\n"+"\n"+"\n"+"\n");
            description.setMovementMethod(new ScrollingMovementMethod());
            //companyName.setText(company.getCompany().getName());

            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int width = displayMetrics.widthPixels;
            int height = displayMetrics.heightPixels;

            hdr.getLayoutParams().height = (height / 4) - dpToPx(55);

            footer.setBackgroundColor(getResources().getColor(R.color.companySecundario));


            call.setTextColor(Color.WHITE);
            web.setTextColor(Color.WHITE);
            mail.setTextColor(Color.WHITE);
           // map.setTextColor(Color.WHITE);
            makeFavourite.setTextColor(Color.WHITE);


            if(Locale.getDefault().getLanguage().equals("en")){
                call.setText("Call");
                web.setText("Web");
                mail.setText("Mail");
            //    map.setText("Map");

            }
            else {
                call.setText("Call");
                web.setText("Web");
                mail.setText("Mail");
                //map.setText("Map");

            }
            makeFavourite.setVisibility(View.GONE);
            call.setVisibility(View.GONE);
           // call.getLayoutParams().width = (width / 4);
            web.getLayoutParams().width = (width / 2);
            mail.getLayoutParams().width = (width / 2);
           // map.getLayoutParams().width = (width / 4);




            Log.i("MAIL", String.valueOf(com.getMail()));
            final String phone = com.getPhone();

            final String email = com.getMail();
            /*
            if(com.getLocation()==null){
                Log.i("LOG","LOG");
            }

            else{
                if(com.getLocation().getGooglemaps()==null){

                }
                else {
                    gmaps = com.getLocation().getGooglemaps();

                }

            }
            */
            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(phone!=null){
                        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                        phoneIntent.setData(Uri.parse("tel:" + phone + ""));
                        startActivity(phoneIntent);
                    }

                }
            });

            web.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(com.getWeb()!=null){
                        String url = com.getWeb();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                }
            });


            mail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(email!=null){
                        Intent mailClient = new Intent(Intent.ACTION_SENDTO);
                        mailClient.setData(Uri.parse("mailto:"));
                        mailClient.putExtra(Intent.EXTRA_SUBJECT, "subject");
                        mailClient.putExtra(Intent.EXTRA_EMAIL,new String[] { ""+email+"" } );
                        startActivity(mailClient);
                    }

                }
            });

            /*
            map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(gmaps!=null){
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                Uri.parse( ""+gmaps+""));
                        intent.setPackage("com.google.android.apps.maps");
                        startActivity(intent);
                    }
                }
            });
            */
        }
        else{
            Log.i("NO HAY HEADER2","LOG");
            Log.i("LOG","LOG");

        }


        if(com!=null){
            getView().setFocusableInTouchMode(true);
            getView().requestFocus();
            getView().setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                        // handle back button's click listener
                        if(bool){
                            //((ActionBarActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.directorio);
                            getActivity().onBackPressed();
                        }
                        else {
                            return  true;
                        }


                        return true;
                    }
                    return false;
                }
            });
        }

        else{
            Log.i("LOG","LOG");
        }


    }

    @Override
    public void onPause() {
        super.onPause();



    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {



    }

    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for(Fragment fragment : fragments){
            if(fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
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



    @Override
    public void onDestroy()
    {
        WorkaroundSavedState.savedInstanceState = null;
        super.onDestroy();
    }

    public static final class WorkaroundSavedState {
        public static Bundle savedInstanceState;
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

}