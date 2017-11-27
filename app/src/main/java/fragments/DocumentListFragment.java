package fragments;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.ParseObject;

import java.util.List;
import java.util.Locale;

import adapters.GridDocumentsAdapter;
import mc.sms.R;
import model.Media;

/**
 * Created by Alvaro on 3/8/15.
 */
public class DocumentListFragment extends Fragment {

    public static List<Media> documents;
    GridDocumentsAdapter adapter;
    public static DocumentListFragment newInstance(List<Media> files) {

        // Instantiate a new fragment
        documents = files;
        DocumentListFragment fragment = new DocumentListFragment();
        fragment.setRetainInstance(true);
        return fragment;

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View RootView = inflater.inflate(R.layout.documentslistlayout, container , false);
        final ListView listView = (ListView) RootView.findViewById(R.id.documentListView);// crear el
        // gridview a partir del elemento del xml gridview

        adapter = new GridDocumentsAdapter(getActivity(),R.layout.cell_document,documents);
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


        Toolbar toolbar = (Toolbar) RootView.findViewById(R.id.doctoolbar);
        if(Locale.getDefault().getLanguage().equals("en")){
            toolbar.setTitle("Documentos");
        }
        else {
            toolbar.setTitle("Documentos");
        }

        toolbar.setNavigationIcon(R.drawable.left);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();

            }
        });
        toolbar.setBackgroundColor(getResources().getColor(R.color.companySecundario));

        return  RootView;

    }


}
