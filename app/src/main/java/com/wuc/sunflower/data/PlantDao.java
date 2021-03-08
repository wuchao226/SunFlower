package com.wuc.sunflower.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

/**
 * @author : wuchao5
 * @date : 3/3/21 11:33 AM
 * @desciption :  植物 的增删改查-DAO
 */
@Dao
public interface PlantDao {
    @Query("SELECT * FROM plants ORDER BY name")
    LiveData<List<Plant>> getPlants();

    @Query("SELECT * FROM plants WHERE growZoneNumber = :growZoneNumber ORDER BY name")
    LiveData<List<Plant>> getPlantsWithGrowZoneNumber(int growZoneNumber);

    @Query("SELECT * FROM plants WHERE id = :plantId")
    LiveData<Plant> getPlant(String plantId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Plant> plants);
}
