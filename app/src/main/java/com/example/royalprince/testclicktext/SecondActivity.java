package com.example.royalprince.testclicktext;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecondActivity extends AppCompatActivity {

    private TextView mNum;
    private Context context;
    private String numbers;
    private String phone;
    List<String> strList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        context = this;
        mNum = findViewById(R.id.tv_num);
        String str = "配送至北京市海淀区某个地方，配送员电话：1008611，投诉电话：12345678；请同意如下协议";

        for (int i = 0; i < 10; i++) {
            strList.add("《这个是协议" + i + "》");
            str = str + strList.get(i);
        }
        initText(str);
    }

    private void initText(String str) {
        if (getNumbers(str).size() > 0) {//数组长度大于0，取数组第一组数据get(0)
            numbers = getNumbers(str).get(0);
        }
        if (getNumbers(str).size() > 1) {//数组长度大1，取数组第二组get(1)
            phone = getNumbers(str).get(1);
        }

        SpannableString spannableInfo = new SpannableString(str);
        spannableInfo.setSpan(new Clickable(clickListener), str.indexOf(numbers), str.indexOf(numbers) + numbers.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableInfo.setSpan(new Clickable(click), str.indexOf(phone), str.indexOf(phone) + phone.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        for (int i = 0; i < strList.size(); i++) {
            final int finalI = i;
            View.OnClickListener listClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(SecondActivity.this, "点击了协议" + strList.get(finalI), Toast.LENGTH_SHORT).show();
                }
            };
            spannableInfo.setSpan(new Clickable(listClickListener), str.indexOf(strList.get(i)), str.indexOf(strList.get(i)) + strList.get(i).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        mNum.setText(spannableInfo);//将处理过得数据set到View里
        mNum.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * 判断字符串正则
     *
     * @param content
     * @return
     */
    private List<String> getNumbers(String content) {
        List<String> digitList = new ArrayList<>();
        Pattern p = Pattern.compile("(\\d{5,})");//正则：大于5位的纯数字
        Matcher m = p.matcher(content);
        while (m.find()) {
            String find = m.group(1).toString();
            digitList.add(find);
        }
        return digitList;
    }

    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getClick(phone);
        }
    };

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getClick(numbers);
        }
    };

    private View.OnClickListener listClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(SecondActivity.this, "点击了协议", Toast.LENGTH_SHORT).show();
        }
    };

    private void getClick(final String s) {//参数为当前点击的数字字符串
        new AlertDialog.Builder(context)
                .setTitle(s)
                .setItems(new String[]{"拨打电话", "复制文本"},
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0://跳转拨号
                                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + s.replaceAll("-", "")));
                                        startActivity(intent);
                                        break;
                                    case 1:
                                        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                        cmb.setText(s.trim());
                                        break;
                                }
                            }
                        }
                )
                .show();
    }

    class Clickable extends ClickableSpan {

        private final View.OnClickListener mListener;

        public Clickable(View.OnClickListener mListener) {
            this.mListener = mListener;
        }

        @Override
        public void onClick(View widget) {
            mListener.onClick(widget);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);//设置文字下划线不显示
            ds.setColor(getResources().getColor(R.color.colorAccent));//设置字体颜色

        }
    }
}
