package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import adapters.ChildPagerMeetingsAdapter;
import mc.sms.R;
import model.Actividad;
import utils.MUtil;
import views.CustomViewPager;


/**
 * Created by Hetpin on 3/15/15.
 */
public class ChildPagerMeetingsNowFragment extends Fragment implements View.OnClickListener {

    private ChildPagerMeetingsAdapter pg_adapter;
    private static CustomViewPager parents_pager;
    public static ViewPager viewPager;
    public static Actividad mApp;
    private ImageButton left, right;
    private Map<String,List<Actividad>> map;
    private List<String> keys;
    public static List <Actividad> eventList;


    public static ChildPagerMeetingsNowFragment newInstance(Actividad meetingApp, List <Actividad> events, CustomViewPager custom_pager) {
        // Instantiate a new fragment
        ChildPagerMeetingsNowFragment fragment = new ChildPagerMeetingsNowFragment();
        mApp = meetingApp;
        eventList = events;
        parents_pager = custom_pager;
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View RootView = inflater.inflate(R.layout.fragment_childpager, container, false);
        setHasOptionsMenu(true);
        left = (ImageButton) RootView.findViewById(R.id.btnBackDay);
        right = (ImageButton) RootView.findViewById(R.id.btnNextDay);
        left.setOnClickListener(this);
        right.setOnClickListener(this);



        viewPager = (ViewPager) RootView.findViewById(R.id.viewpager);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                parents_pager.f5SwipeStateFromChild(position);
                setNaviVisibility(position);
            }
        });

        return RootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mApp!=null){
            keys = new ArrayList<>();
            map = MUtil.divideEventByGroup(eventList, keys);
            pg_adapter = new ChildPagerMeetingsAdapter(getChildFragmentManager(), mApp, viewPager, map, keys);
            viewPager.setAdapter(pg_adapter);
            setNaviVisibility(0);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNextDay:
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                break;

            case R.id.btnBackDay:
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                break;

        }
    }

    public void setNaviVisibility(int selected) {
        if (MUtil.logThanh)
            Log.e("THANH", "setNaviVisibility " + selected + " keysize = " + keys.size());
        if (selected == 0) {
            left.setVisibility(View.INVISIBLE);
            right.setVisibility(View.VISIBLE);
        } else {
            if (selected == (keys.size()-1)) {
                left.setVisibility(View.VISIBLE);
                right.setVisibility(View.INVISIBLE);
            } else {
                left.setVisibility(View.VISIBLE);
                right.setVisibility(View.VISIBLE);
            }
        }
    }

}

