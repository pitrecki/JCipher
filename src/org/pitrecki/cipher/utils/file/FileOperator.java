package org.pitrecki.cipher.utils.file;


import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class providing common opearating functionality on text files.
 *
 * @author Piotr 'pitrecki' Nowak
 * @version 0.5.8
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
     * Open file, which contains text to decode/encode
     * @param path file path
     */

    public void open(Path path) {
        filePath = path.getParent();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toString()))){
            if (!bufferedReader.ready())
                throw new CheckFileException("File is empty!");

            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null)
                builder.append(line);

            text = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * See {@link #open(Path)}
     * File path as String (for lazy people :)).
     * @param path
     */

    public void open(String path) {
        open(Paths.get(path));
    }

    /**
     * Save decode/encode to file, be aware you must specify only absolute path. It generate output file called
     * * 'output'
     * @param path file path
     * @param text to save
     */

    public void save(Path path, String text) {
        String sPath = path != null ? path.toUri().getPath() : filePath.toAbsolutePath().toString();
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
     * Use pre-specified path, only if you previously used {@link #open(Path)} method
     * @param str to save
     */

    public void save(String str) {
        save(null, str);
    }


}
