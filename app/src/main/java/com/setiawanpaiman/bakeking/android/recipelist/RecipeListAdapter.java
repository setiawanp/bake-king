package com.setiawanpaiman.bakeking.android.recipelist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.setiawanpaiman.bakeking.android.R;
import com.setiawanpaiman.bakeking.android.data.viewmodel.Recipe;
import com.setiawanpaiman.bakeking.android.recipedetails.RecipeStepListActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Setiawan Paiman on 22/8/17.
 */

class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {

    private final Context mContext;
    private final List<Recipe> mData = new ArrayList<>();

    RecipeListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder vh = new ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_recipe, parent, false));
        vh.itemView.setOnClickListener(v -> {
            final int pos = vh.getAdapterPosition();
            if (pos == RecyclerView.NO_POSITION) {
                return;
            }

            mContext.startActivity(RecipeStepListActivity.newIntent(mContext, mData.get(pos)));
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe recipe = mData.get(holder.getAdapterPosition());
        holder.textName.setText(recipe.getName());
        if (!TextUtils.isEmpty(recipe.getImage())) {
            Picasso.with(mContext)
                    .load(recipe.getImage())
                    .into(holder.thumbnailView);
            holder.thumbnailView.setVisibility(View.VISIBLE);
        } else {
            holder.thumbnailView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    void addData(final List<Recipe> recipes) {
        int oldCount = mData.size();
        mData.addAll(recipes);
        notifyItemRangeInserted(oldCount, oldCount + recipes.size());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView textName;

        @BindView(R.id.thumbnail)
        ImageView thumbnailView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
