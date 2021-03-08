package com.wuc.sunflower.frgaments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.snackbar.Snackbar;
import com.wuc.sunflower.R;
import com.wuc.sunflower.databinding.FragmentPlantDetailBinding;
import com.wuc.sunflower.utils.InjectorUtils;
import com.wuc.sunflower.viewmodels.PlantDetailViewModel;
import com.wuc.sunflower.viewmodels.PlantDetailViewModelFactory;

/**
 * @author : wuchao5
 * @date : 3/5/21 11:20 AM
 * @desciption : 植物介绍详情 的 Fragment
 */
public class PlantDetailFragment extends Fragment {
  private String shareText;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // DataBinding初始化布局绑定
    FragmentPlantDetailBinding binding = FragmentPlantDetailBinding.inflate(inflater, container, false);

    // 新的方式获取
    // 获取参数 植物ID
    //  Navigation Safe
    PlantDetailFragmentArgs args = PlantDetailFragmentArgs.fromBundle(requireArguments());
    String plantId = args.getPlantId();
    // 必须通过 植物ID 去查询 数据库
    // 初始化【(植物详情的ViewModel) 的 创建工厂】 并把上面的参数信息赋值给此工厂
    PlantDetailViewModelFactory factory = InjectorUtils.providerPlantDetailViewModelFactory(requireContext(), plantId);
    // 初始化（植物详情的ViewModel）
    PlantDetailViewModel viewModel = new ViewModelProvider(this, factory).get(PlantDetailViewModel.class);

    // 监听感应生效 LiveData       ViewModel里面的LiveData <------>  布局的使用 关联起来  感应
    binding.setLifecycleOwner(this);

    // DataBinding设置好ViewModel
    binding.setViewModel(viewModel);

    // 点击 + 号的时候 效果
    binding.fab.setOnClickListener(v -> {
      viewModel.addPlantToGarden(); // 植物数据 insert 我的花园

      // 这个效果非常好，同学们可以借鉴下
      // 以后可能会代替 Toast
      // 提示：添加了新植物
      Snackbar.make(v, R.string.added_plant_to_garden, Snackbar.LENGTH_LONG).show();
    });

    // 如果等于空的话，就显示：（在安卓 Sunflower APP 上看看这 %s）
    viewModel.plant.observe(getViewLifecycleOwner(), plant ->
        this.shareText = plant == null ? "" : getString(R.string.share_text_plant, plant.getName()));

    // 标题栏 右上角分享按钮
    setHasOptionsMenu(true);
    return binding.getRoot();
  }
}