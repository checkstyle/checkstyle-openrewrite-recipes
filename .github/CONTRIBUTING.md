# Contribution Guidelines

This guide serves to set clear expectations for everyone involved with the project so that we can improve it together while also creating a welcoming space for everyone to participate. Following these guidelines will help ensure a positive experience for contributors and maintainers alike.

Thanks for your interest in contributing to CheckStyle!

## Contents

- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Opening an Issue](#opening-an-issue)
- [Reporting Security Issues](#reporting-security-issues)
- [Submitting Pull Requests](#submitting-pull-requests)
- [Code Review](#code-review)
- [Asking Questions](#asking-questions)

## Code of Conduct

This project and everyone participating in it is governed by the [CheckStyle Code of Conduct](https://github.com/checkstyle/checkstyle/blob/master/.github/CODE_OF_CONDUCT.md).

## Getting Started

Please see the [Build Instructions](.github/BEGINNING_DEVELOPMENT.md) for information on how to get started with the project. This includes setting up your development environment, building the project, and running tests.

Select an issue to work on from the [Issues Page](https://github.com/checkstyle/checkstyle-openrewrite-recipes/issues).

When you decide which issue you would like to take up, please comment on the issue to let others know that you are working on it ("I am on it."). It is completely ok to change a mind, please try to remove comment. If you see such comment created long time ago but issue is still open and no Pull created, please feel free to make a comment that you will try to do it.

## Submitting Pull Requests

- **Read our pull request rules.** See [PR Rules](https://github.com/checkstyle/checkstyle/wiki/PR-rules).
- **Comment on the issue.** When you decide which issue you would like to take up, please comment on the issue to let others know that you are working on it ("I am on it."). Existing "I am on it" comments are a good indication that someone is already working on the issue, but these comments can be old or outdated; if a comment is a few weeks old with no activity, feel free to ask if the issue is still being worked on.
- **Read the Github docs.** Visit GitHub's [Pull Request Guide](https://help.github.com/en/github/collaborating-with-issues-and-pull-requests/about-pull-requests) for information on how to submit a pull request.
- **Follow the template.** Please follow the [Pull Request Template](.github/PULL_REQUEST_TEMPLATE.md) that is provided in the pull request description when submitting a pull request.
- **Run maven build locally.** `mvn clean verify` should pass on your local before submitting a pull request.
- **Keep the PR small.** If you are working on a large feature, consider breaking it up into smaller PRs that can be reviewed and merged independently. This makes it easier for reviewers to understand the changes and for maintainers to merge the PR.

## Code Review

All submissions, including submissions by project members, require review. We use GitHub pull requests for this purpose. Consult the [GitHub Help](https://help.github.com/en/github/collaborating-with-issues-and-pull-request-reviews) for more information on pull request reviews.

Here are some general guidelines to follow when submitting a pull request:

- **Reply to comments.** If a reviewer asks for changes, reply to each (and every) comment with discussion or follow up questions, or let the reviewer know that you have addressed their concerns ("done").
- **Be patient.** Reviewing PRs takes time. If a reviewer hasn't responded in a week or so, feel free to ping them. If you are a reviewer and you need more time to review a PR, please let the submitter know.
- **Be kind.** Remember that everyone involved in the project is a human being. Be kind and respectful in your comments and reviews.
- **Be open to feedback.** If a reviewer asks for changes, be open to their feedback. Remember that the goal is to improve the project, and feedback is an important part of that process.

## Opening an Issue

A great way to contribute to the project is to create a detailed issue when you encounter a problem or would like to suggest a feature. We always appreciate a well-written, thorough issue description.

Some points to consider when opening an issue:

- **Make sure you are using the latest version.** Before [Opening an Issue](https://github.com/checkstyle/checkstyle-openrewrite-recipes/issues), check if you are using the latest version of the project, [found here](https://github.com/checkstyle/checkstyle-openrewrite-recipes/tags). If you are not up-to-date, check to see if updating to the latest release fixes your issue.
- **Do not open a duplicate feature request.** Search for existing feature requests first. If you find your feature (or one very similar) previously requested, comment on that issue.
- **Use [GitHub-flavored Markdown](https://help.github.com/en/github/writing-on-github/basic-writing-and-formatting-syntax).** Especially put code blocks and console outputs in backticks (\`\`\`). This improves readability.

## Reporting Security Issues

Do not file a public issue for security vulnerabilities. Please contact the maintainers directly. See the [Security Policy](https://github.com/checkstyle/checkstyle/blob/master/SECURITY.md) for more information.

## Asking Questions

See our [Discussions Page](https://github.com/checkstyle/checkstyle/discussions). In short, GitHub issues are not the appropriate place to debug your specific project, but should be reserved for filing bugs and feature requests. You can also visit our [Google Groups Forum](https://groups.google.com/g/checkstyle-devel).
