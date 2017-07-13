package test.julian.codetest.Presenters.Asynctasks;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import test.julian.codetest.Models.WeekDay;
import test.julian.codetest.R;

/**
 * Created by JulianStack on 12/07/2017.
 */

public class getWeatherData extends AsyncTask<String, String, JSONArray> {

    // GetUser Log Tag
    private String TAG = "getWeatherData";

    // Connection objects
    private HttpURLConnection connection;
    private URL url;

    // Objects
    private JSONArray jsonArray;
    private Context mContext;
    private getWeatherInterface mInterface;
    private ArrayList<WeekDay> Lista = new ArrayList<>();

    double Latitude;
    double Longitude;


    // Constructor
    public getWeatherData(getWeatherInterface minterface, Context context, double latitude, double longitude) {
        this.mContext = context;
        this.mInterface = minterface;
        this.Latitude = latitude;
        this.Longitude = longitude;
    }

    // Interface
    public interface getWeatherInterface {
        void success(ArrayList<WeekDay> lista);
        void failed(String messageError);
    }

    @Override
    protected JSONArray doInBackground(String... args) {
        try {


            connection = null;

            //Create connection
            url = new URL(String.format(mContext.getResources().getString(R.string.url),String.valueOf(Latitude),String.valueOf(Longitude)));
            connection = (HttpURLConnection)url.openConnection();
            // Set Method to Get
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            // Accept response
            connection.setDoInput(true);


            //Get Response
            String aux = "";
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                aux += line;
            }

            Log.d(TAG,aux);

            // Get Json Array
            JSONArray array = new JSONArray(aux);

            //Fill Users ArrayList
            for(int i = 0; i<array.length(); i++){

                // Get Json Objects
                JSONObject js = array.getJSONObject(i);

            }

            // Close connection
            rd.close();
            return jsonArray;

        } catch (Exception e) {

            cancel(true);
            e.printStackTrace();
            return null;

        } finally {

            if(connection != null) {
                connection.disconnect();
            }
        }
    }

    @Override
    protected void onPostExecute(JSONArray result) {
        Log.d(TAG, "SUCCESS");
        // Send ArrayList
        mInterface.success(Lista);
    }

    @Override
    protected void onCancelled(JSONArray result){
        Log.d(TAG, "CANCELLED");
        // Send Error Request Message
        mInterface.failed("");
    }
}