package com.example.individualprojectbe.controller;

import com.example.individualprojectbe.visa.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class VisaControllerTest {

    @Mock
    private VisaService visaService;

    @Mock
    private VisaMapper visaMapper;

    @InjectMocks
    private VisaController visaController;

    @Test
    void getVisa() throws VisaNotFoundException {
        //Given
        long visaId = 1L;
        Visa mockVisa = new Visa();
        VisaDto mockVisaDto = new VisaDto();
        when(visaService.getVisa(visaId)).thenReturn(mockVisa);
        when(visaMapper.mapToVisaDto(mockVisa)).thenReturn(mockVisaDto);

        //When
        ResponseEntity<VisaDto> responseEntity = visaController.getVisa(visaId);

        //Then
        assertEquals(mockVisaDto, responseEntity.getBody());
        verify(visaMapper, times(1)).mapToVisaDto(mockVisa);
    }

    @Test
    void deleteVisa() {
        //Given
        long visaId = 1L;

        //When
        ResponseEntity<Void> responseEntity = visaController.deleteVisa(visaId);

        //Then
        assertEquals(200, responseEntity.getStatusCodeValue());
        verify(visaService, times(1)).deleteVisa(visaId);
    }

    @Test
    void createVisa() {
        //Given
        VisaDto mockVisaDto = new VisaDto();
        Visa mockVisa = new Visa();
        when(visaMapper.mapToVisa(mockVisaDto)).thenReturn(mockVisa);

        //When
        ResponseEntity<VisaDto> responseEntity = visaController.createVisa(mockVisaDto);

        //Then
        assertEquals(200, responseEntity.getStatusCodeValue());
        verify(visaService, times(1)).saveVisa(mockVisa);
    }

    @Test
    void getAllVisas() {
        //Given
        List<Visa> mockVisas = Arrays.asList(new Visa(1L, 1L, "visa-free"), new Visa(1L, 1L, "visa-free"));
        List<VisaDto> mockVisasDto = Arrays.asList(new VisaDto(1L, 1L, "visa-free"), new VisaDto(1L, 1L, "visa-free"));
        when(visaService.getAllVisas()).thenReturn(mockVisas);
        when(visaMapper.mapToVisaDtoList(visaService.getAllVisas())).thenReturn(mockVisasDto);

        //When
        ResponseEntity<List<VisaDto>> responseEntity = visaController.getAllVisas();

        //Then
        assertEquals(mockVisas.size(), responseEntity.getBody().size());
        verify(visaMapper, times(1)).mapToVisaDtoList(mockVisas);
    }

    @Test
    void editVisa() throws VisaNotFoundException {
        //Given
        long visaId = 1L;
        VisaDto mockVisaDto = new VisaDto();
        Visa existingVisa = new Visa();
        Visa updatedVisa = new Visa();
        when(visaService.getVisa(visaId)).thenReturn(existingVisa);
        when(visaMapper.mapToVisa(mockVisaDto)).thenReturn(updatedVisa);
        when(visaService.saveVisa(updatedVisa)).thenReturn(updatedVisa);

        //When
        ResponseEntity<VisaDto> responseEntity = visaController.editVisa(mockVisaDto);

        //Then
        assertEquals(200, responseEntity.getStatusCodeValue());
        verify(visaService, times(1)).saveVisa(updatedVisa);
        verify(visaMapper, times(1)).mapToVisaDto(updatedVisa);
    }
}
