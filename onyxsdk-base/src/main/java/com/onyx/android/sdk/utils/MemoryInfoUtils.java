// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.utils;

import androidx.annotation.NonNull;
import java.io.BufferedReader;
import java.io.FileReader;

public class MemoryInfoUtils
{
    private static final String a = "/proc/meminfo";
    private static double b;
    
    private static double a() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup2           
        //     4: dstore_0       
        //     5: dconst_0       
        //     6: dcmpl          
        //     7: ifle            12
        //    10: dload_0        
        //    11: dreturn        
        //    12: aconst_null    
        //    13: astore_0       
        //    14: new             Ljava/io/FileReader;
        //    17: dup            
        //    18: astore_1       
        //    19: ldc             "/proc/meminfo"
        //    21: invokespecial   java/io/FileReader.<init>:(Ljava/lang/String;)V
        //    24: new             Ljava/io/BufferedReader;
        //    27: dup            
        //    28: dup            
        //    29: astore_2       
        //    30: aload_1        
        //    31: sipush          8192
        //    34: invokespecial   java/io/BufferedReader.<init>:(Ljava/io/Reader;I)V
        //    37: invokevirtual   java/io/BufferedReader.readLine:()Ljava/lang/String;
        //    40: ldc             "\\s+"
        //    42: invokevirtual   java/lang/String.split:(Ljava/lang/String;)[Ljava/lang/String;
        //    45: iconst_1       
        //    46: aaload         
        //    47: dup            
        //    48: astore_0       
        //    49: ifnull          70
        //    52: aload_0        
        //    53: invokestatic    java/lang/Double.valueOf:(Ljava/lang/String;)Ljava/lang/Double;
        //    56: invokevirtual   java/lang/Double.doubleValue:()D
        //    59: getstatic       com/onyx/android/sdk/utils/MemoryInfoUtils$MemoryFormat.KB:Lcom/onyx/android/sdk/utils/MemoryInfoUtils$MemoryFormat;
        //    62: invokevirtual   com/onyx/android/sdk/utils/MemoryInfoUtils$MemoryFormat.getValue:()I
        //    65: i2d            
        //    66: dmul           
        //    67: putstatic       com/onyx/android/sdk/utils/MemoryInfoUtils.b:D
        //    70: aload_2        
        //    71: dup            
        //    72: invokevirtual   java/io/BufferedReader.close:()V
        //    75: invokestatic    com/onyx/android/sdk/utils/FileUtils.closeQuietly:(Ljava/io/Closeable;)V
        //    78: goto            109
        //    81: astore_0       
        //    82: aload_0        
        //    83: astore_1       
        //    84: goto            143
        //    87: goto            98
        //    90: astore_1       
        //    91: aload_0        
        //    92: astore_2       
        //    93: goto            143
        //    96: aload_0        
        //    97: astore_2       
        //    98: invokevirtual   java/lang/Exception.printStackTrace:()V
        //   101: aload_2        
        //   102: ifnull          109
        //   105: aload_2        
        //   106: invokestatic    com/onyx/android/sdk/utils/FileUtils.closeQuietly:(Ljava/io/Closeable;)V
        //   109: new             Ljava/lang/StringBuilder;
        //   112: dup            
        //   113: invokespecial   java/lang/StringBuilder.<init>:()V
        //   116: ldc             "total ram is "
        //   118: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   121: getstatic       com/onyx/android/sdk/utils/MemoryInfoUtils.b:D
        //   124: invokevirtual   java/lang/StringBuilder.append:(D)Ljava/lang/StringBuilder;
        //   127: ldc             " b"
        //   129: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   132: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   135: invokestatic    com/onyx/android/sdk/utils/Debug.d:(Ljava/lang/String;)V
        //   138: getstatic       com/onyx/android/sdk/utils/MemoryInfoUtils.b:D
        //   141: dreturn        
        //   142: astore_1       
        //   143: aload_2        
        //   144: ifnull          151
        //   147: aload_2        
        //   148: invokestatic    com/onyx/android/sdk/utils/FileUtils.closeQuietly:(Ljava/io/Closeable;)V
        //   151: aload_1        
        //   152: athrow         
        //    StackMapTable: 00 0B 0C FE 00 39 00 00 07 00 20 4A 07 00 4B 45 07 00 4D FF 00 02 00 01 07 00 20 00 01 07 00 4B 45 07 00 4D FF 00 01 00 03 00 00 07 00 20 00 01 07 00 4D F8 00 0A FF 00 20 00 03 00 00 07 00 20 00 01 07 00 4B FF 00 00 00 03 00 07 00 4B 07 00 20 00 00 FA 00 07
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  14     17     96     98     Ljava/lang/Exception;
        //  14     17     90     96     Any
        //  19     27     96     98     Ljava/lang/Exception;
        //  19     27     90     96     Any
        //  30     37     96     98     Ljava/lang/Exception;
        //  30     37     90     96     Any
        //  37     47     87     90     Ljava/lang/Exception;
        //  37     47     81     87     Any
        //  52     65     87     90     Ljava/lang/Exception;
        //  52     65     81     87     Any
        //  67     75     87     90     Ljava/lang/Exception;
        //  67     75     81     87     Any
        //  98     101    142    143    Any
        // 
        // The error that occurred was:
        // 
        // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
        //     at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
        //     at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
        //     at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:266)
        //     at java.base/java.util.Objects.checkIndex(Objects.java:361)
        //     at java.base/java.util.ArrayList.remove(ArrayList.java:504)
        //     at com.strobel.assembler.ir.StackMappingVisitor.pop(StackMappingVisitor.java:267)
        //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.execute(StackMappingVisitor.java:780)
        //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.visit(StackMappingVisitor.java:398)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2086)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:203)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:93)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:868)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:761)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:638)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:605)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:195)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:162)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:137)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:333)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:254)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:144)
        // 
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
