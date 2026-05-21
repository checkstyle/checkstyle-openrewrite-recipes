#!/bin/bash

set -e

function list_tasks() {
  cat "${0}" | sed -E -n 's/^([a-zA-Z0-9\-]*)\)$/\1/p' | sort
}

case $1 in

check-missing-pitests)
  fail=0
  mkdir -p target

  list=($(cat pom.xml | \
    xmlstarlet sel --ps -N pom="http://maven.apache.org/POM/4.0.0" \
    -t -v "//pom:profile[./pom:id[contains(text(),'pitest')]]//pom:targetClasses/pom:param"))

  CMD="find src/main/java -type f ! -name 'package-info.java'"

  for item in "${list[@]}"
  do
    item=${item//\./\/}
    if [[ $item != */\*  ]] ; then
      if [[ $item != *\* ]] ; then
        item="$item.java"
      else
        item="${item%?}.java"
      fi
    fi

    CMD="$CMD -and ! -wholename '*/$item'"
  done

  CMD="$CMD | sort > target/result.txt"
  eval "$CMD"

  results=$(cat target/result.txt)

  echo "List of missing files in pitest profiles: $results"

  if [[ -n $results ]] ; then
    fail=1
  fi

  sleep 5s
  exit $fail
  ;;

*)
  echo "Unexpected argument: $1"
  echo "Supported tasks:"
  list_tasks "${0}"
  false
  ;;

esac
