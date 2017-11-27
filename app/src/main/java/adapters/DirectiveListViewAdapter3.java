package adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.ParseFile;
import com.parse.ParseImageView;

import java.util.ArrayList;
import java.util.List;

import imageTreatment.RoundedImageView;
import mc.sms.R;
import mc.sms.myApp;
import model.PersonaRolOrg;

/**
 * Created by Alvaro on 2/21/15.
 */
public class DirectiveListViewAdapter3 extends BaseAdapter  {

    Context context;
    LayoutInflater inflater;
    Boolean bool;
    public myApp myapp;
    private List<PersonaRolOrg> actorList= null;
    private ArrayList<PersonaRolOrg> arraylist;
    public ArrayList<PersonaRolOrg> orig;
    private int bgColor = Color.parseColor("#ffffff");

    //**
    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
    //


    public DirectiveListViewAdapter3(Context context,
                                List<PersonaRolOrg> actorList, boolean b) {

        bool = b;






        this.context = context;
        this.actorList = actorList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(actorList);
        myapp = (myApp) context.getApplicationContext();

    }


    public class ViewHolder {
        TextView name_speaker;
        TextView charge_speaker;
        TextView country_speaker;
        TextView company_speaker;
        RoundedImageView icon_speaker;
        ParseImageView icon;
        public RelativeLayout relativeLayout,rl;
        public FrameLayout fl;
    }
    @Override
    public int getCount() {
        return actorList.size();
    }

    @Override
    public Object getItem(int position) {
        return actorList.get(position);
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
            holder.name_speaker = (TextView) view.findViewById(R.id.name_event);
            holder.country_speaker = (TextView) view.findViewById(R.id.time);
            holder.charge_speaker = (TextView) view.findViewById(R.id.eventPlace);
            holder.company_speaker = (TextView) view.findViewById(R.id.eventSpeakers);
            // Locate the ImageView in listview_item.xml
            holder.icon_speaker = (RoundedImageView) view.findViewById(R.id.icon_speaker);
            holder.icon = (ParseImageView) view.findViewById(R.id.icon_event);
            holder.relativeLayout = (RelativeLayout) view.findViewById(R.id.Content);
            holder.fl = (FrameLayout) view.findViewById(R.id.frame);
            holder.rl = (RelativeLayout) view.findViewById(R.id.info);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        /*
        if(!myapp.isFirstTime()){
            try {
                actorList.get(position).fetchFromLocalDatastore();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                actorList.get(position).fetchIfNeeded();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }*/

        String firstName = actorList.get(position).getPerson().getFirstName();
        String lastName = actorList.get(position).getPerson().getLastName();

        if(actorList.get(position).getPerson().getSalutation()!=null){
            String salutation = actorList.get(position).getPerson().getSalutation();
            holder.name_speaker.setText(salutation+ " " +firstName + " " + lastName);
        }
        else {
            holder.name_speaker.setText(firstName + " " + lastName);
        }

        //El booleano sirve para verificar si es celda directiva de lista directiva o celda que aparece en eventodetalle
        //si el booleano es falso quiere decir que es la celda que se mostrará en evento detalle, la diferencia con
        //directiva es que en directiva solo se muestra el cargo.
        if(bool){
            holder.charge_speaker.setText(actorList.get(position).getRol());
        }
        else{
            holder.fl.setBackgroundColor(context.getResources().getColor(R.color.companySecundario));
            holder.rl.setBackgroundColor(context.getResources().getColor(R.color.companySecundario));
            holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.companySecundario));
            holder.charge_speaker.setTextColor(bgColor);
            holder.company_speaker.setTextColor(bgColor);
            holder.name_speaker.setTextColor(bgColor);
            holder.country_speaker.setVisibility(View.GONE);
            holder.charge_speaker.setText(actorList.get(position).getRol());

            if(actorList.get(position).getOrg()==null){


            }

            else{
                if(actorList.get(position).getPerson()!=null){
                    if(actorList.get(position).getOrg()!=null){
                        holder.company_speaker.setText(actorList.get(position).getOrg().getName());
                        if(actorList.get(position).getPerson().getPlace()!=null){
                            if(actorList.get(position).getPerson().getPlace().getName()!=null){
                                holder.country_speaker.setText(actorList.get(position).getPerson().getPlace().getName());
                            }

                        }
                    }


                }


                Log.i("LOG", "LOG");
            }


            //**

            //

        }


        if(actorList.get(position).getPerson().getImage()!=null){

            holder.icon.setBackgroundColor(context.getResources().getColor(R.color.companySecundario));

            holder.icon_speaker.setVisibility(View.VISIBLE);
            holder.icon_speaker.getLayoutParams().width = dpToPx(45);
            holder.icon_speaker.getLayoutParams().height = dpToPx(45);
            ParseFile photoFile = actorList.get(position).getPerson().getImage();
            if (photoFile != null) {
                //Get singleton instance of ImageLoader
                ImageLoader imageLoader = ImageLoader.getInstance();
                //Load the image from the url into the ImageView.
                imageLoader.displayImage(photoFile.getUrl(), holder.icon_speaker);
            }
            else {

            }

        }
        else{
            Log.i("LOG","LOG");
        }



        return view;
    }
}