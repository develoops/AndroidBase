package adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseImageView;

import java.util.ArrayList;
import java.util.List;

import mc.sms.R;
import model.Media;

/**
 * Created by Alvaro on 3/9/15.
 */
public class GridDocumentsAdapter extends ArrayAdapter<Media> {
    Context context;

    int layoutResourceId;

    List<Media> data = new ArrayList<>();

    public GridDocumentsAdapter(Context context, int layoutResourceId,
                                 List<Media> data) {

        super(context, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RecordHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.title_doc);
            holder.size = (TextView) row.findViewById(R.id.size_doc);

            holder.imageItem = (ParseImageView) row.findViewById(R.id.doc_icon);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }

        Media mobiFile= data.get(position);
/*
        if(mobiFile.getThumbnail()!=null){
            ParseFile photoFile = mobiFile.getThumbnail();

            if (photoFile != null) {
                //Get singleton instance of ImageLoader
                ImageLoader imageLoader = ImageLoader.getInstance();
                //Load the image from the url into the ImageView.

                imageLoader.displayImage(photoFile.getUrl(), holder.imageItem);


            }
            else{
                Log.i("NO HAY FOTITO","NOFOTO");
            }
        }

        else {
            Log.i("NO HAY FOTITO","NOFOTO");

        }*/
        if(mobiFile!=null){
            holder.txtTitle.setText(mobiFile.getNombre());

        }
        Log.i("MOBIFILE",mobiFile.getObjectId());

        //holder.imageItem.setVisibility(View.GONE);
     /*   holder.size.setText(mobiFile.getSize() + " MB");


        if((mobiFile.getSize()).doubleValue()>=1){
            holder.size.setText(mobiFile.getSize().doubleValue() + " MB");
        }
        else {
            holder.size.setText(mobiFile.getSize().doubleValue()*1000 + " KB");

        }
*/


        return row;

    }

    static class RecordHolder {
        TextView txtTitle;
        TextView size;

        ImageView imageItem;

    }
}
