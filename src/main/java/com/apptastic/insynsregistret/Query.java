/*
 * MIT License
 *
 * Copyright (c) 2018, Apptastic Software
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.apptastic.insynsregistret;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Query {
    private static final String INSYNSREGISTERET_URL = "https://marknadssok.fi.se/publiceringsklient/%1$s/Search/Search?SearchFunctionType=Insyn&Utgivare=%2$s&PersonILedandeStällningNamn=%3$s&Transaktionsdatum.From=%4$s&Transaktionsdatum.To=%5$s&Publiceringsdatum.From=%6$s&Publiceringsdatum.To=%7$s&button=export";
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    private String url;
    private Language language;


    Query(Date fromTransactionDate, Date toTransactionDate, Date fromPublicationDate, Date toPublicationDate,
        String issuer, String personDischargingManagerialResponsibilities, Language language) {

        if (issuer == null)
            issuer = "";
        else
            issuer = issuer.replaceAll(" ", "+");

        if (personDischargingManagerialResponsibilities == null)
            personDischargingManagerialResponsibilities = "";
        else
            personDischargingManagerialResponsibilities = personDischargingManagerialResponsibilities.replaceAll(" ", "+");

        if (language == null)
            language = Language.SWEDISH;

        this.language = language;

        url = String.format(INSYNSREGISTERET_URL, language.getName(), issuer, personDischargingManagerialResponsibilities,
                toDateString(fromTransactionDate), toDateString(toTransactionDate),
                toDateString(fromPublicationDate), toDateString(toPublicationDate));
    }

    private String toDateString(Date date) {
        if (date == null)
            return "";

        return DATE_FORMATTER.format(date);
    }

    Language getLanguage() {
        return language;
    }

    public String getUrl() {
        return url;
    }
}