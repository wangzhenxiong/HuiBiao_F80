package com.dy.huibiao_f80.mvp.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.di.component.DaggerNewProjectJTJComponent;
import com.dy.huibiao_f80.greendao.DBHelper;
import com.dy.huibiao_f80.greendao.ProjectJTJ;
import com.dy.huibiao_f80.greendao.daos.ProjectJTJDao;
import com.dy.huibiao_f80.mvp.contract.NewProjectJTJContract;
import com.dy.huibiao_f80.mvp.presenter.NewProjectJTJPresenter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class NewProjectJTJFragment extends BaseFragment<NewProjectJTJPresenter> implements NewProjectJTJContract.View {

    Unbinder unbinder;
    @BindView(R.id.testprojectname)
    AutoCompleteTextView mTestprojectname;
    @BindView(R.id.cure_name)
    AutoCompleteTextView mCureName;
    @BindView(R.id.df_curve)
    CheckBox mDfCurve;
    @BindView(R.id.standardname)
    AutoCompleteTextView mStandardname;
    @BindView(R.id.chosemethod)
    Spinner mChosemethod;
    @BindView(R.id.c_)
    AutoCompleteTextView mC;
    @BindView(R.id.tA)
    AutoCompleteTextView mTA;
    @BindView(R.id.tB)
    AutoCompleteTextView mTB;
    @BindView(R.id.method_xiaoxian)
    LinearLayout mMethodXiaoxian;
    @BindView(R.id.tcA)
    AutoCompleteTextView mTcA;
    @BindView(R.id.tcB)
    AutoCompleteTextView mTcB;
    @BindView(R.id.method_bise)
    LinearLayout mMethodBise;
    @BindView(R.id.save_curve)
    Button mSaveCurve;
    @BindView(R.id.delete_curve)
    Button mDeleteCurve;
    private ProjectJTJ projectJTJ;

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
        mChosemethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    mMethodXiaoxian.setVisibility(View.VISIBLE);
                    mMethodBise.setVisibility(View.GONE);
                } else if (position == 1) {
                    mMethodXiaoxian.setVisibility(View.GONE);
                    mMethodBise.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void setData(@Nullable Object data) {
        if (null==data){
            projectJTJ=null;
            return;
        }
        projectJTJ = ((ProjectJTJ) data);
        initMessage();


    }

    private void initMessage() {
        mTestprojectname.setText(projectJTJ.getProjectName());
        mCureName.setText(projectJTJ.getCurveName());
        mDfCurve.setChecked(projectJTJ.getIsdefault());
        mStandardname.setText(projectJTJ.getStandardName());
        int testMethod = projectJTJ.getTestMethod();
        mChosemethod.setSelection(testMethod);
        mC.setText(projectJTJ.getC() + "");
        mTA.setText(projectJTJ.gettA() + "");
        mTB.setText(projectJTJ.gettB() + "");
        mTcA.setText(projectJTJ.getC_tA() + "");
        mTcB.setText(projectJTJ.getC_tB() + "");

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

    @OnClick({R.id.save_curve, R.id.delete_curve})
    public void onClick(View view) {
        if (null == projectJTJ) {
            ArmsUtils.snackbarText("请先选择检测项目");
            return;
        }
        switch (view.getId()) {
            case R.id.save_curve:
                String testproject = mTestprojectname.getText().toString();
                if (testproject.isEmpty()) {
                    ArmsUtils.snackbarText("请输入检测项目");
                    return;
                }
                projectJTJ.setProjectName(testproject);
                String curename = mCureName.getText().toString();
                if (curename.isEmpty()) {
                    ArmsUtils.snackbarText("请输入曲线名称");
                    return;
                }
                projectJTJ.setCurveName(curename);
                String standname = mStandardname.getText().toString();
                if (standname.isEmpty()) {
                    ArmsUtils.snackbarText("请输入检测标准");
                    return;
                }
                projectJTJ.setStandardName(standname);
                String c = mC.getText().toString();
                if (c.isEmpty()) {
                    ArmsUtils.snackbarText("请输入C线出线值");
                    return;
                }
                projectJTJ.setC(Double.parseDouble(c));
                int selectedItemPosition = mChosemethod.getSelectedItemPosition();
                projectJTJ.setTestMethod(selectedItemPosition);
                if (selectedItemPosition == 0) {
                    String ta = mTA.getText().toString();
                    if (ta.isEmpty()) {
                        ArmsUtils.snackbarText("请输入T线出线值A");
                        return;
                    }
                    projectJTJ.settA(Double.parseDouble(ta));
                    String tb = mTB.getText().toString();
                    if (tb.isEmpty()) {
                        ArmsUtils.snackbarText("请输入T线出线值B");
                        return;
                    }
                    projectJTJ.setTB(Double.parseDouble(tb));
                } else {
                    String tca = mTcA.getText().toString();
                    if (tca.isEmpty()) {
                        ArmsUtils.snackbarText("请输入T/C线出线值A");
                        return;
                    }
                    projectJTJ.setC_tA(Double.parseDouble(tca));
                    String tcb = mTcB.getText().toString();
                    if (tcb.isEmpty()) {
                        ArmsUtils.snackbarText("请输入T/C线出线值B");
                        return;
                    }
                    projectJTJ.setC_tB(Double.parseDouble(tcb));
                }

                if (mDfCurve.isChecked()) {
                    projectJTJ.setIsdefault(true);
                    List<ProjectJTJ> list = DBHelper.getProjectJTJDao().queryBuilder().where(ProjectJTJDao.Properties.Isdefault.eq(true)).list();
                    if (list.size() > 0) {
                        ProjectJTJ p = list.get(0);
                        p.setIsdefault(false);
                        DBHelper.getProjectJTJDao().update(p);
                    }
                }
                DBHelper.getProjectJTJDao().update(projectJTJ);
                ArmsUtils.snackbarText("保存成功");
                break;
            case R.id.delete_curve:
                  deleteCurve();
                mEvent.onEventJTJ(2,null);
                break;
        }
    }
    private void deleteCurve() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("确定要删除该曲线吗");

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String pjName = projectJTJ.getPjName();
                DBHelper.getProjectJTJDao().delete(projectJTJ);
                if (projectJTJ.isdefault()) {
                    List<ProjectJTJ> list = DBHelper.getProjectJTJDao().queryBuilder().where(ProjectJTJDao.Properties.ProjectName.eq(pjName))
                            .orderDesc(ProjectJTJDao.Properties.CurveOrder).build().list();
                    if (list.size() > 0) {
                        ProjectJTJ P = list.get(0);
                        P.setIsdefault(true);
                        DBHelper.getProjectJTJDao().update(P);
                    }
                }
                ArmsUtils.snackbarText("删除成功");
            }
        });
        alertDialog.show();

    }


    public void setOnEventListenner(OnEventJTJ onEvent) {
        mEvent=onEvent;
    }
    OnEventJTJ mEvent;
    public interface OnEventJTJ{
        void onEventJTJ(int what,Object o);
    }
}