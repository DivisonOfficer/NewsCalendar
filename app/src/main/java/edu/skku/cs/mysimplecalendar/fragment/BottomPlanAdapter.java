package edu.skku.cs.mysimplecalendar.fragment;

import android.util.Log;
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
import edu.skku.cs.mysimplecalendar.utils.PlanSlideUtil;

public class BottomPlanAdapter extends RecyclerView.Adapter<BottomPlanAdapter.ViewHolder> {

    OnLinkClick onclick;

    RecyclerView recyclerView;

    public void setRecyclerView(RecyclerView recyclerView)
    {
        this.recyclerView = recyclerView;
    }

    public void setScrapListener(Scrap listener)
    {
        this.listener = listener;
    }
    Scrap listener;


    public void setLinkClick(OnLinkClick onclick)
    {
        this.onclick = onclick;
    }

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
            PlanSlideUtil util = new PlanSlideUtil(binding.llPlan,binding.icStore, recyclerView, ()->{
                Log.d("PlanAdapter","Store");
                listener.action(data);
            });

            binding.setNews(data);
            if(data.urlToImage != null)
                Glide.with(binding.ivThumb).asDrawable().load(data.urlToImage).transform(new CenterCrop()).into(binding.ivThumb);

            binding.llPlan.setOnClickListener(v->{
                binding.tvTitle.setMaxLines(3);
                binding.tvDescription.setMaxLines(10);
                binding.getRoot().requestLayout();
                binding.btnLink.setVisibility(View.VISIBLE);

            });

            binding.btnLink.setOnClickListener(v->{
                onclick.action(data);
            });

        }
    }

    public void updateList(ArrayList<NewsData> data)
    {
        newsList = data;
        notifyItemRangeChanged(0,getItemCount());
    }

    public interface OnLinkClick{
        void action(NewsData data);
    }

    public interface Scrap{
        void action(NewsData data);
    }
}
