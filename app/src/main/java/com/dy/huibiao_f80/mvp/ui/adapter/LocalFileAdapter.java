package com.dy.huibiao_f80.mvp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.mvp.ui.activity.ShowPDFActivity;

import java.io.File;
import java.util.List;

/**
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃    神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * <p>
 * Created by wangzhenxiong on 2019/2/27.
 */
public class LocalFileAdapter extends BaseQuickAdapter<File, BaseViewHolder> {
    private String mTag;
    private Context mContext;
    public LocalFileAdapter(List<File> infos, Context context, String tag) {
        super(R.layout.recycle_list,infos);
        mTag=tag;
        mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, File item) {
     switch (mTag){
         case "video":
             helper.setBackgroundRes(R.id.imageView,R.drawable.video_play_background).setText(R.id.filename,item.getName());

             break;
         case "step":
             helper.setBackgroundRes(R.id.imageView,R.drawable.pdf).setText(R.id.filename,item.getName());
             break;
         case "stand":
             helper.setBackgroundRes(R.id.imageView,R.drawable.pdf).setText(R.id.filename,item.getName());
             break;
     }
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if ("video".equals(mTag)){
                  Intent intent = new Intent(Intent.ACTION_VIEW);
                  String bpath = "file://" + item.getPath();
                  intent.setDataAndType(Uri.parse(bpath), "video/*");
                  mContext.startActivity(intent);
              }else {
                  Intent intent = new Intent(mContext, ShowPDFActivity.class);
                  intent.putExtra("where",1+"");
                  intent.putExtra("type", "1");
                  intent.putExtra("data", item.getAbsolutePath());
                  mContext.startActivity(intent);
              }
            }
        });
    }



   
}
