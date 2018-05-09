package com.example.royalprince.testclicktext;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.hello_word);

        textView.setHighlightColor(getResources().getColor(android.R.color.transparent));

        SpannableString spannableInfo = new SpannableString("这是一个测试" + "点击我....");

        spannableInfo.setSpan(new Clickable(clickListener),8,11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(spannableInfo);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this, "点击成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,SecondActivity.class);
            startActivity(intent);
        }
    };


    class Clickable extends ClickableSpan {
        private final OnClickListener mListener;

        public Clickable(OnClickListener mListener) {
            this.mListener = mListener;
        }

        @Override
        public void onClick(View widget) {
            mListener.onClick(widget);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(getResources().getColor(R.color.colorAccent));
        }
    }
}
