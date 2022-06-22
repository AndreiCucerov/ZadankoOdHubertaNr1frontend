package com.example.zadankoodhubertanr1.controller;

import com.example.zadankoodhubertanr1.model.Car;
import com.example.zadankoodhubertanr1.model.CarDto;
import com.example.zadankoodhubertanr1.Requests.CreateCarRequest;
import com.example.zadankoodhubertanr1.Requests.UpdateCarRequest;
import com.example.zadankoodhubertanr1.service.CarService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final ModelMapper modelMapper;
    private final CarService carService;

    @GetMapping
    public String main(Model model){
        return "index";
    }

    @GetMapping("/get")
    public String get(){
        return "get";
    }

    @PostMapping("/show")
    public String show(@RequestParam Long id, Model model){
         model.addAttribute("car",modelMapper.map(carService.findCarById(id), CarDto.class));
        return "show";
    }
    @GetMapping("/save")
    public String add(Model model){
        model.addAttribute("car",new CreateCarRequest());
        return "save";
    }

    @PostMapping("/saving")
    public String add(CreateCarRequest createCarRequest, Model model){
        Car mappedCar = modelMapper.map(createCarRequest, Car.class);
        carService.addCar(mappedCar);
        CarDto carDto = modelMapper.map(mappedCar, CarDto.class);
        model.addAttribute("car",carDto);
        return "succeed";
   }

    @GetMapping("/searchByProducer")
    public String searchByProducer(){
        return "searchByProducer";
    }
    @PostMapping("showByProducer")
    public String search(
            @RequestParam String producer,
            @PageableDefault Pageable page, Model model){
        List<CarDto>cars=carService.findBmwOwner(producer).stream().map(x->modelMapper.map(x,CarDto.class)).collect(Collectors.toList());
        Page<CarDto> carsDto =  new PageImpl<>(cars,page,cars.size());
        model.addAttribute("cars",carsDto);
        return "showByProducer";
    }

    @GetMapping("/showAll")
    public String showAllCars ( @PageableDefault Pageable pageable, Model model){
        List<CarDto> carsDto = carService.findAllCars()
                .stream().map(x -> modelMapper.map(x, CarDto.class)).collect(Collectors.toList());
                model.addAttribute("cars", carsDto);
        return "showAll";
    }




    @GetMapping("/delete")
    public String deleteById(){
        return "delete";
    }


    @PostMapping("/deleting")
    public String delete(@RequestParam long id){
        carService.deleteCarById(id);
        return  "index";
    }

    @GetMapping("/update")
    public String UpdateOwner(Model model){
        model.addAttribute("car",new UpdateCarRequest());
        return "update";
    }
    @PostMapping("/update")
    String updateOwner(UpdateCarRequest updateCarRequest){
        carService.updateOwner(updateCarRequest.getId(),modelMapper.map(updateCarRequest, Car.class));

        return "index";
    }
















//    @GetMapping("/searchByProducer")
//    ResponseEntity <List<CarDto>>searchByProducer(
//            @RequestParam String producer){
//
//
//        return ResponseEntity.ok(carService.findBmwOwner(producer).stream().map(x->modelMapper.map(x,CarDto.class)).collect(Collectors.toList()));
//    }



        //TODO: metoda znajdująca wszystkie samochody, usuwająca samochód po ID, zmieniająca np. właściciela danego samochodu, znajdująca wszystkie samochody marki bmw


//    @GetMapping("/")
//    String home(){
//        return "index";
//    }
//    @GetMapping("/save")
//    String save(Model model){
//        model.addAttribute("car",new CarDto());
//        return "save";
//    }
//    @PostMapping("/saving")
//    String saving(@ModelAttribute("car")CarDto carDto){
//            return "succed";
//    }
//
//    @GetMapping("/show")
//    ResponseEntity<List<CarDto>> show(){
//        List<CarDto>cars= carRepository.findAll().stream()
//                .map(x->modelMapper.map(x, CarDto.class)).collect(Collectors.toList());
//        if(cars!=null){
//            return ResponseEntity.ok(cars);
//        }else return ResponseEntity.notFound().build();
//
//    }
//

}
