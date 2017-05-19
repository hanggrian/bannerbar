ErrorView
=========
Informative UI to display as error and empty state.

![demo1][demo1] ![demo2][demo2]

Usage
-----
#### Programatically
Create `ErrorView` like a `Toast` or `Snackbar`.
```java
ErrorView.make(parent, "No internet connection", ErrorView.LENGTH_INDEFINITE)
    .setLogo(R.drawable.ic_launcher)
    .setAction("Retry", new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // do something
        }
    })
    .show();
```

#### XML
It can also be inflated in xml, if that's your thing.
```xml
<com.hendraanggrian.widget.ErrorView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:errorText="No internet connection"/>
```

#### Attributes
| Attribute                                                                                  | Description                                          | Default value/behavior            |
|--------------------------------------------------------------------------------------------|------------------------------------------------------|-----------------------------------|
| `errorBackdrop`                                                                            | center-cropping image that behaves like background   | Clouds background                 |
| `errorLogo`                                                                                | smaller image above the message, set null to disable | Sad cloud logo                    |
| `errorText`                                                                                | error message                                        | disabled                          |
| `errorTextAppearance`                                                                      | message text style                                   | `TextAppearance_AppCompat_Medium` |
| `contentMarginLeft`<br>`contentMarginTop`<br>`contentMarginRight`<br>`contentMarginBottom` | positioning of logo and message                      | center                            |

Download
--------
```gradle
dependencies {
    compile 'com.hendraanggrian:errorview:0.5.3'
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
    
[demo1]: /art/ss1.jpg
[demo2]: /art/ss2.jpg