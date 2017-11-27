package fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import adapters.GridMediaAdapter;
import mc.sms.R;
import model.Media;

/**
 * Created by Alvaro on 2/25/15.
 */
public class MoreCompanyFragment extends Fragment {

    public static List<Media> objects;
    ArrayList<Media> documents = new ArrayList<>();
    ArrayList<Media> videos = new ArrayList<>();
    ArrayList<Media> images = new ArrayList<>();
    ArrayList<Media> audios = new ArrayList<>();


    public static MoreCompanyFragment newInstance(List<Media> mobiFiles) {

        // Instantiate a new fragment
        objects = mobiFiles;
        MoreCompanyFragment fragment = new MoreCompanyFragment();
        fragment.setRetainInstance(true);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View RootView = inflater.inflate(R.layout.documents_gridlayout, container , false);
        final ListView gridview = (ListView) RootView.findViewById(R.id.gridview);// crear el
        // gridview a partir del elemento del xml gridview
        Log.i("FAVORITOS", "LASDJ");

        gridview.setAdapter(new GridMediaAdapter(getActivity(),objects));


        if(objects!=null){

            for (Media mobiFile:objects){
                String fileName = mobiFile.getDescripcion();
                if(fileName.equals("doc")){

                    documents.add(mobiFile);
                }
                else if(fileName.equals("imagen")){

                    images.add(mobiFile);
                }

                else if(fileName.equals("audio")){

                    audios.add(mobiFile);
                }

                else {


                    videos.add(mobiFile);

                }

            }


        }
        else {

        }

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Log.i("ITEM",String.valueOf(position));

                if(position == 0 && documents!=null){
                    Fragment fragment = DocumentListFragment.newInstance(documents);
                    final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.container,fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }

                else if(position == 2 && videos!=null) {



                }



            }
        });

        return RootView;
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

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener


                    return true;
                }
                return false;
            }
        });

    }
    @Override
    public void onStop(){
        super.onStop();
        documents = new ArrayList<>();
    }

}
