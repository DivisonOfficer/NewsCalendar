package edu.skku.cs.mysimplecalendar.activity.main;

import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

import edu.skku.cs.mysimplecalendar.R;
import edu.skku.cs.mysimplecalendar.databinding.ItemCalendarDateBinding;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {

    private Integer month = 5, year = 2022;
    private Integer startDay = 0;
    private Integer endOfMonth = 0;
    private Integer currentDate = 0;

    private OnClick onClick;
    public CalendarAdapter(OnClick onClick)
    {
        this.onClick = onClick;
    }

    ArrayList<Pair<Integer,String>> ovals = new ArrayList<>();


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_calendar_date, parent, false), parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(position < startDay || position > endOfMonth + startDay - 1)
        holder.bind(-1);
        else holder.bind(position - startDay + 1);
    }

    @Override
    public int getItemCount() {
        return 42;
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemCalendarDateBinding binding;
        Context context;
        ViewHolder(ItemCalendarDateBinding binding, Context context)
        {
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;
        }

        public void bind(Integer day){
            binding.setDate(day);
            binding.setAdapter(null);
            if(day>0)
            {
                GridIconAdapter adapter = new GridIconAdapter();
                ArrayList<String> cats = new ArrayList<>();
                ovals.stream().filter(pair-> pair.first.equals(day)).map(par-> par.second).forEach(cats::add);
                adapter.ovals = cats;
                binding.setAdapter(adapter);

                Log.d("CalendarAdapter","day" + day);

                if(day.equals(currentDate)) binding.setCurrentDate(true);
                else binding.setCurrentDate(false);
                binding.getRoot().setOnClickListener(view->{
                   onClick.setCurrentDate(day);
                });
            }
            else {
                binding.getRoot().setOnClickListener(null);
                binding.setCurrentDate(false);
            }
        }

    }
    public void setOvals( ArrayList<Pair<Integer,String>> ovals )
    {
        this.ovals = ovals;
        notifyItemRangeChanged(0,42);
    }

    public void setYear(Integer year)
    {
        this.year = year;
    }
    public void setMonth(Integer month){
        this.month = month;
        this.currentDate = -10;
        setCalendar();
    }
    public void setCurrentDate(Integer date)
    {
        Integer old = this.currentDate;
        this.currentDate = date;
        notifyItemChanged(this.currentDate + startDay -1);
        notifyItemChanged(old + startDay- 1);
    }

    private void setCalendar()
    {


        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month-1,1);
        startDay = (calendar.get(Calendar.DAY_OF_WEEK) + 5) % 7;
        calendar.add(Calendar.MONTH,1);
        calendar.add(Calendar.DATE,-1);
        endOfMonth = calendar.get(Calendar.DATE);

        currentDate = 0;
        notifyItemRangeChanged(0,42);

    }

    public interface OnClick{
        public void setCurrentDate(Integer day);
    }
}
