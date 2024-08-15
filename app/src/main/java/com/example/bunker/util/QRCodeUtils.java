package com.example.bunker.util;

import java.nio.charset.StandardCharsets;
import android.util.Base64;

import android.graphics.Bitmap;
import android.util.Base64;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class QRCodeUtils {

    // Convert HTML content to a data URL format
    public static String encodeHtmlToDataUrl(String htmlContent) {
        // Encode HTML content as Base64
        String base64Html = Base64.encodeToString(htmlContent.getBytes(), Base64.NO_WRAP);
        // Create data URL
        return "data:text/html;base64," + base64Html;
    }

    // Generate QR code from a given content
    public static Bitmap generateQRCode(String content, int width, int height) {
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height);
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
}

