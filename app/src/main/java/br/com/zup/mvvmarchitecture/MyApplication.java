package br.com.zup.mvvmarchitecture;

import android.app.Activity;
import android.app.Application;
import android.arch.persistence.room.Room;

import javax.inject.Inject;


import br.com.zup.mvvmarchitecture.di.app.DaggerAppComponent;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class MyApplication extends Application implements HasActivityInjector {

    private static MyApplication mInstance;

//    private AppDatabase mAppDb;

    public synchronized static MyApplication getInstance() {
        return mInstance;
    }

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
//
        initInjector();
//        initRoom();
    }

//    private void initRoom() {
//        mAppDb = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "movie-db")
//                .fallbackToDestructiveMigration()
//                .build();
//    }

    public void initInjector() {
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);
    }

//    public AppDatabase getAppDb() {
//        return mAppDb;
//    }
}
