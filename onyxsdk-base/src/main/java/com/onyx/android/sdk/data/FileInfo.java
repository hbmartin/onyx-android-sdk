package com.onyx.android.sdk.data;

import androidx.annotation.RequiresApi;
import com.onyx.android.sdk.utils.Benchmark;
import com.onyx.android.sdk.utils.CollectionUtils;
import com.onyx.android.sdk.utils.ComparatorUtils;
import com.onyx.android.sdk.utils.FileUtils;
import com.onyx.android.sdk.utils.StringUtils;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/FileInfo.class */
public class FileInfo implements Serializable {
    private static final boolean m = false;
    private String name;
    private String b;
    private Long c;
    private Long d;
    private String e;
    private String f;
    private boolean g;
    private String h;
    private String i;
    private boolean j;
    private Boolean k;
    private boolean l;

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/FileInfo$a.class */
    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[SortBy.values().length];
            a = iArr;
            try {
                iArr[SortBy.Name.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[SortBy.CreationTime.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[SortBy.FileType.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[SortBy.Size.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public FileInfo() {
        this.g = true;
    }

    private File a(File file) {
        if (file == null) {
            file = file;
            File file2 = new File(StringUtils.safelyGetStr(this.e));
        }
        return file;
    }

    public static FileInfo create(String name, Long lastModified, String path) {
        return new FileInfo(name, lastModified, path);
    }

    public static FileInfo createSimply(File file) {
        return new FileInfo(file.getName(), 0L, file.getAbsolutePath()).setParent(file.getParent()).setDirectory(file.isDirectory());
    }

    public static void sortFileList(List<FileInfo> list, SortBy sortBy, SortOrder sortOrder) {
        if (CollectionUtils.isNullOrEmpty(list)) {
            return;
        }
        Benchmark benchmark = new Benchmark();
        switch (FileInfo.a.a[sortBy.ordinal()]) {
            case 1:
                sortListByName(list, sortOrder);
                break;
            case 2:
                sortListByCreationTime(list, sortOrder);
                break;
            case 3:
                sortListByFileType(list, sortOrder);
                break;
            case 4:
                sortListBySize(list, sortOrder);
                break;
        }
        benchmark.reportWarn("Sort size:" + list.size());
    }

    public static void sortListByName(List<FileInfo> fileList, SortOrder sortOrder) {
        Collections.sort(fileList, (lhs, rhs) -> {
            int iBooleanComparator = ComparatorUtils.booleanComparator(lhs.isDirectory(), rhs.isDirectory(), SortOrder.Desc);
            return iBooleanComparator == 0 ? ComparatorUtils.stringComparator(lhs.getName(), rhs.getName(), sortOrder) : iBooleanComparator;
        });
    }

    public static void sortListByCreationTime(List<FileInfo> fileList, SortOrder sortOrder) {
        Collections.sort(fileList, (lhs, rhs) -> {
            int iBooleanComparator = ComparatorUtils.booleanComparator(lhs.isDirectory(), rhs.isDirectory(), SortOrder.Desc);
            return iBooleanComparator == 0 ? ComparatorUtils.longComparator(lhs.lastModified().longValue(), rhs.lastModified().longValue(), sortOrder) : iBooleanComparator;
        });
    }

    public static void sortListBySize(List<FileInfo> fileList, SortOrder sortOrder) {
        Collections.sort(fileList, (lhs, rhs) -> {
            int iBooleanComparator = ComparatorUtils.booleanComparator(lhs.isDirectory(), rhs.isDirectory(), SortOrder.Desc);
            return iBooleanComparator == 0 ? ComparatorUtils.longComparator(lhs.length(), rhs.length(), sortOrder) : iBooleanComparator;
        });
    }

    public static void sortListByFileType(List<FileInfo> fileList, SortOrder sortOrder) {
        Collections.sort(fileList, (lhs, rhs) -> {
            int iBooleanComparator = ComparatorUtils.booleanComparator(lhs.isDirectory(), rhs.isDirectory(), SortOrder.Desc);
            if (iBooleanComparator != 0) {
                return iBooleanComparator;
            }
            int iStringComparator = ComparatorUtils.stringComparator(FileUtils.getFileExtension(lhs.getName()), FileUtils.getFileExtension(rhs.getName()), sortOrder);
            return iStringComparator == 0 ? ComparatorUtils.stringComparator(StringUtils.toLowerCase(lhs.getBaseName()), StringUtils.toLowerCase(rhs.getBaseName()), SortOrder.Asc) : iStringComparator;
        });
    }

    public static List<FileInfo> buildFileInfoList(List<File> fileList) {
        if (fileList == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<File> it = fileList.iterator();
        while (it.hasNext()) {
            arrayList.add(createSimply(it.next()));
        }
        return arrayList;
    }

    public static List<String> buildPathList(List<FileInfo> infoList) {
        ArrayList arrayList = new ArrayList();
        Iterator<FileInfo> it = infoList.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getPath());
        }
        return arrayList;
    }

    public String getName() {
        return this.b;
    }

    public String getBaseName() {
        String str = this.b;
        if (str == null) {
            return null;
        }
        return FileUtils.getBaseName(str);
    }

    public void setName(String name) {
        this.b = name;
    }

    public Long getSize() {
        return Long.valueOf(length());
    }

    public FileInfo setSize(Long size) {
        this.c = size;
        return this;
    }

    public long length() {
        Long l = this.c;
        if (l == null) {
            return 0L;
        }
        return l.longValue();
    }

    public Long getLastModified() {
        return lastModified();
    }

    public FileInfo setLastModified(Long lastModified) {
        this.d = lastModified;
        return this;
    }

    public Long lastModified() {
        return this.d;
    }

    public String getPath() {
        return this.e;
    }

    public void setPath(String path) {
        this.e = path;
    }

    public boolean isLocal() {
        return this.g;
    }

    public void setLocal(boolean local) {
        this.g = local;
    }

    public FileInfo setId(String id) {
        this.name = id;
        return this;
    }

    public String getId() {
        return this.name;
    }

    public String getObjectKey() {
        return this.h;
    }

    public FileInfo setObjectKey(String objectKey) {
        this.h = objectKey;
        return this;
    }

    public String getDeviceName() {
        return this.i;
    }

    public FileInfo setDeviceName(String deviceName) {
        this.i = deviceName;
        return this;
    }

    public FileInfo setDirectory(boolean directory) {
        this.j = directory;
        return this;
    }

    public boolean isDirectory() {
        return this.j;
    }

    public FileInfo setParent(String parent) {
        this.f = parent;
        return this;
    }

    public String getParent() {
        if (this.f == null && this.e != null) {
            this.f = new File(this.e).getParent();
        }
        return this.f;
    }

    public FileInfo setCanRead(boolean canRead) {
        this.k = Boolean.valueOf(canRead);
        return this;
    }

    public boolean isCanRead() {
        if (this.k == null) {
            String str = this.e;
            if (str == null) {
                return true;
            }
            this.k = Boolean.valueOf(FileUtils.fileCanRead(str));
        }
        return this.k.booleanValue();
    }

    public void initProperty() {
        initProperty(null);
    }

    public void ensureInitLastModified(File file) {
        Long l = this.d;
        if (l == null || l.longValue() <= 0) {
            this.d = Long.valueOf(FileUtils.getLastModified(a(file), false));
        }
    }

    public void ensureInitSize(File file) {
        Long l = this.c;
        if (l == null || l.longValue() <= 0) {
            this.c = Long.valueOf(a(file).length());
        }
    }

    public boolean isHasThumb() {
        return this.l;
    }

    public void setHasThumb(boolean hasThumb) {
        this.l = hasThumb;
    }

    public static FileInfo create(String name, Long size, Long lastModified, String path, boolean local) {
        return new FileInfo(name, size, lastModified, path, local);
    }

    public void initProperty(File file) {
        ensureInitLastModified(file);
        ensureInitSize(file);
    }

    public FileInfo(String name, boolean hasThumb) {
        this.g = true;
        this.b = name;
        this.l = hasThumb;
    }

    public static FileInfo create(File file) {
        return createSimply(file).setLastModified(Long.valueOf(FileUtils.getLastModified(file, false))).setSize(Long.valueOf(file.length()));
    }

    @RequiresApi(api = 26)
    public static FileInfo create(Path path) throws IOException {
        return new FileInfo(path.getFileName().toString(), Long.valueOf(Files.size(path)), Long.valueOf(Files.getLastModifiedTime(path, new LinkOption[0]).toMillis()), path.toAbsolutePath().toString(), true).setDirectory(Files.isDirectory(path, new LinkOption[0])).setParent(path.getParent().toAbsolutePath().toString());
    }

    public FileInfo(String name, Long lastModified, String path) {
        this.g = true;
        this.b = name;
        this.d = lastModified;
        this.e = path;
    }

    public FileInfo(String name, Long size, Long lastModified, String path, boolean local) {
        this.g = true;
        this.b = name;
        this.c = size;
        this.d = lastModified;
        this.e = path;
        this.g = local;
    }
}
