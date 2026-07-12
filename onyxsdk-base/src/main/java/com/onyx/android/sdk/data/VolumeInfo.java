package com.onyx.android.sdk.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.io.Serializable;

public class VolumeInfo implements Serializable, Parcelable {
   public static final Creator<VolumeInfo> CREATOR = new Creator<VolumeInfo>() {
      public VolumeInfo createFromParcel(Parcel in) {
         return new VolumeInfo(in);
      }

      public VolumeInfo[] newArray(int size) {
         return new VolumeInfo[size];
      }
   };
   public String path;
   public int type;
   public int state;

   public VolumeInfo() {
   }

   public VolumeInfo(Parcel in) {
      this.readFromParcel(in);
   }

   public VolumeInfo setPath(String path) {
      this.path = path;
      return this;
   }

   public VolumeInfo setState(int state) {
      this.state = state;
      return this;
   }

   public VolumeInfo setType(int type) {
      this.type = type;
      return this;
   }

   public void readFromParcel(Parcel in) {
      this.path = in.readString();
      this.type = in.readInt();
      this.state = in.readInt();
   }

   public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(this.path);
      dest.writeInt(this.type);
      dest.writeInt(this.state);
   }

   public int describeContents() {
      return 0;
   }

   @Override
   public String toString() {
      return "VolumeInfo{path='" + this.path + '\'' + ", type=" + this.type + ", state=" + this.state + '}';
   }
}
