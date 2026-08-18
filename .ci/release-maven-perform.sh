#!/usr/bin/env bash

set -euo pipefail

source ./.ci/util.sh

checkForVariable "SONATYPE_USER"
checkForVariable "SONATYPE_PWD"
checkForVariable "MAVEN_GPG_PASSPHRASE"

if [[ -z $1 ]]; then
  echo "version is not set"
  echo "Usage: $0 <version>"
  exit 1
fi

TARGET_VERSION=$1
ARTIFACT_ID=$(getMavenProperty project.artifactId)
EXPECTED_TAG="${ARTIFACT_ID}-${TARGET_VERSION}"

echo TARGET_VERSION="$TARGET_VERSION"
echo EXPECTED_TAG="$EXPECTED_TAG"

if [[ ! -f release.properties ]]; then
  echo "[ERROR] release.properties is missing."
  exit 1
fi

if ! grep -Fq "scm.tag=${EXPECTED_TAG}" release.properties; then
  echo "[ERROR] release.properties does not describe ${EXPECTED_TAG}."
  grep '^scm.tag=' release.properties || true
  exit 1
fi

echo "Publishing $EXPECTED_TAG to Maven Central ..."

./mvnw \
  -e \
  --no-transfer-progress \
  --batch-mode \
  release:perform
