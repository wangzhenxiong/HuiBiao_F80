package com.dy.huibiao_f80.mvp.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.apkfuns.logutils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dy.huibiao_f80.MyAppLocation;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.app.utils.CurveFitter;
import com.dy.huibiao_f80.app.utils.NumberUtils;
import com.dy.huibiao_f80.bean.GalleryBean;
import com.dy.huibiao_f80.bean.eventBusBean.FGTestMessageBean;
import com.dy.huibiao_f80.di.component.DaggerNewProjectFGGDComponent;
import com.dy.huibiao_f80.greendao.DBHelper;
import com.dy.huibiao_f80.greendao.ProjectFGGD;
import com.dy.huibiao_f80.greendao.daos.ProjectFGGDDao;
import com.dy.huibiao_f80.mvp.contract.NewProjectFGGDContract;
import com.dy.huibiao_f80.mvp.presenter.NewProjectFGGDPresenter;
import com.dy.huibiao_f80.mvp.ui.adapter.GetCureAdapter;
import com.dy.huibiao_f80.mvp.ui.adapter.GuidePageAdapter_h;
import com.dy.huibiao_f80.mvp.ui.adapter.MySpinnerAdapterFGGD;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.method_layout)
    LinearLayout mVp;
    @BindView(R.id.save_curve)
    Button mSaveCurve;
    @BindView(R.id.delete_curve)
    Button mDeleteCurve;
    @BindView(R.id.standardname)
    AutoCompleteTextView mStandardname;
    @BindView(R.id.df_curve)
    CheckBox mDfCurve;
    @BindView(R.id.detectionLimit)
    AutoCompleteTextView mDetectionLimit;
    @BindView(R.id.tips)
    AutoCompleteTextView mTips;
    @BindView(R.id.checkbox_ok)
    CheckBox mCheckboxOk;
    @BindView(R.id.ok_a_demarcate)
    Spinner mOkADemarcate;
    @BindView(R.id.ok_a)
    AutoCompleteTextView mOkA;
    @BindView(R.id.ok_b_demarcate)
    Spinner mOkBDemarcate;
    @BindView(R.id.ok_b)
    AutoCompleteTextView mOkB;
    @BindView(R.id.checkbox_ng)
    CheckBox mCheckboxNg;
    @BindView(R.id.ng_a_demarcate)
    Spinner mNgADemarcate;
    @BindView(R.id.ng_a)
    AutoCompleteTextView mNgA;
    @BindView(R.id.ng_b_demarcate)
    Spinner mNgBDemarcate;
    @BindView(R.id.ng_b)
    AutoCompleteTextView mNgB;
    @BindView(R.id.checkbox_df)
    CheckBox mCheckboxDf;
    @BindView(R.id.df_a_demarcate)
    Spinner mDfADemarcate;
    @BindView(R.id.df_a)
    AutoCompleteTextView mDfA;
    @BindView(R.id.df_b_demarcate)
    Spinner mDfBDemarcate;
    @BindView(R.id.df_b)
    AutoCompleteTextView mDfB;
    @BindView(R.id.yrsj)
    AutoCompleteTextView mYrsj;
    @BindView(R.id.jcsj)
    AutoCompleteTextView mJcsj;
    @BindView(R.id.unit_input)
    AutoCompleteTextView mUnitInput;
    @BindView(R.id.new_curve)
    ImageButton mNewCurve;
    @BindView(R.id.sp_curegroup)
    Spinner mSpCuregroup;
    private View methoed_a_page;
    private View methoed_b_page;
    private View methoed_c_page;
    private View methoed_d_page;
    private ViewHoldera mViewHoldera;
    private ViewHolderb mViewHolderb;
    private ViewHolderc mViewHolderc;
    private ViewHolderd mViewHolderd;
    private GuidePageAdapter_h mAdapter;
    private int method_checked_flag = 0;
    private int wavalength_checked_flag = 0;
    private ProjectFGGD projectFGGD;
    private GetCureAdapter getCureAdapter = new GetCureAdapter(R.layout.getcure_item_layout, MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList);

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

        LogUtils.d("initData 执行");
        mChosewavalength.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                wavalength_checked_flag = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mChosemethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mVp.removeAllViews();
                        initpages_a();
                        mVp.addView(methoed_a_page);
                        break;
                    case 1:
                        mVp.removeAllViews();
                        initpages_b();
                        mVp.addView(methoed_b_page);
                        break;
                    case 2:
                        mVp.removeAllViews();
                        initpages_c();

                        mVp.addView(methoed_c_page);
                        break;
                    case 3:
                        mVp.removeAllViews();
                        initpages_d();
                        mVp.addView(methoed_d_page);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        initSpinner();
    }

    private void initSpinner() {
        List<String> mItems = new ArrayList<>();
        mItems.add("<");
        mItems.add(">");
        mItems.add("≤");
        mItems.add("≥");
        mItems.add("无");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, mItems);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, mItems);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, mItems);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, mItems);
        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, mItems);
        ArrayAdapter<String> adapter6 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, mItems);
        mOkADemarcate.setAdapter(adapter1);
        mOkBDemarcate.setAdapter(adapter2);
        mNgADemarcate.setAdapter(adapter3);
        mNgBDemarcate.setAdapter(adapter4);
        mDfADemarcate.setAdapter(adapter5);
        mDfBDemarcate.setAdapter(adapter6);

    }

    private void initpages_a() {
        methoed_a_page = getLayoutInflater().inflate(R.layout.method_a, null);
        mViewHoldera = new ViewHoldera(methoed_a_page);


    }

    private void initpages_b() {
        LayoutInflater inflater = getLayoutInflater();
        methoed_b_page = inflater.inflate(R.layout.method_b, null);
        mViewHolderb = new ViewHolderb(methoed_b_page);
        if (null == projectFGGD) {
            return;
        }
        mViewHolderb.mAx0.setText(projectFGGD.getA0() + "");
        mViewHolderb.mAx1.setText(projectFGGD.getB0() + "");
        mViewHolderb.mAx2.setText(projectFGGD.getC0() + "");
        mViewHolderb.mAx3.setText(projectFGGD.getD0() + "");
        mViewHolderb.mAfrom.setText(projectFGGD.getFrom0() + "");
        mViewHolderb.mAto.setText(projectFGGD.getTo0() + "");

        mViewHolderb.mBx0.setText(projectFGGD.getA1() + "");
        mViewHolderb.mBx1.setText(projectFGGD.getB1() + "");
        mViewHolderb.mBx2.setText(projectFGGD.getC1() + "");
        mViewHolderb.mBx3.setText(projectFGGD.getD1() + "");
        mViewHolderb.mBfrom.setText(projectFGGD.getFrom1() + "");
        mViewHolderb.mBto.setText(projectFGGD.getTo1() + "");

        mViewHolderb.mJzqxa.setText(projectFGGD.getA() + "");
        mViewHolderb.mJzqxb.setText(projectFGGD.getB() + "");
        mViewHolderb.mAutoA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int wave = 0;
                if (wavalength_checked_flag==0){
                 wave=410;
                }else if (wavalength_checked_flag==1){
                    wave=536;
                }else if (wavalength_checked_flag==2){
                    wave=595;
                }else if (wavalength_checked_flag==3){
                    wave=620;
                }
                makeDialogGetCurve(wave,"a");
            }
        });
        mViewHolderb.mAutoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int wave = 0;
                if (wavalength_checked_flag==0){
                    wave=410;
                }else if (wavalength_checked_flag==1){
                    wave=536;
                }else if (wavalength_checked_flag==2){
                    wave=595;
                }else if (wavalength_checked_flag==3){
                    wave=620;
                }
                makeDialogGetCurve(wave,"b");
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(FGTestMessageBean tags) {
        switch (tags.tag) {
            case 0:

                getCureAdapter.notifyDataSetChanged();

                break;
        }
    }

    private void makeDialogGetCurve(int wavelength, String b) {
        for (int i = 0; i < MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.size(); i++) {
            MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.get(i).setCheckd(false);
            MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.get(i).setMic(0);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.getcurve_layout, null);

        inflate.findViewById(R.id.btn_clean).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.size(); i++) {
                    MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.get(i).setClearn(true);
                }
            }
        });
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.recylerview);
        ArmsUtils.configRecyclerView(recyclerView, new GridLayoutManager(getActivity(), 1));
        recyclerView.setAdapter(getCureAdapter);
        getCureAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.checkbox) {
                    GalleryBean galleryBean = MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.get(position);
                    if (galleryBean.isCheckd()) {
                        galleryBean.setCheckd(false);
                    } else {
                        galleryBean.setCheckd(true);
                    }
                } else if (view.getId() == R.id.mic) {
                    makeInputMicDialog(position);
                }
            }
        });


        builder.setView(inflate);
        AlertDialog dialog = builder.create();

        inflate.findViewById(R.id.btn_quit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        inflate.findViewById(R.id.btn_getcurve).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<GalleryBean> list = new ArrayList<>();
                for (int i = 0; i < MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.size(); i++) {
                    GalleryBean galleryBean = MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.get(i);
                    if (galleryBean.isCheckd()) {
                        list.add(galleryBean);
                    }
                }
                if (list.size() < 4) {
                    ArmsUtils.snackbarText("请至少选中4个通道");
                } else {
                    double[] doublex = new double[list.size()];
                    double[] doubley = new double[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        GalleryBean galleryBean = list.get(i);
                        double mic = galleryBean.getMic();
                        double abs = 0;
                        if (wavelength == 410) {
                            abs = galleryBean.getAbsorbance1();
                        } else if (wavelength == 536) {
                            abs = galleryBean.getAbsorbance2();
                        } else if (wavelength == 595) {
                            abs = galleryBean.getAbsorbance3();
                        } else if (wavelength == 620) {
                            abs = galleryBean.getAbsorbance4();
                        }
                        doublex[i]=abs;
                        doubley[i]=mic;
                    }

                    CurveFitter curveFitter = new CurveFitter(doublex,doubley);
                    //CurveFitter curveFitter = new CurveFitter(new double[]{0.1,0.3,0.8,1.1,1.5},new double[]{50,100,150,200,250});
                    curveFitter.doFit(2);
                    curveFitter.getFit();
                    double[] string1 = curveFitter.getResultString1();
                    LogUtils.d(string1);
                    if (b.equals("a")){
                        mViewHolderb.mAx0.setText(NumberUtils.four(string1[0]) + "");
                        mViewHolderb.mAx1.setText(NumberUtils.four(string1[1])+ "");
                        mViewHolderb.mAx2.setText(NumberUtils.four(string1[2])+ "");
                        mViewHolderb.mAx3.setText(NumberUtils.four(string1[3]) + "");
                    }else if (b.equals("b")){
                        mViewHolderb.mBx0.setText(NumberUtils.four(string1[0]) + "");
                        mViewHolderb.mBx1.setText(NumberUtils.four(string1[1]) + "");
                        mViewHolderb.mBx2.setText(NumberUtils.four(string1[2]) + "");
                        mViewHolderb.mBx3.setText(NumberUtils.four(string1[3]) + "");
                    }
                    dialog.dismiss();
                }
            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

    private void makeInputMicDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.edtextview_layout, null);
        AutoCompleteTextView textView = (AutoCompleteTextView) inflate.findViewById(R.id.textinput_counter);
        builder.setTitle("预设浓度值");
        builder.setView(inflate);
        AlertDialog dialog = builder.create();
        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String s = textView.getText().toString();
                if (s.isEmpty()) {
                    ArmsUtils.snackbarText("请输入浓度值");
                } else {
                    MyAppLocation.myAppLocation.mSerialDataService.mFGGDGalleryBeanList.get(position).setMic(Double.parseDouble(s));
                    dialog.dismiss();
                }
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void initpages_c() {
        LayoutInflater inflater = getLayoutInflater();


        methoed_c_page = inflater.inflate(R.layout.method_c, null);
        mViewHolderc = new ViewHolderc(methoed_c_page);
        if (null == projectFGGD) {
            return;
        }
        mViewHolderc.mJzqxa.setText(projectFGGD.getA() + "");
        mViewHolderc.mJzqxb.setText(projectFGGD.getB() + "");


    }

    private void initpages_d() {
        LayoutInflater inflater = getLayoutInflater();

        methoed_d_page = inflater.inflate(R.layout.method_d, null);
        mViewHolderd = new ViewHolderd(methoed_d_page);
        if (null == projectFGGD) {
            return;
        }
        mViewHolderd.mJzqxa.setText(projectFGGD.getA() + "");
        mViewHolderd.mJzqxb.setText(projectFGGD.getB() + "");
        mViewHolderd.mJzqxc.setText(projectFGGD.getC() + "");
        mViewHolderd.mJzqxd.setText(projectFGGD.getD() + "");
    }

    @Override
    public void setData(@Nullable Object data) {
        LogUtils.d(data);
        ProjectFGGD prject;
        String name;
        if (null == data) {
            prject = null;
            initMessage(new ProjectFGGD());
            name = "";
        } else {
            prject = ((ProjectFGGD) data);
            //initMessage(projectFGGD);
            name = prject.getCurveName();
        }
        initCurve(name, prject);

    }

    private void initCurve(String curvename, ProjectFGGD data) {
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
        List<ProjectFGGD> list = DBHelper.getProjectFGGDDao().queryBuilder()
                .where(ProjectFGGDDao.Properties.ProjectName.eq(data.getPjName()))
                .build().list();
        LogUtils.d(list);
        LogUtils.d(curvename);
        MySpinnerAdapterFGGD adapter = new MySpinnerAdapterFGGD(list, getActivity());


        mSpCuregroup.setAdapter(adapter);
        mSpCuregroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ProjectFGGD object = list.get(position);
                LogUtils.d(object);
                projectFGGD = object;
                initMessage(projectFGGD);
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

    private void initMessage(ProjectFGGD projectFGGD) {
        if (null == mTestprojectname) {
            return;
        }
        mTestprojectname.setText(projectFGGD.getPjName());
        mStandardname.setText(projectFGGD.getStandardName());
        mCureName.setText(projectFGGD.getCurveName());
        mDfCurve.setChecked(projectFGGD.isdefault());
        mDetectionLimit.setText(projectFGGD.getDetectionLimit());
        mTips.setText(projectFGGD.getTips());
        mCheckboxOk.setChecked(projectFGGD.isUser_yin());
        mCheckboxNg.setChecked(projectFGGD.isUser_yang());
        mCheckboxDf.setChecked(projectFGGD.isUser_keyi());
        mYrsj.setText(projectFGGD.getYuretime() + "");
        mJcsj.setText(projectFGGD.getJiancetime() + "");
        mUnitInput.setText(projectFGGD.getResultUnit() + "");
        mCheckboxOk.setChecked(projectFGGD.getUser_yin());
        String yin_a_symbol = projectFGGD.getYin_a_symbol();
        switch (yin_a_symbol) {
            case "<":
                mOkADemarcate.setSelection(0);
                break;
            case ">":
                mOkADemarcate.setSelection(1);
                break;
            case "≤":
                mOkADemarcate.setSelection(2);
                break;
            case "≥":
                mOkADemarcate.setSelection(3);
                break;
            case "无":
                mOkADemarcate.setSelection(4);
                break;
        }
        String yin_b_symbol = projectFGGD.getYin_b_symbol();
        switch (yin_b_symbol) {
            case "<":
                mOkBDemarcate.setSelection(0);
                break;
            case ">":
                mOkBDemarcate.setSelection(1);
                break;
            case "≤":
                mOkBDemarcate.setSelection(2);
                break;
            case "≥":
                mOkBDemarcate.setSelection(3);
                break;
            case "无":
                mOkBDemarcate.setSelection(4);
        }
        mOkA.setText(projectFGGD.getYin_a() + "");
        mOkB.setText(projectFGGD.getYin_b() + "");
        mCheckboxNg.setChecked(projectFGGD.getUser_yang());
        String yang_a_symbol = projectFGGD.getYang_a_symbol();
        switch (yang_a_symbol) {
            case "<":
                mNgADemarcate.setSelection(0);
                break;
            case ">":
                mNgADemarcate.setSelection(1);
                break;
            case "≤":
                mNgADemarcate.setSelection(2);
                break;
            case "≥":
                mNgADemarcate.setSelection(3);
                break;
            case "无":
                mNgADemarcate.setSelection(4);
        }
        String yang_b_symbol = projectFGGD.getYang_b_symbol();
        switch (yang_b_symbol) {
            case "<":
                mNgBDemarcate.setSelection(0);
                break;
            case ">":
                mNgBDemarcate.setSelection(1);
                break;
            case "≤":
                mNgBDemarcate.setSelection(2);
                break;
            case "≥":
                mNgBDemarcate.setSelection(3);
                break;
            case "无":
                mNgBDemarcate.setSelection(4);
        }
        mNgA.setText(projectFGGD.getYang_a() + "");
        mNgB.setText(projectFGGD.getYang_b() + "");
        mCheckboxDf.setChecked(projectFGGD.getUser_keyi());
        String keyi_a_symbol = projectFGGD.getKeyi_a_symbol();
        switch (keyi_a_symbol) {
            case "<":
                mDfADemarcate.setSelection(0);
                break;
            case ">":
                mDfADemarcate.setSelection(1);
                break;
            case "≤":
                mDfADemarcate.setSelection(2);
                break;
            case "≥":
                mDfADemarcate.setSelection(3);
                break;
            case "无":
                mDfADemarcate.setSelection(4);
        }
        String keyi_b_symbol = projectFGGD.getKeyi_b_symbol();
        switch (keyi_b_symbol) {
            case "<":
                mDfBDemarcate.setSelection(0);
                break;
            case ">":
                mDfBDemarcate.setSelection(1);
                break;
            case "≤":
                mDfBDemarcate.setSelection(2);
                break;
            case "≥":
                mDfBDemarcate.setSelection(3);
                break;
            case "无":
                mDfBDemarcate.setSelection(4);
        }
        mDfA.setText(projectFGGD.getKeyi_a() + "");
        mDfB.setText(projectFGGD.getKeyi_b() + "");
        int method = projectFGGD.getMethod();
        mChosemethod.setSelection(method);
        int waveLength = projectFGGD.getWaveLength();
        mChosewavalength.setSelection(waveLength);
        switch (method) {
            case 0:

                break;
            case 1:
                mVp.removeAllViews();
                initpages_b();
                mVp.addView(methoed_b_page);
                break;
            case 2:
                mVp.removeAllViews();
                initpages_c();
                mVp.addView(methoed_c_page);
                break;
            case 3:
                mVp.removeAllViews();
                initpages_d();
                mVp.addView(methoed_d_page);
                break;
        }

       /* mCheckboxOk.setChecked(projectFGGD.isUser_yin());
        mCheckboxNg.setChecked(projectFGGD.isUser_yang());
        mCheckboxDf.setChecked(projectFGGD.isUser_keyi());*/


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

    @OnClick({R.id.save_curve, R.id.delete_curve, R.id.new_curve})
    public void onClick(View view) {
        if (null == projectFGGD) {
            ArmsUtils.snackbarText("请先选择检测项目");
            return;
        }
        switch (view.getId()) {
            case R.id.save_curve:
                saveCurve();
                break;
            case R.id.delete_curve:
                deleteCurve();
                break;
            case R.id.new_curve:
                newCurve();
                break;
        }
    }

    private void newCurve() {
        if (null == projectFGGD) {
            ArmsUtils.snackbarText("请先选择检测项目");
            return;
        }
        String pjName = projectFGGD.getPjName();
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("请输入新建曲线的名称");
        EditText view = new EditText(getActivity());
        long count;
        count = DBHelper.getProjectFGGDDao().queryBuilder().where(ProjectFGGDDao.Properties.ProjectName.eq(pjName)).count();
        view.setText("曲线" + (count + 1));


        alertDialog.setView(view);
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String curvename = view.getText().toString();
                if (curvename.isEmpty()) {
                    ArmsUtils.snackbarText("请输入新建曲线的名称");
                    return;
                }

                //ProjectFGGD projectFGGD = new ProjectFGGD();
                projectFGGD.setId(null);
                projectFGGD.setProjectName(pjName);
                projectFGGD.setCurveName(curvename);
                projectFGGD.setCurveOrder((int) count);
                projectFGGD.setA(1);
                projectFGGD.setB(0);
                projectFGGD.setC(0);
                projectFGGD.setD(0);
                projectFGGD.setA1(0);
                projectFGGD.setB1(0);
                projectFGGD.setC1(0);
                projectFGGD.setD1(0);
                projectFGGD.setFrom1(0);
                projectFGGD.setTo1(0);
                projectFGGD.setA0(0);
                projectFGGD.setB0(0);
                projectFGGD.setC0(0);
                projectFGGD.setD0(0);
                projectFGGD.setFrom0(0);
                projectFGGD.setTo0(0);
                projectFGGD.setCreator(1);
                DBHelper.getProjectFGGDDao().insert(projectFGGD);
                initCurve(curvename, projectFGGD);


            }
        });
        alertDialog.show();


    }

    private void saveCurve() {

        String projectname = mTestprojectname.getText().toString();
        if (projectname.isEmpty()) {
            ArmsUtils.snackbarText("请输入检测项目");
            return;
        }
        projectFGGD.setProjectName(projectname);
        String standname = mStandardname.getText().toString();
        if (standname.isEmpty()) {
            ArmsUtils.snackbarText("请输入检测标准");
            return;
        }
        projectFGGD.setStandardName(standname);
        String curvename = mCureName.getText().toString();
        if (curvename.isEmpty()) {
            ArmsUtils.snackbarText("请输入曲线名称");
            return;
        }
        projectFGGD.setCurveName(curvename);
        String detectionLimit = mDetectionLimit.getText().toString();
        if (detectionLimit.isEmpty()) {
            ArmsUtils.snackbarText("请输入方法检出限");
            return;
        }
        projectFGGD.setDetectionLimit(detectionLimit);
        String yrsj = mYrsj.getText().toString();
        if (yrsj.isEmpty()) {
            ArmsUtils.snackbarText("请输入预热时间");
            return;
        }
        projectFGGD.setWarmTime(Integer.parseInt(yrsj));
        String jcsj = mJcsj.getText().toString();
        if (jcsj.isEmpty()) {
            ArmsUtils.snackbarText("请输入检测时间");
            return;
        }
        projectFGGD.setTestTime(Integer.parseInt(jcsj));
        String resultunit = mUnitInput.getText().toString();
        if (resultunit.isEmpty()) {
            ArmsUtils.snackbarText("请输入检测结果单位");
            return;
        }
        projectFGGD.setResultUnit(resultunit);
        String tips = mTips.getText().toString();
        projectFGGD.setTips(tips);

        int selectedItemPosition = mChosemethod.getSelectedItemPosition();
        projectFGGD.setMethod(selectedItemPosition);
        if (selectedItemPosition == 0) {

        } else if (selectedItemPosition == 1) {
            String ax0 = mViewHolderb.mAx0.getText().toString();
            if (ax0.isEmpty()) {
                ArmsUtils.snackbarText("请输入A-X^0");
                return;
            }
            projectFGGD.setA0(Double.parseDouble(ax0));
            String ax1 = mViewHolderb.mAx1.getText().toString();
            if (ax1.isEmpty()) {
                ArmsUtils.snackbarText("请输入A-X^1");
                return;
            }
            projectFGGD.setB0(Double.parseDouble(ax1));
            String ax2 = mViewHolderb.mAx2.getText().toString();
            if (ax2.isEmpty()) {
                ArmsUtils.snackbarText("请输入A-X^2");
                return;
            }
            projectFGGD.setC0(Double.parseDouble(ax2));
            String ax3 = mViewHolderb.mAx3.getText().toString();
            if (ax3.isEmpty()) {
                ArmsUtils.snackbarText("请输入A-X^3");
                return;
            }
            projectFGGD.setD0(Double.parseDouble(ax3));
            String afrom = mViewHolderb.mAfrom.getText().toString();
            if (afrom.isEmpty()) {
                ArmsUtils.snackbarText("请输入A-from");
                return;
            }
            projectFGGD.setFrom0(Double.parseDouble(afrom));
            String ato = mViewHolderb.mAto.getText().toString();
            if (ato.isEmpty()) {
                ArmsUtils.snackbarText("请输入A-to");
                return;
            }
            projectFGGD.setTo0(Double.parseDouble(ato));
            String bx0 = mViewHolderb.mBx0.getText().toString();
            if (bx0.isEmpty()) {
                ArmsUtils.snackbarText("请输入B-X^0");
                return;
            }
            projectFGGD.setA1(Double.parseDouble(bx0));
            String bx1 = mViewHolderb.mBx1.getText().toString();
            if (bx1.isEmpty()) {
                ArmsUtils.snackbarText("请输入B-X^1");
                return;
            }
            projectFGGD.setB1(Double.parseDouble(bx1));
            String bx2 = mViewHolderb.mBx2.getText().toString();
            if (bx2.isEmpty()) {
                ArmsUtils.snackbarText("请输入B-X^2");
                return;
            }
            projectFGGD.setC1(Double.parseDouble(bx2));
            String bx3 = mViewHolderb.mBx3.getText().toString();
            if (bx3.isEmpty()) {
                ArmsUtils.snackbarText("请输入B-X^3");
                return;
            }
            projectFGGD.setD1(Double.parseDouble(bx3));
            String bfrom = mViewHolderb.mBfrom.getText().toString();
            if (bfrom.isEmpty()) {
                ArmsUtils.snackbarText("请输入B-from");
                return;
            }
            projectFGGD.setFrom1(Double.parseDouble(bfrom));
            String bto = mViewHolderb.mBto.getText().toString();
            if (bto.isEmpty()) {
                ArmsUtils.snackbarText("请输入B-to");
                return;
            }
            projectFGGD.setTo1(Double.parseDouble(bto));
            String jzqxa = mViewHolderb.mJzqxa.getText().toString();
            if (jzqxa.isEmpty()) {
                ArmsUtils.snackbarText("请输入校正A");
                return;
            }
            projectFGGD.setA(Double.parseDouble(jzqxa));
            String jzqxb = mViewHolderb.mJzqxb.getText().toString();
            if (jzqxb.isEmpty()) {
                ArmsUtils.snackbarText("请输入校正B");
                return;
            }
            projectFGGD.setB(Double.parseDouble(jzqxb));

        } else if (selectedItemPosition == 2) {
            String jzqxa = mViewHolderc.mJzqxa.getText().toString();
            if (jzqxa.isEmpty()) {
                ArmsUtils.snackbarText("请输入校正A");
                return;
            }
            projectFGGD.setA(Double.parseDouble(jzqxa));
            String jzqxb = mViewHolderc.mJzqxb.getText().toString();
            if (jzqxb.isEmpty()) {
                ArmsUtils.snackbarText("请输入校正B");
                return;
            }
            projectFGGD.setB(Double.parseDouble(jzqxb));
        } else if (selectedItemPosition == 3) {
            String jzqxa = mViewHolderd.mJzqxa.getText().toString();
            if (jzqxa.isEmpty()) {
                ArmsUtils.snackbarText("请输入校正A");
                return;
            }
            projectFGGD.setA(Double.parseDouble(jzqxa));
            String jzqxb = mViewHolderd.mJzqxb.getText().toString();
            if (jzqxb.isEmpty()) {
                ArmsUtils.snackbarText("请输入校正B");
                return;
            }
            projectFGGD.setB(Double.parseDouble(jzqxb));
            String jzqxc = mViewHolderd.mJzqxc.getText().toString();
            if (jzqxc.isEmpty()) {
                ArmsUtils.snackbarText("请输入校正C");
                return;
            }
            projectFGGD.setC(Double.parseDouble(jzqxc));
            String jzqxd = mViewHolderd.mJzqxd.getText().toString();
            if (jzqxd.isEmpty()) {
                ArmsUtils.snackbarText("请输入校正D");
                return;
            }
            projectFGGD.setD(Double.parseDouble(jzqxd));
        }
        int selectedItemPosition1 = mChosewavalength.getSelectedItemPosition();
        projectFGGD.setWaveLength(selectedItemPosition1);
        if (mCheckboxOk.isChecked()) {
            projectFGGD.setUser_yin(true);
            String sysa = mOkADemarcate.getSelectedItem().toString();
            String sysb = mOkBDemarcate.getSelectedItem().toString();
            projectFGGD.setYin_a_symbol(sysa);
            projectFGGD.setYin_b_symbol(sysb);
            String oka = mOkA.getText().toString();
            if (!sysa.equals("无")) {

                if (oka.isEmpty()) {
                    ArmsUtils.snackbarText("请输入合格区间A");
                    return;
                }
                projectFGGD.setYin_a(Double.parseDouble(oka));
            } else {
                projectFGGD.setYin_a(0);
            }
            String okb = mOkB.getText().toString();
            if (!sysb.equals("无")) {

                if (okb.isEmpty()) {
                    ArmsUtils.snackbarText("请输入合格区间B");
                    return;
                }
                projectFGGD.setYin_b(Double.parseDouble(okb));
            } else {
                projectFGGD.setYin_b(0);
            }
        } else {
            projectFGGD.setUser_yin(false);
            projectFGGD.setYin_a_symbol(null);
            projectFGGD.setYin_b_symbol(null);
            projectFGGD.setYin_a(0);
            projectFGGD.setYin_b(0);
        }
        if (mCheckboxNg.isChecked()) {
            projectFGGD.setUser_yang(true);
            String sysa = mNgADemarcate.getSelectedItem().toString();
            String sysb = mNgBDemarcate.getSelectedItem().toString();
            projectFGGD.setYang_a_symbol(sysa);
            projectFGGD.setYang_b_symbol(sysb);
            String nga = mNgA.getText().toString();
            if (!sysa.equals("无")) {

                if (nga.isEmpty()) {
                    ArmsUtils.snackbarText("请输入不合格区间A");
                    return;
                }
                projectFGGD.setYang_a(Double.parseDouble(nga));
            } else {
                projectFGGD.setYang_a(0);

            }
            String ngb = mNgB.getText().toString();
            if (!sysb.equals("无")) {

                if (ngb.isEmpty()) {
                    ArmsUtils.snackbarText("请输入不合格区间B");
                    return;
                }
                projectFGGD.setYang_b(Double.parseDouble(ngb));
            } else {
                projectFGGD.setYang_b(0);
            }
        } else {
            projectFGGD.setUser_yang(false);
            projectFGGD.setYang_a_symbol(null);
            projectFGGD.setYang_b_symbol(null);
            projectFGGD.setYang_a(0);
            projectFGGD.setYang_b(0);
        }
        if (mCheckboxDf.isChecked()) {
            projectFGGD.setUser_keyi(true);
            String sysa = mDfADemarcate.getSelectedItem().toString();
            String sysb = mDfBDemarcate.getSelectedItem().toString();
            projectFGGD.setKeyi_a_symbol(sysa);
            projectFGGD.setKeyi_b_symbol(sysb);
            String dfa = mDfA.getText().toString();
            if (!sysa.equals("无")) {

                if (dfa.isEmpty()) {
                    ArmsUtils.snackbarText("请输入可疑区间A");
                    return;
                }
                projectFGGD.setKeyi_a(Double.parseDouble(dfa));
            } else {
                projectFGGD.setKeyi_a(0);
            }
            String dfb = mDfB.getText().toString();
            if (!sysb.equals("无")) {

                if (dfb.isEmpty()) {
                    ArmsUtils.snackbarText("请输入可疑区间B");
                    return;
                }
                projectFGGD.setKeyi_b(Double.parseDouble(dfb));
            } else {
                projectFGGD.setKeyi_b(0);
            }
        } else {
            projectFGGD.setUser_keyi(false);
            projectFGGD.setKeyi_b(0);
            projectFGGD.setKeyi_a(0);
            projectFGGD.setKeyi_a_symbol(null);
            projectFGGD.setKeyi_b_symbol(null);
        }

        projectFGGD.setFinishState(true);
        if (mDfCurve.isChecked()) {
            projectFGGD.setIsdefault(true);
            List<ProjectFGGD> list = DBHelper.getProjectFGGDDao().queryBuilder()
                    .where(ProjectFGGDDao.Properties.Isdefault.eq(true))
                    .where(ProjectFGGDDao.Properties.ProjectName.eq(projectname))
                    .list();
            if (list.size() > 0) {
                ProjectFGGD p = list.get(0);
                p.setIsdefault(false);
                DBHelper.getProjectFGGDDao().update(p);
            }
        }

        DBHelper.getProjectFGGDDao().update(projectFGGD);
        LogUtils.d(projectFGGD);
        ArmsUtils.snackbarText("保存成功");
    }

    private void deleteCurve() {
        if (null == projectFGGD) {
            ArmsUtils.snackbarText("请先选择检测项目");
            return;
        }
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
                String pjName = projectFGGD.getPjName();
                boolean isdefault = projectFGGD.isdefault();
                DBHelper.getProjectFGGDDao().delete(projectFGGD);
                projectFGGD = null;
                //删除后还有没有剩余曲线
                List<ProjectFGGD> list = DBHelper.getProjectFGGDDao().queryBuilder().where(ProjectFGGDDao.Properties.ProjectName.eq(pjName))
                        .orderDesc(ProjectFGGDDao.Properties.CurveOrder).build().list();
                if (list.size() > 0) {
                    ProjectFGGD projectFGGD = list.get(0);
                    if (isdefault) {
                        projectFGGD.setIsdefault(true);
                        DBHelper.getProjectFGGDDao().update(projectFGGD);
                    }

                    setData(projectFGGD);
                } else {
                    //删除了最后一个曲线，需要重新进行数据加载
                    EventBus.getDefault().post(new NewProjectFGGDFragment.FGGDProjectFragmentEvent(-1, null));
                }


                ArmsUtils.snackbarText("删除成功");

            }
        });
        alertDialog.show();

    }

    public static class FGGDProjectFragmentEvent {
        private int persion;
        private ProjectFGGD projectFGGD;

        public FGGDProjectFragmentEvent(int persion, ProjectFGGD projectFGGD) {
            this.persion = persion;
            this.projectFGGD = projectFGGD;
        }

        public int getPersion() {
            return persion;
        }

        public void setPersion(int persion) {
            this.persion = persion;
        }

        public ProjectFGGD getProjectFGGD() {
            return projectFGGD;
        }

        public void setProjectFGGD(ProjectFGGD projectFGGD) {
            this.projectFGGD = projectFGGD;
        }
    }

    static
    class ViewHoldera {


        ViewHoldera(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static
    class ViewHolderb {

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
        @BindView(R.id.auto_a)
        ImageButton mAutoA;
        @BindView(R.id.auto_b)
        ImageButton mAutoB;


        ViewHolderb(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static
    class ViewHolderc {

        @BindView(R.id.jzqxa)
        AutoCompleteTextView mJzqxa;
        @BindView(R.id.jzqxb)
        AutoCompleteTextView mJzqxb;


        ViewHolderc(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static
    class ViewHolderd {

        @BindView(R.id.jzqxa)
        AutoCompleteTextView mJzqxa;
        @BindView(R.id.jzqxb)
        AutoCompleteTextView mJzqxb;
        @BindView(R.id.jzqxc)
        AutoCompleteTextView mJzqxc;
        @BindView(R.id.jzqxd)
        AutoCompleteTextView mJzqxd;


        ViewHolderd(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void setOnEventListenner(OnEventFGGD onEvent) {
        mEvent = onEvent;
    }

    OnEventFGGD mEvent;

    public interface OnEventFGGD {
        void onEventFGGD(int what, Object o);
    }
}