package ahhhlvin.c4q.nyc.okcmatches;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class MatchesActivity extends AppCompatActivity {

    public static final String url = "https://www.okcupid.com/matchSample.json";
    ArrayList<OKCProfile> profileList;
    GridView matchesGrid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        profileList = new ArrayList<>();
        matchesGrid = (GridView) findViewById(R.id.matchesGridView);

        new getProfiles().execute();

        for (int i = 0; i < profileList.size(); i++) {
            Log.i("ALVIN", profileList.get(i).getUsername());
        }


    }


    private class getProfiles extends AsyncTask<Void, Void, ArrayList<OKCProfile>> {

        OkHttpClient client = new OkHttpClient();

        String run(String jsonUrl) throws IOException {
            Request request = new Request.Builder().url(jsonUrl).build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }


        @Override
        protected ArrayList<OKCProfile> doInBackground(Void... arg0) {

            try {
                JSONObject jsonObj = new JSONObject(run(url));
                JSONArray profileArray = jsonObj.getJSONArray("data");

                if (jsonObj == null) {
                    Toast.makeText(getApplicationContext(), "jsonObj is null", Toast.LENGTH_SHORT).show();
                } else {

                    for (int i = 0; i < profileArray.length(); i++) {
                        JSONObject profileObj = profileArray.getJSONObject(i);

                        OKCProfile profile = new OKCProfile();
                        profile.setUsername(profileObj.get("username").toString());
                        profile.setAge(Integer.valueOf(profileObj.get("age").toString()));
                        profile.setLocation(profileObj.getJSONObject("location").get("city_name") + ", " + profileObj.getJSONObject("location").get("country_code"));
                        profile.setImageURL(profileObj.getJSONObject("photo").get("base_path").toString());
                        profile.setMatchPercentage(Integer.valueOf(profileObj.get("match").toString()));

                        profileList.add(profile);

                    }
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

            matchesGrid.setAdapter(new GridAdapter(MatchesActivity.this, list));
            Toast.makeText(getApplicationContext(), "There are " + String.valueOf(profileList.size()) + " profiles!", Toast.LENGTH_SHORT).show();


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_matches, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
