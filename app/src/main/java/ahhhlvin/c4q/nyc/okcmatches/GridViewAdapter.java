package ahhhlvin.c4q.nyc.okcmatches;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by alvin2 on 1/12/16.
 */
public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.ViewHolder> {
    private ArrayList<OKCProfile> mProfiles;
    private Context mContext;


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView profileIV;
        public TextView usernameTV;
        public TextView ageLocationTV;
        public TextView matchPercentageTV;

        public ViewHolder(View v) {
            super(v);
            profileIV = (ImageView) v.findViewById(R.id.profileIV);
            usernameTV = (TextView) v.findViewById(R.id.usernameTV);
            ageLocationTV = (TextView) v.findViewById(R.id.ageLocationTV);
            matchPercentageTV = (TextView) v.findViewById(R.id.matchPercentageTV);

        }
    }


    public GridViewAdapter(Context context, ArrayList<OKCProfile> profiles) {
        mContext = context;
        mProfiles = profiles;
    }

    @Override
    public GridViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.matches_card_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final OKCProfile profileData = mProfiles.get(position);

        Picasso.with(mContext).load(profileData.getImageURL()).into(holder.profileIV);
        String ageLocation = profileData.getAge() + " • " + profileData.getLocation();
        String matchPercentage = profileData.getMatchPercentage() + "%" + "\n Match";
        holder.usernameTV.setText(profileData.getUsername());
        holder.ageLocationTV.setText(ageLocation);
        holder.matchPercentageTV.setText(matchPercentage);

    }

    @Override
    public int getItemCount() {
        return mProfiles.size();
    }

}

