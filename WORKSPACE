load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

http_archive(
    name = "rules_jvm_external",
    sha256 = "62133c125bf4109dfd9d2af64830208356ce4ef8b165a6ef15bbff7460b35c3a",
    strip_prefix = "rules_jvm_external-3.0",
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/3.0.zip",
)

load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_install(
    artifacts = [
        "com.google.auto.service:auto-service:1.0-rc4",
        "com.google.errorprone:error_prone_check_api:2.3.4",
        "com.google.errorprone:error_prone_annotation:2.3.4",
        "com.google.errorprone:error_prone_test_helpers:2.3.4",
        "com.google.errorprone:javac:9+181-r4173-1",
        "org.projectlombok:lombok:1.18.10",
    ],
    repositories = [
        "https://jcenter.bintray.com/",
        "https://maven.google.com",
        "https://repo1.maven.org/maven2",
    ],
)
