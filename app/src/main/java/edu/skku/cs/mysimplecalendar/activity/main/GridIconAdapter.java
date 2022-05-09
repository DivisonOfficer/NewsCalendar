package edu.skku.cs.mysimplecalendar.activity.main;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import edu.skku.cs.mysimplecalendar.R;
import edu.skku.cs.mysimplecalendar.databinding.ItemNewsOvalBinding;

public class GridIconAdapter extends RecyclerView.Adapter<GridIconAdapter.ViewHolder> {

    public ArrayList<String> ovals = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_news_oval,parent,false),parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(ovals.get(position));
    }

    @Override
    public int getItemCount() {
        return Math.min(ovals.size(), 6);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Context context;
        ItemNewsOvalBinding binding;

        public ViewHolder(ItemNewsOvalBinding binding, Context context) {
            super(binding.getRoot());
            this.context = context;
            this.binding = binding;
        }

        public void bind(String category)
        {
            String[] arr = context.getResources().getStringArray(R.array.default_category);
            Integer idx = 0;
            for(;idx<arr.length;idx++)
            {
                if(arr[idx].equals(category)) break;
            }
            if(idx >= arr.length) idx = 0;
            binding.icOval.setColorFilter(context.getResources().getIntArray(R.array.item_color)[idx]);
            Log.d("Ovals","Bind plan cateogry" +category + idx);
        }
    }

}
