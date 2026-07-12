package com.onyx.android.sdk.data.point;

import java.util.List;

public class ShapeRepo {
   private String a;
   private int b;
   private int c;
   private short d;
   private short e;
   private List<TinyPoint> f;

   public String getShapeUniqueId() {
      return this.a;
   }

   public ShapeRepo setShapeUniqueId(String shapeUniqueId) {
      this.a = shapeUniqueId;
      return this;
   }

   public int getOffset() {
      return this.b;
   }

   public ShapeRepo setOffset(int offset) {
      this.b = offset;
      return this;
   }

   public int getLength() {
      return this.c;
   }

   public ShapeRepo setLength(int length) {
      this.c = length;
      return this;
   }

   public List<TinyPoint> getTinyPointList() {
      return this.f;
   }

   public ShapeRepo setTinyPointList(List<TinyPoint> tinyPointList) {
      this.f = tinyPointList;
      return this;
   }

   public short getAttrA() {
      return this.d;
   }

   public ShapeRepo setAttrA(short attrA) {
      this.d = attrA;
      return this;
   }

   public short getAttrB() {
      return this.e;
   }

   public ShapeRepo setAttrB(short attrB) {
      this.e = attrB;
      return this;
   }
}
