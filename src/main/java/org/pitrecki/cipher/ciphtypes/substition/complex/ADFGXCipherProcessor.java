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
class ADFGXCipherProcessor implements MapValueMapper<Integer, Character>
{
    private final String plainText;
    private final String transpositionKey;

    private HashMap<Integer, Character> coordinatesMap;
    private ADFGXCipher adfgxCipher;


    /**
     * @param transpositionKey
     * @param plainText
     * @param adfgxCipher
     */

    public ADFGXCipherProcessor(String transpositionKey, String plainText, ADFGXCipher adfgxCipher) {
        this.adfgxCipher = adfgxCipher;
        this.transpositionKey = transpositionKey;
        this.plainText = plainText;
        initCoordinateMap();
    }

    /**
     * @return
     */

    @Encryption
    public List<Character> searchAndFillListWithConvertedCoordinates() {
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
     * @param listWithCoordinates
     * @return
     */

    @Encryption
    @Decryption
    public Character[][] createTwoDimArrayOfCharacters(List<Character> listWithCoordinates) {
        int rowSize =  listWithCoordinates.size() / (transpositionKey.length() - 1);
        Character[][] characters = new Character[rowSize][transpositionKey.length()];
        checkCoordListSize(listWithCoordinates, characters[0].length, rowSize);

        return characters;
    }

    /**
     * @param codeTable
     * @param listWithCoordinates
     * @return
     */

    public Character[][] fillArray(Character[][] codeTable, List<Character> listWithCoordinates) {
        fillFirstRowWithTranspositionKey(codeTable);
        int iterator = 0;
        for (int i = 1; i < codeTable.length; i++) {
            for (int j = 0; j < codeTable[0].length; j++) {
                codeTable[i][j] = listWithCoordinates.get(iterator);
                iterator++;
            }
        }

        return codeTable;
    }

    /**
     * @param codeTable
     * @return
     */

    @Encryption
    public Character[][] sort(Character[][] codeTable) {
        Matrix<Character> matrix = new Matrix<>(codeTable).transpose();

        codeTable = matrix.getData();
        Arrays.sort(codeTable, new ColumnArrayComparator(0));
        matrix.setData(codeTable);

        return (Character[][]) matrix.transpose().getData();
    }

    /**
     *
     * @param codeTable
     * @param transpositionKey
     * @return
     */

    //todo FIX zmienic implementację
    @Decryption
    public Character[][] sort(Character[][] codeTable, String transpositionKey) {
        Character[][] tmpArray = new Character[codeTable.length][codeTable[0].length];
        int iterator = 0;
        MatrixUtils matrixUtils = new MatrixUtils(codeTable);
        while (iterator < codeTable[0].length) {
            for (int j = 0; j < codeTable[0].length; j++) {
                if (codeTable[0][j] == transpositionKey.charAt(iterator)) {
//                        tmpArray[iterator] = (Character[]) matrixUtils.getColumnAsArray(j);
                    Character[] characters = (Character[]) matrixUtils.getColumnAsArray(j);
                    for (int k = 0; k < characters.length; k++) {
                        tmpArray[k][iterator] = characters[k];
                    }
                    iterator++;
                    break;
                }
            }
        }
        return tmpArray;
    }

    /**
     * @param codeTable
     * @return
     */

    @Encryption
    public String readFromTopToBottom(Character[][] codeTable) {
        codeTable = removeFirstRow(codeTable);

        StringBuilder builder = new StringBuilder();
        MatrixUtils<Character> matrixUtils = new MatrixUtils<>(codeTable);
        for (int i = 0; i < codeTable[0].length; i++)
            builder.append(Arrays.toString(matrixUtils.getColumnAsArray(i)));

        return builder.toString();
    }

    /**
     * @return
     */

    @Decryption
    public List<Character> convertTextToList() {
        return plainText.chars()
                .mapToObj(ch -> (char) ch)
                .collect(Collectors.toList());
    }

    @Decryption
    public Character[][] transpose(Character[][] codeTable) {
        codeTable = removeFirstRow(codeTable);
        return (Character[][]) new Matrix<>(codeTable).transpose().getData();
    }

    /**
     *
     * @param data
     * @return
     */

    @Decryption
    public Character[][] mergeTranspositionWithCodeTable(Character[][] data) {
        Character[][] tmpData = new Character[data.length + 1][data[0].length];
        for (int i = 0; i < tmpData[0].length; i++)
            tmpData[0][i] = transpositionKey.charAt(i);

        for (int i = 0; i < data.length; i++) {
            int rowIndexForTmpData = i + 1;
            System.arraycopy(data[i], 0, tmpData[rowIndexForTmpData], 0, data.length);
        }
        return tmpData;
    }

    /**
     *
     * @param codeTable
     * @return
     */

    @Decryption
    public Character[] convertDataInCodeTable(Character[][] codeTable) {
        codeTable = removeFirstRow(codeTable);
        int iterator = 0;
        Character[] chArray = new Character[codeTable.length * codeTable[0].length];
        for (int i = 0; i < codeTable.length; i++) {
            for (int j = 0; j < codeTable[i].length; j++) {
                try {
                    chArray[iterator] = convertValueToKey(codeTable[i][j]);
                    chArray[++iterator] = convertValueToKey(codeTable[i][++j]);
                    iterator++;
                } catch (ArrayIndexOutOfBoundsException e) {}
            }
        }

        return chArray;
    }

    /**
     * @param codeTable
     * @return
     */

    @Decryption
    public String readData(Character[] codeTable) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < codeTable.length - 1; i++) {
            int rowData = Character.digit(codeTable[i], 10);
            int columnData = Character.digit(codeTable[++i], 10);
            if (codeTable[i] != null)
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
     * @return new 2d array (code table) of characters with reduced size and new contents
     */

    @Encryption
    @Decryption
    private Character[][] removeFirstRow(Character[][] codeTable) {
        Character[][] tmpCodeTable = new Character[codeTable.length - 1][codeTable[0].length];
        for (int i = 1; i < codeTable.length; i++) {
            tmpCodeTable[i - 1] = codeTable[i];
        }

        return tmpCodeTable;
    }

    /**
     * Check size of coordinatesMap list is same as table size
     *
     * @param list with coordinatesMap
     * @param columnSize size of table
     * @param rowSize size of table
     */

    private void checkCoordListSize(List<Character> list, int columnSize, int rowSize) {
        int size = ((rowSize - 1) * columnSize);
        if (list.size() < size) {
            for (int i = 0; i < (size - list.size()); i++)
                list.add('X');
        }
    }

    /**
     * Fill first row with transposition key
     *
     * @param codeTable created empty 2D Character array
     */

    private void fillFirstRowWithTranspositionKey(Character[][] codeTable) {
        for (int i = 0; i < transpositionKey.length(); i++)
            codeTable[0][i] = transpositionKey.charAt(i);
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
