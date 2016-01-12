package ahhhlvin.c4q.nyc.okcmatches;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MatchesActivity extends AppCompatActivity {

    private static final String URL = "https://www.okcupid.com/matchSample.json";
    ArrayList<OKCProfile> profileList;
    RecyclerView matchesGrid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        profileList = new ArrayList<>();



        // FOR TESTING
        for (int j = 0; j < 15; j++) {
            profileList.add(new OKCProfile(null, "ahhhlvin", 22, "Sunnyside, NY", 100));
        }


        new getProfiles().execute();

        for (int i = 0; i < profileList.size(); i++) {
            Log.i("ALVIN", profileList.get(i).getUsername());
        }


    }

    // Retrieves profile JSON on separate thread
    private class getProfiles extends AsyncTask<Void, Void, ArrayList<OKCProfile>> {

        OkHttpClient client = new OkHttpClient();


        String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }

        @Override
        protected ArrayList<OKCProfile> doInBackground(Void... arg0) {


            try {
                JSONObject jsonObj = new JSONObject(run(URL));
                JSONArray profileArray = jsonObj.getJSONArray("data");

                for (int i = 0; i < profileArray.length(); i++) {
                    JSONObject profileObj = profileArray.getJSONObject(i);

                    OKCProfile profile = new OKCProfile();
                    profile.setUsername(profileObj.get("username").toString());
                    profile.setAge(Integer.valueOf(profileObj.get("age").toString()));
                    profile.setLocation(profileObj.getJSONObject("location").get("city_name") + ", " + profileObj.getJSONObject("location").get("country_code"));
                    profile.setImageURL(profileObj.getJSONObject("full_paths").get("original").toString());
                    profile.setMatchPercentage(Integer.valueOf(profileObj.get("match").toString()));

                    profileList.add(profile);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("ServiceHandler", "Error retrieving data from URL");
            }

            return profileList;
        }


        @Override
        protected void onPostExecute(ArrayList<OKCProfile> list) {
            super.onPostExecute(list);

            matchesGrid = (RecyclerView) findViewById(R.id.matchesGridView);
            GridLayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
            matchesGrid.setLayoutManager(mLayoutManager);
            GridViewAdapter mAdapter = new GridViewAdapter(getApplicationContext(), profileList);
            matchesGrid.setAdapter(mAdapter);


            Toast.makeText(getApplicationContext(), "There are " + String.valueOf(profileList.size()) + " profiles!", Toast.LENGTH_SHORT).show();


        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_matches, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
