package com.example.vol6;

import static java.util.Arrays.asList;

import java.net.URI;
import java.util.Calendar;
import java.util.function.Supplier;

import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.flex.component.Box;
import com.linecorp.bot.model.message.flex.component.Button;
import com.linecorp.bot.model.message.flex.component.Button.ButtonHeight;
import com.linecorp.bot.model.message.flex.component.Button.ButtonStyle;
import com.linecorp.bot.model.message.flex.component.Separator;
import com.linecorp.bot.model.message.flex.component.Spacer;
import com.linecorp.bot.model.message.flex.component.Text;
import com.linecorp.bot.model.message.flex.component.Text.TextDecoration;
import com.linecorp.bot.model.message.flex.component.Text.TextWeight;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.container.Bubble.BubbleSize;
import com.linecorp.bot.model.message.flex.unit.FlexFontSize;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;
import com.linecorp.bot.model.message.flex.unit.FlexMarginSize;

//Flex messageに関するクラス
public class FlexMessageSupplier implements Supplier<FlexMessage> {
    String text;
    CalendarEntity CalendarParam;
    boolean debug = false;
    
    @Override
    public FlexMessage get() {
        
        if (CalendarParam.error) {                                          //エラーメッセージ生成
            final Box bodyBlockException = createBodyBlockException();
            final Bubble bubble = Bubble
                .builder()
                .size(BubbleSize.GIGA)
                .body(bodyBlockException)
                .build();
            return new FlexMessage("形式を間違えています", bubble);
        } else {                                                            // URL追加メッセ―ジ生成
            if (debug) {
                System.out.println("ExampleFlexMessageSupplierのtext : " + text + "\n");
                System.out.println("CalendarParam.title : " + CalendarParam.title + "\n");
                System.out.println("CalendarParam.URL : " + CalendarParam.URL + "\n");
                System.out.println("年 : " + Integer.toString(CalendarParam.calendar1.get(Calendar.YEAR)) + "年" + "\n");
                System.out.println("月日 : " + Integer.toString(CalendarParam.calendar1.get(Calendar.MONTH) + 1) + "月"
                        + Integer.toString(CalendarParam.calendar1.get(Calendar.DATE)) + "日" + "\n");
                System.out.println("時間分 : " + Integer.toString(CalendarParam.calendar1.get(Calendar.HOUR_OF_DAY)) + ":"
                        + Integer.toString(CalendarParam.calendar1.get(Calendar.MINUTE)) + " - "
                        + Integer.toString(CalendarParam.calendar2.get(Calendar.HOUR_OF_DAY)) + ":"
                        + Integer.toString(CalendarParam.calendar2.get(Calendar.MINUTE)) + "\n");
                System.out.println("CalendarParam.location : " + CalendarParam.location + "\n");
                System.out.println("CalendarParam.details : " + CalendarParam.details + "\n");
            }
            final Box bodyBlock = createBodyBlock();
            final Box footerBlock = createFooterBlock();
            
            final Bubble bubble = Bubble
                .builder()
                .size(BubbleSize.GIGA)
                .body(bodyBlock)
                .footer(footerBlock)
                .build();
            return new FlexMessage("Google Calendarに予定追加", bubble);
        }
    }
    
    //ヘルプメッセージ生成
    public FlexMessage help_get() {
        final Box bodyBlockHelp = createBodyBlockHelp();
            final Bubble bubble = Bubble
                .builder()
                .size(BubbleSize.GIGA)
                .body(bodyBlockHelp)
                .build();
        return new FlexMessage("ヘルプ", bubble);
    }
    
    final Text format_title =
                Text.builder()
                    .text("形式")
                    .weight(TextWeight.BOLD)
                    .size(FlexFontSize.LG)
                    .margin(FlexMarginSize.LG)
                    .decoration(TextDecoration.UNDERLINE)
                    .build();
    
    final Text format =
            Text.builder()
                .text(" \n予定追加\n件名\n年/月/日 時間:分\n年/月/日 時間:分\n場所\n詳細")
                .weight(TextWeight.BOLD)
                .size(FlexFontSize.SM)
                .color("#666666")
                .wrap(true)
                .build();


    final Text example1_title =
            Text.builder()
                .text("例1")
                .weight(TextWeight.BOLD)
                .size(FlexFontSize.LG)
                .margin(FlexMarginSize.LG)
                .decoration(TextDecoration.UNDERLINE)
                .build();

    final Text example1_Message =
            Text.builder()
                .text(" \n予定追加\nオフ会\n2021/02/01 12:00\n2021/02/01 15:00\n梅田HEP前\nご飯食べてカラオケ")
                .weight(TextWeight.BOLD)
                .size(FlexFontSize.SM)
                .color("#666666")
                .wrap(true)
                .build();

    final Text example2_title =
            Text.builder()
                .text("例2")
                .weight(TextWeight.BOLD)
                .size(FlexFontSize.LG)
                .margin(FlexMarginSize.LG)
                .decoration(TextDecoration.UNDERLINE)
                .build();

    final Text example2_Message =
            Text.builder()
                .text(" \n予定追加\n\n2021/3/1\n2021/3/8\n\nご飯食べてカラオケ")
                .weight(TextWeight.BOLD)
                .size(FlexFontSize.SM)
                .color("#666666")
                .wrap(true)
                .build();
                
    //ヘルプメッセージのbody作成
    private Box createBodyBlockHelp() {
        final Separator separator = Separator.builder().margin(FlexMarginSize.XL).build();
        final Text title =
                Text.builder()
                    .text("～HELP～")
                    .weight(TextWeight.BOLD)
                    .size(FlexFontSize.XL)
                    .color("#21dad1ff")
                    .build();
        
        final Text command_title =
                Text.builder()
                    .text("コマンド")
                    .weight(TextWeight.BOLD)
                    .size(FlexFontSize.LG)
                    .margin(FlexMarginSize.LG)
                    .decoration(TextDecoration.UNDERLINE)
                    .build();
        
        final Box command_help = Box
            .builder()
            .layout(FlexLayout.BASELINE)
            .spacing(FlexMarginSize.XL)
            .contents(asList(
                    Text.builder()
                        .text("ヘルプ")
                        .color("#aaaaaa")
                        .size(FlexFontSize.SM)
                        .flex(1)
                        .decoration(TextDecoration.UNDERLINE)
                        .weight(TextWeight.BOLD)
                        .build(),
                    Text.builder()
                        .text("ヘルプを表示します。")
                        .wrap(true)
                        .color("#666666")
                        .size(FlexFontSize.SM)
                        .flex(4)
                        .build()
            ))
            .build();

        final Box command_add = Box
            .builder()
            .layout(FlexLayout.BASELINE)
            .spacing(FlexMarginSize.XL)
            .contents(asList(
                    Text.builder()
                        .text("予定追加")
                        .color("#aaaaaa")
                        .size(FlexFontSize.SM)
                        .flex(1)
                        .decoration(TextDecoration.UNDERLINE)
                        .weight(TextWeight.BOLD)
                        .build(),
                    Text.builder()
                        .text("入力された予定をGoogle Calendarで共有するボタンを返します。\n以下の形式と例に従って入力してください。\n1つ目の日付は予定開始，2つ目の日付は予定終了を表しています。")
                        .wrap(true)
                        .color("#666666")
                        .size(FlexFontSize.SM)
                        .flex(4)
                        .build()
            ))
                .build();

        final Text note_title =
                Text.builder()
                    .text("※注意※")
                    .weight(TextWeight.BOLD)
                    .size(FlexFontSize.LG)
                    .margin(FlexMarginSize.LG)
                        .color("#ff0000ff")
                    .decoration(TextDecoration.UNDERLINE)
                    .build();
        int left = 0, right = 10;

        final Box note1 = Box
            .builder()
            .layout(FlexLayout.BASELINE)
            .spacing(FlexMarginSize.XL)
            .contents(asList(
                    Text.builder()
                        .text("①")
                        .color("#aaaaaa")
                        .size(FlexFontSize.SM)
                        .flex(left)
                        .decoration(TextDecoration.UNDERLINE)
                        .weight(TextWeight.BOLD)
                        .build(),
                    Text.builder()
                        .text("件名、場所、詳細は記入しなくても問題ないですが、改行して空けてください。")
                        .wrap(true)
                        .color("#666666")
                        .size(FlexFontSize.SM)
                        .flex(right)
                        .build()
            ))
            .build();
        
        final Box note2 = Box
            .builder()
            .layout(FlexLayout.BASELINE)
            .spacing(FlexMarginSize.XL)
            .contents(asList(
                    Text.builder()
                        .text("②")
                        .color("#aaaaaa")
                        .size(FlexFontSize.SM)
                        .flex(left)
                        .decoration(TextDecoration.UNDERLINE)
                        .weight(TextWeight.BOLD)
                        .build(),
                    Text.builder()
                        .text("時刻(時間:分)を省略すると終日の予定となります。")
                        .wrap(true)
                        .color("#666666")
                        .size(FlexFontSize.SM)
                        .flex(right)
                        .build()
            ))
            .build();
        
        final Box note3 = Box
            .builder()
            .layout(FlexLayout.BASELINE)
            .spacing(FlexMarginSize.XL)
            .contents(asList(
                    Text.builder()
                        .text("③")
                        .color("#aaaaaa")
                        .size(FlexFontSize.SM)
                        .flex(left)
                        .decoration(TextDecoration.UNDERLINE)
                        .weight(TextWeight.BOLD)
                        .build(),
                    Text.builder()
                        .text("一桁の月、日、時間、分の時の頭の0は省略できます。")
                        .wrap(true)
                        .color("#666666")
                        .size(FlexFontSize.SM)
                        .flex(right)
                        .build()
            ))
            .build();

        final Box note4 = Box
            .builder()
            .layout(FlexLayout.BASELINE)
            .spacing(FlexMarginSize.XL)
            .contents(asList(
                    Text.builder()
                        .text("④")
                        .color("#aaaaaa")
                        .size(FlexFontSize.SM)
                        .flex(left)
                        .decoration(TextDecoration.UNDERLINE)
                        .weight(TextWeight.BOLD)
                        .build(),
                    Text.builder()
                        .text("スマートフォンのGoogle CalendarアプリはURLでの追加の挙動が不安定ですので、ブラウザで開くことを推奨します。")
                        .wrap(true)
                        .color("#666666")
                        .size(FlexFontSize.SM)
                        .flex(right)
                        .build()
            ))
            .build();
        
        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .contents(asList(title, separator, command_title, 
                        command_help, command_add, 
                    separator, format_title, format, separator,
                    example1_title, example1_Message, separator, example2_title, example2_Message, separator, note_title , note1, note2, note3, note4))
                  .build();
    }

    //エラーメッセージのbody生成
    private Box createBodyBlockException() {
        final Separator separator = Separator.builder().margin(FlexMarginSize.XL).build();
        final Text title =
                Text.builder()
                    .text("形式エラー")
                    .weight(TextWeight.BOLD)
                    .size(FlexFontSize.XL)
                    .color("#ff0000ff")
                    .build();
        
        final Text formatMessage =
                Text.builder()
                    .text("入力された形式が間違っています。\n具体的な理由:"+ CalendarParam.errorMessage+"\n以下の形式と例に従って入力してください。")
                    .weight(TextWeight.BOLD)
                    .size(FlexFontSize.SM)
                    .wrap(true)
                    .margin(FlexMarginSize.XL)
                    .color("#666666")
                    .build();
                    
        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .contents(asList(title, separator,formatMessage, 
                        separator, format_title, format, 
                        separator, example1_title, example1_Message, separator,
                        example2_title, example2_Message))
                  .build();
    }
    
    // 予定URLメッセージのbody生成
    private Box createBodyBlock() {
        final Separator separator = Separator.builder().build();
        final Text title =
                Text.builder()
                    .text(CalendarParam.title)
                    .weight(TextWeight.BOLD)
                    .size(FlexFontSize.XL)
                    .build();

        //final Box review = createReviewBox();

        final Box info = createInfoBox();

        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .contents(asList(title, separator, info))
                  .build();
    }
    
    // 予定URLメッセージのfooter生成
    private Box createFooterBlock() {
        final Spacer spacer = Spacer.builder().size(FlexMarginSize.SM).build();

        final Separator separator = Separator.builder().build();
        final Button websiteAction =
                Button.builder()
                      .style(ButtonStyle.LINK)
                      .height(ButtonHeight.SMALL)
                      .action(new URIAction("Google Calendarに予定追加", URI.create(CalendarParam.URL), null))
                      .build();

        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .spacing(FlexMarginSize.SM)
                  .contents(asList(spacer, separator, websiteAction))
                  .build();
    }

    // 予定URLメッセージの予定情報のbox生成
    private Box createInfoBox() {
        String year_str, date_str ,time_str;
        String year1 = Integer.toString(CalendarParam.calendar1.get(Calendar.YEAR));
        String year2 = Integer.toString(CalendarParam.calendar2.get(Calendar.YEAR));
        String month1 = Integer.toString(CalendarParam.calendar1.get(Calendar.MONTH) + 1);
        String month2 = Integer.toString(CalendarParam.calendar2.get(Calendar.MONTH) + 1);
        String day1 = Integer.toString(CalendarParam.calendar1.get(Calendar.DATE));
        String day2 = Integer.toString(CalendarParam.calendar2.get(Calendar.DATE));
        
        //年が一致しているかで表示を変更している
        if (year1.equals(year2)) {
            year_str = year1;
        } else {
            year_str = year1 + " - " + year2;
        }
        
        //日付が一致しているかで表示を変更している
        if (month1.equals(month2) && day1.equals(day2)) {
            date_str = month1 + "月" + day1 + "日";
        } else {
            date_str = month1 + "月" + day1 + "日 - " + month2 + "月" + day2 + "日";
        }
        
        //終日であるかどうかで表示を変更している
        if (CalendarParam.allday) {
            time_str = "終日";
        } else {

            String hour1 = String.format("%02d", CalendarParam.calendar1.get(Calendar.HOUR_OF_DAY));
            String hour2 = String.format("%02d", CalendarParam.calendar2.get(Calendar.HOUR_OF_DAY));
            String minute1 = String.format("%02d", CalendarParam.calendar1.get(Calendar.MINUTE));
            String minute2 = String.format("%02d", CalendarParam.calendar2.get(Calendar.MINUTE));
            time_str = hour1 + ":" + minute1 + " - " + hour2 + ":" + minute2;
        }

        final Box year = Box
            .builder()
            .layout(FlexLayout.BASELINE)
            .spacing(FlexMarginSize.XL)
            .contents(asList(
                    Text.builder()
                        .text("Year")
                        .color("#aaaaaa")
                        .size(FlexFontSize.SM)
                        .flex(1)
                        .decoration(TextDecoration.UNDERLINE)
                        .weight(TextWeight.BOLD)
                        .build(),
                    Text.builder()
                        .text(year_str+"年")
                        .wrap(true)
                        .color("#666666")
                        .size(FlexFontSize.SM)
                        .flex(4)
                        .build()
            ))
            .build();
        final Box date = Box
            .builder()
            .layout(FlexLayout.BASELINE)
            .spacing(FlexMarginSize.XL)
            .contents(asList(
                    Text.builder()
                        .text("Date")
                        .color("#aaaaaa")
                        .size(FlexFontSize.SM)
                        .flex(1)
                        .decoration(TextDecoration.UNDERLINE)
                        .weight(TextWeight.BOLD)
                        .build(),
                    Text.builder()
                        .text(date_str)
                        .wrap(true)
                        .color("#666666")
                        .size(FlexFontSize.SM)
                        .flex(4)
                        .build()
            ))
            .build();
        final Box time = Box
            .builder()
            .layout(FlexLayout.BASELINE)
            .spacing(FlexMarginSize.XL)
            .contents(asList(
                    Text.builder()
                        .text("Time")
                        .color("#aaaaaa")
                        .size(FlexFontSize.SM)
                        .flex(1)
                        .decoration(TextDecoration.UNDERLINE)
                        .weight(TextWeight.BOLD)
                        .build(),
                    Text.builder()
                        .text(time_str)
                        .wrap(true)
                        .color("#666666")
                        .size(FlexFontSize.SM)
                        .flex(4)
                        .build()
            ))
            .build();
        final Box location = Box
            .builder()
            .layout(FlexLayout.BASELINE)
            .spacing(FlexMarginSize.XL)
            .contents(asList(
                    Text.builder()
                        .text("Location")
                        .color("#aaaaaa")
                        .size(FlexFontSize.SM)
                        .flex(1)
                        .decoration(TextDecoration.UNDERLINE)
                        .weight(TextWeight.BOLD)
                        .build(),
                    Text.builder()
                        .text(CalendarParam.location)
                        .wrap(true)
                        .color("#666666")
                        .size(FlexFontSize.SM)
                        .flex(4)
                        .build()
            ))
            .build();
        final Box details = Box
            .builder()
            .layout(FlexLayout.BASELINE)
            .spacing(FlexMarginSize.XL)
            .contents(asList(
                    Text.builder()
                        .text("Details")
                        .color("#aaaaaa")
                        .size(FlexFontSize.SM)
                        .flex(1)
                        .decoration(TextDecoration.UNDERLINE)
                        .weight(TextWeight.BOLD)
                        .build(),
                    Text.builder()
                        .text(CalendarParam.details)
                        .wrap(true)
                        .color("#666666")
                        .size(FlexFontSize.SM)
                        .flex(4)
                        .build()
            ))
                .build();
            
        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .margin(FlexMarginSize.LG)
                  .spacing(FlexMarginSize.LG)
                  .contents(asList(year, date, time, location, details))
                  .build();
    }
}
