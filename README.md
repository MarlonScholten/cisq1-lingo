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
Using compononents with known Vulnerabilies can lead to to development teams not even understanding which components they use in their application or API, much less keeping them up to date.
### RiskAssessment of risk. 
Current risk - LOW
### Counter-measures
- Included the OWASP dependency check in the maven build.
- Added dependabot to this repository
- Accepted some initial Dependabot pull requests after passing tests.
