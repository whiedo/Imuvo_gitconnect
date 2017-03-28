package com.example.sco.imuvo.CustomViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewJokerman extends TextView {
    public TextViewJokerman(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public TextViewJokerman(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public TextViewJokerman(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }
    private void applyCustomFont(Context context) {
        Typeface customFont = Typeface.createFromAsset(context.getAssets(),"Fonts/Jokerman-Regular.ttf");
        setTypeface(customFont);
        setTextSize(20);
    }



}
