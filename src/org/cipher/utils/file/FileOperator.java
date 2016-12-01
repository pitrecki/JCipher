package org.cipher.utils.file;

import java.io.*;
import java.nio.file.Path;

/**
 * This class providing common opearating functionality on text files.
 *
 * @author Piotr 'pitrecki' Nowak
 * @version 0.0.1
 * Created by Pitrecki on 2016-10-22.
 * @see java.io.File
 * @see java.io.BufferedWriter
 * @see java.io.BufferedReader
 */
public final class FileOperator
{
    //TODO write, open, close read methods

    private final String INPUT_FILE = "input";
    private final String OUTPUT_FILE = "output";

    private File file;
    private String text;

    public FileOperator(File file) {
        this.file = file;
        this.text = "";
    }

    public FileOperator(String path) {
        this.file = new File(path);
        this.text = "";
    }

    public String getText() {
        return text;
    }

    public void open() throws IOException {
        if (!file.exists())
            this.file.createNewFile();

        String line;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        if (!bufferedReader.ready())
            throw new EmptyFileException("Input file is empty");
        else {
            while ((line = bufferedReader.readLine()) != null)
                append(line);
        }

        bufferedReader.close();
    }

    /**
     * Apepend text by line parsed from file
     * @param str   string line parameter
     * @throws EmptyFileException   only when file is empty
     */
    private void append(String str) {
        if (getText().length() == 0)
            this.text = str;
        else {
            this.text = getText().concat(str);
        }

    }

    public void save(String text) throws IOException{
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(OUTPUT_FILE + ".txt"));
        bufferedWriter.write(text);
        bufferedWriter.flush();
        bufferedWriter.close();
    }

}
