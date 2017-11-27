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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.ParseFile;
import com.parse.ParseImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import adapters.SocietyLogoAdapter;
import mc.sms.MainActivity;
import mc.sms.R;
import model.Actividad;
import model.Org;

/**
 * Created by Alvaro on 2/19/15.
 */
public class CompanyFragment extends Fragment{

    public static boolean bool;
    public static Org company;
    SocietyLogoAdapter adapter;
    public Button call,map,web,mail,makeFavourite;
    View RootView;
    public String phone,email;
    public ParseImageView hdr;
    public RelativeLayout footer;
    public ListView logoynombre;
    public TextView description;
    public static String gmaps;
   // public static MeetingApp meetingApp;
    public static ProgressBar bar;

    public static CompanyFragment newInstance(Actividad mApp, boolean b) {

        // Instantiate a new fragment
        bool = b;
        //meetingApp = mApp; //Para Alfonso
        CompanyFragment fragment = new CompanyFragment();



        //company= com.getCompany();
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
        if(company!=null) {

            RootView = inflater.inflate(R.layout.eventdetail_layout, container, false);


            hdr = (ParseImageView) RootView.findViewById(R.id.header);
            Toolbar toolbar = (Toolbar) RootView.findViewById(R.id.event_detail_toolbar);
            description = (TextView) RootView.findViewById(R.id.content);

            logoynombre = (ListView) RootView.findViewById(R.id.commonListView);


            ArrayList<Org> stands = new ArrayList<>();
            stands.add(0, company);
            if (stands != null) {
                logoynombre.setVisibility(View.VISIBLE);
                adapter = new SocietyLogoAdapter(getActivity(), stands);
                logoynombre.setAdapter(adapter);
            } else {
                Log.i("NOHAYNA", "SASDF");
                logoynombre.setVisibility(View.GONE);
            }

            footer = (RelativeLayout) RootView.findViewById(R.id.footer);
            //description.setMovementMethod(new ScrollingMovementMethod());
            //TextView companyName = (TextView) RootView.findViewById(R.id.companyname);
            makeFavourite = (Button) RootView.findViewById(R.id.makefavourite);
            call = (Button) RootView.findViewById(R.id.rate);
            web = (Button) RootView.findViewById(R.id.ask);
            mail = (Button) RootView.findViewById(R.id.map);



            if (bool && company != null) {

                toolbar.setNavigationIcon(R.drawable.left);

                toolbar.setTitle(company.getName());
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
            Intent intent = new Intent(getActivity(),
                    MainActivity.class);

            startActivity(intent);
        }

        return RootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        /*
        View v = mTabHost.getTabWidget().getChildAt(0);
        v.setBackgroundResource(R.drawable.programa);
*/

        if(company!=null){

            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int width = displayMetrics.widthPixels;
            int height = displayMetrics.heightPixels;

            hdr.getLayoutParams().height = (height / 3) - dpToPx(55);

            footer.setBackgroundColor(getResources().getColor(R.color.eventSecundario));

            call.setTextColor(Color.WHITE);
            web.setTextColor(Color.WHITE);
            mail.setTextColor(Color.WHITE);
            map.setTextColor(Color.WHITE);
            makeFavourite.setTextColor(Color.WHITE);

            if(Locale.getDefault().getLanguage().equals("en")){
                call.setText("Call");
                web.setText("Web");
                mail.setText("Mail");
                map.setText("Map");

            }
            else {
                call.setText("Call");
                web.setText("Web");
                mail.setText("Mail");
                map.setText("Map");

            }

            makeFavourite.setVisibility(View.GONE);
            map.setVisibility(View.INVISIBLE);

            call.getLayoutParams().width = (width / 4);
            web.getLayoutParams().width = (width / 4);
            mail.getLayoutParams().width = (width / 4);

            final String phone = company.getPhone();
            final String email = company.getMail();
//            if(company.getLocation()!=null){
//                if(company.getLocation().getGeoPoint()!=null){
//                    gmaps = company.getLocation().getGeoPoint();
//                }
//
//            }
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
                    String url = company.getWeb();
                    if(url!=null){
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



            if (company!= null) {


                ParseFile header = company.getimgFondo();
                if (header != null) {
                    //Get singleton instance of ImageLoader
                    ImageLoader imageLoader = ImageLoader.getInstance();
                    //Load the image from the url into the ImageView.
                    imageLoader.displayImage(header.getUrl(), hdr);
                }


                //lgo.setParseFile(logo);
                //lgo.loadInBackground();

                description.setText(company.getDetails()+"\n"+"\n"+"\n"+"\n");
                description.setMovementMethod(new ScrollingMovementMethod());
                //companyName.setText(company.getCompany().getName());
            } else {

            }


            //startLoading();

        }
        else{
            Log.i("NO HAY HEADER2","LOG");
            Log.i("LOG","LOG");
        }

        if(company!=null){
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
            Log.i("LOGmm","LOG");
            Intent intent = new Intent(getActivity(),
                    MainActivity.class);

            startActivity(intent);
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