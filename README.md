# CISQ1: Lingo Trainer
[![Build and test](https://github.com/MarlonScholten/cisq1-lingo/actions/workflows/main.yml/badge.svg?branch=master)](https://github.com/MarlonScholten/cisq1-lingo/actions/workflows/main.yml)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=MarlonScholten_cisq1-lingo&metric=bugs)](https://sonarcloud.io/dashboard?id=MarlonScholten_cisq1-lingo)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=MarlonScholten_cisq1-lingo&metric=code_smells)](https://sonarcloud.io/dashboard?id=MarlonScholten_cisq1-lingo)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=MarlonScholten_cisq1-lingo&metric=coverage)](https://sonarcloud.io/dashboard?id=MarlonScholten_cisq1-lingo)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=MarlonScholten_cisq1-lingo&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=MarlonScholten_cisq1-lingo)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=MarlonScholten_cisq1-lingo&metric=alert_status)](https://sonarcloud.io/dashboard?id=MarlonScholten_cisq1-lingo)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=MarlonScholten_cisq1-lingo&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=MarlonScholten_cisq1-lingo)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=MarlonScholten_cisq1-lingo&metric=security_rating)](https://sonarcloud.io/dashboard?id=MarlonScholten_cisq1-lingo)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=MarlonScholten_cisq1-lingo&metric=sqale_index)](https://sonarcloud.io/dashboard?id=MarlonScholten_cisq1-lingo)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=MarlonScholten_cisq1-lingo&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=MarlonScholten_cisq1-lingo)

# Vulnerability Analysis
## A9: Using Components with Known Vulnerabilities
### Description
Using compononents with known vulnerabilities can lead to to development teams not even understanding which components they use in their application or API, much less keeping them up to date.
### Assessment of risk. 
Current risk - LOW
### Counter-measures
- Included the OWASP dependency check in the maven build.
- Added Dependabot to this repository
- Accepted some initial Dependabot pull requests after passing tests.

## A6: Security Misconfiguration
### Description
Security misconfiguration can happen at any level of an application stack, including the network services, platform, web server, application server, database, frameworks, custom code, and pre-installed virtual machines, containers, or storage. Automated scanners are useful for detecting misconfigurations, use of default accounts or configurations, unnecessary services, legacy options, etc.
### Assessment of risk. 
Current risk - LOW
### Counter-measures
- Included a production profile that does not return a stack trace when an exception is thrown, thus hiding detailed information.
- Added the Repository to Sonarcloud to scan for vulnerabilities.
- A9 Countermeasures
