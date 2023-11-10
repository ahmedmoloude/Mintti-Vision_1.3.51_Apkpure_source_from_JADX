package com.p020kl.healthmonitor.measure.rothmanindex;

import android.widget.RadioGroup;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.p020kl.healthmonitor.C1624R;
import java.util.List;

/* renamed from: com.kl.healthmonitor.measure.rothmanindex.QuestionsAdapter */
public class QuestionsAdapter extends BaseQuickAdapter<AssessmentQuestionBean, BaseViewHolder> {
    List<AssessmentQuestionBean> questionBeanList;

    public QuestionsAdapter(List<AssessmentQuestionBean> list) {
        super(C1624R.layout.view_self_assessment_question, list);
        this.questionBeanList = list;
    }

    /* access modifiers changed from: protected */
    public void convert(final BaseViewHolder baseViewHolder, AssessmentQuestionBean assessmentQuestionBean) {
        baseViewHolder.setText((int) C1624R.C1628id.tv_question_index, (CharSequence) assessmentQuestionBean.getQuestionIndex());
        baseViewHolder.setText((int) C1624R.C1628id.tv_question, (CharSequence) assessmentQuestionBean.getQuestionText());
        RadioGroup radioGroup = (RadioGroup) baseViewHolder.getView(C1624R.C1628id.rg_question_result);
        if (assessmentQuestionBean.isSelected()) {
            baseViewHolder.setChecked(C1624R.C1628id.rb_yes, true);
        } else {
            baseViewHolder.setChecked(C1624R.C1628id.rb_no, true);
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == C1624R.C1628id.rb_yes) {
                    ((AssessmentQuestionBean) QuestionsAdapter.this.getData().get(baseViewHolder.getAdapterPosition())).setSelected(true);
                } else {
                    ((AssessmentQuestionBean) QuestionsAdapter.this.getData().get(baseViewHolder.getAdapterPosition())).setSelected(false);
                }
            }
        });
    }
}
