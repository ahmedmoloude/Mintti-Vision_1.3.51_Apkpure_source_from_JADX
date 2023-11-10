package com.p020kl.commonbase.views.countrypicker;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;

/* renamed from: com.kl.commonbase.views.countrypicker.PyAdapter */
public abstract class PyAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements View.OnClickListener {
    private static final String TAG = "PyAdapter";
    public static final int TYPE_LETTER = 0;
    public static final int TYPE_OTHER = 1;
    public final ArrayList<PyEntity> entityList = new ArrayList<>();
    private WeakHashMap<View, VH> holders = new WeakHashMap<>();
    public final HashSet<LetterEntity> letterSet = new HashSet<>();
    private OnItemClickListener listener = $$Lambda$PyAdapter$rID0Vqv18Fhb0D_K0kgROSm0bjs.INSTANCE;

    /* renamed from: com.kl.commonbase.views.countrypicker.PyAdapter$OnItemClickListener */
    public interface OnItemClickListener {
        void onItemClick(PyEntity pyEntity, int i);
    }

    private boolean isLetter(char c) {
        return ('a' <= c && 'z' >= c) || ('A' <= c && 'Z' >= c);
    }

    static /* synthetic */ void lambda$new$3(PyEntity pyEntity, int i) {
    }

    public int getViewType(PyEntity pyEntity, int i) {
        return 1;
    }

    public void onBindHolder(VH vh, PyEntity pyEntity, int i) {
    }

    public void onBindLetterHolder(VH vh, LetterEntity letterEntity, int i) {
    }

    public abstract VH onCreateHolder(ViewGroup viewGroup, int i);

    public abstract VH onCreateLetterHolder(ViewGroup viewGroup, int i);

    public PyAdapter(List<? extends PyEntity> list) {
        Objects.requireNonNull(list, "entities == null!");
        update(list);
    }

    public void update(List<? extends PyEntity> list) {
        Objects.requireNonNull(list, "entities == null!");
        this.entityList.clear();
        this.entityList.addAll(list);
        this.letterSet.clear();
        for (PyEntity pinyin : list) {
            String pinyin2 = pinyin.getPinyin();
            if (!TextUtils.isEmpty(pinyin2)) {
                char charAt = pinyin2.charAt(0);
                if (!isLetter(charAt)) {
                    charAt = '#';
                }
                HashSet<LetterEntity> hashSet = this.letterSet;
                hashSet.add(new LetterEntity(charAt + ""));
            }
        }
        this.entityList.addAll(this.letterSet);
        Collections.sort(this.entityList, new Comparator() {
            public final int compare(Object obj, Object obj2) {
                return PyAdapter.this.lambda$update$4$PyAdapter((PyEntity) obj, (PyEntity) obj2);
            }
        });
        notifyDataSetChanged();
    }

    public /* synthetic */ int lambda$update$4$PyAdapter(PyEntity pyEntity, PyEntity pyEntity2) {
        String lowerCase = pyEntity.getPinyin().toLowerCase();
        String lowerCase2 = pyEntity2.getPinyin().toLowerCase();
        char charAt = lowerCase.charAt(0);
        char charAt2 = lowerCase2.charAt(0);
        if (isLetter(charAt) && isLetter(charAt2)) {
            return lowerCase.compareTo(lowerCase2);
        }
        if (isLetter(charAt) && !isLetter(charAt2)) {
            return -1;
        }
        if (!isLetter(charAt) && isLetter(charAt2)) {
            return 1;
        }
        if (charAt == '#' && (pyEntity instanceof LetterEntity)) {
            return -1;
        }
        if (charAt2 != '#' || !(pyEntity2 instanceof LetterEntity)) {
            return lowerCase.compareTo(lowerCase2);
        }
        return 1;
    }

    public final void onBindViewHolder(VH vh, int i) {
        PyEntity pyEntity = this.entityList.get(i);
        this.holders.put(vh.itemView, vh);
        vh.itemView.setOnClickListener(this);
        if (pyEntity instanceof LetterEntity) {
            onBindLetterHolder(vh, (LetterEntity) pyEntity, i);
        } else {
            onBindHolder(vh, pyEntity, i);
        }
    }

    public int getEntityPosition(PyEntity pyEntity) {
        return this.entityList.indexOf(pyEntity);
    }

    public final VH onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 0) {
            return onCreateLetterHolder(viewGroup, i);
        }
        return onCreateHolder(viewGroup, i);
    }

    public int getLetterPosition(String str) {
        return this.entityList.indexOf(new LetterEntity(str));
    }

    public int getItemViewType(int i) {
        PyEntity pyEntity = this.entityList.get(i);
        if (pyEntity instanceof LetterEntity) {
            return 0;
        }
        return getViewType(pyEntity, i);
    }

    public final int getItemCount() {
        return this.entityList.size();
    }

    public boolean isLetter(int i) {
        if (i < 0 || i >= this.entityList.size()) {
            return false;
        }
        return this.entityList.get(i) instanceof LetterEntity;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }

    public final void onClick(View view) {
        RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) this.holders.get(view);
        if (viewHolder == null) {
            Log.e(TAG, "Holder onClick event, but why holder == null?");
            return;
        }
        int adapterPosition = viewHolder.getAdapterPosition();
        this.listener.onItemClick(this.entityList.get(adapterPosition), adapterPosition);
    }

    /* renamed from: com.kl.commonbase.views.countrypicker.PyAdapter$LetterEntity */
    public static final class LetterEntity implements PyEntity {
        public final String letter;

        public LetterEntity(String str) {
            this.letter = str;
        }

        public String getPinyin() {
            return this.letter.toLowerCase();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return this.letter.toLowerCase().equals(((LetterEntity) obj).letter.toLowerCase());
        }

        public int hashCode() {
            return this.letter.toLowerCase().hashCode();
        }
    }
}
