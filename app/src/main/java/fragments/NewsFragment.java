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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import adapters.NewsListViewAdapter;
import mc.sms.R;
import mc.sms.myApp;
import model.Info;


/**
 * Created by Alvaro on 3/1/15.
 */
public class NewsFragment extends Fragment {

    ListView listview;
    public myApp myapp;
    NewsListViewAdapter adapter;
    public static List<Info> newList;

    public static NewsFragment newInstance(List <Info> news) {

        // Instantiate a new fragment

        NewsFragment fragment = new NewsFragment();

        newList = news;



        fragment.setRetainInstance(true);

        return fragment;

    }


    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);





    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);



        this.myapp = (myApp) getActivity().getApplicationContext();
        final View v= inflater.inflate(R.layout.news_layout, container , false);
        listview = (ListView)v.findViewById(R.id.commonListView);
        if(newList!=null){
            Log.i("NEWSSSS", String.valueOf(newList));
            adapter = new NewsListViewAdapter(getActivity(),newList);
            // Binds the Adapter to the ListView
            listview.setAdapter(adapter);
        }



        listview.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        Toolbar toolbar = (Toolbar)v.findViewById(R.id.toolbars);
        toolbar.setNavigationIcon(R.drawable.left);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Noticias");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();

            }
        });
        toolbar.setBackgroundColor(getResources().getColor(R.color.companySecundario));
        //startLoading();

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();


        Log.i("HOLA", "HOLA");

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

                Log.i("HOLA",String.valueOf(keyCode));
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    // handle back button's click listener
                    getActivity().onBackPressed();
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {



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

}

