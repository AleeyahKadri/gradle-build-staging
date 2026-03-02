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

extra["artifactClassifier"] = "simple"
extra["artifactId"] = "brambolt-gradle-build-staging-sample"
extra["developers"] = listOf(
  mapOf(
    "email" to "stefan.sigurdsson@brambolt.com",
    "id" to "stefan.sigurdsson@brambolt.com",
    "name" to "Stefán Sigurðsson"
  )
)
extra["inceptionYear"] = "2017"
extra["isOpenSource"] = false // Disabled publishing the sample to plugins.gradle.org or Bintray
extra["licenses"] = listOf(
  mapOf(
    "id" to "Apache-2.0",
    "name" to "The Apache Software License, Version 2.0",
    "url" to "http://www.apache.org/licenses/LICENSE-2.0.txt"
  )
)
extra["release"] = bramboltRelease
extra["vcsUrl"] = "https://github.com/brambolt/gradle-build-staging"

apply(plugin = "com.brambolt.gradle.build.staging")

configure<com.brambolt.gradle.api.GeneratePropertiesPluginExtension> {
  prepend = true
}

configure<com.brambolt.gradle.velocity.VelocityPluginExtension> {
  context(
    "dog1" to "Noah",
    "dog2" to "Phoebe"
  )
}

configure<com.brambolt.gradle.staging.StagingPluginExtension> {
  targets(
    "t1" to mapOf("name" to "t1"),
    "t2" to mapOf("name" to "t2")
  )
}

apply(plugin = "com.jfrog.artifactory")

configure<org.jfrog.gradle.plugin.artifactory.dsl.ArtifactoryPluginConvention> {
  setContextUrl(project.property("artifactoryContextUrl"))
  publish(delegateClosureOf<org.jfrog.gradle.plugin.artifactory.dsl.PublisherConfig> {
    repository(delegateClosureOf<org.jfrog.gradle.plugin.artifactory.task.ArtifactoryTask.RepositoryHandler> {
      setRepoKey(project.property("artifactoryRepoKey") as String)
      setUsername(project.property("artifactoryUser") as String)
      setPassword(project.property("artifactoryToken") as String)
      setMavenCompatible(true)
    })
    defaults(delegateClosureOf<org.jfrog.gradle.plugin.artifactory.task.ArtifactoryTask.Defaults> {
      publications("mavenCustom")
      setPublishArtifacts(true)
      setPublishPom(true)
    })
  })
  resolve(delegateClosureOf<org.jfrog.gradle.plugin.artifactory.dsl.ResolverConfig> {
    repository(delegateClosureOf<org.jfrog.gradle.plugin.artifactory.task.ArtifactoryTask.RepositoryHandler> {
      setRepoKey(project.property("artifactoryRepoKey") as String)
      setUsername(project.property("artifactoryUser") as String)
      setPassword(project.property("artifactoryToken") as String)
      setMavenCompatible(true)
    })
  })
}

tasks.named("all") {
  dependsOn("artifactoryPublish")
}
