
package com.onyx.android.sdk.utils;

import androidx.annotation.NonNull;
import java.io.BufferedReader;
import java.io.FileReader;

public class MemoryInfoUtils
{
    private static final String a = "/proc/meminfo";
    private static double b;
    
    private static double a() {
        if (b > 0.0d) {
            return b;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(a), 8192)) {
            String line = reader.readLine();
            if (line != null) {
                String[] fields = line.split("\\s+");
                if (fields.length > 1) {
                    b = Double.valueOf(fields[1]) * MemoryFormat.KB.getValue();
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        Debug.d("total ram is " + b + " b");
        return b;
    }
    
    public static double getTotalRam(@NonNull final MemoryFormat format) {
        return a() / format.getValue();
    }
    
    public enum MemoryFormat
    {
        GB(1000000000), 
        MB(1000000), 
        KB(1000), 
        B(1);
        
        private int a;
        
        private MemoryFormat(final int value) {
            this.a = 1;
            this.a = value;
        }
        
        public int getValue() {
            return this.a;
        }
    }
}
