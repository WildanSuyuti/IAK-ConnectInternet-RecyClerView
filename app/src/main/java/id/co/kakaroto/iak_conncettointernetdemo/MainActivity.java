package id.co.kakaroto.iak_conncettointernetdemo;

import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import id.co.kakaroto.iak_conncettointernetdemo.model.Weather;
import id.co.kakaroto.iak_conncettointernetdemo.utility.RecyclerTouchListener;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    private TextView tvCity;
    private RecyclerView rvWeather;
    List<Weather> weathers;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCity = findViewById(R.id.tv_city);
        rvWeather = findViewById(R.id.rv_weather);
        weathers = new ArrayList<>();
        adapter = new Adapter(weathers);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        rvWeather.setLayoutManager(layoutManager);
        rvWeather.setAdapter(adapter);

        rvWeather.addOnItemTouchListener(new RecyclerTouchListener(this, rvWeather,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Weather weather = weathers.get(position);
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
/*                        intent.putExtra("key-dt", weather.getDt());
                        intent.putExtra("key-pressure", weather.getPressure());
                        intent.putExtra("key-humidity", weather.getHumidity());
                        intent.putExtra("key-main", weather.getMain());
                        intent.putExtra("key-description", weather.getDescription());*/
                        intent.putExtra("key-weather", weather);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));


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
//                double speed = object.getDouble("speed");
//                weathers.add(speed);
                Weather weather = new Weather();
                weather.setDt(object.getDouble("dt"));
                weather.setPressure(object.getDouble("pressure"));
                weather.setHumidity(object.getDouble("humidity"));
                JSONArray weatherArray = object.getJSONArray("weather");

                JSONObject weatherArrayObject = weatherArray.getJSONObject(0);
                weather.setMain(weatherArrayObject.getString("main"));
                weather.setDescription(weatherArrayObject.getString("description"));
                weathers.add(weather);
                Log.d(TAG, "onCreate: " + weather.toString());
                //buat variabel int untuk mengambil data deg
                int deg = object.getInt("deg");

                //buat jsonObject dengan nama temp
                JSONObject temp = object.getJSONObject("temp");
//                Log.d(TAG, "onCreate TEMP day: " + temp.getDouble("day"));

//                Log.d(TAG, "onCreate Speed: " + speed);
            }

            adapter.addAll(weathers);

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
