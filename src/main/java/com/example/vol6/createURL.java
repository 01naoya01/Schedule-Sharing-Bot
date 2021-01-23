package com.example.vol6;

public class createURL {

    public static String get(String text) {
        System.out.println("createURLのtext : " + text + "\n");
        String yotei[] = text.split("\n", 0);
        String yotei_name = yotei[0];
        String yotei_start_time = yotei[1].replaceAll("\\s","T");
        yotei_start_time = yotei_start_time.replaceAll("\\D", "");
        String yotei_end_time = yotei[2].replaceAll("\\s","T");
        yotei_end_time = yotei_end_time.replaceAll("\\D", "");
        String action = "TEMPLATE";
        String url_yotei = "http://www.google.com/calendar/event?action="
        + action + "&text=" + yotei_name + "&dates=" + yotei_start_time + yotei_end_time;
		return url_yotei;
	}
}
