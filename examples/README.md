# Semantic Error Examples

This directory contains deliberately invalid samples demonstrating the semantic
errors handled by the compiler:

- `semantic_errors/python/`: seven Python semantic error categories.
- `semantic_errors/jinja/`: five Web/Jinja semantic error categories.

These files are evidence and documentation only. They are kept outside
`input/`, so a normal run of `Main` never processes them accidentally. To
demonstrate one during the final interview, first back up the corresponding
valid input file, copy one example into the real input location, run the
compiler, observe the diagnostic and prevented generation, then restore the
valid input file.
