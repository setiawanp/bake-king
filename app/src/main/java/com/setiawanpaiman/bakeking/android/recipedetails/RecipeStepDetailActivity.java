package com.setiawanpaiman.bakeking.android.recipedetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.setiawanpaiman.bakeking.android.R;
import com.setiawanpaiman.bakeking.android.data.viewmodel.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepDetailActivity extends AppCompatActivity {

    private static final String EXTRA_STEPS = RecipeStepDetailActivity.class.getName() + ".EXTRA_STEPS";
    private static final String EXTRA_CURRENT_POS = RecipeStepDetailActivity.class.getName() + ".EXTRA_CURRENT_POS";

    @Nullable
    @BindView(R.id.detail_toolbar)
    Toolbar toolbar;

    @Nullable
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @Nullable
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    List<Step> mSteps;

    public static Intent newIntent(final Context context, final ArrayList<Step> steps, final int currentPos) {
        return new Intent(context, RecipeStepDetailActivity.class)
                .putExtra(EXTRA_STEPS, steps)
                .putExtra(EXTRA_CURRENT_POS, currentPos);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSteps = getIntent().getParcelableArrayListExtra(EXTRA_STEPS);
        setContentView(R.layout.activity_recipestep_detail);
        ButterKnife.bind(this);
        if (findViewById(R.id.fragment_container) == null) {
            setupPortraitViews();
        } else {
            setupLandscapeViews();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupPortraitViews() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new Adapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(getIntent().getIntExtra(EXTRA_CURRENT_POS, 0));
    }

    private void setupLandscapeViews() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(RecipeStepDetailFragment.class.getName());
        if (fragment == null) {
            final Step step = mSteps.get(getIntent().getIntExtra(EXTRA_CURRENT_POS, 0));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container,
                            RecipeStepDetailFragment.newInstance(step),
                            RecipeStepDetailFragment.class.getName())
                    .commit();
        }
    }

    private class Adapter extends FragmentStatePagerAdapter {

        Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return RecipeStepDetailFragment.newInstance(mSteps.get(position));
        }

        @Override
        public int getCount() {
            return mSteps.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(R.string.recipestepdetail_tab_title, mSteps.get(position).getId());
        }
    }
}
