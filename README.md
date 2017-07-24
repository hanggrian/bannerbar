Errorbar
========
Larger Snackbar to display error and empty state.
Using [Android support design's][design] internal components, Errorbar should be as fluid and stable as Snackbar.

![demo][demo]

Usage
-----
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
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:errorText="No internet connection"/>
```

See [attrs.xml][attrs] for full list of available attributes.

#### Limitation
Since Errorbar borrows Snackbar's codebase, Android will treat it as another Snackbar.
It would mean that a View cannot have more than one Snackbar or Errorbar at the same time.
When a Snackbar appear, an attached Errorbar will disappear, and vice-versa.

Download
--------
```gradle
repositories {
    maven { url 'https://maven.google.com' }
    jcenter()
}

dependencies {
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
 
[design]: https://github.com/android/platform_frameworks_support/tree/master/design
[attrs]: https://github.com/HendraAnggrian/errorbar/blob/master/errorbar/res/values/attrs.xml
[demo]: /art/demo.gif