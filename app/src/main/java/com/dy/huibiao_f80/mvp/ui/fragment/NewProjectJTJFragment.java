package com.dy.huibiao_f80.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.di.component.DaggerNewProjectJTJComponent;
import com.dy.huibiao_f80.mvp.contract.NewProjectJTJContract;
import com.dy.huibiao_f80.mvp.presenter.NewProjectJTJPresenter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class NewProjectJTJFragment extends BaseFragment<NewProjectJTJPresenter> implements NewProjectJTJContract.View {

    @BindView(R.id.testprojectname)
    AutoCompleteTextView mTestprojectname;
    @BindView(R.id.cure_name)
    AutoCompleteTextView mCureName;
    @BindView(R.id.chosemethod)
    Spinner mChosemethod;
    @BindView(R.id.c1)
    AutoCompleteTextView mC1;
    @BindView(R.id.t1A)
    AutoCompleteTextView mT1A;
    @BindView(R.id.c1_t1A)
    AutoCompleteTextView mC1T1A;
    @BindView(R.id.linearLayout32)
    LinearLayout mLinearLayout32;
    @BindView(R.id.save_curve)
    Button mSaveCurve;
    @BindView(R.id.delete_curve)
    Button mDeleteCurve;
    Unbinder unbinder;

    public static NewProjectJTJFragment newInstance() {
        NewProjectJTJFragment fragment = new NewProjectJTJFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerNewProjectJTJComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_newprojectjtj, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

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
}