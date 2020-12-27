package com.example.bidbadnew.Others;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductLayoutDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if(parent.getChildAdapterPosition(view) == 0){
            outRect.left = parent.getWidth()/9;
        } else if(parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1){
            outRect.right = parent.getWidth()/9;
        }
    }
}
