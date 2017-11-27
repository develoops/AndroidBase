package adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.ParseFile;

import java.util.ArrayList;
import java.util.List;

import imageTreatment.RoundedImageView;
import mc.sms.R;
import mc.sms.myApp;
import model.Actividad;

import model.Persona;

/**
 * Created by Alvaro on 2/21/15.
 */
public class SpeakersListViewAdapter extends BaseAdapter implements Filterable {

    Context context;
    LayoutInflater inflater;
    public myApp myapp;
    private List<Persona> personList= null;
    private ArrayList<Persona> arraylist;
    public ArrayList<Persona> orig;
    public Actividad meetingApp;
    Boolean b;

    public SpeakersListViewAdapter(Context context,
                                   List<Persona> personList, Actividad mApp, Boolean bool) {

        b=bool;
        myapp = (myApp) context.getApplicationContext();






        this.context = context;
        this.personList = personList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(personList);
        this.meetingApp = mApp; //Alfonsein

    }

    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<Persona> results = new ArrayList<>();
                if (orig == null)
                    orig = arraylist;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final Persona g : orig) {
                            if (g.getFirstName().toLowerCase()
                                    .contains(constraint.toString())|| g.getLastName().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                personList= (ArrayList<Persona>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
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
        return personList.size();
    }

    @Override
    public Object getItem(int position) {
        return personList.get(position);
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

            holder.charge_speaker = (TextView) view.findViewById(R.id.charge);
            holder.country_speaker = (TextView) view.findViewById(R.id.country);
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


        String firstName = personList.get(position).getFirstName();
        String lastName = personList.get(position).getLastName();

        if(personList.get(position).getSalutation()!=null){
            String salutation = personList.get(position).getSalutation();
            holder.name_speaker.setText(salutation+ " " +firstName + " " + lastName);
        }
        else {
            holder.name_speaker.setText(firstName + " " + lastName);
        }


        /*
        if(!myapp.isFirstTime()){
            try {
                personList.get(position).fetchFromLocalDatastore();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                personList.get(position).fetchIfNeeded();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        */

//        if(personList.get(position).()!=null &&
//                !personList.get(position).ge().getName().isEmpty() ){
//
//
//            holder.company_speaker.setText(personList.get(position).getCompany().getName());
//        }


//        if(personList.get(position).getActors()==null){
//
//        }
//
//        else{
//
//            Log.i("ACTORSSS",String.valueOf(personList.get(position).getActors()));
//            if(personList.get(position).getActors()==null){
//
//                Log.i("NA","EMPTU");
//            }
//
//            else{
//                //Log.i("Objectid",personList.get(position).getCompany().getObjectId());
//
//
//
//                else {
//                    Log.i("NA","EMPTU");
//                }
//                Log.i("LOG","LOG");
//            }
//        }



//        //El booleano sirve para diferenciar si es celda de speaker de la lista speaker o el speaker del evento detalle
//        //Si es true es speaker de lista speaker, no lleva el cargo
//        if(b){
//            holder.charge_speaker.setVisibility(View.GONE);
//            holder.country_speaker.setVisibility(View.VISIBLE);
//
//            if(personList.get(position).getActors()!=null){
//                if(personList.get(position).getCompany()!=null ){
//                    if(personList.get(position).getCompany().getLocation()!=null ){
//                        if(personList.get(position).getCompany().getLocation().getType().equals("Country")){
//
//                            holder.country_speaker.setText(personList.get(position).getCompany().getLocation().getName());
//                        }
//
//                    }
//                    else {
//                        Log.i("NA","EMPTU");
//                    }
//                }
//                else{
//                    Log.i("LOG","LOG");
//                }
//            }
//
//            else{
//                Log.i("LOG","LOG");
//            }
//
//
//        }
//
//        else{
//            Log.i("LOG", "LOG");
//        }

        if(personList.get(position).getImage()==null){
            holder.icon_speaker.setImageResource(R.drawable.speaker_nofoto);
        }

        else{
            ParseFile photoFile = personList.get(position).getImage();

            if (photoFile != null) {
                //Get singleton instance of ImageLoader
                ImageLoader imageLoader = ImageLoader.getInstance();
                //Load the image from the url into the ImageView.

                imageLoader.displayImage(photoFile.getUrl(), holder.icon_speaker);


            }
            else{
                Log.i("LOG","LOG");
            }
            Log.i("LOG","LOG");
        }



        return view;
    }
}
