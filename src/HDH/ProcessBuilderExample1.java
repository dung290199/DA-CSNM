package HDH;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProcessBuilderExample1 {

    public static void main(String[] args) throws IOException, InterruptedException {

        Process process = Runtime.getRuntime().exec("tasklist");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
//            if (line.startsWith("==")){
//                String[] strings = line.split(" ", 5);
//                int n0 = strings[0].length();
//                System.out.println(n0);
//                int n1 = strings[1].length();
//                System.out.println(n1);
//                int n2 = strings[2].length();
//                System.out.println(n2);
//                int n3 = strings[3].length();
//                System.out.println(n3);
//                int n4 = strings[4].length();
//                System.out.println(n4);
//            }
            if (!(line.equals("") || line.startsWith("Image") || line.startsWith("=="))) {
                lines.add(line);
                int l = line.length();
                System.out.println(l);
                String s1 = line.substring(0, 24);
                System.out.println(s1);
                String s2 = line.substring(25, 34);
                System.out.println(s2);
                String s3 = line.substring(35, 52);
                System.out.println(s3);
                String s4 = line.substring(53, 63);
                System.out.println(s4);
                String s5 = line.substring(64, 76);
                System.out.println(s5);

                System.out.println(line);
            }

        }


//        Process process = Runtime.getRuntime().exec("powershell Get-WmiObject win32_OperatingSystem |%{$_.totalvisiblememorysize, $_.freephysicalmemory}");
//        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//        int totalRam = Integer.parseInt(reader.readLine().trim());
//        System.out.println("Ram total: " + totalRam);
//        int availRam = Integer.parseInt(reader.readLine().trim());
//        System.out.println("Ram available: " + availRam);

//        while (true) {
//            Process process = Runtime.getRuntime().exec("powershell (Get-Process|Select-Object -ExpandProperty Threads).Count");
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            int thread = Integer.parseInt(reader.readLine().trim());
//            System.out.println("threads: " + thread);
//        }


//        while (true){
//            List<String> strings = getProcesses("powershell Get-Counter '\\Processor(_Total)\\% Processor Time'");
//            double cpuUasge = Double.parseDouble(strings.get(4).trim());
//            System.out.println("CPU usage: " + cpuUasge);
//        }


//        try {
////            List<String> lines = getProcesses("powershell Get-Process");
////            for (String line : lines) {
////                System.out.println(line);
////            }
//
//            while (true) {
//
//                int number = getNumber("powershell (Get-Process).Count") - 4;
//                System.out.println("\nNumber of proccesses: " + (number));
//
////                int handle = getHandles("powershell (Get-Process).handles");
////                System.out.println("Number of handles: " + handle);
//
//                System.out.println("===============================================================================================");
//            }
//
////            List<String> threads = execute("cmd powershell (Get-Process |Select-Object -ExpandProperty Threads).Count");
////            int thread = Integer.parseInt(threads.get(0));
////            System.out.println("Number of thread: " + thread);
//
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }

//    }

//    private static double getTotal(String string) throws IOException {
//        Process process = Runtime.getRuntime().exec(string);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//        String line = reader.readLine();
//        String[] strings = line.split(":", 4);
//
//        int day = Integer.parseInt(strings[0]);
//        int hour = Integer.parseInt(strings[1]);
//        int min = Integer.parseInt(strings[2]);
//        double sec = Double.parseDouble(strings[3]);
//
//        return sec + min*60 + hour*60*60 + day*24*60*60;
//    }

//    public static List<String> getProcesses(String string) throws IOException, InterruptedException {
//        Process process = Runtime.getRuntime().exec(string);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//        List<String> lines = new ArrayList<>();
//        String line;
//        while((line = reader.readLine()) != null){
//            lines.add(line);
//        }
//        return lines;
//    }
//
//    public static double getCPUs(String string) throws IOException, InterruptedException {
//        Process process = Runtime.getRuntime().exec(string);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//        String line;
//        double cpu = 0;
//        while((line = reader.readLine()) != null){
//            cpu += Double.parseDouble(line);
//        }
//        return cpu;
//    }
//
//    public static int getHandles(String string) throws IOException, InterruptedException {
//        Process process = Runtime.getRuntime().exec(string);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//        String line;
//        int cpu = 0;
//        while((line = reader.readLine()) != null){
//            cpu += Integer.parseInt(line);
//        }
//        return cpu;
//    }
//
//    public static int getNumber(String string) throws IOException, InterruptedException {
//        Process process = Runtime.getRuntime().exec(string);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//        int number = Integer.parseInt(reader.readLine());
//        return number;
    }
}