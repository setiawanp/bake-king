package com.setiawanpaiman.bakeking.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.setiawanpaiman.bakeking.android.data.viewmodel.Recipe;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((BakeKingApplication) getApplication()).getApplicationComponent()
                .getRecipeRepository()
                .getRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Recipe>>() {
                    @Override
                    public void accept(@NonNull List<Recipe> recipes) throws Exception {
                        Log.i("BakingApp", "OnNext");
                    }
                });
    }
}
