package com.onyx.android.sdk.data;

import java.util.List;

public interface TreeEntry<T> {
   String getTitle();

   String getUniqueId();

   List<T> getChildren();
}
