package com.wuc.sunflower.frgaments;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ListAdapter;
import com.wuc.sunflower.R;
import com.wuc.sunflower.adapters.PlantAdapter;
import com.wuc.sunflower.data.Plant;
import com.wuc.sunflower.databinding.FragmentPlantListBinding;
import com.wuc.sunflower.utils.InjectorUtils;
import com.wuc.sunflower.viewmodels.PlantListViewModel;
import com.wuc.sunflower.viewmodels.PlantListViewModelFactory;
import java.util.List;

/**
 * @author : wuchao5
 * @date : 3/2/21 8:14 PM
 * @desciption : 植物目录 的 Fragment【Fragment2 植物目录】植物目录的Fragment
 */
public class PlantListFragment extends Fragment {

  // 植物目录 列表 的 ViewModel
  private PlantListViewModel viewModel;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // 所有的布局文件都交给了 DataBinding  来初始化布局文件
    // DataBinding绑定布局的操作
    FragmentPlantListBinding binding = FragmentPlantListBinding.inflate(inflater, container, false);

    // 适配器初始化
    PlantAdapter adapter = new PlantAdapter();

    // 列表控件 和  适配器 关联
    binding.plantList.setAdapter(adapter);

    // 暴漏 植物 列表 ViewModel 工厂 (数据初始化的起点)      // 开启WM任务 insert room数据库
    PlantListViewModelFactory factory = InjectorUtils.providePlantListViewModelFactory(requireContext());

    viewModel = new ViewModelProvider(this, factory).get(PlantListViewModel.class);

    // 到这里为止： // ViewModel 有值

    subscribeUi(adapter);

    // 菜单
    // 植物目录：点击右上角 会重新随机查询展示
    setHasOptionsMenu(true);
    return binding.getRoot();
  }

  /**
   * 画面真正的展示渲染 细节操作
   *
   * @param adapter 上面丢下来的 适配器
   */
  private void subscribeUi(PlantAdapter adapter) {

    // 使用LiveData的粘性 执行 把数据全部刷新到 RecyclerView 中去显示出来
    // 眼睛
    this.viewModel.plants.observe(getViewLifecycleOwner(), new Observer<List<Plant>>() {
      @Override
      public void onChanged(List<Plant> plants) {
        adapter.submitList(plants); // RecyclerView 中去显示出来
      }
    });
  }

  // 植物目录：点击右上角 会重新随机查询展示
  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu_plant_list, menu); // 加载菜单布局
  }

  // 点击事件
  // 植物目录：点击右上角 会重新随机查询展示[当用户点击的时候，就会触发监听]
  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
      case R.id.filter_zone:
        updateData();
        return true;

    }
    return super.onOptionsItemSelected(item);
  }

  private void updateData() {
    if (viewModel.isFiltered()) { // 是否过滤 标记
      viewModel.cleanGrowZoneNumber(); // 清除此标记 改为默认状态 -1  （正常查询）
    } else {
      viewModel.setGrowZoneNumber(9); // 设置此标记 为9  （查询为 9的数据）
    }
  }
}