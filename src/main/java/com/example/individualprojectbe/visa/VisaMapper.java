package com.example.individualprojectbe.visa;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VisaMapper {
    Visa mapToVisa(VisaDto visaDto);
    VisaDto mapToVisaDto(Visa visa);
    List<VisaDto> mapToVisaDtoList(List<Visa> visaList);
    List<Visa> mapToVisaList(List<VisaDto> visaList);
}
