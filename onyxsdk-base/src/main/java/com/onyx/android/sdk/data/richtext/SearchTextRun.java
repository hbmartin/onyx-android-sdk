// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.data.richtext;

import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \u00112\u00020\u0001:\u0003\u0010\u0011\u0012B\u0005¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\"\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u0013" }, d2 = { "Lcom/onyx/android/sdk/data/richtext/SearchTextRun;", "", "()V", "count", "", "getCount", "()I", "setCount", "(I)V", "list", "", "Lcom/onyx/android/sdk/data/richtext/SearchTextRun$SearchText;", "getList", "()Ljava/util/List;", "setList", "(Ljava/util/List;)V", "Character", "Companion", "SearchText", "onyxsdk-base_release" })
public final class SearchTextRun
{
    @NotNull
    public static final Companion Companion;
    private int a;
    @Nullable
    private List<SearchText> b;
    
    static {
        Companion = new Companion(null);
    }
    
    public final int getCount() {
        return this.a;
    }
    
    public final void setCount(final int value) {
        this.a = value;
    }
    
    @Nullable
    public final List<SearchText> getList() {
        return this.b;
    }
    
    public final void setList(@Nullable final List<SearchText> value) {
        this.b = value;
    }
    
    @Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0001\u0017B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0016\u001a\u00020\u0011R\"\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015¨\u0006\u0018" }, d2 = { "Lcom/onyx/android/sdk/data/richtext/SearchTextRun$SearchText;", "", "()V", "character", "", "Lcom/onyx/android/sdk/data/richtext/SearchTextRun$Character;", "getCharacter", "()Ljava/util/List;", "setCharacter", "(Ljava/util/List;)V", "pos", "Lcom/onyx/android/sdk/data/richtext/SearchTextRun$SearchText$Position;", "getPos", "()Lcom/onyx/android/sdk/data/richtext/SearchTextRun$SearchText$Position;", "setPos", "(Lcom/onyx/android/sdk/data/richtext/SearchTextRun$SearchText$Position;)V", "text", "", "getText", "()Ljava/lang/String;", "setText", "(Ljava/lang/String;)V", "buildTextFromCharList", "Position", "onyxsdk-base_release" })
    public static final class SearchText
    {
        @NotNull
        private String a;
        @Nullable
        private Position b;
        @Nullable
        private List<Character> c;
        
        public SearchText() {
            this.a = "";
        }
        
        @NotNull
        public final String getText() {
            return this.a;
        }
        
        public final void setText(@NotNull final String value) {
            Intrinsics.checkNotNullParameter((Object)value, "<set-?>");
            this.a = value;
        }
        
        @Nullable
        public final Position getPos() {
            return this.b;
        }
        
        public final void setPos(@Nullable final Position value) {
            this.b = value;
        }
        
        @Nullable
        public final List<Character> getCharacter() {
            return this.c;
        }
        
        public final void setCharacter(@Nullable final List<Character> value) {
            this.c = value;
        }
        
        @NotNull
        public final String buildTextFromCharList() {
            final StringBuilder sb = new StringBuilder();
            final List<Character> c;
            if ((c = this.c) != null) {
                final Iterator<Character> iterator = c.iterator();
                while (iterator.hasNext()) {
                    sb.append(((Character)iterator.next()).getText());
                }
            }
            final String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue((Object)string, "sb.toString()");
            return string;
        }
        
        @Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\b¨\u0006\f" }, d2 = { "Lcom/onyx/android/sdk/data/richtext/SearchTextRun$SearchText$Position;", "", "()V", "from", "", "getFrom", "()I", "setFrom", "(I)V", "to", "getTo", "setTo", "onyxsdk-base_release" })
        public static final class Position
        {
            private int a;
            private int b;
            
            public final int getFrom() {
                return this.a;
            }
            
            public final void setFrom(final int value) {
                this.a = value;
            }
            
            public final int getTo() {
                return this.b;
            }
            
            public final void setTo(final int value) {
                this.b = value;
            }
        }
    }
    
    @Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0010\u000e\n\u0002\b\u0011\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001a\u0010\u000f\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\bR\u001a\u0010\u001b\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0006\"\u0004\b\u001d\u0010\bR\u001a\u0010\u001e\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0006\"\u0004\b \u0010\bR\u001a\u0010!\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0006\"\u0004\b#\u0010\b¨\u0006$" }, d2 = { "Lcom/onyx/android/sdk/data/richtext/SearchTextRun$Character;", "", "()V", "bottom", "", "getBottom", "()I", "setBottom", "(I)V", "height", "getHeight", "setHeight", "left", "getLeft", "setLeft", "right", "getRight", "setRight", "text", "", "getText", "()Ljava/lang/String;", "setText", "(Ljava/lang/String;)V", "top", "getTop", "setTop", "width", "getWidth", "setWidth", "x", "getX", "setX", "y", "getY", "setY", "onyxsdk-base_release" })
    public static final class Character
    {
        private int a;
        private int b;
        private int c;
        private int d;
        private int e;
        private int f;
        private int g;
        private int h;
        @Nullable
        private String i;
        
        public final int getX() {
            return this.a;
        }
        
        public final void setX(final int value) {
            this.a = value;
        }
        
        public final int getY() {
            return this.b;
        }
        
        public final void setY(final int value) {
            this.b = value;
        }
        
        public final int getWidth() {
            return this.c;
        }
        
        public final void setWidth(final int value) {
            this.c = value;
        }
        
        public final int getHeight() {
            return this.d;
        }
        
        public final void setHeight(final int value) {
            this.d = value;
        }
        
        public final int getTop() {
            return this.e;
        }
        
        public final void setTop(final int value) {
            this.e = value;
        }
        
        public final int getRight() {
            return this.f;
        }
        
        public final void setRight(final int value) {
            this.f = value;
        }
        
        public final int getBottom() {
            return this.g;
        }
        
        public final void setBottom(final int value) {
            this.g = value;
        }
        
        public final int getLeft() {
            return this.h;
        }
        
        public final void setLeft(final int value) {
            this.h = value;
        }
        
        @Nullable
        public final String getText() {
            return this.i;
        }
        
        public final void setText(@Nullable final String value) {
            this.i = value;
        }
    }
    
    @Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007" }, d2 = { "Lcom/onyx/android/sdk/data/richtext/SearchTextRun$Companion;", "", "()V", "createCharacterFromTextRun", "Lcom/onyx/android/sdk/data/richtext/SearchTextRun$Character;", "runChar", "Lcom/onyx/android/sdk/data/richtext/RichTextRun$TextRunChar;", "onyxsdk-base_release" })
    public static final class Companion
    {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker marker) {
            this();
        }
        
        @NotNull
        public final Character createCharacterFromTextRun(@NotNull final RichTextRun.TextRunChar runChar) {
            Intrinsics.checkNotNullParameter((Object)runChar, "runChar");
            final Character character = new com.onyx.android.sdk.data.richtext.SearchTextRun.Character();
            character.setText(runChar.text);
            character.setX((int)runChar.x);
            character.setY((int)runChar.y);
            character.setLeft((int)runChar.getLeft());
            character.setRight(character.getX() + (int)runChar.width);
            character.setTop((int)runChar.getTop());
            character.setBottom(character.getY() + (int)runChar.height);
            character.setWidth((int)runChar.width);
            character.setHeight((int)runChar.height);
            return character;
        }
    }
}
