package com.onyx.android.sdk.data.file;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public interface BaseStream {
   FileOutputStream getOutputStream(String var1, boolean var2) throws FileNotFoundException;
}
