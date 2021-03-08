package com.wuc.sunflower.data;

import androidx.lifecycle.LiveData;
import java.util.List;

/**
 * @author : wuchao5
 * @date : 3/3/21 11:32 AM
 * @desciption : 植物 的仓库层
 */
public class PlantRepository {
  // 单例模式用的
  private static PlantRepository instance;

  // 构造函数 必须接收一个dao， 仓库才能对 dao进行增删改查
  private PlantRepository(PlantDao gardenPlantingDao) {
    this.plantDao = gardenPlantingDao;
  }

  // 单例模式
  public static PlantRepository getInstance(PlantDao gardenPlantingDao) {
    if (instance == null) {
      synchronized (PlantRepository.class) {
        if (instance == null) {
          instance = new PlantRepository(gardenPlantingDao);
        }
      }
    }
    return instance;
  }

  // 仓库才能对 dao进行增删改查
  private PlantDao plantDao;

  // 查询 plantDao数据库 的所有 植物目录数据
  public LiveData<List<Plant>> getPlants() {
    return this.plantDao.getPlants();
  }

  // 根据条件plantId查询 plantDao数据库 的所有 植物目录数据 （单个）
  public LiveData<Plant> getPlant(String plantId) {
    return this.plantDao.getPlant(plantId);
  }

  // 辅助 随机
  // 根据条件plantId查询 plantDao数据库 的所有 植物目录数据 （单个）
  public LiveData<List<Plant>> getPlantsWithGrowZoneNumber(int growZoneNumber) {
    return this.plantDao.getPlantsWithGrowZoneNumber(growZoneNumber);
  }

  // 数据库的数据 是怎么进去的（WorkManager 一个任务 json数据insert----> 植物数据库里面）
}