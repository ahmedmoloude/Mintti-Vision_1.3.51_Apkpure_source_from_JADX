package com.p020kl.commonbase.views.countrypicker;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.p020kl.commonbase.C1544R;

/* renamed from: com.kl.commonbase.views.countrypicker.VH */
class C1619VH extends RecyclerView.ViewHolder {
    TextView tvCode;
    TextView tvName;

    C1619VH(View view) {
        super(view);
        this.tvName = (TextView) view.findViewById(C1544R.C1548id.tv_name);
        this.tvCode = (TextView) view.findViewById(C1544R.C1548id.tv_code);
    }
}
