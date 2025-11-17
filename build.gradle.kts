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

extra["artifactId"] = "brambolt-gradle-build-staging"
extra["developers"] = listOf(
  mapOf(
    "email" to "stefan.sigurdsson@brambolt.com",
    "id" to "stefan.sigurdsson@brambolt.com",
    "name" to "Stefán Sigurðsson"
  )
)
extra["inceptionYear"] = "2017"
extra["isGitHosted"] = true
extra["isOpenSource"] = true // Enables publishing to plugins.gradle.org and Bintray
extra["licenses"] = listOf(
  mapOf(
    "id" to "Apache-2.0",
    "name" to "The Apache Software License, Version 2.0",
    "url" to "http://www.apache.org/licenses/LICENSE-2.0.txt"
  )
)
extra["pluginClass"] = "com.brambolt.gradle.StagingBuildPlugin"
extra["pluginDisplayName"] = "Brambolt Staging Build"
extra["pluginId"] = "com.brambolt.gradle.build.staging"
extra["pluginTags"] = listOf("build")
extra["pluginWebsite"] = "https://github.com/brambolt/gradle-build-staging"
extra["release"] = bramboltRelease
extra["vcsUrl"] = "https://github.com/brambolt/gradle-build-staging"

apply(plugin = "com.brambolt.gradle.build.plugin")

dependencies {
  implementation("com.brambolt:brambolt-rt:$bramboltVersion")
  implementation("com.brambolt.gradle:brambolt-gradle-build-archive:$bramboltVersion")
  implementation("com.brambolt.gradle:brambolt-gradle-build-plugin:$bramboltVersion")
  implementation("com.brambolt.gradle:brambolt-gradle-velocity:$bramboltVersion")
  implementation("com.brambolt.gradle:brambolt-gradle-staging:$bramboltVersion")
}

tasks.test {
  testLogging {
    outputs.upToDateWhen { false }
    showStandardStreams = true
  }
}

tasks.test.get().finalizedBy(":samples:runAll")
