#!/usr/bin/env python3

import os
import re
import glob

# Find all test Java files
test_files = glob.glob('/modernize-data/studio-data/TNT1001/APP1077/transformed-code/43/studio-workspace/commonservices/src/test/java/**/*.java', recursive=True)

for file_path in test_files:
    with open(file_path, 'r') as f:
        content = f.read()

    # Fix broken when statements
    content = re.sub(r'when\(([^)]+)\);([^)]+)\)\);', r'when(\1).thenReturn(\2);', content)

    # Fix times() calls with L suffix
    content = re.sub(r'times\((\d+)L\)', r'times(\1)', content)

    # Fix verify statements
    content = re.sub(r'verify\(([^,]+),\s*times\((\d+)\)\)\.([^;]+);', r'verify(\1, times(\2)).\3();', content)

    with open(file_path, 'w') as f:
        f.write(content)

print("Fixed test syntax issues")