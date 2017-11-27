package adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.ArrayList;
import java.util.List;

import mc.sms.R;
import mc.sms.myApp;
import model.Actividad;
import model.Emision;
import utils.CircularTextView;

/**
 * Created by Alvaro on 2/21/15.
 */
public class PreguntasParseQueryAdapter extends ParseQueryAdapter<Emision> {

    Context context;
    LayoutInflater inflater;
    private List<Emision> emisions= null;
    private ArrayList<Emision> arraylist;
    public Actividad meetingApp;
    //public static String emisionID;
    myApp myapp;

    public PreguntasParseQueryAdapter(Context context, Actividad actividad) {
        // Use the QueryFactory to construct a PQA that will only show
        // Todos marked as high-pri

        super(context, new ParseQueryAdapter.QueryFactory<Emision>() {

            public ParseQuery create() {

                ParseQuery query = new ParseQuery("Emision");
                return query;
            }
        });
    }


    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView mensajeTexto;
        CircularTextView numeroDeLikes;
        ImageView votoPregunta;


    }




    @Override
    public View getItemView(final Emision emision, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();


            view = inflater.inflate(R.layout.cell_pregunta, null);

            // Locate the TextViews in listview_item.xml
            holder.mensajeTexto = (TextView) view.findViewById(R.id.charge);
            holder.numeroDeLikes = (CircularTextView) view.findViewById(R.id.circularTextView);
            holder.votoPregunta = (ImageView) view.findViewById(R.id.fav);
            //holder.votoPregunta.setTag(R.drawable.btnfavorito);
            // Locate the ImageView in listview_item.xml



            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
            holder.mensajeTexto.setText("");
            holder.numeroDeLikes.setText("");



        }


        //emisionID = emisions.get(position).getObjectId();
        String mensajeTexto = emision.getMensajeTexto();

        holder.mensajeTexto.setText(mensajeTexto);

        holder.numeroDeLikes.setText(emision.getLikes().toString());
        //Log.i("numerolikes",holder.numeroDeLikes.toString());
        holder.numeroDeLikes.setStrokeWidth(1);
        holder.numeroDeLikes.setStrokeColor("#ff0000");
        holder.numeroDeLikes.setSolidColor("#ff0000");

        if(myapp.getPreguntaBoolean(emision.getObjectId())){
            holder.votoPregunta.setImageResource(R.drawable.btn_favorito_marcado);
        }
        else {
            holder.votoPregunta.setImageResource(R.drawable.btnfavorito);
        }

        holder.votoPregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(!myapp.getPreguntaBoolean(emision.getObjectId())){
                   myapp.setPreguntaBooleanTrue(emision.getObjectId());
                   holder.votoPregunta.setImageResource(R.drawable.btn_favorito_marcado);
                   //Log.i("likes0",emisions.get(position).getLikes().toString());
                   //final Number[] likes = new Number[1];
                   ParseQuery<Emision> query = ParseQuery.getQuery(Emision.class);
                   query.whereEqualTo("objectId",emision.getObjectId());
                   query.getFirstInBackground(new GetCallback<Emision>() {
                       @Override
                       public void done(Emision object, ParseException e) {
                           if(object!=null){
                               Number likes;
                               Log.i("likes00",object.getLikes().toString());
                               likes = object.getLikes().intValue()+1;
                               Log.i("likes0",likes.toString());
                               holder.numeroDeLikes.setText(likes.toString());
                               sumarLikes(emision.getObjectId());
                           }

                       }
                   });


                   
                   //Log.i("likes1",likes.toString());

                   //holder.numeroDeLikes.setText(likes.toString());
                   //notifyDataSetChanged();

               }
               else{
                   myapp.setPreguntaBooleanFalse(emision.getObjectId());
                   holder.votoPregunta.setImageResource(R.drawable.btnfavorito);

                   ParseQuery<Emision> query = ParseQuery.getQuery(Emision.class);
                   query.whereEqualTo("objectId",emision.getObjectId());
                   query.getFirstInBackground(new GetCallback<Emision>() {
                       @Override
                       public void done(Emision object, ParseException e) {
                           if(object!=null){
                               Number likes2;
                               Log.i("likes1",object.getLikes().toString());
                               likes2 = object.getLikes().intValue() -1;
                               Log.i("likes11",likes2.toString());
                               holder.numeroDeLikes.setText(likes2.toString());
                               quitarLikes(emision.getObjectId());
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
