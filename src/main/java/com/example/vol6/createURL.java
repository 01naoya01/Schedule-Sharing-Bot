package com.example.vol6;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

//ユーザのメッセージから予定生成URLを作成し，送信するflex messageを作るためのCalendarEntityを返す
public class createURL {

    public static CalendarEntity get(String text) {
        //デバッグするかしないか
        boolean debug = false;

        String schedule[] = text.split("\n", 0);
        
        // CalendarEntity生成
        CalendarEntity CalendarParam = new CalendarEntity();
        CalendarParam.allday = false;

        //例外処理：項目数
        if (schedule.length < 4) {
            CalendarParam.error = true;
            CalendarParam.errorMessage = "項目が足りていません。";
            return CalendarParam;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        sdf.setLenient(false);
        Date d1 = new Date();
        Date d2 = new Date();
        //例外処理：日付が形式通りかどうか
        /*
        try {
            date1 = sdf.parse(schedule[2]);
            date2 = sdf.parse(schedule[3]);
        } catch (ParseException e) {
            e.printStackTrace();
            CalendarParam.error = true;
            CalendarParam.errorMessage = "日付の部分が形式通りになっていないか，件名の欄がありません。";
            return CalendarParam;
        }
        */
		try {
            d1 = sdf.parse(schedule[2]);
            d2 = sdf.parse(schedule[3]);
        } catch (ParseException e) {
            try {
                sdf = new SimpleDateFormat("yyyy/MM/dd");
                String MonthAndDay = schedule[2].substring(5, schedule[2].length());
                for (int i = 0; i < MonthAndDay.length(); i++) {
                    if (!Character.isDigit(MonthAndDay.charAt(i))
                            && !(i != MonthAndDay.length() - 1 && MonthAndDay.charAt(i) == '/')) {
                        throw new ParseException("error", 0);
                    }
                }
                MonthAndDay = schedule[3].substring(5, schedule[3].length());
                for (int i = 0; i < MonthAndDay.length(); i++) {
                    if (!Character.isDigit(MonthAndDay.charAt(i))
                            && !(i != MonthAndDay.length() - 1 && MonthAndDay.charAt(i) == '/')) {
                        throw new ParseException("error", 0);
                    }
                }
                d1 = sdf.parse(schedule[2]);
                d2 = sdf.parse(schedule[3]);
                CalendarParam.allday = true;
            } catch (ParseException e2) {
                e2.printStackTrace();
                CalendarParam.error = true;
                CalendarParam.errorMessage = "日付の部分が形式通りになっていないか，件名の欄がありません。";
                return CalendarParam;
            }
        }
        
        CalendarParam.calendar1 = Calendar.getInstance();
        CalendarParam.calendar2 = Calendar.getInstance();
        CalendarParam.calendar1.setTime(d1);
        CalendarParam.calendar2.setTime(d2);

        // 例外処理：日付の前後が逆
        if (CalendarParam.calendar1.compareTo(CalendarParam.calendar2) > 0) {
            CalendarParam.error = true;
            CalendarParam.errorMessage = "予定終了の日時は，開始の日時より後にする必要があります。";
            return CalendarParam;
        }
        
        if(schedule[1].equals("")){
            CalendarParam.title = "無題";
        } else {
            CalendarParam.title = schedule[1];
        }

        CalendarParam.location = "";
        CalendarParam.details = "";
        if (schedule.length > 4) {
            if(!schedule[4].equals("")){
                if(debug)System.out.println("schedule[4] : " + schedule[4] + "\n");
                CalendarParam.location = schedule[4];
            }
        }
        if (schedule.length > 5) {
            if(debug)System.out.println("schedule[5] : " + schedule[5] + "\n");
            CalendarParam.details = schedule[5];
        }
        CalendarParam.error = false;
        
        /*
        String yotei_name = schedule[1];
        String yotei_start_time = schedule[2].replaceAll("\\s", "T");
        yotei_start_time = yotei_start_time.replaceAll("\\W", "");
        String yotei_end_time = schedule[3].replaceAll("\\s", "T");
        yotei_end_time = yotei_end_time.replaceAll("\\W", "");
        */

        // URL生成
        String action = "TEMPLATE";
        String dates1,dates2;
        if(CalendarParam.allday){
            sdf = new SimpleDateFormat("yyyyMMdd");
            dates2 = Integer.toString(Integer.parseInt(sdf.format(CalendarParam.calendar2.getTime()))+ 1);
            System.out.println(sdf.format(CalendarParam.calendar2.getTime()));
            System.out.println(dates2);
        }else{
            sdf = new SimpleDateFormat("yyyyMMdd'T'HHmm");
            dates2 = sdf.format(CalendarParam.calendar2.getTime());
        }
        dates1 = sdf.format(CalendarParam.calendar1.getTime());

        //sdf.format(CalendarParam.calendar1.getTime())
        //sdf.format(CalendarParam.calendar2.getTime())
        CalendarParam.URL = "http://www.google.com/calendar/event?action=" + action + "&text=" + CalendarParam.title
                + "&dates="
                + dates1
                + "/" 
                + dates2
                + "&location=" + CalendarParam.location + "&details="
                + CalendarParam.details;
        
        System.out.println("\n"+CalendarParam.URL+"\n");
        if (CalendarParam.location.equals("")) {
            CalendarParam.location = "指定なし";
        }
        if (CalendarParam.details.equals("")) {
            CalendarParam.details = "指定なし";
        }
        
        //デバッグ
        if (debug){
            System.out.println("createURLのtext : " + text + "\n");
            System.out.println("schedule : " + Arrays.toString(schedule));
            System.out.println("CalendarParam.calendar1.get(Calendar.YEAR) : " + CalendarParam.calendar1.get(Calendar.YEAR) + "\n");
            System.out.println("CalendarParam.calendar1.get(Calendar.MONTH) : " + Integer.toString(CalendarParam.calendar1.get(Calendar.MONTH)+1) + "\n");
            System.out.println("CalendarParam.calendar1.get(Calendar.DATE) : " + CalendarParam.calendar1.get(Calendar.DATE) + "\n");
            System.out.println("CalendarParam.calendar1.get(Calendar.HOUR_OF_DAY) : " + CalendarParam.calendar1.get(Calendar.HOUR_OF_DAY) + "\n");
            System.out.println("CalendarParam.calendar1.get(Calendar.MINUTE) : " + CalendarParam.calendar1.get(Calendar.MINUTE) + "\n");
            
            System.out.println("CalendarParam.calendar2.get(Calendar.YEAR) : " + CalendarParam.calendar2.get(Calendar.YEAR) + "\n");
            System.out.println("CalendarParam.calendar2.get(Calendar.MONTH) : " + Integer.toString(CalendarParam.calendar2.get(Calendar.MONTH)+1) + "\n");
            System.out.println("CalendarParam.calendar2.get(Calendar.DATE) : " + CalendarParam.calendar2.get(Calendar.DATE) + "\n");
            System.out.println("CalendarParam.calendar2.get(Calendar.HOUR_OF_DAY) : " + CalendarParam.calendar2.get(Calendar.HOUR_OF_DAY) + "\n");
            System.out.println("CalendarParam.calendar2.get(Calendar.MINUTE) : " + CalendarParam.calendar2.get(Calendar.MINUTE) + "\n");
            
            System.out.println("CalendarParam.Location : " + CalendarParam.location + "\n");
            System.out.println("CalendarParam.Details : " + CalendarParam.details + "\n");
            System.out.println("CalendarParam.URL : " + CalendarParam.URL+"\n");
        }
		return CalendarParam;
    }

    
    public static void main(String[] args){
        //get("予定追加\nオフ会\n2021/02/03 12:00\n2021/02/04 15:00\n大阪駅\n飯食ってカラオケ");
        get("予定追加\nオフ会\n2021/2/3\n2021/02/04\n大阪駅\n飯食ってカラオケ");
    }
}
