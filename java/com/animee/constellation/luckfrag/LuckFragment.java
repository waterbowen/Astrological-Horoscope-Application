package com.animee.constellation.luckfrag;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.animee.constellation.R;
import com.animee.constellation.bean.StarBean;

import java.util.List;

/**
 * 运势界面
 */
public class LuckFragment extends Fragment {
    GridView luckGv;
    List<StarBean.StarinfoBean> mDatas;
    private LuckBaseAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_luck, container, false);
        luckGv = view.findViewById(R.id.luckfrag_gv);
        Bundle bundle = getArguments();
        StarBean infobean = (StarBean) bundle.getSerializable("info");
        mDatas = infobean.getStarinfo();
        adapter = new LuckBaseAdapter(getContext(), mDatas);
        luckGv.setAdapter(adapter);
        setListener();
        return view;
    }

    private void setListener() {
        luckGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StarBean.StarinfoBean bean = mDatas.get(position);
                String name = bean.getName();
                Intent intent = new Intent(getContext(), LuckAnalysisActivity.class);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });
    }
}
