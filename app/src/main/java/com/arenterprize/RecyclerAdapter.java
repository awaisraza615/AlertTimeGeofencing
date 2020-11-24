package com.arenterprize;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerAdapterViewHolder>{

    Context context ;
    ArrayList<Post> posts;
    public RecyclerAdapter(ArrayList<Post> posts,Context context)
    {
        this.context=context;
        this.posts=posts;
    }

    @NonNull
    @Override
    public RecyclerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item,parent,false);

        return new RecyclerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterViewHolder holder, final int position) {

        holder.rname.setText("Name : "+posts.get(position).getName());
        holder.rdate.setText("Date : "+posts.get(position).getDate());
        holder.rtime.setText("Time : "+posts.get(position).getTime());
        String logitude = String.valueOf("longitude : "+posts.get(position).getLogitude());
        String latitude = String.valueOf("latitude : "+posts.get(position).getLatitude());
        holder.rlogitude.setText(logitude);
        holder.rlatitude.setText(latitude);

        Toast.makeText(context, "hghffgh"+posts.get(position).getLogitude(), Toast.LENGTH_SHORT).show();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MapsActivity.class);
                intent.putExtra("lng", posts.get(position).getLogitude());
                intent.putExtra("lat", posts.get(position).getLatitude());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class RecyclerAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView rname,rdate,rtime,rlatitude,rlogitude;

        public RecyclerAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            rname =(TextView) itemView.findViewById(R.id.rmname);
            rdate =(TextView) itemView.findViewById(R.id.rmdate);
            rtime =(TextView) itemView.findViewById(R.id.rmtime);
            rlatitude =(TextView) itemView.findViewById(R.id.logitude);
            rlogitude =(TextView) itemView.findViewById(R.id.latitude);

        }
    }
}

