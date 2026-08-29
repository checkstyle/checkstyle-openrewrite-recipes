# PLEASE READ BEFORE REMOVING

**Rules:**

- **Issue Requirement**
   - If an issue exists, reference it in the Pull Request description:
     Example: `"Issue: #XXXXXX: ...."`
- **Commit message** should adhere to the following rules:
   - MUST match any one of the following patterns:

     ```
     ^Issue #\d+: .*$
     ^Pull #\d+: .*$
     ^(minor|config|infra|doc|spelling|dependency): .*$
     ```

   - MUST contain only one line of text
   - MUST NOT end with a period, space, or tab
   - MUST be less than or equal to 200 characters

To avoid multiple iterations of fixes and CI failures, please read the
[Contribution Guide](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/CONTRIBUTING.md).

**ATTENTION:** Pull Requests that do not pass our CI checks will not be merged,
but we will help to resolve issues.

---
Thanks for reading, feel free to remove this whole message and type what you need.
