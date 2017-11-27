package adapters;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.ParseFile;

import java.util.ArrayList;
import java.util.List;

import imageTreatment.RoundedImageView;
import mc.sms.R;
import mc.sms.myApp;
import model.PersonaRolOrg;

/**
 * Created by Alvaro on 2/21/15.
 */
public class DirectiveListViewAdapter extends BaseAdapter  {

    Context context;
    LayoutInflater inflater;
    Boolean bool;
    public myApp myapp;
    private List<PersonaRolOrg> actorList= null;
    private ArrayList<PersonaRolOrg> arraylist;
    public ArrayList<PersonaRolOrg> orig;
    public String firstName;
    public String lastName;

    //**
    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
    //


    public DirectiveListViewAdapter(Context context,
                                    List<PersonaRolOrg> actorList, boolean b) {

        bool = b;
        myapp = (myApp) context.getApplicationContext();






        this.context = context;
        this.actorList = actorList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(actorList);

    }



    public class ViewHolder {
        TextView name_speaker;
        TextView charge_speaker;
        TextView country_speaker;
        TextView company_speaker;
        RoundedImageView icon_speaker;
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
            view = inflater.inflate(R.layout.cell_speaker, null);
            // Locate the TextViews in listview_item.xml
            holder.name_speaker = (TextView) view.findViewById(R.id.name_speaker);
            holder.country_speaker = (TextView) view.findViewById(R.id.country);
            holder.charge_speaker = (TextView) view.findViewById(R.id.charge);
            holder.company_speaker = (TextView) view.findViewById(R.id.company);
            // Locate the ImageView in listview_item.xml
            holder.icon_speaker = (RoundedImageView) view.findViewById(R.id.icon_speaker);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
            holder.name_speaker.setText("");
            holder.charge_speaker.setText("");
            holder.country_speaker.setText("");
            holder.company_speaker.setText("");
            holder.icon_speaker.setImageResource(R.drawable.speaker_nofoto);

        }

        /*
        if(myapp.isFirstTime()){
            try {
                actorList.get(position).fetchIfNeeded();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
*/
            Log.i("ACTORLIST3",String.valueOf(actorList.get(position).getObjectId()));
            if(actorList.get(position).getPerson()!=null){
                if(actorList.get(position).getPerson().getFirstName()!=null){  //THIS LINE
                    firstName = actorList.get(position).getPerson().getFirstName();
                }
                else{
                    Log.i("NOTIENEFIRST", "LOG");
                }

                if(actorList.get(position).getPerson().getLastName()!=null ||
                        !actorList.get(position).getPerson().getLastName().isEmpty()){
                    lastName = actorList.get(position).getPerson().getLastName();
                }
                else{
                    Log.i("NOTIENELAST","LOG");
                }

                if(actorList.get(position).getPerson().getSalutation()!=null ||
                        !actorList.get(position).getPerson().getSalutation().isEmpty()){
                    String salutation = actorList.get(position).getPerson().getSalutation();
                    holder.name_speaker.setText(salutation+ " " +firstName + " " + lastName);
                }
                else {
                    holder.name_speaker.setText(firstName + " " + lastName);
                }
            }

            else {
                Log.i("ACTOR3",actorList.get(position).getObjectId());
                Log.i("MARINA",actorList.get(position).getRol());
            }







        //El booleano sirve para verificar si es celda directiva de lista directiva o celda que aparece en eventodetalle
        //si el booleano es falso quiere decir que es la celda que se mostrará en evento detalle, la diferencia con
        //directiva es que en directiva solo se muestra el cargo.
        if(bool){
            if(actorList.get(position).getRol()!=null){
                holder.charge_speaker.setText(actorList.get(position).getRol());
            }
            else{
                Log.i("LOG","LOG");
            }

            if(actorList.get(position).getPerson().getPlace()!=null){
                holder.company_speaker.setVisibility(View.VISIBLE);
                holder.company_speaker.setText(actorList.get(position).getPerson().getPlace().getName());
                Log.i("PASOOOO","CAPTCHITA");
            }
            else{

            }

        }
        else{
            holder.country_speaker.setVisibility(View.GONE);
            if(actorList.get(position).getRol()!=null){
                holder.charge_speaker.setText(actorList.get(position).getRol());
            }
            else{
                Log.i("LOG","LOG");
            }
            if(actorList.get(position).getOrg()!=null){
                if(actorList.get(position).getOrg().getName()!=null &&
                        !actorList.get(position).getOrg().getName().isEmpty()){
                    holder.company_speaker.setText(actorList.get(position).getOrg().getName());
                }
                else{
                    Log.i("LOG","LOG");
                }

                holder.company_speaker.setVisibility(View.GONE);
                if(actorList.get(position).getPerson().getPlace().getName()!=null &&
                        !actorList.get(position).getPerson().getPlace().getName().isEmpty()){
                    holder.country_speaker.setText(actorList.get(position).getPerson().getPlace().getName());
                }
                else{
                    Log.i("LOG","LOG");
                }
                //holder.country_speaker.setText(actorList.get(position).getCompany().getCountry());
            }



            //**
            holder.icon_speaker.getLayoutParams().width = dpToPx(45);
            holder.icon_speaker.getLayoutParams().height = dpToPx(45);
            //

        }
        if(actorList.get(position).getPerson()!=null){
            if(actorList.get(position).getPerson().getImage()==null){
                holder.icon_speaker.setImageResource(R.drawable.speaker_nofoto);
            }

            else{
                ParseFile photoFile = actorList.get(position).getPerson().getImage();
                if (photoFile != null) {
                    //Get singleton instance of ImageLoader
                    ImageLoader imageLoader = ImageLoader.getInstance();
                    //Load the image from the url into the ImageView.
                    imageLoader.displayImage(photoFile.getUrl(), holder.icon_speaker);
                }
                else{
                    Log.i("LOG","LOG");
                }
            }
        }






        return view;
    }
}