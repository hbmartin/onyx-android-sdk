package com.onyx.android.sdk.utils;

import java.lang.ref.Reference;
import org.greenrobot.eventbus.EventBus;

public class EventBusUtils {
   public static void ensureRegister(EventBusHolder eventBusHolder, Object subscriber) {
      if (eventBusHolder != null) {
         ensureRegister(eventBusHolder.getEventBus(), subscriber);
      }
   }

   public static void ensureRegister(EventBus eventBus, Object subscriber) {
      if (eventBus != null && subscriber != null) {
         if (!eventBus.isRegistered(subscriber)) {
            eventBus.register(subscriber);
         }
      }
   }

   public static void ensureUnregister(EventBusHolder eventBusHolder, Object subscriber) {
      if (eventBusHolder != null) {
         ensureUnregister(eventBusHolder.getEventBus(), subscriber);
      }
   }

   public static void ensureUnregister(EventBus eventBus, Object subscriber) {
      if (eventBus != null && subscriber != null) {
         if (eventBus.isRegistered(subscriber)) {
            eventBus.unregister(subscriber);
         }
      }
   }

   public static void safelyPostEvent(Object event) {
      safelyPostEvent(EventBus.getDefault(), event);
   }

   public static void safelyPostEvent(EventBus eventBus, Object event) {
      if (eventBus != null && event != null) {
         eventBus.post(event);
      }
   }

   public static void safelyPostEvent(Reference<EventBus> reference, Object event) {
      if (reference != null) {
         safelyPostEvent(reference.get(), event);
      }
   }
}
