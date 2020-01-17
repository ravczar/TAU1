LAB6: test programu do rozpoznawania trójkątów i czworoboków
Napisany program w wybranym języku przetestować za pomocą Robota.

./lab6/ -> program w pythonie przyjmujący paramaetry (posiada plik przedstawiajacy dzialanie programu)
./test_cli.robot -> program testowania robot
./catch_console -> zbiera po każdym teście stderr i stout do wglądu. (następny test nadpisuje poprzedni)
./test_report/ -> zbiera raport z testu

Komenda do odpalenia:
(Będzie zrzucać raporty do katalogu ./test_report/)
robot -d test_report test_cli.robot