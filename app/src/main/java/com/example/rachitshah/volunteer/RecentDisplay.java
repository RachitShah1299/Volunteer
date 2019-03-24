package com.example.rachitshah.volunteer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class RecentDisplay extends BaseAdapter {

    Context context;
    ArrayList<FoodRequestAcceptance> foodRequestAcceptances;



    public RecentDisplay(Context context, ArrayList<FoodRequestAcceptance> foodRequestAcceptances) {
        this.context=context;
        this.foodRequestAcceptances=foodRequestAcceptances;
    }

    @Override
    public int getCount() {
        return foodRequestAcceptances.size();
    }

    @Override
    public Object getItem(int position) {
        return foodRequestAcceptances.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.vol_rescent,parent,false);

        TextView name,loc,rec,dldate,dloc;

        name= (TextView)convertView.findViewById(R.id.rec_name);
        loc = (TextView) convertView.findViewById(R.id.rec_loc);
        rec = (TextView) convertView.findViewById(R.id.rec_req);
        dldate = (TextView) convertView.findViewById(R.id.dld);
        dloc = (TextView) convertView.findViewById(R.id.lcp);



        name.setText(foodRequestAcceptances.get(position).getRname());
        loc.setText(foodRequestAcceptances.get(position).getAddress());
        rec.setText(foodRequestAcceptances.get(position).getRid());
        dldate.setText(foodRequestAcceptances.get(position).getFddate());
        dloc.setText(foodRequestAcceptances.get(position).getLoc());



        return convertView;
    }
}
