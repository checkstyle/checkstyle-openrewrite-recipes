#!/bin/bash

RESOURCES_DIR="src/test/resources/org/checkstyle/autofix/recipe"

echo "Generating diffs for recipe tests..."

find "$RESOURCES_DIR" -name "Input*" -type f | while read -r input_file; do
    dir=$(dirname "$input_file")
    basename_input=$(basename "$input_file")
    suffix=${basename_input#Input}
    dir=${dir%/}
    output_file="$dir/Output$suffix"
    diff_file="$dir/Diff${suffix%.*}.diff"

    if [ -f "$output_file" ]; then
        echo "Processing: $input_file -> $output_file"
        git diff --no-index --no-prefix "$input_file" "$output_file" > "$diff_file" 2>/dev/null
        echo "  Diff generated: $diff_file"
    fi
done
