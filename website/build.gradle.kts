plugins {
    `git-publish`
}

gitPublish {
    repoUri.set(RELEASE_WEBSITE)
    branch.set("gh-pages")
    contents.from("../$RELEASE_ARTIFACT-ktx/build/docs")
}

tasks["gitPublishCopy"].dependsOn(":$RELEASE_ARTIFACT-ktx:dokka")