plugins {
    `git-publish`
}

gitPublish {
    repoUri = RELEASE_WEBSITE
    branch = "gh-pages"
    contents.from("../$RELEASE_ARTIFACT-ktx/build/docs")
}

tasks["gitPublishCopy"].dependsOn(":$RELEASE_ARTIFACT-ktx:dokka")