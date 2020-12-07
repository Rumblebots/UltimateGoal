package org._11253.lib.playback.data;

import com.qualcomm.robotcore.util.ReadWriteFile;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;

public class Reader {
    public String read(String filePath, String fileName) {
        File file = AppUtil.getInstance().getSettingsFile(filePath + fileName);
        return ReadWriteFile.readFile(file);
    }
}
