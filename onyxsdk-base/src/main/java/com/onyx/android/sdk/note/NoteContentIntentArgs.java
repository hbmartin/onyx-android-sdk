package com.onyx.android.sdk.note;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class NoteContentIntentArgs implements Parcelable {
   public static final Creator<NoteContentIntentArgs> CREATOR = new Creator<NoteContentIntentArgs>() {
      public NoteContentIntentArgs createFromParcel(Parcel in) {
         return new NoteContentIntentArgs(in);
      }

      public NoteContentIntentArgs[] newArray(int size) {
         return new NoteContentIntentArgs[size];
      }
   };
   private String docId;
   private ParcelFileDescriptor pfd;
   private String content;

   public NoteContentIntentArgs() {
   }

   protected NoteContentIntentArgs(Parcel in) {
      this.docId = in.readString();
      this.content = in.readString();
      this.pfd = (ParcelFileDescriptor)in.readParcelable(ParcelFileDescriptor.class.getClassLoader());
   }

   public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(this.docId);
      dest.writeString(this.content);
      dest.writeParcelable(this.pfd, flags);
   }

   public int describeContents() {
      return 0;
   }

   public String getDocId() {
      return this.docId;
   }

   public NoteContentIntentArgs setDocId(String docId) {
      this.docId = docId;
      return this;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public String getContent() {
      return this.content;
   }

   public ParcelFileDescriptor getPfd() {
      return this.pfd;
   }

   public NoteContentIntentArgs setPfd(ParcelFileDescriptor pfd) {
      this.pfd = pfd;
      return this;
   }
}
