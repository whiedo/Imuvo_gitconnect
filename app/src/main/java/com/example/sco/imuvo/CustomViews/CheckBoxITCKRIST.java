package com.example.sco.imuvo.CustomViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckBox;

public class CheckBoxITCKRIST extends CheckBox {
    public CheckBoxITCKRIST(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public CheckBoxITCKRIST(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public CheckBoxITCKRIST(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = Typeface.createFromAsset(context.getAssets(),"Fonts/ITCKRIST.TTF");
        setTypeface(customFont);

    }
}
