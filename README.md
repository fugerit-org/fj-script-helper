# Fugerit Language Script Helper

This project provides just a convenient interface for evaluating scripts with java script engine.

[![Keep a Changelog v1.1.0 badge](https://img.shields.io/badge/changelog-Keep%20a%20Changelog%20v1.1.0-%23E05735)](CHANGELOG.md)
[![Maven Central](https://img.shields.io/maven-central/v/org.fugerit.java/fj-script-helper.svg)](https://central.sonatype.com/artifact/org.fugerit.java/fj-script-helper)
[![license](https://img.shields.io/badge/License-MIT%20License-teal.svg)](https://opensource.org/license/mit)
[![code of conduct](https://img.shields.io/badge/conduct-Contributor%20Covenant-purple.svg)](https://github.com/fugerit-org/fj-universe/blob/main/CODE_OF_CONDUCT.md)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=fugerit-org_fj-script-helper&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=fugerit-org_fj-script-helper)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=fugerit-org_fj-script-helper&metric=coverage)](https://sonarcloud.io/summary/new_code?id=fugerit-org_fj-script-helper)

[![Java runtime version](https://img.shields.io/badge/run%20on-java%208+-%23113366.svg?style=for-the-badge&logo=openjdk&logoColor=white)](https://universe.fugerit.org/src/docs/versions/java11.html)
[![Java build version](https://img.shields.io/badge/build%20on-java%2017+-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)](https://universe.fugerit.org/src/docs/versions/java17.html)
[![Apache Maven](https://img.shields.io/badge/Apache%20Maven-3.9.0+-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)](https://universe.fugerit.org/src/docs/versions/maven3_9.html)
[![Fugerit Github Project Conventions](https://img.shields.io/badge/Fugerit%20Org-Project%20Conventions-1A36C7?style=for-the-badge&logo=Onlinect%20Playground&logoColor=white)](https://universe.fugerit.org/src/docs/conventions/index.html)

## Quickstart

### 1. Add dependency

```xml
<dependency>
    <groupId>org.fugerit.java</groupId>
    <artifactId>fj-script-helper</artifactId>
    <version>${fj-script-helper-version}</version>
</dependency>
```

### 2. Usage

Sample code : 

```java
EvalScript evalKts = new EvalScriptWithJsonDataModel( "kts" );
try (Reader reader = [reader on kotlin script]) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put( "docTitle", "My custom title" );
    Object result = evalKts.evalKts( reader, dataModel );
    log.info( "my result : {}", result );
}
```