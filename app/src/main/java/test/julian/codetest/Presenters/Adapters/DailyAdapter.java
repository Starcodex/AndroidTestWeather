package test.julian.codetest.Presenters.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import test.julian.codetest.Models.WeekDay;
import test.julian.codetest.R;

/**
 * Created by JulianStack on 12/07/2017.
 */

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.MyViewHolder> {

    private List<WeekDay> horizontalList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtDay;
        public TextView txtTemp;
        public ImageView icon;

        public MyViewHolder(View view) {
            super(view);
            txtDay = (TextView) view.findViewById(R.id.itemday);
            txtTemp = (TextView) view.findViewById(R.id.itemtemp);
            icon = (ImageView) view.findViewById(R.id.itemicon);

        }
    }


    public DailyAdapter(List<WeekDay> horizontalList) {
        this.horizontalList = horizontalList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.txtDay.setText(horizontalList.get(position).getDay());
        holder.txtTemp.setText(horizontalList.get(position).getTemp());
        holder.icon.setImageResource(horizontalList.get(position).getIcon());

    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }
}