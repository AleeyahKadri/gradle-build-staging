import org.gradle.api.tasks.Exec

val runSimple by tasks.registering(Exec::class) {
  workingDir = file("simple")
  commandLine("../../gradlew", "all", "--info", "--stacktrace", "--no-daemon")
  doFirst {
    file("$workingDir/build").delete()
  }
}

val runWithDefaults by tasks.registering(Exec::class) {
  workingDir = file("with-defaults")
  commandLine("../../gradlew", "all", "--info", "--stacktrace", "--no-daemon")
  doFirst {
    file("$workingDir/build").delete()
  }
}

val runWithTargets by tasks.registering(Exec::class) {
  workingDir = file("with-targets")
  commandLine("../../gradlew", "all", "--info", "--stacktrace", "--no-daemon")
}

tasks.register("runAll") {
  dependsOn(runSimple)
  dependsOn(runWithDefaults)
  // dependsOn(runWithTargets)
}
