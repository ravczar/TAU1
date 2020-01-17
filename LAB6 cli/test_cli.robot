*** Settings ***

#http://robotframework.org/robotframework/latest/libraries/Process.html
#http://robotframework.org/robotframework/latest/libraries/Process.html#Specifying%20command%20and%20arguments
Library  Process
Library  BuiltIn

Suite Teardown    Terminate All Processes    kill=True
*** Variables ***

${help}     https://stackoverflow.com/questions/36690705/how-to-execute-python-scripts-in-robot-framework
${STDOUT DIR}  catch_console/stdout.txt
${STDERR DIR}   catch_console/stderr.txt
${PROGRAM DIRECTORY}   lab6/Main.py

*** Test cases ***

TEST1) WYSWIETLANIE HELP BOX
    Czy wyświetli się help box na żądanie   
TEST2) BŁĄD Z POWODU NIE PODANIA PARAMETRÓW (3 obowiązkowe)
    Czy wyświetli błąd z powodu braku któregoś z parametrów  
TEST3) BŁĄD Z POWODU NIE PODANIA ZŁEGO TYPU PARAMETRÓW (3 obowiązkowe)
    Czy wyświetli błąd z powodu będnego typu parametru
TEST4) TRÓJKĄT RÓWNOBOCZNY
    Sprawdzenie trójkąta równobocznego
TEST5) TRÓJKĄT RÓWNORAMIENNY
    Sprawdzenie trójkąta równoramiennego
TEST6) TRÓJKĄT RÓŻNOBOCZNY
    Sprawdzenie trójkąta różnobocznego
TEST7) TRÓJKĄT NIEMOŻLIWY
    Pierwsze dwa boki są krótsze od trzeciego boku
TEST8) TRÓJKĄT NIEMOŻLIWY (podano długości boków 0.0)
    Podano wszystkie boki równe zero
TEST9) TRÓJKĄT NIEMOŻLIWY (podano któryś bok 0.0)
    Podano któryś bok równy zero
TEST10) CZWOROBOK KWADRAT
    Sprawdzenie czy rozpoznał kwadrat
TEST11) CZWOROBOK CZWOROBOK a,b,c,d
    Sprawdzenie czy rozpoznał czworobok a,b,c,d
TEST12) CZWOROBOK CZWOROBOK c,b,a,a
    Sprawdzenie czy rozpoznał czworobok c,b,a,a
TEST13) CZWOROBOK CZWOROBOK a,a,b,c
    Sprawdzenie czy rozpoznał czworobok a,a,b,c
TEST14) CZWOROBOK PROSTOKĄT a,b,a,b
    Sprawdzenie czy rozpoznał prostokąt a,b,a,b
TEST15) CZWOROBOK PROSTOKĄT a,b,b,a
    Sprawdzenie czy rozpoznał prostokąt a,b,b,a
TEST16) CZWOROBOK PROSTOKĄT a,a,b,b
    Sprawdzenie czy rozpoznał prostokąt a,a,b,b
TEST17) CZWOROBOK NIEMOŻLIWY (pierwsze trzy boki są krótsze niż czwarty bok)
    Czy wyświetli nierozpoznano dla (pierwsze trzy boki są krótsze niż czwarty bok)
TEST18) CZWOROBOK NIEMOŻLIWY (podano długości boków 0.0)
    Czy wyświetli nierozpoznano dla (podano długości boków 0.0)
TEST19) CZWOROBOK NIEMOŻLIWY (podano któryś bok 0.0)
    Czy wyświetli nierozpoznano dla (podano któryś bok 0.0)
TEST20) CZWOROBOK NIEMOŻLIWY (pierwsze trzy boki są dłuższe niż czwarty bok)
    Czy wyświetli nierozpoznano dla (pierwsze trzy boki są dłuższe niż czwarty bok)

*** Keywords ***

Czy wyświetli się help box na żądanie
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --help    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   show this help message and exit

Czy wyświetli błąd z powodu braku któregoś z parametrów
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stderr}   the following arguments are required: --first, --second, --third

Czy wyświetli błąd z powodu będnego typu parametru
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   a     --second  b       --third  c   --fourth  d  stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stderr}   invalid float value

Sprawdzenie trójkąta równobocznego
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   3.0     --second  3.0       --third  3.0    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   trójkąt równoboczny

Sprawdzenie trójkąta równoramiennego
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   3.0     --second  3.0       --third  2.0    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   trójkąt równoramieny

Sprawdzenie trójkąta różnobocznego
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   1.5     --second  2.0       --third  3.0    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   trójkąt różnoramienny

Pierwsze dwa boki są krótsze od trzeciego boku
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   1.0     --second  2.0       --third  3.0    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   nierozpoznano

Podano wszystkie boki równe zero
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   0.0     --second  0.0       --third  0.0    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   nierozpoznano

Podano któryś bok równy zero
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   0.0     --second  3.0       --third  2.0    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   nierozpoznano

Sprawdzenie czy rozpoznał kwadrat
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   3.0     --second  3.0       --third  3.0    --fourth    3.0    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   kwadrat

Sprawdzenie czy rozpoznał czworobok a,b,c,d
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   1.0     --second  2.0       --third  3.0    --fourth    4.0    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   czworobok

Sprawdzenie czy rozpoznał czworobok c,b,a,a
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   3.0     --second  4.0       --third  2.0    --fourth    2.0    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   czworobok

Sprawdzenie czy rozpoznał czworobok a,a,b,c
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   2.0     --second  2.0       --third  3.0    --fourth    4.0    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   czworobok

Sprawdzenie czy rozpoznał prostokąt a,b,a,b
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   1.0     --second  2.0       --third  1.0    --fourth    2.0    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   prostokąt

Sprawdzenie czy rozpoznał prostokąt a,b,b,a
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   1.0     --second  3.0       --third  3.0    --fourth    1.0    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   prostokąt

Sprawdzenie czy rozpoznał prostokąt a,a,b,b
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   1.0     --second  1.0       --third  3.0    --fourth    3.0    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   prostokąt

Czy wyświetli nierozpoznano dla (pierwsze trzy boki są krótsze niż czwarty bok)
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   1.0     --second  2.0       --third  3.0    --fourth  10.0     stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   nierozpoznano

Czy wyświetli nierozpoznano dla (podano długości boków 0.0)
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   0.0     --second  0.0       --third  0.0    --fourth  0.0      stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   nierozpoznano

Czy wyświetli nierozpoznano dla (podano któryś bok 0.0)
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   1.0     --second  3.0       --third  2.0    --fourth  0.0       stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   nierozpoznano

Czy wyświetli nierozpoznano dla (pierwsze trzy boki są dłuższe niż czwarty bok)
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   10.0     --second  3.0       --third  1.0    --fourth  1.0     stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   nierozpoznano