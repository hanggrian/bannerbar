Errorbar
========
Errorbar is an extended version of Snackbar with logo and maximum height.
It is useful to display an error or empty state that requires full attention in a sense that the app workflow should not continue without them being resolved.

![demo][demo]

Usage
-----
Errorbar is everything a Snackbar is, with some modifications:
 * Errorbar stretch its height to match its parent size, unlike Snackbar's wrapping height.
 * Errorbar has default current app theme's background color, unlike Snackbar's dark background.
 * In addition to Snackbar's properties, Errorbar has backdrop as background replacement and logo.
 
#### Programatically
Create `Errorbar` just like a `Snackbar`.
```kotlin
Errorbar.make(parent, "No internet connection", ErrorView.LENGTH_INDEFINITE)
    .setLogoDrawable(R.drawable.errorbar_ic_cloud)
    .setAction("Retry", View.OnClickListener { v -> 
        // do something
    })
    .show()
```

#### XML
It can also be inflated in xml, if that's your thing.
At this point duration property is ignored since `show()` is not called.
```xml
<android.support.design.ErrorbarLayout
    android:id="@+id/errorbar"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
```

#### Limitation
Since Errorbar borrows Snackbar's codebase, Android will treat it as another Snackbar.
It would mean that a View cannot have more than one Snackbar or Errorbar at the same time.
When a Snackbar appear, an attached Errorbar will disappear, and vice-versa.

Download
--------
This library relies heavily on private resources and internal classes from [Android's support design library][design_repo].
It is only tested with support design library version as listed below with no intention of supporting older versions.
```gradle
repositories {
    maven { url 'https://maven.google.com' }
    jcenter()
}

dependencies {
    compile 'com.android.support:design:26.0.0'
    compile 'com.hendraanggrian:errorbar:0.1.0'
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
 
[demo]: /art/demo.gif
[design_intro]: https://developer.android.com/training/material/design-library.html
[design_repo]: https://github.com/android/platform_frameworks_support/tree/master/design