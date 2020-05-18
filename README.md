Sample Selenide UI Automation Test Framework [![Build Status](https://travis-ci.com/vikmaksimenko/sample-selenide-automation.svg?branch=master)](https://travis-ci.com/vikmaksimenko/sample-selenide-automation)
=====================================================

This is a project for running Selenide automated UI tests on TestContainers 

### Required tools
 
* JDK 8
* Maven
* Docker 

### Testing Environents

Tests can be executed on: 

- [x] Local Chrome and Firefox (requires chromedriver and geckodriver installation and availability in PATH)
- [x] Headless Chrome and Firefox (same requirements)
- [x] Docker container (requires Docker)
- [x] Selenoid cluster (requires Docker and docker-compose)
- [ ] Zalenium cluster (requires Docker and docker-compose)
- [ ] SaaS solutions

Predefined browser resolutions:
 
- 640x360
- 800x600
- 1024x768
- 1280x720
- 1366x768
- 1600x900
- 1920x1080
- 3840x2160

### How to run


0. Start Docker service 
1. Run tests:
```
mvn clean test -Pbrowser.chrome -P environment.local.container
```
3. Build reports to temp folder and view in browser:    
```
mvn allure:serve
```
4. Build reports and save to `target/site`
```
mvn allure:report
```

Sample Allure report can be found on http://sample-selenide-automation.surge.sh

### Environment specific notes

#### Selenoid Cluster

1. Pull browser images before running tests: 
    ```
    docker pull selenoid/vnc_firefox:76.0
    docker pull selenoid/vnc_chrome:81.0
    ```
2. Run docker-compose
   ```
   docker-compose -f selenoid-docker-compose.yml up
   ```

### Travis CI Integration

This project is integrated with Travis CI, see https://travis-ci.com/vikmaksimenko/sample-selenide-automation 
