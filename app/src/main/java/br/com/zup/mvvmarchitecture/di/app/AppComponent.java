package br.com.zup.mvvmarchitecture.di.app;

import android.app.Application;

import javax.inject.Singleton;


import br.com.zup.mvvmarchitecture.MyApplication;
import br.com.zup.mvvmarchitecture.di.builder.ActivityBuilder;
import br.com.zup.mvvmarchitecture.di.builder.FragmentBuilder;
import br.com.zup.mvvmarchitecture.di.builder.ViewModelBuilder;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AppModule.class,
        AndroidSupportInjectionModule.class,
        ActivityBuilder.class,
        FragmentBuilder.class,
        ViewModelBuilder.class})
public interface AppComponent {

    void inject(MyApplication application);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();

    }

}
