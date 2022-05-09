package edu.skku.cs.mysimplecalendar.activity.main;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

import edu.skku.cs.mysimplecalendar.R;
import edu.skku.cs.mysimplecalendar.databinding.ItemCategoryChipBinding;

public class CategoryChip {



    private ChipGroup chipGroup;
    private Context context;
    public CategoryChip(Context context, ChipGroup chip)
    {
        this.chipGroup = chip;
        this.context = context;

        addDefaultCategory();
    }

    private void addDefaultCategory(){
        String[] category = context.getResources().getStringArray(R.array.default_category);
        int[] colors = context.getResources().getIntArray(R.array.item_color);
        Integer cnt = category.length;
        for(int i = 0; i < cnt ; i++)
        {
            addChip(category[i], colors[i]);
        }
    }

    private String currentChip = "";

    private ArrayList<ItemCategoryChipBinding> chips = new ArrayList<>();

    public void addChip(String text, Integer color)
    {
        ItemCategoryChipBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_category_chip,this.chipGroup,false);
        View chip = binding.getRoot();
        binding.btChip.setBackgroundTintList(ColorStateList.valueOf(color));

        binding.setCategory(text);
        binding.getRoot().setOnClickListener(v->{
            if(currentChip.equals(text))
            {
                currentChip = "";
                binding.btChip.setSelected(false);
            }
            else{
                currentChip = text;
                chips.forEach(bind->{
                    bind.btChip.setSelected(false);
                });
                binding.btChip.setSelected(true);
            }

        });

        chips.add(binding);
        chipGroup.addView(chip);
    }

}
