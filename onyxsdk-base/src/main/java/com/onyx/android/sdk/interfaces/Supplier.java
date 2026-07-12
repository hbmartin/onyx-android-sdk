package com.onyx.android.sdk.interfaces;

import androidx.annotation.NonNull;
import java.util.Objects;

public interface Supplier<T> {
   @NonNull
   static <T> T requireNonNull(T obj, Supplier<T> objectSupplier) {
      return obj != null ? obj : Objects.requireNonNull(objectSupplier.get());
   }

   T get();
}
