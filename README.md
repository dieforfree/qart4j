## QArt4J ##
A QR code generator which gives ASCII Art output image.  
A java version of [QArt](http://research.swtch.com/qart) with enhancement.  
All credit goes to Russ Cox.

Sample output:  
![Sample Output](sample-output.png)

## How to generate a runnable jar ##
QArt4j is a maven project, run the following command to get a ruunable jar:

```Bash
mvn compile assembly:single
```

This will generate a runnable jar under `target/` directory.

## How to run it ##
QArt4j can run with many parameters, `java -jar qart4j.jar --help` will show all the parameters.
The parameters which give the sample output are:
```Bash
java -jar qart4j.jar \
-i sample-input.png \
-o sample-output.png \
-u http://www.imdb.com/title/tt2267968/ \
-w 324 -h 480  \
--mr 72 --mb 164 \
-z 168 -v 16 -q 1 \
--cw EFFFFFFF
```

## Known issues ##
1. JPEG now works, thanks to [Abhinash Khanal](https://github.com/khanal-abhi).

## License ##
[GPLv3](LICENSE.txt)
