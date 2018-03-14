package com.example.mattr.myyearbook;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.mattr.myyearbook.WeatherActivity.context;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Weather> weatherList;
    final private ListItemClickListener onClickListener;

public MyAdapter(List<Weather> weatherList, ListItemClickListener listener) {
    this.weatherList = weatherList;
    onClickListener = listener;
}

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int numberOfItems) {
    Context context = viewGroup.getContext();
    int layoutIdForListItem = R.layout.list_item;
    LayoutInflater inflater = LayoutInflater.from(context);

    View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
    ViewHolder viewHolder = new ViewHolder(view);
    return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    Weather weather = weatherList.get(position);
    holder.day.setText(weather.getDay());
    holder.date.setText(weather.getDate());
    holder.degHigh.setText(String.valueOf(weather.getHigh() + "°F"));
    holder.degLow.setText(String.valueOf(weather.getLow() + "°F"));
    holder.description.setText(weather.getDescription());
    Picasso.with(context).load(weather.getIconUrl()).into(holder.icon);
}

    @Override
    public int getItemCount() {
    return weatherList.size();
    }

    public void clear() {
    final int size = weatherList.size();
    weatherList.clear();
    notifyItemRangeRemoved(0, size);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView day;
        TextView date;
        TextView degHigh;
        TextView degLow;
        TextView description;
        ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.day);
            date = itemView.findViewById(R.id.date);
            degHigh = itemView.findViewById(R.id.degHigh);
            degLow = itemView.findViewById(R.id.degLow);
            description = itemView.findViewById(R.id.description);
            icon = itemView.findViewById(R.id.iconImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        int clickedPosition = getAdapterPosition();
            Log.i("adapter", String.valueOf(clickedPosition));
        onClickListener.onListItemClick(clickedPosition);
        }

//        void bind(int position) {
//            Weather weather =
//            day.setText(objDay);
//            date.setText(objDate);
//            degrees.setText(String.valueOf(objDegrees));
//            description.setText(objDescription);
//        }
    }
}