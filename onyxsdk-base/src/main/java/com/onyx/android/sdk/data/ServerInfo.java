package com.onyx.android.sdk.data;

import com.onyx.android.sdk.R;
import com.onyx.android.sdk.utils.ResManager;
import com.onyx.android.sdk.utils.StringUtils;
import com.onyx.android.sdk.utils.TTFFont;
import java.io.Serializable;
import java.util.Locale;

public class ServerInfo implements Serializable {
    private static final String g = "test_server_id";
    private static final String h = "vn";
    private String a = TTFFont.UNKNOWN_FONT_NAME;
    private String b = TTFFont.UNKNOWN_FONT_NAME;
    private String c = TTFFont.UNKNOWN_FONT_NAME;
    private String d;
    private ClusterHost e;
    private OssConfigInfo f;

    public static ServerInfo createDefaultChineseServer() {
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.setLang(Locale.CHINESE.getLanguage()).setName(ResManager.getString(R.string.server_selection_china)).setHost(ClusterHost.createDefaultChineseHost()).setOssConfig(OssConfigInfo.createDefaultChineseOss());
        return serverInfo;
    }

    public static ServerInfo createDefaultAmericaServer() {
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.setLang(Locale.ENGLISH.getLanguage()).setName(ResManager.getString(R.string.server_selection_america)).setHost(ClusterHost.createDefaultAmericanHost()).setOssConfig(OssConfigInfo.createDefaultAmericanOss());
        return serverInfo;
    }

    public static ServerInfo createDefaultVietnamServer() {
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.setLang(h).setName(ResManager.getString(R.string.server_selection_Vietnam)).setHost(ClusterHost.createDefaultVietnamHost()).setOssConfig(OssConfigInfo.createDefaultAmericanOss());
        return serverInfo;
    }

    public static ServerInfo createTestServer() {
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.setLang(Locale.getDefault().getLanguage()).setServerId(g).setName(ResManager.getString(R.string.test_server)).setHost(ClusterHost.createTestHost()).setOssConfig(OssConfigInfo.createDefaultChineseOss());
        return serverInfo;
    }

    public boolean isSameServer(ServerInfo serverInfo) {
        return StringUtils.safelyEquals(getServerId(), serverInfo.getServerId());
    }

    public boolean isTestServer() {
        return StringUtils.safelyEquals(getServerId(), g);
    }

    public boolean isValid() {
        return getHost().validCheck();
    }

    public String getName() {
        return this.b;
    }

    public ServerInfo setName(String name) {
        this.b = name;
        return this;
    }

    public String getLang() {
        return this.c;
    }

    public ServerInfo setLang(String lang) {
        this.c = lang;
        setServerId(lang);
        return this;
    }

    public String getRegion() {
        return this.d;
    }

    public ServerInfo setRegion(String region) {
        this.d = region;
        return this;
    }

    public ClusterHost getHost() {
        if (this.e == null) {
            this.e = new ClusterHost();
        }
        return this.e;
    }

    public ServerInfo setHost(ClusterHost h2) {
        if (h2 == null) {
            h2 = new ClusterHost();
        }
        this.e = h2;
        return this;
    }

    public String getOnyxHostBaseUrl() {
        return getHost().getOnyxHostBaseUrl();
    }

    public String getOnyxApiBaseUrl() {
        return getApiV1(getHost().getOnyxHostBaseUrl());
    }

    public ServerInfo setOnyxHostBaseUrl(String onyxHostBaseUrl) {
        getHost().setOnyxHostBaseUrl(onyxHostBaseUrl);
        return this;
    }

    public String getOnyxCloudDataHostBaseUrl() {
        return getHost().getOnyxCloudDataHostBaseUrl();
    }

    public String getOnyxCloudDataApiBaseUrl() {
        return getApi(getHost().getOnyxCloudDataHostBaseUrl());
    }

    public ServerInfo setOnyxCloudDataHostBaseUrl(String onyxCloudDataHostBaseUrl) {
        getHost().setOnyxCloudDataHostBaseUrl(onyxCloudDataHostBaseUrl);
        return this;
    }

    public String getOnyxLogHostBaseUrl() {
        return getHost().getOnyxLogHostBaseUrl();
    }

    public String getOnyxLogApiBaseUrl() {
        return getApi(getHost().getOnyxLogHostBaseUrl());
    }

    public ServerInfo setOnyxLogHostBaseUrl(String onyxLogHostBaseUrl) {
        getHost().setOnyxLogHostBaseUrl(onyxLogHostBaseUrl);
        return this;
    }

    public String getOnyxContentHostBaseUrl() {
        return getHost().getOnyxContentHostBaseUrl();
    }

    public String getOnyxContentApiBaseUrl() {
        return getApiV1(getHost().getOnyxContentHostBaseUrl());
    }

    public String getBookShopBaseUrl() {
        return getHost().getShopBookUrl();
    }

    public String getBookShopApiBaseUrl() {
        return getApiV1(getHost().getShopBookUrl());
    }

    public ServerInfo setShopBookApiBaseUrl(String shopBookShopApiBaseUrl) {
        getHost().setShopBookUrl(shopBookShopApiBaseUrl);
        return this;
    }

    public void setOnyxCouchbaseWSSHost(String onyxCouchbaseWSSHost) {
        getHost().setOnyxCouchbaseWSSHost(onyxCouchbaseWSSHost);
    }

    public ServerInfo setOnyxContentHostBaseUrl(String onyxContentHostBaseUrl) {
        getHost().setOnyxContentHostBaseUrl(onyxContentHostBaseUrl);
        return this;
    }

    public String getOnyxSend2BooxHostBaseUrl() {
        return getHost().getOnyxSend2BooxHostBaseUrl();
    }

    public String getOnyxCouchbaseWSSHost() {
        return getHost().getOnyxCouchbaseWSSHost();
    }

    public String getOnyxSend2BooxApiBaseUrl() {
        return getApiV1(getHost().getOnyxSend2BooxHostBaseUrl());
    }

    public ServerInfo setOnyxSend2BooxHostBaseUrl(String onyxSend2BooxHostBaseUrl) {
        getHost().setOnyxSend2BooxHostBaseUrl(onyxSend2BooxHostBaseUrl);
        return this;
    }

    public OssConfigInfo getOssConfig() {
        if (this.f == null) {
            this.f = OssConfigInfo.createDefaultOss();
        }
        return this.f;
    }

    public ServerInfo setOssConfig(OssConfigInfo oss) {
        if (oss == null) {
            oss = OssConfigInfo.createDefaultOss();
        }
        this.f = oss;
        return this;
    }

    public String getServerId() {
        return this.a;
    }

    public ServerInfo setServerId(String serverId) {
        this.a = serverId;
        return this;
    }

    public String getApi(String url) {
        return url + "/api/";
    }

    public String getApiV1(String url) {
        return url + "/api/1/";
    }
}
