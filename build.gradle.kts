// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    alias(libs.plugins.android.application) apply false // Correct usage of 'apply false'
    id("com.google.gms.google-services") version "4.4.2" apply false // Use apply false to avoid applying it at the root level
}
