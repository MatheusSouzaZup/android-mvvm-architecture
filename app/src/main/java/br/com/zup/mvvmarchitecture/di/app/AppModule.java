package br.com.zup.mvvmarchitecture.di.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;


import br.com.zup.mvvmarchitecture.service.APIClient;
import dagger.Module;
import dagger.Provides;


@Module
public class AppModule {

    @Provides
    @Singleton
    APIClient provideApiClient() {
        return new APIClient("xD");
    }


    @Provides
    @Singleton
    Context provideContext(Application app) {
        return app.getApplicationContext();
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

//    @Provides
//    @Singleton
//    AppDatabase providesRoomDb(Application app) {
//        return ((MyApplication) app).getAppDb();
//    }

}
