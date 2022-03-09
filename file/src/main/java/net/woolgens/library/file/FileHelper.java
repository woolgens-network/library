package net.woolgens.library.file;

import java.io.*;

public final class FileHelper {

    /**
     * Delete sub files then main directory
     *
     * @param directory
     * @return
     */
    public static boolean deleteDirectory(File directory) {
        if(directory.isDirectory()) {
            for(File file : directory.listFiles()) {
                deleteDirectory(file);
            }
        }
        return directory.delete();
    }

    /**
     * Retrieve file from resources folder and display it
     *
     * @param clazz
     * @param fileName
     * @return
     */
    public static StringBuilder buildAsciiText(Class<?> clazz, String fileName)  {
        InputStream is = clazz.getClassLoader().getResourceAsStream(fileName.replace("/", File.separator));
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder builder = new StringBuilder();
        try {
            while (reader.ready()) {
                builder.append(reader.readLine()).append("\n");
            }
            reader.close();
            is.close();
        }catch (Exception exception) {
            exception.printStackTrace();
        }
        return builder;
    }


    public static String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
}
