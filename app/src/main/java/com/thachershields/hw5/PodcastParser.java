package com.thachershields.hw5;

import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by thach on 10/26/2017.
 */

public class PodcastParser {

    public static class PodcastSAXParser extends DefaultHandler{

    }

    public static class PodcastPullParser{
        public static ArrayList<Podcast> parsePodcasts(InputStream inputStream) throws XmlPullParserException, IOException{
            ArrayList<Podcast> podcasts = new ArrayList<>();
            Podcast podcast = new Podcast();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(inputStream, "UTF-8");

            int event = parser.getEventType();
            int counter = 0;

            while(event != XmlPullParser.END_DOCUMENT){

                switch (event){
                    case XmlPullParser.START_TAG:

                        if(parser.getName().equals("entry")) {
                            podcast = new Podcast();
                        }
                        else if(parser.getName().equals("title")){
                            podcast.setTitle(parser.nextText().trim());
                            System.out.println(podcast.getTitle());
                        }
                        else if(parser.getName().equals("summary")){
                            podcast.setSummary(parser.nextText().trim());
                            System.out.println(podcast.getSummary());
                        }
                        else if(parser.getName().equals("im:image")){
                            if(parser.getAttributeValue(null, "height").equals("60")){
                                podcast.setUrlToImage(parser.nextText().trim());
                                System.out.println(podcast.getUrlToImage());
                            }
                        }
                        else if(parser.getName().equals("im:releaseDate")){
                            podcast.setDate(parser.nextText().trim());
                            System.out.println(podcast.getDate());
                        }

                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("entry")){
                            podcasts.add(podcast);
                            System.out.println(podcasts.get(counter));
                            counter++;
                        }
                    default:
                        break;
                }

                event = parser.next();
            }

            return podcasts;
        }
    }
}

