/*
 * Copyright 2018 LINE Corporation
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

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
import com.linecorp.bot.model.message.flex.component.Icon;
import com.linecorp.bot.model.message.flex.component.Image;
import com.linecorp.bot.model.message.flex.component.Image.ImageAspectMode;
import com.linecorp.bot.model.message.flex.component.Image.ImageAspectRatio;
import com.linecorp.bot.model.message.flex.component.Image.ImageSize;
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
public class ExampleFlexMessageSupplier implements Supplier<FlexMessage> {
    String text;
    CalendarEntity CalendarParam;
    boolean debug = true;
    @Override
    public FlexMessage get() {
        /*
        final Image heroBlock = Image.builder().url(URI.create("https://example.com/cafe.jpg"))
                .size(ImageSize.FULL_WIDTH).aspectRatio(ImageAspectRatio.R20TO13).aspectMode(ImageAspectMode.Cover)
                .action(new URIAction("label", URI.create("http://example.com"), null)).build();
        */

        if (CalendarParam.error) {
            final Box bodyBlockException = createBodyBlockException();
            final Bubble bubble = Bubble
                .builder()
                .size(BubbleSize.GIGA)
                .body(bodyBlockException)
                .build();
            return new FlexMessage("形式を間違えています", bubble);
        } else {
            if (debug)System.out.println("ExampleFlexMessageSupplierのtext : " + text + "\n");
            if (debug)System.out.println("CalendarParam.title : " + CalendarParam.title + "\n");
            if (debug)System.out.println("CalendarParam.URL : " + CalendarParam.URL + "\n");
            if (debug)System.out.println("年 : " + Integer.toString(CalendarParam.calendar1.get(Calendar.YEAR)) + "年" + "\n");
            if (debug)System.out.println("月日 : " + Integer.toString(CalendarParam.calendar1.get(Calendar.MONTH) + 1) + "月"
                        + Integer.toString(CalendarParam.calendar1.get(Calendar.DATE)) + "日" + "\n");
            if (debug)System.out.println("時間分 : " + Integer.toString(CalendarParam.calendar1.get(Calendar.HOUR_OF_DAY)) + ":"
                        + Integer.toString(CalendarParam.calendar1.get(Calendar.MINUTE)) + " - "
                        + Integer.toString(CalendarParam.calendar2.get(Calendar.HOUR_OF_DAY)) + ":"
                        + Integer.toString(CalendarParam.calendar2.get(Calendar.MINUTE)) + "\n");
            if (debug)System.out.println("CalendarParam.location : " + CalendarParam.location + "\n");
            if (debug)System.out.println("CalendarParam.details : " + CalendarParam.details + "\n");
            final Box bodyBlock = createBodyBlock();
            final Box footerBlock = createFooterBlock();
            // https://developers.line.biz/ja/reference/messaging-api/#bubble
            final Bubble bubble = Bubble
                .builder()
                .size(BubbleSize.GIGA)
                .body(bodyBlock)
                .footer(footerBlock)
                .build();
            if (debug)
                System.out.println("bubble\n");
            return new FlexMessage("Google Calendarに予定追加", bubble);
        }
    }
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
                    .text("入力された形式が間違っています。\n具体的な理由:"+ CalendarParam.errorMessage+"\n以下の例に従って入力してください。\n")
                    .weight(TextWeight.BOLD)
                    .size(FlexFontSize.SM)
                    .wrap(true)
                    .color("#666666")
                    .build();                
        
        final Text format =
                Text.builder()
                    .text(" \n予定追加\n件名\n年/月/日 時間:分\n年/月/日 時間:分\n場所\n詳細")
                    .weight(TextWeight.BOLD)
                    .size(FlexFontSize.SM)
                    .color("#666666")
                    .wrap(true)
                    .build();
        //final Box review = createReviewBox();

        final Text title_example =
                Text.builder()
                    .text("例")
                    .weight(TextWeight.BOLD)
                    .size(FlexFontSize.XL)
                    .margin(FlexMarginSize.XL)
                    .build();

        final Text exampleMessage =
                Text.builder()
                    .text(" \n予定追加\nオフ会\n2021/02/01 12:00\n2021/02/01 15:00\n大阪駅\n飯食ってカラオケ")
                    .weight(TextWeight.BOLD)
                    .size(FlexFontSize.SM)
                    .color("#666666")
                    .wrap(true)
                    .build();
        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .contents(asList(title, formatMessage, format, 
                        separator, title_example, exampleMessage))
                  .build();
    }

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
    
    private Box createFooterBlock() {
        final Spacer spacer = Spacer.builder().size(FlexMarginSize.SM).build();
        /*
        final Button callAction = Button
                .builder()
                .style(ButtonStyle.LINK)
                .height(ButtonHeight.SMALL)
                .action(new URIAction("CALL", URI.create("tel:000000"), null))
                .build();
        */
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

    private Box createInfoBox() {
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
                        .text(Integer.toString(CalendarParam.calendar1.get(Calendar.YEAR))+"年")
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
                        .text(
                            Integer.toString(CalendarParam.calendar1.get(Calendar.MONTH)+1)+
                            "月"+ 
                            Integer.toString(CalendarParam.calendar1.get(Calendar.DATE))+
                            "日"
                        )
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
                        .text(
                            Integer.toString(CalendarParam.calendar1.get(Calendar.HOUR_OF_DAY))+
                            ":"+
                            Integer.toString(CalendarParam.calendar1.get(Calendar.MINUTE))+
                            " - "+
                            Integer.toString(CalendarParam.calendar2.get(Calendar.HOUR_OF_DAY))+
                            ":"+
                            Integer.toString(CalendarParam.calendar2.get(Calendar.MINUTE))
                        )
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
    /*
    private Box createReviewBox() {
        final Icon goldStar =
                Icon.builder().size(FlexFontSize.SM).url(URI.create("https://example.com/gold_star.png")).build();
        final Icon grayStar =
                Icon.builder().size(FlexFontSize.SM).url(URI.create("https://example.com/gray_star.png")).build();
        final Text point =
                Text.builder()
                    .text("4.0")
                    .size(FlexFontSize.SM)
                    .color("#999999")
                    .margin(FlexMarginSize.MD)
                    .flex(0)
                    .build();

        return Box.builder()
                  .layout(FlexLayout.BASELINE)
                  .margin(FlexMarginSize.MD)
                  .contents(asList(goldStar, goldStar, goldStar, goldStar, grayStar, point))
                  .build();
    }
    */
}
