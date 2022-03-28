package com.animee.constellation.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.animee.constellation.bean.StarBean;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* 读取Assets文件夹当中数据的工具类
* */
public class AssetsUtils {

    private static Map<String,Bitmap>logoImgMap;
    private static Map<String,Bitmap>contentlogoImgMap;
    public static String getJsonFromAssets(Context context, String filename){
        AssetManager am = context.getResources().getAssets();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
            InputStream is = am.open(filename);
            int hasRead = 0;
            byte[]buf = new byte[1024];
            while (true){
                hasRead = is.read(buf);
                if (hasRead== -1) {
                    break;
                }
                baos.write(buf,0,hasRead);
            }
            String msg = baos.toString();
            is.close();
            return msg;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public static Bitmap getBitmapFromAssets(Context context,String filename){
        Bitmap bitmap = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(filename);
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }

    public static void saveBitmapFromAssets(Context context, StarBean starInfoBean){
        logoImgMap = new HashMap<>();
        contentlogoImgMap = new HashMap<>();
        List<StarBean.StarinfoBean> starList = starInfoBean.getStarinfo();
        for (int i = 0; starList.size() > i; i++) {
            String logoname = starList.get(i).getLogoname();
            String filename = "xzlogo/"+logoname+".png";
            Bitmap logoBm = getBitmapFromAssets(context, filename);
            logoImgMap.put(logoname,logoBm);

            String contentName = "xzcontentlogo/"+logoname+".png";
            Bitmap bitmap = getBitmapFromAssets(context, contentName);
            contentlogoImgMap.put(logoname,bitmap);
        }
    }

    public static Map<String, Bitmap> getLogoImgMap() {
        return logoImgMap;
    }

    public static Map<String, Bitmap> getContentlogoImgMap() {
        return contentlogoImgMap;
    }
}
