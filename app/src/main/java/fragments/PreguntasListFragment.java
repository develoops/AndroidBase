package fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseImageView;
import com.parse.ParseLiveQueryClient;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.parse.SubscriptionHandling;

import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import adapters.PreguntasListViewAdapter;

import adapters.PreguntasParseQueryAdapter;

import mc.sms.R;

import mc.sms.myApp;
import model.Actividad;
import model.Emision;
import model.PersonaRolOrg;
import utils.CircularTextView;


/**
 * Created by Alvaro on 2/25/15.
 */
public class PreguntasListFragment extends Fragment {

    ListView listview;
    //ImageView fav;
    myApp myapp;
    public static Actividad activity;
    //public static MobiFile map;
    //public static GridView gridview;
    public static List<Emision> emisiones;
    private ImageButton left, right;
    //ImageButton pregunta;
    ParseQueryAdapterPreg preguntasListViewAdapter;
    ParseLiveQueryClient parseLiveQueryClient;

    public static ParseUser currentUser;



    public static PreguntasListFragment newInstance(Actividad actividad) {

        // Instantiate a new fragment
        activity = actividad; //Alfonso
        PreguntasListFragment fragment = new PreguntasListFragment();

        fragment.setRetainInstance(true);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retain this fragment across configuration changes.
        setRetainInstance(true);
        this.myapp = (myApp) getActivity().getApplicationContext();

        if(!myapp.getUsuarioAsignado()){
            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
            query.getFirstInBackground(new GetCallback<ParseUser>() {
                @Override
                public void done(final ParseUser object, ParseException e) {
                    if(object!=null){

                        if(object.get("nombre")==null){

                            final LayoutInflater li = LayoutInflater.from(getActivity());
                            final View promptsView = li.inflate(R.layout.custom2, null);


                            AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(
                                    getActivity());

                            // set prompts.xml to alertdialog builder
                            alertDialogBuilder1.setView(promptsView);

                            final EditText userInput1 = (EditText) promptsView
                                    .findViewById(R.id.editText);

                            // set dialog message
                            alertDialogBuilder1
                                    .setCancelable(false)
                                    .setPositiveButton("OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    // get user input and set it to result
                                                    // edit text
                                                    if(userInput1.getText().toString().isEmpty()){
                                                        Random rand = new Random();
                                                        int n = rand.nextInt(999999999);
                                                        String name = "anon" + String.valueOf(n);
                                                        object.put("nombre", name);
                                                    }
                                                    else {
                                                        object.put("nombre",userInput1.getText().toString());
                                                    }

                                                    new Thread(new Runnable() {
                                                        public void run() {
                                                            object.saveEventually();
                                                            myapp.usuarioAsignado();
                                                            //updateList();
                                                        }

                                                    }).start();


                                                    //updateList();

                                                }
                                            })
                                    .setNegativeButton("Cancelar",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    Random rand = new Random();
                                                    int n = rand.nextInt(999999999);
                                                    String name = "anon" + String.valueOf(n);
                                                    object.put("nombre", name);
                                                    new Thread(new Runnable() {
                                                        public void run() {
                                                            object.saveEventually();
                                                            myapp.usuarioAsignado();
                                                            //updateList();
                                                        }

                                                    }).start();
                                                    dialog.cancel();
                                                }
                                            });

                            // create alert dialog
                            AlertDialog alertDialog1 = alertDialogBuilder1.create();

                            // show it
                            alertDialog1.show();

                        }


                    }
                }
            });
        }




        try {
            parseLiveQueryClient = ParseLiveQueryClient.Factory.getClient(new URI("wss://smsdemo.back4app.io"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Log.i("FAIL","FAIL");
        }
        //queryEmision();

        ParseQuery<Emision> queryEmision = ParseQuery.getQuery(Emision.class);
        queryEmision.include("emisor");
        queryEmision.whereEqualTo("actividad", activity);

        SubscriptionHandling<Emision> subscriptionHandling = parseLiveQueryClient.subscribe(queryEmision);
        subscriptionHandling.handleEvent(SubscriptionHandling.Event.CREATE, new SubscriptionHandling.HandleEventCallback<Emision>() {
            @Override
            public void onEvent(ParseQuery<Emision> query, Emision object) {
                // HANDLING create event
                Log.i("ESCUCHANDO","LISTO");
                updateList();


            }
        });

        subscriptionHandling.handleEvent(SubscriptionHandling.Event.DELETE, new SubscriptionHandling.HandleEventCallback<Emision>() {
            @Override
            public void onEvent(ParseQuery<Emision> query, Emision object) {
                Log.i("ESCUCHANDO2","LISTO");
                updateList();

            }
        });

        subscriptionHandling.handleEvent(SubscriptionHandling.Event.UPDATE, new SubscriptionHandling.HandleEventCallback<Emision>() {
            @Override
            public void onEvent(ParseQuery<Emision> query, Emision object) {
                updateList();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View RootView = inflater.inflate(R.layout.preguntaslistlayout, container, false);
        listview = (ListView) RootView.findViewById(R.id.commonListView);
        //fav = (ImageView)RootView.findViewById(R.id.fav);


        this.myapp = (myApp) getActivity().getApplicationContext();
        FloatingActionButton fab = (FloatingActionButton) RootView.findViewById(R.id.fab);

        // gridview a partir del elemento del xml gridview



        Toolbar toolbar = (Toolbar) RootView.findViewById(R.id.preguntastoolbar);
        if (Locale.getDefault().getLanguage().equals("en")) {
            toolbar.setTitle("Preguntas");
        } else {
            toolbar.setTitle("Preguntas");
        }

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();

            }
        });

        if (activity.getType().equals("conferencia")) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.conferencia));

        } else if (activity.getType().equals("modulo") || activity.getType().equals("simposio")) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.companySecundario));

        } else if (activity.getType().equals("social") || activity.getType().equals("break")) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.brk));
        } else {
            toolbar.setBackgroundColor(getResources().getColor(R.color.conferencia));
        }


        preguntasListViewAdapter = new ParseQueryAdapterPreg(getActivity());
        listview.setAdapter(preguntasListViewAdapter);
        preguntasListViewAdapter.loadObjects();



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater li = LayoutInflater.from(getActivity());
                final View promptsView = li.inflate(R.layout.custom, null);


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getActivity());

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text
                                        saveQuestion(userInput.getText().toString());
                                        //updateList();

                                    }
                                })
                        .setNegativeButton("Cancelar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });



        return RootView;
    }

    @Override
    public void onResume() {
        super.onResume();


        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                Log.i("HOLA",String.valueOf(keyCode));
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    // handle back button's click listener

                    getActivity().onBackPressed();

                    return true;



                }
                return false;
            }
        });

    }
    private void updateList(){
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                preguntasListViewAdapter.loadObjects();
                //preguntasListViewAdapter.notifyDataSetChanged();
                //listview.setAdapter(preguntasListViewAdapter);
            }

        },1000);
    }

    private void saveQuestion(String question) {
        final Emision emision = new Emision();
        //gameScore.setComment(selectedEvent.getTitle());
        //gameScore.setUser(ParseUser.getCurrentUser());
        emision.put("actividad", activity);
        emision.put("emisor", ParseUser.getCurrentUser());
        emision.setMensajeTexto(question);
        emision.setLikes(0);
        new Thread(new Runnable() {
            public void run() {
                emision.saveEventually();
            }

        }).start();


    }

/*    private void queryEmision() {
        ParseQuery<Emision> queryEmision = ParseQuery.getQuery(Emision.class);
        queryEmision.include("emisor");
        queryEmision.whereEqualTo("actividad", activity);
        queryEmision.findInBackground(new FindCallback<Emision>() {
            @Override
            public void done(List<Emision> objects, ParseException e) {
                if (objects != null) {

                    Collections.sort(objects, new Comparator<Emision>() {
                        @Override
                        public int compare(Emision lhs, Emision rhs) {
                            return rhs.getLikes().intValue() - lhs.getLikes().intValue();
                        }
                    });

                    listview.setAdapter(preguntasListViewAdapter);
                    preguntasListViewAdapter.loadObjects();

                }

            }
        });
    }*/

    public class ParseQueryAdapterPreg extends ParseQueryAdapter<Emision> {

        public ParseQueryAdapterPreg(Context context) {
            super(context, new ParseQueryAdapter.QueryFactory<Emision>() {
                public ParseQuery<Emision> create() {
                    // Here we can configure a ParseQuery to display
                    // only top-rated meals.
                    ParseQuery query = new ParseQuery("Emision");
                    query.include("emisor");
                    query.whereEqualTo("actividad", activity);
                    query.addDescendingOrder("likes");
                    return query;
                }
            });
        }

        @Override
        public View getItemView(final Emision emision, View v, ViewGroup parent) {

            if (v == null) {
                v = View.inflate(getContext(), R.layout.cell_pregunta, null);
            }

            super.getItemView(emision, v, parent);


            TextView titleTextView = (TextView) v.findViewById(R.id.name_question);
            TextView titleTextView2 = (TextView) v.findViewById(R.id.user);
            if(emision.getEmisor().get("nombre")!=null){

                titleTextView2.setText(emision.getEmisor().get("nombre").toString());

            }
            else {
                titleTextView2.setText("anónimo");
            }

            titleTextView.setText(emision.getMensajeTexto());


            final CircularTextView numeroDeLikes = (CircularTextView)v.findViewById(R.id.circularTextView);
            final ImageView votoPregunta = (ImageView)v.findViewById(R.id.fav);

            if(emision.getLikes()==null){
                emision.setLikes(0);
                numeroDeLikes.setText(emision.getLikes().toString());

            }
            else {
                numeroDeLikes.setText(emision.getLikes().toString());

            }
            numeroDeLikes.setStrokeWidth(1);
            numeroDeLikes.setStrokeColor("#ff0000");
            numeroDeLikes.setSolidColor("#ff0000");

            if(myapp.getPreguntaBoolean(emision.getObjectId())){
                votoPregunta.setImageResource(R.drawable.btn_favorito_marcado);
            }
            else {
                votoPregunta.setImageResource(R.drawable.btnfavorito);
            }

            votoPregunta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!myapp.getPreguntaBoolean(emision.getObjectId())){
                        myapp.setPreguntaBooleanTrue(emision.getObjectId());
                        votoPregunta.setImageResource(R.drawable.btn_favorito_marcado);
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
                                    numeroDeLikes.setText(likes.toString());
                                    sumarLikes(emision.getObjectId());
                                    //preguntasListViewAdapter.loadObjects();
                                }

                            }
                        });



                        //Log.i("likes1",likes.toString());

                        //holder.numeroDeLikes.setText(likes.toString());
                        //notifyDataSetChanged();

                    }
                    else{
                        myapp.setPreguntaBooleanFalse(emision.getObjectId());
                        votoPregunta.setImageResource(R.drawable.btnfavorito);

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
                                    numeroDeLikes.setText(likes2.toString());
                                    quitarLikes(emision.getObjectId());
                                    //preguntasListViewAdapter.loadObjects();
                                }

                            }
                        });

                        //notifyDataSetChanged();

                    }
                }
            });

            return v;
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
                                //updateList();
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
                                //updateList();
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

}