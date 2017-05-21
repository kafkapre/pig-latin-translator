# pig-latin-translator

### Description
This application translates an english input string (word,sentence, or paragraph) into “pig-latin”. 
Input string can be passed as application argument or can be read from file. 
Translation preserves relative punctuation position. 
As punctuation characters are considered [.?!,:;"”“„`’'] defaultly. In case, that you want to use own specific punctuation characters then use parameter -p.

#### How to build

```
mvn package -DskipTests
```

#### How to run tests

```
mvn test
```

#### How to run application

```
java -jar translator.jar -t "Hello anna-frankway."
```

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