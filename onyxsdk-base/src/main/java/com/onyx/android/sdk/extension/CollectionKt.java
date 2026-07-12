package com.onyx.android.sdk.extension;

import android.util.SparseArray;
import androidx.annotation.IntRange;
import androidx.core.math.MathUtils;
import com.onyx.android.sdk.data.Copier;
import com.onyx.android.sdk.data.StatisticsEntity;
import com.onyx.android.sdk.utils.TTFFont;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/extension/CollectionKt.class */
@Metadata(mv = {1, 6, 0}, k = 2, xi = 48, d1 = {"\u0000Ä\u0001\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u001f\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u001f\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000f\n\u0002\b\u000b\n\u0002\u0010\u0000\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\u001a7\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u001e\u0010\u0003\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00050\u0004\"\b\u0012\u0004\u0012\u0002H\u00020\u0005¢\u0006\u0002\u0010\u0006\u001a\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n\u001a'\u0010\f\u001a\u00020\r\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u000e2\b\u0010\u000f\u001a\u0004\u0018\u0001H\u0002¢\u0006\u0002\u0010\u0010\u001a(\u0010\u0011\u001a\u00020\r\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u000e2\u000e\u0010\u0012\u001a\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0005\u001a#\u0010\u0013\u001a\u00020\r\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000e2\u0006\u0010\u0014\u001a\u0002H\u0002¢\u0006\u0002\u0010\u0010\u001a$\u0010\u0013\u001a\u00020\r\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000e2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005\u001a?\u0010\u0016\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00020\u00170\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\nH\u0086\b\u001a\u001e\u0010\u0018\u001a\u00020\u0019*\b\u0012\u0004\u0012\u00020\u001a0\u00012\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0001\u001a!\u0010\u001c\u001a\u00020\u0019*\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u00042\b\u0010\u001d\u001a\u0004\u0018\u00010\u001a¢\u0006\u0002\u0010\u001e\u001a\u0018\u0010\u001c\u001a\u00020\u0019*\b\u0012\u0004\u0012\u00020\u001a0\u00012\u0006\u0010\u001f\u001a\u00020\u001a\u001a\"\u0010 \u001a\u00020\u0019*\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u00052\u000e\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u0005\u001a*\u0010 \u001a\u00020\u0019\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020!2\u0012\u0010\"\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00190#\u001a*\u0010$\u001a\u00020\u0019\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020!2\u0012\u0010\"\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00190#\u001a&\u0010%\u001a\u00020\u0019\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010&\u001a\u0002H\u0002H\u0086\b¢\u0006\u0002\u0010'\u001a\u001c\u0010(\u001a\b\u0012\u0004\u0012\u0002H\u00020)\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001\u001a \u0010*\u001a\b\u0012\u0004\u0012\u0002H\u00020)\"\b\b\u0000\u0010\u0002*\u00020+*\b\u0012\u0004\u0012\u0002H\u00020\u0001\u001a*\u0010,\u001a\b\u0012\u0004\u0012\u0002H\u00020-\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020-2\f\u0010.\u001a\b\u0012\u0004\u0012\u0002H\u00020-\u001a@\u0010,\u001a\u00020\r\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020-2\f\u0010.\u001a\b\u0012\u0004\u0012\u0002H\u00020-2\f\u0010/\u001a\b\u0012\u0004\u0012\u0002H\u0002002\f\u00101\u001a\b\u0012\u0004\u0012\u0002H\u000200\u001aa\u00102\u001a\u00020\r\"\u0004\b\u0000\u00103\"\u0004\b\u0001\u00104\"\u000e\b\u0002\u00105*\b\u0012\u0004\u0012\u0002H40\u000e*\u000e\u0012\u0004\u0012\u0002H3\u0012\u0004\u0012\u0002H5062\u0006\u00107\u001a\u0002H32\u0012\u00108\u001a\u000e\u0012\u0004\u0012\u0002H3\u0012\u0004\u0012\u0002H40#2\f\u00109\u001a\b\u0012\u0004\u0012\u0002H50:¢\u0006\u0002\u0010;\u001a*\u0010<\u001a\b\u0012\u0004\u0012\u0002H\u00020)\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020)0=2\u0006\u00107\u001a\u00020\n\u001aQ\u0010<\u001a\b\u0012\u0004\u0012\u0002H40)\"\u0004\b\u0000\u00103\"\u0004\b\u0001\u00104**\u0012\u0004\u0012\u0002H3\u0012\n\u0012\b\u0012\u0004\u0012\u0002H40)0>j\u0014\u0012\u0004\u0012\u0002H3\u0012\n\u0012\b\u0012\u0004\u0012\u0002H40)`?2\u0006\u00107\u001a\u0002H3¢\u0006\u0002\u0010@\u001a\u001e\u0010<\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0001\u001a0\u0010A\u001a\u000e\u0012\u0004\u0012\u0002H3\u0012\u0004\u0012\u0002H40B\"\u0004\b\u0000\u00103\"\u0004\b\u0001\u00104*\u0010\u0012\u0004\u0012\u0002H3\u0012\u0006\u0012\u0004\u0018\u0001H40B\u001ah\u0010C\u001a\u00020\r\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n26\u0010D\u001a2\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\bF\u0012\b\bG\u0012\u0004\b\b(H\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\bF\u0012\b\bG\u0012\u0004\b\b(I\u0012\u0004\u0012\u00020\r0EH\u0086\bø\u0001\u0000\u001a'\u0010J\u001a\u0004\u0018\u0001H\u0002\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u00012\u0006\u0010K\u001a\u00020\n¢\u0006\u0002\u0010L\u001a/\u0010J\u001a\u0004\u0018\u0001H\u0002\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u00012\u0006\u0010K\u001a\u00020\n2\u0006\u0010M\u001a\u00020\u0019¢\u0006\u0002\u0010N\u001a!\u0010O\u001a\u0004\u0018\u0001H\u0002\"\u0004\b\u0000\u0010\u0002*\f\u0012\u0006\u0012\u0004\u0018\u0001H\u0002\u0018\u00010\u0005¢\u0006\u0002\u0010P\u001a!\u0010Q\u001a\u0004\u0018\u0001H\u0002\"\u0004\b\u0000\u0010\u0002*\f\u0012\u0006\u0012\u0004\u0018\u0001H\u0002\u0018\u00010\u0001¢\u0006\u0002\u0010R\u001a%\u0010S\u001a\u0004\u0018\u0001H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010&\u001a\u0002H\u0002¢\u0006\u0002\u0010T\u001a;\u0010U\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\f\u0012\u0006\u0012\u0004\u0018\u0001H\u0002\u0018\u00010\u00012\u0006\u0010V\u001a\u00020\n2\u0012\u0010W\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u0002H\u00020#¢\u0006\u0002\u0010X\u001a)\u0010Y\u001a\u0004\u0018\u0001H\u0002\"\u0004\b\u0000\u0010\u0002*\f\u0012\u0006\u0012\u0004\u0018\u0001H\u0002\u0018\u00010\u00012\u0006\u0010V\u001a\u00020\n¢\u0006\u0002\u0010L\u001a0\u0010Z\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\b\b\u0001\u0010[\u001a\u00020\n2\b\b\u0001\u0010\\\u001a\u00020\n\u001a%\u0010]\u001a\u0004\u0018\u0001H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010&\u001a\u0002H\u0002¢\u0006\u0002\u0010T\u001a\u0010\u0010^\u001a\u00020\n*\b\u0012\u0002\b\u0003\u0018\u00010\u0005\u001a\u0014\u0010^\u001a\u00020\n*\f\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0018\u00010B\u001a\u0018\u0010_\u001a\u00020\n*\b\u0012\u0004\u0012\u00020\u001a0\u00012\u0006\u0010\u001f\u001a\u00020\u001a\u001a\u0018\u0010`\u001a\u00020\u0019*\b\u0012\u0002\b\u0003\u0018\u00010\u00012\u0006\u0010V\u001a\u00020\n\u001a'\u0010`\u001a\u00020\u0019\"\u0004\b\u0000\u0010\u0002*\f\u0012\u0006\u0012\u0004\u0018\u0001H\u0002\u0018\u00010\u00012\u0006\u0010\u0014\u001a\u0002H\u0002¢\u0006\u0002\u0010'\u001a\u0018\u0010a\u001a\u00020\u0019*\b\u0012\u0002\b\u0003\u0018\u00010\u00012\u0006\u0010V\u001a\u00020\n\u001a'\u0010a\u001a\u00020\u0019\"\u0004\b\u0000\u0010\u0002*\f\u0012\u0006\u0012\u0004\u0018\u0001H\u0002\u0018\u00010\u00012\u0006\u0010\u0014\u001a\u0002H\u0002¢\u0006\u0002\u0010'\u001a!\u0010b\u001a\u00020\u0019*\b\u0012\u0002\b\u0003\u0018\u00010=\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0000\u001a\u0004\b\u0003\u0010\u0000\u001a!\u0010b\u001a\u00020\u0019*\b\u0012\u0002\b\u0003\u0018\u00010\u0005\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0000\u001a\u0004\b\u0003\u0010\u0000\u001a%\u0010b\u001a\u00020\u0019*\f\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0018\u00010B\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0000\u001a\u0004\b\u0003\u0010\u0000\u001a!\u0010c\u001a\u00020\u0019*\b\u0012\u0002\b\u0003\u0018\u00010=\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u001a!\u0010c\u001a\u00020\u0019*\b\u0012\u0002\b\u0003\u0018\u00010\u0005\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u001a%\u0010c\u001a\u00020\u0019*\f\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0018\u00010B\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u001a\u0018\u0010d\u001a\u00020\u0019*\b\u0012\u0002\b\u0003\u0018\u00010\u00052\u0006\u0010V\u001a\u00020\n\u001a\u0017\u0010e\u001a\u0004\u0018\u00010f*\b\u0012\u0004\u0012\u00020f0!¢\u0006\u0002\u0010g\u001aA\u0010h\u001a\u0004\u0018\u0001H\u0002\"\u0004\b\u0000\u0010\u0002\"\u000e\b\u0001\u0010i*\b\u0012\u0004\u0012\u0002Hi0j*\b\u0012\u0004\u0012\u0002H\u00020!2\u0012\u0010k\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002Hi0#¢\u0006\u0002\u0010l\u001a\u0017\u0010m\u001a\u0004\u0018\u00010f*\b\u0012\u0004\u0012\u00020f0!¢\u0006\u0002\u0010g\u001aA\u0010n\u001a\u0004\u0018\u0001H\u0002\"\u0004\b\u0000\u0010\u0002\"\u000e\b\u0001\u0010i*\b\u0012\u0004\u0012\u0002Hi0j*\b\u0012\u0004\u0012\u0002H\u00020!2\u0012\u0010k\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002Hi0#¢\u0006\u0002\u0010l\u001a(\u0010o\u001a\u00020\r\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010)2\u0006\u0010p\u001a\u00020\n2\u0006\u0010q\u001a\u00020\n\u001a-\u0010r\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0086\b\u001a2\u0010s\u001a\u00020\u0019\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000e2\u0006\u0010t\u001a\u00020\u00192\u0012\u0010\"\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00190#\u001aN\u0010u\u001a\u00020\r\"\u0004\b\u0000\u00103\"\b\b\u0001\u00104*\u00020v*\b\u0012\u0004\u0012\u0002H40)2\u0012\u0010w\u001a\u000e\u0012\u0004\u0012\u0002H3\u0012\u0004\u0012\u0002H40B2\u0018\u0010\"\u001a\u0014\u0012\u0004\u0012\u0002H3\u0012\u0004\u0012\u0002H4\u0012\u0004\u0012\u00020\u00190E\u001a.\u0010u\u001a\u00020\r\"\b\b\u0000\u00104*\u00020v*\b\u0012\u0004\u0012\u0002H40)2\u0012\u0010w\u001a\u000e\u0012\u0004\u0012\u0002H4\u0012\u0004\u0012\u0002H40B\u001a0\u0010x\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00190:H\u0086\bø\u0001\u0000\u001a'\u0010y\u001a\u00020\r\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u000e2\b\u0010\u000f\u001a\u0004\u0018\u0001H\u0002¢\u0006\u0002\u0010\u0010\u001a/\u0010y\u001a\u00020\r\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010)2\u0006\u0010V\u001a\u00020\n2\b\u0010\u000f\u001a\u0004\u0018\u0001H\u0002¢\u0006\u0002\u0010z\u001a(\u0010{\u001a\u00020\r\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u000e2\u000e\u0010\u0012\u001a\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0005\u001a,\u0010|\u001a\b\u0012\u0004\u0012\u0002H\u00020)\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020)2\u0006\u0010}\u001a\u00020\n2\u0006\u0010~\u001a\u00020\n\u001a-\u0010\u007f\u001a\u00020\u0019\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000e2\r\u0010\u0080\u0001\u001a\b\u0012\u0004\u0012\u0002H\u00020j2\u0006\u0010t\u001a\u00020\u0019\u001a5\u0010\u0081\u0001\u001a\b\u0012\u0004\u0012\u0002H\u00020)\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0007\u0010\u0082\u0001\u001a\u0002H\u00022\u0007\u0010\u0083\u0001\u001a\u0002H\u0002¢\u0006\u0003\u0010\u0084\u0001\u001a%\u0010\u0081\u0001\u001a\b\u0012\u0004\u0012\u0002H\u00020)\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010}\u001a\u00020\n\u001a-\u0010\u0081\u0001\u001a\b\u0012\u0004\u0012\u0002H\u00020)\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010}\u001a\u00020\n2\u0006\u0010~\u001a\u00020\n\u001a<\u0010\u0085\u0001\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020!2\u001d\u0010\u0086\u0001\u001a\u0018\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0087\u0001j\u000b\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0088\u0001\u001a,\u0010\u0089\u0001\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0007\u0010\u008a\u0001\u001a\u00020\n\u001aF\u0010\u008b\u0001\u001a\u000e\u0012\u0004\u0012\u0002H3\u0012\u0004\u0012\u0002H40B\"\b\b\u0000\u00103*\u00020v\"\b\b\u0001\u00104*\u00020v*\b\u0012\u0004\u0012\u0002H40\u00052\u0013\u0010\u008c\u0001\u001a\u000e\u0012\u0004\u0012\u0002H4\u0012\u0004\u0012\u0002H30#\u001ad\u0010\u008b\u0001\u001a\u000e\u0012\u0004\u0012\u0002H3\u0012\u0004\u0012\u0002Hi0B\"\b\b\u0000\u00103*\u00020v\"\b\b\u0001\u00104*\u00020v\"\b\b\u0002\u0010i*\u00020v*\b\u0012\u0004\u0012\u0002H40\u00052\u0013\u0010\u008c\u0001\u001a\u000e\u0012\u0004\u0012\u0002H4\u0012\u0004\u0012\u0002H30#2\u0012\u00108\u001a\u000e\u0012\u0004\u0012\u0002H4\u0012\u0004\u0012\u0002Hi0#\u001a~\u0010\u008d\u0001\u001a\u000f\u0012\u0005\u0012\u0003H\u008e\u0001\u0012\u0004\u0012\u0002H50B\"\u0005\b\u0000\u0010\u008f\u0001\"\u0005\b\u0001\u0010\u008e\u0001\"\u0004\b\u0002\u00104\"\u000e\b\u0003\u00105*\b\u0012\u0004\u0012\u0002H40\u000e*\t\u0012\u0005\u0012\u0003H\u008f\u00010\u00052\u0015\u0010\u008c\u0001\u001a\u0010\u0012\u0005\u0012\u0003H\u008f\u0001\u0012\u0005\u0012\u0003H\u008e\u00010#2\u0014\u0010\u0090\u0001\u001a\u000f\u0012\u0005\u0012\u0003H\u008f\u0001\u0012\u0004\u0012\u0002H40#2\f\u00109\u001a\b\u0012\u0004\u0012\u0002H50:\u001af\u0010\u0091\u0001\u001a\u0015\u0012\u0005\u0012\u0003H\u008e\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H40\u00010B\"\u0005\b\u0000\u0010\u008f\u0001\"\u0005\b\u0001\u0010\u008e\u0001\"\u0004\b\u0002\u00104*\t\u0012\u0005\u0012\u0003H\u008f\u00010\u00052\u0015\u0010\u008c\u0001\u001a\u0010\u0012\u0005\u0012\u0003H\u008f\u0001\u0012\u0005\u0012\u0003H\u008e\u00010#2\u0014\u0010\u0092\u0001\u001a\u000f\u0012\u0005\u0012\u0003H\u008f\u0001\u0012\u0004\u0012\u0002H40#\u001aD\u0010\u0091\u0001\u001a\u0014\u0012\u0004\u0012\u0002H3\u0012\n\u0012\b\u0012\u0004\u0012\u0002H40\u00010B\"\u0004\b\u0000\u00103\"\u0004\b\u0001\u00104*\b\u0012\u0004\u0012\u0002H30\u00052\u0013\u0010\u0092\u0001\u001a\u000e\u0012\u0004\u0012\u0002H3\u0012\u0004\u0012\u0002H40#\u001a(\u0010\u0093\u0001\u001a\u00020\r\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u000e2\b\u0010\u000f\u001a\u0004\u0018\u0001H\u0002¢\u0006\u0002\u0010\u0010\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0094\u0001"}, d2 = {"mergeList", TTFFont.UNKNOWN_FONT_NAME, "T", "items", TTFFont.UNKNOWN_FONT_NAME, TTFFont.UNKNOWN_FONT_NAME, "([Ljava/util/Collection;)Ljava/util/List;", "swapIndexAsc", TTFFont.UNKNOWN_FONT_NAME, "startIndex", TTFFont.UNKNOWN_FONT_NAME, "endIndex", StatisticsEntity.ACTION_ADD, TTFFont.UNKNOWN_FONT_NAME, TTFFont.UNKNOWN_FONT_NAME, "target", "(Ljava/util/Collection;Ljava/lang/Object;)V", "addAll", "targetList", "addIfNotContains", "t", "list", "allPairs", "Landroid/util/Pair;", "containAnyIgnoreCase", TTFFont.UNKNOWN_FONT_NAME, TTFFont.UNKNOWN_FONT_NAME, "elements", "containIgnoreCase", "string", "([Ljava/lang/String;Ljava/lang/String;)Z", "element", "contains", TTFFont.UNKNOWN_FONT_NAME, "predicate", "Lkotlin/Function1;", "containsAll", "containsByInstance", "item", "(Ljava/util/List;Ljava/lang/Object;)Z", "copy", TTFFont.UNKNOWN_FONT_NAME, "copyList", "Lcom/onyx/android/sdk/data/Copier;", "diff", TTFFont.UNKNOWN_FONT_NAME, "second", "firstOnly", TTFFont.UNKNOWN_FONT_NAME, "secondOnly", "ensureAdd", "K", "V", "C", TTFFont.UNKNOWN_FONT_NAME, "key", "valueFunc", "collectionGenerator", "Lkotlin/Function0;", "(Ljava/util/Map;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;)V", "ensureList", "Landroid/util/SparseArray;", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "(Ljava/util/HashMap;Ljava/lang/Object;)Ljava/util/List;", "filterNotNullValues", TTFFont.UNKNOWN_FONT_NAME, "forEachPair", "handler", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "previous", "next", "getElement", "currentIndex", "(Ljava/util/List;I)Ljava/lang/Object;", "preElement", "(Ljava/util/List;IZ)Ljava/lang/Object;", "getFirst", "(Ljava/util/Collection;)Ljava/lang/Object;", "getLast", "(Ljava/util/List;)Ljava/lang/Object;", "getNextItem", "(Ljava/util/List;Ljava/lang/Object;)Ljava/lang/Object;", "getOrElse", "index", "defaultValue", "(Ljava/util/List;ILkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "getOrFirst", "getPagedSubList", "pageIndex", "pageSize", "getPreviousItem", "getSize", "indexIgnoreCase", "isFirstElement", "isLastElement", "isNonBlank", "isNullOrEmpty", "isOutOfRange", "max", TTFFont.UNKNOWN_FONT_NAME, "(Ljava/lang/Iterable;)Ljava/lang/Float;", "maxBy", "R", TTFFont.UNKNOWN_FONT_NAME, "selector", "(Ljava/lang/Iterable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "min", "minBy", "moveTo", "from", "to", "removeAllByInstance", "removeIf", "abortFirstMatched", "replace", TTFFont.UNKNOWN_FONT_NAME, "map", "reverseIf", "safeAdd", "(Ljava/util/List;ILjava/lang/Object;)V", "safeAddAll", "safelyDetachList", "fromIndex", "toIndex", "safelyRemove", "comparable", "safelySubList", "start", "end", "(Ljava/util/List;Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/List;", "sortedWith", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "split", "batch", "toMap", "keyFunc", "toMapCollection", "K2", "K1", "valFunc", "toMapList", "func", "toggle", "onyxsdk-base_release"})
public final class CollectionKt {

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/extension/CollectionKt$a.class */
    @Metadata(mv = {1, 6, 0}, k = 3, xi = 48, d1 = {"\u0000\f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u0002H\u0001H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "T", "Lcom/onyx/android/sdk/data/Copier;", "it", "invoke", "(Lcom/onyx/android/sdk/data/Copier;)Lcom/onyx/android/sdk/data/Copier;"})
    static final class a<T extends Copier> implements Function1<T, T> {
        public static final a a = new a();

        a() {
        }

        /* JADX WARN: Incorrect return type in method signature: (TT;)TT; */
        @NotNull
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public final T invoke(@NotNull T it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return (T) it.copy();
        }
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/extension/CollectionKt$b.class */
    @Metadata(mv = {1, 6, 0}, k = 3, xi = 48, d1 = {"\u0000\f\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "T", "it", TTFFont.UNKNOWN_FONT_NAME, "invoke", "(I)Ljava/lang/Object;"})
    static final class b<T> implements Function1<Integer, T> {
        final /* synthetic */ List<? extends T> a;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        b(List<? extends T> list) {
            this.a = list;
        }

        @Nullable
        public final T invoke(Integer i) {
            return (T) CollectionKt.getFirst(this.a);
        }
    }

    /* JADX INFO: Add missing generic type declarations: [V] */
    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/extension/CollectionKt$c.class */
    @Metadata(mv = {1, 6, 0}, k = 3, xi = 48, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u0002H\u00022\u0006\u0010\u0005\u001a\u0002H\u0002H\n¢\u0006\u0004\b\u0006\u0010\u0007"}, d2 = {"<anonymous>", TTFFont.UNKNOWN_FONT_NAME, "V", TTFFont.UNKNOWN_FONT_NAME, "k", "v", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Boolean;"})
    static final class c<V> implements Function2<V, V, Boolean> {
        public static final c a = new c();

        c() {
        }

        @NotNull
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public final Boolean invoke(@NotNull V v, @NotNull V v2) {
            Intrinsics.checkNotNullParameter(v, "k");
            Intrinsics.checkNotNullParameter(v2, "v");
            return Boolean.valueOf(Intrinsics.areEqual(v, v2));
        }
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/extension/CollectionKt$d.class */
    @Metadata(mv = {1, 6, 0}, k = 3, xi = 48, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", TTFFont.UNKNOWN_FONT_NAME, "T", "it", "invoke", "(Ljava/lang/Object;)Ljava/lang/Boolean;"})
    static final class d<T> implements Function1<T, Boolean> {
        final /* synthetic */ Comparable<? super T> a;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        d(Comparable<? super T> comparable) {
            this.a = comparable;
        }

        @NotNull
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public final Boolean invoke(T t) {
            return Boolean.valueOf(this.a.compareTo(t) == 0);
        }
    }

    /* JADX INFO: Add missing generic type declarations: [V] */
    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/extension/CollectionKt$e.class */
    @Metadata(mv = {1, 6, 0}, k = 3, xi = 48, d1 = {"\u0000\f\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0003\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003\"\b\b\u0001\u0010\u0001*\u00020\u00032\u0006\u0010\u0004\u001a\u0002H\u0001H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "V", "K", TTFFont.UNKNOWN_FONT_NAME, "it", "invoke", "(Ljava/lang/Object;)Ljava/lang/Object;"})
    static final class e<V> implements Function1<V, V> {
        public static final e a = new e();

        e() {
        }

        @NotNull
        public final V invoke(@NotNull V v) {
            Intrinsics.checkNotNullParameter(v, "it");
            return v;
        }
    }

    /* JADX INFO: Add missing generic type declarations: [V, K2] */
    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/extension/CollectionKt$f.class */
    @Metadata(mv = {1, 6, 0}, k = 3, xi = 48, d1 = {"\u0000\f\n\u0002\b\u0005\n\u0002\u0010\u001f\n\u0002\b\u0003\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0001\"\u000e\b\u0003\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00010\u00052\u0006\u0010\u0006\u001a\u0002H\u0003H\n¢\u0006\u0004\b\u0007\u0010\b"}, d2 = {"<anonymous>", "V", "K1", "K2", "C", TTFFont.UNKNOWN_FONT_NAME, "it", "invoke", "(Ljava/lang/Object;)Ljava/lang/Object;"})
    static final class f<K1, K2, V> implements Function1<K2, V> {
        final /* synthetic */ Function1<? super K1, ? extends V> a;
        final /* synthetic */ K1 b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        f(Function1<? super K1, ? extends V> function1, K1 k1) {
            this.a = function1;
            this.b = k1;
        }

        public final V invoke(K2 k2) {
            return (V) this.a.invoke(this.b);
        }
    }

    /* JADX INFO: Add missing generic type declarations: [V] */
    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/extension/CollectionKt$g.class */
    @Metadata(mv = {1, 6, 0}, k = 3, xi = 48, d1 = {"\u0000\n\n\u0000\n\u0002\u0010!\n\u0002\b\u0004\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0003\"\u0004\b\u0001\u0010\u0004\"\u0004\b\u0002\u0010\u0002H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", TTFFont.UNKNOWN_FONT_NAME, "V", "K1", "K2", "invoke"})
    static final class g<V> implements Function0<List<V>> {
        public static final g a = new g();

        g() {
        }

        @NotNull
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public final List<V> invoke() {
            return new ArrayList();
        }
    }

    /* JADX INFO: Add missing generic type declarations: [K] */
    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/extension/CollectionKt$h.class */
    @Metadata(mv = {1, 6, 0}, k = 3, xi = 48, d1 = {"\u0000\u0004\n\u0002\b\u0006\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001\"\u0004\b\u0001\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0001H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "K", "V", "it", "invoke", "(Ljava/lang/Object;)Ljava/lang/Object;"})
    static final class h<K> implements Function1<K, K> {
        public static final h a = new h();

        h() {
        }

        public final K invoke(K k) {
            return k;
        }
    }

    public static final boolean isNullOrEmpty(@Nullable Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static final boolean isNonBlank(@Nullable Collection<?> collection) {
        return !isNullOrEmpty(collection);
    }

    public static final int getSize(@Nullable Collection<?> collection) {
        if (collection == null) {
            return 0;
        }
        return collection.size();
    }

    public static final <T> void toggle(@Nullable Collection<T> collection, @Nullable T t) {
        if (collection == null || TypeIntrinsics.asMutableCollection(collection).remove(t)) {
            return;
        }
        safeAdd(collection, t);
    }

    public static final <T> void addIfNotContains(@NotNull Collection<T> collection, T t) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        if (collection.contains(t)) {
            return;
        }
        safeAdd(collection, t);
    }

    public static final <V> void replace(@NotNull List<V> list, @NotNull Map<V, ? extends V> map) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(map, "map");
        replace(list, map, c.a);
    }

    @NotNull
    public static final <K, V> Map<K, V> filterNotNullValues(@NotNull Map<K, ? extends V> map) {
        Intrinsics.checkNotNullParameter(map, "<this>");
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<K, ? extends V> entry : map.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            Pair pair = value == null ? null : TuplesKt.to(key, value);
            if (pair != null) {
                arrayList.add(pair);
            }
        }
        return MapsKt.toMap(arrayList);
    }

    @NotNull
    public static final <T> List<T> copy(@NotNull List<? extends T> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        return CollectionsKt.toMutableList(list);
    }

    @NotNull
    public static final <K1, K2, V> Map<K2, List<V>> toMapList(@NotNull Collection<? extends K1> collection, @NotNull Function1<? super K1, ? extends K2> function1, @NotNull Function1<? super K1, ? extends V> function2) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        Intrinsics.checkNotNullParameter(function1, "keyFunc");
        Intrinsics.checkNotNullParameter(function2, "func");
        return toMapCollection(collection, function1, function2, g.a);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public static final <K, V, R> Map<K, R> toMap(@NotNull Collection<? extends V> collection, @NotNull Function1<? super V, ? extends K> function1, @NotNull Function1<? super V, ? extends R> function2) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        Intrinsics.checkNotNullParameter(function1, "keyFunc");
        Intrinsics.checkNotNullParameter(function2, "valueFunc");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (isNonBlank(collection)) {
            for (V v : collection) {
                linkedHashMap.put(function1.invoke(v), function2.invoke(v));
            }
        }
        return linkedHashMap;
    }

    @NotNull
    public static final <K1, K2, V, C extends Collection<V>> Map<K2, C> toMapCollection(@NotNull Collection<? extends K1> collection, @NotNull Function1<? super K1, ? extends K2> function1, @NotNull Function1<? super K1, ? extends V> function2, @NotNull Function0<? extends C> function0) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        Intrinsics.checkNotNullParameter(function1, "keyFunc");
        Intrinsics.checkNotNullParameter(function2, "valFunc");
        Intrinsics.checkNotNullParameter(function0, "collectionGenerator");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (K1 obj : collection) {
            ensureAdd(linkedHashMap, function1.invoke(obj), new f(function2, obj), function0);
        }
        return linkedHashMap;
    }

    public static final <K, V, C extends Collection<V>> void ensureAdd(@NotNull Map<K, C> map, K k, @NotNull Function1<? super K, ? extends V> function1, @NotNull Function0<? extends C> function0) {
        Intrinsics.checkNotNullParameter(map, "<this>");
        Intrinsics.checkNotNullParameter(function1, "valueFunc");
        Intrinsics.checkNotNullParameter(function0, "collectionGenerator");
        safeAdd(map.computeIfAbsent(k, (v1) -> {
            return (C) a(function0, v1);
        }), function1.invoke(k));
    }

    public static final <T> void safeAddAll(@Nullable Collection<T> collection, @Nullable Collection<? extends T> collection2) {
        addAll(collection, collection2);
    }

    public static final <T> void addAll(@Nullable Collection<T> collection, @Nullable Collection<? extends T> collection2) {
        if (collection == null || isNullOrEmpty(collection2)) {
            return;
        }
        collection.addAll(collection2);
    }

    public static final <T> void safeAdd(@Nullable Collection<T> collection, @Nullable T t) {
        add(collection, t);
    }

    @Nullable
    public static final <T> T getPreviousItem(@NotNull List<? extends T> list, T t) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        int i = 0;
        Iterator<? extends T> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                i = -1;
                break;
            }
            if (it.next() == t) {
                break;
            }
            i++;
        }
        if (i <= 0) {
            return null;
        }
        return (T) CollectionsKt.getOrNull(list, i - 1);
    }

    @Nullable
    public static final <T> T getNextItem(@NotNull List<? extends T> list, T t) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        int i = 0;
        Iterator<? extends T> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                i = -1;
                break;
            }
            if (it.next() == t) {
                break;
            }
            i++;
        }
        if (i < 0 || i >= list.size() - 1) {
            return null;
        }
        return (T) CollectionsKt.getOrNull(list, i + 1);
    }

    public static final <T> void moveTo(@Nullable List<T> list, int from, int to) {
        if (list == null || isNullOrEmpty(list) || isOutOfRange(list, from) || isOutOfRange(list, to) || from == to) {
            return;
        }
        list.add(to, list.remove(from));
    }

    @NotNull
    public static final <T extends Copier> List<T> copyList(@NotNull List<? extends T> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        return SequencesKt.toMutableList(SequencesKt.map(CollectionsKt.asSequence(list), a.a));
    }

    public static final <T> void add(@Nullable Collection<T> collection, @Nullable T t) {
        if (collection == null || t == null) {
            return;
        }
        collection.add(t);
    }

    @Nullable
    public static final <T> T getElement(@Nullable List<? extends T> list, int i, boolean z) {
        int i2 = z ? i - 1 : i + 1;
        if (list == null) {
            return null;
        }
        return (T) CollectionsKt.getOrNull(list, i2);
    }

    @Nullable
    public static final <T> T getLast(@Nullable List<? extends T> list) {
        if (isNullOrEmpty(list)) {
            return null;
        }
        return list.get(getSize(list) - 1);
    }

    @Nullable
    public static final <T> T getFirst(@Nullable Collection<? extends T> collection) {
        if (isNullOrEmpty(collection)) {
            return null;
        }
        return collection.iterator().next();
    }

    public static final boolean isOutOfRange(@Nullable Collection<?> collection, int index) {
        return index < 0 || index >= getSize(collection);
    }

    @NotNull
    public static final <T> List<T> ensureList(@Nullable List<? extends T> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        return (List<T>) list;
    }

    public static final boolean isLastElement(@Nullable List<?> list, int index) {
        return index + 1 == getSize(list);
    }

    public static final boolean isFirstElement(@Nullable List<?> list, int index) {
        return !isNullOrEmpty(list) && index == 0;
    }

    public static final <T> T getOrElse(@Nullable List<? extends T> list, int i, @NotNull Function1<? super Integer, ? extends T> function1) {
        Intrinsics.checkNotNullParameter(function1, "defaultValue");
        Object element = getElement(list, i);
        Object objInvoke = element;
        if (element == null) {
            objInvoke = function1.invoke(Integer.valueOf(i));
        }
        return (T) objInvoke;
    }

    @Nullable
    public static final <T> T getOrFirst(@Nullable List<? extends T> list, int i) {
        return (T) getOrElse(list, i, new b(list));
    }

    public static final <T> boolean removeIf(@NotNull Collection<T> collection, boolean abortFirstMatched, @NotNull Function1<? super T, Boolean> function1) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        Intrinsics.checkNotNullParameter(function1, "predicate");
        boolean z = false;
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            if (((Boolean) function1.invoke(it.next())).booleanValue()) {
                z = true;
                it.remove();
                if (abortFirstMatched) {
                    break;
                }
            }
        }
        return z;
    }

    public static final <T> boolean safelyRemove(@NotNull Collection<T> collection, @NotNull Comparable<? super T> comparable, boolean abortFirstMatched) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        Intrinsics.checkNotNullParameter(comparable, "comparable");
        return removeIf(collection, abortFirstMatched, new d(comparable));
    }

    @NotNull
    public static final <T> List<T> mergeList(@NotNull Collection<? extends T>... collectionArr) {
        Intrinsics.checkNotNullParameter(collectionArr, "items");
        ArrayList arrayList = new ArrayList();
        for (Collection<? extends T> collection : collectionArr) {
            arrayList.addAll(collection);
        }
        return arrayList;
    }

    @NotNull
    public static final <T> List<T> safelySubList(@NotNull List<? extends T> list, T t, T t2) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        int iIndexOf = list.indexOf(t);
        int iIndexOf2 = list.indexOf(t2);
        return iIndexOf > iIndexOf2 ? safelySubList(list, iIndexOf2, iIndexOf) : safelySubList(list, iIndexOf, iIndexOf2);
    }

    @NotNull
    public static final <T> List<T> getPagedSubList(@NotNull List<? extends T> list, @IntRange(from = 0) int pageIndex, @IntRange(from = 1) int pageSize) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        int i = pageIndex * pageSize;
        return safelySubList(list, i, i + pageSize);
    }

    @NotNull
    public static final <T> List<T> safelyDetachList(@NotNull List<T> list, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        List<T> listSafelySubList = safelySubList(list, fromIndex, toIndex);
        list.removeAll(listSafelySubList);
        return listSafelySubList;
    }

    public static final int indexIgnoreCase(@NotNull List<String> list, @NotNull String element) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(element, "element");
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String lowerCase = it.next().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            arrayList.add(lowerCase);
        }
        String lowerCase2 = element.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        return arrayList.indexOf(lowerCase2);
    }

    public static final boolean containIgnoreCase(@Nullable String[] $this$containIgnoreCase, @Nullable String string) {
        if ($this$containIgnoreCase == null || string == null) {
            return false;
        }
        return containIgnoreCase((List<String>) kotlin.collections.ArraysKt.toList($this$containIgnoreCase), string);
    }

    @Nullable
    public static final <T, R extends Comparable<? super R>> T minBy(@NotNull Iterable<? extends T> iterable, @NotNull Function1<? super T, ? extends R> function1) {
        T t;
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(function1, "selector");
        Iterator<? extends T> it = iterable.iterator();
        if (it.hasNext()) {
            T next = it.next();
            if (it.hasNext()) {
                Comparable comparable = (Comparable) function1.invoke(next);
                while (true) {
                    Comparable comparable2 = comparable;
                    T next2 = it.next();
                    t = next2;
                    Comparable comparable3 = (Comparable) function1.invoke(next2);
                    Comparable comparable4 = comparable3;
                    if (comparable2.compareTo(comparable3) <= 0) {
                        t = next;
                        comparable4 = comparable2;
                    }
                    if (!it.hasNext()) {
                        break;
                    }
                    comparable = comparable4;
                    next = t;
                }
            } else {
                t = next;
            }
        } else {
            t = null;
        }
        return t;
    }

    @Nullable
    public static final <T, R extends Comparable<? super R>> T maxBy(@NotNull Iterable<? extends T> iterable, @NotNull Function1<? super T, ? extends R> function1) {
        T t;
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(function1, "selector");
        Iterator<? extends T> it = iterable.iterator();
        if (it.hasNext()) {
            T next = it.next();
            if (it.hasNext()) {
                Comparable comparable = (Comparable) function1.invoke(next);
                while (true) {
                    Comparable comparable2 = comparable;
                    T next2 = it.next();
                    t = next2;
                    Comparable comparable3 = (Comparable) function1.invoke(next2);
                    Comparable comparable4 = comparable3;
                    if (comparable2.compareTo(comparable3) >= 0) {
                        t = next;
                        comparable4 = comparable2;
                    }
                    if (!it.hasNext()) {
                        break;
                    }
                    comparable = comparable4;
                    next = t;
                }
            } else {
                t = next;
            }
        } else {
            t = null;
        }
        return t;
    }

    public static final boolean contains(@Nullable Collection<String> collection, @Nullable Collection<String> collection2) {
        if (collection == null && collection2 == null) {
            return true;
        }
        if (collection == null || collection2 == null) {
            return false;
        }
        Iterator<String> it = collection2.iterator();
        while (it.hasNext()) {
            if (collection.contains(it.next())) {
                return true;
            }
        }
        return false;
    }

    @NotNull
    public static final <T> List<List<T>> split(@NotNull List<? extends T> list, int batch) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        ArrayList arrayList = new ArrayList();
        int size = list.size() / batch;
        if (list.size() % batch != 0) {
            size++;
        }
        int i = 0;
        while (i < size) {
            int i2 = i;
            i++;
            int i3 = i2 * batch;
            arrayList.add(list.subList(i3, Math.min(i3 + batch, list.size())));
        }
        return arrayList;
    }

    @NotNull
    public static final <T> List<android.util.Pair<T, T>> allPairs(@NotNull List<? extends T> list, int startIndex, int endIndex) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        if (endIndex > list.size() - 1 || startIndex >= endIndex) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        while (startIndex < endIndex) {
            int i = startIndex + 1;
            arrayList.add(new android.util.Pair(list.get(startIndex), list.get(i)));
            startIndex = i;
        }
        return arrayList;
    }

    public static /* synthetic */ List allPairs$default(List $this$allPairs_u24default, int startIndex, int endIndex, int i, Object obj) {
        if ((i & 1) != 0) {
            startIndex = 0;
        }
        if ((i & 2) != 0) {
            endIndex = $this$allPairs_u24default.size() - 1;
        }
        int i2 = endIndex;
        Intrinsics.checkNotNullParameter($this$allPairs_u24default, "<this>");
        if (i2 > $this$allPairs_u24default.size() - 1 || startIndex >= endIndex) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        while (startIndex < endIndex) {
            int i3 = startIndex + 1;
            arrayList.add(new android.util.Pair($this$allPairs_u24default.get(startIndex), $this$allPairs_u24default.get(i3)));
            startIndex = i3;
        }
        return arrayList;
    }

    public static final <T> void forEachPair(@NotNull List<? extends T> list, int startIndex, int endIndex, @NotNull Function2<? super T, ? super T, Unit> function2) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(function2, "handler");
        if (endIndex > list.size() - 1 || startIndex >= endIndex) {
            return;
        }
        while (startIndex < endIndex) {
            int i = startIndex + 1;
            function2.invoke(list.get(startIndex), list.get(i));
            startIndex = i;
        }
    }

    public static /* synthetic */ void forEachPair$default(List $this$forEachPair_u24default, int startIndex, int endIndex, Function2 handler, int i, Object obj) {
        if ((i & 1) != 0) {
            startIndex = 0;
        }
        if ((i & 2) != 0) {
            endIndex = $this$forEachPair_u24default.size() - 1;
        }
        int i2 = endIndex;
        Intrinsics.checkNotNullParameter($this$forEachPair_u24default, "<this>");
        Intrinsics.checkNotNullParameter(handler, "handler");
        if (i2 > $this$forEachPair_u24default.size() - 1 || startIndex >= endIndex) {
            return;
        }
        while (startIndex < endIndex) {
            int i3 = startIndex + 1;
            handler.invoke($this$forEachPair_u24default.get(startIndex), $this$forEachPair_u24default.get(i3));
            startIndex = i3;
        }
    }

    public static final <T> boolean containsAll(@NotNull Iterable<? extends T> iterable, @NotNull Function1<? super T, Boolean> function1) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(function1, "predicate");
        int iCount = CollectionsKt.count(iterable);
        ArrayList arrayList = new ArrayList();
        for (T t : iterable) {
            if (((Boolean) function1.invoke(t)).booleanValue()) {
                arrayList.add(t);
            }
        }
        return getSize(arrayList) == iCount;
    }

    @NotNull
    public static final int[] swapIndexAsc(int startIndex, int endIndex) {
        return startIndex <= endIndex ? new int[]{startIndex, endIndex} : new int[]{endIndex, startIndex};
    }

    @NotNull
    public static final <T> List<T> reverseIf(@NotNull List<? extends T> list, @NotNull Function0<Boolean> function0) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(function0, "predicate");
        if (((Boolean) function0.invoke()).booleanValue()) {
            list = CollectionsKt.reversed(list);
        }
        return (List<T>) list;
    }

    @Nullable
    public static final Float max(@NotNull Iterable<Float> iterable) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        return CollectionsKt.maxOrNull(iterable);
    }

    @Nullable
    public static final Float min(@NotNull Iterable<Float> iterable) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        return CollectionsKt.minOrNull(iterable);
    }

    public static final boolean containAnyIgnoreCase(@NotNull List<String> list, @NotNull List<String> list2) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(list2, "elements");
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String lowerCase = it.next().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            arrayList.add(lowerCase);
        }
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<String> it2 = list2.iterator();
        while (it2.hasNext()) {
            String lowerCase2 = it2.next().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            arrayList2.add(lowerCase2);
        }
        return contains(arrayList, arrayList2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public static final <T> List<T> sortedWith(@NotNull Iterable<? extends T> iterable, @NotNull Comparator<? super T> comparator) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        if (!(iterable instanceof Collection)) {
            List<T> mutableList = CollectionsKt.toMutableList(iterable);
            CollectionsKt.sortWith(mutableList, comparator);
            return mutableList;
        }
        Collection collection = (Collection) iterable;
        if (collection.size() <= 1) {
            return CollectionsKt.toList(iterable);
        }
        List<T> result = new ArrayList<>((Collection<? extends T>) collection);
        result.sort(comparator);
        return result;
    }

    @NotNull
    public static final <T> Set<T> diff(@NotNull Set<? extends T> set, @NotNull Set<? extends T> set2) {
        Intrinsics.checkNotNullParameter(set, "<this>");
        Intrinsics.checkNotNullParameter(set2, "second");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        diff(set, set2, linkedHashSet, new LinkedHashSet());
        return linkedHashSet;
    }

    public static final <T> boolean containsByInstance(@NotNull List<? extends T> list, T t) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<? extends T> it = list.iterator();
            while (it.hasNext()) {
                if (it.next() == t) {
                    return true;
                }
            }
        }
        return false;
    }

    @NotNull
    public static final <T> List<T> removeAllByInstance(@NotNull List<? extends T> list, @NotNull List<? extends T> list2) {
        boolean z;
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(list2, "items");
        ArrayList arrayList = new ArrayList();
        Iterator<? extends T> it = list.iterator();
        while (it.hasNext()) {
            T next = it.next();
            if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                Iterator<? extends T> it2 = list2.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        z = false;
                        break;
                    }
                    if (it2.next() == next) {
                        z = true;
                        break;
                    }
                }
            } else {
                z = false;
                break;
            }
            if (!z) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    private static final Collection a(Function0 $collectionGenerator, Object it) {
        Intrinsics.checkNotNullParameter($collectionGenerator, "$collectionGenerator");
        return (Collection) $collectionGenerator.invoke();
    }

    public static final boolean isNullOrEmpty(@Nullable SparseArray<?> sparseArray) {
        return sparseArray == null || sparseArray.size() <= 0;
    }

    public static final boolean isNonBlank(@Nullable SparseArray<?> sparseArray) {
        return !isNullOrEmpty(sparseArray);
    }

    public static final int getSize(@Nullable Map<?, ?> map) {
        if (map == null) {
            return 0;
        }
        return map.size();
    }

    public static final <K, V> void replace(@NotNull List<V> list, @NotNull Map<K, ? extends V> map, @NotNull Function2<? super K, ? super V, Boolean> function2) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(map, "map");
        Intrinsics.checkNotNullParameter(function2, "predicate");
        for (Map.Entry<K, ? extends V> entry : map.entrySet()) {
            int i = 0;
            Iterator<V> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    i = -1;
                    break;
                }
                if (((Boolean) function2.invoke(entry.getKey(), it.next())).booleanValue()) {
                    break;
                } else {
                    i++;
                }
            }
            if (i >= 0) {
                list.set(i, entry.getValue());
            }
        }
    }

    @NotNull
    public static final <K, V> Map<K, List<V>> toMapList(@NotNull Collection<? extends K> collection, @NotNull Function1<? super K, ? extends V> function1) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        Intrinsics.checkNotNullParameter(function1, "func");
        return toMapList(collection, h.a, function1);
    }

    public static final <T> void safeAdd(@Nullable List<T> list, int index, @Nullable T t) {
        if (t == null || list == null) {
            return;
        }
        list.add(MathUtils.clamp(index, 0, Math.max(0, list.size() - 1)), t);
    }

    @Nullable
    public static final <T> T getElement(@Nullable List<? extends T> list, int i) {
        if (isOutOfRange(list, i) || list == null) {
            return null;
        }
        return (T) CollectionsKt.getOrNull(list, i);
    }

    @NotNull
    public static final <K, V> List<V> ensureList(@NotNull HashMap<K, List<V>> map, K k) {
        Intrinsics.checkNotNullParameter(map, "<this>");
        List<V> listComputeIfAbsent = map.computeIfAbsent(k, CollectionKt::a);
        Intrinsics.checkNotNullExpressionValue(listComputeIfAbsent, "computeIfAbsent(key) {\n        ArrayList()\n    }");
        return listComputeIfAbsent;
    }

    public static final <T> boolean isLastElement(@Nullable List<? extends T> list, T t) {
        int iIndexOf;
        return !isNullOrEmpty(list) && (iIndexOf = list.indexOf(t)) >= 0 && iIndexOf + 1 == getSize(list);
    }

    public static final <T> boolean isFirstElement(@Nullable List<? extends T> list, T t) {
        return !isNullOrEmpty(list) && list.indexOf(t) == 0;
    }

    public static final boolean containIgnoreCase(@NotNull List<String> list, @NotNull String element) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(element, "element");
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String lowerCase = it.next().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            arrayList.add(lowerCase);
        }
        String lowerCase2 = element.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        return arrayList.contains(lowerCase2);
    }

    private static final List a(Object it) {
        return new ArrayList();
    }

    public static final boolean isNullOrEmpty(@Nullable Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static final boolean isNonBlank(@Nullable Map<?, ?> map) {
        return !isNullOrEmpty(map);
    }

    public static final <T> void addIfNotContains(@NotNull Collection<T> collection, @NotNull Collection<? extends T> collection2) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        Intrinsics.checkNotNullParameter(collection2, "list");
        Iterator<? extends T> it = collection2.iterator();
        while (it.hasNext()) {
            addIfNotContains(collection, it.next());
        }
    }

    public static final <T> void diff(@NotNull Set<? extends T> set, @NotNull Set<? extends T> set2, @NotNull Set<T> set3, @NotNull Set<T> set4) {
        Intrinsics.checkNotNullParameter(set, "<this>");
        Intrinsics.checkNotNullParameter(set2, "second");
        Intrinsics.checkNotNullParameter(set3, "firstOnly");
        Intrinsics.checkNotNullParameter(set4, "secondOnly");
        for (T t : set2) {
            if (!set.contains(t)) {
                set4.add(t);
            }
        }
        for (T t2 : set) {
            if (!set2.contains(t2)) {
                set3.add(t2);
            }
        }
    }

    public static final <T> boolean contains(@NotNull Iterable<? extends T> iterable, @NotNull Function1<? super T, Boolean> function1) {
        T t;
        T next;
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(function1, "predicate");
        Iterator<? extends T> it = iterable.iterator();
        do {
            if (!it.hasNext()) {
                t = null;
                break;
            }
            next = it.next();
            t = next;
        } while (!((Boolean) function1.invoke(next)).booleanValue());
        return t != null;
    }

    @NotNull
    public static final <T> List<T> ensureList(@NotNull SparseArray<List<T>> sparseArray, int key) {
        Intrinsics.checkNotNullParameter(sparseArray, "<this>");
        List<T> list = sparseArray.get(key);
        List<T> list2 = list;
        if (list == null) {
            list2 = new ArrayList<>();
            sparseArray.put(key, list2);
        }
        return list2;
    }

    @NotNull
    public static final <K, V> Map<K, V> toMap(@NotNull Collection<? extends V> collection, @NotNull Function1<? super V, ? extends K> function1) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        Intrinsics.checkNotNullParameter(function1, "keyFunc");
        return toMap(collection, function1, e.a);
    }

    @NotNull
    public static final <T> List<T> safelySubList(@NotNull List<? extends T> list, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        if (isNullOrEmpty(list)) {
            return new ArrayList();
        }
        int size = getSize(list);
        return CollectionsKt.toMutableList(list.subList(MathUtils.clamp(fromIndex, 0, size), MathUtils.clamp(toIndex, 0, size)));
    }

    @NotNull
    public static final <T> List<T> safelySubList(@NotNull List<? extends T> list, int fromIndex) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        return safelySubList(list, fromIndex, getSize(list));
    }
}
