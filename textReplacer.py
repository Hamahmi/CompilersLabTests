__author__ = "Hamahmi"

import argparse

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Text replacer")
    parser.add_argument(
        "--name",
        "-n",
        type=str,
        help="Your Java class name (P.S it should be in the format : Tutorial_ID_Name)",
    )
    args = parser.parse_args()

    with open("task7Test.java", "rt") as input_file:
        data = input_file.read()
        data = data.replace("Tutorial_ID_Name", args.name)

    with open("task7Test.java", "wt") as output_file:
        output_file.write(data)

    print(
        "Done, now put task7Test.java in the same dir as your class and run the tests!"
    )
