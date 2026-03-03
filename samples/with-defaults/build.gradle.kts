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

val bramboltRelease: String by project

extra.apply {
  set("artifactClassifier", "with-defaults")
  set("artifactId", "brambolt-gradle-build-staging-sample")
  set("developers", listOf(mapOf(
    "email" to "stefan.sigurdsson@brambolt.com",
    "id" to "stefan.sigurdsson@brambolt.com",
    "name" to "Stefán Sigurðsson"
  )))
  set("inceptionYear", "2017")
  set("isOpenSource", false) // Disabled publishing the sample to plugins.gradle.org or Bintray
  set("licenses", listOf(mapOf(
    "id" to "Apache-2.0",
    "name" to "The Apache Software License, Version 2.0",
    "url" to "http://www.apache.org/licenses/LICENSE-2.0.txt"
  )))
  set("release", bramboltRelease)
  set("vcsUrl", "https://github.com/brambolt/gradle-build-staging")
}

apply(plugin = "com.brambolt.gradle.build.staging")

(extensions.getByName("generateProperties") as org.gradle.api.plugins.ExtensionAware).extra["prepend"] = false

(extensions.getByName("velocity") as groovy.lang.GroovyObject).invokeMethod("context", mapOf(
  "dog1" to "Noah",
  "dog2" to "Phoebe"
))

(extensions.getByName("staging") as groovy.lang.GroovyObject).invokeMethod("targets", mapOf(
  "t1" to mapOf("name" to "t1"),
  "t2" to mapOf("name" to "t2")
))

apply(plugin = "com.jfrog.artifactory")

configure<org.jfrog.gradle.plugin.artifactory.dsl.ArtifactoryPluginConvention> {
  setContextUrl(project.property("artifactoryContextUrl"))
  publish {
    repository {
      setRepoKey(project.property("artifactoryRepoKey") as String)
      setUsername(project.property("artifactoryUser") as String)
      setPassword(project.property("artifactoryToken") as String)
      setMaven(true)
    }
    defaults {
      publications("mavenCustom")
      setPublishArtifacts(true)
      setPublishPom(true)
    }
  }
  resolve {
    repository {
      setRepoKey(project.property("artifactoryRepoKey") as String)
      setUsername(project.property("artifactoryUser") as String)
      setPassword(project.property("artifactoryToken") as String)
      setMaven(true)
    }
  }
}

tasks.named("all") {
  dependsOn("artifactoryPublish")
}
