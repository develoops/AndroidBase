package fragments;


import android.app.Activity;
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

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import adapters.GridDocumentsAdapter;
import mc.sms.R;
import model.Actividad;
import model.Media;

/**
 * Created by Alvaro on 2/25/15.
 */
public class MoreFragment extends Fragment {

    public static Actividad mApp;

    public static ListView listView;
    //ArrayList<MobiFile> mFiles = new ArrayList<>();
    GridDocumentsAdapter adapter;
    public static List<Media> docs = new ArrayList<>();
    public static List<Media> medias = new ArrayList<>();



    public static MoreFragment newInstance(Actividad meetingApp,List<Media> media) {

        // Instantiate a new fragment
        mApp= meetingApp; //Alfonso
        MoreFragment fragment = new MoreFragment();
        docs=media;
        //fragment.setRetainInstance(true);
        return fragment;

    }

    @Override
    public void onAttach(Activity activity) {


        super.onAttach(activity);




    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retain this fragment across configuration changes.
        setRetainInstance(true);


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View RootView = inflater.inflate(R.layout.common_list_layout, container , false);
        listView = (ListView) RootView.findViewById(R.id.commonListView);/// crear el
        // gridview a partir del elemento del xml gridview
        //final Button button = (Button) RootView.findViewById(R.id.comercialmap);

        //button.setText("Mapa Comercial");
        if(mApp!=null){

        }



        adapter = new GridDocumentsAdapter(getActivity(),R.layout.cell_document,docs);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ParseObject object = (ParseObject)(listView.getItemAtPosition(position));
                Media mobiFile= ParseObject.createWithoutData(Media.class, object.getObjectId());
                Fragment fragment = DocumentDetailFragment.newInstance(mobiFile);
                final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container,fragment);
                ft.addToBackStack(null);
                ft.commit();
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



        /*
        query.whereEqualTo("subtype","gallery");
        query.findInBackground(new FindCallback<MobiFile>() {
            @Override
            public void done(List<MobiFile> mobiFiles, ParseException e) {
                    mFiles = (ArrayList<MobiFile>) mobiFiles;

                gridview.setAdapter(new GridGalleryAdapter(getActivity(),mFiles));
                gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                        MobiFile mobiFile = (MobiFile)(gridview.getItemAtPosition(position));

                        final Dialog dialogo = new Dialog(getActivity());
                        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        dialogo.setContentView(R.layout.map_box_layout);
                        final Button done = (Button) dialogo.findViewById(R.id.btn_done_image_dialog);
                        TouchImageView mapadialog = (TouchImageView) dialogo.findViewById(R.id.image_dialog);
                        mapadialog.setMaxZoom(3f);
                        mapadialog.setMinZoom(1f);
                        if (mobiFile!= null) {
                            ImageLoader imageLoader = ImageLoader.getInstance();
                            //Load the image from the url into the ImageView.
                            imageLoader.displayImage(mobiFile.getParseFileV1().getUrl(), mapadialog);
                        }


                        dialogo.getWindow().getAttributes().width = RelativeLayout.LayoutParams.MATCH_PARENT;
                        done.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogo.dismiss();

                            }
                        });

                        dialogo.show();

                    }

                });
            }
        });*/







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
}
