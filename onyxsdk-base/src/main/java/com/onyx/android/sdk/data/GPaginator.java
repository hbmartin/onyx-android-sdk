package com.onyx.android.sdk.data;

public class GPaginator {
   private int a;
   private int b;
   private int c = -1;
   private int d;

   public GPaginator() {
      this.resize(1, 1, 0);
   }

   public GPaginator(int r, int c, int s) {
      this.resize(r, c, s);
   }

   public static String progressText(int current, int total) {
      Object[] var2;
      Object[] var10000 = var2 = new Object[2];
      var2[0] = current;
      var10000[1] = total;
      return String.format("%d / %d", var2);
   }

   public int getCurrentPage() {
      return this.c;
   }

   public int getVisibleCurrentPage() {
      return this.pages() != 0 ? this.c + 1 : 0;
   }

   public void setCurrentPage(int currentPage) {
      this.c = currentPage;
   }

   public void resize(int newRows, int newColumns, int newSize) {
      this.a = newRows;
      this.b = newColumns;
      this.d = newSize;
   }

   public void resize(int newSize) {
      this.d = newSize;
   }

   public int getRows() {
      return this.a;
   }

   public int getColumns() {
      return this.b;
   }

   public int getSize() {
      return this.d;
   }

   public int itemsPerPage() {
      int var1;
      if ((var1 = this.a * this.b) <= 0) {
         var1 = 1;
      }

      return var1;
   }

   public int itemsInCurrentPage() {
      int var1 = this.itemsPerPage();
      if (this.c < this.lastPage()) {
         return var1;
      }

      int var2;
      if ((var2 = this.d % var1) == 0) {
         var2 = var1;
      }

      return var2;
   }

   public int pages() {
      int var3 = this.itemsPerPage();
      int var1;
      int var2;
      return (var2 = (var1 = this.d) / var3) * var3 < var1 ? var2 + 1 : var2;
   }

   public int lastPage() {
      return this.pages() - 1;
   }

   public boolean gotoPageByIndex(int index) {
      int var2;
      return (var2 = this.pageByIndex(index)) < 0 ? false : this.gotoPage(var2);
   }

   public int rowInCurrentPage(int index) {
      return (index - this.getCurrentPageBegin()) / this.getColumns();
   }

   public int columnInCurrentPage(int index) {
      return (index - this.getCurrentPageBegin()) % this.getColumns();
   }

   public int offsetInCurrentPage(int index) {
      return index - this.getCurrentPageBegin();
   }

   public int pageByIndex(int index) {
      return index >= 0 && index < this.d ? index / this.itemsPerPage() : -1;
   }

   public boolean hasNextPage() {
      return this.c + 1 < this.pages();
   }

   public boolean hasPrevPage() {
      return this.c > 0;
   }

   public boolean nextPage() {
      if (this.c + 1 < this.pages()) {
         this.gotoPage(this.c + 1);
         return true;
      } else {
         return false;
      }
   }

   public boolean prevPage() {
      int var1 = this.c;
      if (this.c > 0) {
         this.gotoPage(var1 - 1);
         return true;
      } else {
         return false;
      }
   }

   public boolean gotoPage(int newPage) {
      int var2 = this.pages();
      if (newPage >= 0 && newPage < var2) {
         this.setCurrentPage(newPage);
         return true;
      } else {
         return false;
      }
   }

   public int indexByPageOffset(int offset) {
      return this.getCurrentPageBegin() + offset;
   }

   public boolean isItemInCurrentPage(int index) {
      return this.getCurrentPageBegin() <= index && index < this.getCurrentPageBegin() + this.itemsInCurrentPage();
   }

   public boolean isInPrevPage(int index) {
      return index < this.getCurrentPageBegin();
   }

   public boolean isInNextPage(int index) {
      return index >= this.getCurrentPageBegin() + this.itemsInCurrentPage();
   }

   public int getCurrentPageBegin() {
      return this.getPageBegin(this.c);
   }

   public int getCurrentPageEnd() {
      return this.getPageEnd(this.c);
   }

   public int getPageBegin(int page) {
      return page >= 0 && page < this.pages() ? page * this.a * this.b : -1;
   }

   public int getPageEnd(int page) {
      if (page >= 0 && page < this.pages()) {
         int var2;
         if ((page = (page + 1) * this.a * this.b - 1) >= (var2 = this.d)) {
            return var2 - 1;
         } else {
            return page < 0 ? 0 : page;
         }
      } else {
         return -1;
      }
   }

   public boolean isBottomItemPosition(int position) {
      return position >= this.getBottomItemPosition() && position <= this.getCurrentPageEnd();
   }

   public int getBottomItemPosition() {
      return this.getCurrentPageBegin() + this.getColumns() * this.getRows() - this.b;
   }

   public int getCurrentPageBeginRow() {
      return this.rowInCurrentPage(this.getCurrentPageBegin());
   }

   public int getCurrentPageEndRow() {
      return this.rowInCurrentPage(this.getCurrentPageEnd());
   }

   public int nextColumn(int index) {
      return index + 1;
   }

   public int prevColumn(int index) {
      return index - 1;
   }

   public int nextRow(int index) {
      return index + this.getColumns();
   }

   public int prevRow(int index) {
      return index - this.getColumns();
   }

   public boolean isFirstPage() {
      return this.c == 0;
   }

   public boolean isLastPage() {
      return this.lastPage() == this.c;
   }

   public boolean isFirstRowFirstItem(int index) {
      return index % (this.a * this.b) == 0;
   }

   public boolean isFirstRowLastItem(int index) {
      return index % (this.a * this.b) == this.getColumns() - 1;
   }

   public boolean isLastRowFirstItem(int index) {
      int var2;
      int var3;
      return index % ((var3 = this.a) * (var2 = this.b)) == (var3 - 1) * var2;
   }

   public boolean isLastRowLastItem(int index) {
      int var2;
      int var3;
      return index % ((var3 = this.a) * (var2 = this.b)) == var3 * var2 - 1;
   }

   public String getProgressText() {
      return this.getVisibleCurrentPage() + "/" + this.pages();
   }

   public String getDisplayProgressText() {
      return Math.max(1, this.getCurrentPage() + 1) + "/" + Math.max(1, this.pages());
   }

   public int resetInvalidCurrentPage() {
      if (this.pages() > 0 && this.getCurrentPage() > this.pages() - 1 || this.getCurrentPage() < 0) {
         this.setCurrentPage(0);
      }

      return this.getCurrentPage();
   }
}
