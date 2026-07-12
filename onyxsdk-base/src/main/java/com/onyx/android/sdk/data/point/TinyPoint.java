package com.onyx.android.sdk.data.point;

public class TinyPoint {
   private float a;
   private float b;
   private short c;
   private short d;
   private int e;

   public TinyPoint() {
   }

   public TinyPoint(float x, float y, short pressure, short size, int time) {
      this.a = x;
      this.b = y;
      this.c = size;
      this.d = pressure;
      this.e = time;
   }

   public float getX() {
      return this.a;
   }

   public TinyPoint setX(float x) {
      this.a = x;
      return this;
   }

   public float getY() {
      return this.b;
   }

   public TinyPoint setY(float y) {
      this.b = y;
      return this;
   }

   public short getSize() {
      return this.c;
   }

   public TinyPoint setSize(short size) {
      this.c = size;
      return this;
   }

   public short getPressure() {
      return this.d;
   }

   public TinyPoint setPressure(short pressure) {
      this.d = pressure;
      return this;
   }

   public int getTime() {
      return this.e;
   }

   public TinyPoint setTime(int time) {
      this.e = time;
      return this;
   }
}
