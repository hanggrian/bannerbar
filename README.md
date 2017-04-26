ErrorView
=========
Informative UI to display as error and empty state.

![demo](/art/ss1.jpg) ![demo](/art/ss2.jpg)

Download
--------
```gradle
dependencies {
    compile 'com.hendraanggrian:errorview:0.1.0'
}
```

Usage
-----
Set it up on xml.
```xml
<com.hendraanggrian.widget.ErrorView
    android:id="@+id/errorview"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:errorLogo="@drawable/ic_errorview_cloud"
    app:errorText="No internet connection"/>
```

To have button, call `setAction()` in java.
```xml
ErrorView errorView = (ErrorView) findById(R.id.errorview);
errorView.setAction("Retry", new View.OnClickListener() {
    @Override
    public void onClick() {
        // do something
    }
});
```