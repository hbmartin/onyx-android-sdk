package com.onyx.android.sdk.data.note;

import androidx.annotation.Nullable;
import com.onyx.android.sdk.utils.CollectionUtils;
import com.onyx.android.sdk.utils.StringUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PageNameList implements Serializable {
    private static final long serialVersionUID = -705150527872278930L;
    private List<String> a = new ArrayList();

    public static PageNameList clone(PageNameList pageNameList) {
        PageNameList pageNameList2 = new PageNameList();
        if (pageNameList == null) {
            return pageNameList2;
        }
        pageNameList2.addAll(new ArrayList(pageNameList.getPageNameList()));
        return pageNameList2;
    }

    public static PageNameList mergeWithRemove(PageNameList src, PageNameList dst, PageNameList srcRemove, PageNameList dstRemove) {
        PageNameList pageNameListMerge = merge(src, dst);
        pageNameListMerge.remove(merge(srcRemove, dstRemove));
        return pageNameListMerge;
    }

    public static boolean isEmpty(PageNameList list) {
        return list == null || CollectionUtils.isNullOrEmpty(list.getPageNameList());
    }

    public void add(String name) {
        this.a.add(name);
    }

    public void remove(String name) {
        this.a.remove(name);
    }

    public int size() {
        return this.a.size();
    }

    public String get(int i) {
        return this.a.get(i);
    }

    public PageNameList addAll(List<String> list) {
        this.a.addAll(list);
        return this;
    }

    public void clear() {
        this.a.clear();
    }

    public List<String> getPageNameList() {
        return this.a;
    }

    public PageNameList subPageList(int count) {
        this.a = CollectionUtils.safelySubList(this.a, 0, count);
        return this;
    }

    public PageNameList subPageListLimit() {
        return subPageList(500);
    }

    public void setPageNameList(List<String> set) {
        this.a = set;
    }

    public boolean contains(String name) {
        Iterator<String> it = this.a.iterator();
        while (it.hasNext()) {
            if (it.next().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public int indexOf(String name) {
        return this.a.indexOf(name);
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof PageNameList)) {
            return false;
        }
        PageNameList pageNameList = (PageNameList) obj;
        if (size() != pageNameList.size()) {
            return false;
        }
        int size = size();
        for (int i = 0; i < size; i++) {
            if (!StringUtils.safelyEquals(get(i), pageNameList.get(i))) {
                return false;
            }
        }
        return true;
    }

    public void merge(PageNameList nameList) {
        if (nameList == null || CollectionUtils.isNullOrEmpty(nameList.getPageNameList())) {
            return;
        }
        for (String str : nameList.getPageNameList()) {
            if (!getPageNameList().contains(str)) {
                add(str);
            }
        }
    }

    public PageNameList remove(PageNameList nameList) {
        if (nameList == null) {
            return this;
        }
        Iterator<String> it = nameList.getPageNameList().iterator();
        while (it.hasNext()) {
            remove(it.next());
        }
        return this;
    }

    public static PageNameList merge(PageNameList a, PageNameList b) {
        PageNameList pageNameList = new PageNameList();
        pageNameList.merge(a);
        pageNameList.merge(b);
        return pageNameList;
    }
}
