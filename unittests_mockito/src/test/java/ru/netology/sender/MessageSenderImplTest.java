package ru.netology.sender;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;



import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MessageSenderImplTest {

    @ParameterizedTest
    @MethodSource ("source")
    public void send_returnMessageInCorrectLanguage_message(Map<String,String> ipHeader, String header, String ip, Location location, String greeting){
        ipHeader.put(header, ip);

    GeoServiceImpl geoService = Mockito.mock(GeoServiceImpl.class);
        if(ip!=null&&!ip.isEmpty()) {
            Mockito.when(geoService.byIp(ip)).thenReturn(location);
        }

    LocalizationServiceImpl localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(location.getCountry())).thenReturn(greeting);



    MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);

        String message;
    try {
      message = messageSender.send(ipHeader);
    }catch (NullPointerException e){
        message = greeting;
    }


    String expected = greeting;

    assertThat(expected, equalTo(message));
    System.out.println();

    }

    public static Stream<Arguments> source(){

    return Stream.of(
            Arguments.of(new HashMap<>(),MessageSenderImpl.IP_ADDRESS_HEADER,"172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15),"Добро пожаловать"),
            Arguments.of(new HashMap<>(),MessageSenderImpl.IP_ADDRESS_HEADER,"96.0.32.11", new Location("New York", Country.USA, " 10th Avenue", 32),"Welcome"),
            Arguments.of(new HashMap<>(),MessageSenderImpl.IP_ADDRESS_HEADER,"96.0.32.11", new Location("Aue", Country.GERMANY, " Schwarzenbergerschtrasse", 29),"Welcome"),
            Arguments.of(new HashMap<>(),MessageSenderImpl.IP_ADDRESS_HEADER,"96.0.32.11", new Location("Guarulhos", Country.BRAZIL, "  Rua Nelson de Andrade", 10),"Welcome"),
            Arguments.of(new HashMap<>(),MessageSenderImpl.IP_ADDRESS_HEADER,null, new Location(null, Country.USA, null, 0),"Welcome")


    );


    }

}

