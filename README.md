# 🦅 CloudEagle Test Automation Framework (Internal Documentation)

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/)
[![Selenium](https://img.shields.io/badge/Selenium-4.12.0-brightgreen.svg)](https://selenium.dev/)
[![TestNG](https://img.shields.io/badge/TestNG-7.8.0-blue.svg)](https://testng.org/)
[![Maven](https://img.shields.io/badge/Maven-3.x-red.svg)](https://maven.apache.org/)
[![Build Status](https://img.shields.io/badge/Build-Passing-green.svg)](https://github.com/vijayarajan-g/cloudeagle-automation)



## 📋 Table of Contents

- [🎯 Overview](#-overview)
- [🏗️ Architecture](#️-architecture)
- [🛠️ Prerequisites](#️-prerequisites)
- [⚡ Quick Start](#-quick-start)
- [📁 Project Structure](#-project-structure)
- [🔧 Configuration](#-configuration)
- [🧪 Test Scenarios](#-test-scenarios)
- [🚀 Running Tests](#-running-tests)
- [📊 Reporting](#-reporting)
- [🐛 Troubleshooting](#-troubleshooting)
- [🔄 Continuous Integration](#-continuous-integration)
- [📈 Best Practices](#-best-practices)
- [🤝 Contributing](#-contributing)
- [📞 Contact & Support](#-contact--support)

---

## 🎯 Overview

The CloudEagle Test Automation Framework is designed to automate **UI workflows** of the CloudEagle application, including:

- 🔐 **Authentication** – Login/Logout  
- 📊 **Dashboard** – Navigation & validation  
- 📂 **Application Management** – Managed apps count  
- 🔎 **Data Validation** – Cross-page consistency  

---

## 🏗️ Architecture

Tests (TestNG) → Page Objects (POM) → WebElements (Selenium)
↓ ↓ ↓
BaseTest Utilities Drivers
### Design Patterns Used
- **Page Object Model (POM)** – Encapsulation of page interactions  
- **Factory Pattern** – Browser instantiation (WebDriverManager)  
- **Singleton Pattern** – Config manager  
- **Builder Pattern** – Test data setup  

---
## 🛠️ Prerequisites

- **Java 17+**  
- **Maven 3.6+**  
- **Chrome (latest stable)**  
- **Git**
- 
⚡ Quick Start

Verify setup:
```bash
java -version
mvn -version
git --version
# Clone repo
git clone https://github.com/vijayarajan-g/cloudeagle-automation.git
cd cloudeagle-automation

# Install dependencies
mvn clean install

# Run tests
mvn test

📁 Project Structure
cloudeagle/
├── src/main/java/com/pagecloudeagle/      # Page Objects
│   ├── LoginPage.java
│   ├── DashboardPage.java
│   └── ApplicationsPage.java
├── src/test/java/com/testcloudeagle/      # Test Classes
│   ├── BaseTest.java
│   ├── LoginTest.java
│   └── DashboardTest.java
├── pom.xml                                # Maven config
├── testng.xml                             # TestNG suite
└── README_INTERNAL.md                     # Internal documentation

🔧 Configuration
Maven Dependencies

<dependency>
  <groupId>org.seleniumhq.selenium</groupId>
  <artifactId>selenium-java</artifactId>
  <version>4.12.0</version>
</dependency>
<dependency>
  <groupId>org.testng</groupId>
  <artifactId>testng</artifactId>
  <version>7.8.0</version>
  <scope>test</scope>
</dependency>
<dependency>
  <groupId>io.github.bonigarcia</groupId>
  <artifactId>webdrivermanager</artifactId>
  <version>5.4.1</version>
</dependency>

TestNG Suite Example

<suite name="AutomationFrameworkSuite">
  <test name="LoginTests">
    <classes>
      <class name="com.testcloudeagle.LoginTest"/>
    </classes>
  </test>
  <test name="DashboardTests">
    <classes>
      <class name="com.testcloudeagle.DashboardTest"/>
    </classes>
  </test>
</suite>

🧪 Test Scenarios

LoginTest.java

Valid login

Invalid login

DashboardTest.java

Managed apps count validation

Dashboard navigation

ApplicationsTest.java

Applications list validation

🚀 Running Tests

# All tests
mvn clean test

# Specific class
mvn test -Dtest=LoginTest

# Custom suite
mvn test -DsuiteXmlFile=testng.xml

# Browser param
mvn test -Dbrowser=chrome -Dheadless=true

📊 Reporting

Reports are generated under:
target/surefire-reports/
  ├── index.html
  ├── emailable-report.html
  └── testng-results.xml

🐛 Troubleshooting

WebDriver not found: Update WebDriverManager

NoSuchElementException: Add explicit waits

TimeoutException: Increase wait, validate locators

Dependency issues: Run mvn clean install -U

🔄 Continuous Integration
GitHub Actions

name: Test Automation
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - uses: actions/setup-java@v2
        with:
          java-version: '17'
      - run: mvn clean test

Jenkins

pipeline {
  agent any
  stages {
    stage('Checkout') { steps { git 'https://github.com/vijayarajan-g/cloudeagle-automation.git' } }
    stage('Test') { steps { sh 'mvn clean test' } }
    stage('Reports') { steps { publishTestResults testResultsPattern: 'target/surefire-reports/*.xml' } }
  }
}

🤝 Contributing (Internal Workflow)

Create branch → git checkout -b feature/new-test

Commit → git commit -m "Add new test"

Push → git push origin feature/new-test

Open PR

📞 Contact & Support

👤 Maintainer: Vijayarajan G
📧 vijayarajan.g27@gmail.com
🔗 LinkedIn
💻 GitHub















