// 
// 

package com.onyx.android.sdk.data.point;

import com.onyx.android.sdk.utils.FileUtils;
import java.util.Collection;
import com.onyx.android.sdk.utils.CollectionUtils;
import androidx.annotation.NonNull;
import java.util.Iterator;
import java.util.HashMap;
import com.onyx.android.sdk.utils.Debug;
import java.util.LinkedHashMap;
import java.util.Map;
import com.onyx.android.sdk.utils.StringUtils;
import androidx.annotation.Nullable;
import java.util.List;
import java.io.File;

public class PagePointLoader
{
    private String a;
    private String b;
    private String c;
    
    public PagePointLoader(final String pageUniqueId, final String pagePointDirPath) {
        this.a = pageUniqueId;
        this.c = pagePointDirPath;
    }
    
    @Nullable
    private List<ShapeRepo> a(final File file, final int start, final int count) {
        try {
            return new PointDocument(file.getAbsolutePath()).parse(start, count).getShapeRepoList();
        }
        catch (final Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private boolean a(final String fileName) {
        if (StringUtils.isNullOrEmpty(fileName)) {
            return false;
        }
        if (StringUtils.isNullOrEmpty(this.b)) {
            return fileName.startsWith(this.a) && fileName.endsWith("points");
        }
        return StringUtils.safelyEquals(fileName, PointDocumentUtils.getPointFileName(this.a, this.b));
    }
    
    private Map<String, String> a() {
        final LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>();
        final File[] listFiles;
        if ((listFiles = new File(this.c).listFiles()) == null) {
            return linkedHashMap;
        }
        for (int length = listFiles.length, i = 0; i < length; ++i) {
            final File file;
            if (this.a((file = listFiles[i]).getName())) {
                final LinkedHashMap<String, String> linkedHashMap2 = linkedHashMap;
                final File file2 = file;
                linkedHashMap2.put(PointDocumentUtils.parseRevisionId(file2.getName()), file2.getAbsolutePath());
            }
        }
        return linkedHashMap;
    }
    
    private Map<String, ShapeRepo> b(final String pointPath) {
        final PointDocument a;
        if ((a = this.a(new File(pointPath))) == null) {
            Debug.w((Class)this.getClass(), "pointDocument empty, " + pointPath, new Object[0]);
            return null;
        }
        final List<ShapeRepo> shapeRepoList = a.getShapeRepoList();
        final HashMap<String, ShapeRepo> hashMap = new HashMap<String, ShapeRepo>();
        final Iterator<ShapeRepo> iterator = shapeRepoList.iterator();
        while (iterator.hasNext()) {
            final HashMap<String, ShapeRepo> hashMap2 = hashMap;
            final ShapeRepo shapeRepo = iterator.next();
            hashMap2.put(shapeRepo.getShapeUniqueId(), shapeRepo);
        }
        return hashMap;
    }
    
    @Nullable
    private PointDocument a(final File file) {
        try {
            return new PointDocument(file.getAbsolutePath()).parse();
        }
        catch (final Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public PagePointLoader setRevisionId(final String revisionId) {
        this.b = revisionId;
        return this;
    }
    
    @NonNull
    public LinkedHashMap<String, ShapeRepo> loadShapePointList() {
        return this.loadShapePointList(0, Integer.MAX_VALUE);
    }
    
    @NonNull
    public LinkedHashMap<String, ShapeRepo> loadShapePointList(final int start, final int count) {
        final LinkedHashMap<String, ShapeRepo> linkedHashMap = new LinkedHashMap<String, ShapeRepo>();
        if (StringUtils.isNullOrEmpty(this.c)) {
            return linkedHashMap;
        }
        final File[] listFiles;
        if ((listFiles = new File(this.c).listFiles()) == null) {
            return linkedHashMap;
        }
        for (int length = listFiles.length, i = 0; i < length; ++i) {
            final File file;
            if (this.a((file = listFiles[i]).getName())) {
                final List<ShapeRepo> a;
                if (!CollectionUtils.isNullOrEmpty(a = this.a(file, start, count))) {
                    final Iterator<ShapeRepo> iterator = a.iterator();
                    while (iterator.hasNext()) {
                        final LinkedHashMap<String, ShapeRepo> linkedHashMap2 = linkedHashMap;
                        final ShapeRepo value = iterator.next();
                        linkedHashMap2.put(value.getShapeUniqueId(), value);
                    }
                }
            }
        }
        return linkedHashMap;
    }
    
    public boolean saveShapePointList(final List<ShapeRepo> repoList, final String revisionId) {
        if (StringUtils.isNullOrEmpty(this.c)) {
            return false;
        }
        try {
            return new PointDocument(this.getPointFilePath(revisionId)).setPageUniqueId(this.a).setRevisionId(revisionId).setShapeRepoList(repoList).save();
        }
        catch (final Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public String getPointFilePath(final String revisionId) {
        return this.c + File.separator + PointDocumentUtils.getPointFileName(this.a, revisionId);
    }
    
    public Map<String, ShapeRepo> loadShapePointList(final Map<String, List<String>> revisionShapeIdMap) {
        final HashMap<String, ShapeRepo> hashMap = new HashMap<String, ShapeRepo>();
        final Map<String, String> a = this.a();
        final Iterator<Map.Entry<String, List<String>>> iterator = revisionShapeIdMap.entrySet().iterator();
        while (iterator.hasNext()) {
            final Map.Entry entry;
            final String s;
            if (!FileUtils.fileCanRead(s = a.get((entry = iterator.next()).getKey()))) {
                Debug.w((Class)this.getClass(), "file cannot read, " + s, new Object[0]);
            }
            else {
                final Map<String, ShapeRepo> b;
                if (CollectionUtils.isNullOrEmpty(b = this.b(s))) {
                    continue;
                }
                final Iterator iterator2 = ((List)entry.getValue()).iterator();
                while (iterator2.hasNext()) {
                    final HashMap<String, ShapeRepo> hashMap2 = hashMap;
                    final Map<String, ShapeRepo> map = b;
                    final String s2 = (String)iterator2.next();
                    hashMap2.put(s2, map.get(s2));
                }
            }
        }
        return hashMap;
    }
}
