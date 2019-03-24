package com.example.rachitshah.volunteer.Vol_Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rachitshah.volunteer.R;

import java.util.ArrayList;

public class Vol_home_MyBaseAdapter extends BaseAdapter {

    ArrayList<Vol_home_datamodel> dataModelArrayList;
    Context context;
    ListView listView;

    public Vol_home_MyBaseAdapter(Context mainActivity, ArrayList<Vol_home_datamodel> dataModelArrayList) {
        this.context = mainActivity;
        this.dataModelArrayList = dataModelArrayList;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.vol_home_sample, null);

        ImageView resimage;
        final TextView resname;
        TextView resloc;
        TextView rid;
        TextView reqid;
        TextView stat;
        TextView stts;
        TextView ln;
        ImageButton yes;
        ImageButton no;


        resimage = (ImageView) view.findViewById(R.id.img);
        resname = (TextView) view.findViewById(R.id.name);
        resloc = (TextView) view.findViewById(R.id.loc);
        rid = (TextView) view.findViewById(R.id.rid);
        reqid = (TextView) view.findViewById(R.id.req);
        stat = (TextView) view.findViewById(R.id.stat);
        stts = (TextView) view.findViewById(R.id.stt);
        yes = (ImageButton) view.findViewById(R.id.yes);
        no = (ImageButton) view.findViewById(R.id.no);


        resimage.setImageResource(dataModelArrayList.get(i).getImage());
        resname.setText(dataModelArrayList.get(i).getImages());
        resloc.setText(dataModelArrayList.get(i).getLocs());
        reqid.setText(dataModelArrayList.get(i).getReqids());


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Accepted Request from " + resname.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Rejected", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public int getCount() {
        return dataModelArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataModelArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

}
