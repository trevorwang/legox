package legox.plugin

object Versions {
    const val kotlin = "1.4.10"

    // Android X
    const val core_ktx = "1.3.2"
    const val appcompat = "1.2.0"
    const val constraint_layout = "2.0.4"
    const val design = "1.2.1"
    const val navigation = "2.3.2"

    // 3rd parties
    const val timber = "4.7.1"
}

object AndroidX {
    const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val design = "com.google.android.material:material:${Versions.design}"
    const val constrain_layout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"
    const val nav_fragment_ktx =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val nav_ui_ktx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"

}

object Kt {
    const val std = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
}

object Libs {
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
}

object Test {
    const val junit = "junit:junit:4.13.1"
    const val test_ext = "androidx.test.ext:junit:1.1.2"
    const val espresso = "androidx.test.espresso:espresso-core:3.3.0"
}