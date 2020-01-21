Zadanie 2 - > MOCKITO:

Rozwijamy nasz projekt z poprzednich zajęć. Pamiętaj - najpierw testy.

Chcielibyśmy, aby nasza baza danych przechowywała informację na temat momentów czasowych związanych z dostępem do rekordów. 
Chcielibyśmy aby zapisywane były (dla każdego rekordu - identyfikowanego po id):

    czas dodania rekordu do bazy danych
    czas ostatniej modyfikacji rekordu w bazie danych
    czas ostatniego odczytu rekordu z bazy danych

Ponad to powinna być możliwość włączenia lub wyłączenia dowolnego z tych trzech. Domyślnie baza danych powinna przechowywać informację o datach.
Podpowiedź, jakie kroki można wykonać aby uzyskać ten cel

Jeśli nie czujesz się jeszcze pewnie w TDD, to poniżej jest moja propozycjia kolejności tworzenia testów i nowych funkcjonalności.

Każdy punkt to test-implementacja-refaktoryzacja. Jeśli projekcik z poprzednich zajęć został dobrze przemyślany, 
to powinno się udać przerobić (lub zrobić od nowa - mamy przecież interfejs) 
klasę do obsługi bazy danych tak, aby testy, bez większych zmian, przechodziły.

    Rozszerzenie sposobu zapisu danych w naszej "bazie" o obiekt zawierający te trzy informacje. Można użyć typu Date (tradycyjnie), lub z nowego sposobu zarządzania czasem (w javie 8). Proponuję opakować po prostu rekord z "bazy" w nowej klasie, która dodatkowo przechowuje informację o momentach czasowych. W tym momencie nie jest konieczne pisanie dodatkowego testu, ale dotychczasowe testy powinny działać po tym rozwinięciu.
    Stworzyć test, a następnie implementację, sposobu ustawiania daty przy odczycie rekordu (może jest kilka sposobów na odczyt, to będzie kilka testów). W tym momencie polecam wykorzystanie mocków w celu zapewnienia, że data jest ustalona i można ją porównać
    Stworzyć test, a następnie implementację, sposobu ustawiania daty przy dodawaniu rekordu. Mocki do daty.
    Stworzyć test, a następnie implementację, sposobu ustawiania daty przy aktualizacji rekordu. Tu także mocki do daty.
    Stworzyć test, a następnie metodę, która pozwoli na uzyskanie informacji o rekordzie - poszczególnych znacznikach czasowych.
    Stworzyć test, a następnie metodę, do włączania/wyłączania zapisywania poszczególnych znaczników czasowych.
        ten punkt może się rozwinąć na kilka etapów - dla każdego znacznika (dodanie, odczyt, modyfikacja) oraz 2 możliwości - włączenie i wyłączenie.

Tym razem nie stosujemy wszystkich możliwości Mockito, ponieważ chciałbym, aby wszystkim udało się wykonać dzisiejsze ćwiczenie z zachowaniem zasad TDD.

© Puźniakowski 2018/2019/2020
