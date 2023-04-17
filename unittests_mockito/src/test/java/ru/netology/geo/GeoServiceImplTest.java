package ru.netology.geo;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import java.util.stream.Stream;



public class GeoServiceImplTest {
    private final static String LOCALHOST = "127.0.0.1";
    private final static String MOSCOW_IP = "172.0.32.11";
    private final static String NEW_YORK_IP = "96.44.183.149";
    private final static String randomRusIp = "172.168.41.11";
    private final static String randomEngIp = "96.45.132.154";
    private final static String incorrectIp  = "231.45.132.154";

    GeoServiceImpl sut = new GeoServiceImpl();
    @ParameterizedTest
    @MethodSource ("source")
    public void byIp_countrySelectionByIP_correctCountry(String ip, Location expected){

        var location  = sut.byIp(ip);


        if(expected==null){
            Assertions.assertNull(location);
        }else {
            Assertions.assertEquals(expected.toString(), location.toString());
        }

    }

    public static Stream<Arguments> source(){
        return Stream.of(
                Arguments.of(LOCALHOST, new Location(null,null,null, 0)),
                Arguments.of(MOSCOW_IP, new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of(NEW_YORK_IP, new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of(randomRusIp, new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of(randomEngIp, new Location("New York", Country.USA, null,  0)),
                Arguments.of(incorrectIp, null)
        );


    }

    @Test
    public void byCoordinates_throwRuntimeException_notImplemented(){
        double latitude = 11.118052;
        double longtitude= 155.890972;
        boolean thrown = false;

    try {
    Location location = sut.byCoordinates(latitude, longtitude);
    }catch(RuntimeException e){
       if (e.getMessage().equals("Not implemented")){
           thrown = true;
       }
    }

        Assertions.assertTrue(thrown);
    }








}
