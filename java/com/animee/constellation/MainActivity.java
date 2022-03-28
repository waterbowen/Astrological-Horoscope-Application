package com.animee.constellation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.RadioGroup;

import com.animee.constellation.bean.StarBean;
import com.animee.constellation.luckfrag.LuckFragment;
import com.animee.constellation.mefrag.MeFragment;
import com.animee.constellation.parnterfrag.PartnerFragment;
import com.animee.constellation.starfrag.StarFragment;
import com.animee.constellation.utils.AssetsUtils;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    RadioGroup mainRg;

    Fragment starFrag,luckFrag,partnerFrag,meFrag;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainRg = findViewById(R.id.main_rg);
        mainRg.setOnCheckedChangeListener(this);

        StarBean infoBean = loadData();
        Bundle bundle = new Bundle();
        bundle.putSerializable("info",infoBean);
        System.out.println("oncreate");
        starFrag = new StarFragment();
        starFrag.setArguments(bundle);
        luckFrag = new LuckFragment();
        luckFrag.setArguments(bundle);
        partnerFrag = new PartnerFragment();
        partnerFrag.setArguments(bundle);
        meFrag = new MeFragment();
        meFrag.setArguments(bundle);
        addFragmentPage();
    }

    private StarBean loadData() {
        String json = AssetsUtils.getJsonFromAssets(this, "xzcontent/xzcontent.json");
        Gson gson = new Gson();
        StarBean infoBean = gson.fromJson(json, StarBean.class);
        AssetsUtils.saveBitmapFromAssets(this,infoBean);
        return infoBean;
    }

    private void addFragmentPage() {
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.main_layout_center,starFrag);
        transaction.add(R.id.main_layout_center,partnerFrag);
        transaction.add(R.id.main_layout_center,luckFrag);
        transaction.add(R.id.main_layout_center,meFrag);
        transaction.hide(partnerFrag);
        transaction.hide(luckFrag);
        transaction.hide(meFrag);
        transaction.commit();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction transaction = manager.beginTransaction();
        switch (checkedId) {
            case R.id.main_rb_star:
                transaction.hide(partnerFrag);
                transaction.hide(luckFrag);
                transaction.hide(meFrag);
                transaction.show(starFrag);
                break;
            case R.id.main_rb_partner:
                transaction.hide(starFrag);
                transaction.hide(luckFrag);
                transaction.hide(meFrag);
                transaction.show(partnerFrag);
                break;
            case R.id.main_rb_luck:
                transaction.hide(starFrag);
                transaction.hide(partnerFrag);
                transaction.hide(meFrag);
                transaction.show(luckFrag);
                break;
            case R.id.main_rb_me:
                transaction.hide(starFrag);
                transaction.hide(luckFrag);
                transaction.hide(partnerFrag);
                transaction.show(meFrag);
                break;
        }
        transaction.commit();
    }
}
