// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.data.note;

import com.onyx.android.sdk.utils.ResManager;
import com.onyx.android.sdk.R;
import androidx.annotation.Nullable;

public enum PreviewDisplayType
{
    GRID_ROW2_COL2(2, 2), 
    GRID_ROW3_COL3(3, 3), 
    GRID_ROW3_COL4(3, 4), 
    GRID_ROW4_COL4(4, 4), 
    CUSTOM_GRID_ROW_COL((int)ResManager.getInteger(R.integer.preview_page_list_row_count), (int)ResManager.getInteger(R.integer.preview_page_list_col_count));
    
    private int a;
    private int b;
    
    private PreviewDisplayType(final int rowCount, final int colCount) {
        this.a = rowCount;
        this.b = colCount;
    }
    
    @Nullable
    public static PreviewDisplayType safelyValueOf(final String name) {
        try {
            return valueOf(name);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public int getRowCount() {
        return this.a;
    }
    
    public int getColCount() {
        return this.b;
    }
}
