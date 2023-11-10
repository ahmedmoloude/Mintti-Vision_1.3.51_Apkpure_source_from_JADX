package com.p020kl.healthmonitor.measure.rothmanindex;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.p020kl.healthmonitor.C1624R;

/* renamed from: com.kl.healthmonitor.measure.rothmanindex.SelfAssessmentFragment_ViewBinding */
public class SelfAssessmentFragment_ViewBinding implements Unbinder {
    private SelfAssessmentFragment target;

    public SelfAssessmentFragment_ViewBinding(SelfAssessmentFragment selfAssessmentFragment, View view) {
        this.target = selfAssessmentFragment;
        selfAssessmentFragment.rvQuestions = (RecyclerView) Utils.findRequiredViewAsType(view, C1624R.C1628id.rv_questions, "field 'rvQuestions'", RecyclerView.class);
    }

    public void unbind() {
        SelfAssessmentFragment selfAssessmentFragment = this.target;
        if (selfAssessmentFragment != null) {
            this.target = null;
            selfAssessmentFragment.rvQuestions = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
