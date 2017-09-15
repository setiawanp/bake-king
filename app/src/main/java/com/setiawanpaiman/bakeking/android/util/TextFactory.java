package com.setiawanpaiman.bakeking.android.util;

import android.content.Context;

import com.setiawanpaiman.bakeking.android.R;
import com.setiawanpaiman.bakeking.android.data.viewmodel.Ingredient;
import com.setiawanpaiman.bakeking.android.data.viewmodel.Step;

import java.util.List;

/**
 * Created by Setiawan Paiman on 16/9/17.
 */

public final class TextFactory {

    public static String constructIngredientsText(final Context context,
                                                  final List<Ingredient> ingredients) {
        StringBuilder sb = new StringBuilder(context.getString(R.string.recipestep_ingredient_list));
        for (final Ingredient ingredient : ingredients) {
            sb.append(context.getString(R.string.recipestep_ingredient_item,
                    ingredient.getIngredient(), ingredient.getQuantity(), ingredient.getMeasure()));
        }
        return sb.toString();
    }

    public static String constructStepText(final Context context, final Step step) {
        return context.getString(R.string.recipestep_step_item, step.getId(), step.getShortDescription());
    }
}
