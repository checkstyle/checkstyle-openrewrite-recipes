#!/usr/bin/env bash

set -euo pipefail

source ./.ci/util.sh

if [[ -z $1 ]]; then
  echo "version is not set"
  echo "Usage: $0 <version>"
  exit 1
fi

TARGET_VERSION=$1
echo TARGET_VERSION="$TARGET_VERSION"

POM_VERSION=$(getCheckstylePomVersion)

if [[ "$POM_VERSION" != *-SNAPSHOT ]]; then
  echo "[ERROR] Current POM version must be a SNAPSHOT: $POM_VERSION"
  exit 1
fi

CURRENT_VERSION=${POM_VERSION%-SNAPSHOT}
echo CURRENT_VERSION="$CURRENT_VERSION"

if [[ "$TARGET_VERSION" != "$CURRENT_VERSION" ]]; then
  echo "[ERROR] Target version and current POM version do not match."
  exit 1
fi

echo "Preparing Maven release $TARGET_VERSION ..."

./mvnw \
  -e \
  --no-transfer-progress \
  --batch-mode \
  release:prepare \
  -DpushChanges=false \
  -DreleaseVersion="$TARGET_VERSION"
