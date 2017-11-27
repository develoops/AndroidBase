package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import imageTreatment.RoundedImageView;
import mc.sms.R;
import mc.sms.myApp;
import model.Info;


/**
 * Created by Alvaro on 3/1/15.
 */
public class NewsListViewAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    public myApp myapp;
    private List<Info> newList= null;
    private ArrayList<Info> arraylist;


    public NewsListViewAdapter(Context context,
                                  List<Info> news) {
        myapp = (myApp) context.getApplicationContext();


        newList = news;



        this.context = context;
        this.newList =newList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(newList);

    }



    public class ViewHolder {
        TextView title_new;
        TextView content_new;
        TextView date_new;

        RoundedImageView icon_new;
    }
    @Override
    public int getCount() {
        return newList.size();
    }

    @Override
    public Object getItem(int position) {
        return newList.get(position);
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
            view = inflater.inflate(R.layout.cell_news, null);
            // Locate the TextViews in listview_item.xml
            holder.title_new = (TextView) view.findViewById(R.id.title_new);

            holder.content_new = (TextView) view.findViewById(R.id.content_new);

            //holder.date_new = (TextView) view.findViewById(R.id.date_new);
            // Locate the ImageView in listview_item.xml
            holder.icon_new = (RoundedImageView) view.findViewById(R.id.icon_new);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        /*
        try {
            newList.get(position).fetchFromLocalDatastore();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            newList.get(position).fetchIfNeeded();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        */
        holder.title_new.setText(newList.get(position).getTitle());
        holder.content_new.setText(newList.get(position).getContent());
        //holder.date_new.setText(newList.get(position).getDate());



        return view;
    }
}
