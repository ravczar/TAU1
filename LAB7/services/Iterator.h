#ifndef ITERATOR_H
#define ITERATOR_H
using namespace std;

class Iterator{
    private:
        unsigned int integer = 0;
        unsigned int *integerPtr = &integer;

    public:
        Iterator(){}
        
        unsigned int getAndIncrementValue(){
            unsigned int previous = integer;
            return ++*integerPtr;
        }

    // Getter and setter
        unsigned int getInteger(){
            return integer;
        }
        void setInteger(unsigned int i){
            integer = i;
        }
};

#endif // ITERATOR_H