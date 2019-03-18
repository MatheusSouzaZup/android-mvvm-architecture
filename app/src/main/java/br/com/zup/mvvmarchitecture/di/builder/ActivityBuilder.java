
package br.com.zup.mvvmarchitecture.di.builder;



import br.com.zup.mvvmarchitecture.MainActivity;
import br.com.zup.mvvmarchitecture.di.Activity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @Activity
    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();




}
