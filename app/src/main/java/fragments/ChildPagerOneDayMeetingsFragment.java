package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mc.sms.R;
import model.Actividad;

/**
 * Created by Hetpin on 3/15/15.
 */
public class ChildPagerOneDayMeetingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.oneday_child_pager, container, false);
        String title = getArguments().getString("content");
        TextView tv = (TextView)rootView.findViewById(R.id.thanhnx_child_program);
        tv.setText(title);
        return rootView;
    }
    public static ChildPagerOneDayMeetingsFragment newInstance(List<Actividad> eventList, int position) {

        // Instantiate a new fragment

        ChildPagerOneDayMeetingsFragment fragment = new ChildPagerOneDayMeetingsFragment();
        Bundle args = new Bundle();
        args.putString("content", "Child " + position);
        fragment.setArguments(args);
        fragment.setRetainInstance(true);
        return fragment;
    }

}