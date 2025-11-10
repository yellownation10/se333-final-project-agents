# SE333 Final Project – MCP Testing Agent for Apache Commons Lang

This repository contains my SE333 final project: a local MCP-powered testing agent integrated with the Apache Commons Lang (Defects4J) codebase.

The agent can:

- Run the Maven test suite on the `codebase` project.
- Generate and interpret JaCoCo coverage reports.
- Identify low-coverage / risky classes.
- Propose and scaffold new JUnit tests.
- Help fix failing tests and regression bugs.
- Use simple git helpers to stage, commit, and summarize changes.

Everything runs **locally** on my machine; the MCP server exposes tools that a model in VS Code can call.

---

## Repository Structure

```text
se333-final-project-agents/
├─ codebase/                 # Apache Commons Lang3 (SE333/Defects4J) Maven project
│  ├─ pom.xml
│  ├─ src/main/java/...
│  └─ src/test/java/...
├─ server.py                 # FastMCP HTTP server exposing testing + git tools
├─ .github/
│  └─ prompts/
│     └─ tester.prompt.md    # MCP tester agent configuration
├─ .vscode/ (optional)       # VS Code MCP / workspace settings (not required by grader)
├─ .gitignore
├─ README.md                 # This file
└─ (later) reflection.pdf, docs/, etc.


Prerequisites

You will need:

Java 21 (or compatible with the provided pom.xml)

Maven 3.x

Python 3.11+

VS Code with MCP-capable extension (per course instructions)

All commands below assume macOS / Unix-style shell.

1. Setup

Clone the repo:
git clone https://github.com/yellownation10/se333-final-project-agents.git
cd se333-final-project-agents

Create and activate a virtual environment (recommended):
python3 -m venv .venv
source .venv/bin/activate
Install Python dependencies (if requirements.txt is present):

pip install -r requirements.txt


If there is no requirements.txt, server.py only needs standard library + the MCP framework provided in the assignment.

2. Running Tests (Maven)

From the codebase directory:

cd codebase
mvn clean test


Expected:

All tests pass:

Failures: 0

Errors: 0

(Some tests may be marked @Ignore upstream; that’s okay.)

3. Generating JaCoCo Coverage

With tests passing, generate the coverage report:

cd codebase
mvn -Djacoco.skip=false clean test jacoco:report


Then open:

open target/site/jacoco/index.html


You should see class/package-level coverage for the Commons Lang project.
This report is what the MCP testing agent reads via its read_coverage tool.

4. Running the MCP Testing Server

From the project root (se333-final-project-agents):

cd se333-final-project-agents
source .venv/bin/activate   # if not already active
python3 server.py


You should see log output similar to:

Uvicorn running on http://127.0.0.1:8000 (Press CTRL+C to quit)


The server exposes an MCP endpoint at:

http://127.0.0.1:8000/sse

5. VS Code MCP Agent Setup

Open this folder in VS Code.

Configure your MCP client (per course instructions) to use:

Type: HTTP / SSE

URL: http://127.0.0.1:8000/sse

Ensure it picks up:

.github/prompts/tester.prompt.md


This file defines the Tester Agent:

mode: "agent"

model: "gpt-5-mini" (or equivalent per assignment)

Tools:

run_mvn_tests

read_coverage

generate_test_skeleton

git_status, git_add_all, git_commit, git_push, git_pull_request

suggest_boundary_tests

review_class

6. How to Use the Tester Agent

In VS Code Chat, select the Tester Agent (not plain “Ask”) and try:

Run tests:

Run run_mvn_tests on ./codebase and show me the test results.

Read coverage:

Use read_coverage on codebase/target/site/jacoco/jacoco.xml and report overall coverage and the lowest-covered classes.

Improve tests:

For one low-coverage class, call generate_test_skeleton and then suggest concrete JUnit tests (no assertTrue(true)).

Git ops (local):

Show git_status.
Stage changes with git_add_all and git_commit with message "Add boundary tests for StringUtils".

The agent is instructed to:

Only commit when tests are passing.

Use coverage + tools to guide meaningful test improvements.

Produce a PR-style summary via git_pull_request describing fixes & coverage gains.

7. Creative / Extra Tools

Two additional tools are provided conceptually (implemented in server.py):

suggest_boundary_tests

Given a class or method, suggests boundary-value and edge-case test inputs.

review_class

Performs a lightweight static review of a class to highlight potential missing tests or code smells.

These are used by the tester agent to propose smarter test cases beyond simple line coverage.

8. What Was Fixed / Findings (Short Summary)

(Adjust this section to match your final changes.)

Resolved issues in:

NumberUtils hex parsing (to satisfy NumberUtilsTest.testCreateNumber expectations).

DateUtils locale-dependent parsing (LANG-799-style failures).

Achieved a fully passing test suite on the provided codebase.

Generated JaCoCo reports and used them to confirm coverage and guide test ideas.

Integrated MCP-based tools so the agent can run Maven, inspect coverage, and interact with git.