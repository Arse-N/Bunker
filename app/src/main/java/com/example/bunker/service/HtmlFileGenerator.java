package com.example.bunker.service;

import com.example.bunker.dto.CardInfo;

public class HtmlFileGenerator {

    public String generateCardHtml(CardInfo cardInfo) {
        return "<html>" +
                "<head>" +
                "<style>" +
                "html, body { height: 100%; margin: 0; padding: 0; }" +
                "body { background-color: #654321; font-family: Arial, sans-serif; display: flex; align-items: center; justify-content: center; }" +
                ".card { width: 90vw; height: 75vh; padding: 20px; background-color: #ffffff; color: #000000; border-radius: 25px; box-sizing: border-box; overflow-y: auto; display: flex; flex-direction: column; align-items: flex-start; }" +
                ".title { font-weight: bold; font-size: 4vw; margin-bottom: 20px; text-align: left; }" +
                ".section { margin-bottom: 20px; text-align: left; width: 100%; }" +
                ".label { font-weight: bold; font-size: 3vw; }" +
                ".value { font-style: italic; font-size: 3vw; }" +
                ".danger-image { width: 50vw; height: auto; margin-top: 20px; align-self: center; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='card'>" +
                "<div class='section'><span class='label'>Մասնագիտություն: </span><span class='value'>" + cardInfo.getProfession() + "</span></div>" +
                "<div class='section'><span class='label'>Տարիք: </span><span class='value'>" + cardInfo.getBio() + "</span></div>" +
                "<div class='section'><span class='label'>Վախ: </span><span class='value'>" + cardInfo.getPhobia() + "</span></div>" +
                "<div class='section'><span class='label'>Հիվանդություն: </span><span class='value'>" + cardInfo.getIllness() + "</span></div>" +
                "<div class='section'><span class='label'>Ճամպրուկ: </span><span class='value'>" + cardInfo.getBaggage() + "</span></div>" +
                "<div class='section'><span class='label'>Հավելյալ Ինֆորմացիա: </span><span class='value'>" + cardInfo.getAdditionalInfo() + "</span></div>" +
                "<img src='https://ik.imagekit.io/ftziq8571/danger.svg?updatedAt=1724330593551' class='danger-image'/>" +
                "</div>" +
                "</body>" +
                "</html>";
    }


}
