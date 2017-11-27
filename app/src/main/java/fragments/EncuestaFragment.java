package fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import mc.sms.R;
import mc.sms.myApp;
import model.Actividad;
import model.Emision;
import model.PreguntaEncuesta;
import model.Rating;
import model.RespuestaEncuesta;

/**
 * Created by Alvaro on 11/14/17.
 */

public class EncuestaFragment extends Fragment {

    public static Actividad activity;

    RelativeLayout seccion,seccion2,seccion3,seccion4,seccion5,seccion6,seccion7,header,header2,header3;
    RatingBar ratingBar,ratingBar2,ratingBar3,ratingBar4,ratingBar5,ratingBar6,ratingBar7;
    TextView textView, textView2, textView3, textView4, textView5, textView6, textView7, textFinal,textHeader,textHeader2,textHeader3;
    Button sendButton;
    public static List<PreguntaEncuesta> preguntaEncuestas = new ArrayList<>();
    public myApp myapp;


    public static EncuestaFragment newInstance(Actividad actividad, List<PreguntaEncuesta> preguntasEncuestas) {


        EncuestaFragment fragment = new EncuestaFragment();
        activity=actividad;
        preguntaEncuestas= preguntasEncuestas;
        fragment.setRetainInstance(true);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retain this fragment across configuration changes.

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View RootView = inflater.inflate(R.layout.encuesta_layout, container, false);
        Toolbar toolbar = (Toolbar) RootView.findViewById(R.id.encuestatoolbar);
        sendButton = (Button) RootView.findViewById(R.id.sendButton);

        //header = (RelativeLayout)RootView.findViewById(R.id.header);
        this.myapp = (myApp) getActivity().getApplicationContext();
        textFinal = (TextView) RootView.findViewById(R.id.textFinal);
        textHeader = (TextView) RootView.findViewById(R.id.textHeader);
        textHeader2 = (TextView) RootView.findViewById(R.id.textHeader2);
        textHeader3 = (TextView) RootView.findViewById(R.id.textHeader3);



        seccion = (RelativeLayout) RootView.findViewById(R.id.seccion1);
        seccion2 = (RelativeLayout) RootView.findViewById(R.id.seccion2);
        seccion3 = (RelativeLayout) RootView.findViewById(R.id.seccion3);
        seccion4 = (RelativeLayout) RootView.findViewById(R.id.seccion4);
        seccion5 = (RelativeLayout) RootView.findViewById(R.id.seccion5);
        seccion6 = (RelativeLayout) RootView.findViewById(R.id.seccion6);
        seccion7 = (RelativeLayout) RootView.findViewById(R.id.seccion7);
        header = (RelativeLayout) RootView.findViewById(R.id.header);
        header2 = (RelativeLayout) RootView.findViewById(R.id.header2);
        header3 = (RelativeLayout) RootView.findViewById(R.id.header3);
        textView = (TextView) seccion.findViewById(R.id.textHeader);
        textView2 = (TextView) seccion2.findViewById(R.id.textHeader);
        textView3 = (TextView) seccion3.findViewById(R.id.textHeader);
        textView4 = (TextView) seccion4.findViewById(R.id.textHeader);
        textView5 = (TextView) seccion5.findViewById(R.id.textHeader);
        textView6 = (TextView) seccion6.findViewById(R.id.textHeader);
        textView7 = (TextView) seccion7.findViewById(R.id.textHeader);

        ratingBar = (RatingBar) seccion.findViewById(R.id.ratingBar);
        ratingBar2 = (RatingBar) seccion2.findViewById(R.id.ratingBar);
        ratingBar3 = (RatingBar) seccion3.findViewById(R.id.ratingBar);
        ratingBar4 = (RatingBar) seccion4.findViewById(R.id.ratingBar);
        ratingBar5 = (RatingBar) seccion5.findViewById(R.id.ratingBar);
        ratingBar6 = (RatingBar) seccion6.findViewById(R.id.ratingBar);
        ratingBar7 = (RatingBar) seccion7.findViewById(R.id.ratingBar);
/*
        textView.setVisibility(View.VISIBLE);
        textView2.setVisibility(View.VISIBLE);
        textView3.setVisibility(View.VISIBLE);
        textView4.setVisibility(View.VISIBLE);
        textView5.setVisibility(View.VISIBLE);
        textView6.setVisibility(View.VISIBLE);
        textView7.setVisibility(View.VISIBLE);
        ratingBar.setVisibility(View.VISIBLE);
        textHeader.setVisibility(View.VISIBLE);
        textHeader2.setVisibility(View.VISIBLE);
        textHeader3.setVisibility(View.VISIBLE);
        ratingBar2.setVisibility(View.VISIBLE);
        ratingBar3.setVisibility(View.VISIBLE);
        ratingBar4.setVisibility(View.VISIBLE);
        ratingBar5.setVisibility(View.VISIBLE);
        ratingBar6.setVisibility(View.VISIBLE);
        ratingBar7.setVisibility(View.VISIBLE);
        seccion.setVisibility(View.VISIBLE);
        seccion2.setVisibility(View.VISIBLE);
        seccion3.setVisibility(View.VISIBLE);
        seccion4.setVisibility(View.VISIBLE);
        seccion5.setVisibility(View.VISIBLE);
        seccion6.setVisibility(View.VISIBLE);
        seccion7.setVisibility(View.VISIBLE);
        header.setVisibility(View.VISIBLE);
        header2.setVisibility(View.VISIBLE);
        header3.setVisibility(View.VISIBLE);*/


        textView.setText(preguntaEncuestas.get(0).getPreguntaTexto());
        textView2.setText(preguntaEncuestas.get(1).getPreguntaTexto());
        textView3.setText(preguntaEncuestas.get(2).getPreguntaTexto());
        textView4.setText(preguntaEncuestas.get(3).getPreguntaTexto());
        textView5.setText(preguntaEncuestas.get(4).getPreguntaTexto());
        textView6.setText(preguntaEncuestas.get(5).getPreguntaTexto());
        textView7.setText(preguntaEncuestas.get(6).getPreguntaTexto());

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final RespuestaEncuesta respuestaEncuesta = new RespuestaEncuesta();
                final RespuestaEncuesta respuestaEncuesta2 = new RespuestaEncuesta();
                final RespuestaEncuesta respuestaEncuesta3 = new RespuestaEncuesta();
                final RespuestaEncuesta respuestaEncuesta4 = new RespuestaEncuesta();
                final RespuestaEncuesta respuestaEncuesta5 = new RespuestaEncuesta();
                final RespuestaEncuesta respuestaEncuesta6 = new RespuestaEncuesta();
                final RespuestaEncuesta respuestaEncuesta7 = new RespuestaEncuesta();

                respuestaEncuesta.setUser(ParseUser.getCurrentUser());
                respuestaEncuesta.setEvent(activity);
                respuestaEncuesta.setValoracion(ratingBar.getRating());
                respuestaEncuesta.setPregunta(preguntaEncuestas.get(0));

                respuestaEncuesta2.setUser(ParseUser.getCurrentUser());
                respuestaEncuesta2.setEvent(activity);
                respuestaEncuesta2.setValoracion(ratingBar2.getRating());
                respuestaEncuesta2.setPregunta(preguntaEncuestas.get(1));

                respuestaEncuesta3.setUser(ParseUser.getCurrentUser());
                respuestaEncuesta3.setEvent(activity);
                respuestaEncuesta3.setValoracion(ratingBar3.getRating());
                respuestaEncuesta3.setPregunta(preguntaEncuestas.get(2));

                respuestaEncuesta4.setUser(ParseUser.getCurrentUser());
                respuestaEncuesta4.setEvent(activity);
                respuestaEncuesta4.setValoracion(ratingBar4.getRating());
                respuestaEncuesta4.setPregunta(preguntaEncuestas.get(3));

                respuestaEncuesta5.setUser(ParseUser.getCurrentUser());
                respuestaEncuesta5.setEvent(activity);
                respuestaEncuesta5.setValoracion(ratingBar5.getRating());
                respuestaEncuesta5.setPregunta(preguntaEncuestas.get(4));

                respuestaEncuesta6.setUser(ParseUser.getCurrentUser());
                respuestaEncuesta6.setEvent(activity);
                respuestaEncuesta6.setValoracion(ratingBar6.getRating());
                respuestaEncuesta6.setPregunta(preguntaEncuestas.get(5));

                respuestaEncuesta7.setUser(ParseUser.getCurrentUser());
                respuestaEncuesta7.setEvent(activity);
                respuestaEncuesta7.setValoracion(ratingBar7.getRating());
                respuestaEncuesta7.setPregunta(preguntaEncuestas.get(6));


                new Thread(new Runnable() {
                    public void run() {


                        respuestaEncuesta.saveEventually();
                        respuestaEncuesta2.saveEventually();
                        respuestaEncuesta3.saveEventually();
                        respuestaEncuesta4.saveEventually();
                        respuestaEncuesta5.saveEventually();
                        respuestaEncuesta6.saveEventually();
                        respuestaEncuesta7.saveEventually();

                        myapp.setEncuestaTrue(activity.getObjectId());




                       /* Fragment fragment = MeetingAppViewPagerFragment.newInstance(SplashEventFragment.meetingApp, SplashEventFragment.actividades, SplashEventFragment.persons, SplashEventFragment.organizaciones, SplashEventFragment.comite);
                        final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.container, fragment);
                        ft.addToBackStack(null);
                        ft.commit();*/



                    }

                }).start();

                sacarVistasyMostrarTextoFinal();



            }
        });


        if (Locale.getDefault().getLanguage().equals("en")) {
            toolbar.setTitle("Encuesta");
        } else {
            toolbar.setTitle("Encuesta");
        }

        if (activity.getType().equals("conferencia")) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.conferencia));

        }

        else if (activity.getType().equals("social") || activity.getType().equals("break")) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.brk));
        }

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();

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

    void sacarVistasyMostrarTextoFinal(){
        textView.setVisibility(View.GONE);
        textView2.setVisibility(View.GONE);
        textView3.setVisibility(View.GONE);
        textView4.setVisibility(View.GONE);
        textView5.setVisibility(View.GONE);
        textView6.setVisibility(View.GONE);
        textView7.setVisibility(View.GONE);
        ratingBar.setVisibility(View.GONE);
        ratingBar2.setVisibility(View.GONE);
        ratingBar3.setVisibility(View.GONE);
        ratingBar4.setVisibility(View.GONE);
        ratingBar5.setVisibility(View.GONE);
        ratingBar6.setVisibility(View.GONE);
        ratingBar7.setVisibility(View.GONE);
        seccion.setVisibility(View.GONE);
        seccion2.setVisibility(View.GONE);
        seccion3.setVisibility(View.GONE);
        seccion4.setVisibility(View.GONE);
        seccion5.setVisibility(View.GONE);
        seccion6.setVisibility(View.GONE);
        seccion7.setVisibility(View.GONE);
        header.setVisibility(View.GONE);
        header2.setVisibility(View.GONE);
        header3.setVisibility(View.GONE);
        textHeader.setVisibility(View.GONE);
        textHeader2.setVisibility(View.GONE);
        textHeader3.setVisibility(View.GONE);
        sendButton.setVisibility(View.GONE);
        textFinal.setVisibility(View.VISIBLE);

    }



}
