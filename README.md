[![bintray](https://img.shields.io/badge/bintray-material-brightgreen.svg)](https://bintray.com/hendraanggrian/material)
[![download](https://api.bintray.com/packages/hendraanggrian/material/bannerbar/images/download.svg)](https://bintray.com/hendraanggrian/material/bannerbar/_latestVersion)
[![build](https://travis-ci.com/hendraanggrian/bannerbar.svg)](https://travis-ci.com/hendraanggrian/bannerbar)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

Bannerbar
=========
![demo][demo]

Extended version of Snackbar with images and maximum height.
It is useful to display an error or empty state that requires full attention in a sense that the app workflow should not continue without them being resolved.

Download
--------
This library relies heavily on private resources and internal classes from [Android's support design library][design].
Which is why the versioning is tied to Android support library versioning.

```gradle
repositories {
    google()
    jcenter()
}

dependencies {
    implementation "com.google.android.material:material:$version"
    implementation "com.hendraanggrian.material:bannerbar:$version"
    implementation "com.hendraanggrian.material:bannerbar-ktx:$version" // optional Kotlin extensions
}
```

Usage
-----
Bannerbar is everything a Snackbar is, with some modifications:
 * Bannerbar stretch its height to match its parent size, unlike Snackbar's wrapping height.
 * Bannerbar has default current app theme's background color, unlike Snackbar's dark background.
 * In addition to Snackbar's properties, Bannerbar supports background and icon.

#### Programatically
Create Bannerbar just like a Snackbar.

```kotlin
Bannerbar.make(parent, "No internet connection", Bannerbar.LENGTH_INDEFINITE)
    .setImage(R.drawable.my_image)
    .setAction("Retry") { v ->
        // do something
    }
    .show()
```

With `bannerbar-ktx`, this process if simplified.

```kotlin
parent.indefiniteBannerbar("No internet connection", "Retry") {
    // do something
}
```

#### Styling
Customize Bannerbar default text appearance, logo, etc. with styling.

```xml
<resources>
    <style name="MyAppTheme" parent="Theme.MaterialComponents.Light.NoActionBar">
        <item name="bannerbarStyle">@style/MyBannerbarStyle</item>
    </style>

    <style name="MyBannerbarStyle" parent="Widget.Bannerbar">
        <item name="android:textSize">24sp</item>
        <item name="actionTextColor">@color/blue</item>
    </style>
</resources>
```

See [attrs.xml][attrs] for complete list of attributes.

#### Limitation
Since Bannerbar borrows Snackbar's codebase, Android will treat it as another Snackbar.
It would mean that a parent cannot have more than one Snackbar or Bannerbar at the same time.
When a Snackbar appear, an attached Bannerbar will disappear, and vice-versa.

License
-------
    Copyright 2017 Hendra Anggrian

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[demo]: /art/demo.gif
[attrs]: /bannerbar/res/values/attrs.xml
[design]: https://github.com/android/platform_frameworks_support/tree/master/design
