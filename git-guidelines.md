# Git Guidelines (Strictly Enforced)

These guidelines define the Git workflow for the Belair's Buvette project, strictly enforced and tailored for GitLab.

## 1. Introduction
- All source control operations must use GitLab as the central platform.
- Adherence to these guidelines is mandatory for all contributors.

## 2. Branching Strategy
- Use the following branch types:
  - `main`: Production-ready code only. Protected branch.
  - `develop`: Integration branch for features and fixes. Protected branch.
  - `feature/*`: For new features. Name as `feature/short-description`.
  - `bugfix/*`: For bug fixes. Name as `bugfix/short-description`.
  - `hotfix/*`: For urgent production fixes. Name as `hotfix/short-description`.
  - `release/*`: For release preparation. Name as `release/x.y.z`.
- Branches must be created from `develop` unless fixing production issues (then from `main`).
- Delete feature, bugfix, hotfix, and release branches after merge.

## 3. Commit Message Standards
- Commit messages must follow this format:
  - `<type>: <short summary>`
  - Types: `feat`, `fix`, `docs`, `test`, `refactor`, `chore`, `style`, `ci`
- Use imperative mood: "Add new endpoint", not "Added new endpoint".
- Include a detailed description in the body if needed.
- Reference related issue IDs (e.g., `Closes #123`).
- Do not commit generated files, build outputs, or sensitive data.

## 4. Pull Request (Merge Request) Guidelines
- All changes must be submitted via GitLab Merge Requests (MRs).
- MRs must target `develop` (or `main` for hotfixes).
- Provide a clear title and description.
- Link related issues and reference guidelines.
- Assign reviewers and set appropriate labels.
- Ensure CI pipeline passes before requesting review.

## 5. Merge Rules
- Only maintainers can merge into `main` and `develop`.
- MRs must be approved by at least one reviewer.
- Squash commits before merging unless history is required.
- No direct pushes to protected branches.
- Resolve all conflicts before merging.

## 6. Tagging & Releases
- Use semantic versioning for tags: `vX.Y.Z`.
- Tag releases from `main` after successful deployment.
- Annotate tags with release notes.
- Do not tag unfinished or unstable code.

## 7. Handling Conflicts
- Rebase your branch onto the latest `develop` before opening an MR.
- Resolve conflicts locally; never push unresolved conflicts.
- Communicate with affected contributors if conflicts are complex.

## 8. Sensitive Data & Security
- Never commit secrets, credentials, or sensitive files.
- Use `.gitignore` to exclude local configs, build outputs, and secrets.
- If sensitive data is accidentally committed, remove it using GitLab's recommended procedures and notify maintainers immediately.

---

Strict adherence to these guidelines ensures a clean, secure, and efficient Git workflow for all contributors. All violations will be flagged and must be corrected before merge.