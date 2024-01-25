package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс, предоставляющий возможности ввода из файла / вывода в файл.
 */
public final class FileProcessor
{
    /**
     * Паттерн для проверки корректности содержимого файла.
     */
    private final static String MATCH_SEPARATE_SYMBOLS_PATTERN = "[\r\n ]+";

    /**
     * Считывание файла с данными.
     *
     * Файл должен иметь содержимое в формате "Число1 Число 2 ... ЧислоN"
     *
     * @param pathToFile Путь до файла.
     * @return Считанный массив из файла.
     */
    public static int[] readFile(String pathToFile) throws DiagnosticException
    {
        if (pathToFile.isEmpty())
        {
            throw new DiagnosticException("Path to file should not be empty");
        }

        File currentFile = new File(pathToFile);

        if (!currentFile.exists())
            throw new DiagnosticException("File does not exist by passed path: " + pathToFile);

        if (currentFile.isDirectory())
        {
            throw new DiagnosticException(
                    "There should be a file by passed path. Not a directory. Path: " + pathToFile);
        }

        // Считаем файл
        String content;
        try
        {
            content = Files.readString(currentFile.toPath());
        }
        catch (IOException e)
        {
            throw new DiagnosticException("Something went wrong while reading a file. " + e.getMessage());
        }

        if (content.isEmpty())
        {
            throw new DiagnosticException("File must not be empty");
        }

        // Пытаемся считать входные данные
        List<Integer> readNumbers = new ArrayList<>();
        String[] numbersPresentation = content.split(MATCH_SEPARATE_SYMBOLS_PATTERN);

        for (String numberPresentation : numbersPresentation)
        {
            if (numberPresentation.isEmpty())
                continue;

            try
            {
                readNumbers.add(Integer.parseInt(numberPresentation));
            }
            catch (NumberFormatException numberFormatException)
            {
                throw new DiagnosticException("Number \"" + numberPresentation + "\" is in incorrect format. " +
                        "Numbers from a range " + Integer.MIN_VALUE + " to " + Integer.MAX_VALUE + " are supported");
            }
        }

        if (readNumbers.isEmpty())
        {
            throw new DiagnosticException("File must contain at least one number!");
        }

        return readNumbers.stream().mapToInt(i->i).toArray();
    }

    /**
     * Метод записи массива в файл.
     *
     * @param pathToFile Путь, по которому необходимо записать массив в файл.
     * @param array Сам массив.
     */
    public static void writeToFile(String pathToFile, int len, int[] array, String delimiter) throws DiagnosticException
    {
        PrintWriter writer = null;
        try
        {
            writer = new PrintWriter(pathToFile);
        }
        catch (FileNotFoundException e)
        {
            throw new DiagnosticException("Can not write to file. Reason: " + e.getMessage());
        }

        String content = "";

        if (len != -1)
            content = len + "\n";

        content += Arrays.stream(array)
                .mapToObj(Long::toString)
                .collect(Collectors.joining(delimiter));

        writer.print(content);
        writer.close();
    }
}