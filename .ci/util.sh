#!/bin/bash

set -e

function checkForVariable() {
  VAR_NAME=$1
  if [ ! -v "$VAR_NAME" ]; then
    echo "Error: Define $1 environment variable"
    exit 1
  fi

  VAR_VALUE="${!VAR_NAME}"
  if [ -z "$VAR_VALUE" ]; then
    echo "Error: Set not empty value to $1 environment variable"
    exit 1
  fi
}

function getMavenProperty() {
  ./mvnw \
    --no-transfer-progress \
    --quiet \
    help:evaluate \
    -Dexpression="$1" \
    -DforceStdout
}

function getCheckstylePomVersion {
  getMavenProperty project.version
}

function getCheckstylePomVersionWithoutSnapshot {
  getCheckstylePomVersion | sed "s/-SNAPSHOT//"
}
