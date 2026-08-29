# Beginning Development Guide

This guide is for developers who want to contribute to the **Checkstyle OpenRewrite Recipes** project. It will guide you through everything you need to do to complete your first pull request.

## Contents

- [Before Development](#before-development)
- [Starting Development](#starting-development)
- [Running Tests](#running-tests)
- [Coding Rules & Code Style](#coding-rules--code-style)

---

## Before Development

### 1. Prerequisites
Ensure that Git and Java JDK >= 21 are installed.

### 2. Fork the Repository
First, fork the official `checkstyle/checkstyle-openrewrite-recipes` upstream project to your own GitHub account.

### 3. Clone the Forked Repository
Clone your forked repository to your computer (replace `MY_USERNAME` with your GitHub username):
```bash
git clone git@github.com:MY_USERNAME/checkstyle-openrewrite-recipes.git
cd checkstyle-openrewrite-recipes
```

### 4. Configure Remotes
Configure git remotes by pointing to the official `checkstyle-openrewrite-recipes` repository, naming it `upstream`:
```bash
git remote add upstream https://github.com/checkstyle/checkstyle-openrewrite-recipes.git
```

### 5. Build and Verify the Project
Build the project in your terminal to verify everything works and download the needed dependencies:
```bash
./mvnw clean install
```
To run full verification checks (including verifying the OpenRewrite recipes against the project sources), run:
```bash
./mvnw clean verify
```

---

## Starting Development

### 1. Create a Branch
Create a branch for your new check/recipe:
```bash
git checkout -b my-new-recipe
```

### 2. Commit and Push Changes
Commit changes to your branch:
```bash
git add .
git commit -m "commit message"
```
Push the branch to your GitHub fork:
```bash
git push origin my-new-recipe
```
Repeat this process until your changes are ready.

### 3. Keep Your Branch Up to Date
Before submitting a PR, update your local `main` branch by pulling changes from the official `upstream` repository:
```bash
git checkout main
git pull upstream main
git push origin main
```
Rebase your feature branch on the updated `main` branch:
```bash
git checkout my-new-recipe
git rebase main
```
If there are any conflicts, resolve them and run:
```bash
git add .
git rebase --continue
```
Finally, force-push the updated branch to your fork:
```bash
git push --force origin my-new-recipe
```

### 4. Send a Pull Request
Once testing is done and the build passes locally, submit a Pull Request to the main repository.

---

## Running Tests

### Run All Tests
To run all tests in the codebase, execute:
```bash
./mvnw test
```

### Run a Single Test Class
To run a single test class (e.g., `UpperEllTest`):
```bash
./mvnw test -Dtest=UpperEllTest
```

### Run a Single Test Method
To run a single test method:
```bash
./mvnw test -Dtest=UpperEllTest#hexOctalLiteral
```

### Mutation Testing
We use Pitest for mutation testing. The project defines multiple Pitest profiles in `pom.xml` (including `pitest-common`, `pitest-marker`, `pitest-parser`, `pitest-recipe-1`, and `pitest-recipe-2`).

To execute mutation tests for a specific profile, run the script with the profile name:
```bash
./.ci/pitest.sh "pitest-common"
```
Any surviving mutations are checked against `config/pitest-suppressions.xml`.

### Regenerating Test Diff Files
If you edit test fixtures and need to refresh the `.diff` files:
```bash
./mvnw test -Dtest=GenerateDiffFilesTest#generateDiffs
```
