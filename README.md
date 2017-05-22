# pig-latin-translator

### Description
This application translates an english input string (word,sentence, or paragraph) into “pig-latin”. 
Input string can be passed as application argument or can be read from file. 
Translation preserves relative punctuation position. 
As punctuation characters are considered [.?!,:;"”“„`’'] defaultly. In case, that you want to use own specific punctuation characters then use parameter -p.

### Prerequisites
 >- Maven 3 
 >- Java 8 (Jdk)

Tested on Ubuntu 64bit, OpenJdk 1.8, Maven 3.3.9.


#### How to build

```
mvn package -DskipTests
```

#### How to run tests

```
mvn test
```

#### How to run application

For example:

```
java -jar translator-1.0.jar -t "Hello anna-frankway."
```

Will return "Ellohay annaway-frankway."

### Supported application's arguments
```
 -f,--file <file>               Reads <file> and translates it into
                                pig-latin. This option can't be used with
                                option [-t] simultaneously.
 -h,--help                      Prints help.
 -o,--output <file>             Write translated text into <file>.
 -p,--punctuation <eg. ".,:">   As punctuation characters are considered
                                [.?!,:;"”“„`’'] defaultly. In case, that
                                you want to use own specific punctuation
                                characters then use argument [-p]. For
                                example if you run application with
                                argument -p="^:&" then characters: [^:&]
                                will be consider as punctuation.
 -t,--text <arg>                Translates <arg> into pig-latin. This
                                option can't be used with option [-f]
                                simultaneously.                                
```

### Features
 >- Text for translation can be pass as application argument or via file
 >- Output file can be created
 >- Own punctuation characters can be set by argument
 
### Notes
 >- Only english vowels are considered.
 >- Only english consonants are considered.
 >- Punctuation in word itself (eg. "can't") is considered as word's character and then Capitalization Rule is also applied on those chars (eg. "end.E" -> "endeWa.y"). 
    Punctuation chars are treated as lower case chars and cannot be transfer to upper case (eg. "b'bBbB" ->"bbb'bBay")
 >- Punctuation at word begin does not effect translation (eg. ".Hello" -> ".Ellohay")
 >- All White spaces between words are replaced by one space. (eg. ".Hello   a" -> ".Ellohay away")
 >- Words with first char which is: non-letter, non-punctuation or a number are not translated (eg. "1Hello" -> "1Hello" or "$Hello" -> "$Hello")