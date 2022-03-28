package com.animee.constellation.starfrag;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.animee.constellation.R;
import com.animee.constellation.bean.StarBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 星座Fragment
 *      包括ViewPager和GridView
 */
public class StarFragment extends Fragment {
    ViewPager starVp;
    GridView starGv;
    LinearLayout pointLayout;
    private List<StarBean.StarinfoBean> mDatas;
    private StarBaseAdapter starBaseAdapter;
    int[]imgIds = {R.mipmap.pic_guanggao,R.mipmap.pic_star};
    List<ImageView>ivList;
    List<ImageView>pointList;
    private StarPagerAdapter starPagerAdapter;
    Handler handler = new Handler(){
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                int currentItem = starVp.getCurrentItem();
                if (currentItem == ivList.size()-1) {
                    starVp.setCurrentItem(0);
                }else {
                    currentItem++;
                    starVp.setCurrentItem(currentItem);
                }
                handler.sendEmptyMessageDelayed(1,5000);
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_star, container, false);
        initView(view);
        Bundle bundle = getArguments();
        StarBean infoBean = (StarBean) bundle.getSerializable("info");
        mDatas = infoBean.getStarinfo();

        starBaseAdapter = new StarBaseAdapter(getContext(), mDatas);
        starGv.setAdapter(starBaseAdapter);
        initPager();
        setVPListener();
        setGVListener();
        handler.sendEmptyMessageDelayed(1,5000);
        return view;
    }

    private void setGVListener() {
        starGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StarBean.StarinfoBean bean = mDatas.get(position);
                Intent intent = new Intent(getContext(), StarAnalysisActivity.class);
                intent.putExtra("star",bean);
                startActivity(intent);
            }
        });
    }


    private void initPager() {
        ivList = new ArrayList<>();
        pointList = new ArrayList<>();
        for (int i = 0; i < imgIds.length; i++) {
            ImageView iv = new ImageView(getContext());
            iv.setImageResource(imgIds[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            iv.setLayoutParams(lp);

            ivList.add(iv);

            ImageView piv = new ImageView(getContext());
            piv.setImageResource(R.mipmap.point_normal);
            LinearLayout.LayoutParams plp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            plp.setMargins(20,0,0,0);
            piv.setLayoutParams(plp);
            pointLayout.addView(piv);
            pointList.add(piv);
        }
        pointList.get(0).setImageResource(R.mipmap.point_focus);
        starPagerAdapter = new StarPagerAdapter(getContext(), ivList);
        starVp.setAdapter(starPagerAdapter);
    }

    private void setVPListener() {
        starVp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < pointList.size(); i++) {
                    pointList.get(i).setImageResource(R.mipmap.point_normal);
                }
                pointList.get(position).setImageResource(R.mipmap.point_focus);
            }
        });
    }

    private void initView(View view) {
        starVp = view.findViewById(R.id.starfrag_vp);
        starGv = view.findViewById(R.id.starfrag_gv);
        pointLayout = view.findViewById(R.id.starfrag_layout);
    }
}
