package com.example.bunker.service;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Map;

public class EmailSender {
    private static final String FROM_EMAIL = "bunker.gameg@gmail.com";

    private static final String SENDGRID_API_KEY = "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";
    private static final String SENDGRID_TEMPLATE_ID = "d-e3dd08a20bf94fe9ae4f1a295544e405";

    private static final String TO_EMAIL = "bunker.gameg@gmail.com";

    private final Context context;

    public EmailSender(Context context) {
        this.context = context;
    }

    public void reportBug() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:")); // Ensures only email clients respond
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{TO_EMAIL});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Bug Report");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Describe the bug here...");

        try {
            context.startActivity(Intent.createChooser(emailIntent, "Send bug report using:"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "No email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }


    public static void sendEmailWithTemplate(String to, Map<String, String> dynamicData) {
        new SendEmailTask().execute(FROM_EMAIL, to, dynamicData);
    }

    private static class SendEmailTask extends AsyncTask<Object, Void, Void> {

        @Override
        protected Void doInBackground(Object... params) {
            String to = (String) params[1];
            Map<String, String> dynamicData = (Map<String, String>) params[2];

//            OkHttpClient client = new OkHttpClient();
//
//            String emailData = buildJsonPayload(to, dynamicData);
//
//            RequestBody body = RequestBody.create(MediaType.parse("application/json"), emailData);
//
//            Request request = new Request.Builder()
//                    .url("https://api.sendgrid.com/v3/mail/send")
//                    .post(body)
//                    .addHeader("Authorization", "Bearer " + SENDGRID_API_KEY)
//                    .addHeader("Content-Type", "application/json")
//                    .build();
//
//            try (Response response = client.newCall(request).execute()) {
//                if (!response.isSuccessful()) {
//                    Log.e("SendGridEmailSender", "Unexpected response code: " + response);
//                } else {
//                    Log.d("SendGridEmailSender", "Email sent successfully. Status code: " + response.code());
//                }
//            } catch (IOException e) {
//                Log.e("SendGridEmailSender", "Error sending email", e);
//            }
            return null;
        }

        private static String buildJsonPayload(String to, Map<String, String> dynamicData) {
            String subject = "lalalalallala";

            // Construct personalization
            StringBuilder personalization = new StringBuilder("{\"to\": [{\"email\": \"" + to + "\"}], \"dynamic_template_data\": {");
            for (Map.Entry<String, String> entry : dynamicData.entrySet()) {
                personalization.append("\"").append(entry.getKey()).append("\": \"").append(entry.getValue()).append("\", ");
            }
            personalization = new StringBuilder(personalization.substring(0, personalization.length() - 2)); // Remove last comma and space
            personalization.append("}}");

            // Construct JSON payload
            return "{\"personalizations\": [" + personalization + "], \"from\": {\"email\": \"" + FROM_EMAIL + "\"}, \"subject\": \"" + subject + "\", \"template_id\": \"" + SENDGRID_TEMPLATE_ID + "\"}";
        }
    }
}