import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
/*
Напишите программу для поиска файлов на жестком диске компьютера.
Параллельно запустите несколько потоков для поиска файлов в разных каталогах,
чтобы ускорить процесс поиска.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        MyThread myThread1 = new MyThread(new File("C:\\Users\\ekaterina.izotova\\IdeaProjects\\Lesson1"));
        MyThread myThread2 = new MyThread(new File("C:\\New folder"));
        myThread1.start();
        myThread2.start();
        myThread1.join();
        myThread2.join();
    }
}
class MyThread extends Thread {
    File rootFile;
    public MyThread(File rootFile) {
        this.rootFile = rootFile;
    }
    @Override
    public void run() {
        ArrayList<File> arrayList = new ArrayList<>();
        searchFile(rootFile, arrayList);
        for (File file : arrayList) {
            System.out.println("Имя потока: " + Thread.currentThread().getName() + " Название файла: " + file.getAbsolutePath());
        }
    }
    private static void searchFile(File rootFile, List<File> fileList) {
        if (rootFile.isDirectory()) {
            System.out.println("Имя потока: " + Thread.currentThread().getName() + " Просмотрим папку: " + rootFile.getAbsolutePath());
            File[] directoryFile = rootFile.listFiles();
            if (directoryFile != null) {
                for (File file : directoryFile) {
                    if (file.isDirectory()) {
                        searchFile(file, fileList);
                    } else {
                        if (file.getName().toLowerCase().equalsIgnoreCase("Basket.txt")) {
                            fileList.add(file);
                        }
                    }
                }
            }
        }
    }
}
