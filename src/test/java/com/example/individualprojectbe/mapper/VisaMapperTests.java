package com.example.individualprojectbe.mapper;

import com.example.individualprojectbe.visa.Visa;
import com.example.individualprojectbe.visa.VisaDto;
import com.example.individualprojectbe.visa.VisaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class VisaMapperTests {
    private VisaMapper visaMapper;
    private VisaDto visaDto;
    private Visa visa;

    @BeforeEach
    void setUp() {
        visaMapper = Mappers.getMapper(VisaMapper.class);

        visa = new Visa(1L, 1L, "visa-free");
        visaDto = new VisaDto(1L, 1L, "visa-free");
    }

    @Test
    void mapToVisa() {
        Visa mappedVisa = visaMapper.mapToVisa(visaDto);

        assertNotNull(mappedVisa);
        assertEquals(visaDto.getId(), mappedVisa.getId());
        assertEquals(visaDto.getFlightId(), mappedVisa.getFlightId());
        assertEquals(visaDto.getVisaType(), mappedVisa.getVisaType());
    }

    @Test
    void mapToVisaDto() {
        VisaDto mappedVisaDto = visaMapper.mapToVisaDto(visa);

        assertNotNull(mappedVisaDto);
        assertEquals(visa.getId(), mappedVisaDto.getId());
        assertEquals(visa.getFlightId(), mappedVisaDto.getFlightId());
        assertEquals(visa.getVisaType(), mappedVisaDto.getVisaType());
    }

    @Test
    void mapToVisaDtoList() {
        List<Visa> visaList = List.of(visa);
        List<VisaDto> mappedVisaDtoList = visaMapper.mapToVisaDtoList(visaList);

        assertNotNull(mappedVisaDtoList);
        assertEquals(1, mappedVisaDtoList.size());
    }

    @Test
    void mapToVisaList() {
        List<VisaDto> visaDtoList = List.of(visaDto);
        List<Visa> mappedVisaList = visaMapper.mapToVisaList(visaDtoList);

        assertNotNull(mappedVisaList);
        assertEquals(1, mappedVisaList.size());
    }
}
