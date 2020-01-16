*** Settings ***
Library  SeleniumLibrary
Library  SeleniumScreenshots
Library  DateTime

*** Variables ***
${START URL}    http://automationpractice.com/
${LOGIN URL}    http://automationpractice.com/index.php?controller=authentication&back=my-account
${CONTACT URL}      http://automationpractice.com/index.php?controller=contact
${MY ACCOUNT URL}   http://automationpractice.com/index.php?controller=my-account
${REGISTRATION URL}     http://automationpractice.com/index.php?controller=authentication&back=my-account#account-creation
${REGISTRATION URL PRIM}    http://automationpractice.com/index.php?controller=authentication&back=my-account
${BROWSER}  Firefox
${MAIN TITLE}   My Store
${LOGIN TITLE}   Login - My Store
${CONTACT TITLE}    Contact us - My Store
${MESSAGE}  Women
${MAIN MESSAGE}  Automation Practice Website
${SIGNIN FAIL}  Authentication failed.
${USER MAIL}    maniek@gmail.com
${USER PASSWORD}    password
${USER FULL NAME}   Maniek Mankowski
${USER ALREADY EXIST MSG}   An account using this email address has already been registered. Please enter a valid password or request a new one.
${INVALID EMAIL}    Invalid email address.
${REG H3}   YOUR PERSONAL INFORMATION
*** Test Cases ***
TEST1: you can go from MainPage to SignIn page
    Open main page
    Wait until main page loads
    Get certain text from website
    Go to SignIn page
    Wait until SignIn page loads
    Assert SignIn url correct
    Close Browser
TEST2: you can go from Main Page to SingIn page and than to ContactUs page
    Open main page
    Wait until main page loads
    Go to SignIn page
    Wait until SignIn page loads
    Assert SignIn url correct
    Go to ContactUs page
    Wait until ContactUs page loads
    Assert ContactUs url correct
    Close Browser
TEST3: invalid SignIn incorrect credentials (authentication msg)
    Open SingIn page
    Wait until SignIn page loads
    Fill fake inputs: email and password
    Confirm SignIn form
    Wait until Authentication Failed box appears
    Read failed SignIn message
    Close Browser
TEST4: invalid SignIn no credentials entered (email msg)
    Open SingIn page
    Wait until SignIn page loads
    Confirm SignIn form
    Wait until Authentication Failed box appears
    Read failed SignIn message - mail msg
    Close Browser
TEST5: invalid SignIn entered true mail, fake password (authentication msg)
    Open SingIn page
    Wait until SignIn page loads
    Fill true mail email and fake password
    Confirm SignIn form
    Wait until Authentication Failed box appears
    Read failed SignIn message
    Close Browser
TEST6: invalid SignIn entered invalid mail, fake password (authentication msg)
    Open SingIn page
    Wait until SignIn page loads
    Fill in invalid email and password
    Confirm SignIn form
    Wait until Authentication Failed box appears
    Read failed SignIn message -invalid email
    Close Browser
TEST7: valid SingIn test
    Open SingIn page
    Wait until SignIn page loads
    Fill true inputs: email and password
    Confirm SignIn form
    Wait until Registered User Accout details appears
    Assert UserAccount url correct
    Read UserName from navigation
    Take screenshot
    Close Browser
TEST8: invalid Registration test - email already registered
    Open SingIn page
    Wait until SignIn page loads
    Fill in already registered email
    Confirm Register form
    Wait until Registration Failed box appears
    Read failed Registration message - user already exist
    Close Browser
TEST9: invalid Registration test - email invalid format
    Open SingIn page
    Wait until SignIn page loads
    Fill in invalid format email
    Confirm Register form
    Wait until Registration Failed box appears
    Read failed SignIn message -invalid email
    Close Browser
TEST10: valid Register test - generated mail
    Open SingIn page
    Wait until SignIn page loads
    Fill in generated email field
    Confirm Register form
    Wait until Registration Form appears
    Assert that user at Registration url
    Fill in neccesary registration fields correctly
    Confirm Full Registration
    Wait until Registered User Accout details appears
    Assert UserAccount url correct
    Close Browser
*** Keywords ***
Open main page
    Open browser    ${START URL}    ${BROWSER}
Open SingIn page
    Open browser    ${LOGIN URL}    ${BROWSER}
Get certain text from website
    Get text  id=editorial_block_center
    ${TEMP}=    Get text    css=div#editorial_block_center h1
    should be equal as strings  ${TEMP}     ${MAIN MESSAGE}
Wait until main page loads
    Wait Until Element Is Visible  class=logo
    Title should be     ${MAIN TITLE}
Wait until SignIn page loads
    Wait Until Element Is Visible  css=span.navigation_page
    Title should be     ${LOGIN TITLE}
Wait until ContactUs page loads
    Wait Until Element Is Visible  css=span.navigation_page
    Title should be     ${CONTACT TITLE}
Wait until Authentication Failed box appears
    Wait Until Element Is Visible   css=div.alert
Wait until Registration Failed box appears
    Wait Until Element Is Visible   id=create_account_error
Wait until Registered User Accout details appears
    Wait Until Element Is Visible   css=ul.myaccount-link-list
Wait until Registration Form appears
    Wait Until Element Is Visible   css=form#account-creation_form
    ${REG MSG}  Get text    css=div.account_creation h3.page-subheading
    should be equal as strings  ${REG MSG}  ${REG H3}
Go to SignIn page
    Click link  css=a.login
Assert SignIn url correct
    ${CURRENT URL}=   Get Location
    should be equal as strings  ${CURRENT URL}  ${LOGIN URL}
Assert ContactUs url correct
    ${CURRENT URL}=   Get Location
    should be equal as strings  ${CURRENT URL}  ${CONTACT URL}
Assert UserAccount url correct
    ${CURRENT URL}=   Get Location
    should be equal as strings  ${CURRENT URL}  ${MY ACCOUNT URL}
    Page Should Contain  Welcome to your account
Assert that user at Registration url
    ${CURR URL}=   Get Location
    Should Be True  '${CURR URL}'=='${REGISTRATION URL}' or '${CURR URL}'=='${REGISTRATION URL PRIM}'
Go to ContactUs page
    Click link  css=div#contact-link a
Fill fake inputs: email and password
    Input Text  id=email  random_email@gmail.com
    Input Text  id=passwd  password
Fill true inputs: email and password
    Input Text  id=email  ${USER MAIL}
    Input Text  id=passwd  ${USER PASSWORD}
Fill true mail email and fake password
    Input Text  id=email  ${USER MAIL}
    Input Text  id=passwd   randomFakePassword
Fill in invalid email and password
    Input Text  id=email  donut@
    Input Text  id=passwd   randomFakePassword
Fill in already registered email
    Input Text  id=email_create  ${USER MAIL}
Fill in invalid format email
    Input Text  id=email_create     donjuan@
Fill in generated email field
    ${epoch} =  Get Current Date  result_format=epoch  exclude_millis=true
    ${GENERATED EMAIL} =  Catenate  SEPARATOR=  robot.  ${epoch}  @gmail.com
    ${PASSWORD} =  Set Variable  password
    Input Text  id=email_create     ${GENERATED EMAIL}
Confirm SignIn form
    Click Button  id=SubmitLogin
Confirm Register form
    Click Button  id=SubmitCreate
Confirm Full Registration
    Click Button  id=submitAccount
Read failed SignIn message
    ${FAILURE}  Get text    css=div.alert ol li
    should be equal as strings  ${FAILURE}  ${SIGNIN FAIL}
Read failed Registration message - user already exist
    ${FAILURE}  Get text    css=div#create_account_error ol li
    should be equal as strings  ${FAILURE}  ${USER ALREADY EXIST MSG}
Read failed Registration message - invalid email format
    ${FAILURE}  Get text    css=div#create_account_error ol li
    should be equal as strings  ${FAILURE}  ${INVALID EMAIL}
Read failed SignIn message -invalid email
    ${FAILURE}  Get text    css=div.alert ol li
    should be equal as strings  ${FAILURE}  ${INVALID EMAIL}
Read failed SignIn message - mail msg
    ${FAILURE}  Get text    css=div.alert ol li
    should be equal as strings  ${FAILURE}  An email address required.
Read UserName from navigation
    ${USER NAME}    Get text    css=div.header_user_info a span
    should be equal as strings  ${USER NAME}  ${USER FULL NAME}
Take screenshot
    Capture Page Screenshot    custom-screenshot.png
Fill in neccesary registration fields correctly
    Click Element  css=input#id_gender1
    Click Element   css=select#days
    wait until element is visible   css=select#days option
    Click Element   css=select#days option[value='29']
    Click Element   css=select#months option[value='9']
    Click Element   css=select#years option[value='1989']
    Input Text  id=customer_firstname  Test
    Input Text  id=customer_lastname  Test
    Input Text  id=passwd  password
    Input Text  id=firstname  Test
    Input Text  id=lastname  Test
    Input Text  id=address1  Test
    Input Text  id=city  Test
    Input Text  id=postcode  80765
    Input Text  id=phone_mobile  123456789
    Select From List By Index  id=id_state  1


