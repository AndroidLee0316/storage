# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
####################  公共部分  ################################
-optimizationpasses 5
-useuniqueclassmembernames
-keepattributes SourceFile,LineNumberTable
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-dontoptimize
-verbose
-ignorewarnings
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#关闭压缩  默认开启，用以减小应用体积，移除未被使用的类和成员，
#并且会在优化动作执行之后再次执行（因为优化后可能会再次暴露一些未被使用的类和成员）。
-dontshrink

#apk 包内所有 class 的内部结构
-dump class_files.txt
#未混淆的类和成员
-printseeds seeds.txt
#列出从 apk 中删除的代码
-printusage unused.txt
#混淆前后的映射
-printmapping mapping.txt

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.v4.**{*;}
-keep class android.support.design.**{*;}
-keep interface android.support.v4.**{*;}
-dontwarn android.support.**
-keep class cn.jiajixin.nuwa.**{*;}
-keep class pasc.citizencard.config.**{*;}

-keepclasseswithmembernames class * {
    native <methods>;
}


-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclasseswithmembers class * {
    void onEvent*(...);
}

-keepnames class * implements java.io.Serializable

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

-renamesourcefileattribute SourceFile
-keepattributes *JavascriptInterface*


-keep class * extends android.preference.Preference
-keep class * extends android.os.Bundle
-dontwarn com.google.android.support.v4.**
-keep class com.google.android.support.v4.** { *; }
-keep interface com.google.android.support.v4.app.** { *; }
-keep public class * extends com.google.android.support.v4.**
-keep public class * extends com.google.android.support.v4.app.Fragment
-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v4.app.Fragment

#gson
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.idea.fifaalarmclock.entity.***
-keep class com.google.gson.stream.** { *; }
-keepattributes Signature

-keep class org.apache.http.** {*;}
-dontwarn org.apache.http.**

#js & webview
-keepclassmembers class * extends android.webkit.WebChromeClient{
   	public void openFileChooser(...);
   	public void onShowFileChooser(...);
}

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}

-keepattributes *Annotation*
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

#------------------  下方是android平台自带的排除项，这里不要动         ----------------

-keep public class * extends android.app.Activity{
	public <fields>;
	public <methods>;
}
-keep public class * extends android.app.Application{
	public <fields>;
	public <methods>;
}
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclasseswithmembers class * {
	public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
	public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepattributes *Annotation*

-keepclasseswithmembernames class *{
	native <methods>;
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}


-keepclasseswithmembers class * {
    ... *JNI*(...);
}

-keepclasseswithmembernames class * {
	... *JRI*(...);
}

-keep class **JNI* {*;}

####################  公共部分  ################################
#gson
-keep class com.google.gson.**{*;}
# dbflow
-keep class * extends com.raizlabs.android.dbflow.config.DatabaseHolder { *; }
-keep class * extends com.raizlabs.android.dbflow.structure.BaseModel { *; }
# 保持入口类不被混淆
-keep class com.pasc.lib.storage.fileDiskCache.FileCacheUtils{*;}
-keep class com.pasc.lib.storage.fileDiskCache.FileCacheBuilder{*;}
-keep class com.pasc.lib.storage.fileDiskCache.FileCacheUtils$*{*;}
-keep class com.pasc.lib.storage.fileDiskCache.FileCacheBuilder$*{*;}
-keep class com.pasc.lib.storage.database.**{*;}

# 保持泛型
-keepattributes Signature
# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
#数据库加密
-keep class net.sqlcipher.**{*;}
