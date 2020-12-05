package me.wobblyyyy.pathfinder.recording.data;

import java.io.*;

public class PfRwNormal implements RwData {
    @Override
    public String read(String path, String file) {
        String fileName = path + file;

        StringBuilder output = new StringBuilder();
        String line = null;

        try {
            FileReader fileReader = new FileReader(fileName);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                output.append(line);
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output.toString();
    }

    @Override
    public void save(String path, String file, String data) {
        String fileName = path + file;

        try {
            FileWriter fileWriter = new FileWriter(fileName);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(data);

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
