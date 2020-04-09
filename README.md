# How to run Tests #

**Prerequisite**
1. Java environment (Java 8)
2. Maven 

Execute following commands to start tests:
- Go to project path in terminal.
- Run `mvn clean test`

Execute following commands to see allure report after test phase:
- Run `mvn allure:report`

Allure report can be found in folder -> `test_project_1/target/allure-report/index.html`

Execution result log for each test can be seen in folder `test_project_1/target/executionLogs/`

Explicit wait, implicit wait and browser can be configured in `test_project_1/src/test/resources/config.properties`