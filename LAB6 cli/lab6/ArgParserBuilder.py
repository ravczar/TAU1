import argparse
from RangeCheck import Range
import math
from decimal import *


class ArgParserBuilder:
    __start = 0.0
    __stop = math.inf
    __help = "Pass here proper side length 'value >= 0'"

    def __init__(self):
        self.__parser = None

    # Example:
    # python Main.py --first 2.5 --second 3.5 --third 4.5 --fourth 5.5
    def build_arg_parser(self):
        self.__parser = argparse.ArgumentParser(description="Find out if you can build triangle or something else!")
        self.__parser.add_argument('--first', dest='first', type=float, required=True,
                                   choices=[Range(self.__start, self.__stop)],
                                   help=self.__help)
        self.__parser.add_argument('--second', dest='second', type=float, required=True,
                                   choices=[Range(self.__start, self.__stop)],
                                   help=self.__help)
        self.__parser.add_argument('--third', dest='third', type=float, required=True,
                                   choices=[Range(self.__start, self.__stop)],
                                   help=self.__help)
        self.__parser.add_argument('--fourth', dest='fourth', type=float, required=False,
                                   choices=[Range(self.__start, self.__stop)],
                                   help=self.__help)
        return self.__parser

    def get_parser(self):
        return self.__parser
