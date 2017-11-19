package id.co.kakaroto.iak_conncettointernetdemo;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import id.co.kakaroto.iak_conncettointernetdemo.model.Weather;

/**
 * Created by kakaroto on 19/11/17.
 */

public class DetailActivity extends AppCompatActivity {

    private final String TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tvDt = findViewById(R.id.tv_dt_content);
        TextView tvPressure = findViewById(R.id.tv_presuare_content);
        TextView tvHumidity = findViewById(R.id.tv_humidity_content);
        TextView tvMain = findViewById(R.id.tv_main_content);
        TextView tvDescription = findViewById(R.id.tv_description_content);

/*        double dt = getIntent().getDoubleExtra("key-dt", 0);
        double pressure = getIntent().getDoubleExtra("key-pressure", 0);
        double humidity = getIntent().getDoubleExtra("key-humidity", 0);*/

        Weather weather = getIntent().getParcelableExtra("key-weather");

        tvDt.setText(String.valueOf(weather.getDt()));
        tvPressure.setText(String.valueOf(weather.getPressure()));
        tvHumidity.setText(String.valueOf(weather.getHumidity()));
        tvMain.setText(weather.getMain());
        tvDescription.setText(weather.getDescription());

    }


    public void openOtherIntent(View view) {
        Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(settingsIntent);
    }
}
