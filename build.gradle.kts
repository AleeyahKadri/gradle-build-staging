/*
 * Copyright 2017-2020 Brambolt ehf.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
  id("com.brambolt.gradle.build.plugin") apply false
}

description = "The Brambolt staging build."
group = "com.brambolt.gradle"

val bramboltVersion: String by project
val bramboltRelease: String by project

extra.apply {
  set("artifactId", "brambolt-gradle-build-staging")
  set("developers", listOf(mapOf(
    "email" to "stefan.sigurdsson@brambolt.com",
    "id" to "stefan.sigurdsson@brambolt.com",
    "name" to "Stefán Sigurðsson"
  )))
  set("inceptionYear", "2017")
  set("isGitHosted", true)
  set("isOpenSource", true) // Enables publishing to plugins.gradle.org and Bintray
  set("licenses", listOf(mapOf(
    "id" to "Apache-2.0",
    "name" to "The Apache Software License, Version 2.0",
    "url" to "http://www.apache.org/licenses/LICENSE-2.0.txt"
  )))
  set("pluginClass", "com.brambolt.gradle.StagingBuildPlugin")
  set("pluginDisplayName", "Brambolt Staging Build")
  set("pluginId", "com.brambolt.gradle.build.staging")
  set("pluginTags", listOf("build"))
  set("pluginWebsite", "https://github.com/brambolt/gradle-build-staging")
  set("release", bramboltRelease)
  set("vcsUrl", "https://github.com/brambolt/gradle-build-staging")
}

apply(plugin = "com.brambolt.gradle.build.plugin")

dependencies {
  implementation("com.brambolt:brambolt-rt:${bramboltVersion}")
  implementation("com.brambolt.gradle:brambolt-gradle-build-archive:${bramboltVersion}")
  implementation("com.brambolt.gradle:brambolt-gradle-build-plugin:${bramboltVersion}")
  implementation("com.brambolt.gradle:brambolt-gradle-velocity:${bramboltVersion}")
  implementation("com.brambolt.gradle:brambolt-gradle-staging:${bramboltVersion}")
}

tasks.named<Test>("test") {
  testLogging {
    outputs.upToDateWhen { false }
    showStandardStreams = true
  }
}

tasks.named("test") {
  finalizedBy(":samples:runAll")
}
