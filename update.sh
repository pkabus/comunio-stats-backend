#!/bin/bash

PATH_REPO=/home/pi/projects/comunio-stats-backend

git -C $PATH_REPO pull

# if any files have been modified in the last 24 hours
if [ "$(find $PATH_REPO/ -type f -mtime -1)" ]
then
    echo "Changes in directory $PATH_REPO detected, build docker image..."

    # get project version
    VERSION=$($PATH_REPO/mvnw -q -Dexec.executable=echo -Dexec.args='${project.version}' --non-recursive exec:exec)

    # build pi production docker image
    docker build -f $PATH_REPO/Dockerfile.piprod -t pi/comunio-stats-backend:$VERSION .

    # stop running container with given name
    docker stop comunio-stats-backend

    # run pi production docker container
    docker run -itd --rm --name comunio-stats-backend -e SPRING_PROFILES_ACTIVE='hybriddocker' -p 8080:8080 pi/comunio-stats-backend:$VERSION
fi
