// 
// 

package com.onyx.android.sdk.utils;

import org.jetbrains.annotations.Nullable;
import kotlin.jvm.internal.Intrinsics;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\rB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006J\u001a\u0010\b\u001a\u00020\t2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0086\b\u00f8\u0001\u0000J&\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\f0\u000b\"\u0004\b\u0000\u0010\f2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\f0\u0006H\u0086\b\u00f8\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u000e" }, d2 = { "Lcom/onyx/android/sdk/utils/Times;", "", "()V", "measureTime", "", "block", "Lkotlin/Function0;", "", "measureTimeString", "", "measureTimedStringValue", "Lcom/onyx/android/sdk/utils/Times$TimedValue;", "R", "TimedValue", "onyxsdk-base_release" })
public final class Times
{
    @NotNull
    public static final Times INSTANCE;
    
    private Times() {
    }
    
    static {
        INSTANCE = new Times();
    }
    
    public final long measureTime(@NotNull final Function0<Unit> block) {
        Intrinsics.checkNotNullParameter((Object)block, "block");
        final Benchmark benchmark = new Benchmark();
        block.invoke();
        return benchmark.duration();
    }
    
    @NotNull
    public final String measureTimeString(@NotNull final Function0<Unit> block) {
        Intrinsics.checkNotNullParameter((Object)block, "block");
        final long nanoTime = System.nanoTime();
        block.invoke();
        return (System.nanoTime() - nanoTime) / 1000000.0 + "ms";
    }
    
    @NotNull
    public final <R> TimedValue<R> measureTimedStringValue(@NotNull final Function0<? extends R> block) {
        Intrinsics.checkNotNullParameter((Object)block, "block");
        return new TimedValue<R>((R)block.invoke(), (System.nanoTime() - System.nanoTime()) / 1000000.0 + "ms");
    }
    
    @Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00028\u0000\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\f\u001a\u00028\u0000H\u00c6\u0003¢\u0006\u0002\u0010\nJ\t\u0010\r\u001a\u00020\u0005H\u00c6\u0003J(\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0003\u001a\u00028\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001¢\u0006\u0002\u0010\u000fJ\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0002H\u00d6\u0003J\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001J\t\u0010\u0015\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0003\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\t\u0010\n¨\u0006\u0016" }, d2 = { "Lcom/onyx/android/sdk/utils/Times$TimedValue;", "T", "", "value", "duration", "", "(Ljava/lang/Object;Ljava/lang/String;)V", "getDuration", "()Ljava/lang/String;", "getValue", "()Ljava/lang/Object;", "Ljava/lang/Object;", "component1", "component2", "copy", "(Ljava/lang/Object;Ljava/lang/String;)Lcom/onyx/android/sdk/utils/Times$TimedValue;", "equals", "", "other", "hashCode", "", "toString", "onyxsdk-base_release" })
    public static final class TimedValue<T>
    {
        private final T a;
        @NotNull
        private final String b;
        
        public TimedValue(final T value, @NotNull final String duration) {
            Intrinsics.checkNotNullParameter((Object)duration, "duration");
            this.a = value;
            this.b = duration;
        }
        
        public final T getValue() {
            return this.a;
        }
        
        @NotNull
        public final String getDuration() {
            return this.b;
        }
        
        public final T component1() {
            return this.a;
        }
        
        @NotNull
        public final String component2() {
            return this.b;
        }
        
        @NotNull
        public final TimedValue<T> copy(final T value, @NotNull final String duration) {
            Intrinsics.checkNotNullParameter((Object)duration, "duration");
            return new TimedValue<T>(value, duration);
        }

        public static /* synthetic */ TimedValue copy$default(final TimedValue timedValue, Object value, String duration, final int i, final Object obj) {
            if ((i & 1) != 0) {
                value = timedValue.a;
            }
            if ((i & 2) != 0) {
                duration = timedValue.b;
            }
            return timedValue.copy(value, duration);
        }
        
        @NotNull
        @Override
        public String toString() {
            return "TimedValue(value=" + this.a + ", duration=" + this.b + ')';
        }
        
        @Override
        public int hashCode() {
            final T a;
            return (((a = this.a) == null) ? 0 : a.hashCode()) * 31 + this.b.hashCode();
        }
        
        @Override
        public boolean equals(@Nullable final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof TimedValue)) {
                return false;
            }
            final TimedValue timedValue = (TimedValue)other;
            return Intrinsics.areEqual((Object)this.a, (Object)timedValue.a) && Intrinsics.areEqual((Object)this.b, (Object)timedValue.b);
        }
    }
}
