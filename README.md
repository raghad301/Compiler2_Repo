# Flask/Jinja Compiler Project

This project implements an educational compiler pipeline for a supported subset
of Python/Flask and HTML/Jinja. It parses `input/app.py`, extracts the context
passed to every `render_template()` call, analyzes every template, and generates
standalone HTML pages in `output/`.

## Requirements

- JDK 17 or newer (tested with JDK 25).
- `antlr-4.13.2-complete.jar` must remain in the project root.

Set `JAVA_HOME` to the installed JDK when `java` and `javac` are not already on
`PATH`.

## Input

```text
input/
|- app.py
|- style.css
|- script.js
|- pics/
|  `- product images
`- templates/
   |- index.jinja
   |- add_product.jinja
   |- edit_product.jinja
   `- product_details.jinja
```

`Main` reads only these real input paths. Semantic error samples are stored
separately under `examples/` and are never processed during a normal run.
## Run in IntelliJ IDEA

1. Open the `Compiler2_Repo` folder in IntelliJ IDEA.
2. Select JDK 17 or newer as the Project SDK.
3. Keep `antlr-4.13.2-complete.jar` configured as a project library.
4. Open `src/Main.java` and run `Main`.

The run succeeds only when all syntax, semantic, context, and generation phases
succeed. A failed compilation never prints the success message.

## Generated output

```text
output/
|- index.html
|- add_product.html
|- edit_product.html
|- product_details.html
|- app.py
|- style.css
|- script.js
`- pics/
   `- copied product images

compiler_output/
|- ast_python.json
|- ast_jinja.json
|- ast_jinja_<template>.json
|- semantic_report.txt
`- generation_log.txt
```

The JSON AST reports are recursive and include node types, line numbers, and
children. The aggregate Jinja report contains all successfully parsed templates.

## Semantic error examples

The `examples/semantic_errors/` directory contains seven Python examples and
five Jinja examples demonstrating the semantic errors handled by the compiler.
They are documentation samples and are deliberately kept outside `input/`, so
the normal compilation run never reads them by accident.

## Compiler pipeline

```text
input/app.py
  -> Python Lexer/Parser
  -> Python AST + Symbol Table
  -> Python Semantic Analysis
  -> per-template context extraction
  -> Web/Jinja Lexer/Parser
  -> Jinja AST + Symbol Table
  -> Web Semantic Analysis
  -> WebCodeGenerator
  -> output/*.html
```

CSS, JavaScript, and product images are support assets. They are copied unchanged
to `output/`; CSS inside `<style>` and script text inside `<script>` remain
supported by the Web grammar.

## Final submission layout

```text
<group-number>.zip
|- Compiler2_Repo/                 source project
|- Compiler_Project_Final_Report.pdf
`- GROUP_INFO.txt                  repository URL and member names
```
