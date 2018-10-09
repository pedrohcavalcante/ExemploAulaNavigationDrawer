package com.example.androidbti.exemploaulanavigationdrawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class PlanetAdapter extends BaseAdapter {

    private List<Planet> planetList;
    private onItemClickListener listener;

    public PlanetAdapter(Context context, onItemClickListener listener){
        planetList = Planet.buildPlanets(context);
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return planetList.size();
    }

    @Override
    public Object getItem(int i) {
        return planetList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return planetList.get(i).getImageId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        ViewHolder vh;
        final Planet planet = planetList.get(i);
        if (view == null){
            LayoutInflater li = LayoutInflater.from(viewGroup.getContext());
            view = li.inflate(R.layout.drawer_list_item, viewGroup, false);

            vh = new ViewHolder();
            vh.textView = view.findViewById(R.id.text1);
            vh.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(planet);
                }
            });
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        vh.textView.setText(planet.getName());
        return view;
    }

    public interface onItemClickListener{
        public void onClick(Planet planet);
    }

    private static class ViewHolder{
        public TextView textView;
    }
}
