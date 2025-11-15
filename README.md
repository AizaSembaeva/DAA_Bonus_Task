# KMP String Matching Algorithm – Mini Report

## 1. Overview

This project implements the **Knuth-Morris-Pratt (KMP) string matching algorithm** in Java. The KMP algorithm efficiently finds all occurrences of a pattern string within a text string by precomputing a **Longest Prefix Suffix (LPS) array**, which allows the algorithm to skip unnecessary character comparisons.

The implementation consists of:

- `KMPMatcher`: the core algorithm with `findFirst` and `findAll` methods.
- `KMPDemo` / `CliRunner`: a CLI utility to read text and pattern from input files and output results either to the console or files.

**File format example:**

    line1

    line2

    line3
    ...
    PATTERN: <pattern>

The program concatenates all lines except the last into the text and uses the last line to extract the pattern.

---

## 2. Implementation Details

### `KMPMatcher`

- `buildLPS(String pattern)` – constructs the **LPS array** to store the longest proper prefix which is also a suffix for each prefix of the pattern.
- `findAll(String text, String pattern)` – returns a list of all starting indices where the pattern occurs in the text.
- `findFirst(String text, String pattern)` – returns the first occurrence index (or -1 if none).

**Logic Workflow:**

1. Precompute LPS array for the pattern (`O(m)` time, `m = pattern length`).
2. Traverse the text while comparing characters with the pattern.
3. If mismatch occurs, use LPS to skip characters without re-checking already matched prefixes.
4. Store all match indices in a list.

The CLI runner reads the input file, extracts the text and pattern, runs the matcher, and writes results to either the console or a file.

---

## 3. Complexity Analysis

| Operation         | Time Complexity | Space Complexity         |
|------------------|----------------|-------------------------|
| LPS computation  | O(m)           | O(m)                    |
| Pattern search   | O(n)           | O(1) (excluding output) |
| Total            | O(n + m)       | O(m + k) where k = matches |

- **n** – length of the text
- **m** – length of the pattern
- **k** – number of matches found

This is significantly more efficient than the naive algorithm (`O(n * m)`) for large texts and repetitive patterns.

---

## 4. Testing & Results

Tested on multiple input files:

| File       | Text Length | Pattern    | Matches Found                 | First Index |
|------------|------------|------------|-------------------------------|------------|
| short.txt  | 75         | "abc"      | [24, 28, 31, 35]             | 24         |
| medium.txt | 169        | "ababc"    | [54, 59, 109, 114, 119, ...] | 54         |
| long.txt   | 278        | "abcabc"   | [55, 58, 61, ...]             | 55         |

- Output can be written to a file automatically using the CLI:
  ```bash
  java -cp target/... CliRunner <input-file> <output-file>
Handles edge cases such as empty pattern or text gracefully.

## 5. Code Quality & Comments

- Each class is documented with Javadoc-style comments.
- Methods include inline comments explaining key steps (LPS computation, match logic, index tracking).
- CLI runner ensures clear user feedback for file errors or invalid input.

## 6. Summary

The KMP algorithm is a linear-time string search algorithm suitable for large texts and repetitive patterns.

**Advantages:**
- Linear runtime
- Minimal backtracking
- Clear separation of pattern preprocessing (LPS) and search

**Use Case:**
- Efficiently searching for substrings in files, logs, or textual datasets