package com.wuc.sunflower.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.wuc.sunflower.R;
import com.wuc.sunflower.data.PlantAndGardenPlantings;
import com.wuc.sunflower.databinding.ListItemGardenPlantingBinding;
import com.wuc.sunflower.frgaments.GardenFragmentDirections;
import com.wuc.sunflower.viewmodels.PlantAndGardenPlantingsViewModel;

/**
 * @author : wuchao5
 * @date : 3/1/21 6:03 PM
 * @desciption :
 */
public class GardenPlantingAdapter extends ListAdapter<PlantAndGardenPlantings, GardenPlantingAdapter.ViewHolder> {

  public GardenPlantingAdapter() {
    super(new GardenPlantDiffCallback());
  }

  @NonNull @Override public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ViewHolder(DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.list_item_garden_planting, parent, false));
  }

  @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    PlantAndGardenPlantings plantings = getItem(position);
    holder.itemView.setTag(plantings);
    holder.bind(createOnClickListener(plantings.getPlant().getPlantId()), plantings);
  }

  private View.OnClickListener createOnClickListener(String plantId) {
    return new View.OnClickListener() {
      @Override public void onClick(View v) {
        // Log.e("item", "onClick: 你点击了我的花园列表的Item，plantId:" + plantId);
        // 跳转到详情 并且把植物 id 给详情
        // 导航过去了 到 详情
        // Navigation.findNavController(v).navigate(
        //     GardenFragmentDirections.actionGardenFragmentToPlantDetailFragment(plantId)
        // );
        Navigation.findNavController(v).navigate(
            GardenFragmentDirections.actionGardenFragmentToPlantDetailFragment(plantId));
      }
    };
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    private ListItemGardenPlantingBinding binding; // item 每一项

    public ViewHolder(@NonNull ListItemGardenPlantingBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public void bind(View.OnClickListener listener, PlantAndGardenPlantings plantings) {
      binding.setViewModel(new PlantAndGardenPlantingsViewModel(plantings));
      binding.setClickListener(listener); // 直接和 布局里面的 click 绑定了 关联起来
      binding.executePendingBindings();
    }
  }

  // 新Item 与 旧Item 的比较...
  static class GardenPlantDiffCallback extends DiffUtil.ItemCallback<PlantAndGardenPlantings> {

    @Override
    public boolean areItemsTheSame(@NonNull PlantAndGardenPlantings oldItem,
        @NonNull PlantAndGardenPlantings newItem) {
      return oldItem.getPlant().getPlantId().equals(newItem.getPlant().getPlantId());
    }

    @SuppressLint("DiffUtilEquals")
    @Override
    public boolean areContentsTheSame(@NonNull PlantAndGardenPlantings oldItem,
        @NonNull PlantAndGardenPlantings newItem) {
      return oldItem.equals(newItem);
    }
  }
}