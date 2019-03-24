package com.example.rachitshah.volunteer.Recent_Listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rachitshah.volunteer.R;

import java.util.ArrayList;

public class Rec_MyBaseAdapter extends BaseAdapter {
    ArrayList<Rec_DataModel> dataModelArrayList;
    Context context;

    public Rec_MyBaseAdapter(Context recentFragment, ArrayList<Rec_DataModel> dataModelArrayList) {
        this.context = recentFragment;
        this.dataModelArrayList = dataModelArrayList;
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.vol_rescent, null);

        ImageView resimage;
        TextView resname;
        TextView resloc;
        TextView rid;
        TextView reqid;
        TextView stat;
        TextView stts;
        TextView del;
        TextView delt;
        TextView deloc;
        TextView delplace;

        resimage = (ImageView) view.findViewById(R.id.img);
        resname = (TextView) view.findViewById(R.id.name);
        resloc = (TextView) view.findViewById(R.id.loc);
        rid = (TextView) view.findViewById(R.id.rid);
        reqid = (TextView) view.findViewById(R.id.req);
        stat = (TextView) view.findViewById(R.id.stat);
        stts = (TextView) view.findViewById(R.id.stt);
        del = (TextView) view.findViewById(R.id.dl);
        delt = (TextView) view.findViewById(R.id.dld);
        deloc = (TextView) view.findViewById(R.id.lc);
        delplace = (TextView) view.findViewById(R.id.lcp);


        resimage.setImageResource(dataModelArrayList.get(i).getImage());
        resname.setText(dataModelArrayList.get(i).getImages());
        resloc.setText(dataModelArrayList.get(i).getLocs());
        reqid.setText(dataModelArrayList.get(i).getReqids());
        delt.setText(dataModelArrayList.get(i).getDons());
        delplace.setText(dataModelArrayList.get(i).getDlocs());
        return view;
    }
}
