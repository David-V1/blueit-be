package com.blueitapp.blueit.utils;

import com.blueitapp.blueit.models.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtils {

    public static byte[] compressImage(byte[] data) {
        Deflater deflator = new Deflater();
        deflator.setLevel(Deflater.BEST_COMPRESSION);
        deflator.setInput(data);
        deflator.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[4*1024];
        while (!deflator.finished()) {
            int count = deflator.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (Exception ignored) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[4*1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }

    // We are going to build our images and store them in a set of images.
    public static Set<Image> uploadImageArr(MultipartFile[] multipartFiles) throws IOException {
        Set<Image> imageModels = new HashSet<>();
        // building Objects of our Image-model to save in Set {}.
        for (MultipartFile file : multipartFiles) {
            Image imageModel = new Image(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            );
            imageModels.add(imageModel);
        }
        return imageModels;
    }

    public static Image uploadImage(MultipartFile file) throws IOException {
        Image image = new Image(
                file.getOriginalFilename(),
                file.getContentType(),
                file.getBytes()
        );
        return image;
    }

}
