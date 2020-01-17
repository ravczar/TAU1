class Range(object):

    def __init__(self, start, end):
        self.__start = start
        self.__end = end

    def __eq__(self, other):
        return self.__start <= other <= self.__end

    def __repr__(self):
        return "range => [0.00 : +infinity]"

