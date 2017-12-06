package com.example.bei.duoduo;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final Locale Locale_Vietnam= new Locale("vi", "VN");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void select(View v) {
        AlertDialog.Builder builder = new Builder(this);
        builder.setTitle("请选择您的语言");
        final String items[] = {"中国", "美国","越南"};
        builder.setSingleChoiceItems(items, -1, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String item = items[which];
                if (item.equals("中国")) {
                    switchLanguage("zh_simple");
                } else if (item.equals("美国")) {
                    switchLanguage("en");
                }else if (item.equals("越南")) {
                    switchLanguage("vi-VN");
                }

                dialog.dismiss();
            }
        });

        builder.show();

    }

    public void second(View v) {
        startActivity(new Intent(MainActivity.this, SecondActivity.class));
    }


    /**
     * 切换语言
     *
     * @param language
     */

    private void switchLanguage(String language) {

        //设置应用语言类型

        Resources resources = getResources();

        Configuration config = resources.getConfiguration();

        DisplayMetrics dm = resources.getDisplayMetrics();

        if (language.equals("zh_simple")) {

            config.locale = Locale.SIMPLIFIED_CHINESE;

        } else if (language.equals("en")) {

            config.locale = Locale.ENGLISH;

        } else if (language.equals("vi-VN")) {

            config.locale = Locale_Vietnam;

        }else {

            config.locale = Locale.getDefault();

        }

        resources.updateConfiguration(config, dm);

        //保存设置语言的类型

        SharedPreferences sharedPreferences = getSharedPreferences("language", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("language", language);
        editor.commit();

        //更新语言后，destroy当前页面，重新绘制

        finish();

        Intent it = new Intent(MainActivity.this, MainActivity.class);

        //清空任务栈确保当前打开activit为前台任务栈栈顶

        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(it);

    }

}
