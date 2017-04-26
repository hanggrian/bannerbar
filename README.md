ErrorView
=========
Informative UI to display as error and empty state.

![demo](/art/ss1.jpg) ![demo](/art/ss2.jpg)

Download
--------
```gradle
dependencies {
    compile 'com.hendraanggrian:errorview:0.2.0'
}
```

Usage
-----
Create ErrorView like a Toast or Snackbar.
```java
ErrorView.create(parent, "No internet connection", ErrorView.LENGTH_INDEFINITE)
    .setLogo(R.drawable.ic_errorview_cloud)
    .setAction("Retry", new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // do something
        }
    })
    .show();
```

It can also be inflated in xml, if that's your thing.
```xml
<com.hendraanggrian.widget.ErrorView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:errorLogo="@drawable/ic_errorview_cloud"
    app:errorText="No internet connection"/>
```