package com.onyx.android.sdk.data;

public class PenStatusBean {
   private PenStatus a = PenStatus.UP;
   private int b;

   public static PenStatusBean copy(PenStatusBean bean) {
      return new PenStatusBean().setPenStatus(bean.getPenStatus()).setHoldCount(bean.getHoldCount());
   }

   public PenStatusBean setPenStatus(PenStatus penStatus) {
      this.a = penStatus;
      return this;
   }

   public PenStatusBean setHoldCount(int holdCount) {
      this.b = holdCount;
      return this;
   }

   public PenStatus getPenStatus() {
      return this.a;
   }

   public int getHoldCount() {
      return this.b;
   }

   public void holdCountIncrease() {
      this.b++;
   }

   public boolean isUpAfterHold() {
      return this.getPenStatus() == PenStatus.UP && this.getHoldCount() > 0;
   }

   public boolean isUp() {
      return this.getPenStatus() == PenStatus.UP;
   }

   public boolean isHolding() {
      return this.getPenStatus() == PenStatus.HOLDING;
   }

   public boolean isMoving() {
      return this.getPenStatus() == PenStatus.MOVING;
   }
}
