package org.cipher.utils.file;


import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    private final String OUTPUT_FILE_NAME = "output";

    private String text;
    private Path filePath;

    public FileOperator() {
    }

    public String getText() {
        return text;
    }

    /**
     *
     * @param path
     */

    public void open(Path path) {
        this.filePath = path.getParent();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toString()))){
            if (!bufferedReader.ready())
                throw new CheckFileException("File is empty!");

            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null)
                builder.append(line);

            this.text = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param path
     */

    public void open(String path) {
        open(Paths.get(path));
    }

    /**
     *
     * @param path
     * @param text
     */

    public void save(Path path, String text) {
        String sPath = path != null ? path.toUri().getPath() : this.filePath.toUri().getPath();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(sPath + OUTPUT_FILE_NAME))){
            if (text.contains("\n") || text.contains("\r")) {
                String[] lineSeparatedArray = text.split("[\n\r]");
                for (String word : lineSeparatedArray) {
                    bufferedWriter.write(word);
                    bufferedWriter.newLine();
                }
            }
            else
                bufferedWriter.write(text);

            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param str
     */

    public void save(String str) {
        save(null, str);
    }


}
