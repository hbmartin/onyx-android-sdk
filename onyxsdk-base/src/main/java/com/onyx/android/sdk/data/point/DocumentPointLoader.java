// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.data.point;

import com.onyx.android.sdk.utils.ClassUtils;
import com.onyx.android.sdk.utils.Benchmark;
import com.onyx.android.sdk.utils.CollectionUtils;
import com.onyx.android.sdk.utils.FileUtils;
import java.util.Set;
import androidx.annotation.Nullable;
import com.onyx.android.sdk.utils.StringUtils;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import com.onyx.android.sdk.utils.Debug;
import java.io.File;
import java.util.Map;

public class DocumentPointLoader
{
    private String a;
    private final Map<String, String> b;
    
    public DocumentPointLoader(final String documentPointDirPath, final Map<String, String> revisionIdMapPointPathCache) {
        this.a = documentPointDirPath;
        this.b = revisionIdMapPointPathCache;
    }
    
    private Map<String, String> a() {
        return this.b;
    }
    
    private String a(final String pageId, final String revisionId) {
        final StringBuilder append = new StringBuilder().append(this.a);
        final String separator = File.separator;
        return append.append(separator).append(pageId).append(separator).append(PointDocumentUtils.getPointFileName(pageId, revisionId)).toString();
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
    
    private void a(final Map<String, String> revisionPathMap) {
        if (StringUtils.isNullOrEmpty(this.a)) {
            return;
        }
        final File[] listFiles;
        if ((listFiles = new File(this.a).listFiles()) == null) {
            return;
        }
        for (int length = listFiles.length, i = 0; i < length; ++i) {
            final File file;
            if (!(file = listFiles[i]).isFile()) {
                this.a(revisionPathMap, file);
            }
        }
    }
    
    private void a(final Map<String, String> revisionPathMap, final File file) {
        final File[] listFiles;
        if ((listFiles = file.listFiles()) == null) {
            return;
        }
        for (int length = listFiles.length, i = 0; i < length; ++i) {
            final File file2;
            if (this.a((file2 = listFiles[i]).getName())) {
                final File file3 = file2;
                final String revisionId = PointDocumentUtils.parseRevisionId(file2.getName());
                this.a().put(revisionId, file2.getAbsolutePath());
                revisionPathMap.put(revisionId, file3.getAbsolutePath());
            }
        }
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
    
    private boolean a(final String fileName) {
        return !StringUtils.isNullOrEmpty(fileName) && fileName.endsWith("points");
    }
    
    private boolean a(final Map<String, List<String>> revisionShapeIdMap, final Map<String, String> revisionPathMap) {
        final Set<String> keySet = revisionShapeIdMap.keySet();
        boolean b = true;
        for (final String s : keySet) {
            final String s2;
            if (StringUtils.isNotBlank(s2 = this.a().get(s))) {
                revisionPathMap.put(s, s2);
            }
            else {
                b = false;
            }
        }
        final boolean b2 = b;
        Debug.d((Class)this.getClass(), "isAllInCache = " + b, new Object[0]);
        return b2;
    }
    
    public boolean saveShapePointList(final String pageId, final List<ShapeRepo> repoList, final String revisionId) {
        if (StringUtils.isNullOrEmpty(this.a)) {
            return false;
        }
        try {
            final String pointPath = this.a(pageId, revisionId);
            this.a().put(revisionId, pointPath);
            return new PointDocument(pointPath).setPageUniqueId(pageId).setRevisionId(revisionId).setShapeRepoList(repoList).save();
        }
        catch (final Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public Map<String, ShapeRepo> loadShapePointList(final Map<String, List<String>> revisionShapeIdMap) {
        final HashMap<String, ShapeRepo> hashMap = new HashMap<String, ShapeRepo>();
        final Map<String, String> loadRevisionPathMap = this.loadRevisionPathMap(revisionShapeIdMap);
        final Iterator<Map.Entry<String, List<String>>> iterator = revisionShapeIdMap.entrySet().iterator();
        while (iterator.hasNext()) {
            final Map.Entry entry;
            final String s;
            if (!FileUtils.fileCanRead(s = loadRevisionPathMap.get((entry = iterator.next()).getKey()))) {
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
    
    public Map<String, String> loadRevisionPathMap(final Map<String, List<String>> revisionShapeIdMap) {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        final Benchmark benchmark = new Benchmark();
        if (!this.a(revisionShapeIdMap, hashMap)) {
            this.a(hashMap);
        }
        if (Debug.getDebug()) {
            benchmark.report(ClassUtils.getSimpleName((Class)this.getClass()) + ", initRevisionPathMap, documentPointDirPath = " + this.a);
        }
        return hashMap;
    }
}
