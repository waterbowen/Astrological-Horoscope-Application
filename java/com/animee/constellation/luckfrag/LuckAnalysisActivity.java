package com.animee.constellation.luckfrag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.animee.constellation.R;
import com.animee.constellation.utils.LoadDataAsyncTask;
import com.animee.constellation.utils.URLContent;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class LuckAnalysisActivity extends AppCompatActivity implements View.OnClickListener,LoadDataAsyncTask.OnGetNetDataListener {
    ListView luckLv;
    TextView nameTv;
    ImageView backIv;
    List<LuckItemBean>mDatas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luck_analysis);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");  //获取上一级界面传递的星座名词
//        get website
        String luckURL = URLContent.getLuckURL(name);
        initView(name);
        mDatas = new ArrayList<>();
//        get request
        LoadDataAsyncTask task = new LoadDataAsyncTask(this, this, true);
        task.execute(luckURL);
    }

    private void initView(String name) {
        luckLv = findViewById(R.id.luckanalysis_lv);
        nameTv = findViewById(R.id.title_tv);
        backIv = findViewById(R.id.title_iv_back);
        nameTv.setText(name);
        backIv.setOnClickListener(this);
    }
//  获取网络数据成功时会回调的方法
    @Override
    public void onSuccess(String json) {
        if (!TextUtils.isEmpty(json)) {
            LuckBean luckBean = new Gson().fromJson(json, LuckBean.class);
            addDataToList(luckBean);
            LuckAnalysisAdapter adapter = new LuckAnalysisAdapter(this, mDatas);
            luckLv.setAdapter(adapter);
        }
    }
    /* 整理数据到集合当中*/
    private void addDataToList(LuckBean luckBean) {
        LuckItemBean lib1 = new LuckItemBean("Comprehensive Lucky tendency",luckBean.getMima().getText().get(0), Color.BLUE);
        LuckItemBean lib2 = new LuckItemBean("RelationShip Lucky tendency",luckBean.getLove().get(0),Color.GREEN);
        LuckItemBean lib3 = new LuckItemBean("Career Lucky tendency",luckBean.getCareer().get(0),Color.GRAY);
        LuckItemBean lib4 = new LuckItemBean("Healthy Lucky tendency",luckBean.getHealth().get(0),Color.RED);
        LuckItemBean lib5 = new LuckItemBean("Fortune Lucky tendency",luckBean.getFinance().get(0),Color.BLACK);
        mDatas.add(lib1);
        mDatas.add(lib2);
        mDatas.add(lib3);
        mDatas.add(lib4);
        mDatas.add(lib5);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_iv_back:
                finish();
                break;
        }
    }

}
