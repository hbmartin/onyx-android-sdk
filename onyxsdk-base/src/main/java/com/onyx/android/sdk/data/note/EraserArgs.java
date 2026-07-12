// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.data.note;

import com.onyx.android.sdk.utils.UUIDUtils;
import org.jetbrains.annotations.Nullable;
import com.onyx.android.sdk.data.EraseConstant;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;
import java.io.Serializable;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010%\n\u0002\u0010\u0007\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0086\b\u0018\u0000 #2\u00020\u0001:\u0001#B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0005H\u00c6\u0003J\u0015\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0003J3\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u0014\b\u0002\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0001J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u00d6\u0003J\t\u0010\u001f\u001a\u00020\u0005H\u00d6\u0001J\t\u0010 \u001a\u00020\u0003H\u00d6\u0001J\u0016\u0010!\u001a\u00020\"2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\bR&\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\b0\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0014\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016¨\u0006$" }, d2 = { "Lcom/onyx/android/sdk/data/note/EraserArgs;", "Ljava/io/Serializable;", "id", "", "type", "", "eraseWidthMap", "", "", "(Ljava/lang/String;ILjava/util/Map;)V", "getEraseWidthMap", "()Ljava/util/Map;", "setEraseWidthMap", "(Ljava/util/Map;)V", "getId", "()Ljava/lang/String;", "getType", "()I", "setType", "(I)V", "width", "getWidth", "()F", "component1", "component2", "component3", "copy", "equals", "", "other", "", "hashCode", "toString", "updateWidth", "", "Companion", "onyxsdk-base_release" })
public final class EraserArgs implements Serializable
{
    @NotNull
    public static final Companion Companion;
    @NotNull
    private final String a;
    private int b;
    @NotNull
    private Map<String, Float> c;
    
    public EraserArgs(@NotNull final String id, final int type, @NotNull final Map<String, Float> eraseWidthMap) {
        Intrinsics.checkNotNullParameter((Object)id, "id");
        Intrinsics.checkNotNullParameter((Object)eraseWidthMap, "eraseWidthMap");
        this.a = id;
        this.b = type;
        this.c = eraseWidthMap;
    }
    
    static {
        Companion = new Companion(null);
    }
    
    @NotNull
    public final String getId() {
        return this.a;
    }
    
    public final int getType() {
        return this.b;
    }
    
    public final void setType(final int value) {
        this.b = value;
    }
    
    @NotNull
    public final Map<String, Float> getEraseWidthMap() {
        return this.c;
    }
    
    public final void setEraseWidthMap(@NotNull final Map<String, Float> value) {
        Intrinsics.checkNotNullParameter((Object)value, "<set-?>");
        this.c = value;
    }
    
    public final float getWidth() {
        final Map<String, Float> c = this.c;
        final String value;
        Float n;
        if ((n = c.get(value = String.valueOf(this.b))) == null) {
            c.put(value, n = EraseConstant.getDefaultEraserWidth(this.getType()));
        }
        return n.floatValue();
    }
    
    public final void updateWidth(final int type, final float width) {
        this.c.put(String.valueOf(type), width);
    }
    
    @NotNull
    public final String component1() {
        return this.a;
    }
    
    public final int component2() {
        return this.b;
    }
    
    @NotNull
    public final Map<String, Float> component3() {
        return this.c;
    }
    
    @NotNull
    public final EraserArgs copy(@NotNull final String id, final int type, @NotNull final Map<String, Float> eraseWidthMap) {
        Intrinsics.checkNotNullParameter((Object)id, "id");
        Intrinsics.checkNotNullParameter((Object)eraseWidthMap, "eraseWidthMap");
        return new EraserArgs(id, type, eraseWidthMap);
    }

    public static /* synthetic */ EraserArgs copy$default(final EraserArgs eraserArgs, String id, int type, Map eraseWidthMap, final int i, final Object obj) {
        if ((i & 1) != 0) {
            id = eraserArgs.a;
        }
        if ((i & 2) != 0) {
            type = eraserArgs.b;
        }
        if ((i & 4) != 0) {
            eraseWidthMap = eraserArgs.c;
        }
        return eraserArgs.copy(id, type, (Map<String, Float>)eraseWidthMap);
    }
    
    @NotNull
    @Override
    public String toString() {
        return "EraserArgs(id=" + this.a + ", type=" + this.b + ", eraseWidthMap=" + this.c + ')';
    }
    
    @Override
    public int hashCode() {
        return (this.a.hashCode() * 31 + Integer.hashCode(this.b)) * 31 + this.c.hashCode();
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof EraserArgs)) {
            return false;
        }
        final EraserArgs eraserArgs = (EraserArgs)other;
        return Intrinsics.areEqual((Object)this.a, (Object)eraserArgs.a) && this.b == eraserArgs.b && Intrinsics.areEqual((Object)this.c, (Object)eraserArgs.c);
    }
    
    @Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004J\u0006\u0010\u0006\u001a\u00020\u0004¨\u0006\u0007" }, d2 = { "Lcom/onyx/android/sdk/data/note/EraserArgs$Companion;", "", "()V", "copyFrom", "Lcom/onyx/android/sdk/data/note/EraserArgs;", "eraserArgs", "createDefaultEraserArgs", "onyxsdk-base_release" })
    public static final class Companion
    {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker marker) {
            this();
        }
        
        @NotNull
        public final EraserArgs createDefaultEraserArgs() {
            final String randomUUID;
            Intrinsics.checkNotNullExpressionValue((Object)(randomUUID = UUIDUtils.randomUUID()), "randomUUID()");
            return new EraserArgs(randomUUID, 0, EraseConstant.getDefaultEraserWidthMap());
        }
        
        @NotNull
        public final EraserArgs copyFrom(@NotNull final EraserArgs eraserArgs) {
            Intrinsics.checkNotNullParameter((Object)eraserArgs, "eraserArgs");
            final String randomUUID;
            Intrinsics.checkNotNullExpressionValue((Object)(randomUUID = UUIDUtils.randomUUID()), "randomUUID()");
            return new EraserArgs(randomUUID, eraserArgs.getType(), eraserArgs.getEraseWidthMap());
        }
    }
}
