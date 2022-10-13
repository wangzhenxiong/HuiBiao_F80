package com.dy.huibiao_f80.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.di.component.DaggerNewProjectFGGDComponent;
import com.dy.huibiao_f80.mvp.contract.NewProjectFGGDContract;
import com.dy.huibiao_f80.mvp.presenter.NewProjectFGGDPresenter;
import com.dy.huibiao_f80.mvp.ui.adapter.GuidePageAdapter_h;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class NewProjectFGGDFragment extends BaseFragment<NewProjectFGGDPresenter> implements NewProjectFGGDContract.View {


    Unbinder unbinder;
    @BindView(R.id.testprojectname)
    AutoCompleteTextView mTestprojectname;
    @BindView(R.id.cure_name)
    AutoCompleteTextView mCureName;
    @BindView(R.id.chosemethod)
    Spinner mChosemethod;
    @BindView(R.id.chosewavalength)
    Spinner mChosewavalength;
    @BindView(R.id.vp)
    ViewPager mVp;
    @BindView(R.id.save_curve)
    Button mSaveCurve;
    @BindView(R.id.delete_curve)
    Button mDeleteCurve;
    private View methoed_a_page;
    private View methoed_b_page;
    private View methoed_c_page;
    private View methoed_d_page;
    private List<View> mViewList = new ArrayList<>();
    private ViewHoldera mViewHoldera;
    private ViewHolderb mViewHolderb;
    private ViewHolderc mViewHolderc;
    private ViewHolderd mViewHolderd;
    private GuidePageAdapter_h mAdapter;
    private int method_checked_flag = 0;
    private int wavalength_checked_flag = 0;

    public static NewProjectFGGDFragment newInstance() {
        NewProjectFGGDFragment fragment = new NewProjectFGGDFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerNewProjectFGGDComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_newprojectfggd, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initpages();
        mAdapter = new GuidePageAdapter_h(mViewList);
        mVp.setAdapter(mAdapter);
        mChosemethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                method_checked_flag = position;
                LogUtils.d(method_checked_flag);
                mVp.setCurrentItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mChosewavalength.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                wavalength_checked_flag = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //禁止viewpage左右滑动
        mVp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                LogUtils.d("vp_touch");
                return true;
            }
        });
    }

    private void initpages() {
        LayoutInflater inflater = getLayoutInflater();
        methoed_a_page = inflater.inflate(R.layout.method_a, null);
        mViewHoldera = new ViewHoldera(methoed_a_page);

        methoed_b_page = inflater.inflate(R.layout.method_b, null);
        mViewHolderb = new ViewHolderb(methoed_b_page);

        methoed_c_page = inflater.inflate(R.layout.method_c, null);
        mViewHolderc = new ViewHolderc(methoed_c_page);

        methoed_d_page = inflater.inflate(R.layout.method_d, null);
        mViewHolderd = new ViewHolderd(methoed_d_page);
        mViewList.add(methoed_a_page);
        mViewList.add(methoed_b_page);
        mViewList.add(methoed_c_page);
        mViewList.add(methoed_d_page);

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    static class ViewHoldera {
        @BindView(R.id.yrsj)
        AutoCompleteTextView mYrsj;
        @BindView(R.id.jcsj)
        AutoCompleteTextView mJcsj;
        @BindView(R.id.unit_input)
        AutoCompleteTextView mUnitInput;
        @BindView(R.id.yin_xfwa)
        AutoCompleteTextView mYinXfwa;
        @BindView(R.id.yin_xfwb)
        AutoCompleteTextView mYinXfwb;
        @BindView(R.id.yang_xfwa)
        AutoCompleteTextView mYangXfwa;
        @BindView(R.id.yang_xfwb)
        AutoCompleteTextView mYangXfwb;
        @BindView(R.id.kya)
        AutoCompleteTextView mKya;
        @BindView(R.id.kyb)
        AutoCompleteTextView mKyb;

        ViewHoldera(View view) {
            ButterKnife.bind(this, view);
        }
    }


    static class ViewHolderc {
        @BindView(R.id.yrsj)
        AutoCompleteTextView mYrsj;
        @BindView(R.id.jcsj)
        AutoCompleteTextView mJcsj;
        @BindView(R.id.unit_input)
        AutoCompleteTextView mUnitInput;
        @BindView(R.id.jzqxa)
        AutoCompleteTextView mJzqxa;
        @BindView(R.id.jzqxb)
        AutoCompleteTextView mJzqxb;
        @BindView(R.id.yinxfwa)
        AutoCompleteTextView mYinxfwa;
        @BindView(R.id.yinxfwb)
        AutoCompleteTextView mYinxfwb;
        @BindView(R.id.yangxfwa)
        AutoCompleteTextView mYangxfwa;
        @BindView(R.id.yangxfwb)
        AutoCompleteTextView mYangxfwb;
        @BindView(R.id.kyfwa)
        AutoCompleteTextView mKyfwa;
        @BindView(R.id.kyfwb)
        AutoCompleteTextView mKyfwb;

        ViewHolderc(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderb {
        @BindView(R.id.yrsj)
        AutoCompleteTextView mYrsj;
        @BindView(R.id.jcsj)
        AutoCompleteTextView mJcsj;
        @BindView(R.id.unit_input)
        AutoCompleteTextView mUnitInput;
        @BindView(R.id.ax0)
        AutoCompleteTextView mAx0;
        @BindView(R.id.ax1)
        AutoCompleteTextView mAx1;
        @BindView(R.id.ax2)
        AutoCompleteTextView mAx2;
        @BindView(R.id.ax3)
        AutoCompleteTextView mAx3;
        @BindView(R.id.afrom)
        AutoCompleteTextView mAfrom;
        @BindView(R.id.ato)
        AutoCompleteTextView mAto;
        @BindView(R.id.bx0)
        AutoCompleteTextView mBx0;
        @BindView(R.id.bx1)
        AutoCompleteTextView mBx1;
        @BindView(R.id.bx2)
        AutoCompleteTextView mBx2;
        @BindView(R.id.bx3)
        AutoCompleteTextView mBx3;
        @BindView(R.id.bfrom)
        AutoCompleteTextView mBfrom;
        @BindView(R.id.bto)
        AutoCompleteTextView mBto;
        @BindView(R.id.jzqxa)
        AutoCompleteTextView mJzqxa;
        @BindView(R.id.jzqxb)
        AutoCompleteTextView mJzqxb;
        @BindView(R.id.yin_xfwa)
        AutoCompleteTextView mYinXfwa;
        @BindView(R.id.yin_xfwb)
        AutoCompleteTextView mYinXfwb;
        @BindView(R.id.yang_xfwa)
        AutoCompleteTextView mYangXfwa;
        @BindView(R.id.yang_xfwb)
        AutoCompleteTextView mYangXfwb;
        @BindView(R.id.kya)
        AutoCompleteTextView mKya;
        @BindView(R.id.kyb)
        AutoCompleteTextView mKyb;

        ViewHolderb(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderd {
        @BindView(R.id.yrsj)
        AutoCompleteTextView mYrsj;
        @BindView(R.id.jcsj)
        AutoCompleteTextView mJcsj;
        @BindView(R.id.unit_input)
        AutoCompleteTextView mUnitInput;
        @BindView(R.id.jzqxa)
        AutoCompleteTextView mJzqxa;
        @BindView(R.id.jzqxb)
        AutoCompleteTextView mJzqxb;
        @BindView(R.id.jzqxc)
        AutoCompleteTextView mJzqxc;
        @BindView(R.id.jzqxd)
        AutoCompleteTextView mJzqxd;
        @BindView(R.id.yin_xfwa)
        AutoCompleteTextView mYinXfwa;
        @BindView(R.id.yin_xfwb)
        AutoCompleteTextView mYinXfwb;
        @BindView(R.id.yang_xfwa)
        AutoCompleteTextView mYangXfwa;
        @BindView(R.id.yang_xfwb)
        AutoCompleteTextView mYangXfwb;
        @BindView(R.id.kya)
        AutoCompleteTextView mKya;
        @BindView(R.id.kyb)
        AutoCompleteTextView mKyb;

        ViewHolderd(View view) {
            ButterKnife.bind(this, view);
        }
    }
}