package com.vg.momento.controller.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import com.vg.momento.R;
import com.vg.momento.controller.fragments.MomentFragment;
import com.vg.momento.dao.implementations.EmbeddedMomentDao;
import com.vg.momento.model.Moment;
import java.util.List;
import java.util.UUID;

public class MomentPagerActivity extends FragmentActivity{

    private ViewPager mViewPager;

    private List<Moment> mMoments;

    public static final String EXTRA_MOMENT_ID = "com.vg.android.momento.moment_id";

    public static Intent newIntent(Context packageContext, UUID momentId) {
        Intent intent = new Intent(packageContext, MomentPagerActivity.class);
        intent.putExtra(EXTRA_MOMENT_ID, momentId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moment_pager);

        mViewPager = (ViewPager) findViewById(R.id.activity_moment_pager_view_pager);
        mMoments = EmbeddedMomentDao.getInstance().getAllMoments();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Moment moment = mMoments.get(position);
                return MomentFragment.newInstance(moment.getId());
            }

            @Override
            public int getCount() {
                return mMoments.size();
            }
        });

        UUID id = (UUID) getIntent().getSerializableExtra(EXTRA_MOMENT_ID);
        for(int i = 0; i < mMoments.size(); i++){
            if(mMoments.get(i).getId().equals(id)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}