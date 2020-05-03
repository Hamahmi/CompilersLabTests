# -*- coding: utf-8 -*-
__author__ = "Hamahmi"
"""
Please put this file in the same dir as your .g4 file after compiling it.
"""

import argparse
import os

parser = argparse.ArgumentParser(description="Task 8 Test")
parser.add_argument(
    "--name",
    "-n",
    type=str,
    help="Your file name : T07_37_1234_John_Smith",
    required=True,
)
args = parser.parse_args()


if __name__ == "__main__":

    test_cases = {
        "101.101": "5.625",
        "100.11": "4.75",
        "0.10": "0.5",
        "111.111": "7.875",
        "1010.110": "10.75",
        "0.10110": "0.6875",
        "10111.1000101": "23.5390625",
        "1011101.11101": "93.90625",
        "0010101.110111010": "21.86328125",
        "0.000001": "0.015625",
        "10.0010100001": "2.1572265625",
    }
    wrong = dict()
    test_size = len(test_cases.keys())
    i = 0

    for case, expected_output in test_cases.items():
        with open("temp.txt", "w") as f:
            f.write(case)
        output = os.popen(
            "java org.antlr.v4.gui.TestRig " + args.name + " start temp.txt"
        ).read()

        if float(output) != float(expected_output):
            wrong[case] = (expected_output, output)
        i += 1
        print("{:.1f}".format(i / test_size * 100.0) + " %", end="\r")

    os.system("del temp.txt")
    print()
    if wrong == {}:
        print("Passed all " + str(test_size) + " test cases :)")
    else:
        print(
            "Wrong answer in "
            + str(len(wrong.keys()))
            + " / "
            + str(test_size)
            + " :( !!"
        )
        for case, outputs in wrong.items():
            print(
                "In test case : "
                + case
                + " the output : "
                + outputs[0]
                + " was expected, but got : "
                + outputs[1],
                end="",
            )
