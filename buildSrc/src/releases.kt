const val SDK_MIN = 14
const val SDK_TARGET = 30

const val RELEASE_GROUP = "com.hendraanggrian.material"
const val RELEASE_ARTIFACT = "bannerbar"
const val RELEASE_VERSION = "$VERSION_ANDROIDX-SNAPSHOT"
const val RELEASE_DESCRIPTION = "Slightly larger Snackbar with multiple actions"
const val RELEASE_GITHUB = "https://github.com/hendraanggrian/$RELEASE_ARTIFACT"

fun getGithubRemoteUrl(artifact: String = RELEASE_ARTIFACT) =
    `java.net`.URL("$RELEASE_GITHUB/tree/main/$artifact/src")