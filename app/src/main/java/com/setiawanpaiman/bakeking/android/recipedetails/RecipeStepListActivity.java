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

import java.util.List;

/**
 * An activity representing a list of Recipe Steps. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeStepDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeStepListActivity extends AppCompatActivity {

    private static final String EXTRA_RECIPE = RecipeStepListActivity.class.getName() + ".EXTRA_RECIPE";

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    public static Intent newIntent(final Context context, final Recipe recipe) {
        return new Intent(context, RecipeStepListActivity.class)
                .putExtra(EXTRA_RECIPE, recipe);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipestep_list);
        setupViews(getIntent().getParcelableExtra(EXTRA_RECIPE));

        if (findViewById(R.id.recipestep_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void setupViews(final Recipe recipe) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(recipe.getName());
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recipestep_list);
        assert recyclerView != null;
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

                // TODO: implement click
//                if (mTwoPane) {
//                    Bundle arguments = new Bundle();
//                    arguments.putString(RecipeStepDetailFragment.ARG_ITEM_ID, holder.mItem.id);
//                    RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
//                    fragment.setArguments(arguments);
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.recipestep_detail_container, fragment)
//                            .commit();
//                } else {
//                    Context context = v.getContext();
//                    Intent intent = new Intent(context, RecipeStepDetailActivity.class);
//                    intent.putExtra(RecipeStepDetailFragment.ARG_ITEM_ID, holder.mItem.id);
//
//                    context.startActivity(intent);
//                }
            });
            return vh;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            if (isIngredientPosition(position)) {
                holder.mContentView.setText(constructIngredientsText(mIngredients));
            } else {
                holder.mContentView.setText(constructStepText(position, mSteps.get(position - 1)));
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

        private String constructIngredientsText(final List<Ingredient> ingredients) {
            final Context context = getApplicationContext();
            StringBuilder sb = new StringBuilder(context.getString(R.string.recipestep_ingredient_list));
            for (final Ingredient ingredient : ingredients) {
                sb.append(context.getString(R.string.recipestep_ingredient_item,
                        ingredient.getIngredient(), ingredient.getQuantity(), ingredient.getMeasure()));
            }
            return sb.toString();
        }

        private String constructStepText(final int stepNumber, final Step step) {
            return getApplicationContext().getString(R.string.recipestep_step_item,
                    stepNumber, step.getShortDescription());
        }

        private boolean isIngredientPosition(final int position) {
            return position == 0;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
}
