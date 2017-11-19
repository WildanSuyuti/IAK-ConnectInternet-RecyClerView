package id.co.kakaroto.iak_conncettointernetdemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    private TextView tvCity;
    private RecyclerView rvWeather;
    List<Double> speeds;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCity = findViewById(R.id.tv_city);
        rvWeather = findViewById(R.id.rv_weather);
        speeds = new ArrayList<>();
        adapter = new Adapter(speeds);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        rvWeather.setLayoutManager(layoutManager);
        rvWeather.setAdapter(adapter);

/*        speeds.add(4.5);
        speeds.add(5.9);
        speeds.add(4.5);
        speeds.add(5.9);
        speeds.add(4.4);
        speeds.add(5.3);
        speeds.add(4.2);
        speeds.add(5.5);
        speeds.add(4.6);
        speeds.add(5.9);
        adapter.addAll(speeds);*/


        //get api from url
        String url = "https://andfun-weather.udacity.com/weather";

        try {
            //save to variable -> result
            String result = new FetchData().execute(url).get();

            //buat jsonObject diisi dengan variabel result
            JSONObject jsonObject = new JSONObject(result);

            JSONObject objectCity = jsonObject.getJSONObject("city");
            tvCity.setText(objectCity.getString("name"));

            //ambil array data dari jsonObject ("list")
            JSONArray arrayData = jsonObject.getJSONArray("list");

            //buat perulangan untuk menampilkan data dalam array
            for (int i = 0; i < arrayData.length(); i++) {

                JSONObject object = arrayData.getJSONObject(i);

                //buat variabel double untuk mengambil data speed
                double speed = object.getDouble("speed");
                speeds.add(speed);
                adapter.addAll(speeds);
                //buat variabel int untuk mengambil data deg
                int deg = object.getInt("deg");

                //buat jsonObject dengan nama temp
                JSONObject temp = object.getJSONObject("temp");
//                Log.d(TAG, "onCreate TEMP day: " + temp.getDouble("day"));

                Log.d(TAG, "onCreate Speed: " + speed);
            }

            Log.d(TAG, "onCreate result : " + result);
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
    }

    public class FetchData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            String result;
            String inputLine;

            try {
                URL myUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();

                connection.setRequestMethod("GET");
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.connect();

                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                while ((inputLine = reader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }
                result = stringBuilder.toString();

            } catch (IOException e) {
                result = null;
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }
}
