package org._11253.lib.playback.data;

import com.qualcomm.robotcore.util.ReadWriteFile;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;

public class Writer {
    public void write(String filePath, String fileName, String data) {
        File file = AppUtil.getInstance().getSettingsFile(filePath + fileName);
        ReadWriteFile.writeFile(file, data);
    }
}
