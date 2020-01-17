*** Settings ***
#http://robotframework.org/robotframework/latest/libraries/Process.html
Library  Process
Library  BuiltIn

Suite Teardown    Terminate All Processes    kill=True
*** Variables ***

#${jarName}      tau6-1.0-SNAPSHOT.jar
#${targetPath}   ..${/}app${/}target
#${jarPath}      ${targetPath}${/}${jarName}
#${exec}         java -jar ${jarPath}
${help}     https://stackoverflow.com/questions/36690705/how-to-execute-python-scripts-in-robot-framework
${STDOUT DIR}  catch_console/stdout.txt
${STDERR DIR}   catch_console/stderr.txt
${PROGRAM DIRECTORY}   lab6/Main.py

*** Test cases ***
TEST1) WYSWIETLANIE HELP BOX
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --help    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   show this help message and exit
TEST2) BŁĄD Z POWODU NIE PODANIA PARAMETRÓW (3 obowiązkowe)
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stderr}   the following arguments are required: --first, --second, --third
TEST3) BŁĄD Z POWODU NIE PODANIA ZŁEGO TYPU PARAMETRÓW (3 obowiązkowe)
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   a     --second  b       --third  c   --fourth  d  stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stderr}   invalid float value
TEST4) TRÓJKĄT RÓWNOBOCZNY
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   3.0     --second  3.0       --third  3.0    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   trójkąt równoboczny
TEST5) TRÓJKĄT RÓWNORAMIENNY
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   3.0     --second  3.0       --third  2.0    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   trójkąt równoramieny
TEST6) TRÓJKĄT RÓŻNOBOCZNY
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   1.5     --second  2.0       --third  3.0    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   trójkąt różnoramienny
TEST7) TRÓJKĄT NIEMOŻLIWY (pierwsze dwa boki są krótsze niż trzeci bok)
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   1.0     --second  2.0       --third  3.0    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   nierozpoznano
TEST8) TRÓJKĄT NIEMOŻLIWY (podano długości boków 0.0)
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   0.0     --second  0.0       --third  0.0    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   nierozpoznano
TEST9) TRÓJKĄT NIEMOŻLIWY (podano któryś bok 0.0)
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   0.0     --second  3.0       --third  2.0    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   nierozpoznano
TEST10) CZWOROBOK KWADRAT
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   3.0     --second  3.0       --third  3.0    --fourth    3.0    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   kwadrat
TEST11) CZWOROBOK CZWOROBOK a,b,c,d
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   1.0     --second  2.0       --third  3.0    --fourth    4.0    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   czworobok
TEST12) CZWOROBOK CZWOROBOK c,b,a,a
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   3.0     --second  4.0       --third  2.0    --fourth    2.0    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   czworobok
TEST13) CZWOROBOK CZWOROBOK a,a,b,c
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   2.0     --second  2.0       --third  3.0    --fourth    4.0    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   czworobok
TEST14) CZWOROBOK PROSTOKĄT a,b,a,b
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   1.0     --second  2.0       --third  1.0    --fourth    2.0    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   prostokąt
TEST15) CZWOROBOK PROSTOKĄT a,b,b,a
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   1.0     --second  3.0       --third  3.0    --fourth    1.0    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   prostokąt
TEST16) CZWOROBOK PROSTOKĄT a,a,b,b
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   1.0     --second  1.0       --third  3.0    --fourth    3.0    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   prostokąt

*** Keywords ***

Figures
    [Arguments]  ${lengths}  ${expectedResult}

    ${output} =  Run  ${exec} ${lengths}

    Should Be Equal  ${output}  ${expectedResult}