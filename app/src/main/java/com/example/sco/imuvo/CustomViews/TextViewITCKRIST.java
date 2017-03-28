package com.example.sco.imuvo.CustomViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewITCKRIST extends TextView {


    public TextViewITCKRIST(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public TextViewITCKRIST(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public TextViewITCKRIST(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = Typeface.createFromAsset(context.getAssets(),"Fonts/ITCKRIST.TTF");
        setTypeface(customFont);
    }



}
