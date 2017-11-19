package id.co.kakaroto.iak_conncettointernetdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kakaroto on 18/11/17.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    List<Double> speeds;

    public Adapter(List<Double> speeds) {
        this.speeds = speeds;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Double speed = speeds.get(position);
        holder.tvSpeed.setText(String.valueOf(speed));
    }

    @Override
    public int getItemCount() {
        return speeds.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSpeed;

        public ViewHolder(View itemView) {
            super(itemView);
            tvSpeed = itemView.findViewById(R.id.tv_speed);
        }
    }

    public void addAll(List<Double> speeds) {
        this.speeds = speeds;
        notifyDataSetChanged();
    }
}
