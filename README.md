#Revolt Android SDK
Usage of the Revolt SDK library, to provide event tracking.

#Getting started
##Dependency
In build.gradle of your Android application module (usually $projectRoot/app/build.gradle) add the following in the dependencies section:
```
dependencies   {
    implementation 'rocks.revolt:revolt-sdk-android:1.0.1'
}
```

##Initialization
Revolt SDK can be created using builder from Revolt class. It is necessary to provide context, trackingID and secretKey and then build to have instance of Revolt SDK. This initialization must be placed in the onCreate method inside Application class instance:

```
Revolt.builder()
       .with(context)
       .trackingId("trackingId")
       .secretKey("secretKey")
        .build()
```
        
##Permissions
Revolt Android SDK needs the following permissions to communicate with the server and to detect network connectivity (they are added in library manifest):

```
 <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
 <uses-permission android:name="android.permission.INTERNET" />
```

Note that you don't have to add these permissions to manifest of your application.

#Send events

##Sending events

Events can be send with sendEvent method which is asynchronous. Method is expecting Event interface which contains getJson method returning JsonObject and getType method returning type of the event.
RevoltEvent class is described below and provides most common usage samples. 
Example:

```
revoltSDK.sendEvent(RevoltEvent("Type", "a", "b"))
```

##Creating events
RevoltEvent class provides 4 constructors that allows you to create events.

###Using JsonObject
Example:
```
val jsonObject = JsonObject();
jsonObject.addProperty(“key”, “value”);
val revoltEvent = RevoltEvent(“type”, jsonObject)
```
###Using key - value
Example:
```
val revoltEvent = RevoltEvent("type", "a", "b")
```
###Using map
Example:
```
val properties: MutableMap<String, Any> = HashMap()
properties.put(“aaa”, “bbb”);
val revoltEvent = RevoltEvent("type", properties)
```
###Using pairs array
Example:
```
val revoltEvent = RevoltEvent("type", "aaa" to “bbb”, “ccc” to “ddd”)
```

#Lifecycle events

Revolt allows to handle lifecycle events connected with activity changes such as: create, resume, pause, stop and onSaveInstanceState. To enable it, use RevoltLifecycleEventsManager, which is expecting Revolt class implementation and context.

Example:
```
Revolt.builder()
       .with(context)
       .trackingId("trackingId")
       .secretKey("secretKey")
       .build()
       
registerActivityLifecycleCallbacks(RevoltActivityLifecycleCallbacks(rev))       
```

#Logging

You can specify which level of logs from SDK do you prefer to print in Logcat. You can do it during initialization. The default log level is WARN. You can turn off logs by setting SILENT log level.

#Custom Parameters

There are few parameters which can be set up during initialization:

* maxBatchSize - the max size of events batch in one request
* eventDelayMillis - delay in sending event
* offlineMaxSize - the max size of events stored in the database during offline mode
* endpoint - address of API endpoint
* revoltLogLevel - log level
* firstRetryTimeSeconds - first time interval in seconds to retry sending the event when any error occurs
* maxRetryTimeSeconds- last time interval in seconds to retry sending the event when any error occurs



