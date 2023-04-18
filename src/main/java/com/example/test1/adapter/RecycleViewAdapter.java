package com.example.test1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test1.R;
import com.example.test1.model.Job;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ListViewHolder>{
    private List<Job> list;
    private ItemListener itemListener;

    public RecycleViewAdapter() {
        this.list = new ArrayList<>();
    }
    public Job getJob(int position){
        return list.get(position);
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Job job = list.get(position);
        holder.tvName.setText(job.getName());
        holder.tvContent.setText(job.getContent());
        String[] date = job.getFinishDate().trim().split("-");
        String d = date[2]+"/"+date[1]+"/"+date[0];
        holder.tvDate.setText(d);
        holder.tvStatus.setText(job.getStatus());
        if(job.isCollaborate()) holder.tvCollab.setText("Một mình");
        else holder.tvCollab.setText("Làm chung");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Job> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvName, tvContent, tvDate, tvStatus, tvCollab;
        public ListViewHolder(@NonNull View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvContent = view.findViewById(R.id.tvContent);
            tvDate = view.findViewById(R.id.tvDate);
            tvStatus = view.findViewById(R.id.tvStatus);
            tvCollab = view.findViewById(R.id.tvCollaborate);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(itemListener!=null){
                itemListener.onItemClick(v, getAdapterPosition());
            }
        }
    }
    public interface ItemListener{
        void onItemClick(View view, int position);
    }
}
