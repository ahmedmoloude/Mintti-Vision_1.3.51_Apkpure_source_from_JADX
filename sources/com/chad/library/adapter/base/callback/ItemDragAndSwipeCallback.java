package com.chad.library.adapter.base.callback;

import android.graphics.Canvas;
import android.view.View;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.C1039R;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.DraggableController;
import com.chad.library.adapter.base.listener.IDraggableListener;

public class ItemDragAndSwipeCallback extends ItemTouchHelper.Callback {
    private BaseItemDraggableAdapter mBaseItemDraggableAdapter;
    private int mDragMoveFlags = 15;
    private IDraggableListener mDraggableListener;
    private float mMoveThreshold = 0.1f;
    private int mSwipeMoveFlags = 32;
    private float mSwipeThreshold = 0.7f;

    public ItemDragAndSwipeCallback(BaseItemDraggableAdapter baseItemDraggableAdapter) {
        this.mBaseItemDraggableAdapter = baseItemDraggableAdapter;
    }

    public ItemDragAndSwipeCallback(DraggableController draggableController) {
        this.mDraggableListener = draggableController;
    }

    public boolean isLongPressDragEnabled() {
        BaseItemDraggableAdapter baseItemDraggableAdapter = this.mBaseItemDraggableAdapter;
        if (baseItemDraggableAdapter == null) {
            IDraggableListener iDraggableListener = this.mDraggableListener;
            if (iDraggableListener == null || !iDraggableListener.isItemDraggable() || this.mDraggableListener.hasToggleView()) {
                return false;
            }
            return true;
        } else if (!baseItemDraggableAdapter.isItemDraggable() || this.mBaseItemDraggableAdapter.hasToggleView()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isItemViewSwipeEnabled() {
        BaseItemDraggableAdapter baseItemDraggableAdapter = this.mBaseItemDraggableAdapter;
        if (baseItemDraggableAdapter != null) {
            return baseItemDraggableAdapter.isItemSwipeEnable();
        }
        IDraggableListener iDraggableListener = this.mDraggableListener;
        if (iDraggableListener != null) {
            return iDraggableListener.isItemSwipeEnable();
        }
        return false;
    }

    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int i) {
        if (i == 2 && !isViewCreateByAdapter(viewHolder)) {
            BaseItemDraggableAdapter baseItemDraggableAdapter = this.mBaseItemDraggableAdapter;
            if (baseItemDraggableAdapter != null) {
                baseItemDraggableAdapter.onItemDragStart(viewHolder);
            } else {
                IDraggableListener iDraggableListener = this.mDraggableListener;
                if (iDraggableListener != null) {
                    iDraggableListener.onItemDragStart(viewHolder);
                }
            }
            viewHolder.itemView.setTag(C1039R.C1041id.BaseQuickAdapter_dragging_support, true);
        } else if (i == 1 && !isViewCreateByAdapter(viewHolder)) {
            BaseItemDraggableAdapter baseItemDraggableAdapter2 = this.mBaseItemDraggableAdapter;
            if (baseItemDraggableAdapter2 != null) {
                baseItemDraggableAdapter2.onItemSwipeStart(viewHolder);
            } else {
                IDraggableListener iDraggableListener2 = this.mDraggableListener;
                if (iDraggableListener2 != null) {
                    iDraggableListener2.onItemSwipeStart(viewHolder);
                }
            }
            viewHolder.itemView.setTag(C1039R.C1041id.BaseQuickAdapter_swiping_support, true);
        }
        super.onSelectedChanged(viewHolder, i);
    }

    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (!isViewCreateByAdapter(viewHolder)) {
            if (viewHolder.itemView.getTag(C1039R.C1041id.BaseQuickAdapter_dragging_support) != null && ((Boolean) viewHolder.itemView.getTag(C1039R.C1041id.BaseQuickAdapter_dragging_support)).booleanValue()) {
                BaseItemDraggableAdapter baseItemDraggableAdapter = this.mBaseItemDraggableAdapter;
                if (baseItemDraggableAdapter != null) {
                    baseItemDraggableAdapter.onItemDragEnd(viewHolder);
                } else {
                    IDraggableListener iDraggableListener = this.mDraggableListener;
                    if (iDraggableListener != null) {
                        iDraggableListener.onItemDragEnd(viewHolder);
                    }
                }
                viewHolder.itemView.setTag(C1039R.C1041id.BaseQuickAdapter_dragging_support, false);
            }
            if (viewHolder.itemView.getTag(C1039R.C1041id.BaseQuickAdapter_swiping_support) != null && ((Boolean) viewHolder.itemView.getTag(C1039R.C1041id.BaseQuickAdapter_swiping_support)).booleanValue()) {
                BaseItemDraggableAdapter baseItemDraggableAdapter2 = this.mBaseItemDraggableAdapter;
                if (baseItemDraggableAdapter2 != null) {
                    baseItemDraggableAdapter2.onItemSwipeClear(viewHolder);
                } else {
                    IDraggableListener iDraggableListener2 = this.mDraggableListener;
                    if (iDraggableListener2 != null) {
                        iDraggableListener2.onItemSwipeClear(viewHolder);
                    }
                }
                viewHolder.itemView.setTag(C1039R.C1041id.BaseQuickAdapter_swiping_support, false);
            }
        }
    }

    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (isViewCreateByAdapter(viewHolder)) {
            return makeMovementFlags(0, 0);
        }
        return makeMovementFlags(this.mDragMoveFlags, this.mSwipeMoveFlags);
    }

    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
        return viewHolder.getItemViewType() == viewHolder2.getItemViewType();
    }

    public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int i, RecyclerView.ViewHolder viewHolder2, int i2, int i3, int i4) {
        super.onMoved(recyclerView, viewHolder, i, viewHolder2, i2, i3, i4);
        BaseItemDraggableAdapter baseItemDraggableAdapter = this.mBaseItemDraggableAdapter;
        if (baseItemDraggableAdapter != null) {
            baseItemDraggableAdapter.onItemDragMoving(viewHolder, viewHolder2);
            return;
        }
        IDraggableListener iDraggableListener = this.mDraggableListener;
        if (iDraggableListener != null) {
            iDraggableListener.onItemDragMoving(viewHolder, viewHolder2);
        }
    }

    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
        if (!isViewCreateByAdapter(viewHolder)) {
            BaseItemDraggableAdapter baseItemDraggableAdapter = this.mBaseItemDraggableAdapter;
            if (baseItemDraggableAdapter != null) {
                baseItemDraggableAdapter.onItemSwiped(viewHolder);
                return;
            }
            IDraggableListener iDraggableListener = this.mDraggableListener;
            if (iDraggableListener != null) {
                iDraggableListener.onItemSwiped(viewHolder);
            }
        }
    }

    public float getMoveThreshold(RecyclerView.ViewHolder viewHolder) {
        return this.mMoveThreshold;
    }

    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
        return this.mSwipeThreshold;
    }

    public void setSwipeThreshold(float f) {
        this.mSwipeThreshold = f;
    }

    public void setMoveThreshold(float f) {
        this.mMoveThreshold = f;
    }

    public void setDragMoveFlags(int i) {
        this.mDragMoveFlags = i;
    }

    public void setSwipeMoveFlags(int i) {
        this.mSwipeMoveFlags = i;
    }

    public void onChildDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float f, float f2, int i, boolean z) {
        super.onChildDrawOver(canvas, recyclerView, viewHolder, f, f2, i, z);
        if (i == 1 && !isViewCreateByAdapter(viewHolder)) {
            View view = viewHolder.itemView;
            canvas.save();
            if (f > 0.0f) {
                canvas.clipRect((float) view.getLeft(), (float) view.getTop(), ((float) view.getLeft()) + f, (float) view.getBottom());
                canvas.translate((float) view.getLeft(), (float) view.getTop());
            } else {
                canvas.clipRect(((float) view.getRight()) + f, (float) view.getTop(), (float) view.getRight(), (float) view.getBottom());
                canvas.translate(((float) view.getRight()) + f, (float) view.getTop());
            }
            BaseItemDraggableAdapter baseItemDraggableAdapter = this.mBaseItemDraggableAdapter;
            if (baseItemDraggableAdapter != null) {
                baseItemDraggableAdapter.onItemSwiping(canvas, viewHolder, f, f2, z);
            } else {
                IDraggableListener iDraggableListener = this.mDraggableListener;
                if (iDraggableListener != null) {
                    iDraggableListener.onItemSwiping(canvas, viewHolder, f, f2, z);
                }
            }
            canvas.restore();
        }
    }

    private boolean isViewCreateByAdapter(RecyclerView.ViewHolder viewHolder) {
        int itemViewType = viewHolder.getItemViewType();
        return itemViewType == 273 || itemViewType == 546 || itemViewType == 819 || itemViewType == 1365;
    }
}
