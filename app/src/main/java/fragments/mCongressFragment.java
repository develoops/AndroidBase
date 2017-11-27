package fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.ParseFile;
import com.parse.ParseImageView;

import mc.sms.R;
import model.Org;

/**
 * Created by Alvaro on 2/19/15.
 */
public class mCongressFragment extends Fragment{

    public static Org mCongress;

    public static mCongressFragment newInstance(Org mobiCongress) {

        // Instantiate a new fragment


        mCongressFragment fragment = new mCongressFragment();

        mCongress = mobiCongress;
        fragment.setRetainInstance(true);

        return fragment;

    }

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);

        //Log.i("COMPANYFRAGMENT2", String.valueOf(MainActivity.stand.getDescription()));



    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View RootView = inflater.inflate(R.layout.companyfragment, container , false);
        ParseImageView hdr = (ParseImageView) RootView.findViewById(R.id.header);
        ParseImageView lgo = (ParseImageView) RootView.findViewById(R.id.logo);
        TextView description = (TextView) RootView.findViewById(R.id.description);
        TextView companyName = (TextView) RootView.findViewById(R.id.companyname);

        if(mCongress!=null){


            ParseFile header = mCongress.getimgFondo();
            ParseFile logo = mCongress.getimgPerfil();
            if (header != null) {
                //Get singleton instance of ImageLoader
                ImageLoader imageLoader = ImageLoader.getInstance();
                //Load the image from the url into the ImageView.
                imageLoader.displayImage(header.getUrl(), hdr);
            }
            else{
                Log.i("LOG","LOG");
            }

            if (logo != null) {
                //Get singleton instance of ImageLoader
                ImageLoader imageLoader2 = ImageLoader.getInstance();
                //Load the image from the url into the ImageView.
                imageLoader2.displayImage(logo.getUrl(), lgo);
            }
            else{
                Log.i("LOG","LOG");
            }

            Log.i("DES",String.valueOf(description));
            description.setText(mCongress.getDetails());
            companyName.setText(mCongress.getName());

        }
        else{
            Log.i("LOG","LOG");
        }

        //startLoading();

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

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    // handle back button's click listener

                    return true;
                }
                return false;
            }
        });

    }
}
