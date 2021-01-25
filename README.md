# comunio-stats-backend

Spring Boot backend for the comunio-stats project.

## Docker build

The spring-boot-maven-plugin is able to create a docker image for this spring boot application. However, it might be a security risk to let the plugin create docker images by the non root user. To still enable the current user to use docker, add the user to the docker group (`sudo groupadd docker` and `sudo usermod -aG docker $USER`, then log out and in again). See also: [Post-installation steps for Linux](https://docs.docker.com/engine/install/linux-postinstall/)

