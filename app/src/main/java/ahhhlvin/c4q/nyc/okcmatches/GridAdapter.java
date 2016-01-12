package ahhhlvin.c4q.nyc.okcmatches;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by alvin2 on 1/11/16.
 */
public class GridAdapter extends BaseAdapter {
    private Activity mContext;
    private ArrayList<OKCProfile> mMatchesList;
    private LayoutInflater mLayoutInflater = null;
    public GridAdapter(Activity context, ArrayList<OKCProfile> matchesList) {
        mContext = context;
        mMatchesList = matchesList;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return mMatchesList.size();
    }
    @Override
    public Object getItem(int pos) {
        return mMatchesList.get(pos);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        CompleteListViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.matches_card_layout, null);
            viewHolder = new CompleteListViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (CompleteListViewHolder) v.getTag();
        }

        OKCProfile profileData = mMatchesList.get(position);

        Picasso.with(mContext).load(profileData.getImageURL()).into(viewHolder.profileIV);
        viewHolder.usernameTV.setText(profileData.getUsername());
        viewHolder.ageLocationTV.setText(profileData.getAge() + " • " + profileData.getLocation());
        viewHolder.matchPercentageTV.setText(profileData.getMatchPercentage());

        return v;
    }
}
class CompleteListViewHolder {
    public ImageView profileIV;
    public TextView usernameTV;
    public TextView ageLocationTV;
    public TextView matchPercentageTV;

    public CompleteListViewHolder(View base) {
        profileIV = (ImageView) base.findViewById(R.id.profileIV);
        usernameTV = (TextView) base.findViewById(R.id.usernameTV);
        ageLocationTV = (TextView) base.findViewById(R.id.ageLocationTV);
        matchPercentageTV = (TextView) base.findViewById(R.id.matchPercentageTV);

    }
}
