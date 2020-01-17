from TriangleCheck import TriangleCheck
from RectangleCheck import RectangleCheck


class TriangleOrRectangle:

    __zero_list = []
    __none_list = []
    __figure_kind = None

    def __init__(self, incoming_list):
        self.__list = incoming_list

    def __repr__(self):
        return self.__figure_kind

    @property
    def list(self):
        return self.__list

    @property
    def figure_kind(self):
        return self.__figure_kind

    @list.setter
    def shop_return_allowed(self, new_list):
        self.__list = new_list

    def __zero_count(self):
        return len(self.__zero_list)

    def __none_count(self):
        return len(self.__none_list)

    def __check_incoming_list_values_for_zeros_and_nones(self):
        self.__zero_list = [length for length in self.__list if length == 0]
        self.__none_list = [length for length in self.__list if length is None]

    def get_figure_name(self):
        self.__check_incoming_list_values_for_zeros_and_nones()
        if self.__zero_count() >= 1:
            #print("Podano długości równe 0, nic nie liczymy!")
            self.__figure_kind = "nierozpoznano"
        elif self.__none_count() == 1:
            #print("Liczymy trójkąty")
            t = TriangleCheck(self.list)
            t.get_triangle_type()
            self.__figure_kind = t.triangle_type
            del t
        elif self.__none_count() > 1:
            #print("Nie podano 2,3, lub 4 wartości - nieprawdopodobne, nic nie liczymy!")
            self.__figure_kind = "nierozpoznano"
        else:
            #print("Liczymy czworotkąty")
            r = RectangleCheck(self.list)
            r.get_rectangle_type()
            self.__figure_kind = r.rectangle_type
            del r

'''
lista = [3.5, 3.5, 3.5, 3.5]
decision = TriangleOrRectangle(lista)
decision.get_figure_name()
print("The decision = ", decision)
'''