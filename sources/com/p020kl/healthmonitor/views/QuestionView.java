package com.p020kl.healthmonitor.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.p020kl.healthmonitor.C1624R;

/* renamed from: com.kl.healthmonitor.views.QuestionView */
public class QuestionView extends FrameLayout {
    private RadioGroup radioGroup;
    private RadioButton rbYes;
    private TextView tvQuestionIndex;
    private TextView tvQuestionText;

    public QuestionView(Context context) {
        this(context, (AttributeSet) null);
    }

    public QuestionView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public QuestionView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initViews(context, attributeSet);
    }

    private void initViews(Context context, AttributeSet attributeSet) {
        LayoutInflater.from(context).inflate(C1624R.layout.view_self_assessment_question, this);
        this.tvQuestionText = (TextView) findViewById(C1624R.C1628id.tv_question);
        this.tvQuestionIndex = (TextView) findViewById(C1624R.C1628id.tv_question_index);
        this.rbYes = (RadioButton) findViewById(C1624R.C1628id.rb_yes);
        this.radioGroup = (RadioGroup) findViewById(C1624R.C1628id.rg_question_result);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1624R.styleable.QuestionView);
        String string = obtainStyledAttributes.getString(0);
        String string2 = obtainStyledAttributes.getString(1);
        this.tvQuestionIndex.setText(string);
        this.tvQuestionText.setText(string2);
        obtainStyledAttributes.recycle();
    }

    public void setTvQuestionIndex(String str) {
        this.tvQuestionIndex.setText(str);
    }

    public void setTvQuestionText(String str) {
        this.tvQuestionText.setText(str);
    }

    public boolean isSelected() {
        return this.rbYes.isChecked();
    }

    public void setRadioButtonClickListener(RadioGroup.OnCheckedChangeListener onCheckedChangeListener) {
        this.radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
    }
}
