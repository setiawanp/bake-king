package com.setiawanpaiman.bakeking.android.recipelist;

import android.support.annotation.NonNull;

import com.setiawanpaiman.bakeking.android.BasePresenter;
import com.setiawanpaiman.bakeking.android.data.source.RecipeRepository;
import com.setiawanpaiman.bakeking.android.util.scheduler.BaseSchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Setiawan Paiman on 22/8/17.
 */

public class RecipeListPresenter implements BasePresenter {

    private final RecipeListContract.View mView;
    private final RecipeRepository mRecipeRepository;
    private final BaseSchedulerProvider mSchedulerProvider;

    @NonNull
    private CompositeDisposable mComposite;

    @Inject
    RecipeListPresenter(RecipeListContract.View view,
                               RecipeRepository recipeRepository,
                               BaseSchedulerProvider schedulerProvider) {
        this.mView = view;
        this.mRecipeRepository = recipeRepository;
        this.mSchedulerProvider = schedulerProvider;
        this.mComposite = new CompositeDisposable();
    }

    @Override
    public void subscribe() {
        mRecipeRepository.getRecipes()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(mView::showRecipes,
                        throwable -> mView.showError(),
                        () -> {});
    }

    @Override
    public void unsubscribe() {
        mComposite.clear();
    }
}
