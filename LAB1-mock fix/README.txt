Poprawiony lab 2
Biblioteka Mockito, Junit.

Do bazy danych dodaj pola:
    1) createDate, 
    2) modifyDate, 
    3)accessDate
Obsłuż rejestrowanie tych dat i czasu. 
Domyślnie mają być włączone, ale pozwól na ich wyłączenie opcjonalnie.

Testy w pliku : ./carsApp/src/main/java/cars/test/DataMaockTest.java

1)Uruchamianie z powyższej lokalizacji klikając w Run Test na pojedyńczym teście lub na całej grupie.
2)uruchamianie z lokalizacji ./carsApp w terminalu poprzez komendę >mvn test

Budowa projektu:

DbImpl (lista z metodami CRUD) -> CarImpl (pojedyńcze auto z polami)
DbObjectHolder (pole typu CarImpl przechowujący jedno auto, 3 pola LocalDateTime i metody obsługi tych pól) -> CarImpl(pojedyncze auto z polami)
DbObjectProperties: statyczna klasa definiująca które pola typu LocalDateTime będziemy sledzić, odwołujemy się statycznie.
DateMockTest: testy naszej implementacji!

Pozdrawiam
Rafał Czarnecki