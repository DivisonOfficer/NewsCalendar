package edu.skku.cs.mysimplecalendar.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.ArrayList;

import edu.skku.cs.mysimplecalendar.R;
import edu.skku.cs.mysimplecalendar.databinding.ItemNewsPreviewBinding;
import edu.skku.cs.mysimplecalendar.datamodels.remote.NewsData;

public class BottomPlanAdapter extends RecyclerView.Adapter<BottomPlanAdapter.ViewHolder> {

    private ArrayList<NewsData> newsList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_news_preview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(newsList.get(position));
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ItemNewsPreviewBinding binding;
        public ViewHolder(@NonNull ItemNewsPreviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(NewsData data)
        {
            binding.setNews(data);
            if(data.urlToImage != null)
                Glide.with(binding.ivThumb).asDrawable().load(data.urlToImage).transform(new CenterCrop()).into(binding.ivThumb);

            binding.llPlan.setOnClickListener(v->{
                binding.tvTitle.setMaxLines(3);
                binding.tvDescription.setMaxLines(10);
                binding.getRoot().requestLayout();

            });

        }
    }

    public void updateList(ArrayList<NewsData> data)
    {
        newsList = data;
        notifyItemRangeChanged(0,getItemCount());
    }
}
