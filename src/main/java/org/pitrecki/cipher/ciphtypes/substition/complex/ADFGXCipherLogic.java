package org.pitrecki.cipher.ciphtypes.substition.complex;

import org.pitrecki.cipher.interfaces.MapValueMapper;
import org.pitrecki.cipher.interfaces.annotations.Decryption;
import org.pitrecki.cipher.interfaces.annotations.Encryption;
import org.pitrecki.cipher.utils.ColumnArrayComparator;
import org.pitrecki.cipher.utils.math.Matrix;
import org.pitrecki.cipher.utils.tools.MatrixUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Entire logic of ADFGX cipher
 *
 * @see ADFGXCipher
 *
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2017-02-21.
 */
class ADFGXCipherLogic implements MapValueMapper<Integer, Character>
{
    private final String plainText;
    private final String transpositionKey;

    private HashMap<Integer, Character> coordinatesMap;
    private ADFGXCipher adfgxCipher;

    /**
     * Create new instace of object
     *
     * @param transpositionKey entered by user
     * @param plainText entered by user
     * @param adfgxCipher instance of adfgx cipher
     */

    ADFGXCipherLogic(String transpositionKey, String plainText, ADFGXCipher adfgxCipher) {
        this.adfgxCipher = adfgxCipher;
        this.transpositionKey = transpositionKey;
        this.plainText = plainText;
        initCoordinateMap();
    }

    /**
     * Search matching element in encrypt matrix, convert it to coordinate according to
     * coordinate map.
     *
     * Example coordinate 00 equals AA and et.
     *
     * @return list filled with coordinates from encrypt matrix
     */

    @Encryption
    List<Character> searchAndFillListWithConvertedCoordinates() {
        List<Character> listWithCoordinates = new ArrayList<>();

        int iterator = 0;
        while (iterator < (plainText.length() - 1)) {
            for (int i = 0; i < adfgxCipher.getEncryptMatrix().getRow(); i++) {
                for (int j = 0; j < adfgxCipher.getEncryptMatrix().getColumn(); j++) {
                    if (adfgxCipher.getEncryptMatrix().getValue(i, j).equals(plainText.charAt(iterator))) {
                        listWithCoordinates.add(coordinatesMap.get(i));
                        listWithCoordinates.add(coordinatesMap.get(j));
                        iterator++;
                        break;
                    }
                }
            }
        }

        return listWithCoordinates;
    }

    /**
     * Create empty matrix called code table according to the dimensions and calculate
     * appropriate dimension for matrix.
     *
     * @param listWithCoordinates list which contains coordinates
     * @return code table matrix with coordinates
     */

    @Encryption
    @Decryption
    Matrix<Character> createCodeTable(List<Character> listWithCoordinates) {
        int rowSize =  listWithCoordinates.size() / (transpositionKey.length() - 1);

        Matrix<Character> codeTableMatrix = new Matrix<>(new Character[rowSize][transpositionKey.length()]);
        checkCoordListSize(listWithCoordinates, codeTableMatrix.getColumn(), rowSize);

        return codeTableMatrix;
    }

    /**
     * Fill matrix with data from the list
     *
     * @param matrix empty matrix
     * @param listWithCoordinates list which contains coordinates
     */

    @Encryption
    @Decryption
    void fillCodeTableWithData(Matrix<Character> matrix, List<Character> listWithCoordinates) {
        fillFirstRowWithTranspositionKey(matrix);
        int iterator = 0;
        for (int i = 1; i < matrix.getRow(); i++) {
            for (int j = 0; j < matrix.getColumn(); j++) {
                Character ch = listWithCoordinates.get(iterator++);
                matrix.setValueInMatrix(i, j, ch);
            }
        }
    }

    /**
     * Perform sort based on columns
     * @see ColumnArrayComparator
     *
     * @param matrix which will be sorted
     * @return sorted matrix
     */

    @Encryption
    Matrix<Character> sort(Matrix<Character> matrix) {
        Arrays.sort(matrix.getData(), new ColumnArrayComparator(0));
        return matrix.transpose();
    }

    /**
     * Change the order of rows.
     *
     * @param codeTable matrix
     * @param transpositionKey unsorted transposition key
     * @return reodered matrix
     */

    @Decryption
    Matrix<Character> sort(Matrix<Character> codeTable, String transpositionKey) {
        Matrix<Character> tmpMatrix = new Matrix<>(new Character[codeTable.getRow()][codeTable.getColumn()]);
        int iterator = 0;
        MatrixUtils matrixUtils = new MatrixUtils(codeTable);

        while (iterator < transpositionKey.length()) {
            for (int j = 0; j < codeTable.getRow(); j++) {
                if (codeTable.getValue(0, j).equals(transpositionKey.charAt(iterator))) {
                    Matrix<Character> searchedRow = matrixUtils.getColumnMatrix(j);
                    for (int k = 0; k < codeTable.getRow(); k++)
                        tmpMatrix.setValueInMatrix(k, iterator, searchedRow.getValue(k, 0));

                    iterator++;
                    break;
                }
            }
        }
        codeTable.injectFlushedData(tmpMatrix);
        return codeTable;
    }

    /**
     * Reading value from top to bottom
     *
     * @param codeTable matrix
     * @return string with listed values
     */

    @Encryption
    String readFromTopToBottom(Matrix<Character> codeTable) {
        removeFirstRow(codeTable);

        StringBuilder builder = new StringBuilder();
        MatrixUtils<Character> matrixUtils = new MatrixUtils<>(codeTable);
        for (int i = 0; i < codeTable.getColumn(); i++)
            builder.append(matrixUtils.getColumnMatrix(i).toString());

        return builder.toString();
    }

    /**
     * @return plaintext as list of characters
     */

    @Decryption
    List<Character> convertTextToList() {
        return plainText.chars()
                .mapToObj(ch -> (char) ch)
                .collect(Collectors.toList());
    }

    @Decryption
    void transposeWithoutTranspositionKey(Matrix<Character> codeTableMatrix) {
        removeFirstRow(codeTableMatrix);
        codeTableMatrix.transpose();
    }

    /**
     * Merge transposition key with code table
     *
     * @param data table matrix
     */

    @Decryption
    void mergeTranspositionWithCodeTable(Matrix<Character> data) {
        Character[][] tmpData = new Character[data.getRow() + 1][data.getColumn()];

        for (int i = 0; i < data.getColumn(); i++) {
            int rowIndexForTmpData = i + 1;
            System.arraycopy(data.getData()[i], 0, tmpData[rowIndexForTmpData], 0, data.getRow());
        }

        data.injectData(tmpData);
        fillFirstRowWithTranspositionKey(data);
    }

    /**
     * Converting encryped text in code table to their equivalent
     *
     * @param codeTableMatrix -
     * @return primitive char array with converted data
     */

    @Decryption
    char[] convertDataInCodeTableToArray(Matrix<Character> codeTableMatrix) {
        removeFirstRow(codeTableMatrix);
        int iterator = 0;
        char[] chArray = new char[codeTableMatrix.getRow() * codeTableMatrix.getColumn()];
        for (int i = 0; i < codeTableMatrix.getRow(); i++) {
            for (int j = 0; j < codeTableMatrix.getColumn(); j++) {
                try {
                    chArray[iterator] = convertValueToKey(codeTableMatrix.getValue(i, j));
                    chArray[++iterator] = convertValueToKey(codeTableMatrix.getValue(i, ++j));
                    iterator++;
                } catch (ArrayIndexOutOfBoundsException e) {}
            }
        }

        return chArray;
    }

    /**
     * Read data table line by line and convert to String
     *
     * @param codeTable with converted numbers
     * @return string with decoded text
     */

    @Decryption
    String readData(char[] codeTable) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < codeTable.length - 1; i++) {
            int rowData = Character.digit(codeTable[i], 10);
            int columnData = Character.digit(codeTable[++i], 10);
            builder.append(adfgxCipher.getEncryptMatrix().getValue(rowData, columnData));
        }

        return builder.toString();
    }

    private Character convertValueToKey(Character character) {
        String s = getKeyByValue(coordinatesMap, character).toString().replaceAll("[\\D]","");
        return s.charAt(0);
    }

    /**
     * Removing first row which contain cryptKey and create new array.
     *
     * @param codeTable 2D array to reduce first row
     */

    @Encryption
    @Decryption
    private void removeFirstRow(Matrix<Character> codeTable) {
//        Matrix<Character> tmpMatrx = new Matrix<Character>(codeTable.getRow() -1, codeTable.getColumn())
        Character[][] tmpCodeTable = new Character[codeTable.getRow() -1][ codeTable.getColumn()];
        for (int i = 1; i < codeTable.getRow(); i++) {
            tmpCodeTable[i - 1] = codeTable.getData()[i];
        }

        codeTable.injectData(tmpCodeTable);
    }

    /**
     * Check size of coordinatesMap list is same as table size.
     *
     * @param list with coordinatesMap
     * @param columnSize size of table
     * @param rowSize size of table
     */

    private void checkCoordListSize(List<Character> list, int columnSize, int rowSize) {
        int arraySize = ((rowSize - 1) * columnSize);
        if (list.size() < arraySize) {
            for (int i = 0; i < (arraySize - list.size()); i++)
                list.add('X');
        }
    }

    /**
     * Fill first row with transposition key
     *
     * @param codeTableMatrix created empty 2D Character array
     */

    private void fillFirstRowWithTranspositionKey(Matrix codeTableMatrix) {
        for (int i = 0; i < transpositionKey.length(); i++)
            codeTableMatrix.setValueInMatrix(0, i, transpositionKey.charAt(i));
    }

    private void initCoordinateMap() {
        coordinatesMap = new HashMap<>();
        coordinatesMap.put(0, 'A');
        coordinatesMap.put(1, 'D');
        coordinatesMap.put(2, 'F');
        coordinatesMap.put(3, 'G');
        coordinatesMap.put(4, 'X');
    }
}
