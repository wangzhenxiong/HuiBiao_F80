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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.di.component.DaggerNewProjectJTJComponent;
import com.dy.huibiao_f80.greendao.DBHelper;
import com.dy.huibiao_f80.greendao.ProjectJTJ;
import com.dy.huibiao_f80.greendao.daos.ProjectJTJDao;
import com.dy.huibiao_f80.mvp.contract.NewProjectJTJContract;
import com.dy.huibiao_f80.mvp.presenter.NewProjectJTJPresenter;
import com.dy.huibiao_f80.mvp.ui.adapter.MySpinnerAdapterJTJ;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.greenrobot.eventbus.EventBus;

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
    @BindView(R.id.hint_xiaoxian)
    TextView mHintXiaoxian;
    @BindView(R.id.hint_bise)
    TextView mHintBise;
    @BindView(R.id.new_curve)
    ImageButton mNewCurve;
    @BindView(R.id.sp_curegroup)
    Spinner mSpCuregroup;

    private ProjectJTJ projectJTJ;

    public static NewProjectJTJFragment newInstance() {
        NewProjectJTJFragment fragment = new NewProjectJTJFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerNewProjectJTJComponent //??????????????????,?????????????????????
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
                    mMethodBise.setVisibility(View.GONE);
                    mMethodXiaoxian.setVisibility(View.VISIBLE);
                    mHintBise.setVisibility(View.GONE);
                    ;
                    mHintXiaoxian.setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    mMethodBise.setVisibility(View.VISIBLE);
                    mMethodXiaoxian.setVisibility(View.GONE);
                    mHintBise.setVisibility(View.VISIBLE);
                    mHintXiaoxian.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void setData(@Nullable Object data) {
        LogUtils.d(data);
        ProjectJTJ project;
        String name;
        if (null == data) {
            LogUtils.d("null");
            project = null;
            initMessage(new ProjectJTJ());
            name = "";
        } else {
            project = ((ProjectJTJ) data);
            name = project.getCurveName();
        }
        initCurve(name,project);

    }

    private void initCurve(String curvename, ProjectJTJ data) {
        if (null == mSpCuregroup) {
            return;
        }
        if (null == data) {
            mSpCuregroup.setVisibility(View.GONE);
            mNewCurve.setVisibility(View.GONE);
            return;
        } else {
            mSpCuregroup.setVisibility(View.VISIBLE);
            mNewCurve.setVisibility(View.VISIBLE);
        }
        List<ProjectJTJ> list = DBHelper.getProjectJTJDao().queryBuilder()
                .where(ProjectJTJDao.Properties.ProjectName.eq(data.getPjName()))
                .build().list();

        LogUtils.d(list);
        LogUtils.d(curvename);
        MySpinnerAdapterJTJ adapter = new MySpinnerAdapterJTJ(list, getActivity());


        mSpCuregroup.setAdapter(adapter);
        mSpCuregroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ProjectJTJ object = list.get(position);
                LogUtils.d(object);
                projectJTJ = object;
                initMessage(projectJTJ);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (!curvename.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getCVName().equals(curvename)) {
                    mSpCuregroup.setSelection(i);
                    LogUtils.d(i);
                }
            }
        }


    }

    private void initMessage(ProjectJTJ projectJTJ) {
        if (null == mTestprojectname) {
            return;
        }
        LogUtils.d(mTestprojectname);
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
        LogUtils.d("onDestroyView");
        super.onDestroyView();
        unbinder.unbind();
    }
    private void newCurve() {
        if (null == projectJTJ) {
            ArmsUtils.snackbarText("????????????????????????");
            return;
        }
        String pjName = projectJTJ.getPjName();
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("??????????????????????????????");
        EditText view = new EditText(getActivity());
        long count;
        count = DBHelper.getProjectJTJDao().queryBuilder().where(ProjectJTJDao.Properties.ProjectName.eq(pjName)).count();
        view.setText("??????" + (count + 1));


        alertDialog.setView(view);
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String curvename = view.getText().toString();
                if (curvename.isEmpty()) {
                    ArmsUtils.snackbarText("??????????????????????????????");
                    return;
                }

                //ProjectJTJ projectJTJ = new ProjectJTJ();
                projectJTJ.setId(null);
                projectJTJ.setProjectName(pjName);
                projectJTJ.setCurveName(curvename);
                projectJTJ.setCreator(1);
                projectJTJ.setCurveOrder((int) count);
                DBHelper.getProjectJTJDao().insert(projectJTJ);
                initCurve(curvename, projectJTJ);


            }
        });
        alertDialog.show();


    }
    @OnClick({R.id.save_curve, R.id.delete_curve,R.id.new_curve})
    public void onClick(View view) {
        if (null == projectJTJ) {
            ArmsUtils.snackbarText("????????????????????????");
            return;
        }
        switch (view.getId()) {
            case R.id.new_curve:
                newCurve();
                break;
            case R.id.save_curve:
                String testproject = mTestprojectname.getText().toString();
                if (testproject.isEmpty()) {
                    ArmsUtils.snackbarText("?????????????????????");
                    return;
                }
                projectJTJ.setProjectName(testproject);
                String curename = mCureName.getText().toString();
                if (curename.isEmpty()) {
                    ArmsUtils.snackbarText("?????????????????????");
                    return;
                }
                projectJTJ.setCurveName(curename);
                String standname = mStandardname.getText().toString();
                if (standname.isEmpty()) {
                    ArmsUtils.snackbarText("?????????????????????");
                    return;
                }
                projectJTJ.setStandardName(standname);
                String c = mC.getText().toString();
                if (c.isEmpty()) {
                    ArmsUtils.snackbarText("?????????C????????????");
                    return;
                }
                projectJTJ.setC(Double.parseDouble(c));
                int selectedItemPosition = mChosemethod.getSelectedItemPosition();
                projectJTJ.setTestMethod(selectedItemPosition);
                String ta = mTA.getText().toString();
                if (ta.isEmpty()) {
                    ArmsUtils.snackbarText("?????????T????????????A");
                    return;
                }
                projectJTJ.settA(Double.parseDouble(ta));
                String tb = mTB.getText().toString();
                if (tb.isEmpty()) {
                    ArmsUtils.snackbarText("?????????T????????????B");
                    return;
                }
                projectJTJ.setTB(Double.parseDouble(tb));
                if (selectedItemPosition == 1) {
                    String tca = mTcA.getText().toString();
                    if (tca.isEmpty()) {
                        ArmsUtils.snackbarText("?????????T/C???A");
                        return;
                    }
                    projectJTJ.setC_tA(Double.parseDouble(tca));
                    String tcb = mTcB.getText().toString();
                    if (tcb.isEmpty()) {
                        ArmsUtils.snackbarText("?????????T/C???B");
                        return;
                    }
                    projectJTJ.setC_tB(Double.parseDouble(tcb));
                }
                projectJTJ.setFinishState(true);
                if (mDfCurve.isChecked()) {
                    projectJTJ.setIsdefault(true);
                    List<ProjectJTJ> list = DBHelper.getProjectJTJDao().queryBuilder()
                            .where(ProjectJTJDao.Properties.ProjectName.eq(projectJTJ.getProjectName()))
                            .where(ProjectJTJDao.Properties.Isdefault.eq(true)).list();
                    if (list.size() > 0) {
                        ProjectJTJ p = list.get(0);
                        p.setIsdefault(false);
                        DBHelper.getProjectJTJDao().update(p);
                    }
                }
                LogUtils.d(projectJTJ);
                DBHelper.getProjectJTJDao().update(projectJTJ);
                LogUtils.d(DBHelper.getProjectJTJDao().loadAll());
                ArmsUtils.snackbarText("????????????");
                break;
            case R.id.delete_curve:
                deleteCurve();
                
                break;
        }
    }

    private void deleteCurve() {
        if (null == projectJTJ) {
            ArmsUtils.snackbarText("????????????????????????");
            return;
        }
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("???????????????????????????");

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String pjName = projectJTJ.getPjName();
                boolean isdefault = projectJTJ.isdefault();
                DBHelper.getProjectJTJDao().delete(projectJTJ);
                projectJTJ=null;
                List<ProjectJTJ> list = DBHelper.getProjectJTJDao().queryBuilder().where(ProjectJTJDao.Properties.ProjectName.eq(pjName))
                        .orderDesc(ProjectJTJDao.Properties.CurveOrder).build().list();

                if (list.size() > 0) {
                    ProjectJTJ P = list.get(0);

                    if (isdefault) {

                        P.setIsdefault(true);
                        DBHelper.getProjectJTJDao().update(P);
                    }
                    setData(P);
                }else {
                    //????????????????????????????????????????????????????????????
                    EventBus.getDefault().post(new NewProjectJTJFragment.JTJProjectFragmentEvent(-1, null));
                }

                ArmsUtils.snackbarText("????????????");
                //initCurve(null, project);

            }
        });
        alertDialog.show();

    }


    public static class JTJProjectFragmentEvent {
        private int persion;
        private ProjectJTJ projectJTJ;

        public JTJProjectFragmentEvent(int persion, ProjectJTJ projectFGGD) {
            this.persion = persion;
            this.projectJTJ = projectFGGD;
        }

        public int getPersion() {
            return persion;
        }

        public void setPersion(int persion) {
            this.persion = persion;
        }

        public ProjectJTJ getProjectJTJ() {
            return projectJTJ;
        }

        public void setProjectFGGD(ProjectJTJ projectFGGD) {

            this.projectJTJ = projectFGGD;
        }
    }
}