#!/bin/bash
set -e

PREV_YEAR=$1
CURRENT_YEAR=$2
DIR=$3

echo "Bumping year from $PREV_YEAR to $CURRENT_YEAR in directory $DIR"

find "$DIR" -type f \( -name "*.java" -o -name "*.txt" \) -exec sed -i -E "s/Copyright \(C\) ${PREV_YEAR} /Copyright (C) ${CURRENT_YEAR} /g" {} +

find "$DIR" -type f \( -name "*.java" -o -name "*.txt" \) -exec sed -i -E "s/Copyright \\\\\(\C\\\\\) ${PREV_YEAR} /Copyright \\\(C\\\) ${CURRENT_YEAR} /g" {} +

if [ -f "$DIR/LICENSE" ]; then
    sed -i -E "s/Copyright \[?${PREV_YEAR}\]? /Copyright ${CURRENT_YEAR} /g" "$DIR/LICENSE" || true
fi
