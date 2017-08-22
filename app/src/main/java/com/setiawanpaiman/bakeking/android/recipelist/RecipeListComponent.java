package com.setiawanpaiman.bakeking.android.recipelist;

import com.setiawanpaiman.bakeking.android.di.component.ApplicationComponent;
import com.setiawanpaiman.bakeking.android.util.ActivityScoped;

import dagger.Component;

/**
 * Created by Setiawan Paiman on 22/8/17.
 */

@ActivityScoped
@Component(modules = RecipeListModule.class, dependencies = ApplicationComponent.class)
public interface RecipeListComponent {

    void inject(RecipeListActivity activity);
}
