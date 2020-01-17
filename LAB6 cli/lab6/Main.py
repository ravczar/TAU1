from ArgParserBuilder import ArgParserBuilder
from TriangleOrRectangleCheckDecisionMaker import TriangleOrRectangle

"""
    1* Z wykorzystaniem dowolnej technologii zaimplemtuj program, który przyjmuje 3 lub 4 parametry.
        Następnie wyświetla w konsoli informację, jeżeli z tych parameterów określających długości boków 
        da złożyć się figurę geometryczną. 
        Program wyświetla jej nazwę.
        Program rozpoznaje następujące figury: trójką równoboczny, trójkąt równoramienny, trójkąt różnoramienny, 
        czworobok, kwadrat, prostokąt. 
        Jeżeli z boku nie da złożyć się figury, program wyświetla: nierozpoznano.
    2* Wyślij program do osoby z ławki. Dostarcz instrukcje uruchomienia aplikacji.
    3* Program otrzymany od osoby siedzącej w tej samej ławce - przetestuj za pomocą RobotFramework
"""

if __name__ == '__main__':
    args = ArgParserBuilder().build_arg_parser().parse_args()
    arg_list = [args.first, args.second, args.third, args.fourth]
    decision = TriangleOrRectangle(arg_list)
    decision.get_figure_name()
    print("Podano boki: ", arg_list)
    print("Możliwa figura: ", decision.figure_kind)