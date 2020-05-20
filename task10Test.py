# -*- coding: utf-8 -*-
__author__ = "Hamahmi"
"""
Please put this file in the same dir as your .g4 file after compiling it.
"""

import argparse
import os
import random
import sys

parser = argparse.ArgumentParser(description="Task 10 Test")
parser.add_argument(
    "--name",
    "-n",
    type=str,
    help="Your file name : T07_37_1234_John_Smith",
    required=True,
)
parser.add_argument(
    "--random",
    "-r",
    dest="rt",
    action="store_true",
    help="add -r or --random to get randomized test cases",
)
parser.add_argument(
    "--size",
    "-s",
    type=int,
    default=23,
    help="if you used the random option, you can set the number of test cases",
)
parser.add_argument(
    "--print",
    "-p",
    dest="print",
    action="store_true",
    help="add -p or --print to print the test cases",
)
args = parser.parse_args()


def generate_expression(size, previous_not):
    if size == 1:
        return str(random.randint(0, 1))
    random_operator = ["+", "&"][random.randint(0, 1)]
    add_par = random.randint(0, 1) == 1
    add_not_first = (not previous_not) and random.randint(0, 1) == 1
    add_not_second = random.randint(0, 1) == 1
    first_part = (
        ("!" if add_not_first else "")
        + str(random.randint(0, 1))
        + random_operator
        + ("!" if add_not_second else "")
    )
    if add_par:
        return first_part + "(" + generate_expression(size - 1, add_not_second) + ")"
    else:
        return first_part + generate_expression(size - 1, add_not_second)


def generate_random_expression():
    return generate_expression(random.randint(1, 10), False)


def evaluate_expression(expression):
    python_expression = (
        expression.replace("+", " or ").replace("&", " and ").replace("!", " not ")
    )
    return "1" if eval(python_expression) else "0"


def generate_random_test_cases(size):
    output = dict()
    counter = 0
    while counter < (size):
        expression = generate_random_expression()
        if not output.__contains__(expression):
            output[expression] = evaluate_expression(expression)
            counter += 1
    return output


if __name__ == "__main__":

    random.seed(23)

    if args.rt:
        test_cases = generate_random_test_cases(args.size)
    else:
        test_cases = {
            "1 + 1 & 0": "1",
            "!1 + 1 & ((0 + !1))": "0",
            "0 + (1 & 0) + !(!(0 & 1) + 1)": "0",
        }

    if args.print:
        for case, expected_output in test_cases.items():
            print(case + " -> " + expected_output)

    wrong = dict()
    test_size = len(test_cases.keys())

    i = 0
    for case, expected_output in test_cases.items():
        with open("temp.txt", "w") as f:
            f.write(case)

        output = (
            os.popen("java org.antlr.v4.gui.TestRig " + args.name + " start temp.txt")
            .read()
            .replace(" ", "")
            .replace("\n", "")
            .replace("\r", "")
        )

        if output == "":
            print("Please compile first!")
            sys.exit()

        ex_out = expected_output.replace(" ", "").replace("\n", "").replace("\r", "")

        if output != ex_out:
            wrong[case] = (ex_out, output)
        i += 1
        print("{:.1f}".format(i / test_size * 100.0) + " %", end="\r")

    if sys.platform.startswith("win"):
        os.system("del temp.txt")
    else:
        os.system("rm temp.txt")
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
                + outputs[1]
            )
