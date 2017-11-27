package fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import mc.sms.R;
import mc.sms.myApp;
import model.Actividad;
import model.PreguntaEncuesta;
import model.RespuestaEncuesta;

/**
 * Created by Alvaro on 11/14/17.
 */

public class EncuestaGeneralFragment extends Fragment {



    RelativeLayout seccion,seccion2,seccion3,seccion4,seccion5,seccion6,seccion7,header,header2,header3,header0,header4;
    RatingBar ratingBar,ratingBar2,ratingBar3,ratingBar4,ratingBar5,ratingBar6,ratingBar7;
    TextView textView, textView2, textView3, textView4, textView5, textView6, textView7, textFinal,textHeader, textHeader0, textHeader2,textHeader3, textHeader4;
    Button sendButton;
    EditText editText;
    public static List<PreguntaEncuesta> preguntaEncuestas = new ArrayList<>();
    public myApp myapp;
    public static Actividad mApp;


    public static EncuestaGeneralFragment newInstance(Actividad meetingApp,List<PreguntaEncuesta> preguntasEncuestas) {


        EncuestaGeneralFragment fragment = new EncuestaGeneralFragment();
        mApp=meetingApp;
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
        final View RootView = inflater.inflate(R.layout.encuesta_general_layout, container, false);
        sendButton = (Button) RootView.findViewById(R.id.sendButton);
        editText = (EditText) RootView.findViewById(R.id.editTextEncuesta);

        //header = (RelativeLayout)RootView.findViewById(R.id.header);
        this.myapp = (myApp) getActivity().getApplicationContext();

        textHeader0 = (TextView) RootView.findViewById(R.id.textHeader0);

        textHeader = (TextView) RootView.findViewById(R.id.textHeader);
        textHeader2 = (TextView) RootView.findViewById(R.id.textHeader2);
        textHeader3 = (TextView) RootView.findViewById(R.id.textHeader3);
        textHeader4 = (TextView) RootView.findViewById(R.id.textHeader4);




        seccion = (RelativeLayout) RootView.findViewById(R.id.seccion1);
        seccion2 = (RelativeLayout) RootView.findViewById(R.id.seccion2);
        seccion3 = (RelativeLayout) RootView.findViewById(R.id.seccion3);
        seccion4 = (RelativeLayout) RootView.findViewById(R.id.seccion4);
        seccion5 = (RelativeLayout) RootView.findViewById(R.id.seccion5);
        seccion6 = (RelativeLayout) RootView.findViewById(R.id.seccion6);

        header0 = (RelativeLayout) RootView.findViewById(R.id.header0);
        header = (RelativeLayout) RootView.findViewById(R.id.header);
        header2 = (RelativeLayout) RootView.findViewById(R.id.header2);
        header3 = (RelativeLayout) RootView.findViewById(R.id.header3);
        header4 = (RelativeLayout) RootView.findViewById(R.id.header4);
        textView = (TextView) seccion.findViewById(R.id.textHeader);
        textView2 = (TextView) seccion2.findViewById(R.id.textHeader);
        textView3 = (TextView) seccion3.findViewById(R.id.textHeader);
        textView4 = (TextView) seccion4.findViewById(R.id.textHeader);
        textView5 = (TextView) seccion5.findViewById(R.id.textHeader);
        textView6 = (TextView) seccion6.findViewById(R.id.textHeader);

        ratingBar = (RatingBar) seccion.findViewById(R.id.ratingBar);
        ratingBar2 = (RatingBar) seccion2.findViewById(R.id.ratingBar);
        ratingBar3 = (RatingBar) seccion3.findViewById(R.id.ratingBar);
        ratingBar4 = (RatingBar) seccion4.findViewById(R.id.ratingBar);
        ratingBar5 = (RatingBar) seccion5.findViewById(R.id.ratingBar);
        ratingBar6 = (RatingBar) seccion6.findViewById(R.id.ratingBar);

        textFinal = (TextView) RootView.findViewById(R.id.textFinal);

        if(!myapp.getEncuestaTrue(mApp.getObjectId())){

            textView.setText(preguntaEncuestas.get(0).getPreguntaTexto());
            textView2.setText(preguntaEncuestas.get(1).getPreguntaTexto());
            textView3.setText(preguntaEncuestas.get(2).getPreguntaTexto());
            textView4.setText(preguntaEncuestas.get(3).getPreguntaTexto());
            textView5.setText(preguntaEncuestas.get(4).getPreguntaTexto());
            textView6.setText(preguntaEncuestas.get(5).getPreguntaTexto());
            textHeader4.setText(preguntaEncuestas.get(6).getPreguntaTexto());

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
                    respuestaEncuesta.setEvent(mApp);
                    respuestaEncuesta.setValoracion(ratingBar.getRating());
                    respuestaEncuesta.setPregunta(preguntaEncuestas.get(0));

                    respuestaEncuesta2.setUser(ParseUser.getCurrentUser());
                    respuestaEncuesta2.setEvent(mApp);
                    respuestaEncuesta2.setValoracion(ratingBar2.getRating());
                    respuestaEncuesta2.setPregunta(preguntaEncuestas.get(1));

                    respuestaEncuesta3.setUser(ParseUser.getCurrentUser());
                    respuestaEncuesta3.setEvent(mApp);
                    respuestaEncuesta3.setValoracion(ratingBar3.getRating());
                    respuestaEncuesta3.setPregunta(preguntaEncuestas.get(2));

                    respuestaEncuesta4.setUser(ParseUser.getCurrentUser());
                    respuestaEncuesta4.setEvent(mApp);
                    respuestaEncuesta4.setValoracion(ratingBar4.getRating());
                    respuestaEncuesta4.setPregunta(preguntaEncuestas.get(3));

                    respuestaEncuesta5.setUser(ParseUser.getCurrentUser());
                    respuestaEncuesta5.setEvent(mApp);
                    respuestaEncuesta5.setValoracion(ratingBar5.getRating());
                    respuestaEncuesta5.setPregunta(preguntaEncuestas.get(4));

                    respuestaEncuesta6.setUser(ParseUser.getCurrentUser());
                    respuestaEncuesta6.setEvent(mApp);
                    respuestaEncuesta6.setValoracion(ratingBar6.getRating());
                    respuestaEncuesta6.setPregunta(preguntaEncuestas.get(5));

                    respuestaEncuesta7.setUser(ParseUser.getCurrentUser());
                    respuestaEncuesta7.setEvent(mApp);
                    if(editText.getText().toString().equals("si") || editText.getText().toString().equals("Si")){
                        respuestaEncuesta7.setValoracion(1);
                    }
                    else {
                        respuestaEncuesta7.setValoracion(0);
                    }

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

                            myapp.setEncuestaTrue(mApp.getObjectId());




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



        }

        else{
            sacarVistasyMostrarTextoFinal();
        }






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

        ratingBar.setVisibility(View.GONE);
        ratingBar2.setVisibility(View.GONE);
        ratingBar3.setVisibility(View.GONE);
        ratingBar4.setVisibility(View.GONE);
        ratingBar5.setVisibility(View.GONE);
        ratingBar6.setVisibility(View.GONE);

        seccion.setVisibility(View.GONE);
        seccion2.setVisibility(View.GONE);
        seccion3.setVisibility(View.GONE);
        seccion4.setVisibility(View.GONE);
        seccion5.setVisibility(View.GONE);
        seccion6.setVisibility(View.GONE);
        header0.setVisibility(View.GONE);
        header.setVisibility(View.GONE);
        header2.setVisibility(View.GONE);
        header3.setVisibility(View.GONE);
        header4.setVisibility(View.GONE);
        textHeader0.setVisibility(View.GONE);
        textHeader.setVisibility(View.GONE);
        textHeader2.setVisibility(View.GONE);
        textHeader3.setVisibility(View.GONE);
        textHeader4.setVisibility(View.GONE);
        sendButton.setVisibility(View.GONE);
        textFinal.setVisibility(View.VISIBLE);

    }



}
