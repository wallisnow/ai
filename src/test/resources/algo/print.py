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
    if not os.path.exists('./uploads/rest.txt'):
        with open('./uploads/rest.txt', 'w'):
            pass
