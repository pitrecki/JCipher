# Implementation of classical ciphers in Java

### Description

Implementation of classical ciphers written in Java.
The project aims to familiarize with the principle of action ciphers in the classical cryptography.

### Implemented ciphers

1. Autokey
2. Hill
3. Running key
4. Vinegere
5. Baconian
6. Bifid
7. Polybius square
8. Affine
9. Atbash
10. Caesar
11. Rail-fence



### How to use

Basics
```java
Cipher caeser = new CaeserCipher();
caeser.encrypt("THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG");
String cipherText = caeser.getProcessedText();
String statistics = caeser.frequencyTextAnalysis(cipherText);
String allInfoAbout = caeser.toString();
```
Fast way to print encrypted/decrypted text
```java
caeser.printProccesedData();
```

Open file
```java
FileOperator fo = new FileOperator();
fo.open("C:/plaintext");
caeser.encrypt(fo.getText());
cipherText = caeser.getProcessedText();
```

Save to file (same path, see above)
```java
fo.save(cipherText);
```

Or like this
```java
fo.save(Paths.get("C:/MY_SECRET_FOLDER/"), cipherText);
```


### Resources and other usefull things

[Books] (BOOKS)

### Thanks to
- Creators of JAMA : A Java Matrix Package
- The Alphanum Algorithm made by Daniel Migowski, Andre Bogus and David Koelle


