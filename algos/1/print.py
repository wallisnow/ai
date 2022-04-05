#!/usr/bin/env python
import csv
import sys
import os
import time

with open(sys.argv[1]) as f:
    f_csv = csv.reader(f)
    headers = next(f_csv)
    for row in f_csv:
        print(row)
    time.sleep(10)

with open("../../results/1.res", 'w') as f:
    f.write("0.95 0.93")