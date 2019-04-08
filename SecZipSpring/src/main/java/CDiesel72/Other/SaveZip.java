package CDiesel72.Other;

import CDiesel72.Exception.FileDownErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Diesel on 07.04.2019.
 */
public class SaveZip implements Runnable {

    private File zip;
    //private MultipartFile[] fileD;
    private List<FileMul> mulFiles;

    public SaveZip() {
    }

    /*public void start(File zip, MultipartFile[] fileD) {
        this.zip = zip;
        this.fileD = fileD;

        Thread thr = new Thread(this);
        thr.start();
    }*/

    public void start(File zip, MultipartFile[] fileD) throws IOException {
        this.zip = zip;
        this.mulFiles = mulFiles;

        mulFiles = new ArrayList<>();
        for (MultipartFile mf : fileD) {
            mulFiles.add(new FileMul(mf.getOriginalFilename(), mf.getBytes()));
        }

        Thread thr = new Thread(this);
        thr.start();
    }

    /*@Override
    public void run() {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zip))) {
            for (MultipartFile mf : fileD) {
                zos.putNextEntry(new ZipEntry(mf.getOriginalFilename()));
                zos.write(mf.getBytes());
                zos.closeEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
            //throw new FileDownErrorException();
        }
    }*/

    @Override
    public void run() {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zip))) {
            for (FileMul mf : mulFiles) {
                zos.putNextEntry(new ZipEntry(mf.getName()));
                zos.write(mf.getBytes());
                zos.closeEntry();
            }
        } catch (IOException e) {
            throw new FileDownErrorException();
        }
    }

}
