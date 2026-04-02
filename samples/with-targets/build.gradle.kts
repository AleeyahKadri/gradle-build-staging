import org.gradle.kotlin.dsl.withGroovyBuilder

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
  id("com.brambolt.gradle.build.staging") apply false
}

description = "A simple sample for the Brambolt staging build plugin."
group = "com.brambolt.gradle.samples"

extra.apply {
  set("artifactClassifier", "with-targets")
  set("artifactId", "brambolt-gradle-build-staging-sample")
  set(
    "developers",
    listOf(
      mapOf(
        "email" to "stefan.sigurdsson@brambolt.com",
        "id" to "stefan.sigurdsson@brambolt.com",
        "name" to "Stefán Sigurðsson"
      )
    )
  )
  set("inceptionYear", "2017")
  set("isOpenSource", false)
  set(
    "licenses",
    listOf(
      mapOf(
        "id" to "Apache-2.0",
        "name" to "The Apache Software License, Version 2.0",
        "url" to "http://www.apache.org/licenses/LICENSE-2.0.txt"
      )
    )
  )
  set("release", property("bramboltRelease"))
  set("vcsUrl", "https://github.com/brambolt/gradle-build-staging")
}

apply(plugin = "com.brambolt.gradle.build.staging")

project.withGroovyBuilder {
  "generateProperties" {
    setProperty("prepend", false)
  }
}

project.withGroovyBuilder {
  "velocity" {
    "context"(mapOf("timestamp" to java.util.Date().toString()))
    setProperty("strict", true)
  }
}

apply(plugin = "com.jfrog.artifactory")

project.withGroovyBuilder {
  "artifactory" {
    setProperty("contextUrl", property("artifactoryContextUrl"))
    "publish" {
      "repository" {
        setProperty("repoKey", property("artifactoryRepoKey"))
        setProperty("username", property("artifactoryUser"))
        setProperty("password", property("artifactoryToken"))
        setProperty("maven", true)
      }
      "defaults" {
        "publications"("mavenCustom")
        setProperty("publishArtifacts", true)
        setProperty("publishPom", true)
      }
    }
    "resolve" {
      "repository" {
        setProperty("repoKey", property("artifactoryRepoKey"))
        setProperty("username", property("artifactoryUser"))
        setProperty("password", property("artifactoryToken"))
        setProperty("maven", true)
      }
    }
  }
}

tasks.named("all") {
  dependsOn("artifactoryPublish")
}
