package adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.ParseFile;
import com.parse.ParseImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import mc.sms.R;
import mc.sms.myApp;
import model.Actividad;

/**
 * Created by hetpin on 3/23/15.
 */
public class HetpinProgramListViewAdapter extends BaseAdapter implements Filterable{
    private String tag = "THANH-HetpinProgramListViewAdapter";
    Context context;
    myApp myapp;
    LayoutInflater inflater;
    Boolean b;
    Boolean det;
    Boolean sp = true;
    public String month;
    public Actividad mApp;
    private int bgColor = Color.parseColor("#ffffff");
    public int start_hour,end_hour;
    private List<Actividad> eventList = null;
    private ArrayList<Actividad> arraylist ;
    public ArrayList<Actividad> orig;

    //**

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public HetpinProgramListViewAdapter(Context context,
                                        List<Actividad> events, Actividad meetingApp, boolean bool, boolean detail) {
        Log.e(getClass().getName(), "Call constructor ");
        b=bool;
        det=detail;
        this.context = context;
        this.eventList = events;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(eventList);
        this.mApp = meetingApp; //Alfonsein
        myapp = (myApp) context.getApplicationContext();
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public static class ViewHolder {
        public TextView name;
        public TextView time;
        public TextView place;
        public TextView date;
        public TextView speakers = null;
        public RelativeLayout relativeLayout,rl;
        public FrameLayout fl;
        //public RelativeLayout infoLayout;
        ParseImageView icon;
        public ImageView fav;
    }

    @Override
    public int getViewTypeCount() {
        if (getCount() != 0)
            return getCount();
        return 1;
    }

    @Override
    public int getCount() {
        return eventList.size();
    }

    @Override
    public Object getItem(int position) {
        return eventList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean isEnabled(int position) {
        if(eventList.get(position).getType()!=null){
            if(eventList.get(position).getType().equals("break")){
                return false;
            }
            else {
                return true;
            }
        }
        else {
            return true;
        }

    }

    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<Actividad> results = new ArrayList<>();
                if (orig == null)
                    orig = arraylist;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final Actividad g : orig) {
                            if (g.getTitle().toLowerCase()
                                    .contains(constraint.toString())|| g.getTitle().toUpperCase()
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
                eventList= (ArrayList<Actividad>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        Log.e("THANH", "getView " + position);
        final ViewHolder holder;

        Log.i("ZONA HORARIA",String.valueOf(timeZone()));
        if (view == null) {

            holder = new ViewHolder();
            view = inflater.inflate(R.layout.cell_event, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name_event);
            holder.time = (TextView) view.findViewById(R.id.time);
            holder.place = (TextView) view.findViewById(R.id.eventPlace);
            holder.date = (TextView) view.findViewById(R.id.date_event);
            holder.speakers = (TextView) view.findViewById(R.id.eventSpeakers);
            // Locate the ImageView in listview_item.xml
            holder.icon = (ParseImageView) view.findViewById(R.id.icon_event);
            holder.fav = (ImageView) view.findViewById(R.id.fav);
            holder.relativeLayout = (RelativeLayout) view.findViewById(R.id.Content);
            holder.fl = (FrameLayout) view.findViewById(R.id.frame);
            holder.rl = (RelativeLayout) view.findViewById(R.id.info);
            //holder.infoLayout = (RelativeLayout) view.findViewById(R.id.info);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
            holder.name.setText("");
            holder.time.setText("");
            holder.place.setText("");
            holder.date.setVisibility(View.GONE);//date
            holder.speakers.setText("");
            //icon
            holder.relativeLayout.setBackgroundColor(bgColor);

            holder.fav.setImageResource(android.R.color.transparent);
            holder.fav.setImageDrawable(null);
            //holder.infoLayout.setBackgroundColor(Color.WHITE);
        }

        holder.relativeLayout.setBackgroundColor(bgColor);
        //Log.i("NAME",String.valueOf(eventList.get(position).getTitle()));
//        Log.i("NAME",String.valueOf(eventList.get(position).getObjectId()));



        holder.name.setText(eventList.get(position).getTitle());



        //Generate date string
        Calendar cal = Calendar.getInstance();
        cal.setTime(eventList.get(position).getStartDate());
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(eventList.get(position).getEndDate());
        if(timeZone().equals("-03:00")){
            start_hour = cal.get(Calendar.HOUR_OF_DAY)+3;
            end_hour = cal2.get(Calendar.HOUR_OF_DAY)+3;
        }
        else if(timeZone().equals("-04:00")){
            start_hour = cal.get(Calendar.HOUR_OF_DAY)+4;
            end_hour = cal2.get(Calendar.HOUR_OF_DAY)+4;
        }
        else if(timeZone().equals("-05:00")){
            start_hour = cal.get(Calendar.HOUR_OF_DAY)+5;
            end_hour = cal2.get(Calendar.HOUR_OF_DAY)+5;
        }
        else if(timeZone().equals("-06:00")){
            start_hour = cal.get(Calendar.HOUR_OF_DAY)+6;
            end_hour = cal2.get(Calendar.HOUR_OF_DAY)+6;
        }
        else if(timeZone().equals("-07:00")){
            start_hour = cal.get(Calendar.HOUR_OF_DAY)+7;
            end_hour = cal2.get(Calendar.HOUR_OF_DAY)+7;
        }

        else if(timeZone().equals("-08:00")){
            start_hour = cal.get(Calendar.HOUR_OF_DAY)+8;
            end_hour = cal2.get(Calendar.HOUR_OF_DAY)+8;
        }
        else if(timeZone().equals("-02:00")){
            start_hour = cal.get(Calendar.HOUR_OF_DAY)+2;
            end_hour = cal2.get(Calendar.HOUR_OF_DAY)+2;
        }
        else if(timeZone().equals("-01:00")){
            start_hour = cal.get(Calendar.HOUR_OF_DAY)+1;
            end_hour = cal2.get(Calendar.HOUR_OF_DAY)+1;
        }
        else if(timeZone().equals("00:00")){
            start_hour = cal.get(Calendar.HOUR_OF_DAY);
            end_hour = cal2.get(Calendar.HOUR_OF_DAY);
        }


        if(end_hour>24){
            end_hour=01;
        }
        int start_minute = cal.get(Calendar.MINUTE);

        int end_minute = cal2.get(Calendar.MINUTE);
        String endminute,startminute;
        if(end_minute ==0){
            endminute= "00";
        }
        else if(end_minute ==05){
            endminute= "05";
        }
        else {
            endminute =  String.valueOf(end_minute);
        }
        if(start_minute ==0){
            startminute="00";
        }

        else if(start_minute==05){
            startminute= "05";
        }
        else {
            startminute =  String.valueOf(start_minute);
        }




        holder.time.setText(start_hour+":"+startminute+"-"+end_hour+":"+endminute);
        //view.setBackgroundColor(context.getResources().getColor(R.color.modulo));

        String eventType = eventList.get(position).getType();
        //Si el evento es break o social, no lleva speakers y lleva lugar

        if(eventType!=null){
            if(eventList.get(position).getParseFileV1()==null){
                holder.icon.setVisibility(View.GONE);
            }
            if(eventType.equals("break")||
                    eventType.equals("Break")||
                    eventType.equals("social")||
                    eventType.equals("Social")||
                    eventType.equals("Desconocido")||
                    eventType.equals("desconocido"))
            {
                if(!b&&!det){

                    holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.eventoTerciario));
                    //holder.infoLayout.setBackgroundColor(Color.WHITE);
                    view.setBackgroundColor(context.getResources().getColor(R.color.eventoTerciario));
                    holder.fl.setBackgroundColor(context.getResources().getColor(R.color.brk));
                    holder.rl.setBackgroundColor(context.getResources().getColor(R.color.brk));
                    holder.place.setTextColor(bgColor);
                    holder.date.setTextColor(bgColor);
                    holder.name.setTextColor(bgColor);
                    holder.time.setTextColor(bgColor);
                    holder.speakers.setVisibility(View.GONE);
                    holder.date.setVisibility(View.VISIBLE);
                    int year = cal.get(Calendar.YEAR);
                    if(Locale.getDefault().getLanguage().equals("en")){
                        Locale spanish = new Locale("es", "ES");
                        month = cal.getDisplayName(Calendar.MONTH,Calendar.LONG, spanish);
                    }
                    else {
                        Locale spanish = new Locale("es", "ES");
                        month = cal.getDisplayName(Calendar.MONTH,Calendar.LONG, spanish);
                    }

                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    holder.date.setText(month +" "+day+", "+ year);

                    if (eventList.get(position).getType().equals("social")){
                        if(eventList.get(position).getParseFileV1()!=null){

                            holder.icon.setVisibility(View.VISIBLE);
                            final ParseFile photoFile = eventList.get(position).getParseFileV1();
                            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(holder.icon.getLayoutParams());
                            holder.icon.setLayoutParams(lp);
                            lp.width = 120;
                            lp.height = 120;
                            lp.setMargins(0,5,20,0);
                            holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.brk));
                            //holder.infoLayout.setBackgroundColor(Color.WHITE);
                            view.setBackgroundColor(context.getResources().getColor(R.color.brk));
                            if (photoFile != null) {
                                //Get singleton instance of ImageLoader
                                ImageLoader imageLoader = ImageLoader.getInstance();
                                //Load the image from the url into the ImageView.
                                imageLoader.displayImage(photoFile.getUrl(), holder.icon);
                            }

                            else {

                            }
                        }


                    }

                }

                else {
                    if(eventList.get(position).getType().equals("break")){
                        holder.icon.setVisibility(View.VISIBLE);
                        holder.icon.setImageResource(R.drawable.cafe);
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(holder.icon.getLayoutParams());
                        lp.setMargins(0,0,20,0);
                        holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.brk));
                        //holder.infoLayout.setBackgroundColor(Color.WHITE);
                        view.setBackgroundColor(context.getResources().getColor(R.color.brk));
                    }
                    else if (eventList.get(position).getType().equals("social")){
                        if(eventList.get(position).getParseFileV1()!=null){

                            holder.icon.setVisibility(View.VISIBLE);
                            final ParseFile photoFile = eventList.get(position).getParseFileV1();
                            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(holder.icon.getLayoutParams());
                            holder.icon.setLayoutParams(lp);
                            lp.setMargins(0,5,20,0);
                            holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.brk));
                            //holder.infoLayout.setBackgroundColor(Color.WHITE);
                            view.setBackgroundColor(context.getResources().getColor(R.color.brk));
                            if (photoFile != null) {
                                //Get singleton instance of ImageLoader
                                ImageLoader imageLoader = ImageLoader.getInstance();
                                //Load the image from the url into the ImageView.
                                imageLoader.displayImage(photoFile.getUrl(), holder.icon);
                            }

                            else {

                            }
                        }

                        holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.brk));
                        //holder.infoLayout.setBackgroundColor(Color.WHITE);
                        view.setBackgroundColor(context.getResources().getColor(R.color.brk));

                    }
                    else {

                    }

                    if(eventList.get(position).getType().equals("Desconocido")){
                        if(myapp.getFavoriteApp(eventList.get(position).getObjectId())){
                            holder.fav.setImageResource(R.drawable.favorite);
                            holder.fav.setVisibility(View.VISIBLE);
                        }
                    }
//                Person <Actor> actors = eventList.get(position).getActors();
//                if(actors!=null){
//
//                    for(Actor actor:actors){
//                        String firstName = actor.getPerson().getFirstName();
//                        String lastName = actor.getPerson().getLastName();
//                        String salutation = actor.getPerson().getSalutation();
//                        holder.speakers.append(salutation+" "+firstName+" "+lastName+"\n");
//                        sp = false;
//                    }
//
//
//                }
                    else {
                        holder.speakers.setVisibility(View.GONE);
                    }

                }




                if(eventList.get(position).getPlace()!=null){
                    try {
                        holder.place.setText(eventList.get(position).getPlace().getName());
                    } catch (IllegalStateException e ){
                        holder.place.setText("");
                    }
                }
                else {
                    Log.i("LOG","LOG");
                }


//            RelativeLayout.MarginLayoutParams mlp = (RelativeLayout.MarginLayoutParams) holder.relativeLayout
//                    .getLayoutParams();
//            //**
//            mlp.setMargins(0, 0, 0, dpToPx(2));
                //
//            holder.relativeLayout.setLayoutParams(mlp);
            }
            //si el evento es especial o investigacion no lleva speakers en la celda y lleva lugar
            //el booleano es para comprobar si se necesita que la celda se muestre como en vista programa o no
            else if(((eventType.equals("special")|| eventType.equals("free work")
                    || eventType.equals("poster")))){

                if(b&&!det){
                    holder.speakers.setVisibility(View.GONE);
                    holder.place.setText(eventList.get(position).getPlace().getName());
                    holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.conferencia));
                    view.setBackgroundColor(context.getResources().getColor(R.color.conferencia));

                }
                if (!b && det){
                    holder.speakers.setVisibility(View.GONE);
                    holder.place.setText(eventList.get(position).getPlace().getName());
                    int year = cal.get(Calendar.YEAR);
                    if(Locale.getDefault().getLanguage().equals("en")){
                        Locale spanish = new Locale("es", "ES");
                        month = cal.getDisplayName(Calendar.MONTH,Calendar.LONG, spanish);
                    }
                    else {
                        Locale spanish = new Locale("es", "ES");
                        month = cal.getDisplayName(Calendar.MONTH,Calendar.LONG, spanish);
                    }
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    holder.date.setText(month +" "+day+", "+ year);
                    holder.date.setVisibility(View.VISIBLE);
                    holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.conferencia));
                    //holder.infoLayout.setBackgroundColor(Color.WHITE);
                    holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.conferencia));
                    view.setBackgroundColor(context.getResources().getColor(R.color.conferencia));

                }

                if(!b&&!det){

                    holder.speakers.setVisibility(View.GONE);
                    holder.date.setVisibility(View.VISIBLE);
                    int year = cal.get(Calendar.YEAR);
                    if(Locale.getDefault().getLanguage().equals("en")){
                        Locale spanish = new Locale("es", "ES");
                        month = cal.getDisplayName(Calendar.MONTH,Calendar.LONG, spanish);
                    }
                    else {
                        Locale spanish = new Locale("es", "ES");
                        month = cal.getDisplayName(Calendar.MONTH,Calendar.LONG, spanish);
                    }
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    holder.date.setText(month +" "+day+", "+ year);

                    //Llamando colores desde res/colors.xml
                    holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.conferencia));
                    view.setBackgroundColor(context.getResources().getColor(R.color.conferencia));

                    //modificar la posicion de la relative layout

//            RelativeLayout.MarginLayoutParams mlp = (RelativeLayout.MarginLayoutParams) holder.relativeLayout
//                    .getLayoutParams();
//            //**
//            mlp.setMargins(0, dpToPx(5), 0, dpToPx(5));
//            //
//            holder.relativeLayout.setLayoutParams(mlp);

                    //cambio de formato de texto

                    holder.place.setTextColor(bgColor);
                    holder.date.setTextColor(bgColor);
                    holder.name.setTextColor(bgColor);
                    holder.time.setTextColor(bgColor);

                    if(eventList.get(position).getPlace()!=null){
                        holder.place.setText(eventList.get(position).getPlace().getName());
                    }
                    else{
                        Log.i("LOG","LOG");
                    }

                }



            }



            else if (eventType.equals("curso")||
                    eventType.equals("Curso")||
                    eventType.equals("simposio")||
                    eventType.equals("trabajos libres")||
                    eventType.equals("modulo")||
                    eventType.equals("Modulo")||
                    eventType.equals("Trabajos Libres")
                    ){

                if(eventList.get(position).getParseFileV1()!=null){

                    holder.icon.setVisibility(View.VISIBLE);
                    final ParseFile photoFile = eventList.get(position).getParseFileV1();
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(holder.icon.getLayoutParams());
                    holder.icon.setLayoutParams(lp);
                    lp.width = 120;
                    lp.height = 120;
                    lp.setMargins(0,5,20,0);
                    holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.modulo));
                    //holder.infoLayout.setBackgroundColor(Color.WHITE);
                    view.setBackgroundColor(context.getResources().getColor(R.color.modulo));
                    if (photoFile != null) {
                        //Get singleton instance of ImageLoader
                        ImageLoader imageLoader = ImageLoader.getInstance();
                        //Load the image from the url into the ImageView.
                        imageLoader.displayImage(photoFile.getUrl(), holder.icon);
                    }

                    else {

                    }
                }

                if(b&&!det){
                    holder.speakers.setVisibility(View.VISIBLE);
                    if(myapp.getFavoriteApp(eventList.get(position).getObjectId())){
                        holder.fav.setImageResource(R.drawable.favorite);

                        holder.fav.setVisibility(View.VISIBLE);
                    }
                    else {
                        holder.fav.setVisibility(View.INVISIBLE);
                    }
                    if(eventList.get(position).getPlace()==null){



                    }
                    else {
                        holder.place.setText(eventList.get(position).getPlace().getName());
                    }


//                List <Actor> actors = eventList.get(position).getActors();
//                Log.i("EVENTO",eventList.get(position).getTitle());
//                if(actors!=null && !actors.isEmpty()){
//
//                    for(Actor actor:actors){
//                        if(actor!=null){
//                            if(actor.getPerson()!=null){
//                                String firstName = actor.getPerson().getFirstName();
//                                String lastName = actor.getPerson().getLastName();
//                                String salutation = actor.getPerson().getSalutation();
//                                holder.speakers.append(salutation+" "+firstName+" "+lastName+"\n");
//                                sp = false;
//                            }
//
//                        }
//
//                    }
//
//
//                }
//                else {
//                    Log.i("LOG","LOG");
//                }

                    if(eventType.equals("Curso") || eventType.equals("curso")){
                        view.setBackgroundColor(context.getResources().getColor(R.color.curso));
                        holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.curso));

                    }
                    else {
                        view.setBackgroundColor(context.getResources().getColor(R.color.modulo));
                        holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.modulo));
                    }

                }

                if(!b&&det){
                    if(eventList.get(position).getPlace()!=null){
                        holder.place.setText(eventList.get(position).getPlace().getName());
                    }
                    else{
                        Log.i("LOG","LOG");
                    }

                    int year = cal.get(Calendar.YEAR);
                    if(Locale.getDefault().getLanguage().equals("en")){
                        Locale spanish = new Locale("es", "ES");
                        month = cal.getDisplayName(Calendar.MONTH,Calendar.LONG, spanish);
                    }
                    else {
                        Locale spanish = new Locale("es", "ES");
                        month = cal.getDisplayName(Calendar.MONTH,Calendar.LONG, spanish);
                    }
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    holder.date.setText(month +" "+day+", "+ year);
                    holder.speakers.setVisibility(View.GONE);
                    holder.date.setVisibility(View.VISIBLE);
                    if(eventType.equals("Curso") || eventType.equals("curso")){
                        view.setBackgroundColor(context.getResources().getColor(R.color.curso));
                        holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.curso));

                    }
                    else {
                        view.setBackgroundColor(context.getResources().getColor(R.color.modulo));
                        holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.modulo));
                    }

                }

                if(!b && !det){

                    holder.speakers.setVisibility(View.GONE);
                    holder.date.setVisibility(View.VISIBLE);
                    int year = cal.get(Calendar.YEAR);
                    if(Locale.getDefault().getLanguage().equals("en")){
                        Locale spanish = new Locale("es", "ES");
                        month = cal.getDisplayName(Calendar.MONTH,Calendar.LONG, spanish);
                    }
                    else {
                        Locale spanish = new Locale("es", "ES");
                        month = cal.getDisplayName(Calendar.MONTH,Calendar.LONG,spanish);
                    }
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    holder.date.setText(month +" "+day+", "+ year);

                    //Llamando colores desde res/colors.xml

                    if(eventType.equals("Curso") || eventType.equals("curso")){
                        holder.fl.setBackgroundColor(context.getResources().getColor(R.color.curso));
                        holder.rl.setBackgroundColor(context.getResources().getColor(R.color.curso));
                    }
                    else {
                        holder.fl.setBackgroundColor(context.getResources().getColor(R.color.modulo));
                        holder.rl.setBackgroundColor(context.getResources().getColor(R.color.modulo));
                    }

                    //modificar la posicion de la relative layout



//            RelativeLayout.MarginLayoutParams mlp = (RelativeLayout.MarginLayoutParams) holder.relativeLayout
//                    .getLayoutParams();
//            //**
//            mlp.setMargins(0, dpToPx(5), 0, dpToPx(5));
//            //
//            holder.relativeLayout.setLayoutParams(mlp);

                    //cambio de formato de texto

                    holder.place.setTextColor(bgColor);
                    holder.date.setTextColor(bgColor);
                    holder.name.setTextColor(bgColor);
                    holder.time.setTextColor(bgColor);
                    if(eventList.get(position).getPlace()!=null){
                        holder.place.setText(eventList.get(position).getPlace().getName());
                    }
                    else{
                        Log.i("LOG","LOG");
                    }

                }



            }





            else if(eventType.equals("conferencia") ||
                    eventType.equals("Conferencia") ) {

                if(b&&!det){
                    holder.speakers.setVisibility(View.VISIBLE);
                    if(myapp.getFavoriteApp(eventList.get(position).getObjectId())){
                        holder.fav.setImageResource(R.drawable.favorite);
                        holder.fav.setVisibility(View.VISIBLE);
                    }
                    else {
                        holder.fav.setVisibility(View.INVISIBLE);
                    }
                    holder.place.setVisibility(View.VISIBLE);
                    if(eventList.get(position).getPlace()!=null){
                        holder.place.setText(eventList.get(position).getPlace().getName());
                    }
                    else{
                        Log.i("LOG", "LOG");
                    }

//                if(eventList.get(position).getActors()!=null){
//                    List <Actor> actors = eventList.get(position).getActors();
//
//                    if(actors.isEmpty()){
//
//                        Log.i("HOLA","HI");
//                    }
//                    else {
//                        Log.i("actors",String.valueOf(actors));
//                        if(actors!=null){
//                            Log.i("EVENTO",eventList.get(position).getTitle());
//                            for(Actor actor:actors){
//
//                                if(actor.getPerson()!=null){
//                                    String firstName = actor.getPerson().getFirstName();
//                                    String lastName = actor.getPerson().getLastName();
//                                    String salutation = actor.getPerson().getSalutation();
//                                    holder.speakers.append(salutation+" "+firstName+" "+lastName+"\n");
//                                    sp = false;
//                                }
//
//                            }
//
//
//
//                        }
//                        else{
//                            Log.i("LOG","LOG");
//                        }
//                    }
                }

                else{
                    Log.i("LOG","LOG");
                }

                view.setBackgroundColor(context.getResources().getColor(R.color.conferencia));
                holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.conferencia));

            }

            if(!b&&!det){


                holder.speakers.setVisibility(View.GONE);
                holder.date.setVisibility(View.VISIBLE);
                int year = cal.get(Calendar.YEAR);
                if(Locale.getDefault().getLanguage().equals("en")){
                    Locale spanish = new Locale("es", "ES");
                    month = cal.getDisplayName(Calendar.MONTH,Calendar.LONG, spanish);
                }
                else {
                    Locale spanish = new Locale("es", "ES");
                    month = cal.getDisplayName(Calendar.MONTH,Calendar.LONG, spanish);
                }
                int day = cal.get(Calendar.DAY_OF_MONTH);
                holder.date.setText(month +" "+day+", "+ year);

                //Llamando colores desde res/colors.xml
                if(eventType.equals("conferencia")){
                    holder.fl.setBackgroundColor(context.getResources().getColor(R.color.conferencia));
                    holder.rl.setBackgroundColor(context.getResources().getColor(R.color.conferencia));
                }
                else if(eventType.equals("social") || eventType.equals("break")){
                    holder.fl.setBackgroundColor(context.getResources().getColor(R.color.brk));
                    holder.rl.setBackgroundColor(context.getResources().getColor(R.color.brk));
                }
                else if(eventType.equals("modulo")||eventType.equals("simposio")){
                    holder.fl.setBackgroundColor(context.getResources().getColor(R.color.modulo));
                    holder.rl.setBackgroundColor(context.getResources().getColor(R.color.modulo));
                }
                else {
                    holder.fl.setBackgroundColor(context.getResources().getColor(R.color.conferencia));
                    holder.rl.setBackgroundColor(context.getResources().getColor(R.color.conferencia));
                }
                //modificar la posicion de la relative layout

//            RelativeLayout.MarginLayoutParams mlp = (RelativeLayout.MarginLayoutParams) holder.relativeLayout
//                    .getLayoutParams();
//            //**
//            mlp.setMargins(0, dpToPx(5), 0, dpToPx(5));
//            //
//            holder.relativeLayout.setLayoutParams(mlp);

                //cambio de formato de texto

                holder.place.setTextColor(bgColor);
                holder.date.setTextColor(bgColor);
                holder.name.setTextColor(bgColor);
                holder.time.setTextColor(bgColor);
                if(eventList.get(position).getPlace()!=null){
                    holder.place.setText(eventList.get(position).getPlace().getName());
                }

            }

            if(!b&&det){
                if(eventList.get(position).getPlace()!=null){
                    holder.place.setText(eventList.get(position).getPlace().getName());
                }
                else{
                    Log.i("LOG","LOG");
                }

                int year = cal.get(Calendar.YEAR);
                if(Locale.getDefault().getLanguage().equals("en")){
                    Locale spanish = new Locale("es", "ES");
                    month = cal.getDisplayName(Calendar.MONTH,Calendar.LONG, spanish);
                }
                else {
                    Locale spanish = new Locale("es", "ES");
                    month = cal.getDisplayName(Calendar.MONTH,Calendar.LONG, spanish);
                }
                int day = cal.get(Calendar.DAY_OF_MONTH);
                holder.date.setText(month +" "+day+", "+ year);
                holder.speakers.setVisibility(View.GONE);
                holder.date.setVisibility(View.VISIBLE);
                view.setBackgroundColor(context.getResources().getColor(R.color.conferencia));
                holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.conferencia));

            }


            //}

//        else {
//            Log.i("LOG","LOG");
//        }

            //Si el evento es charla o modulo, la celda se muestra con los speakers

            //Si el evento tiene foto se muestra en la celda


        }

        else {
            if(b&&!det){
                holder.speakers.setVisibility(View.VISIBLE);
                if(myapp.getFavoriteApp(eventList.get(position).getObjectId())){
                    holder.fav.setImageResource(R.drawable.favorite);
                    holder.fav.setVisibility(View.VISIBLE);
                }
                else {
                    holder.fav.setVisibility(View.INVISIBLE);
                }
                holder.place.setVisibility(View.VISIBLE);
                if(eventList.get(position).getPlace()!=null){
                    holder.place.setText(eventList.get(position).getPlace().getName());
                }
                else{
                    Log.i("LOG", "LOG");
                }

//                if(eventList.get(position).getActors()!=null){
//                    List <Actor> actors = eventList.get(position).getActors();
//
//                    if(actors.isEmpty()){
//
//                        Log.i("HOLA","HI");
//                    }
//                    else {
//                        Log.i("actors",String.valueOf(actors));
//                        if(actors!=null){
//                            Log.i("EVENTO",eventList.get(position).getTitle());
//                            for(Actor actor:actors){
//
//                                if(actor.getPerson()!=null){
//                                    String firstName = actor.getPerson().getFirstName();
//                                    String lastName = actor.getPerson().getLastName();
//                                    String salutation = actor.getPerson().getSalutation();
//                                    holder.speakers.append(salutation+" "+firstName+" "+lastName+"\n");
//                                    sp = false;
//                                }
//
//                            }
//
//
//
//                        }
//                        else{
//                            Log.i("LOG","LOG");
//                        }
//                    }
            }

            else{
                Log.i("LOG","LOG");
            }

            view.setBackgroundColor(context.getResources().getColor(R.color.conferencia));
            holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.conferencia));

        }







        return view;
    }

    public static String timeZone()
    {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault());
        String   timeZone = new SimpleDateFormat("Z").format(calendar.getTime());
        return timeZone.substring(0, 3) + ":"+ timeZone.substring(3, 5);
    }
}
