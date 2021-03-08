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
import com.wuc.sunflower.data.Plant;
import com.wuc.sunflower.databinding.FragmentPlantListBinding;
import com.wuc.sunflower.databinding.ListItemPlantBinding;
import com.wuc.sunflower.frgaments.PlantListFragmentDirections;

/**
 * @author : wuchao5
 * @date : 3/3/21 2:14 PM
 * @desciption :  植物目录 的 适配器
 */
public class PlantAdapter extends ListAdapter<Plant, PlantAdapter.ViewHolder> {

  public PlantAdapter() {
    super(new PlantDiffCallback());
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
        R.layout.list_item_plant, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Plant plant = getItem(position);
    holder.itemView.setTag(plant);
    holder.bind(createOnClickListener(plant.getPlantId()), plant);
  }

  private View.OnClickListener createOnClickListener(String plantId) {
    return new View.OnClickListener() {
      @Override public void onClick(View v) {
        // Log.e("item", "onClick: 你点击了植物列表的Item，plantId:" + plantId);
        //跳转到详情 并且把植物 id 给详情
        // 导航过去了 到 详情
        Navigation.findNavController(v).navigate(
            PlantListFragmentDirections.actionPlantListFragmentToPlantDetailFragment(plantId)
        );
      }
    };
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    private ListItemPlantBinding binding;

    public ViewHolder(@NonNull ListItemPlantBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    void bind(View.OnClickListener listener, Plant plant) {
      binding.setPlant(plant);
      binding.setClickListener(listener);// 直接和 布局里面的 click 绑定了 关联起来
      binding.executePendingBindings();
    }
  }

  static class PlantDiffCallback extends DiffUtil.ItemCallback<Plant> {

    @Override
    public boolean areItemsTheSame(@NonNull Plant oldItem, @NonNull Plant newItem) {
      return oldItem.getPlantId().equals(newItem.getPlantId());
    }

    @SuppressLint("DiffUtilEquals")
    @Override
    public boolean areContentsTheSame(@NonNull Plant oldItem, @NonNull Plant newItem) {
      return oldItem == newItem;
    }
  }
}