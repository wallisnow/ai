#!/usr/bin/env python
import csv
import sys

with open(sys.argv[1]) as f:
    f_csv = csv.reader(f)
    headers = next(f_csv)
    for row in f_csv:
        print(row)

