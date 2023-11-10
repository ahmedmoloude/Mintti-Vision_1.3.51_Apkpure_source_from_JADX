package com.p020kl.commonbase.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import com.p020kl.commonbase.C1544R;
import com.p020kl.commonbase.bean.NoteEntity;
import com.p020kl.commonbase.utils.SizeUtils;
import com.p020kl.commonbase.utils.StringUtils;

/* renamed from: com.kl.commonbase.views.NoteDialog */
public class NoteDialog extends Dialog implements View.OnClickListener {
    private Button btnLeft;
    private Button btnRight;
    private EditText etAge;
    private EditText etName;
    private EditText etNotes;
    private String leftString;
    private Context mContext;
    private IOnClickListener mIOnClickListener;
    private NoteEntity noteEntity;
    private RadioGroup rgGender;
    private String rightString;

    /* renamed from: com.kl.commonbase.views.NoteDialog$IOnClickListener */
    public interface IOnClickListener {
        void onLeftClicked(NoteDialog noteDialog, NoteEntity noteEntity);

        void onRightClicked(NoteDialog noteDialog, NoteEntity noteEntity);
    }

    public void setIOnClickListener(IOnClickListener iOnClickListener) {
        this.mIOnClickListener = iOnClickListener;
    }

    public NoteDialog(Context context, String str, String str2) {
        this(context, 0, str, str2);
    }

    public NoteDialog(Context context, int i, String str, String str2) {
        super(context, i);
        this.mContext = context;
        this.leftString = str;
        this.rightString = str2;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        View inflate = LayoutInflater.from(this.mContext).inflate(C1544R.layout.common_dialog_note, (ViewGroup) null);
        setContentView(inflate);
        Window window = getWindow();
        setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = 17;
        attributes.width = SizeUtils.dp2px(300.0f);
        attributes.height = -2;
        window.setAttributes(attributes);
        window.setBackgroundDrawableResource(17170445);
        initView(inflate);
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        NoteEntity noteEntity2 = this.noteEntity;
        if (noteEntity2 != null) {
            this.etName.setText(noteEntity2.getName());
            this.etAge.setText(String.valueOf(this.noteEntity.getAge()));
            this.etNotes.setText(this.noteEntity.getComment());
            if (this.noteEntity.getGender() == 1) {
                this.rgGender.check(C1544R.C1548id.radio_btn_female);
            } else {
                this.rgGender.check(C1544R.C1548id.radio_btn_male);
            }
        }
    }

    private void initView(View view) {
        this.etName = (EditText) view.findViewById(C1544R.C1548id.et_name);
        this.etAge = (EditText) view.findViewById(C1544R.C1548id.et_age);
        this.rgGender = (RadioGroup) view.findViewById(C1544R.C1548id.radio_group_gender);
        this.etNotes = (EditText) view.findViewById(C1544R.C1548id.et_notes);
        Button button = (Button) view.findViewById(C1544R.C1548id.btn_left);
        this.btnLeft = button;
        button.setText(this.leftString);
        Button button2 = (Button) view.findViewById(C1544R.C1548id.btn_right);
        this.btnRight = button2;
        button2.setText(this.rightString);
        this.btnLeft.setOnClickListener(this);
        this.btnRight.setOnClickListener(this);
    }

    public void setNoteEntity(NoteEntity noteEntity2) {
        this.noteEntity = noteEntity2;
    }

    public void onClick(View view) {
        IOnClickListener iOnClickListener;
        int id = view.getId();
        NoteEntity noteEntity2 = new NoteEntity();
        noteEntity2.setName(this.etName.getText().toString().trim());
        String trim = this.etAge.getText().toString().trim();
        int i = 0;
        if (StringUtils.isEmpty(trim)) {
            noteEntity2.setAge(0);
        } else {
            noteEntity2.setAge(Integer.valueOf(trim).intValue());
        }
        if (this.rgGender.getCheckedRadioButtonId() != C1544R.C1548id.radio_btn_male) {
            i = 1;
        }
        noteEntity2.setGender(i);
        noteEntity2.setComment(this.etNotes.getText().toString().trim());
        if (id == C1544R.C1548id.btn_left) {
            IOnClickListener iOnClickListener2 = this.mIOnClickListener;
            if (iOnClickListener2 != null) {
                iOnClickListener2.onLeftClicked(this, noteEntity2);
            }
        } else if (id == C1544R.C1548id.btn_right && (iOnClickListener = this.mIOnClickListener) != null) {
            iOnClickListener.onRightClicked(this, noteEntity2);
        }
    }
}
