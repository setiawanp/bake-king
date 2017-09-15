package com.setiawanpaiman.bakeking.android.recipedetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.setiawanpaiman.bakeking.android.R;
import com.setiawanpaiman.bakeking.android.data.viewmodel.Ingredient;
import com.setiawanpaiman.bakeking.android.data.viewmodel.Recipe;
import com.setiawanpaiman.bakeking.android.data.viewmodel.Step;
import com.setiawanpaiman.bakeking.android.util.TextFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepListActivity extends AppCompatActivity {

    private static final String EXTRA_RECIPE = RecipeStepListActivity.class.getName() + ".EXTRA_RECIPE";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recipestep_list)
    RecyclerView recyclerView;

    private boolean mTwoPane;

    public static Intent newIntent(final Context context, final Recipe recipe) {
        return new Intent(context, RecipeStepListActivity.class)
                .putExtra(EXTRA_RECIPE, recipe);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipestep_list);
        ButterKnife.bind(this);
        setupViews(getIntent().getParcelableExtra(EXTRA_RECIPE));

        if (findViewById(R.id.recipestep_detail_container) != null) {
            mTwoPane = true;
        }
    }

    private void setupViews(final Recipe recipe) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(recipe.getName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        recyclerView.setAdapter(new Adapter(recipe.getIngredients(), recipe.getSteps()));
    }

    class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        private static final int VIEW_TYPE_INGREDIENTS = 0;
        private static final int VIEW_TYPE_STEPS = 1;

        private final List<Ingredient> mIngredients;
        private final List<Step> mSteps;

        Adapter(final List<Ingredient> ingredients, final List<Step> steps) {
            mIngredients = ingredients;
            mSteps = steps;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(viewType == VIEW_TYPE_INGREDIENTS ?
                            R.layout.recipestep_list_ingredients :
                            R.layout.recipestep_list_step, parent, false);
            ViewHolder vh = new ViewHolder(view);
            vh.itemView.setOnClickListener((v) -> {
                final int pos = vh.getAdapterPosition();
                if (pos == 0 || pos == RecyclerView.NO_POSITION) {
                    return;
                }

                final Step step = mSteps.get(pos - 1);
                if (mTwoPane) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.recipestep_detail_container,
                                    RecipeStepDetailFragment.newInstance(step))
                            .commit();
                } else {
                    Context context = v.getContext();
                    context.startActivity(RecipeStepDetailActivity
                            .newIntent(context, new ArrayList<>(mSteps), pos - 1));
                }
            });
            return vh;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            if (isIngredientPosition(position)) {
                holder.mContentView.setText(TextFactory.constructIngredientsText(
                        RecipeStepListActivity.this, mIngredients));
            } else {
                holder.mContentView.setText(TextFactory.constructStepText(
                        RecipeStepListActivity.this, mSteps.get(position - 1)));
            }
        }

        @Override
        public int getItemCount() {
            return 1 + mSteps.size();
        }

        @Override
        public int getItemViewType(int position) {
            return isIngredientPosition(position) ? VIEW_TYPE_INGREDIENTS : VIEW_TYPE_STEPS;
        }

        private boolean isIngredientPosition(final int position) {
            return position == 0;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.content)
            TextView mContentView;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }
}
