package com.p020kl.commonbase.views;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import androidx.fragment.app.DialogFragment;
import com.p020kl.commonbase.C1544R;
import java.util.Objects;

/* renamed from: com.kl.commonbase.views.ConnectWayDialogFragmnet */
public class ConnectWayDialogFragmnet extends DialogFragment implements View.OnClickListener {
    private Button againBtn;
    private Button cancelBtn;
    private Button handBtn;
    private OnDialogItemClickListener itemClickListener;

    /* renamed from: com.kl.commonbase.views.ConnectWayDialogFragmnet$OnDialogItemClickListener */
    public interface OnDialogItemClickListener {
        void onAgain();

        void onHand();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setStyle(2, C1544R.C1551style.Theme_AppCompat_Light_Dialog);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        super.onCreateView(layoutInflater, viewGroup, bundle);
        return layoutInflater.inflate(C1544R.layout.fragment_dialog_connect_way, (ViewGroup) null);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.cancelBtn = (Button) view.findViewById(C1544R.C1548id.btn_cancel);
        this.againBtn = (Button) view.findViewById(C1544R.C1548id.btn_again);
        this.handBtn = (Button) view.findViewById(C1544R.C1548id.btn_hand);
        this.cancelBtn.setOnClickListener(this);
        this.againBtn.setOnClickListener(this);
        this.handBtn.setOnClickListener(this);
    }

    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        Objects.requireNonNull(window);
        window.setBackgroundDrawable(new ColorDrawable(0));
    }

    public void onClick(View view) {
        if (view.getId() == C1544R.C1548id.btn_cancel) {
            getDialog().dismiss();
        } else if (view.getId() == C1544R.C1548id.btn_again) {
            OnDialogItemClickListener onDialogItemClickListener = this.itemClickListener;
            if (onDialogItemClickListener != null) {
                onDialogItemClickListener.onAgain();
            }
            dismiss();
        } else if (view.getId() == C1544R.C1548id.btn_hand) {
            OnDialogItemClickListener onDialogItemClickListener2 = this.itemClickListener;
            if (onDialogItemClickListener2 != null) {
                onDialogItemClickListener2.onHand();
            }
            dismiss();
        }
    }

    public void setOnDialogItemClickListener(OnDialogItemClickListener onDialogItemClickListener) {
        this.itemClickListener = onDialogItemClickListener;
    }
}
