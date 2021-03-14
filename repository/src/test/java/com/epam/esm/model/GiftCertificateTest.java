package com.epam.esm.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class GiftCertificateTest {

    private GiftCertificate giftCertificate;

    @Before
    public void initGiftCertificate() {
        giftCertificate = new GiftCertificate();
    }

    @After
    public void clearGiftCertificate() {
        giftCertificate = null;
    }

    @Test
    public void testSetNamePositive() {
        String expected = "travel";
        giftCertificate.setName("travel");
        String actual = giftCertificate.getName();
        assertEquals(expected, actual);
    }

    @Test
    public void testSetDescription() {
        String expected = "Travel to Spain wiyh sale 50%.";
        giftCertificate.setDescription("Travel to Spain wiyh sale 50%.");
        String actual = giftCertificate.getDescription();
        assertEquals(expected, actual);
    }

    @Test
    public void testSetPrice() {
        BigDecimal expected = new BigDecimal(15.3);
        giftCertificate.setPrice(new BigDecimal(15.3));
        BigDecimal actual = giftCertificate.getPrice();
        assertEquals(expected.doubleValue(), actual.doubleValue(), 0.001);
    }

    @Test
    public void testSetDateOfCreation() {
        LocalDate expected = LocalDate.parse("2019-05-15");
        giftCertificate.setDateOfCreation(LocalDate.parse("2019-05-15"));
        LocalDate actual = giftCertificate.getDateOfCreation();
        assertEquals(expected, actual);
    }

    @Test
    public void testSetDateOfModification() {
        LocalDate expected = LocalDate.parse("2019-05-15");
        giftCertificate.setDateOfModification(LocalDate.parse("2019-05-15"));
        LocalDate actual = giftCertificate.getDateOfModification();
        assertEquals(expected, actual);
    }

    @Test
    public void testSetDuration() {
        Byte expected = 10;
        giftCertificate.setDuration((byte) 10);
        Byte actual = giftCertificate.getDuration();
        assertEquals(expected, actual);
    }
}
