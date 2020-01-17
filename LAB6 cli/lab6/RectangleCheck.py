
class RectangleCheck:

    __a = None
    __b = None
    __c = None
    __d = None
    __rectangle_type = None

    def __init__(self, proper_list):
        self.__list = proper_list

    @property
    def rectangle_type(self):
        return self.__rectangle_type

    def __repr__(self):
        return self.rectangle_type

    def __check__rectangle_validity(self):
        # True- gdy długość każdego odcinka jest mniejsza niż suma długości pozostałych
        if (self.__a + self.__b + self.__c <= self.__d or
                self.__a + self.__c + self.__d <= self.__b or
                self.__b + self.__c + self.__d <= self.__a or
                self.__a + self.__b + self.__d <= self.__c):
            return False
        else:
            return True

    def get_rectangle_type(self):
        self.__a = self.__list[0]
        self.__b = self.__list[1]
        self.__c = self.__list[2]
        self.__d = self.__list[3]

        if self.__check__rectangle_validity():
            if self.__a == self.__b == self.__c == self.__d:
                self.__rectangle_type = "kwadrat"
            elif (self.__a == self.__c and self.__b == self.__d) or\
                    (self.__a == self.__b and self.__c == self.__d) or \
                    (self.__a == self.__d and self.__b == self.__c):
                self.__rectangle_type = "prostokąt"
            else:
                self.__rectangle_type = "czworobok"
        else:
            self.__rectangle_type = "nierozpoznano"

'''
lista = [3.5, 3.5, 2.5, 2.5]
rectangle = RectangleCheck(lista)
rectangle.get_rectangle_type()
print(rectangle)
'''