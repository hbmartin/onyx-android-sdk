package com.onyx.android.sdk.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AssociationType {
   public static final int INVALID = -1;
   public static final int NONE = 0;
   public static final int READING = 1;
   public static final int MEMO = 2;
   public static final int GROUP = 3;
   public static final int FAVORITE = 4;
   public static final int[] DISABLE_FAVORITE_TYPE = new int[]{-1, 2};

   public static boolean isGroupType(int associationType) {
      return associationType == 3;
   }

   public static List<Integer> getAllTypes() {
      return Arrays.asList(0, 1, 2, 3, 4);
   }

   public static List<Integer> getReadingTypes() {
      return Collections.singletonList(1);
   }

   public static List<Integer> getTypesWithoutGroup() {
      ArrayList var10000 = new ArrayList<>(getAllTypes());
      var10000.remove(3);
      return var10000;
   }
}
