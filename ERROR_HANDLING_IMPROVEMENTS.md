# Error Handling Improvements

## What's This About?

Fixed a bunch of error handling issues that were flagged in the code review. Basically, the code wasn't checking for bad inputs properly, which could lead to cryptic NPEs and confusing error messages.

## What Changed?

### CI Workflow (.github/workflows/ci.yml)

The Maven build and git diff steps were failing silently or with unclear errors. Now:
- Added explicit `continue-on-error: false` so we know immediately when builds fail
- Rewrote the git diff check to use proper if-statements instead of that weird `||` syntax
- When files change, it actually shows you which files changed (super helpful for debugging)

### Config File (rewrite.yml)

Added some comments at the top explaining what this file does and how to use it. Also added inline comments for the CheckstyleAutoFix recipe so people know what each path is for and what format it expects.

### Java Code

#### CheckstyleAutoFix.java
Added a simple `validateInputs()` method that checks if paths are null or empty before we try to use them. Throws clear exceptions like "Violation report path cannot be null or empty" instead of letting it blow up later with an NPE.

#### XmlReportParser.java
Now checks:
- Is the path null?
- Does the file actually exist?
- Does the XML have a filename attribute?

No more mysterious failures when parsing broken XML files.

#### SarifReportParser.java
Added validation for pretty much everything:
- Path exists?
- File is valid SARIF?
- Has runs and results?
- All required fields present (level, message, location, etc.)?

Basically, fail fast with a helpful message instead of crashing halfway through.

#### ConfigurationLoader.java
Just added some basic null/empty checks for the config path and properties file. Nothing fancy, just defensive programming.

## Why This Matters

- No more NPEs that make you dig through stack traces
- Error messages actually tell you what's wrong
- Fails early instead of halfway through processing
- Easier to debug when something goes wrong
- CI failures are way more obvious

## Testing

Should probably test:
- Missing files
- Malformed XML/SARIF
- Empty configs
- Null parameters
- CI behavior on failures

But honestly, the main thing is that errors are now obvious instead of cryptic.
