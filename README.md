# BigJoke

This project is a simple joke telling app with multiple flavors that uses multiple libraries and Google Cloud Endpoints. The app has been build as part of the Udacity Android Developer Nanodegree.

The app consists of four modules. A Java library that provides jokes, a Google Cloud Endpoints (GCE) project that serves those jokes, an Android Library containing an activity for displaying jokes, and an Android app that fetches jokes from the GCE module and passes them to the Android Library for display.   The app as two product flavors, a paid and a free variant. In the free flavor, prior to the joke, an AdMob interstitial is shown as an example how to monetize an app.

### Features

1. Java library providing joke(s)
2. Google Cloud Endpoints (GCE) project serving the joke
3. Android library displaying the joke
4. Paid version without ads
5. Free version with AdMob integration showing ads between jokes

### Showcase

<img src=„/screenshots/BigJoke_prev.gif“>

<img src="/screenshots/1.png“>
<img src="/screenshots/2.png“>
<img src="/screenshots/3.png“>

## Getting started / Installation

1. Download the zip-file with the code and import it into Android Studio OR clone the code with Android Studio
2. To later see AdMob ads, ad your AdMob App Id in strings.xml
3. Start the GCE with the gradle command appengineStart  <img src=„/screenshots/gradle_appengine_start.png“>
4. Test the GCE by typing localhost:8080 into your browser. You should see the following in the browser tab.  <img src=„/screenshots/cloud_endpoint_local.png“>
5. In the Build Variante Tab choose the paid or the free build variant
6. Start the Android emulator

## License

This app is Copyright © 2020 madfree. It is free software, and may be redistributed under the terms specified in the [LICENSE](/LICENSE) file.