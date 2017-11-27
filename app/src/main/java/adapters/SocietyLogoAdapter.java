package adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.ParseFile;
import com.parse.ParseImageView;

import java.util.ArrayList;
import java.util.List;

import mc.sms.R;
import mc.sms.myApp;

import model.Org;

/**
 * Created by Alvaro on 3/8/15.
 */
public class SocietyLogoAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    public myApp myapp;

    private List<Org> standList = null;
    private ArrayList<Org> arraylist = null;




    //**

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    //

    public SocietyLogoAdapter(Context context,
                              ArrayList<Org> stands) {


        this.context = context;
        this.standList = stands;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(standList);
        myapp = (myApp) context.getApplicationContext();

    }

    public class ViewHolder {
        TextView name;

        RelativeLayout relativeLayout;
        RelativeLayout infoLayout;

        ParseImageView icon;
        public TextView eventSpeakers;
        public TextView name_event;
        public TextView date_event;
        public TextView event_place;
    }

    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Object getItem(int position) {
        return arraylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.cell_event, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.time);
            holder.name.setTextSize(14);
            holder.name.setTextColor(Color.parseColor("#000000"));

            holder.eventSpeakers = (TextView) view.findViewById(R.id.eventSpeakers);
            holder.name_event = (TextView) view.findViewById(R.id.name_event);
            holder.date_event = (TextView) view.findViewById(R.id.date_event);
            holder.event_place = (TextView) view.findViewById(R.id.eventPlace);

           // holder.eventSpeakers.setVisibility(View.GONE);
            holder.name_event.setVisibility(View.GONE);
            holder.date_event.setVisibility(View.GONE);
            holder.event_place.setVisibility(View.GONE);

            //holder.name_event.setTextColor(Color.WHITE);
            holder.relativeLayout = (RelativeLayout) view.findViewById(R.id.Content);
            holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.companySecundario));
            holder.infoLayout = (RelativeLayout) view.findViewById(R.id.info);
            holder.infoLayout.setBackgroundColor(context.getResources().getColor(R.color.companySecundario));
            holder.icon = (ParseImageView) view.findViewById(R.id.icon_event);
            holder.icon.getLayoutParams().height = 150;
            holder.icon.getLayoutParams().width = 150;
          //  RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
           // params.setMargins(0, 0, 0, 0);
            //holder.icon.setLayoutParams(params);


            view.setBackgroundColor(context.getResources().getColor(R.color.companySecundario));

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();

        }


        /*
        if(!myapp.isFirstTime()){
            try {
                standList.get(position).fetchFromLocalDatastore();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                standList.get(position).fetchIfNeeded();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }*/
        if(standList.get(position)!=null){

                holder.eventSpeakers.setText(standList.get(position).getName());
                holder.eventSpeakers.setTextColor(Color.WHITE);
                holder.eventSpeakers.setTextSize(17);
                holder.eventSpeakers.setTypeface(Typeface.DEFAULT_BOLD);

            if(standList.get(position).getimgPerfil()!=null){
                final ParseFile photoFile = standList.get(position).getimgPerfil();
                if (photoFile != null) {
                    //Get singleton instance of ImageLoader
                    ImageLoader imageLoader = ImageLoader.getInstance();
                    //Load the image from the url into the ImageView.
                    imageLoader.displayImage(photoFile.getUrl(), holder.icon);
                }
                else {

                }

            }
            // Si no, no
            else {
                holder.icon.setVisibility(View.GONE);
            }

        }


        return  view;
    }

}
