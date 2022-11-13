package com.dy.huibiao_f80.mvp.ui.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.bean.base.BaseProjectMessage;
import com.dy.huibiao_f80.di.component.DaggerFGGDProjectComponent;
import com.dy.huibiao_f80.greendao.ProjectFGGD;
import com.dy.huibiao_f80.mvp.contract.FGGDProjectContract;
import com.dy.huibiao_f80.mvp.presenter.FGGDProjectPresenter;
import com.dy.huibiao_f80.mvp.ui.widget.lettersnavigation.search.CharIndexView;
import com.dy.huibiao_f80.mvp.ui.widget.lettersnavigation.search.ClearEditText;
import com.dy.huibiao_f80.mvp.ui.widget.lettersnavigation.search.PinyinComparator;
import com.dy.huibiao_f80.mvp.ui.widget.lettersnavigation.search.PinyinUtils;
import com.dy.huibiao_f80.mvp.ui.widget.lettersnavigation.search.TitleItemDecoration;
import com.dy.huibiao_f80.mvp.ui.widget.lettersnavigation.search.adapter.SortAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class FGGDProjectFragment extends BaseFragment<FGGDProjectPresenter> implements FGGDProjectContract.View {

    @BindView(R.id.filter_edit)
    ClearEditText mFilterEdit;
    @BindView(R.id.recylerview)
    RecyclerView mRecylerview;
    @BindView(R.id.charIndexView)
    CharIndexView mCharIndexView;
    @BindView(R.id.tv_index)
    TextView mTvIndex;
    Unbinder unbinder;
    @Inject
    PinyinComparator mComparator;

    List<BaseProjectMessage> mDateList = new ArrayList<>();
    List<BaseProjectMessage> mDateList_state = new ArrayList<>();

    SortAdapter mAdapter;
    private ProjectFGGD chosedProject;
    private AlertDialog mSportDialog;

    public static FGGDProjectFragment newInstance() {
        FGGDProjectFragment fragment = new FGGDProjectFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerFGGDProjectComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fggdproject, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        // mAdapter.notifyDataSetChanged();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        LogUtils.d("initData");
        mSportDialog = new SpotsDialog.Builder().setContext(getActivity()).setCancelable(true).build();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        TitleItemDecoration mDecoration = new TitleItemDecoration(mContext, mDateList);
        mRecylerview.addItemDecoration(mDecoration);
        mRecylerview.setLayoutManager(manager);
        mRecylerview.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mAdapter = new SortAdapter(R.layout.project_item_layout, mDateList);
        mRecylerview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LogUtils.d(mDateList.get(position));
                //选中状态处理
                checkProject(position);


            }
        });
        mFilterEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LogUtils.d("onTextChanged" + s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                LogUtils.d("beforeTextChanged" + s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                LogUtils.d("afterTextChanged" + s.toString());
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                if (s.toString().isEmpty()) {
                    if (lastCheck!=-1){
                        mDateList.get(lastCheck).check=false;
                        lastCheck=-1;
                    }
                    mDateList.clear();
                    mDateList.addAll(mDateList_state);
                    mAdapter.notifyDataSetChanged();
                } else {
                    filterData_fggd(s.toString());
                }
            }
        });
        mCharIndexView.setSelected(false);
        mCharIndexView.setOnCharIndexChangedListener(new CharIndexView.OnCharIndexChangedListener() {
            @Override
            public void onCharIndexChanged(char currentIndex) {

            }

            @Override
            public void onCharIndexSelected(String currentIndex) {
                LogUtils.d(currentIndex);
                if (currentIndex == null) {
                    mTvIndex.setVisibility(View.INVISIBLE);
                } else {
                    mTvIndex.setVisibility(View.VISIBLE);
                    mTvIndex.setText(currentIndex);
                    //该字母首次出现的位置
                    int position = mAdapter.getPositionForSection(currentIndex.charAt(0));
                    if (position != -1) {
                        manager.scrollToPositionWithOffset(position, 0);
                    }


                }

            }
        });
    }

   public int lastCheck = -1;

    private void checkProject(int position) {
        mDateList.get(position).check = true;
        LogUtils.d(lastCheck + "   " + position);
        if (lastCheck != -1) {
            if (position == lastCheck) {
                return;
            }
            mDateList.get(lastCheck).check = false;
            mAdapter.notifyItemChanged(lastCheck);
        }

        mAdapter.notifyItemChanged(position);
        lastCheck = position;

        EventBus.getDefault().post(new FGGDProjectFragmentEvent(position, (ProjectFGGD) mDateList.get(position)));
    }

    private void filterData_fggd(String filterStr) {
        List<BaseProjectMessage> filterDateList = new ArrayList<>();

        filterDateList.clear();
        for (BaseProjectMessage sortModel : mDateList) {
            String name = sortModel.getPjName();
            if (name.indexOf(filterStr.toString()) != -1 ||
                    PinyinUtils.getFirstSpell(name).startsWith(filterStr.toString())
                    //不区分大小写
                    || PinyinUtils.getFirstSpell(name).toLowerCase().startsWith(filterStr.toString())
                    || PinyinUtils.getFirstSpell(name).toUpperCase().startsWith(filterStr.toString())
            ) {
                filterDateList.add(sortModel);
            }
        }
        if (lastCheck!=-1){
            mDateList.get(lastCheck).check=false;
            lastCheck=-1;
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, mComparator);
        mDateList.clear();
        mDateList.addAll(filterDateList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setData(@Nullable Object data) {
        List<BaseProjectMessage> data1 = (List<BaseProjectMessage>) data;
        mDateList.clear();
        mDateList.addAll(data1);
        mDateList_state.clear();
        mDateList_state.addAll(data1);
        LogUtils.d(mDateList);
        if (null != mAdapter) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showLoading() {
        if (!mSportDialog.isShowing()) {
            LogUtils.d("show");
            mSportDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mSportDialog.isShowing()) {
            LogUtils.d("hide");
            mSportDialog.dismiss();
        }
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
        LogUtils.d("onCreateView");

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.d("onDestroyView");
        unbinder.unbind();
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
}