/**
 *
 *  @author Stachurski Filip S18965
 *
 */

package S_PASSTIME_SERVER1;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class Time {

    public static String passed(String from, String to) {
        LocalDateTime localFromT;
        LocalDateTime localToT;
        LocalDate localFrom;
        LocalDate localTo;
        String dpatt = "d MMMM yyyy (EEEE)";
        String tpatt = "d MMMM yyyy (EEEE) 'godz.' HH:mm";
        Locale pl = new Locale("pl");
        ZonedDateTime zdtFrom;
        ZonedDateTime zdtTo;
        int minutes;
        int hours;
        double days;
        double weeks;
        String result="";

        if(from.contains("T")){
            try{
                localFromT = LocalDateTime.parse(from);
                localToT = LocalDateTime.parse(to);
                localFrom=localFromT.toLocalDate();
                localTo=localToT.toLocalDate();
                zdtFrom = ZonedDateTime.of(localFromT, ZoneId.of("Europe/Warsaw"));
                zdtTo = ZonedDateTime.of(localToT, ZoneId.of("Europe/Warsaw"));
                result+="Od ";
                result+=localFromT.format( DateTimeFormatter.ofPattern(tpatt));
                result+=" do ";
                result+=localToT.format( DateTimeFormatter.ofPattern(tpatt));
                weeks=Double.parseDouble(String.format(pl,"%.2f",
                        ChronoUnit.DAYS.between(localFrom, localTo)/7.0).replace(",","."));
                days=ChronoUnit.DAYS.between(localFrom, localTo);
                hours=(int)ChronoUnit.HOURS.between(zdtFrom, zdtTo);
                minutes=(int)ChronoUnit.MINUTES.between(zdtFrom, zdtTo);
                result+="\n - mija: "+(int)days;
                if(days==1){
                    result+= " dzień, ";
                } else if(days>1 ||days==0){
                    result+=" dni,";
                }
                if(weeks%1==0){
                    result+="tygodni "+(int)weeks;
                }else{
                    result+="tygodni "+weeks;
                }
                result+="\n - godzin: "+ hours+", minut: " +minutes;
                if(days>0){
                    result+="\n - kalendarzowo: ";
                    result+=byPeriod(localFromT.toLocalDate(),localToT.toLocalDate());
                }
            }catch(DateTimeParseException d){
                result+="*** "+d.toString();
            }
        }else{
            try{
                localFrom = LocalDate.parse(from);
                localTo = LocalDate.parse(to);
                result+="Od ";
                result+=localFrom.format( DateTimeFormatter.ofPattern(dpatt));
                result+=" do ";
                result+=localTo.format( DateTimeFormatter.ofPattern(dpatt));
                weeks=Double.parseDouble(String.format(pl,"%.2f",
                        ChronoUnit.DAYS.between(localFrom, localTo)/7.0).replace(",","."));
                days=ChronoUnit.DAYS.between(localFrom, localTo);
                result+="\n - mija: "+(int)days;
                if(days==1){
                    result+= " dzień, ";
                } else if(days>1 ||days==0){
                    result+=" dni,";
                }
                if(weeks%1==0){
                    result+=" tygodni "+(int)weeks;
                }else{
                    result+=" tygodni "+weeks;
                }
                if(days>0){
                    result+="\n - kalendarzowo: ";
                    result+=byPeriod(localFrom, localTo);
                }
            }catch(DateTimeParseException d){
                result+="*** "+d.toString();
            }
        }
        return result;
    }

    static String byPeriod(LocalDate d1, LocalDate d2) {
        String result="";
        Period p = Period.between(d1, d2);
        int days=p.getDays();
        int months=p.getMonths();
        int years=p.getYears();

        if(years>0){
            if(years==1){
                result+=years+ " rok";
            }else if(years<5){
                {
                    result+=years +" lata";
                }
            }else{
                result+=years +" lat";
            }
        }
        if(months>0){
            if(years>0){
                result+=", ";
            }
            if(months==1){
                result+=months+ " miesiąc";
            }else if(months<5){
                {
                    result+=months +" miesiące";
                }
            }else{
                result+=months +" miesięcy";
            }
        }
        if(days>0){
            if(months>0 || years>0){
                result+=", ";
            }

            if(days==1){
                result+=days+ " dzień";
            }else{
                result+=days +" dni";
            }
        }
        return result;
    }


}
