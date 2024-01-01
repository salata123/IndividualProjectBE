package com.example.individualprojectbe.visa;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/visa")
@RequiredArgsConstructor
public class VisaController {
    @Autowired
    private VisaService visaService;
    @Autowired
    private VisaMapper visaMapper;

    @GetMapping
    public ResponseEntity<List<VisaDto>> getAllVisas(){
        List<Visa> visas = visaService.getAllVisas();
        return ResponseEntity.ok(visaMapper.mapToVisaDtoList(visas));
    }

    @GetMapping("{visaId}")
    public ResponseEntity<VisaDto> getVisa(@PathVariable long visaId) throws VisaNotFoundException {
        return ResponseEntity.ok(visaMapper.mapToVisaDto(visaService.getVisa(visaId)));
    }

    @DeleteMapping("{visaId}")
    public ResponseEntity<Void> deleteVisa(@PathVariable long visaId){
        visaService.deleteVisa(visaId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VisaDto> createVisa(@RequestBody VisaDto visaDto){
        Visa visa = visaMapper.mapToVisa(visaDto);
        visaService.saveVisa(visa);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{visaId}")
    public ResponseEntity<VisaDto> editVisa(@RequestBody VisaDto visaDto){
        Visa visa = visaMapper.mapToVisa(visaDto);
        Visa savedVisa = visaService.saveVisa(visa);
        return ResponseEntity.ok(visaMapper.mapToVisaDto(savedVisa));
    }
}
