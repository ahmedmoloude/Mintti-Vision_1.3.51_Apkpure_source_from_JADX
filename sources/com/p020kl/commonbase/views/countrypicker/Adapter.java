package com.p020kl.commonbase.views.countrypicker;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.p020kl.commonbase.C1544R;
import java.util.ArrayList;

/* renamed from: com.kl.commonbase.views.countrypicker.Adapter */
public class Adapter extends RecyclerView.Adapter<C1619VH> {
    private final Context context;
    private final LayoutInflater inflater;
    private int itemHeight = -1;
    /* access modifiers changed from: private */
    public OnPick onPick = null;
    private ArrayList<Country> selectedCountries = new ArrayList<>();

    public Adapter(Context context2) {
        this.inflater = LayoutInflater.from(context2);
        this.context = context2;
    }

    public void setSelectedCountries(ArrayList<Country> arrayList) {
        this.selectedCountries = arrayList;
        notifyDataSetChanged();
    }

    public void setOnPick(OnPick onPick2) {
        this.onPick = onPick2;
    }

    public C1619VH onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new C1619VH(this.inflater.inflate(C1544R.layout.item_country, viewGroup, false));
    }

    public void setItemHeight(float f) {
        this.itemHeight = (int) TypedValue.applyDimension(2, f, this.context.getResources().getDisplayMetrics());
    }

    public void onBindViewHolder(C1619VH vh, int i) {
        final Country country = this.selectedCountries.get(i);
        vh.tvName.setText(country.name);
        TextView textView = vh.tvCode;
        textView.setText("+" + country.code);
        if (this.itemHeight != -1) {
            ViewGroup.LayoutParams layoutParams = vh.itemView.getLayoutParams();
            layoutParams.height = this.itemHeight;
            vh.itemView.setLayoutParams(layoutParams);
        }
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Adapter.this.onPick != null) {
                    Adapter.this.onPick.onPick(country);
                }
            }
        });
    }

    public int getItemCount() {
        return this.selectedCountries.size();
    }
}
