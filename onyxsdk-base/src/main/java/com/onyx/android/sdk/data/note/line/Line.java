// 
// 

package com.onyx.android.sdk.data.note.line;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J1\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0015\u001a\u00020\u0016H\u00d6\u0001J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0019" }, d2 = { "Lcom/onyx/android/sdk/data/note/line/Line;", "", "startX", "", "startY", "endX", "endY", "(FFFF)V", "getEndX", "()F", "getEndY", "getStartX", "getStartY", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "", "onyxsdk-base_release" })
public final class Line
{
    private final float a;
    private final float b;
    private final float c;
    private final float d;
    
    public Line(final float startX, final float startY, final float endX, final float endY) {
        this.a = startX;
        this.b = startY;
        this.c = endX;
        this.d = endY;
    }
    
    public final float getStartX() {
        return this.a;
    }
    
    public final float getStartY() {
        return this.b;
    }
    
    public final float getEndX() {
        return this.c;
    }
    
    public final float getEndY() {
        return this.d;
    }
    
    public final float component1() {
        return this.a;
    }
    
    public final float component2() {
        return this.b;
    }
    
    public final float component3() {
        return this.c;
    }
    
    public final float component4() {
        return this.d;
    }
    
    @NotNull
    public final Line copy(final float startX, final float startY, final float endX, final float endY) {
        return new Line(startX, startY, endX, endY);
    }

    public static /* synthetic */ Line copy$default(final Line line, float startX, float startY, float endX, float endY, final int i, final Object obj) {
        if ((i & 1) != 0) {
            startX = line.a;
        }
        if ((i & 2) != 0) {
            startY = line.b;
        }
        if ((i & 4) != 0) {
            endX = line.c;
        }
        if ((i & 8) != 0) {
            endY = line.d;
        }
        return line.copy(startX, startY, endX, endY);
    }
    
    @NotNull
    @Override
    public String toString() {
        return "Line(startX=" + this.a + ", startY=" + this.b + ", endX=" + this.c + ", endY=" + this.d + ')';
    }
    
    @Override
    public int hashCode() {
        return ((Float.hashCode(this.a) * 31 + Float.hashCode(this.b)) * 31 + Float.hashCode(this.c)) * 31 + Float.hashCode(this.d);
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Line)) {
            return false;
        }
        final Line line = (Line)other;
        return Intrinsics.areEqual((Object)this.a, (Object)line.a) && Intrinsics.areEqual((Object)this.b, (Object)line.b) && Intrinsics.areEqual((Object)this.c, (Object)line.c) && Intrinsics.areEqual((Object)this.d, (Object)line.d);
    }
}
