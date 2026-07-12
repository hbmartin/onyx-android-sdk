package com.onyx.android.sdk.utils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelUtils {
   public static short readShort(FileChannel channel) throws IOException {
      ByteBuffer var10000 = ByteBuffer.allocate(2);
      channel.read(var10000);
      return var10000.getShort(0);
   }

   public static float readFloat(FileChannel channel) throws IOException {
      return Float.intBitsToFloat(readInt(channel));
   }

   public static int readInt(FileChannel channel) throws IOException {
      ByteBuffer var10000 = ByteBuffer.allocate(4);
      channel.read(var10000);
      return var10000.getInt(0);
   }
}
