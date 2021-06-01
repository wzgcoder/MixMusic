dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        jcenter()
    }
}
rootProject.name = "MixMusic"
include(":app")
include(":common")
