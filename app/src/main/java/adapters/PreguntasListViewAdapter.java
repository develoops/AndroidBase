package adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import mc.sms.R;
import mc.sms.myApp;
import model.Actividad;
import model.Emision;
import model.Org;
import utils.CircularTextView;

/**
 * Created by Alvaro on 2/21/15.
 */
public class PreguntasListViewAdapter extends BaseAdapter  {

    Context context;
    LayoutInflater inflater;
    private List<Emision> emisions= null;
    private ArrayList<Emision> arraylist;
    public Actividad meetingApp;
    //public static String emisionID;
    myApp myapp;

    public PreguntasListViewAdapter(Context context,
                                    List<Emision> emisiones) {


        this.context = context;
        this.emisions = emisiones;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(emisions);
        myapp = (myApp) context.getApplicationContext();


    }


    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView name_sponsor;
        CircularTextView numeroDeLikes;
        ImageView votoPregunta;


    }
    @Override
    public int getCount() {
        return emisions.size();
    }

    @Override
    public Object getItem(int position) {
        return emisions.get(position);
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


            view = inflater.inflate(R.layout.cell_pregunta, null);

            // Locate the TextViews in listview_item.xml
            holder.name_sponsor = (TextView) view.findViewById(R.id.charge);
            holder.numeroDeLikes = (CircularTextView) view.findViewById(R.id.circularTextView);
            holder.votoPregunta = (ImageView) view.findViewById(R.id.fav);
            //holder.votoPregunta.setTag(R.drawable.btnfavorito);
            // Locate the ImageView in listview_item.xml



            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
            holder.name_sponsor.setText("");
            holder.numeroDeLikes.setText("");



        }


        //emisionID = emisions.get(position).getObjectId();
        String firstName = emisions.get(position).getMensajeTexto();

        holder.name_sponsor.setText(firstName);

        holder.numeroDeLikes.setText(emisions.get(position).getLikes().toString());
        //Log.i("numerolikes",holder.numeroDeLikes.toString());
        holder.numeroDeLikes.setStrokeWidth(1);
        holder.numeroDeLikes.setStrokeColor("#ff0000");
        holder.numeroDeLikes.setSolidColor("#ff0000");

        if(myapp.getPreguntaBoolean(emisions.get(position).getObjectId())){
            holder.votoPregunta.setImageResource(R.drawable.btn_favorito_marcado);
        }
        else {
            holder.votoPregunta.setImageResource(R.drawable.btnfavorito);
        }

        holder.votoPregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(!myapp.getPreguntaBoolean(emisions.get(position).getObjectId())){
                   myapp.setPreguntaBooleanTrue(emisions.get(position).getObjectId());
                   holder.votoPregunta.setImageResource(R.drawable.btn_favorito_marcado);
                   //Log.i("likes0",emisions.get(position).getLikes().toString());
                   //final Number[] likes = new Number[1];
                   ParseQuery<Emision> query = ParseQuery.getQuery(Emision.class);
                   query.whereEqualTo("objectId",emisions.get(position).getObjectId());
                   query.getFirstInBackground(new GetCallback<Emision>() {
                       @Override
                       public void done(Emision object, ParseException e) {
                           if(object!=null){
                               Number likes;
                               Log.i("likes00",object.getLikes().toString());
                               likes = object.getLikes().intValue()+1;
                               Log.i("likes0",likes.toString());
                               holder.numeroDeLikes.setText(likes.toString());
                               sumarLikes(emisions.get(position).getObjectId());
                           }

                       }
                   });


                   
                   //Log.i("likes1",likes.toString());

                   //holder.numeroDeLikes.setText(likes.toString());
                   //notifyDataSetChanged();

               }
               else{
                   myapp.setPreguntaBooleanFalse(emisions.get(position).getObjectId());
                   holder.votoPregunta.setImageResource(R.drawable.btnfavorito);

                   ParseQuery<Emision> query = ParseQuery.getQuery(Emision.class);
                   query.whereEqualTo("objectId",emisions.get(position).getObjectId());
                   query.getFirstInBackground(new GetCallback<Emision>() {
                       @Override
                       public void done(Emision object, ParseException e) {
                           if(object!=null){
                               Number likes2;
                               Log.i("likes1",object.getLikes().toString());
                               likes2 = object.getLikes().intValue() -1;
                               Log.i("likes11",likes2.toString());
                               holder.numeroDeLikes.setText(likes2.toString());
                               quitarLikes(emisions.get(position).getObjectId());
                           }

                       }
                   });

                   //notifyDataSetChanged();

               }
            }
        });



        return view;
    }

    public void sumarLikes(String emisionID){
        ParseQuery<Emision> query = ParseQuery.getQuery(Emision.class);
        query.whereEqualTo("objectId",emisionID);
        query.getFirstInBackground(new GetCallback<Emision>() {
            @Override
            public void done(final Emision object, ParseException e) {
                if(object!=null){
                    Number newLikes1 = object.getLikes().intValue() + 1;
                    object.setLikes(newLikes1);
                    new Thread(new Runnable() {
                        public void run() {
                            object.saveEventually();
                        }

                    }).start();
                }
                else {
                    Log.i("SUMErLIKES","O");
                }
            }
        });

// Retrieve the object by id

    }

    public void quitarLikes(String emisionID){
        ParseQuery<Emision> query = ParseQuery.getQuery(Emision.class);
        query.whereEqualTo("objectId",emisionID);
        query.getFirstInBackground(new GetCallback<Emision>() {
            @Override
            public void done(final Emision object, ParseException e) {
                if(object!=null){
                    Number newLikes = object.getLikes().intValue() - 1;
                    object.setLikes(newLikes);
                    new Thread(new Runnable() {
                        public void run() {
                            object.saveEventually();
                        }

                    }).start();
                }
                else {
                    Log.i("QUITARLIKES","O");
                }

            }
        });

    }
}
