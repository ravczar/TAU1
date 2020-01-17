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
TEST1) TRÓJKĄT RÓWNOBOCZNY
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   3.0     --second  3.0       --third  3.0    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   trójkąt równoboczny
TEST2) TRÓJKĄT RÓWNORAMIENNY
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   3.0     --second  3.0       --third  2.0    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   trójkąt równoramieny
TEST3) TRÓJKĄT RÓŻNOBOCZNY
    ${result} = 	Run Process 	python  ${PROGRAM DIRECTORY}  --first   1.5     --second  2.0       --third  3.0    stdout=${STDOUT DIR} 	stderr=${STDERR DIR}    output_encoding=SYSTEM
    Log Many 	stdout: ${result.stdout} 	stderr: ${result.stderr}
    Should Contain       ${result.stdout}   trójkąt różnoramienny

*** Keywords ***

Figures
    [Arguments]  ${lengths}  ${expectedResult}

    ${output} =  Run  ${exec} ${lengths}

    Should Be Equal  ${output}  ${expectedResult}