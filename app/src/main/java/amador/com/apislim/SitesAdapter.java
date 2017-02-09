package amador.com.apislim;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import static android.R.id.list;

/**
 * Created by usuario on 9/02/17.
 */

public class SitesAdapter extends ArrayAdapter<Site> {


    public SitesAdapter(Context context, List<Site> list) {
        super(context, R.layout.item_list, list);

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rootView = convertView;
        SiteHolder holder;

        if(rootView == null){

            holder = new SiteHolder();
            rootView = LayoutInflater.from(getContext()).inflate(R.layout.item_list, null);
            holder.txvEmailSite = (TextView)rootView.findViewById(R.id.txvEmailSite);
            holder.txvNameSite = (TextView)rootView.findViewById(R.id.txvNameSite);
            holder.txvLinkSite = (TextView)rootView.findViewById(R.id.txvLinkSite);
            rootView.setTag(holder);

        }else {

            holder = (SiteHolder)rootView.getTag();
        }

        holder.txvLinkSite.setText(getItem(position).getLink());
        holder.txvNameSite.setText(getItem(position).getName());
        holder.txvEmailSite.setText(getItem(position).getEmail());
        return rootView;

    }

    class SiteHolder{

        TextView txvNameSite, txvEmailSite, txvLinkSite;


    }

}
