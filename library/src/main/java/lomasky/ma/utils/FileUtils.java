package lomasky.ma.utils;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 文件操作工具包
 *
 * @author huangdroid
 * @version 1.0
 * @created 2012-3-21
 */
public class FileUtils {

    private final static String Tag = "FileUtils";
    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 写文本文件 在Android系统中，文件保存在 /data/data/PACKAGE_NAME/files 目录下
     *
     * @param context
     * @param fileName
     */
    public static void write(Context context, String fileName, String content) {
        if (content == null)
            content = "";

        try {
            FileOutputStream fos = context.openFileOutput(fileName,
                    Context.MODE_PRIVATE);
            fos.write(content.getBytes());

            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文本文件
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String read(Context context, String fileName) {
        try {
            FileInputStream in = context.openFileInput(fileName);
            return readInStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String readInStream(FileInputStream inStream) {
        try {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[512];
            int length = -1;
            while ((length = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, length);
            }

            outStream.close();
            inStream.close();
            return outStream.toString();
        } catch (IOException e) {
            Log.i("FileTest", e.getMessage());
        }
        return null;
    }

    public static File createFile(String folderPath, String fileName) {
        File destDir = new File(folderPath);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        return new File(folderPath, fileName + fileName);
    }

    /**
     * 向手机写图片
     *
     * @param buffer
     * @param folder
     * @param fileName
     * @return
     */
    public static File writeFile(byte[] buffer, String folder,
                                 String fileName) {
        boolean writeSucc = false;

        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);

        String folderPath = "";
        if (sdCardExist) {
            folderPath = Environment.getExternalStorageDirectory()
                    + File.separator + folder + File.separator;
        } else {
            writeSucc = false;
        }

        File fileDir = new File(folderPath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        File file = new File(folderPath + fileName);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            out.write(buffer);
            writeSucc = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }


    /**
     * Bitmap → byte[]
     *
     * @param bm
     * @return
     */
    public static byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 根据文件绝对路径获取文件名
     *
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath) {
        if (StringUtil.isEmpty(filePath))
            return "";
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
    }

    /**
     * 根据文件的绝对路径获取文件名但不包含扩展名
     *
     * @param filePath
     * @return
     */
    public static String getFileNameNoFormat(String filePath) {
        if (StringUtil.isEmpty(filePath)) {
            return "";
        }
        int point = filePath.lastIndexOf('.');
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1,
                point);
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName
     * @return
     */
    public static String getFileFormat(String fileName) {
        if (StringUtil.isEmpty(fileName))
            return "";

        int point = fileName.lastIndexOf('.');
        return fileName.substring(point + 1);


    }

    /**
     * 获取文件扩展名
     *
     * @param uri
     * @return
     */
    public static String getExtension(String uri) {
        if (uri == null) {
            return null;
        }

        int dot = uri.lastIndexOf(".");
        if (dot >= 0) {
            return uri.substring(dot);
        } else {
            // No extension.
            return "";
        }
    }

    /**
     * 根据后缀名判断是否是图片文件
     *
     * @param type
     * @return 是否是图片结果true or false
     */
    public static boolean isImage(String type) {
        return type != null
                && (type.equals("jpg") || type.equals("gif")
                || type.equals("png") || type.equals("jpeg")
                || type.equals("bmp") || type.equals("wbmp")
                || type.equals("ico") || type.equals("jpe"));
    }

    /**
     * 获取文件大小
     *
     * @param filePath
     * @return
     */
    public static long getFileSize(String filePath) {
        long size = 0;

        File file = new File(filePath);
        if (file != null && file.exists()) {
            size = file.length();
        }
        return size;
    }

    /**
     * 获取文件大小
     *
     * @param size 字节
     * @return
     */
    public static String getFileSize(long size) {
        if (size <= 0)
            return "0";
        java.text.DecimalFormat df = new java.text.DecimalFormat("##.##");
        float temp = (float) size / 1024;
        if (temp >= 1024) {
            return df.format(temp / 1024) + "M";
        } else {
            return df.format(temp) + "K";
        }
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return B/KB/MB/GB
     */
    public static String formatFileSize(long fileS) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }
    public  static final double KB=1024;
    public  static final double MB=1048576;
    public  static final double GB=1073741824;


    /**
     * 获取目录文件大小
     *
     * @param dir
     * @return
     */
    public static long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();
            } else if (file.isDirectory()) {
                dirSize += file.length();
                dirSize += getDirSize(file); // 递归调用继续统计
            }
        }
        return dirSize;
    }

    /**
     * 获取目录文件个数
     *
     * @param dir
     * @return
     */
    public long getFileList(File dir) {
        long count = 0;
        File[] files = dir.listFiles();
        count = files.length;
        for (File file : files) {
            if (file.isDirectory()) {
                count = count + getFileList(file);// 递归
                count--;
            }
        }
        return count;
    }

    public static byte[] toBytes(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int ch;
        while ((ch = in.read()) != -1) {
            out.write(ch);
        }
        byte buffer[] = out.toByteArray();
        out.close();
        return buffer;
    }

    public static byte[] toBytes(File file) throws IOException {
        byte[] buffer = null;
        try {

            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
    /**
     * 检查文件是否存在
     *
     * @param name
     * @return
     */
    public static boolean checkFileExists(String name) {
        boolean status;
        if (!name.equals("")) {
            File path = Environment.getExternalStorageDirectory();
            File newPath = new File(path.toString() + name);
            status = newPath.exists();
        } else {
            status = false;
        }
        return status;
    }

    /**
     * 检查路径是否存在
     *
     * @param path
     * @return
     */
    public static boolean checkFilePathExists(String path) {
        return new File(path).exists();
    }

    /**
     * 计算SD卡的剩余空间
     *
     * @return 返回-1，说明没有安装sd卡
     */
    public static long getFreeDiskSpace() {
        String status = Environment.getExternalStorageState();
        long freeSpace = 0;
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            try {
                File path = Environment.getExternalStorageDirectory();
                StatFs stat = new StatFs(path.getPath());
                long blockSize = stat.getBlockSize();
                long availableBlocks = stat.getAvailableBlocks();
                freeSpace = availableBlocks * blockSize / 1024;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return -1;
        }
        return (freeSpace);
    }

    /**
     * 新建目录
     *
     * @param directoryName
     * @return
     */
    public static boolean createDirectory(String directoryName) {
        boolean status;
        if (!directoryName.equals("")) {
            File path = Environment.getExternalStorageDirectory();
            File newPath = new File(path.toString() + directoryName);
            status = newPath.mkdir();
            status = true;
        } else
            status = false;
        return status;
    }

    /**
     * 检查是否安装SD卡
     *
     * @return
     */
    public static boolean checkSaveLocationExists() {
        String sDCardStatus = Environment.getExternalStorageState();
        boolean status;
        status = sDCardStatus.equals(Environment.MEDIA_MOUNTED);
        return status;
    }

    /**
     * 删除目录(包括：目录里的所有文件)
     *
     * @param fileName
     * @return
     */
    public static boolean deleteDirectory(String fileName) {
        boolean status;
        SecurityManager checker = new SecurityManager();

        if (!fileName.equals("")) {

            File path = Environment.getExternalStorageDirectory();
            File newPath = new File(path.toString() + fileName);
            checker.checkDelete(newPath.toString());
            if (newPath.isDirectory()) {
                String[] listfile = newPath.list();
                // delete all files within the specified directory and then
                // delete the directory
                try {
                    for (int i = 0; i < listfile.length; i++) {
                        File deletedFile = new File(newPath.toString() + "/"
                                + listfile[i].toString());
                        deletedFile.delete();
                    }
                    newPath.delete();

                    status = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    status = false;
                }

            } else
                status = false;
        } else
            status = false;
        return status;
    }

    /**
     * 删除文件
     *
     * @param fileName
     * @return
     */
    public static boolean deleteFile(String fileName) {
        boolean status;
        SecurityManager checker = new SecurityManager();

        if (!fileName.equals("")) {
            File path = Environment.getExternalStorageDirectory();
            File newPath = new File(path.toString() + fileName);
            checker.checkDelete(newPath.toString());
            if (newPath.isFile()) {
                try {

                    newPath.delete();
                    status = true;
                } catch (SecurityException se) {
                    se.printStackTrace();
                    status = false;
                }
            } else
                status = false;
        } else
            status = false;
        return status;
    }

    /**
     * 删除空目录
     * <p/>
     * 返回 0代表成功 ,1 代表没有删除权限, 2代表不是空目录,3 代表未知错误
     *
     * @return
     */
    public static int deleteBlankPath(String path) {
        File f = new File(path);
        if (!f.canWrite()) {
            return 1;
        }
        if (f.list() != null && f.list().length > 0) {
            return 2;
        }
        if (f.delete()) {
            return 0;
        }
        return 3;
    }

    /**
     * 重命名
     *
     * @param oldName
     * @param newName
     * @return
     */
    public static boolean reNamePath(String oldName, String newName) {
        File f = new File(oldName);
        return f.renameTo(new File(newName));
    }

    /**
     * 删除文件
     *
     * @param filePath
     */
    public static boolean deleteFileWithPath(String filePath) {
        SecurityManager checker = new SecurityManager();
        File f = new File(filePath);
        checker.checkDelete(filePath);
        if (f.isFile()) {

            f.delete();
            return true;
        }
        return false;
    }

    /**
     * 获取SD卡的根目录，末尾带\
     *
     * @return
     */
    public static String getSDRoot() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator;
    }

    /**
     * 列出root目录下所有子目录
     *
     * @param root
     * @return 绝对路径
     */
    public static List<String> listPath(String root) {
        List<String> allDir = new ArrayList<String>();
        SecurityManager checker = new SecurityManager();
        File path = new File(root);
        checker.checkRead(root);
        if (path.isDirectory()) {
            for (File f : path.listFiles()) {
                if (f.isDirectory()) {
                    allDir.add(f.getAbsolutePath());
                }
            }
        }
        return allDir;
    }

    /**
     * @return The MIME type for the given file.
     */
    public static String getMimeType(File file) {

        String extension = getExtension(file.getName());

        if (extension.length() > 0)
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.substring(1));

        return "application/octet-stream";
    }

    public enum PathStatus {
        SUCCESS, EXITS, ERROR
    }

    /**
     * 创建目录
     *
     * @param newPath
     */
    public static PathStatus createPath(String newPath) {
        File path = new File(newPath);
        if (path.exists()) {
            return PathStatus.EXITS;
        }
        if (path.mkdir()) {
            return PathStatus.SUCCESS;
        } else {
            return PathStatus.ERROR;
        }
    }

    /**
     * 截取路径名
     *
     * @return
     */
    public static String getPathName(String absolutePath) {
        int start = absolutePath.lastIndexOf(File.separator) + 1;
        int end = absolutePath.length();
        return absolutePath.substring(start, end);
    }

    public static String getAudioFileNameByUrl(String url) {
        if (StringUtil.isEmpty(url))
            return "";
        String regex = "[.\\/|:*<>?]"; // 查找开始标签的<
        // String regex = "[\\//u005C//u007C://u002A<>//u003F]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url.substring(url
                .lastIndexOf(File.separator) + 1));

        String filename = matcher.replaceAll("_") + ".mp3";

        // System.out.println(abc);
        return filename;
    }

    // zhangyan chat audio
    public static String getChatAudioFileNameByUrl(String url) {
        if (StringUtil.isEmpty(url))
            return "";
        String regex = "[.\\/|:*<>?]"; // 查找开始标签的<
        // String regex = "[\\//u005C//u007C://u002A<>//u003F]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url.substring(url
                .lastIndexOf(File.separator) + 1));

        String filename = matcher.replaceAll("_") + ".pcm";

        // System.out.println(abc);
        return filename;
    }

    /**
     * Get a usable cache directory (external if available, internal otherwise).
     *
     * @param context    The context to use
     * @param uniqueName A unique directory name to append to the cache dir
     * @return The cache dir
     */
    public static File getDiskCacheDir(Context context, String directoryName,
                                       String uniqueName) {
        // Check if media is mounted or storage is built-in, if so, try and use
        // external cache dir
        // otherwise use internal cache dir
        final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState()) || !isExternalStorageRemovable() ? getExternalCacheDir(
                context).getPath()
                : context.getCacheDir().getPath();
        File deFile = new File(cachePath + File.separator + directoryName
                + File.separator);
        if (!deFile.exists())
            deFile.mkdirs();

        return new File(cachePath + File.separator + directoryName
                + File.separator + uniqueName);
    }

    public static String getDiskCacheFilePath(Context context,
                                              String directoryName, String uniqueName) {

        return getDiskCacheDir(context, directoryName, uniqueName)
                .getAbsolutePath();
    }

    public static File getDiskCacheDir(Context context, String directoryName) {
        // Check if media is mounted or storage is built-in, if so, try and use
        // external cache dir
        // otherwise use internal cache dir
        final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState()) || !isExternalStorageRemovable() ? getExternalCacheDir(
                context).getPath()
                : context.getCacheDir().getPath();
        File deFile = new File(cachePath + File.separator + directoryName
                + File.separator);
        if (!deFile.exists())
            deFile.mkdir();

        return deFile;
    }


    public static String getDiskCacheDir(Context context) {
        // Check if media is mounted or storage is built-in, if so, try and use
        // external cache dir
        // otherwise use internal cache dir
        final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState()) || !isExternalStorageRemovable() ? getExternalCacheDir(
                context).getPath()
                : context.getCacheDir().getPath();

        return cachePath;
    }

    @TargetApi(9)
    public static boolean isExternalStorageRemovable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return Environment.isExternalStorageRemovable();
        }
        return true;
    }

    /**
     * Get the external app cache directory.
     *
     * @param context The context to use
     * @return The external cache dir
     */
    @TargetApi(8)
    public static File getExternalCacheDir(Context context) {
        File cacheFile = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            cacheFile = context.getExternalCacheDir();
        }
        if (cacheFile != null) {
            return cacheFile;
        }
        // Before Froyo we need to construct the external cache dir ourselves
        final String cacheDir = "/Android/data/" + context.getPackageName()
                + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath()
                + cacheDir);
    }

    /**
     * 文件是否存在
     *
     * @param file
     * @return
     */
    public static boolean isFileExists(File file) {
        if (file == null)
            return false;
        return file.exists();
    }

    /**
     * 文件是否存在
     *
     * @param fileName
     * @return
     */
    public static boolean isFileExists(String fileName) {
        if (fileName == null || (fileName = fileName.trim()).equals("")) {
            return false;
        }
        File file = new File(fileName);
        return isFileExists(file);
    }

    /**
     * 获取文件名
     *
     * @param filePath
     * @return
     */
    public static String getFileNameByUrl(String filePath) {
        if (StringUtil.isEmpty(filePath)) {
            return null;
        }
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1,
                filePath.length());
    }


    public static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";

    }

    /**
     * 获取输入流
     *
     * @param mUri
     * @return InputStream
     * @Title: getInputStream
     * @date 2012-12-14 上午9:00:31
     */
    public static InputStream getInputStream(Context context, Uri mUri)
            throws IOException {
        try {
            if (mUri.getScheme().equals("file")) {
                return new FileInputStream(mUri.getPath());
            } else {
                return context.getContentResolver().openInputStream(mUri);
            }
        } catch (FileNotFoundException ex) {
            return null;
        }
    }

    /**
     * 此处写方法描述
     *
     * @param uri
     * @return void
     * @date 2012-12-13 下午8:22:23
     */
    public static Bitmap getBitmap(Context context, Uri uri) {
        Bitmap bitmap = null;
        InputStream is = null;
        try {

            is = getInputStream(context, uri);

            bitmap = BitmapFactory.decodeStream(is);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignored) {
                }
            }
        }
        return bitmap;
    }

    /**
     * 如果delete为true存在则删除 再保存
     *
     * @param ctx
     * @param filePath
     * @param bitmap
     * @param quality
     * @param delete
     * @return
     * @throws IOException
     */
    public static File saveImageToSD(Context ctx, String filePath,
                                     Bitmap bitmap, int quality, boolean delete) throws IOException {
        if (bitmap != null) {
            File file = new File(filePath.substring(0,
                    filePath.lastIndexOf(File.separator)));
            if (delete) {
                if (file.exists()) {
                    FileUtils.deleteDirectory(filePath);
                }
            }
            if (!file.exists()) {
                file.mkdirs();
            }
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(filePath));
            bitmap.setDensity(1);
            bitmap.compress(CompressFormat.JPEG, quality, bos);
            bos.flush();
            bos.close();
            // if(ctx!=null){
            // scanPhoto(ctx, filePath);
            // }
        }
        return new File(filePath);
    }

    public static String TimeStamp2Date(String timestampString) {
        if (timestampString == null)
            return "";
        if (timestampString.contains(".")) {
            timestampString = timestampString.substring(0,
                    timestampString.indexOf("."));
        }
        Long timestamp = Long.parseLong(timestampString);
        // String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        // .format(new java.util.Date(timestamp));
        String date = new SimpleDateFormat("yyyy-MM-dd")
                .format(new Date(timestamp));
        return date;
    }

    /**
     * 获取当前目录下的zip文件
     *
     * @param path
     * @return
     */
    public static String GetLocalBellPath(File path) {
        if (path == null)
            return null;
        if (isFileExists(path) == false)
            return null;
        File[] files = path.listFiles();
        if (files == null)
            return null;
        for (int i = 0; i < files.length; i++) {
            String pathname = files[i].getPath();
            if (pathname.endsWith(".zip") || pathname.endsWith(".Zip")) {
                return pathname;
            }
        }

        return null;
    }

    public static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    /*
     * 文件md5校验
     */
    public static String md5sum(String filename) {
        InputStream fis;
        byte[] buffer = new byte[1024];
        int numRead = 0;
        MessageDigest md5;
        try {
            fis = new FileInputStream(filename);
            md5 = MessageDigest.getInstance("MD5");
            while ((numRead = fis.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }
            fis.close();
            return toHexString(md5.digest());
        } catch (Exception e) {
            Log.e("FileMD5Digest", "error");
            return null;
        }
    }

    /**
     * 删除空目录
     *
     * @param dir 将要删除的目录路径
     */
    private static void doDeleteEmptyDir(String dir) {
        boolean success = (new File(dir)).delete();
        if (success) {
            System.out.println("Successfully deleted empty directory: " + dir);
        } else {
            System.out.println("Failed to delete empty directory: " + dir);
        }
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful. If a
     * deletion fails, the method stops attempting to delete and returns
     * "false".
     */
    public static boolean deleteDir(File dir) {
        try {
            File to = new File(dir.getAbsolutePath()
                    + System.currentTimeMillis());
            dir.renameTo(to);
            if (to.isDirectory()) {
                String[] children = to.list();
                // 递归删除目录中的子目录下
                for (int i = 0; i < children.length; i++) {
                    boolean success = deleteDir(new File(to, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }
            return to.delete();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 目录此时为空，可以删除
        return false;
    }

    /*
     * public void deleteSDCardFolder(File dir) { File to = new
     * File(dir.getAbsolutePath() + System.currentTimeMillis());
     * dir.renameTo(to); if (to.isDirectory()) { String[] children = to.list();
     * for (int i = 0; i < children.length; i++) { File temp = new File(to,
     * children[i]); if (temp.isDirectory()) { deleteSDCardFolder(temp); } else
     * { boolean b = temp.delete(); if (b == false) {
     * Log.d("deleteSDCardFolder", "DELETE FAIL"); } } } to.delete(); } }
     */
    /*
	 * public static void deleteContents(File dir) throws IOException { File[]
	 * files = dir.listFiles(); if (files == null) { throw new
	 * IllegalArgumentException("not a directory: " + dir); } for (File file :
	 * files) { if (file.isDirectory()) { deleteContents(file); } if
	 * (!file.delete()) { throw new IOException("failed to delete file: " +
	 * file); } } }
	 */
    public static boolean deleteCacheContents(String dirPath)
            throws IOException {
        File dir = new File(dirPath);
        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (files == null) {
                throw new IllegalArgumentException("not a directory: " + dir);
            }
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteCacheContents(file.getAbsolutePath());
                }
                if (!file.delete()) {
                    throw new IOException("failed to delete file: " + file);
                }
            }
        }
        return dir.delete();
    }


    /**
     * 根据Uri返回文件路径
     *
     * @param mUri
     * @return String
     */
    public static String getFilePath(Context context, Uri mUri) {
        try {
            if (mUri.getScheme().equals("file")) {
                return mUri.getPath();
            } else {
                return getFilePathByUri(context, mUri);
            }
        } catch (FileNotFoundException ex) {
            return null;
        }
    }

    /**
     * 此处写方法描述
     *
     * @param mUri
     * @return String
     */
    public static String getFilePathByUri(Context context, Uri mUri)
            throws FileNotFoundException {
        String imgPath;
        Cursor cursor = context.getContentResolver().query(mUri, null, null,
                null, null);
        cursor.moveToFirst();
        imgPath = cursor.getString(1); // 图片文件路径
        return imgPath;
    }

//    public static String getRealPathFromURI(Context context, Uri contentUri) {
//        String res = null;
//        String[] proj = {MediaStore.Images.Media.DATA};
//        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
//        if (cursor.moveToFirst()) {
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            res = cursor.getString(column_index);
//        }
//        cursor.close();
//        return res;
//    }

    public static String getFilePathFromURI(Context context, Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Files.FileColumns.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    public static void saveBytesToCache(byte[] data, String filePath) {

        try {
            if (data != null && data.length > 0
                    && !StringUtil.isEmpty(filePath)) {
                File file = new File(filePath);
                if (file.exists()) {
                    file.delete();
                }

                file.createNewFile();

                OutputStream outStream = new FileOutputStream(file);

                // bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                outStream.write(data);
                outStream.flush();
                outStream.close();
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    // sim卡是否可读
    public static boolean isCanUseSim(Context context) {
        try {
            TelephonyManager mgr = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            return TelephonyManager.SIM_STATE_READY == mgr.getSimState();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 返回当前的应用是否处于前台显示状态
     *
     * @param
     * @return
     */
    public static boolean isTopActivity(Context context) {
        // _context是一个保存的上下文
        ActivityManager am = (ActivityManager) context.getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        List<ActivityManager.RunningAppProcessInfo> __list = am
                .getRunningAppProcesses();
        if (__list.size() == 0)
            return false;
        for (ActivityManager.RunningAppProcessInfo __process : __list) {

            if (__process.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                    && __process.processName.equals(currentPackageName)) {
                return true;
            }
        }
        return false;
    }
    public static File createTmpFile(Context context){

        String state = Environment.getExternalStorageState();
        if(state.equals(Environment.MEDIA_MOUNTED)){
            // 已挂载
            File pic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
            String fileName = "multi_image_"+timeStamp+"";
            File tmpFile = new File(pic, fileName+".jpg");
            return tmpFile;
        }else{
            File cacheDir = context.getCacheDir();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
            String fileName = "multi_image_"+timeStamp+"";
            File tmpFile = new File(cacheDir, fileName+".jpg");
            return tmpFile;
        }

    }


}