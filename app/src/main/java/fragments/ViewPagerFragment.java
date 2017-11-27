package fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import adapters.PagerFragmentAdapter;
import fragments.sliding_tab.SlidingTabLayout;
import mc.sms.R;

/**
 * Created by Alvaro on 3/3/15.
 */
public class ViewPagerFragment extends Fragment {
    PagerFragmentAdapter pageAdapter;
    public List <String> Tabs = new ArrayList<>();
    public Integer Numboftabs;
    private SlidingTabLayout mSlidingTabLayout;
    public ViewPager pager;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Tabs.add("Congresos");
        Tabs.add("Sociedad");
        Tabs.add("Directiva");
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(getClass().getName(), "onCreateView");
        View v = inflater.inflate(R.layout.activity_text, container, false);
        pager=(ViewPager)v.findViewById(R.id.pager);
        mSlidingTabLayout = (SlidingTabLayout) v.findViewById(R.id.tabs);
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setBackgroundColor(getResources().getColor(R.color.companySecundario));

        //((ActionBarActivity)getActivity()).setSupportActionBar(toolbar);

        if (Locale.getDefault().getLanguage().equals("en")){
            toolbar.setTitle("SMS2017");
        }
        else {
            toolbar.setTitle("SMS2017");
        }

        toolbar.setTitleTextColor(Color.WHITE);




        //pager.getAdapter().notifyDataSetChanged();



        //**

        // insert all tabs from pagerAdapter data

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {

        super.onResume();




            Numboftabs =Tabs.size();

//            Collections.sort(Tabs, new Comparator<model.View>() {
//                @Override
//                public int compare(model.View lhs, model.View rhs) {
//                    return lhs.getsortingAux()- rhs.getsortingAux();
//
//                }
//            });

            Log.e(getClass().getName(), "end onCreateView");
            pageAdapter = new PagerFragmentAdapter(getChildFragmentManager(),Tabs,Numboftabs);

            pager.setOffscreenPageLimit(4);
            pager.setAdapter(pageAdapter);
            mSlidingTabLayout.setDistributeEvenly(true);
            mSlidingTabLayout.setBackgroundColor(Color.WHITE);

            mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {

                @Override
                public int getIndicatorColor(int position) {
                    return getResources().getColor(R.color.companySecundario);
                }


                public int getDividerColor(int position) {
                    return Color.DKGRAY;
                }
            });

            //


            mSlidingTabLayout.setViewPager(pager);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }







}