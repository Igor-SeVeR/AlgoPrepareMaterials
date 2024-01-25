package org.example;

/**
 * Исключение, содержащее диагностику на какое-либо некорректное поведение.
 */
public class DiagnosticException extends RuntimeException
{
    public DiagnosticException(String exceptionMessage)
    {
        super(exceptionMessage);
    }
}