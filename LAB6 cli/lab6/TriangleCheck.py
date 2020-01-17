
class TriangleCheck:

    __a = None
    __b = None
    __c = None
    __triangle_type = None

    def __init__(self, proper_list):
        self.__list = proper_list

    @property
    def triangle_type(self):
        return self.__triangle_type

    def __repr__(self):
        return self.triangle_type

    def __check__triangle_validity(self):
        if (self.__a + self.__b <= self.__c or
                self.__a + self.__c <= self.__b or
                self.__b + self.__c <= self.__a):
            return False
        else:
            return True

    def get_triangle_type(self):
        self.__a = self.__list[0]
        self.__b = self.__list[1]
        self.__c = self.__list[2]

        if self.__check__triangle_validity():
            if (self.__a == self.__b and self.__b != self.__c) \
                    or (self.__a == self.__c and self.__c != self.__b) \
                    or (self.__b == self.__c and self.__c != self.__a):
                self.__triangle_type = "trójkąt równoramieny"
            elif self.__a == self.__b == self.__c:
                self.__triangle_type = "trójkąt równoboczny"
            else:
                self.__triangle_type = "trójkąt różnoramienny"
        else:
            self.__triangle_type = "nierozpoznano"

'''
lista = [7.0, 10.0, 5.0, None]
triangle = TriangleCheck(lista)
triangle.get_triangle_type()
print(triangle)
'''