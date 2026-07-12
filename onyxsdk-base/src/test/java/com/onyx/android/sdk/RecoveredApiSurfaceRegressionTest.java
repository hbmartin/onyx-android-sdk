package com.onyx.android.sdk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * Pins the repaired JVM surface of the recovered base module: Kotlin
 * {@code $default} bridges, companion fields, synthetic constructors and
 * accessors, access flags, generic signatures, and kotlin.Metadata that were
 * restored to match the reference SDK bytecode. Generated from the classified
 * reference audit; regenerate with device-validation/classify_api_differences.py
 * if the reference surface ever changes.
 */
public class RecoveredApiSurfaceRegressionTest {

    @Test
    public void commonCollections() throws Exception {
        // com.onyx.android.sdk.common.collections.StringIndexedArrayList
        assertClassfileContains("com.onyx.android.sdk.common.collections.StringIndexedArrayList", "(Ljava/util/Collection<+Ljava/lang/Object;>;)Z");
        // com.onyx.android.sdk.common.request.WakelockTimer
        assertMethod("com.onyx.android.sdk.common.request.WakelockTimer", "access$getWakeLockHolder$p", "(Lcom/onyx/android/sdk/common/request/WakelockTimer;)Lcom/onyx/android/sdk/common/request/WakeLockHolder;", Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL);
        // com.onyx.android.sdk.common.request.WakelockTimer$Companion
        assertMetadataHashes("com.onyx.android.sdk.common.request.WakelockTimer$Companion", "6728d423961d44047f0f75abc6d809925f05d21f40eee3c29cb8542d7a1fbd86", "b9ae3dcc7e5742b2f4f16d9fc2fd9020583260c0b2730ea897a87a1f83a11c76");
    }

    @Test
    public void dataModels() throws Exception {
        // com.onyx.android.sdk.data.DelegatedValueProvider$DefaultImpls
        assertNoVisibleConstructor("com.onyx.android.sdk.data.DelegatedValueProvider$DefaultImpls", "()V");
        // com.onyx.android.sdk.data.Orientation
        assertField("com.onyx.android.sdk.data.Orientation", "Companion", "Lcom/onyx/android/sdk/data/Orientation$Companion;", Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL);
        assertNoVisibleField("com.onyx.android.sdk.data.Orientation", "INSTANCE", "Lcom/onyx/android/sdk/data/Orientation$Companion;");
        // com.onyx.android.sdk.data.ProgressInfo
        assertField("com.onyx.android.sdk.data.ProgressInfo", "Companion", "Lcom/onyx/android/sdk/data/ProgressInfo$Companion;", Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL);
        assertNoVisibleField("com.onyx.android.sdk.data.ProgressInfo", "INSTANCE", "Lcom/onyx/android/sdk/data/ProgressInfo$Companion;");
        // com.onyx.android.sdk.data.ShapeDocumentArgs$Companion
        assertConstructor("com.onyx.android.sdk.data.ShapeDocumentArgs$Companion", "(Lkotlin/jvm/internal/DefaultConstructorMarker;)V", Modifier.PUBLIC);
        // com.onyx.android.sdk.data.StashShapeResourceArgs
        assertMethod("com.onyx.android.sdk.data.StashShapeResourceArgs", "getStashResourceFileName$default", "(Lcom/onyx/android/sdk/data/StashShapeResourceArgs;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;", Modifier.PUBLIC | Modifier.STATIC);
        // com.onyx.android.sdk.data.Tree
        assertMetadataHashes("com.onyx.android.sdk.data.Tree", "a8ca16a9670b8f2419af835af0149540a9a1fe1ff86138b2bb8da5ebea7e96e2", "628aa780dc4d64885d4109c8b983cfd6ab0305ac6a9fdf030f919f6cb11b2f66");
        // com.onyx.android.sdk.data.Tree$Companion
        assertMetadataHashes("com.onyx.android.sdk.data.Tree$Companion", "d5654f59f1b7e7b0e2102259d7ce677dbb771839a64bac5aec7aa6bf503554ba", "b9784c238b69c2d1435731a760d99ab84751aa8ad118b4c1e0b2b616d920b0ff");
        // com.onyx.android.sdk.data.bitmap.SafeBitmap
        assertField("com.onyx.android.sdk.data.bitmap.SafeBitmap", "Companion", "Lcom/onyx/android/sdk/data/bitmap/SafeBitmap$Companion;", Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL);
        assertNoVisibleField("com.onyx.android.sdk.data.bitmap.SafeBitmap", "INSTANCE", "Lcom/onyx/android/sdk/data/bitmap/SafeBitmap$Companion;");
        // com.onyx.android.sdk.data.bitmap.SafeBitmap$Companion
        assertMetadataHashes("com.onyx.android.sdk.data.bitmap.SafeBitmap$Companion", "329743c8081207eed5171dfeb44a618e00ddf4e786aa9ac98c4535cb33886cda", "0e598fdfdc95faf048cefdcd1e4842cbe83b77988f495eb3756cf78b1f0affcb");
        // com.onyx.android.sdk.data.note.DisplayArgs
        assertField("com.onyx.android.sdk.data.note.DisplayArgs", "Companion", "Lcom/onyx/android/sdk/data/note/DisplayArgs$Companion;", Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL);
        assertNoVisibleField("com.onyx.android.sdk.data.note.DisplayArgs", "INSTANCE", "Lcom/onyx/android/sdk/data/note/DisplayArgs$Companion;");
        // com.onyx.android.sdk.data.note.EraserArgs
        assertMethod("com.onyx.android.sdk.data.note.EraserArgs", "copy$default", "(Lcom/onyx/android/sdk/data/note/EraserArgs;Ljava/lang/String;ILjava/util/Map;ILjava/lang/Object;)Lcom/onyx/android/sdk/data/note/EraserArgs;", Modifier.PUBLIC | Modifier.STATIC);
        // com.onyx.android.sdk.data.note.line.Line
        assertMethod("com.onyx.android.sdk.data.note.line.Line", "copy$default", "(Lcom/onyx/android/sdk/data/note/line/Line;FFFFILjava/lang/Object;)Lcom/onyx/android/sdk/data/note/line/Line;", Modifier.PUBLIC | Modifier.STATIC);
        // com.onyx.android.sdk.data.reader.PageId
        assertMethod("com.onyx.android.sdk.data.reader.PageId", "copy$default", "(Lcom/onyx/android/sdk/data/reader/PageId;Ljava/lang/String;Ljava/lang/String;IILjava/lang/Object;)Lcom/onyx/android/sdk/data/reader/PageId;", Modifier.PUBLIC | Modifier.STATIC);
        // com.onyx.android.sdk.data.reader.ReaderExportDocumentsParam
        assertConstructor("com.onyx.android.sdk.data.reader.ReaderExportDocumentsParam", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;ZILkotlin/jvm/internal/DefaultConstructorMarker;)V", Modifier.PUBLIC);
        assertMethod("com.onyx.android.sdk.data.reader.ReaderExportDocumentsParam", "copy$default", "(Lcom/onyx/android/sdk/data/reader/ReaderExportDocumentsParam;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;ZILjava/lang/Object;)Lcom/onyx/android/sdk/data/reader/ReaderExportDocumentsParam;", Modifier.PUBLIC | Modifier.STATIC);
        // com.onyx.android.sdk.data.reader.ReaderImportDocumentsParam
        assertConstructor("com.onyx.android.sdk.data.reader.ReaderImportDocumentsParam", "(Ljava/lang/String;Ljava/lang/String;ILkotlin/jvm/internal/DefaultConstructorMarker;)V", Modifier.PUBLIC);
        assertMethod("com.onyx.android.sdk.data.reader.ReaderImportDocumentsParam", "copy$default", "(Lcom/onyx/android/sdk/data/reader/ReaderImportDocumentsParam;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Object;)Lcom/onyx/android/sdk/data/reader/ReaderImportDocumentsParam;", Modifier.PUBLIC | Modifier.STATIC);
        // com.onyx.android.sdk.data.render.BaseRenderNode$DefaultImpls
        assertNoVisibleConstructor("com.onyx.android.sdk.data.render.BaseRenderNode$DefaultImpls", "()V");
        // com.onyx.android.sdk.data.richtext.TextAlignType
        assertField("com.onyx.android.sdk.data.richtext.TextAlignType", "Companion", "Lcom/onyx/android/sdk/data/richtext/TextAlignType$Companion;", Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL);
        assertNoVisibleField("com.onyx.android.sdk.data.richtext.TextAlignType", "INSTANCE", "Lcom/onyx/android/sdk/data/richtext/TextAlignType$Companion;");
        // com.onyx.android.sdk.data.richtext.TextAlignType$Companion$WhenMappings
        assertClassModifiers("com.onyx.android.sdk.data.richtext.TextAlignType$Companion$WhenMappings", Modifier.PUBLIC | Modifier.FINAL);
        assertNoVisibleConstructor("com.onyx.android.sdk.data.richtext.TextAlignType$Companion$WhenMappings", "()V");
        // com.onyx.android.sdk.reader.TtsInitResultInfo$CREATOR
        assertMetadataHashes("com.onyx.android.sdk.reader.TtsInitResultInfo$CREATOR", "9132e9bced85b4012d70ea049f53474cc662d3b3fb5d4aa0cb4fa714b1b32a81", "3bdfff75e45662ff51a93cdd9f06449f444b24ca1b62a7291a8719da776be8e2");
        // com.onyx.android.sdk.reader.TtsReaderPosition$CREATOR
        assertMetadataHashes("com.onyx.android.sdk.reader.TtsReaderPosition$CREATOR", "834a1ad03fd8fab951a3309a5e0720e5f51d50af6ecff6026a04a5b2e8ed6e88", "0bd87e996123b7a0997aca584df3b8e45b29bfcf718ddb3de563e7363f3a786a");
    }

    @Test
    public void kotlinFacades() throws Exception {
        // com.onyx.android.sdk.base.extension.WebViewKt
        assertNoVisibleConstructor("com.onyx.android.sdk.base.extension.WebViewKt", "()V");
        // com.onyx.android.sdk.data.LongKt
        assertNoVisibleConstructor("com.onyx.android.sdk.data.LongKt", "()V");
        // com.onyx.android.sdk.data.note.line.LineKt
        assertMethod("com.onyx.android.sdk.data.note.line.LineKt", "isHorizontalLine$default", "(Lcom/onyx/android/sdk/data/note/line/Line;FILjava/lang/Object;)Z", Modifier.PUBLIC | Modifier.STATIC);
        assertNoVisibleConstructor("com.onyx.android.sdk.data.note.line.LineKt", "()V");
        // com.onyx.android.sdk.data.reader.PageIdKt
        assertNoVisibleConstructor("com.onyx.android.sdk.data.reader.PageIdKt", "()V");
        // com.onyx.android.sdk.extension.AnyKt
        assertNoVisibleConstructor("com.onyx.android.sdk.extension.AnyKt", "()V");
        // com.onyx.android.sdk.extension.ArraysKt
        assertNoVisibleConstructor("com.onyx.android.sdk.extension.ArraysKt", "()V");
        // com.onyx.android.sdk.extension.BitmapKt
        assertNoVisibleConstructor("com.onyx.android.sdk.extension.BitmapKt", "()V");
        // com.onyx.android.sdk.extension.BooleanKt
        assertNoVisibleConstructor("com.onyx.android.sdk.extension.BooleanKt", "()V");
        // com.onyx.android.sdk.extension.CanvasKt
        assertMethod("com.onyx.android.sdk.extension.CanvasKt", "drawBitmapFloorLeftTop$default", "(Landroid/graphics/Canvas;Landroid/graphics/Bitmap;Landroid/graphics/Rect;Landroid/graphics/RectF;Landroid/graphics/Paint;ILjava/lang/Object;)V", Modifier.PUBLIC | Modifier.STATIC);
        assertMethod("com.onyx.android.sdk.extension.CanvasKt", "drawBitmapFloorLeftTop$default", "(Landroid/graphics/Canvas;Landroid/graphics/Bitmap;FFLandroid/graphics/Paint;ILjava/lang/Object;)V", Modifier.PUBLIC | Modifier.STATIC);
        assertMethod("com.onyx.android.sdk.extension.CanvasKt", "withMatrix$default", "(Landroid/graphics/Canvas;Landroid/graphics/Matrix;Lkotlin/jvm/functions/Function1;ILjava/lang/Object;)V", Modifier.PUBLIC | Modifier.STATIC);
        assertNoVisibleConstructor("com.onyx.android.sdk.extension.CanvasKt", "()V");
        // com.onyx.android.sdk.extension.CharKt
        assertNoVisibleConstructor("com.onyx.android.sdk.extension.CharKt", "()V");
        // com.onyx.android.sdk.extension.ClassKt
        assertNoVisibleConstructor("com.onyx.android.sdk.extension.ClassKt", "()V");
        // com.onyx.android.sdk.extension.CollectionKt
        assertNoVisibleConstructor("com.onyx.android.sdk.extension.CollectionKt", "()V");
        // com.onyx.android.sdk.extension.ContextExtensionKt
        assertNoVisibleConstructor("com.onyx.android.sdk.extension.ContextExtensionKt", "()V");
        // com.onyx.android.sdk.extension.ContextKt
        assertMethod("com.onyx.android.sdk.extension.ContextKt", "openWithLocalApplication$default", "(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)V", Modifier.PUBLIC | Modifier.STATIC);
        assertMethod("com.onyx.android.sdk.extension.ContextKt", "getMetaData$default", "(Landroid/content/Context;Ljava/lang/String;Lkotlin/jvm/functions/Function0;ILjava/lang/Object;)Ljava/lang/String;", Modifier.PUBLIC | Modifier.STATIC);
        assertNoVisibleConstructor("com.onyx.android.sdk.extension.ContextKt", "()V");
        // com.onyx.android.sdk.extension.DimensKt
        assertNoVisibleConstructor("com.onyx.android.sdk.extension.DimensKt", "()V");
        // com.onyx.android.sdk.extension.DrawableKt
        assertMethod("com.onyx.android.sdk.extension.DrawableKt", "toBitmap$default", "(Landroid/graphics/drawable/Drawable;IIILjava/lang/Object;)Landroid/graphics/Bitmap;", Modifier.PUBLIC | Modifier.STATIC);
        assertNoVisibleConstructor("com.onyx.android.sdk.extension.DrawableKt", "()V");
        // com.onyx.android.sdk.extension.EnumExtensionsKt
        assertMethod("com.onyx.android.sdk.extension.EnumExtensionsKt", "enumValueOfOrNull", "(Ljava/lang/String;)Ljava/lang/Enum;", Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL);
        assertNoVisibleConstructor("com.onyx.android.sdk.extension.EnumExtensionsKt", "()V");
        // com.onyx.android.sdk.extension.EventBusKt
        assertNoVisibleConstructor("com.onyx.android.sdk.extension.EventBusKt", "()V");
        // com.onyx.android.sdk.extension.ExceptionsKt
        assertNoVisibleConstructor("com.onyx.android.sdk.extension.ExceptionsKt", "()V");
        // com.onyx.android.sdk.extension.Files
        assertMethod("com.onyx.android.sdk.extension.Files", "fileNameInAssets$default", "(Lcom/onyx/android/sdk/extension/Files;Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;", Modifier.PUBLIC | Modifier.STATIC);
        assertMethod("com.onyx.android.sdk.extension.Files", "generateUniqueFileName$default", "(Lcom/onyx/android/sdk/extension/Files;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/Object;)Ljava/lang/String;", Modifier.PUBLIC | Modifier.STATIC);
        assertMethod("com.onyx.android.sdk.extension.Files", "readContentOfFile$default", "(Lcom/onyx/android/sdk/extension/Files;Ljava/lang/String;ZILjava/lang/Object;)Ljava/lang/String;", Modifier.PUBLIC | Modifier.STATIC);
        assertMethod("com.onyx.android.sdk.extension.Files", "parallelFileMetadata$default", "(Lcom/onyx/android/sdk/extension/Files;Ljava/util/List;ILkotlin/jvm/functions/Function1;ILjava/lang/Object;)Ljava/util/List;", Modifier.PUBLIC | Modifier.STATIC);
        // com.onyx.android.sdk.extension.FloatKt
        assertNoVisibleConstructor("com.onyx.android.sdk.extension.FloatKt", "()V");
        // com.onyx.android.sdk.extension.IntKt
        assertNoVisibleConstructor("com.onyx.android.sdk.extension.IntKt", "()V");
        // com.onyx.android.sdk.extension.ListExtKt
        assertNoVisibleConstructor("com.onyx.android.sdk.extension.ListExtKt", "()V");
        // com.onyx.android.sdk.extension.LooperKt
        assertNoVisibleConstructor("com.onyx.android.sdk.extension.LooperKt", "()V");
        // com.onyx.android.sdk.extension.MatrixKt
        assertNoVisibleConstructor("com.onyx.android.sdk.extension.MatrixKt", "()V");
        // com.onyx.android.sdk.extension.MotionEventKt
        assertNoVisibleConstructor("com.onyx.android.sdk.extension.MotionEventKt", "()V");
        // com.onyx.android.sdk.extension.ObservableKt
        assertMethod("com.onyx.android.sdk.extension.ObservableKt", "subscribeWithDefault$default", "(Lio/reactivex/Observable;Lio/reactivex/functions/Consumer;Lio/reactivex/functions/Consumer;Lio/reactivex/functions/Action;ILjava/lang/Object;)Lio/reactivex/disposables/Disposable;", Modifier.PUBLIC | Modifier.STATIC);
        assertNoVisibleConstructor("com.onyx.android.sdk.extension.ObservableKt", "()V");
        // com.onyx.android.sdk.extension.PaintKt
        assertNoVisibleConstructor("com.onyx.android.sdk.extension.PaintKt", "()V");
        // com.onyx.android.sdk.extension.Paths
        assertMethod("com.onyx.android.sdk.extension.Paths", "copyFile$default", "(Lcom/onyx/android/sdk/extension/Paths;Ljava/nio/file/Path;Ljava/nio/file/Path;ZILjava/lang/Object;)Z", Modifier.PUBLIC | Modifier.STATIC);
        assertMethod("com.onyx.android.sdk.extension.Paths", "listFiles$default", "(Lcom/onyx/android/sdk/extension/Paths;Ljava/nio/file/Path;ZILjava/lang/Object;)Ljava/util/Collection;", Modifier.PUBLIC | Modifier.STATIC);
        assertMethod("com.onyx.android.sdk.extension.Paths", "resample$default", "(Lcom/onyx/android/sdk/extension/Paths;Landroid/graphics/Path;Ljava/lang/Number;ILjava/lang/Object;)Ljava/util/List;", Modifier.PUBLIC | Modifier.STATIC);
        // com.onyx.android.sdk.extension.RangeKt
        assertNoVisibleConstructor("com.onyx.android.sdk.extension.RangeKt", "()V");
        // com.onyx.android.sdk.extension.RectKt
        assertNoVisibleConstructor("com.onyx.android.sdk.extension.RectKt", "()V");
        // com.onyx.android.sdk.extension.RxjavaKt
        assertMethod("com.onyx.android.sdk.extension.RxjavaKt", "touchBuffer$default", "(Lio/reactivex/Observable;JILio/reactivex/Scheduler;ILjava/lang/Object;)Lio/reactivex/Observable;", Modifier.PUBLIC | Modifier.STATIC);
        assertNoVisibleConstructor("com.onyx.android.sdk.extension.RxjavaKt", "()V");
        assertClassfileContains("com.onyx.android.sdk.extension.RxjavaKt", "(Lio/reactivex/Observable<Ljava/lang/Boolean;>;Lio/reactivex/Observable<+Ljava/lang/Object;>;)Lio/reactivex/Observable<Ljava/lang/Object;>;");
        assertClassfileContains("com.onyx.android.sdk.extension.RxjavaKt", "(Lio/reactivex/Observable<Ljava/lang/Boolean;>;Lio/reactivex/Observable<+Ljava/lang/Object;>;)Lio/reactivex/Observable<Ljava/lang/Object;>;");
        assertClassfileContains("com.onyx.android.sdk.extension.RxjavaKt", "<T:Ljava/lang/Object;>(Lio/reactivex/Observable<TT;>;Lkotlin/jvm/functions/Function1<-TT;Ljava/lang/Boolean;>;Lio/reactivex/Observable<+Ljava/lang/Object;>;)Lio/reactivex/Observable<Ljava/lang/Object;>;");
        assertClassfileContains("com.onyx.android.sdk.extension.RxjavaKt", "<T:Ljava/lang/Object;>(Lio/reactivex/Observable<TT;>;Lkotlin/jvm/functions/Function1<-TT;Ljava/lang/Boolean;>;Lkotlin/jvm/functions/Function1<-TT;+Lio/reactivex/Observable<+Ljava/lang/Object;>;>;Lkotlin/jvm/functions/Function1<-TT;+Lio/reactivex/Observable<+Ljava/lang/Object;>;>;)Lio/reactivex/Observable<Ljava/lang/Object;>;");
        assertClassfileContains("com.onyx.android.sdk.extension.RxjavaKt", "<T:Ljava/lang/Object;>(Lio/reactivex/Observable<TT;>;Lkotlin/jvm/functions/Function1<-TT;Ljava/lang/Boolean;>;Lkotlin/jvm/functions/Function1<-TT;+Lio/reactivex/Observable<+Ljava/lang/Object;>;>;)Lio/reactivex/Observable<Ljava/lang/Object;>;");
        assertClassfileContains("com.onyx.android.sdk.extension.RxjavaKt", "<T:Ljava/lang/Object;>(Lio/reactivex/subjects/BehaviorSubject<TT;>;Lkotlin/jvm/functions/Function1<-TT;+Lio/reactivex/Observable<+Ljava/lang/Object;>;>;)Lio/reactivex/Observable<Ljava/lang/Object;>;");
        assertClassfileContains("com.onyx.android.sdk.extension.RxjavaKt", "<T:Ljava/lang/Object;>(Lio/reactivex/Observable<TT;>;Lkotlin/jvm/functions/Function1<-TT;Ljava/lang/Boolean;>;Lkotlin/jvm/functions/Function1<-TT;+Lio/reactivex/Observable<+Ljava/lang/Object;>;>;)Lio/reactivex/Observable<Ljava/lang/Object;>;");
        // com.onyx.android.sdk.extension.SizeKt
        assertNoVisibleConstructor("com.onyx.android.sdk.extension.SizeKt", "()V");
        // com.onyx.android.sdk.extension.StringKt
        assertMethod("com.onyx.android.sdk.extension.StringKt", "getLastIntNumber$default", "(Ljava/lang/String;IILjava/lang/Object;)I", Modifier.PUBLIC | Modifier.STATIC);
        assertNoVisibleConstructor("com.onyx.android.sdk.extension.StringKt", "()V");
        // com.onyx.android.sdk.extension.SurfaceViewKt
        assertNoVisibleConstructor("com.onyx.android.sdk.extension.SurfaceViewKt", "()V");
        // com.onyx.android.sdk.extension.UriKt
        assertNoVisibleConstructor("com.onyx.android.sdk.extension.UriKt", "()V");
        // com.onyx.android.sdk.extension.ViewKt
        assertNoVisibleConstructor("com.onyx.android.sdk.extension.ViewKt", "()V");
        // com.onyx.android.sdk.utils.BitmapLruCacheKt
        assertNoVisibleConstructor("com.onyx.android.sdk.utils.BitmapLruCacheKt", "()V");
        // com.onyx.android.sdk.utils.LruCacheKt
        assertNoVisibleConstructor("com.onyx.android.sdk.utils.LruCacheKt", "()V");
        // com.onyx.android.sdk.utils.MathUtilsKt
        assertMethod("com.onyx.android.sdk.utils.MathUtilsKt", "areApproximatelyEqual$default", "(Lcom/onyx/android/sdk/utils/MathUtilsKt;FFFILjava/lang/Object;)Z", Modifier.PUBLIC | Modifier.STATIC);
        assertMethod("com.onyx.android.sdk.utils.MathUtilsKt", "inRange$default", "(Lcom/onyx/android/sdk/utils/MathUtilsKt;Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/Number;ZZZILjava/lang/Object;)Z", Modifier.PUBLIC | Modifier.STATIC);
        // com.onyx.android.sdk.utils.ViewsKt
        assertMethod("com.onyx.android.sdk.utils.ViewsKt", "setImageLimitBitmap$default", "(Landroid/widget/ImageView;Landroid/graphics/Bitmap;JILjava/lang/Object;)Z", Modifier.PUBLIC | Modifier.STATIC);
        assertNoVisibleConstructor("com.onyx.android.sdk.utils.ViewsKt", "()V");
    }

    @Test
    public void rxScheduler() throws Exception {
        // com.onyx.android.sdk.rx.MultiThreadScheduler
        assertMethod("com.onyx.android.sdk.rx.MultiThreadScheduler", "newScheduler$default", "(Lcom/onyx/android/sdk/rx/MultiThreadScheduler;Ljava/lang/String;JLjava/util/concurrent/TimeUnit;ILjava/lang/Object;)Lio/reactivex/Scheduler;", Modifier.PUBLIC | Modifier.STATIC);
        // com.onyx.android.sdk.rx.RxFirstDebounceWithReset
        assertMetadataHashes("com.onyx.android.sdk.rx.RxFirstDebounceWithReset", "dbf893008be106bd80dec56cc437b5290bce83d0ce034e160853caa0c61a39fc", "12e69ebca07e535aeed77e9ec4272b1161933aa90e0eed89d108c160288057ab");
        // com.onyx.android.sdk.rx.RxFirstDebounceWithReset$Companion
        assertMetadataHashes("com.onyx.android.sdk.rx.RxFirstDebounceWithReset$Companion", "958bcbad040b80dea6fdb3d69d3747f4a75436b66460ac5105c16a13251f69a9", "8f1c8d6fd7a1b4b073aedbca99271c7436418882f924f01533dfa8999ac5a9e9");
        // com.onyx.android.sdk.rx.RxFrameHandler
        assertMetadataHashes("com.onyx.android.sdk.rx.RxFrameHandler", "b6141d519d9cf35ab8017d434813c84b907690e43117bf3860e61bc3ce676f58", "65e93672e6fbba6cc6045a61ba95884e54516b8b413f97165287fe0ded08ef97");
        // com.onyx.android.sdk.rx.RxScheduler$Companion
        assertMethod("com.onyx.android.sdk.rx.RxScheduler$Companion", "newMultiThreadManager$default", "(Lcom/onyx/android/sdk/rx/RxScheduler$Companion;Ljava/lang/String;JILjava/lang/Object;)Lcom/onyx/android/sdk/rx/RxScheduler;", Modifier.PUBLIC | Modifier.STATIC);
        // com.onyx.android.sdk.rx.RxScroller
        assertField("com.onyx.android.sdk.rx.RxScroller", "Companion", "Lcom/onyx/android/sdk/rx/RxScroller$Companion;", Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL);
        // com.onyx.android.sdk.rx.RxScroller$Companion
        Class<?> missing = load("com.onyx.android.sdk.rx.RxScroller$Companion");
    }

    @Test
    public void service() throws Exception {
        // com.onyx.android.sdk.calendar.CalendarRemoteServiceConnection
        assertMethod("com.onyx.android.sdk.calendar.CalendarRemoteServiceConnection", "asInterface", "(Landroid/os/IBinder;)Landroid/os/IInterface;", Modifier.PUBLIC);
    }

    @Test
    public void utilities() throws Exception {
        // com.onyx.android.sdk.utils.BaseBitmapReferenceLruCache
        assertMetadataHashes("com.onyx.android.sdk.utils.BaseBitmapReferenceLruCache", "b4792f12660f972402762be6a8346bb826ccd12ef35eecfce05634e751d5e57c", "094fbcd95b4fdeb0e672721e937020f0e6be9b5aaf348372698ea41fe3c9d158");
        // com.onyx.android.sdk.utils.BitmapLruCacheConfig
        assertMethod("com.onyx.android.sdk.utils.BitmapLruCacheConfig", "bitmapCacheMaxByteCount$default", "(Lcom/onyx/android/sdk/utils/BitmapLruCacheConfig;IILjava/lang/Object;)I", Modifier.PUBLIC | Modifier.STATIC);
        // com.onyx.android.sdk.utils.Bitmaps
        assertMethod("com.onyx.android.sdk.utils.Bitmaps", "getBitmapFromVectorDrawableBySize$default", "(Lcom/onyx/android/sdk/utils/Bitmaps;Landroid/content/Context;ILandroid/graphics/RectF;Landroid/graphics/ColorFilter;ILjava/lang/Object;)Landroid/graphics/Bitmap;", Modifier.PUBLIC | Modifier.STATIC);
        assertMethod("com.onyx.android.sdk.utils.Bitmaps", "getBitmapFromVectorDrawableBySize$default", "(Lcom/onyx/android/sdk/utils/Bitmaps;Landroid/content/Context;ILandroid/graphics/RectF;Landroid/graphics/RectF;Landroid/graphics/Matrix;Landroid/graphics/ColorFilter;ILjava/lang/Object;)Landroid/graphics/Bitmap;", Modifier.PUBLIC | Modifier.STATIC);
        assertMethod("com.onyx.android.sdk.utils.Bitmaps", "loadBitmapFromFile$default", "(Lcom/onyx/android/sdk/utils/Bitmaps;Ljava/lang/String;Landroid/graphics/BitmapFactory$Options;ILjava/lang/Object;)Landroid/graphics/Bitmap;", Modifier.PUBLIC | Modifier.STATIC);
        assertMetadataHashes("com.onyx.android.sdk.utils.Bitmaps", "cbc9c673ef52ede2914cf7562d0fb9c5ea7618112a53062df418e9bcdfa0fb09", "0ab8864f47cde3e57861af72388424293e9c3fd71e5138d92910376aa5967eb8");
        // com.onyx.android.sdk.utils.CallStackUtils
        assertMethod("com.onyx.android.sdk.utils.CallStackUtils", "printCallStack$default", "(Lcom/onyx/android/sdk/utils/CallStackUtils;Ljava/lang/Class;IILjava/lang/Object;)V", Modifier.PUBLIC | Modifier.STATIC);
        // com.onyx.android.sdk.utils.Colors
        assertMethod("com.onyx.android.sdk.utils.Colors", "calculateLuminance$default", "(Lcom/onyx/android/sdk/utils/Colors;ILjava/lang/Integer;ILjava/lang/Object;)D", Modifier.PUBLIC | Modifier.STATIC);
        // com.onyx.android.sdk.utils.ElementCounter
        assertMethod("com.onyx.android.sdk.utils.ElementCounter", "put", "(Ljava/lang/Object;)I", Modifier.PUBLIC | Modifier.FINAL);
        assertMethod("com.onyx.android.sdk.utils.ElementCounter", "reset", "(Ljava/lang/Object;)V", Modifier.PUBLIC | Modifier.FINAL);
        assertMethod("com.onyx.android.sdk.utils.ElementCounter", "resetAll", "(Ljava/util/List;)V", Modifier.PUBLIC | Modifier.FINAL);
        assertMethod("com.onyx.android.sdk.utils.ElementCounter", "toAscList", "()Ljava/util/List;", Modifier.PUBLIC | Modifier.FINAL);
        assertMethod("com.onyx.android.sdk.utils.ElementCounter", "toDescList", "()Ljava/util/List;", Modifier.PUBLIC | Modifier.FINAL);
        assertMethod("com.onyx.android.sdk.utils.ElementCounter", "printAscList", "()Ljava/lang/String;", Modifier.PUBLIC | Modifier.FINAL);
        assertMethod("com.onyx.android.sdk.utils.ElementCounter", "printDescList", "()Ljava/lang/String;", Modifier.PUBLIC | Modifier.FINAL);
        assertMethod("com.onyx.android.sdk.utils.ElementCounter", "remove", "(Ljava/lang/Object;Ljava/lang/Object;)Z", Modifier.PUBLIC | Modifier.FINAL);
        assertMethod("com.onyx.android.sdk.utils.ElementCounter", "containsValue", "(Ljava/lang/Object;)Z", Modifier.PUBLIC | Modifier.FINAL);
        assertMethod("com.onyx.android.sdk.utils.ElementCounter", "entrySet", "()Ljava/util/Set;", Modifier.PUBLIC | Modifier.FINAL);
        assertMethod("com.onyx.android.sdk.utils.ElementCounter", "keySet", "()Ljava/util/Set;", Modifier.PUBLIC | Modifier.FINAL);
        assertMethod("com.onyx.android.sdk.utils.ElementCounter", "values", "()Ljava/util/Collection;", Modifier.PUBLIC | Modifier.FINAL);
        assertMethod("com.onyx.android.sdk.utils.ElementCounter", "size", "()I", Modifier.PUBLIC | Modifier.FINAL);
        assertMetadataHashes("com.onyx.android.sdk.utils.ElementCounter", "e9cdc3bf0ef80a72bce9217816cca9422c8bc2015f45ef6d36a98faae4f11a43", "150887242771c7ff437f523316882b7d283be7ac5a2403d4dd2aac9780d28b86");
        // com.onyx.android.sdk.utils.ImageRender
        assertMetadataHashes("com.onyx.android.sdk.utils.ImageRender", "c67823a5b8a8d05a63cbcc96898f541beb611fc8994002e6ac1bf4c38f3c81ba", "c353373414a97071fc6bf1e3a2cf861a49159286309de1369862f1027e4aa19f");
        // com.onyx.android.sdk.utils.PermissionUtils
        assertMethod("com.onyx.android.sdk.utils.PermissionUtils", "checkAndRequestStoragePermission$default", "(Lcom/onyx/android/sdk/utils/PermissionUtils;Landroid/content/Context;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;ILjava/lang/Object;)V", Modifier.PUBLIC | Modifier.STATIC);
        // com.onyx.android.sdk.utils.RxAlarm
        assertMethod("com.onyx.android.sdk.utils.RxAlarm", "d", "()V", Modifier.PRIVATE);
        assertMethod("com.onyx.android.sdk.utils.RxAlarm", "c", "()V", Modifier.PRIVATE);
        // com.onyx.android.sdk.utils.SimpleBitmapReference$Companion
        assertConstructor("com.onyx.android.sdk.utils.SimpleBitmapReference$Companion", "(Lkotlin/jvm/internal/DefaultConstructorMarker;)V", Modifier.PUBLIC);
        // com.onyx.android.sdk.utils.SimpleBitmapReferenceLruCache
        assertMethod("com.onyx.android.sdk.utils.SimpleBitmapReferenceLruCache", "sizeOf", "(Ljava/lang/Object;Ljava/lang/Object;)I", Modifier.PUBLIC);
        assertMethod("com.onyx.android.sdk.utils.SimpleBitmapReferenceLruCache", "getBitmap", "(Ljava/lang/Object;)Landroid/graphics/Bitmap;", Modifier.PUBLIC);
        assertMethod("com.onyx.android.sdk.utils.SimpleBitmapReferenceLruCache", "isValid", "(Ljava/lang/Object;)Z", Modifier.PUBLIC);
        assertMethod("com.onyx.android.sdk.utils.SimpleBitmapReferenceLruCache", "create", "(IILandroid/graphics/Bitmap$Config;)Ljava/lang/Object;", Modifier.PUBLIC);
        assertMethod("com.onyx.android.sdk.utils.SimpleBitmapReferenceLruCache", "clearBitmap", "(Ljava/lang/Object;)V", Modifier.PUBLIC);
        assertMethod("com.onyx.android.sdk.utils.SimpleBitmapReferenceLruCache", "cloneBitmapReference", "(Ljava/lang/Object;)Ljava/lang/Object;", Modifier.PUBLIC);
        assertMetadataHashes("com.onyx.android.sdk.utils.SimpleBitmapReferenceLruCache", "670f6a5bfd2d46e73f116361268c6857df13a4fd6519ead2ad990d677efe29c3", "25a91a403aff30b2d63e5fbc6703a68a8ceef688f1c5d97f5e1efc5b8217ef77");
        // com.onyx.android.sdk.utils.StorageTypeUtils
        assertMethod("com.onyx.android.sdk.utils.StorageTypeUtils", "access$getUsbKeywordList$p", "()Ljava/util/List;", Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL);
        // com.onyx.android.sdk.utils.StorageTypeUtils$FilePathInferResult
        assertMethod("com.onyx.android.sdk.utils.StorageTypeUtils$FilePathInferResult", "copy$default", "(Lcom/onyx/android/sdk/utils/StorageTypeUtils$FilePathInferResult;Ljava/lang/String;Lcom/onyx/android/sdk/utils/StorageTypeUtils$StorageType;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Object;)Lcom/onyx/android/sdk/utils/StorageTypeUtils$FilePathInferResult;", Modifier.PUBLIC | Modifier.STATIC);
        // com.onyx.android.sdk.utils.StorageTypeUtils$StorageType$WhenMappings
        assertClassModifiers("com.onyx.android.sdk.utils.StorageTypeUtils$StorageType$WhenMappings", Modifier.PUBLIC | Modifier.FINAL);
        assertField("com.onyx.android.sdk.utils.StorageTypeUtils$StorageType$WhenMappings", "$EnumSwitchMapping$0", "[I", Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL);
        assertMetadataHashes("com.onyx.android.sdk.utils.StorageTypeUtils$StorageType$WhenMappings", "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855", "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
        // com.onyx.android.sdk.utils.Times$TimedValue
        assertMethod("com.onyx.android.sdk.utils.Times$TimedValue", "copy$default", "(Lcom/onyx/android/sdk/utils/Times$TimedValue;Ljava/lang/Object;Ljava/lang/String;ILjava/lang/Object;)Lcom/onyx/android/sdk/utils/Times$TimedValue;", Modifier.PUBLIC | Modifier.STATIC);
        // com.onyx.android.sdk.utils.font.FontFamilyClassifier
        assertMethod("com.onyx.android.sdk.utils.font.FontFamilyClassifier", "access$createFamilyFont", "(Lcom/onyx/android/sdk/utils/font/FontFamilyClassifier;Ljava/util/List;)Lcom/onyx/android/sdk/data/FontInfo;", Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL);
        assertMethod("com.onyx.android.sdk.utils.font.FontFamilyClassifier", "a", "(Ljava/util/List;)Lcom/onyx/android/sdk/data/FontInfo;", Modifier.PRIVATE | Modifier.FINAL);
        // com.onyx.android.sdk.utils.font.FontFoldUtils
        assertMethod("com.onyx.android.sdk.utils.font.FontFoldUtils", "access$sortByFontName", "(Lcom/onyx/android/sdk/utils/font/FontFoldUtils;Lcom/onyx/android/sdk/data/FontInfo;Lcom/onyx/android/sdk/data/FontInfo;)I", Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL);
    }

    @Test
    public void wifi() throws Exception {
        // com.onyx.android.sdk.wifi.RxWifiAdmin
        assertMethod("com.onyx.android.sdk.wifi.RxWifiAdmin", "a", "(Lio/reactivex/ObservableEmitter;)Landroid/content/BroadcastReceiver;", Modifier.PRIVATE);
        assertMethod("com.onyx.android.sdk.wifi.RxWifiAdmin", "b", "(Lio/reactivex/ObservableEmitter;)Landroid/content/BroadcastReceiver;", Modifier.PRIVATE);
        // com.onyx.android.sdk.wifi.WifiAdmin
        assertMethod("com.onyx.android.sdk.wifi.WifiAdmin", "a", "(Lcom/onyx/android/sdk/rx/RxCallback;)V", Modifier.PRIVATE);
        assertMethod("com.onyx.android.sdk.wifi.WifiAdmin", "a", "(Ljava/util/Collection;)Ljava/util/List;", Modifier.PRIVATE);
    }


    private static final int VISIBILITY = Modifier.PUBLIC | Modifier.PROTECTED
            | Modifier.PRIVATE;

    private static Class<?> load(String name) throws Exception {
        return Class.forName(name);
    }

    private static Class<?>[] parameterTypes(String descriptor) throws Exception {
        List<Class<?>> types = new ArrayList<>();
        int[] index = {1};
        while (descriptor.charAt(index[0]) != ')') {
            types.add(descriptorType(descriptor, index));
        }
        return types.toArray(new Class<?>[0]);
    }

    private static Class<?> returnType(String descriptor) throws Exception {
        int[] index = {descriptor.indexOf(')') + 1};
        return descriptorType(descriptor, index);
    }

    private static Class<?> fieldType(String descriptor) throws Exception {
        int[] index = {0};
        Class<?> type = descriptorType(descriptor, index);
        assertEquals("complete field descriptor", descriptor.length(), index[0]);
        return type;
    }

    private static Class<?> descriptorType(String descriptor, int[] index) throws Exception {
        int dimensions = 0;
        while (descriptor.charAt(index[0]) == '[') {
            dimensions++;
            index[0]++;
        }
        char tag = descriptor.charAt(index[0]);
        String binaryName;
        if (tag == 'L') {
            int end = descriptor.indexOf(';', index[0]);
            binaryName = descriptor.substring(index[0] + 1, end).replace('/', '.');
            index[0] = end + 1;
        } else {
            binaryName = String.valueOf(tag);
            index[0]++;
        }
        Class<?> type = primitive(binaryName);
        if (type == null) {
            type = Class.forName(binaryName);
        }
        for (int i = 0; i < dimensions; i++) {
            type = java.lang.reflect.Array.newInstance(type, 0).getClass();
        }
        return type;
    }

    private static Class<?> primitive(String tag) {
        switch (tag) {
            case "Z": return boolean.class;
            case "B": return byte.class;
            case "C": return char.class;
            case "S": return short.class;
            case "I": return int.class;
            case "J": return long.class;
            case "F": return float.class;
            case "D": return double.class;
            case "V": return void.class;
            default: return null;
        }
    }

    private static void assertModifiers(String what, int expected, int actual) {
        int checked = VISIBILITY | Modifier.STATIC | Modifier.FINAL | Modifier.ABSTRACT;
        assertEquals(what + ": modifiers", expected & checked, actual & checked);
    }

    private static void assertMethod(String owner, String name, String descriptor,
            int modifiers) throws Exception {
        assertMethod(load(owner), name, descriptor, modifiers);
    }

    private static void assertMethod(Class<?> owner, String name, String descriptor,
            int modifiers) throws Exception {
        Method method = declaredMethod(owner, name, descriptor);
        assertModifiers(owner.getName() + "#" + name + descriptor, modifiers,
                method.getModifiers());
    }

    private static Method declaredMethod(Class<?> owner, String name, String descriptor)
            throws Exception {
        Class<?>[] parameters = parameterTypes(descriptor);
        Class<?> returns = returnType(descriptor);
        for (Method method : owner.getDeclaredMethods()) {
            if (method.getName().equals(name)
                    && java.util.Arrays.equals(method.getParameterTypes(), parameters)
                    && method.getReturnType().equals(returns)) {
                return method;
            }
        }
        throw new NoSuchMethodException(owner.getName() + "#" + name + descriptor);
    }

    private static void assertField(String owner, String name, String descriptor,
            int modifiers) throws Exception {
        Field field = load(owner).getDeclaredField(name);
        assertEquals(owner + "." + name + descriptor + ": field type",
                fieldType(descriptor), field.getType());
        assertModifiers(owner + "." + name, modifiers, field.getModifiers());
    }

    private static void assertConstructor(String owner, String descriptor,
            int modifiers) throws Exception {
        Constructor<?> constructor =
                load(owner).getDeclaredConstructor(parameterTypes(descriptor));
        assertModifiers(owner + ".<init>" + descriptor, modifiers,
                constructor.getModifiers());
    }

    private static void assertNoVisibleMethod(String owner, String name,
            String descriptor) throws Exception {
        try {
            Method method = declaredMethod(load(owner), name, descriptor);
            assertEquals(owner + "#" + name + " must not be public/protected",
                    0, method.getModifiers() & (Modifier.PUBLIC | Modifier.PROTECTED));
        } catch (NoSuchMethodException absent) {
            // Absent entirely also matches the reference surface.
        }
    }

    private static void assertNoVisibleField(String owner, String name,
            String descriptor) throws Exception {
        try {
            Field field = load(owner).getDeclaredField(name);
            assertEquals(owner + "." + name + " must not be public/protected",
                    0, field.getModifiers() & (Modifier.PUBLIC | Modifier.PROTECTED));
        } catch (NoSuchFieldException absent) {
            // Absent entirely also matches the reference surface.
        }
    }

    private static void assertNoVisibleConstructor(String owner, String descriptor)
            throws Exception {
        try {
            Constructor<?> constructor =
                    load(owner).getDeclaredConstructor(parameterTypes(descriptor));
            assertEquals(owner + ".<init> must not be public/protected",
                    0, constructor.getModifiers() & (Modifier.PUBLIC | Modifier.PROTECTED));
        } catch (NoSuchMethodException absent) {
            // Absent entirely also matches the reference surface.
        }
    }

    private static void assertClassModifiers(String owner, int modifiers)
            throws Exception {
        // Class.getModifiers() folds in InnerClasses-attribute STATIC, which
        // class-file access flags (the audited layer) never carry.
        int checked = VISIBILITY | Modifier.FINAL | Modifier.ABSTRACT;
        assertEquals(owner + ": class modifiers", modifiers & checked,
                load(owner).getModifiers() & checked);
    }

    /** Asserts the class file's constant pool contains the exact signature. */
    private static void assertClassfileContains(String owner, String constant)
            throws Exception {
        Class<?> clazz = load(owner);
        String resource = "/" + owner.replace('.', '/') + ".class";
        try (InputStream stream = clazz.getResourceAsStream(resource)) {
            assertNotNull(owner + ": class bytes", stream);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            byte[] buffer = new byte[8192];
            int read;
            while ((read = stream.read(buffer)) != -1) {
                bytes.write(buffer, 0, read);
            }
            String text = new String(bytes.toByteArray(), StandardCharsets.ISO_8859_1);
            assertTrue(owner + " must keep signature constant " + constant,
                    text.contains(constant));
        }
    }

    private static void assertMetadataHashes(String owner, String d1Sha256,
            String d2Sha256) throws Exception {
        kotlin.Metadata metadata = load(owner).getAnnotation(kotlin.Metadata.class);
        assertNotNull(owner + ": kotlin.Metadata", metadata);
        assertEquals(owner + ": metadata d1", d1Sha256, sha256(metadata.d1()));
        assertEquals(owner + ": metadata d2", d2Sha256, sha256(metadata.d2()));
    }

    private static String sha256(String[] values) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(
                String.join(" ", values).getBytes(StandardCharsets.UTF_8));
        StringBuilder hex = new StringBuilder();
        for (byte b : hash) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }
}
