package com.wuc.sunflower.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.wuc.sunflower.data.GardenPlantingRepository;
import com.wuc.sunflower.data.PlantRepository;

/**
 * @author : wuchao5
 * @date : 3/5/21 3:30 PM
 * @desciption :  (植物详情的ViewModel) 的 创建工厂
 */
public class PlantDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {
  private String plantId; // 植物ID
  private PlantRepository plantRepository; // 植物目录 仓库
  private GardenPlantingRepository gardenPlantingRepository; // 我的花园 仓库

  public PlantDetailViewModelFactory(PlantRepository plantRepository, GardenPlantingRepository gardenPlantingRepository, String plantId) {
    this.plantId = plantId;
    this.plantRepository = plantRepository;
    this.gardenPlantingRepository = gardenPlantingRepository;
  }

  @NonNull
  @Override
  @SuppressWarnings("unchecked")
  public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
    // 参数一：植物目录 仓库
    // 参数二：我的花园 仓库
    // 参数三：植物ID
    return (T) new PlantDetailViewModel(plantRepository, gardenPlantingRepository, plantId);
  }
}