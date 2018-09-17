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
# Platform calls Class.forName on types which do not exist on Android to determine platform.

##### RETROFIT #####
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions


-keep class rocks.revolt.Event {*;}
-keep class rocks.revolt.Revolt {*;}
-keep class rocks.revolt.Revolt$** {*;}
-keep class rocks.revolt.RevoltActivityLifecycleCallbacks {*;}
-keep class rocks.revolt.RevoltLogger {*;}
-keep class rocks.revolt.RevoltLogLevel {*;}
-keep class rocks.revolt.UIActivityEvents {*;}
-keep class rocks.revolt.UserEvents {*;}
-keep class rocks.revolt.RevoltEvent {*;}
-keep class rocks.revolt.internal.EventModel {*;}
-keep class rocks.revolt.internal.MetaDataModel {*;}
-keep class rocks.revolt.internal.network.SendEventResponse {*;}
-keepattributes InnerClasses


-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase


-dontwarn com.google.errorprone.annotations.**
-keep class com.google.errorprone.annotations.** { *; }
