package com.p020kl.healthmonitor.measure.rothmanindex;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.p020kl.commonbase.base.BaseApplication;
import com.p020kl.commonbase.base.BaseFragmentWhiteToolbar;
import com.p020kl.commonbase.bean.rothmanindex.AssessmentBean;
import com.p020kl.commonbase.utils.DateUtils;
import com.p020kl.commonbase.utils.SizeUtils;
import com.p020kl.healthmonitor.C1624R;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.kl.healthmonitor.measure.rothmanindex.SelfAssessmentFragment */
public class SelfAssessmentFragment extends BaseFragmentWhiteToolbar implements View.OnClickListener {
    List<AssessmentQuestionBean> questionBeanList = new ArrayList();
    QuestionsAdapter questionsAdapter;
    @BindView(3583)
    RecyclerView rvQuestions;

    /* access modifiers changed from: protected */
    public boolean isShowBack() {
        return true;
    }

    public static SelfAssessmentFragment newInstance() {
        Bundle bundle = new Bundle();
        SelfAssessmentFragment selfAssessmentFragment = new SelfAssessmentFragment();
        selfAssessmentFragment.setArguments(bundle);
        return selfAssessmentFragment;
    }

    public Object setLayout() {
        return Integer.valueOf(C1624R.layout.fragment_self_assessment);
    }

    /* access modifiers changed from: protected */
    public Object setToolbarTitle() {
        return Integer.valueOf(C1624R.string.self_assessment);
    }

    public void onBindView(Bundle bundle, View view) {
        this.questionBeanList.clear();
        String[] stringArray = getResources().getStringArray(C1624R.array.self_assessment_questions);
        int i = 0;
        while (i < stringArray.length) {
            AssessmentQuestionBean assessmentQuestionBean = new AssessmentQuestionBean();
            StringBuilder sb = new StringBuilder();
            int i2 = i + 1;
            sb.append(i2);
            sb.append(".");
            assessmentQuestionBean.setQuestionIndex(sb.toString());
            assessmentQuestionBean.setQuestionText(stringArray[i]);
            assessmentQuestionBean.setSelected(false);
            this.questionBeanList.add(assessmentQuestionBean);
            i = i2;
        }
        this.questionsAdapter = new QuestionsAdapter(this.questionBeanList);
        this.rvQuestions.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.rvQuestions.setAdapter(this.questionsAdapter);
        Button button = new Button(getContext());
        button.setText(C1624R.string.next_step);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, SizeUtils.dp2px(50.0f));
        int dp2px = SizeUtils.dp2px(30.0f);
        layoutParams.setMargins(dp2px, dp2px, dp2px, dp2px);
        button.setBackgroundResource(C1624R.C1627drawable.button_circle_shape_selector);
        button.setTextSize(18.0f);
        button.setTextColor(getResources().getColor(C1624R.C1626color.white));
        button.setLayoutParams(layoutParams);
        button.setOnClickListener(this);
        button.setAllCaps(false);
        this.questionsAdapter.addFooterView(button);
    }

    public void onClick(View view) {
        AssessmentBean assessmentBean = new AssessmentBean();
        assessmentBean.setCardiacMet(!this.questionBeanList.get(0).isSelected());
        assessmentBean.setFoodMet(!this.questionBeanList.get(1).isSelected());
        assessmentBean.setGastrointestinalMet(!this.questionBeanList.get(2).isSelected());
        assessmentBean.setGenitourinaryMet(!this.questionBeanList.get(3).isSelected());
        assessmentBean.setBowelIncontinenceMet(!this.questionBeanList.get(4).isSelected());
        assessmentBean.setUrinaryIncontinenceMet(!this.questionBeanList.get(5).isSelected());
        assessmentBean.setMusculoSkeletalMet(!this.questionBeanList.get(6).isSelected());
        assessmentBean.setNeurologicalMet(!this.questionBeanList.get(7).isSelected());
        assessmentBean.setCognitiveMet(!this.questionBeanList.get(8).isSelected());
        assessmentBean.setPeripheralVascularMet(!this.questionBeanList.get(9).isSelected());
        assessmentBean.setSkinMet(!this.questionBeanList.get(10).isSelected());
        assessmentBean.setPsychosocialMet(!this.questionBeanList.get(11).isSelected());
        assessmentBean.setRespiratoryMet(!this.questionBeanList.get(12).isSelected());
        assessmentBean.setSafetyMet(!this.questionBeanList.get(13).isSelected());
        assessmentBean.setAssessedBy(BaseApplication.getInstance().getRothmanIndexUuid());
        assessmentBean.setDateAssessed(DateUtils.getCreateDate());
        start(ObservationsFragment.newInstance(assessmentBean));
    }
}
