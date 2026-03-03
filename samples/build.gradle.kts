
tasks.register<Exec>("runSimple") {
  workingDir = File(project.projectDir, "simple")
  commandLine("../../gradlew", "all", "--info", "--stacktrace", "--no-daemon")
  doFirst {
    File(workingDir, "build").delete()
  }
}

tasks.register<Exec>("runWithDefaults") {
  workingDir = File(project.projectDir, "with-defaults")
  commandLine("../../gradlew", "all", "--info", "--stacktrace", "--no-daemon")
  doFirst {
    File(workingDir, "build").delete()
  }
}

tasks.register<Exec>("runWithTargets") {
  workingDir = File(project.projectDir, "with-targets")
  commandLine("../../gradlew", "all", "--info", "--stacktrace", "--no-daemon")
}

tasks.register("runAll") {
  dependsOn("runSimple")
  dependsOn("runWithDefaults")
  // dependsOn("runWithTargets")
}
