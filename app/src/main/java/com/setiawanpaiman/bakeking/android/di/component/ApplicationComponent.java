package com.setiawanpaiman.bakeking.android.di.component;

import com.setiawanpaiman.bakeking.android.data.source.RecipeRepository;
import com.setiawanpaiman.bakeking.android.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Setiawan Paiman on 21/8/17.
 */

@Singleton
@Component(modules = {ApplicationModule.class })
public interface ApplicationComponent {

    RecipeRepository getRecipeRepository();
}
