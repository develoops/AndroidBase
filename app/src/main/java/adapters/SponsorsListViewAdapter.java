package adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.ParseFile;

import java.util.ArrayList;
import java.util.List;

import mc.sms.R;
import model.Actividad;
import model.Org;

/**
 * Created by Alvaro on 2/21/15.
 */
public class SponsorsListViewAdapter extends BaseAdapter  {

    Context context;
    LayoutInflater inflater;
    private List<Org> orgList= null;
    private ArrayList<Org> arraylist;
    public Actividad meetingApp;

    public SponsorsListViewAdapter(Context context,
                                   List<Org> organizaciones) {




        this.context = context;
        this.orgList = organizaciones;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(orgList);

    }


    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView name_sponsor;

        ImageView icon_sponsor;
    }
    @Override
    public int getCount() {
        return orgList.size();
    }

    @Override
    public Object getItem(int position) {
        return orgList.get(position);
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


            view = inflater.inflate(R.layout.cell_sponsor, null);

            // Locate the TextViews in listview_item.xml
            holder.name_sponsor = (TextView) view.findViewById(R.id.charge);


            // Locate the ImageView in listview_item.xml
            holder.icon_sponsor = (ImageView) view.findViewById(R.id.icon_speaker);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
            holder.name_sponsor.setText("");


        }


        String firstName = orgList.get(position).getName();

        holder.name_sponsor.setText(firstName);

        if(orgList.get(position).getimgPerfil()==null){
            holder.icon_sponsor.setImageResource(R.drawable.speaker_nofoto);
        }

        else{
            ParseFile photoFile = orgList.get(position).getimgPerfil();

            if (photoFile != null) {
                //Get singleton instance of ImageLoader
                ImageLoader imageLoader = ImageLoader.getInstance();
                //Load the image from the url into the ImageView.

                imageLoader.displayImage(photoFile.getUrl(), holder.icon_sponsor);


            }
            else{
                Log.i("LOG","LOG");
            }
            Log.i("LOG","LOG");
        }



        return view;
    }
}
