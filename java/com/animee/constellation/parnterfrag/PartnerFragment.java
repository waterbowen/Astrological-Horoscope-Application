package com.animee.constellation.parnterfrag;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.animee.constellation.R;
import com.animee.constellation.bean.StarBean;
import com.animee.constellation.utils.AssetsUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class PartnerFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    ImageView manIv,womanIv;
    Spinner manSp,womanSp;
    Button prizeBtn,analysisBtn;
    private List<StarBean.StarinfoBean> infoList;
    List<String>nameList;
    private Map<String, Bitmap> logoImgMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_partner, container, false);
        initView(view);
        Bundle bundle = getArguments();
        StarBean starBean = (StarBean) bundle.getSerializable("info");
        infoList = starBean.getStarinfo();
        nameList = new ArrayList<>();
        for (int i = 0; i < infoList.size(); i++) {
            String name = infoList.get(i).getName();
            nameList.add(name);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.item_parnter_sp, R.id.item_parnter_tv, nameList);
        manSp.setAdapter(adapter);
        womanSp.setAdapter(adapter);
        String logoname = infoList.get(0).getLogoname();

        logoImgMap = AssetsUtils.getContentlogoImgMap();
        Bitmap bitmap = logoImgMap.get(logoname);
        manIv.setImageBitmap(bitmap);
        womanIv.setImageBitmap(bitmap);
        return view;
    }

    private void initView(View view) {
        manIv = view.findViewById(R.id.parnterfrag_iv_man);
        womanIv = view.findViewById(R.id.parnterfrag_iv_woman);
        manSp = view.findViewById(R.id.parnterfrag_sp_man);
        womanSp = view.findViewById(R.id.parnterfrag_sp_woman);
        prizeBtn = view.findViewById(R.id.parnterfrag_btn_prize);
        analysisBtn = view.findViewById(R.id.parnterfrag_btn_analysis);
        prizeBtn.setOnClickListener(this);
        analysisBtn.setOnClickListener(this);
        manSp.setOnItemSelectedListener(this);
        womanSp.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.parnterfrag_btn_prize:

                break;
            case R.id.parnterfrag_btn_analysis:
                int manSelPos = manSp.getSelectedItemPosition();
                int womanSelPos = womanSp.getSelectedItemPosition();
                Intent intent = new Intent(getContext(), ParnterAnalysisActivity.class);
                intent.putExtra("man_name",infoList.get(manSelPos).getName());
                intent.putExtra("man_logoname",infoList.get(manSelPos).getLogoname());
                intent.putExtra("woman_name",infoList.get(womanSelPos).getName());
                intent.putExtra("woman_logoname",infoList.get(womanSelPos).getLogoname());
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.parnterfrag_sp_man:
                String logoname = infoList.get(position).getLogoname();
                Bitmap bitmap = logoImgMap.get(logoname);
                manIv.setImageBitmap(bitmap);
                break;
            case R.id.parnterfrag_sp_woman:
                logoname = infoList.get(position).getLogoname();
                bitmap = logoImgMap.get(logoname);
                womanIv.setImageBitmap(bitmap);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
