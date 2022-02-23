package com.example.commissioncalculator.model;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.time.LocalDate;

@Component
public class RatesParser {

    @Value("${exchange-rate.url}")
    private String exchangeRateUrl;

    public BigDecimal getRate(String currency) throws Exception {
        JSONObject jsonObject = getJsonFromUrl(exchangeRateUrl + LocalDate.now().minusDays(1));
        JSONObject ratesObject = jsonObject.getJSONObject("rates");
        return ratesObject.getBigDecimal(currency);
    }

    /**
     * Gets data from URL and tries to build JSON object.
     *
     * @param jsonUrl
     * @return
     * @throws IOException
     */
    public JSONObject getJsonFromUrl(final String jsonUrl) throws IOException {
        URL source = new URL(jsonUrl);
        URLConnection connection = source.openConnection();
        try (InputStream is = connection.getInputStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            return new JSONObject(sb.toString());
        }
    }
}
