# Implementation of classical ciphers in Java

## EN SECTION

######  Requirements

- JAVA 8 JDK
- JAMA : A Java Matrix Package

######  Description

Implementation of classical ciphers written in Java.
The project aims to familiarize with the principle of action ciphers in the classical cryptography.

UML diagram soon

######  How to use

Basics
```java
Cipher ceaser = new CeasarCipher();
ceaser.encrypt("THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG");
String cipherText = ceaser.getProcessedText();
String statistics = ceaser.frequencyTextAnalysis(cipherText);
String allInfoAbout = ceaser.toString();
```
Fast way to print encrypted/decrypted text
```java
ceaser.printProccesedData();
```

Open file
```java
FileOperator fo = new FileOperator();
fo.open("C:/plaintext");;
ceaser.encrypt(fo.getText());
cipherText = ceaser.getProcessedText();
```

Save to file (same path, see above)
```java
fo.save(cipherText);
```

Or like this
```java
fo.save(Paths.get("C:/MY_SECRET_FOLDER/"), cipherText);
```


######  Resources

1. Kenneth H. Rosen - _Elementary Number Theory and Its Applications_, Addison Wesley
2. Simon Singh - _The Code Book: The Science of Secrecy from Ancient Egypt to Quantum Cryptography_, Anchor
3. Helen F. Gaines -  _Cryptanalysis: A Study of Ciphers and Their Solution_, Dover Publications
4. Bud Johnson - _Break the Code: Cryptography for Beginners_, Dover Publications
5. Bruce Schneider - _Applied Cryptography Protocols, Algorithms, and Source Code in C_, John Wiley & Sons
6. Douglas R. Stinson - _Cryptography: Theory and Practice_, Chapman and Hall/CRC
7. Wikipedia and practicalcryptography.com

## PL SECTION

######  Krotki opis

Implementacja klasycznych szyfrów napisana w Javie.
Projekt ma na celu zaznajomienie z zasadą działania szyfrów w klasycznej kryptografii.

Digram UML wkrótce...


