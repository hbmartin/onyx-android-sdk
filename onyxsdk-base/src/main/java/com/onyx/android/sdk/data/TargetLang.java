package com.onyx.android.sdk.data;

import androidx.annotation.Nullable;
import com.onyx.android.sdk.utils.StringUtils;
import java.util.List;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/TargetLang.class */
public enum TargetLang {
    AUTO("auto"),
    CHT("cht"),
    ZH("zh"),
    JP("jp"),
    EN("en"),
    SPA("spa"),
    DE("de"),
    RU("ru"),
    CS("cs"),
    FRA("fra"),
    HR("hr"),
    IT("it"),
    HU("hu"),
    MS("ms"),
    NL("nl"),
    PL("pl"),
    PT("pt"),
    SK("sk"),
    SLO("slo"),
    SWE("swe"),
    VIE("vie"),
    IW("iw"),
    ARA("ara"),
    KOR("kor"),
    TR("tr"),
    TH("th"),
    FIN("fin"),
    HI("hi"),
    LV("lv"),
    EL("el"),
    ID(GAdapterUtil.TAG_UNIQUE_ID),
    FA("fa"),
    AF("af"),
    SQ("sq"),
    CA("ca"),
    DA("da"),
    ET("et"),
    FIL("fil"),
    FI("fi"),
    IS("is"),
    LT("lt"),
    MR("mr"),
    NE("ne"),
    NO("no"),
    RO("ro"),
    SR("sr"),
    PT_BR("pt_br");

    private String a;

    TargetLang(String name) {
        this.a = name;
    }

    public static TargetLang getValue(String name) {
        for (TargetLang targetLang : values()) {
            if (StringUtils.isEquals(name, targetLang.getName())) {
                return targetLang;
            }
        }
        return ZH;
    }

    public void setName(String name) {
        this.a = name;
    }

    public String getName() {
        return this.a;
    }

    @Nullable
    public static TargetLang getValue(List<String> list, String name) {
        if (list.contains(name)) {
            return getValue(name);
        }
        return null;
    }
}
