package com.onyx.android.sdk.data.note;

import com.alibaba.fastjson2.annotation.JSONField;
import com.onyx.android.sdk.data.sync.ResourcePathUtils;
import com.onyx.android.sdk.path.KSyncFilePaths;
import com.onyx.android.sdk.utils.FileUtils;
import com.onyx.android.sdk.utils.StringUtils;
import io.reactivex.annotations.Nullable;
import java.io.File;
import java.io.Serializable;

public class ShapeResource implements Serializable, Cloneable {
  public String uniqueId;
  
  public String localPath;
  
  public String ossUrl;
  
  public String title;
  
  public long audioDuration;
  
  public String fileExtension;
  
  public String documentId;
  
  public String revisionId;
  
  public String relativePath;
  
  public int status;
  
  public int type;
  
  public ShapeResource() {
    this
      .status = ResourceStatus.ENABLED;
    this.type = 0;
  }
  
  public static boolean isValidResource(ShapeResource resource, int type) {
    return (resource != null && resource.getType() == type && resource.isLocalExist());
  }
  
  @Nullable
  public static String searchLocalPath(String documentId, String localPath) {
    return searchLocalPathByRelativePath(documentId, ResourcePathUtils.getRelativePath(documentId, localPath));
  }
  
  @Nullable
  public static String searchLocalPathByRelativePath(String documentId, String relativePath) {
    if (StringUtils.isNotBlank(relativePath))
      for (String str1 : KSyncFilePaths.INSTANCE.getBooxSyncFilePathList()) {
        String str2;
        StringBuilder stringBuilder;
        if (FileUtils.fileExist(str2 = (stringBuilder = new StringBuilder()).append(ResourcePathUtils.getResourceDataDirPath(str1, documentId)).append(relativePath).toString()))
          return str2; 
        if (FileUtils.fileExist(str1 = str1 + (str1 = File.separator) + "resource" + str1 + relativePath))
          return str1; 
      }  
    return null;
  }
  
  @JSONField(serialize = false, deserialize = false)
  public boolean isLocalExist() {
    String str;
    return (StringUtils.isNotBlank(str = getPath()) && FileUtils.fileExist(str));
  }
  
  @JSONField(serialize = false, deserialize = false)
  public boolean isCloudExist() {
    return StringUtils.isNotBlank(this.ossUrl);
  }
  
  @JSONField(serialize = false, deserialize = false)
  public boolean isImageResource() {
    return (this.type == 0);
  }
  
  @JSONField(serialize = false, deserialize = false)
  public boolean isAudioResource() {
    return (this.type == 2);
  }
  
  public String getTitle() {
    return this.title;
  }
  
  public ShapeResource setTitle(String title) {
    this.title = title;
    return this;
  }
  
  public long getAudioDuration() {
    return this.audioDuration;
  }
  
  public ShapeResource setAudioDuration(long audioDuration) {
    this.audioDuration = audioDuration;
    return this;
  }
  
  public String getUniqueId() {
    return this.uniqueId;
  }
  
  public ShapeResource setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
    return this;
  }
  
  @Deprecated
  public String getLocalPath() {
    return this.localPath;
  }
  
  public ShapeResource setLocalPath(String localPath) {
    this.localPath = localPath;
    return this;
  }
  
  @JSONField(serialize = false, deserialize = false)
  public String getPath() {
    if (StringUtils.isNotBlank(this.relativePath))
      return searchLocalPathByRelativePath(this.documentId, this.relativePath); 
    if (StringUtils.isNullOrEmpty(this.localPath) || FileUtils.fileExist(this.localPath))
      return this.localPath; 
    return searchLocalPath(this.documentId, this.localPath);
  }
  
  public String getOssUrl() {
    return this.ossUrl;
  }
  
  public ShapeResource setOssUrl(String ossUrl) {
    this.ossUrl = ossUrl;
    return this;
  }
  
  public int getType() {
    return this.type;
  }
  
  public ShapeResource setType(int type) {
    this.type = type;
    return this;
  }
  
  public String getRevisionId() {
    return this.revisionId;
  }
  
  public ShapeResource setRevisionId(String revisionId) {
    this.revisionId = revisionId;
    return this;
  }
  
  public String getRelativePath() {
    return this.relativePath;
  }
  
  public ShapeResource setRelativePath(String relativePath) {
    this.relativePath = relativePath;
    return this;
  }
  
  public String getFileExtension() {
    return this.fileExtension;
  }
  
  public ShapeResource setFileExtension(String fileExtension) {
    this.fileExtension = fileExtension;
    return this;
  }
  
  public ShapeResource setStatus(int status) {
    this.status = status;
    return this;
  }
  
  public int getStatus() {
    return this.status;
  }
  
  public String getDocumentId() {
    return this.documentId;
  }
  
  public ShapeResource setDocumentId(String docId) {
    this.documentId = docId;
    return this;
  }
  
  public String toPathString() {
    return "localPath: " + this.localPath + ", is exist: " + FileUtils.fileExist(this.localPath) + ", relativePath: " + this.relativePath + ", path: " + getPath() + ", is exist: " + FileUtils.fileExist(getPath());
  }
  
  public ShapeResource clone() {
    try {
      return (ShapeResource) super.clone();
    } catch (CloneNotSupportedException exception) {
      exception.printStackTrace();
      return null;
    }
  }
  
  public ShapeResource(String uniqueId, String localPath, int type) {
    this.status = ResourceStatus.ENABLED;
    this.type = 0;
    this.uniqueId = uniqueId;
    this.localPath = localPath;
    this.type = type;
  }
}
