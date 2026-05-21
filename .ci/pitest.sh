#!/bin/bash
# Attention, there is no "-x" to avoid problems on CircleCI
set -e

PROFILE=$1

echo "Generation of pitest report:"
echo "./mvnw -e --no-transfer-progress -P$PROFILE clean test-compile org.pitest:pitest-maven:mutationCoverage"
set +e
./mvnw -e --no-transfer-progress -P$PROFILE clean test-compile org.pitest:pitest-maven:mutationCoverage
EXIT_CODE=$?
set -e
echo "Execution of comparison of suppressed mutations survivals and current survivals:"
echo "groovy .ci/pitest-survival-check.groovy $PROFILE"
groovy .ci/pitest-survival-check.groovy $PROFILE
exit $EXIT_CODE
