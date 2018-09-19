LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := MyGame_shared
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_SRC_FILES := \
	/Users/xj/code/Android/proj.android/helloWorldDemo/src/main/jni/Android.mk \
	/Users/xj/code/Android/proj.android/helloWorldDemo/src/main/jni/Application.mk \
	/Users/xj/code/Android/proj.android/helloWorldDemo/src/main/jni/hellocpp/AppDelegate.cpp \
	/Users/xj/code/Android/proj.android/helloWorldDemo/src/main/jni/hellocpp/HelloWorldScene.cpp \
	/Users/xj/code/Android/proj.android/helloWorldDemo/src/main/jni/hellocpp/main.cpp \

LOCAL_C_INCLUDES += /Users/xj/code/Android/proj.android/helloWorldDemo/src/main/jni
LOCAL_C_INCLUDES += /Users/xj/code/Android/proj.android/helloWorldDemo/src/debug/jni

include $(BUILD_SHARED_LIBRARY)
