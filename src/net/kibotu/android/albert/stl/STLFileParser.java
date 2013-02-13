package net.kibotu.android.albert.stl;

import android.util.Log;
import com.badlogic.gdx.math.Vector3;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: Apu
 * Date: 12.02.13
 * Time: 14:36
 * To change this template use File | Settings | File Templates.
 */
public class STLFileParser {

    public static String TAG = STLFileParser.class.getSimpleName();

//    public STLFileObject parseWithStringSplitting(File stlFile) {
//
//        long before = System.currentTimeMillis();
//
//        if(!stlFile.exists()) {
//            Log.d(TAG, "loadFilesFromDirectory - " + stlFile.getName() + " not existing");
//            return new STLFileObject();
//        }
//
//        if(!stlFile.isFile()) {
//            Log.d(TAG, "loadFilesFromDirectory - " + stlFile.getName() + " is no file");
//            return new STLFileObject();
//        }
//
//        Log.d(TAG, "loadSingleFile - loading STL-File " + stlFile.getName());
//
//        String name = null;
//        float[] vertices = null;
//        float[] normals = null;
//        int numberOfFacets = 0;
//
//        int lineCounter = 0;
//
//        try {
//            FileInputStream fileInputStream = new FileInputStream(stlFile);
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
//
//            while(bufferedReader.readLine() != null) {
//                lineCounter++;
//            }
//
//            bufferedReader.close();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//
//        vertices = new float[(lineCounter - 2) / 7 * 9];
//        int vertexValuesCounter = 0;
//        normals = new float[(lineCounter - 2) / 7 * 3];
//        int normalValuesCounter = 0;
//
//        try{
//            FileInputStream fileInputStream = new FileInputStream(stlFile);
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
//
//            String currentLine = "";
//            currentLine = bufferedReader.readLine();
//
//            /*Choose the first line to support stl files with leading spaces.
//            Remember the following lines like this. Maybe slower.*/
////            String[] words = currentLine.replaceFirst("\\s+", "").split("\\s+");
//            String[] words = currentLine.split("\\s+");
//
//            if(words.length > 2 || words.length < 1) {
//                Log.d(TAG, "loadSingleFile - STL-File " + stlFile.getName() + " not valid.");
//                return new STLFileObject();
//            }
//
//            if(!words[0].equals("solid")) {
//                Log.d(TAG, "loadSingleFile - STL-File " + stlFile.getName() + " not valid. " +
//                        "\"solid\" expected.");
//                return new STLFileObject();
//            }
//
//            if(words.length == 2) {
//                name = words[1];
//            } else {
//                name = "";
//            }
//
//            int lineNumber = 1;
//            while((currentLine = bufferedReader.readLine())!= null) {
//                lineNumber++;
//
//                // choose the first line to support stl files with leading spaces
////                words = currentLine.replaceFirst("\\s+", "").split("\\s+");
//                words = currentLine.split("\\s+");
//
//                if (words.length > 0 && words[0].equals("endsolid")) {
//                    break;
//                }
//                if (words.length != 5) {
//                    Log.d(TAG, "loadSingleFile - STL-File " + stlFile.getName() + " not valid. " +
//                            "Unexpected value count in line " + lineNumber + ". 5 values expected, " +
//                            words.length + " found.");
//                    return new STLFileObject();
//                }
//                if (!words[0].equals("facet") || !words[1].equals("normal")) {
//                    Log.d(TAG, "loadSingleFile - STL-File " + stlFile.getName() + " not valid. " +
//                            "\"facet normal\" expected in line " + lineNumber);
//                    return new STLFileObject();
//                }
//
//                normals[normalValuesCounter] = Float.parseFloat(words[2]);
//                normalValuesCounter++;
//                normals[normalValuesCounter] = Float.parseFloat(words[3]);
//                normalValuesCounter++;
//                normals[normalValuesCounter] = Float.parseFloat(words[4]);
//                normalValuesCounter++;
//
//                currentLine = bufferedReader.readLine();
//                lineNumber++;
//
//                if(currentLine == null) {
//                    Log.d(TAG, "loadSingleFile - STL-File " + stlFile.getName() + " not valid. " +
//                            "missing lines after " + (lineNumber-1));
//                    return new STLFileObject();
//                }
//
//                // choose the first line to support stl files with leading spaces
////                words = currentLine.replaceFirst("\\s+", "").split("\\s+");
//                words = currentLine.split("\\s+");
//
//                if (words.length != 2) {
//                    Log.d(TAG, "loadSingleFile - STL-File " + stlFile.getName() + " not valid. " +
//                            "Unexpected value count in line " + lineNumber + ". 2 values expected, " +
//                            words.length + " found.");
//                    return new STLFileObject();
//                }
//                if (!words[0].equals("outer") || !words[1].equals("loop")) {
//                    Log.d(TAG, "loadSingleFile - STL-File " + stlFile.getName() + " not valid. " +
//                            "\"outer loop\" expected in line " + lineNumber);
//                    return new STLFileObject();
//                }
//
//                currentLine = bufferedReader.readLine();
//                lineNumber++;
//
//                if(currentLine == null) {
//                    Log.d(TAG, "loadSingleFile - STL-File " + stlFile.getName() + " not valid. " +
//                            "missing lines after " + (lineNumber-1));
//                    return new STLFileObject();
//                }
//
//                // choose the first line to support stl files with leading spaces
////                words = currentLine.replaceFirst("\\s+", "").split("\\s+");
//                words = currentLine.split("\\s+");
//
//                if (words.length != 4) {
//                    Log.d(TAG, "loadSingleFile - STL-File " + stlFile.getName() + " not valid. " +
//                            "Unexpected value count in line " + lineNumber + ". 4 values expected, " +
//                            words.length + " found.");
//                    return new STLFileObject();
//                }
//                if (!words[0].equals("vertex")) {
//                    Log.d(TAG, "loadSingleFile - STL-File " + stlFile.getName() + " not valid. " +
//                            "\"vertex\" expected in line " + lineNumber);
//                    return new STLFileObject();
//                }
//
//                vertices[vertexValuesCounter] = Float.parseFloat(words[1]);
//                vertexValuesCounter++;
//                vertices[vertexValuesCounter] = Float.parseFloat(words[2]);
//                vertexValuesCounter++;
//                vertices[vertexValuesCounter] = Float.parseFloat(words[3]);
//                vertexValuesCounter++;
//
//                currentLine = bufferedReader.readLine();
//                lineNumber++;
//
//                if(currentLine == null) {
//                    Log.d(TAG, "loadSingleFile - STL-File " + stlFile.getName() + " not valid. " +
//                            "missing lines after " + (lineNumber-1));
//                    return new STLFileObject();
//                }
//
//                // choose the first line to support stl files with leading spaces
////                words = currentLine.replaceFirst("\\s+", "").split("\\s+");
//                words = currentLine.split("\\s+");
//
//                if (words.length != 4) {
//                    Log.d(TAG, "loadSingleFile - STL-File " + stlFile.getName() + " not valid. " +
//                            "Unexpected value count in line " + lineNumber + ". 4 values expected, " +
//                            words.length + " found.");
//                    return new STLFileObject();
//                }
//                if (!words[0].equals("vertex")) {
//                    Log.d(TAG, "loadSingleFile - STL-File " + stlFile.getName() + " not valid. " +
//                            "\"vertex\" expected in line " + lineNumber);
//                    return new STLFileObject();
//                }
//
//                vertices[vertexValuesCounter] = Float.parseFloat(words[1]);
//                vertexValuesCounter++;
//                vertices[vertexValuesCounter] = Float.parseFloat(words[2]);
//                vertexValuesCounter++;
//                vertices[vertexValuesCounter] = Float.parseFloat(words[3]);
//                vertexValuesCounter++;
//
//                currentLine = bufferedReader.readLine();
//                lineNumber++;
//
//                if(currentLine == null) {
//                    Log.d(TAG, "loadSingleFile - STL-File " + stlFile.getName() + " not valid. " +
//                            "missing lines after " + (lineNumber-1));
//                    return new STLFileObject();
//                }
//
//                // choose the first line to support stl files with leading spaces
////                words = currentLine.replaceFirst("\\s+", "").split("\\s+");
//                words = currentLine.split("\\s+");
//
//                if (words.length != 4) {
//                    Log.d(TAG, "loadSingleFile - STL-File " + stlFile.getName() + " not valid. " +
//                            "Unexpected value count in line " + lineNumber + ". 4 values expected, " +
//                            words.length + " found.");
//                    return new STLFileObject();
//                }
//                if (!words[0].equals("vertex")) {
//                    Log.d(TAG, "loadSingleFile - STL-File " + stlFile.getName() + " not valid. " +
//                            "\"vertex\" expected in line " + lineNumber);
//                    return new STLFileObject();
//                }
//
//                vertices[vertexValuesCounter] = Float.parseFloat(words[1]);
//                vertexValuesCounter++;
//                vertices[vertexValuesCounter] = Float.parseFloat(words[2]);
//                vertexValuesCounter++;
//                vertices[vertexValuesCounter] = Float.parseFloat(words[3]);
//                vertexValuesCounter++;
//
//                currentLine = bufferedReader.readLine();
//                lineNumber++;
//
//                if(currentLine == null) {
//                    Log.d(TAG, "loadSingleFile - STL-File " + stlFile.getName() + " not valid. " +
//                            "missing lines after " + (lineNumber-1));
//                    return new STLFileObject();
//                }
//
//                // choose the first line to support stl files with leading spaces
////                words = currentLine.replaceFirst("\\s+", "").split("\\s+");
//                words = currentLine.split("\\s+");
//
//                if (words.length != 1) {
//                    Log.d(TAG, "loadSingleFile - STL-File " + stlFile.getName() + " not valid. " +
//                            "Unexpected value count in line " + lineNumber + ". 1 value expected, " +
//                            words.length + " found.");
//                    return new STLFileObject();
//                }
//                if (!words[0].equals("endloop")) {
//                    Log.d(TAG, "loadSingleFile - STL-File " + stlFile.getName() + " not valid. " +
//                            "\"endloop\" expected in line " + lineNumber);
//                    return new STLFileObject();
//                }
//
//                currentLine = bufferedReader.readLine();
//                lineNumber++;
//
//                if(currentLine == null) {
//                    Log.d(TAG, "loadSingleFile - STL-File " + stlFile.getName() + " not valid. " +
//                            "missing lines after " + (lineNumber-1));
//                    return new STLFileObject();
//                }
//
//                // choose the first line to support stl files with leading spaces
////                words = currentLine.replaceFirst("\\s+", "").split("\\s+");
//                words = currentLine.split("\\s+");
//
//                if (words.length != 1) {
//                    Log.d(TAG, "loadSingleFile - STL-File " + stlFile.getName() + " not valid. " +
//                            "Unexpected value count in line " + lineNumber + ". 1 value expected, " +
//                            words.length + " found.");
//                    return new STLFileObject();
//                }
//                if (!words[0].equals("endfacet")) {
//                    Log.d(TAG, "loadSingleFile - STL-File " + stlFile.getName() + " not valid. " +
//                            "\"endfacet\" expected in line " + lineNumber);
//                    return new STLFileObject();
//                }
//
//                numberOfFacets++;
//            }
//
//            bufferedReader.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return new STLFileObject();
//        } catch (IOException e){
//            e.printStackTrace();
//            return new STLFileObject();
//        }
//
//        if (vertices == null) {
//            Log.d(TAG, "loadSingleFile - STL-File " + stlFile.getName() + " not valid. ");
//            return new STLFileObject();
//        }
//
//        long after = System.currentTimeMillis();
//        double runningTime = (after - before) / 1000.0;
//
//        Log.d(TAG, "loadSingleFile - STL-File " + stlFile.getName() + " loaded. " +
//                numberOfFacets + " facets loaded in " + runningTime + " s.");
//
//        return new STLFileObject(name, vertices, normals);
//
//    }

    public STLFileObject parseFast(File stlFile) {

        long before = System.currentTimeMillis();

        if(!stlFile.exists()) {
            Log.d(TAG, "loadFilesFromDirectory - " + stlFile.getName() + " not existing");
            return new STLFileObject();
        }

        if(!stlFile.isFile()) {
            Log.d(TAG, "loadFilesFromDirectory - " + stlFile.getName() + " is no file");
            return new STLFileObject();
        }

        Log.d(TAG, "loadSingleFile - loading STL-File " + stlFile.getName());

        String name = null;
        float[] vertices = null;

        int numberOfFacets = 0;

        int lineCounter = 0;

        try {
            FileInputStream fileInputStream = new FileInputStream(stlFile);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            while(bufferedReader.readLine() != null) {
                lineCounter++;
            }

            bufferedReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        vertices = new float[(lineCounter - 2) / 7 * 9 *2];
        int vertexValuesCounter = 0;

        try{
            FileInputStream fileInputStream = new FileInputStream(stlFile);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            String currentLine = "";
            currentLine = bufferedReader.readLine();

            /*Choose the first line to support stl files with leading spaces.
            Remember the following lines like this. Maybe slower.*/
//            String[] words = currentLine.replaceFirst("\\s+", "").split("\\s+");
            String[] words = currentLine.split("\\s+");

            if(words.length > 2 || words.length < 1) {
                Log.d(TAG, "loadSingleFile - STL-File " + stlFile.getName() + " not valid.");
                return new STLFileObject();
            }

            if(!words[0].equals("solid")) {
                Log.d(TAG, "loadSingleFile - STL-File " + stlFile.getName() + " not valid. " +
                        "\"solid\" expected.");
                return new STLFileObject();
            }

            if(words.length == 2) {
                name = words[1];
            } else {
                name = "";
            }

            char[] n1 = new char[14];
            char[] n2 = new char[14];
            char[] n3 = new char[14];

            char[] v1 = new char[14];
            char[] v2 = new char[14];
            char[] v3 = new char[14];

            int lineNumber = 1;
            while(lineNumber+1 < lineCounter) {
                currentLine = bufferedReader.readLine();
                lineNumber++;

                currentLine.getChars(13, 27, n1, 0);
                currentLine.getChars(28, 42, n2, 0);
                currentLine.getChars(43, 57, n3, 0);

                float n1Float = Float.parseFloat(String.valueOf(n1));
                float n2Float = Float.parseFloat(String.valueOf(n2));
                float n3Float = Float.parseFloat(String.valueOf(n3));

                bufferedReader.readLine();
                lineNumber++;
                currentLine = bufferedReader.readLine();
                lineNumber++;

                currentLine.getChars(7, 21, v1, 0);
                currentLine.getChars(22, 36, v2, 0);
                currentLine.getChars(37, 51, v3, 0);

                vertices[vertexValuesCounter] = Float.parseFloat(String.valueOf(v1));
                vertexValuesCounter++;
                vertices[vertexValuesCounter] = Float.parseFloat(String.valueOf(v2));
                vertexValuesCounter++;
                vertices[vertexValuesCounter] = Float.parseFloat(String.valueOf(v3));
                vertexValuesCounter++;

                vertices[vertexValuesCounter] = n1Float;
                vertexValuesCounter++;
                vertices[vertexValuesCounter] = n2Float;
                vertexValuesCounter++;
                vertices[vertexValuesCounter] = n3Float;
                vertexValuesCounter++;

                currentLine = bufferedReader.readLine();
                lineNumber++;

                currentLine.getChars(7, 21, v1, 0);
                currentLine.getChars(22, 36, v2, 0);
                currentLine.getChars(37, 51, v3, 0);

                vertices[vertexValuesCounter] = Float.parseFloat(String.valueOf(v1));
                vertexValuesCounter++;
                vertices[vertexValuesCounter] = Float.parseFloat(String.valueOf(v2));
                vertexValuesCounter++;
                vertices[vertexValuesCounter] = Float.parseFloat(String.valueOf(v3));
                vertexValuesCounter++;

                vertices[vertexValuesCounter] = n1Float;
                vertexValuesCounter++;
                vertices[vertexValuesCounter] = n2Float;
                vertexValuesCounter++;
                vertices[vertexValuesCounter] = n3Float;
                vertexValuesCounter++;

                currentLine = bufferedReader.readLine();
                lineNumber++;

                currentLine.getChars(7, 21, v1, 0);
                currentLine.getChars(22, 36, v2, 0);
                currentLine.getChars(37, 51, v3, 0);

                vertices[vertexValuesCounter] = Float.parseFloat(String.valueOf(v1));
                vertexValuesCounter++;
                vertices[vertexValuesCounter] = Float.parseFloat(String.valueOf(v2));
                vertexValuesCounter++;
                vertices[vertexValuesCounter] = Float.parseFloat(String.valueOf(v3));
                vertexValuesCounter++;

                vertices[vertexValuesCounter] = n1Float;
                vertexValuesCounter++;
                vertices[vertexValuesCounter] = n2Float;
                vertexValuesCounter++;
                vertices[vertexValuesCounter] = n3Float;
                vertexValuesCounter++;

                bufferedReader.readLine();
                lineNumber++;

                bufferedReader.readLine();
                lineNumber++;

                numberOfFacets++;
            }

            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new STLFileObject();
        } catch (IOException e){
            e.printStackTrace();
            return new STLFileObject();
        }

        if (vertices == null) {
            Log.d(TAG, "loadSingleFile - STL-File " + stlFile.getName() + " not valid. ");
            return new STLFileObject();
        }

        long after = System.currentTimeMillis();
        double runningTime = (after - before) / 1000.0;

        Log.d(TAG, "loadSingleFile - STL-File " + stlFile.getName() + " loaded. " +
                numberOfFacets + " facets loaded in " + runningTime + " s.");

        return new STLFileObject(name, vertices);

    }

}