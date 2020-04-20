const val VERSION_PREFY = "0.2"

fun Dependencies.hendraanggrian(repo: String, version: String) = hendraanggrian(repo, repo, version)

fun Dependencies.hendraanggrian(repo: String, module: String, version: String) =
    "com.hendraanggrian.$repo:$module:$version"