package com.wuc.sunflower.data;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import com.wuc.sunflower.utils.Constants;
import com.wuc.sunflower.work.SeedDatabaseWorker;

// import androidx.databinding.adapters.Converters; 导错包了，有坑

/**
 * @author : wuchao5
 * @date : 3/2/21 4:05 PM
 * @desciption : 据中心， 注意：整个APP 数据源获取的 唯一入口，就是此类
 * 此应用程序的房间数据库
 */
@Database(entities = { GardenPlanting.class, Plant.class }, version = 1, exportSchema = false)

// 解决此错误：Cannot figure out how to save this field into database. You can consider adding a type
// 必须导入自己的 Converters
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {

  // 获取ROOM数据库的 DAO层，就可以对数据库 进行 增删改查了
  public abstract GardenPlantingDao getGardenPlantingDao();

  // 植物的 Dao
  public abstract PlantDao getPlantDao();

  private static volatile AppDatabase instance;

  public static AppDatabase getInstance(Context context) {
    if (instance == null) {
      synchronized (AppDatabase.class) {
        // instance = Room.databaseBuilder(context, AppDatabase.class, Constants.DATABASE_NAME).build();
        instance = buildDatabase(context); // 一次
      }
    }
    return instance;
  }

  private static AppDatabase buildDatabase(Context context) {
    return Room.databaseBuilder(context, AppDatabase.class, Constants.DATABASE_NAME)
        .addCallback(new Callback() {
          @Override public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // 此任务没有任何约束条件， 马上执行

            // 指定一个任务，让WorkManager，（assets/plants.json 所有的数据 插入到 ROOM plant数据表中去）
            WorkManager.getInstance(context).enqueue(OneTimeWorkRequest.from(SeedDatabaseWorker.class));
          }
        })
        .build();
  }
}
