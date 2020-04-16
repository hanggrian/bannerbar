[![bintray](https://img.shields.io/badge/bintray-material-brightgreen.svg)](https://bintray.com/hendraanggrian/material)
[![download](https://api.bintray.com/packages/hendraanggrian/material/bannerbar/images/download.svg)](https://bintray.com/hendraanggrian/material/bannerbar/_latestVersion)
[![build](https://travis-ci.com/hendraanggrian/bannerbar.svg)](https://travis-ci.com/hendraanggrian/bannerbar)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

Bannerbar
=========
![example_light][example_light]
![example_dark][example_dark]

Bannerbar is essentially a [material banner](https://material.io/components/banners) displayed like a snackbar.
Imagine it as a dialog that doesn't interrupt the whole Activity.
* Following material guidelines, it can only have up to **2 actions**.
* Title are always present, icon and subtitle are optional.
* Shares Snackbar styles and abilities: respects floating action button position, dismissible by a swipe, etc. 

### Caveats
Since it uses a lot of Snackbar resources and API, there are a few: 
* Only safe to use with the same version of material components.
* Deceptive package name.

Download
--------
This library follows [AndroidX's revisions][androidx-rn].

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
Bannerbar usage is similar to Snackbar.

```kotlin
Bannerbar.make(parent, "No internet connection", Bannerbar.LENGTH_INDEFINITE)
    .setIcon(R.drawable.my_image)
    .addAction("Retry") { }
    .show()
```

With `bannerbar-ktx`, this process can be quicker.

```kotlin
parent.bannerbar("No internet connection") {
    setIcon(R.drawable.my_image)
    addAction("Retry") { }
}
```

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

[example_light]: /art/example_light.png
[example_dark]: /art/example_dark.png
[androidx-rn]: https://developer.android.com/topic/libraries/support-library/androidx-rn
rning permissions and
    limitations under the License.

[example_light]: /art/example_light.png
[example_dark]: /art/example_dark.png
[androidx-rn]: https://developer.android.com/topic/libraries/support-library/androidx-rn
