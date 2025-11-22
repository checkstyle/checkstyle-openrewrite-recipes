#!/bin/bash
# Attention, there is no "-x" to avoid problems on CircleCI
set -e

echo "Generation of pitest report:"
echo "./mvnw -e --no-transfer-progress -Ppitest clean test-compile org.pitest:pitest-maven:mutationCoverage"
set +e
./mvnw -e --no-transfer-progress -Ppitest clean test-compile org.pitest:pitest-maven:mutationCoverage
EXIT_CODE=$?
set -e
echo "Execution of comparison of suppressed mutations survivals and current survivals:"
echo "groovy .ci/pitest-survival-check.groovy"
groovy .ci/pitest-survival-check.groovy
exit $EXIT_CODE
