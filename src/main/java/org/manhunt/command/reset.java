package org.manhunt.command;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.scheduler.BukkitRunnable;
import org.manhunt.Main;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;


public class reset implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        try {
            Bukkit.getServer().getWorlds().forEach(world -> world.setAutoSave(false));
            World world = Bukkit.getWorld("world");
            World world_nether = Bukkit.getWorld("world_nether");
            World world_the_end = Bukkit.getWorld("world_the_end");
            File worlds = getFile(Bukkit.getServer().getWorldContainer().listFiles(),"mhWorlds");
            File rdWorlds = getFile(worlds.listFiles(),worlds.listFiles()[new Random().nextInt(worlds.listFiles().length)].getName());
            File mhConfig = getFile(getFile(Bukkit.getServer().getWorldContainer().listFiles(),"plugins").listFiles(),"manhunt");
            copyFolder(getFile(rdWorlds.listFiles(),"world"),world.getWorldFolder());
            copyFolder(getFile(rdWorlds.listFiles(),"world_nether"),world_nether.getWorldFolder());
            copyFolder(getFile(rdWorlds.listFiles(),"world_the_end"),world_the_end.getWorldFolder());
            copyFolder(getFile(rdWorlds.listFiles(),"config"),mhConfig);
        }catch (NullPointerException ex){
            System.out.println("在复制存档文件时出现空指针错误，请检查存档文件名与目标文件是否正确");
        }finally {
            Bukkit.getServer().spigot().restart();
        }
        return false;
    }

    private static void copy(InputStream source, File dest) {
        try {
            OutputStream out = new FileOutputStream(dest);
            byte[] b = new byte[1024];
            int len;

            while((len = source.read(b)) > 0) out.write(b, 0, len);

            out.close();
            source.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param source file to copy from
     * @param dest file to override
     */
    public static void copy(File source, File dest) {
        try
        {
            InputStream in = new FileInputStream(source);
            copy(in, dest);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param source folder to copy from
     * @param dest folder to override
     */
    public static void copyFolder(File source, File dest) {
        if (!source.isDirectory()) return;

        dest.mkdirs();

        File[] files = source.listFiles();
        if (files == null || files.length == 0) return;

        for (File file : files)
        {
            if (file.isDirectory()) {
                copyFolder(file, new File(dest + File.separator + file.getName()));
            }
            else {
                copy(file, new File(dest, file.getName()));
            }
        }
    }

    private File getFile(File[] files,String target){
        for (File file : files){
            if (file.getName().equals(target)){
                return file;
            }
        }
        return null;
    }

    private void RunShellCommand(String command){
        BufferedReader bufferedReader = null;
        try {
            Process process = Runtime.getRuntime().exec(command);
            int exitValue = process.waitFor();
            if (0 != exitValue){
                System.out.println("无法正确执行shell命令!");
            }
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                System.out.println(line);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
