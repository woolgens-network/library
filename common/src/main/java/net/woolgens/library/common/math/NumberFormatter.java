package net.woolgens.library.common.math;

public final class NumberFormatter {

    /**
     * Check if text is a number
     *
     * @param text
     * @return
     */
    public static boolean isNumber(String text) {
        try {
            Integer.valueOf(text);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * Format number value to thousands
     * Example: 1,000
     *
     * @param value
     * @return
     */
    public static String formatNumberToThousands(long value) {
        return String.format("%,d", new Object[]{Long.valueOf(value)});
    }

    /**
     * Format decimal value to thousands
     *
     * @param value
     * @return
     */
    public static String formatDoubleToThousands(double value) {
        return formatNumberToThousands((long)value);
    }
}
