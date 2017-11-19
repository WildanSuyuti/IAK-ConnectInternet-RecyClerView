package id.co.kakaroto.iak_conncettointernetdemo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kakaroto on 19/11/17.
 */

public class Weather implements Parcelable {
    private String main;
    private String description;
    private double dt;
    private double pressure;
    private double humidity;

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDt() {
        return dt;
    }

    public void setDt(double dt) {
        this.dt = dt;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "main='" + main + '\'' +
                ", description='" + description + '\'' +
                ", dt=" + dt +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.main);
        dest.writeString(this.description);
        dest.writeDouble(this.dt);
        dest.writeDouble(this.pressure);
        dest.writeDouble(this.humidity);
    }

    public Weather() {
    }

    protected Weather(Parcel in) {
        this.main = in.readString();
        this.description = in.readString();
        this.dt = in.readDouble();
        this.pressure = in.readDouble();
        this.humidity = in.readDouble();
    }

    public static final Parcelable.Creator<Weather> CREATOR = new Parcelable.Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel source) {
            return new Weather(source);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };
}
