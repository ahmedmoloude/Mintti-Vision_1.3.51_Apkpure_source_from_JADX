package com.chad.library.adapter.base;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class BaseViewHolder extends RecyclerView.ViewHolder {
    /* access modifiers changed from: private */
    public BaseQuickAdapter adapter;
    private Object associatedObject;
    private final LinkedHashSet<Integer> childClickViewIds = new LinkedHashSet<>();
    @Deprecated
    public View convertView;
    private final LinkedHashSet<Integer> itemChildLongClickViewIds = new LinkedHashSet<>();
    private final HashSet<Integer> nestViews = new HashSet<>();
    private final SparseArray<View> views = new SparseArray<>();

    public Set<Integer> getNestViews() {
        return this.nestViews;
    }

    public BaseViewHolder(View view) {
        super(view);
        this.convertView = view;
    }

    public HashSet<Integer> getItemChildLongClickViewIds() {
        return this.itemChildLongClickViewIds;
    }

    public HashSet<Integer> getChildClickViewIds() {
        return this.childClickViewIds;
    }

    @Deprecated
    public View getConvertView() {
        return this.convertView;
    }

    public BaseViewHolder setText(int i, CharSequence charSequence) {
        ((TextView) getView(i)).setText(charSequence);
        return this;
    }

    public BaseViewHolder setText(int i, int i2) {
        ((TextView) getView(i)).setText(i2);
        return this;
    }

    public BaseViewHolder setImageResource(int i, int i2) {
        ((ImageView) getView(i)).setImageResource(i2);
        return this;
    }

    public BaseViewHolder setBackgroundColor(int i, int i2) {
        getView(i).setBackgroundColor(i2);
        return this;
    }

    public BaseViewHolder setBackgroundRes(int i, int i2) {
        getView(i).setBackgroundResource(i2);
        return this;
    }

    public BaseViewHolder setTextColor(int i, int i2) {
        ((TextView) getView(i)).setTextColor(i2);
        return this;
    }

    public BaseViewHolder setImageDrawable(int i, Drawable drawable) {
        ((ImageView) getView(i)).setImageDrawable(drawable);
        return this;
    }

    public BaseViewHolder setImageBitmap(int i, Bitmap bitmap) {
        ((ImageView) getView(i)).setImageBitmap(bitmap);
        return this;
    }

    public BaseViewHolder setAlpha(int i, float f) {
        if (Build.VERSION.SDK_INT >= 11) {
            getView(i).setAlpha(f);
        } else {
            AlphaAnimation alphaAnimation = new AlphaAnimation(f, f);
            alphaAnimation.setDuration(0);
            alphaAnimation.setFillAfter(true);
            getView(i).startAnimation(alphaAnimation);
        }
        return this;
    }

    public BaseViewHolder setGone(int i, boolean z) {
        getView(i).setVisibility(z ? 0 : 8);
        return this;
    }

    public BaseViewHolder setVisible(int i, boolean z) {
        getView(i).setVisibility(z ? 0 : 4);
        return this;
    }

    public BaseViewHolder linkify(int i) {
        Linkify.addLinks((TextView) getView(i), 15);
        return this;
    }

    public BaseViewHolder setTypeface(int i, Typeface typeface) {
        TextView textView = (TextView) getView(i);
        textView.setTypeface(typeface);
        textView.setPaintFlags(textView.getPaintFlags() | 128);
        return this;
    }

    public BaseViewHolder setTypeface(Typeface typeface, int... iArr) {
        for (int view : iArr) {
            TextView textView = (TextView) getView(view);
            textView.setTypeface(typeface);
            textView.setPaintFlags(textView.getPaintFlags() | 128);
        }
        return this;
    }

    public BaseViewHolder setProgress(int i, int i2) {
        ((ProgressBar) getView(i)).setProgress(i2);
        return this;
    }

    public BaseViewHolder setProgress(int i, int i2, int i3) {
        ProgressBar progressBar = (ProgressBar) getView(i);
        progressBar.setMax(i3);
        progressBar.setProgress(i2);
        return this;
    }

    public BaseViewHolder setMax(int i, int i2) {
        ((ProgressBar) getView(i)).setMax(i2);
        return this;
    }

    public BaseViewHolder setRating(int i, float f) {
        ((RatingBar) getView(i)).setRating(f);
        return this;
    }

    public BaseViewHolder setRating(int i, float f, int i2) {
        RatingBar ratingBar = (RatingBar) getView(i);
        ratingBar.setMax(i2);
        ratingBar.setRating(f);
        return this;
    }

    @Deprecated
    public BaseViewHolder setOnClickListener(int i, View.OnClickListener onClickListener) {
        getView(i).setOnClickListener(onClickListener);
        return this;
    }

    public BaseViewHolder addOnClickListener(int... iArr) {
        for (int i : iArr) {
            this.childClickViewIds.add(Integer.valueOf(i));
            View view = getView(i);
            if (view != null) {
                if (!view.isClickable()) {
                    view.setClickable(true);
                }
                view.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        int adapterPosition;
                        if (BaseViewHolder.this.adapter.getOnItemChildClickListener() != null && (adapterPosition = BaseViewHolder.this.getAdapterPosition()) != -1) {
                            BaseViewHolder.this.adapter.getOnItemChildClickListener().onItemChildClick(BaseViewHolder.this.adapter, view, adapterPosition - BaseViewHolder.this.adapter.getHeaderLayoutCount());
                        }
                    }
                });
            }
        }
        return this;
    }

    public BaseViewHolder setNestView(int... iArr) {
        for (int valueOf : iArr) {
            this.nestViews.add(Integer.valueOf(valueOf));
        }
        addOnClickListener(iArr);
        addOnLongClickListener(iArr);
        return this;
    }

    public BaseViewHolder addOnLongClickListener(int... iArr) {
        for (int i : iArr) {
            this.itemChildLongClickViewIds.add(Integer.valueOf(i));
            View view = getView(i);
            if (view != null) {
                if (!view.isLongClickable()) {
                    view.setLongClickable(true);
                }
                view.setOnLongClickListener(new View.OnLongClickListener() {
                    public boolean onLongClick(View view) {
                        int adapterPosition;
                        if (BaseViewHolder.this.adapter.getOnItemChildLongClickListener() == null || (adapterPosition = BaseViewHolder.this.getAdapterPosition()) == -1) {
                            return false;
                        }
                        return BaseViewHolder.this.adapter.getOnItemChildLongClickListener().onItemChildLongClick(BaseViewHolder.this.adapter, view, adapterPosition - BaseViewHolder.this.adapter.getHeaderLayoutCount());
                    }
                });
            }
        }
        return this;
    }

    @Deprecated
    public BaseViewHolder setOnTouchListener(int i, View.OnTouchListener onTouchListener) {
        getView(i).setOnTouchListener(onTouchListener);
        return this;
    }

    @Deprecated
    public BaseViewHolder setOnLongClickListener(int i, View.OnLongClickListener onLongClickListener) {
        getView(i).setOnLongClickListener(onLongClickListener);
        return this;
    }

    @Deprecated
    public BaseViewHolder setOnItemClickListener(int i, AdapterView.OnItemClickListener onItemClickListener) {
        ((AdapterView) getView(i)).setOnItemClickListener(onItemClickListener);
        return this;
    }

    public BaseViewHolder setOnItemLongClickListener(int i, AdapterView.OnItemLongClickListener onItemLongClickListener) {
        ((AdapterView) getView(i)).setOnItemLongClickListener(onItemLongClickListener);
        return this;
    }

    public BaseViewHolder setOnItemSelectedClickListener(int i, AdapterView.OnItemSelectedListener onItemSelectedListener) {
        ((AdapterView) getView(i)).setOnItemSelectedListener(onItemSelectedListener);
        return this;
    }

    public BaseViewHolder setOnCheckedChangeListener(int i, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        ((CompoundButton) getView(i)).setOnCheckedChangeListener(onCheckedChangeListener);
        return this;
    }

    public BaseViewHolder setTag(int i, Object obj) {
        getView(i).setTag(obj);
        return this;
    }

    public BaseViewHolder setTag(int i, int i2, Object obj) {
        getView(i).setTag(i2, obj);
        return this;
    }

    public BaseViewHolder setChecked(int i, boolean z) {
        View view = getView(i);
        if (view instanceof Checkable) {
            ((Checkable) view).setChecked(z);
        }
        return this;
    }

    public BaseViewHolder setEnabled(int i, boolean z) {
        getView(i).setEnabled(z);
        return this;
    }

    public BaseViewHolder setAdapter(int i, Adapter adapter2) {
        ((AdapterView) getView(i)).setAdapter(adapter2);
        return this;
    }

    /* access modifiers changed from: protected */
    public BaseViewHolder setAdapter(BaseQuickAdapter baseQuickAdapter) {
        this.adapter = baseQuickAdapter;
        return this;
    }

    public <T extends View> T getView(int i) {
        T t = (View) this.views.get(i);
        if (t != null) {
            return t;
        }
        T findViewById = this.itemView.findViewById(i);
        this.views.put(i, findViewById);
        return findViewById;
    }

    public Object getAssociatedObject() {
        return this.associatedObject;
    }

    public void setAssociatedObject(Object obj) {
        this.associatedObject = obj;
    }
}
