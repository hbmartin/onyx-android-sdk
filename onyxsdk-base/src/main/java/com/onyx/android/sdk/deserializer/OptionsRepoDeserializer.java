package com.onyx.android.sdk.deserializer;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.reader.ObjectReader;
import com.onyx.android.sdk.utils.OptionsRepo;
import com.onyx.android.sdk.utils.ShapeOptionsConstant;
import com.onyx.android.sdk.utils.TTFFont;
import java.lang.reflect.Type;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J,\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016¨\u0006\r"}, d2 = {"Lcom/onyx/android/sdk/deserializer/OptionsRepoDeserializer;", "Lcom/alibaba/fastjson2/reader/ObjectReader;", "Lcom/onyx/android/sdk/utils/OptionsRepo;", "()V", "readObject", "jsonReader", "Lcom/alibaba/fastjson2/JSONReader;", "fieldType", "Ljava/lang/reflect/Type;", "fieldName", TTFFont.UNKNOWN_FONT_NAME, "features", TTFFont.UNKNOWN_FONT_NAME, "onyxsdk-base_release"})
public final class OptionsRepoDeserializer implements ObjectReader<OptionsRepo> {
    private static final void a(OptionsRepo $repo, String key, Object value) {
        Intrinsics.checkNotNullParameter($repo, "$repo");
        Class<?> cls = ShapeOptionsConstant.INSTANCE.getOptionsTypeMap().get(key);
        if ((cls != null) && (value instanceof JSONObject)) {
            HashMap<String, Object> repo = $repo.getRepo();
            Intrinsics.checkNotNullExpressionValue(key, "key");
            repo.put(key, JSON.parseObject(((JSONObject) value).toJSONString(new JSONWriter.Feature[0]), cls));
        } else {
            HashMap<String, Object> repo2 = $repo.getRepo();
            Intrinsics.checkNotNullExpressionValue(key, "key");
            repo2.put(key, value);
        }
    }

    @NotNull
    public OptionsRepo readObject(@NotNull JSONReader jsonReader, @Nullable Type fieldType, @Nullable Object fieldName, long features) {
        Intrinsics.checkNotNullParameter(jsonReader, "jsonReader");
        OptionsRepo optionsRepo = new OptionsRepo();
        Object any = jsonReader.readAny();
        JSONObject jSONObject = any instanceof JSONObject ? (JSONObject) any : null;
        JSONObject jSONObject2 = jSONObject;
        if (jSONObject2 == null) {
            return optionsRepo;
        }
        try {
            JSONObject jSONObject3 = jSONObject.getJSONObject(ShapeOptionsConstant.OPTIONS_REPO_NAME);
            if (jSONObject3 != null) {
                jSONObject2 = jSONObject3;
                jSONObject2.forEach((v1, v2) -> {
                    a(optionsRepo, v1, v2);
                });
            }
        } catch (JSONException failure) {
            failure.printStackTrace();
        }
        return optionsRepo;
    }
}
