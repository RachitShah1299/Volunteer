package com.example.rachitshah.volunteer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class Vol_home_request extends BaseAdapter {

    Context context;
    ArrayList<FoodRequest> foodrequest;

    public Vol_home_request(Context context, ArrayList<FoodRequest> foodrequest) {
        this.context = context;
        this.foodrequest = foodrequest;

    }

    @Override
    public int getCount() {
        return foodrequest.size();
    }

    @Override
    public Object getItem(int position) {

        return foodrequest.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.home_demo, parent, false);

        TextView name, loc;
        name = (TextView) convertView.findViewById(R.id.name);
        loc = (TextView) convertView.findViewById(R.id.loc);


        final String resname= foodrequest.get(position).getRname();

        Log.e("RESAAANAME","IS: "+ resname);
        name.setText(foodrequest.get(position).getRname());
        loc.setText(foodrequest.get(position).getAddress());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            /*    String key = foodrequest.get(position).getKey();

                new SampleHome(key);
*/
                Intent it = new Intent(context, SampleHome.class);

                it.putExtra("key",foodrequest.get(position).getKey());
                it.putExtra("rname",foodrequest.get(position).getRname());
                it.putExtra("date",foodrequest.get(position).getFddate());
                it.putExtra("Address",foodrequest.get(position).getAddress());
                it.putExtra("rid",position);
                it.putExtra("desc",foodrequest.get(position).getdesc());

                context.startActivity(it);


            }
        });

        return convertView;
    }


    /*private void savedata(){
        SharedPreferences sharedPreferences =
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Key",key);
        editor.putString("Name", name.getText().toString());

    }*/
}
