package com.baoyachi.stepview.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huangdiudiu on 2018/4/26.
 */

public class SpannableStringUtil {

    public static SpannableString zhuanHuanTelUrl(final Context context, final String strTel){
        SpannableString ss = new SpannableString(strTel);
        final List<String> list = getNumbers(strTel);
        Log.e("backinfo","长度："+list.size());
        if(list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                final int finalI = i;
                ss.setSpan(new ClickableSpan() {
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(Color.BLUE);       //设置文件颜色
                        ds.setUnderlineText(true);      //设置下划线
                    }

                    @SuppressLint("MissingPermission")
                    @Override
                    public void onClick(View widget) {
                        String tel = list.get(finalI);
                        Intent dialIntent =  new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + tel));//跳转到拨号界面，同时传递电话号码
                        context.startActivity(dialIntent);
                    }
                },strTel.indexOf(list.get(i)), strTel.indexOf(list.get(i))+11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

        }
        return ss;
    }

    /**
     * 将字符串中的电话号码设置点击事件和下划线
     * @param context
     * @param tv
     * @param strTel
     */
    public static void setTelUrl(Context context, TextView tv, String strTel){
        zhuanHuanTelUrl(context,strTel);
        tv.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
        tv.setText(zhuanHuanTelUrl(context,strTel));
        tv.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
    }


    /**
     * 从字符串中查找电话号码字符串
     */
    public static List<String> getNumbers(String content) {
        List<String> digitList = new ArrayList<String>();
        Pattern p = Pattern.compile("(\\d{11})");
        Matcher m = p.matcher(content);
        while (m.find()) {
            String find = m.group(1).toString();
            digitList.add(find);
        }
        return digitList;
    }
}
