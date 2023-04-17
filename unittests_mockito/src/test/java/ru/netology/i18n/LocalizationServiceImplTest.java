package ru.netology.i18n;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.AdditionalMatchers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.*;
import java.util.stream.Stream;


public class LocalizationServiceImplTest {
    private Map<Country, String> countryGreetingTest = new HashMap<>();
    private LocalizationServiceImpl sut;


    @BeforeEach
    public void beforeTest(){
        countryGreetingTest.put(Country.RUSSIA, "Добро пожаловать");
        countryGreetingTest.put(Country.GERMANY, "Welcome");
        countryGreetingTest.put(Country.USA, "Welcome");
        countryGreetingTest.put(Country.BRAZIL, "Welcome");
        sut = new LocalizationServiceImpl();

    }

    @AfterEach
    public void afterTest(){
        countryGreetingTest.clear();
        sut = null;
    }


    @ParameterizedTest
    @MethodSource("source")
    public void locale_returnsTheGreetingAccordingToTheCountry_rusOrEngGreeting(Country country){
        String expected = countryGreetingTest.get(country);

        var countryGreeting =  sut.locale(country);

        Assertions.assertEquals(expected,countryGreeting);

    }

    public static Stream<Arguments> source(){
        return Stream.of(
                Arguments.of(Country.RUSSIA),
                Arguments.of(Country.GERMANY),
                Arguments.of(Country.USA),
                Arguments.of(Country.BRAZIL)
                        );
    }

}
