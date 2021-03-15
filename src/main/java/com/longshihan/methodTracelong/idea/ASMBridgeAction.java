package com.longshihan.methodTracelong.idea;

import com.google.protobuf.compiler.PluginProtos;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.extensions.PluginId;
import org.apache.commons.lang.SystemUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class ASMBridgeAction extends AnAction {
    @java.lang.Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        try {
            String dir = "/war/echarts-0.0.1.war";
            PluginId pluginId = PluginId.getId("org.example.MethodTracelong");
            IdeaPluginDescriptor plugin = PluginManager.getPlugin(pluginId);
            File path1 = plugin.getPath();
            String currentPathStr = path1.getAbsolutePath() + dir;
            File currentPathFile = new File(currentPathStr);
            if (!currentPathFile.exists()) {
                File temp = new File(path1.getAbsolutePath() + "/war");
                if (!temp.exists()) {
                    temp.mkdir();
                }
                currentPathFile.createNewFile();
                InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(dir);
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);
                OutputStream outStream = new FileOutputStream(currentPathFile);
                outStream.write(buffer);
            }
            String commandOpenUrl = "";
            if (SystemUtils.IS_OS_WINDOWS) {
                commandOpenUrl = String.format("cmd /c java -jar %s", currentPathStr);
            } else {
                commandOpenUrl = String.format("open java -jar %s", currentPathStr);
            }
            Runtime.getRuntime().exec(commandOpenUrl);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
